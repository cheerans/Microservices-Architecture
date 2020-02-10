import threading
from datetime import datetime, time
import logging

from com.autoscaler.constants.Constants import HIGH_CPU_USAGE_PERCENT, LOW_CPU_USAGE_PERCENT

logger = logging.getLogger(__name__)


class AutoScaleStrategy(object):
    traffic_map = {}

    def __init__(self, config, dockerSvc, scheduler, datetime_module=None):

        self.config = config
        self.dockerSvc = dockerSvc
        self.scheduler = scheduler
        self.datetime_module = datetime_module or datetime

    def start(self):

        job = self.scheduler.add_job(self.run, 'interval', seconds=self.config['poll_interval_seconds'])
        job.modify(next_run_time=self.datetime_module.now(self.scheduler.timezone))
        self.scheduler.start()

    def run(self):

        autoscale_rules = self.config['autoscale_config']
        for autoscale_rule in autoscale_rules:
            service_name = autoscale_rule['service_name']
            logger.info("SERVICE_NAME {}".format(service_name))
            scale_min = autoscale_rule['scale_min']
            scale_max = autoscale_rule['scale_max']
            scale_step = autoscale_rule['scale_step']
            x = threading.Thread(target=self.decide_scale_thread,
                                 args=(service_name, scale_min, scale_max, scale_step,))
            x.start()

    def decide_scale_thread(self, service_name, scale_min, scale_max, scale_step):

        system_cpu_usage_key = service_name + "_" + "system_cpu_usage"
        cpu_usage_key = service_name + "_" + "cpu_usage"

        system_cpu_usage_start = None
        if system_cpu_usage_key in self.traffic_map:
            system_cpu_usage_start = self.traffic_map[system_cpu_usage_key]

        cpu_usage_start = None

        if cpu_usage_key in self.traffic_map:
            cpu_usage_start = self.traffic_map[cpu_usage_key]

        cpu_usage_end, system_cpu_usage_end, cpu_count = self.dockerSvc.get_cpu_usage(service_name)

        logger.info("cpu_usage_end, system_cpu_usage_end, cpu_count".
                    format(cpu_usage_end, system_cpu_usage_end, cpu_count))

        self.traffic_map[system_cpu_usage_key] = system_cpu_usage_end
        self.traffic_map[cpu_usage_key] = cpu_usage_end

        if system_cpu_usage_start is None or cpu_usage_start is None:
            return

        delta_system_cpu_usage = system_cpu_usage_start - system_cpu_usage_end
        delta_cpu_usage = cpu_usage_start - cpu_usage_end
        cpu_usage_change_percent = (delta_cpu_usage/delta_system_cpu_usage) * 100
        scale_up = (cpu_usage_change_percent > 0) and (cpu_usage_change_percent > HIGH_CPU_USAGE_PERCENT)
        scale_down = (cpu_usage_change_percent < 0) and (cpu_usage_change_percent < LOW_CPU_USAGE_PERCENT)

        if scale_up:
            current_replica_count = self.docker_client.get_service_replica_count(service_name=service_name)
            logger.debug("Replica count for {}:{}".format(service_name, current_replica_count))
            if (current_replica_count + scale_step) <= scale_max:
                logger.info("Scaling up {} from {} to {} as metric value is {}".format(
                    service_name,
                    current_replica_count,
                    current_replica_count + scale_step)
                )
                self.docker_client.scale_service(
                    service_name=service_name,
                    replica_count=current_replica_count + scale_step
                )

        if scale_down:
            if (current_replica_count - scale_step) >= scale_min:
                logger.info("Scaling down {} from {} to {} as metric value is {}".format(
                    service_name,
                    current_replica_count,
                    current_replica_count - scale_step)
                )
                self.docker_client.scale_service(
                    service_name=service_name,
                    replica_count=current_replica_count - scale_step
                )
