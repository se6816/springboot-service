#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)           # 현재 stop.sh가 속해있는 경로를 찾습니다.
source ${ABSDIR}/profile.sh          # 자바로 보면 import와 같습니다. profile.sh에 있는 여러 function을 사용할 수 있도록 가져옵니다.

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT에서 구동 중인 애플리케이션 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if[ -z ${IDLE_PID}]
then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> KILL -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi
 
