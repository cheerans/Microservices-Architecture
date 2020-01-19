import logging
import os

from apscheduler.schedulers.blocking import BlockingScheduler
from pytz import utc
import yaml
from com.autoscaler.service.AutoScaleStrategy import AutoScaleStrategy
from com.autoscaler.service.DockerService import DockerService


DEFAULT_LOG_LEVEL = 'info'

logger = logging.getLogger(__name__)

if __name__ == "__main__":
    logging.basicConfig(format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
                        level=logging.getLevelName(DEFAULT_LOG_LEVEL.upper()))

    config_filename = os.environ["CONFIG_FILE"]
    with open(config_filename) as config_file:
        config = yaml.load(config_file)
        logger.debug("Config %s", config)
        dockerSvc = DockerService()
        scheduler = BlockingScheduler(timezone=utc)
        autoscaler = AutoScaleStrategy(config, dockerSvc, scheduler)
        autoscaler.start()
