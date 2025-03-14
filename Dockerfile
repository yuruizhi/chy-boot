FROM ccr.ccs.tencentyun.com/changyi_docker/oracle-java:8_server-jre_unlimited
LABEL maintainer="Henry.Yu <yuruizhi@vchangyi.com>"

# 安装字体工具及设置时区为京八区
RUN apk update && apk add --update curl bash htop tzdata \
    && cp -r -f /usr/share/zoneinfo/Hongkong /etc/localtime \
    && rm -rf /var/cache/apk/* \
    && mkdir -p /data/logs/jvm

ADD chy-boot-api/target/chy-boot-api-1.0-SNAPSHOT.jar /app.jar

ENV PORT 8084
HEALTHCHECK --interval=120s --timeout=300s CMD curl http://localhost:$PORT/actuator/health || exit 1
ENTRYPOINT java ${JAVA_OPTS} -Duser.timezone=GMT+08 -Djava.security.egd=file:/dev/./urandom -jar /app.jar