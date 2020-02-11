import json
import logging
import os
import threading

import docker
import urllib3
from docker.types import ServiceMode

from com.autoscaler.exception.ServiceNotFoundException import ServiceNotFoundException

logger = logging.getLogger(__name__)

def jsons(resp):
    pass

class DockerService(object):
    def __init__(self):
        self.docker_engine = docker.from_env()

    def get_req_rate(self,service_name):
        req_rate = None
        req_count_url = os.environ["REQ_COUNT_URL"]
        logger.info("REQ_COUNT_URL {}".format(req_count_url))
        resp = urllib3.PoolManager().request('GET',req_count_url)
        resp = json.loads(resp.data.decode('utf-8'))
        if 'measurements' in resp:
            resp=next(filter(lambda x: x['statistic'] == 'COUNT', resp['measurements']))
            if 'value' in resp:
                req_rate = int(resp['value'])
        return req_rate

    def get_cpu_usage(self,service_name):

        #jsonStr = {"cpu_stats": { "cpu_usage": { "total_usage": 43746356446 } } }
        #jsonStr = json.dumps(jsonStr)
        #jsonStr = json.loads(jsonStr)
        #jsonStr = jsonStr["cpu_stats"]
        total_cpu_usage = 0
        total_system_cpu_usage = 0
        total_cpu_count = 0
        try:
            client = docker.from_env()
            containerLst = client.containers.list()
            service_count = 0
            for container in containerLst:
                stats = container.stats(stream=False)
                if service_name in stats["name"]:
                    service_count += 1
                    cpu_stats = stats["cpu_stats"]
                    cpu_usage = None
                    if cpu_stats is not None:
                        cpu_usage = cpu_stats["cpu_usage"]
                        system_cpu_usage = cpu_stats["system_cpu_usage"]
                        cpu_count = cpu_stats["online_cpus"]
                        if cpu_usage is not None:
                            cpu_usage = cpu_usage["total_usage"]
                            if cpu_usage is not None:
                                total_cpu_usage += cpu_usage
                        if system_cpu_usage is not None:
                            total_system_cpu_usage += system_cpu_usage
                        if cpu_count is not None:
                            total_cpu_count += cpu_count

        except Exception as e:
            logger.info(e.__str__())

        print(total_cpu_usage, total_system_cpu_usage, total_cpu_count)

        if service_count > 0:
            if total_cpu_usage is not None:
                total_cpu_usage = total_cpu_usage / service_count
            if total_system_cpu_usage is not None:
                total_system_cpu_usage = total_system_cpu_usage / service_count
            if total_cpu_count is not None:
                total_cpu_count = total_cpu_count / service_count

        print(total_cpu_usage, total_system_cpu_usage, total_cpu_count)
        return total_cpu_usage, total_system_cpu_usage, total_cpu_count

    def _get_service(self, service_name):
        services = self.docker_engine.services.list(filters=dict(name=service_name))            
        if (not services):
            raise ServiceNotFoundException(service_name)                     
        return services[0]

    def get_service_replica_count(self, service_name):
        service = self._get_service(service_name)
        return service['Spec']['Mode']['Replicated']['Replicas']

    def scale_service(self, service_name, replica_count):
        service = self._get_service(service_name)
        service.update(mode=ServiceMode("replicated", replicas=replica_count))
