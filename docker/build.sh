#!/bin/bash -e

WORKESPACE=$(cd "$(dirname "$0")"; pwd)/../
cd ${WORKESPACE}

command="cd /app; mvn clean package -P ${PROFILE} -Dmaven.test.skip=true"
docker run \
  -i \
  -v $(pwd):/app -v /root/.m2:/root/.m2 \
  registry.cn-hangzhou.aliyuncs.com/maiscrm/tool-maven-builder:3.1.1-${ENV} \
  sh -c "${command}"

cp ${WORKESPACE}/chy-boot-api/target/chy-boot-api-1.0-SNAPSHOT.jar ${WORKESPACE}/docker/stooltracker-backend.jar
