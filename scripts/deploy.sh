#!/bin/bash

source ./var.sh
echo "===== $MYSQL_DATABASE_URL ====="
REPOSITORY=/home/ec2-user/E2E1-KenelEngine
JAR_REPOSITORY=$REPOSITORY/build/libs
BATCH_JAR_REPOSITORY=$REPOSITORY/module-batch/build/libs/

echo "======= Move to REPOSITORY ======"
cd $REPOSITORY

echo "======= Grant gradlew ======"
chmod +x ./gradlew

echo "===== Build Gradle ====="
./gradlew build --x test


echo "===== 현재 구동중인 애플리케이션의 PID 확인 ====="CURRENT_PID=$(pgrep -f SNAPSHOT.jar)

echo "===== 현재 구동중인 애플리케이션의 PID: $CURRENT_PID ====="
if [ -z "$CURRENT_PID" ];
then
  echo "===== 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다 ====="else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "===== 새 애플리케이션 배포 ====="
JAR_NAME=$(ls -tr $JAR_REPOSITORY/ | grep SNAPSHOT.jar | tail -n 1)
BATCH_JAR_NAME=$(ls -tr $BATCH_JAR_REPOSITORY/ | grep SNAPSHOT.jar | tail -n 1)

echo "===== GRANT JAR Name: $JAR_NAME ====="echo "===== GRANT BATCH_JAR_NAME: $BATCH_JAR_NAME ====="chmod +x $JAR_REPOSITORY/$JAR_NAME
chmod +x $BATCH_JAR_REPOSITORY/$BATCH_JAR_NAME

echo "===== $JAR_NAME 실행 ====="nohup java -jar -Dspring.config.location=$REPOSITORY/src/main/resources/application.yml \
        $JAR_REPOSITORY/$JAR_NAME 1>log.out 2>err.out &

echo "===== $BATCH_JAR_NAME 실행 ====="nohup java -jar -Dspring.config.location=$REPOSITORY/module-batch/src/main/resources/application.yml \
        $BATCH_JAR_REPOSITORY/$BATCH_JAR_NAME 1>batch_log.out 2>batch_err.out &