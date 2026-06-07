#!/bin/bash
set -a
source ~/.env
set +a

pkill -f 'app.jar' || true
sleep 2

nohup java -javaagent:~/newrelic.jar -jar ~/app.jar > ~/app.log 2>&1 < /dev/null &
disown $!