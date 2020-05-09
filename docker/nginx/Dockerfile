ARG ENV
FROM registry.cn-hangzhou.aliyuncs.com/maiscrm/base-openresty:1.13.6.2-${ENV}

COPY nginx/conf/stooltracker-backend.conf /etc/nginx/sites-enabled/stooltracker-backend.conf

EXPOSE 80
