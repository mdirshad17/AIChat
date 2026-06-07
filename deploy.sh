#!/bin/bash
export $(grep -v '^#' ~/.env | xargs)

pkill -f 'app.jar' || true
sleep 2

nohup java \
  -javaagent:/home/ubuntu/newrelic.jar \
  -Dnewrelic.config.license_key=$NEW_RELIC_KEY \
  -Dnewrelic.config.app_name=AI_CHATBOT \
  -jar /home/ubuntu/app.jar > /home/ubuntu/app.log 2>&1 < /dev/null &
disown $!