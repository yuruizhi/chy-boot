FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="YuRuizhi <282373647.com>"

# 安装字体工具及设置时区为京八区
RUN apk update && apk add --update curl bash htop tzdata \
    && cp -r -f /usr/share/zoneinfo/Hongkong /etc/localtime \
    && rm -rf /var/cache/apk/* \
    && mkdir -p /data/logs/jvm

# 添加非root用户
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ADD chy-boot-rest-api/target/chy-boot-rest-api-1.0-SNAPSHOT.jar /app.jar

ENV PORT 8084
ENV JAVA_OPTS="-XX:+UseZGC -XX:+ZGenerational -XX:MaxRAMPercentage=75.0"
HEALTHCHECK --interval=120s --timeout=300s CMD curl http://localhost:$PORT/actuator/health || exit 1
ENTRYPOINT java ${JAVA_OPTS} --enable-preview -Duser.timezone=GMT+08 -Djava.security.egd=file:/dev/./urandom -jar /app.jar