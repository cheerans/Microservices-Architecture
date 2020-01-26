import threading
from datetime import datetime, time
import logging

import docker

from com.autoscaler.constants.Constants import DELTA_CPU, DELTA_REQ

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
            self.docker_engine = docker.from_env()
            #containerLst = self.docker_engine.containers.list(filters=dict(name=service_name))
            containerLst = self.docker_engine.containers.list()
            logger.info("CONTAINERS_LIST {}".format(containerLst))

            scale_min = autoscale_rule['scale_min']
            scale_max = autoscale_rule['scale_max']
            scale_step = autoscale_rule['scale_step']
            x = threading.Thread(target=self.decide_scale_thread,
                                 args=(service_name, scale_min, scale_max, scale_step,))
            x.start()

    def decide_scale_thread(self, service_name, scale_min, scale_max, scale_step):

        req_rate_key = service_name + "_" + "req_rate"
        cpu_usage_key = service_name + "_" + "cpu_usage"

        req_rate_start = None
        if req_rate_key in self.traffic_map:
            req_rate_start = self.traffic_map[req_rate_key]
        cpu_usage_start = None
        if cpu_usage_key in self.traffic_map:
            cpu_usage_start = self.traffic_map[cpu_usage_key]
        req_rate_end = self.dockerSvc.get_req_rate(service_name)
        cpu_usage_end = self.dockerSvc.get_cpu_usage(service_name)

        self.traffic_map[req_rate_key] = req_rate_end
        self.traffic_map[cpu_usage_key] = cpu_usage_end

        if req_rate_start is None or cpu_usage_start is None:
            return

        scale_up = (((req_rate_start - req_rate_end) / self.config['poll_interval_seconds']) > DELTA_REQ) or \
                   ((cpu_usage_start - cpu_usage_end) > DELTA_CPU)
        scale_down = (((req_rate_start - req_rate_end) / self.config['poll_interval_seconds']) < -DELTA_REQ) or \
                     ((cpu_usage_start - cpu_usage_end) < -DELTA_CPU)

        # systemDelta = float(docker_system_usage2) - float(docker_system_usage1)
        # daoke_cpu_num = str(str(self.client.inspect_container(str(container)))).split('DAOKECPU=')[-1].split("\'")[0]
        # if cpuDelta >= 0 and systemDelta >= 0:
        #     if daoke_cpu_num == "{u":
        #         daoke_cpu_num = 24
        #         return round((float(cpuDelta) / float(systemDelta) * cpu_num * 100.0) / float(daoke_cpu_num), 2)
        #     elif daoke_cpu_num == "0":
        #         daoke_cpu_num = 24
        #         return round((float(cpuDelta) / float(systemDelta) * cpu_num * 100.0) / float(daoke_cpu_num), 2)
        #     else:
        #         return round((float(cpuDelta) / float(systemDelta) * cpu_num * 100.0) / float(daoke_cpu_num), 2)

        if scale_up:
            current_replica_count = self.docker_client.get_service_replica_count(service_name=service_name)
            logger.debug("Replica count for {}: {}".format(service_name, current_replica_count))
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
