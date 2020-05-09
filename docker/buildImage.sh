#!/bin/bash -e

export ENV=${ENV:-"staging"}
export ENV_VERSION=${ENV}-$(date "+%Y%m%d%H%M")
IMAGE_NAME=${IMAGE_NAME:-"registry.cn-hangzhou.aliyuncs.com/maiscrm/chyri-stooltracker-backend"}

buildImage() {
  imageName=${IMAGE_NAME}$1
  dockerFile=$2
  docker build \
    --build-arg ENV=${ENV} \
    -t ${imageName}:${ENV} -t ${imageName}:${ENV_VERSION} \
    -f ${dockerFile} .
  docker push ${imageName}:${ENV}
  docker push ${imageName}:${ENV_VERSION}
}

./build.sh

buildImage '' Dockerfile
buildImage '-nginx' nginx/Dockerfile

latestServiceImageTag set stooltracker-backend
