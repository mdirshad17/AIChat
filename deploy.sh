#!/bin/bash
set -a
source ~/.env
set +a

pkill -f 'app.jar' || true
sleep 2

nohup java -javaagent:/home/ubuntu/newrelic.jar -jar /home/ubuntu/app.jar > /home/ubuntu/app.log 2>&1 < /dev/null &
disown $!