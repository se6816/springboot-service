#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)           # 현재 stop.sh가 속해있는 경로를 찾습니다.
source ${ABSDIR}/profile.sh          # 자바로 보면 import와 같습니다. profile.sh에 있는 여러 function을 사용할 수 있도록 가져옵니다.

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=springboot-service

echo ">Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행 권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."
nohup java -jar -Dspring.config.location=classpath:/application.yml,classpath:/application-$IDLE_PROFILE.yml,/home/ec2-user/app/application-oauth.yml,/home/ec2-user/app/application-datasource.yml $JAR_NAME > $REPOSITORY/nohub.out 2>&1 &
 
