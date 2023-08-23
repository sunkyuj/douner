#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/app
DEPLOY_LOG=$REPOSITORY/deploy.log

echo "> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fla java | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

TIME_NOW=$(date +%c)

echo "> 새 애플리케이션 배포" >> $DEPLOY_LOG

echo "> 환경 변수 불러오기" >> $DEPLOY_LOG
#source /home/ubuntu/.bashrc >> $DEPLOY_LOG
export DB_PASSWORD=$(aws ssm get-parameters --region ap-northeast-2 --names DB_PASSWORD --query Parameters[0].Value | sed 's/"//g')
export DB_URL=$(aws ssm get-parameters --region ap-northeast-2 --names DB_URL --query Parameters[0].Value | sed 's/"//g')
export DB_USERNAME=$(aws ssm get-parameters --region ap-northeast-2 --names DB_USERNAME --query Parameters[0].Value | sed 's/"//g')
export JWT_SECRET_KEY=$(aws ssm get-parameters --region ap-northeast-2 --names JWT_SECRET_KEY --query Parameters[0].Value | sed 's/"//g')
export TOKEN_HEADER_PREFIX=$(aws ssm get-parameters --region ap-northeast-2 --names TOKEN_HEADER_PREFIX --query Parameters[0].Value | sed 's/"//g')


JAR_NAME=$(ls -tr $REPOSITORY/*SNAPSHOT.jar | tail -n 1) >> $DEPLOY_LOG

#JAR_NAME="/home/ubuntu/app/douner-0.0.1-SNAPSHOT.jar"

echo "> JAR NAME: $JAR_NAME" >> $DEPLOY_LOG

echo "> $JAR_NAME 에 실행권한 추가" >> $DEPLOY_LOG

chmod +x $JAR_NAME >> $DEPLOY_LOG

echo "> $JAR_NAME 실행" >> $DEPLOY_LOG

nohup java -jar -Duser.timezone=Asia/Seoul -D $JAR_NAME >> $REPOSITORY/nohup.out 2>&1 &

CURRENT_PID=$(pgrep -f $JAR_NAME)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG