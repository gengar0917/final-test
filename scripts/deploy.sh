#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/finalproject12Be
cd $REPOSITORY

APP_NAME=finalproject12Be
JAR_NAME=finalproject12Be-0.0.1-SNAPSHOT.jar
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 애플리케이션이 없습니다."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Deploy - $JAR_PATH"
nohup java -jar $JAR_PATH --spring.profiles.active=prod > /dev/null 2> /dev/null < /dev/null &