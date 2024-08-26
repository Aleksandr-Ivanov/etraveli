FROM mysql:latest

COPY scripts /docker-entrypoint-initdb.d/

ENV MYSQL_ROOT_PASSWORD=rootpass

ENV MYSQL_USER=card-clearing
ENV MYSQL_PASSWORD=card-clearing
ENV MYSQL_DATABASE=card_clearing_db

ENV PORT=3306
EXPOSE 3306