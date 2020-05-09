ARG ENV
FROM registry.cn-hangzhou.aliyuncs.com/maiscrm/base-java:8u231-${ENV}

RUN mkdir -p /srv/stooltracker-backend/logs

COPY service/ /etc/service/
COPY stooltracker-backend.jar /srv/stooltracker-backend/stooltracker-backend.jar

EXPOSE 8084
