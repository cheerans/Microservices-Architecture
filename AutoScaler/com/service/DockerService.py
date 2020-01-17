import json

import docker
import urllib3
from docker.types import ServiceMode

from com.autoscaler.exception.ServiceNotFoundException import ServiceNotFoundException


def jsons(resp):
    pass


class DockerService(object):
    def __init__(self):
        self.docker_engine = docker.from_env()

    def get_req_rate(self,service_name):
        req_rate = None
        resp = urllib3.PoolManager().request('GET','http://localhost:8091/actuator/metrics/req.count')
        resp = resp.data
        resp=json.loads(resp)
        if 'measurements' in resp:
            resp=next(filter(lambda x: x['statistic'] == 'COUNT', resp['measurements']))
            if 'value' in resp:
                req_rate = int(resp['value'])
        return req_rate

    def get_cpu_usage(self,service_name):

        req_rate = None
        resp = urllib3.PoolManager().request('GET','http://localhost:8091/actuator/metrics/req.count')
        resp = resp.data
        resp=json.loads(resp)
        if 'measurements' in resp:
            resp=next(filter(lambda x: x['statistic'] == 'COUNT', resp['measurements']))
            if 'value' in resp:
                req_rate = int(resp['value'])
        return req_rate

    def _get_service(self, service_name):
        services = self.docker_engine.services.list(filters=dict(name=service_name))
        if (not services):
            raise ServiceNotFoundException(service_name)
        return services[0]

    def get_service_replica_count(self, service_name):
        service = self._get_service(service_name)
        return service.attrs['Spec']['Mode']['Replicated']['Replicas']

    def scale_service(self, service_name, replica_count):
        service = self._get_service(service_name)
        service.update(mode=ServiceMode("replicated", replicas=replica_count))
