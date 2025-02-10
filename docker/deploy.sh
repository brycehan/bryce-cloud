#!/bin/bash

# 启动脚本
case $1 in

"copy") {
  echo "-------------------- 复制 bryce-cloud sql --------------------"
  cp ../db/mysql/* ./mysql/db

  # 复制 jar包
  echo "-------------------- 复制 bryce-cloud-gateway jar包 --------------------"
  cp ../bryce-cloud-gateway/target/bryce-cloud-gateway-*.jar ./bryce/gateway/jar

  echo "-------------------- 复制 bryce-cloud-auth jar包 --------------------"
  cp ../bryce-cloud-auth/target/bryce-cloud-auth-*.jar ./bryce/auth/jar

  echo "-------------------- 复制 bryce-cloud-system jar包 --------------------"
  cp ../bryce-cloud-module/bryce-cloud-system/target/bryce-cloud-system-*.jar ./bryce/module/system/jar

  echo "-------------------- 复制 bryce-cloud-storage jar包 --------------------"
  cp ../bryce-cloud-module/bryce-cloud-storage/target/bryce-cloud-storage-*.jar ./bryce/module/storage/jar

  echo "-------------------- 复制 bryce-cloud-email jar包 --------------------"
    cp ../bryce-cloud-module/bryce-cloud-email/target/bryce-cloud-email-*.jar ./bryce/module/email/jar

  echo "-------------------- 复制 bryce-cloud-sms jar包 --------------------"
  cp ../bryce-cloud-module/bryce-cloud-sms/target/bryce-cloud-sms-*.jar ./bryce/module/sms/jar

  echo "-------------------- 复制 bryce-cloud-monitor jar包 --------------------"
  cp ../bryce-cloud-module/bryce-cloud-monitor/target/bryce-cloud-monitor-*.jar ./bryce/module/monitor/jar

};;

"remove") {
  echo "-------------------- 删除 bryce-cloud sql --------------------"
  rm -f ./mysql/db/*.sql
  echo "-------------------- 删除 bryce-cloud jar包 --------------------"
  rm -f ./bryce/gateway/jar/*.jar
  rm -f ./bryce/auth/jar/*.jar
  rm -f ./bryce/module/*/jar/*.jar
};;

"port") {
  echo "-------------------- 开启 bryce-cloud 端口 --------------------"
  firewall-cmd --zone=public --add-port=3306/tcp --permanent
  firewall-cmd --zone=public --add-port=6379/tcp --permanent
  firewall-cmd --zone=public --add-port=8848/tcp --permanent
  firewall-cmd --zone=public --add-port=9848/tcp --permanent
  firewall-cmd --zone=public --add-port=9849/tcp --permanent
  firewall-cmd --zone=public --add-port=8090/tcp --permanent
  firewall-cmd --zone=public --add-port=9011/tcp --permanent
  firewall-cmd --zone=public --add-port=9012/tcp --permanent
  firewall-cmd --zone=public --add-port=9013/tcp --permanent
  firewall-cmd --zone=public --add-port=9014/tcp --permanent
  firewall-cmd --zone=public --add-port=9015/tcp --permanent
  firewall-cmd --zone=public --add-port=9016/tcp --permanent
  # 配置立即生效
  firewall-cmd --reload
};;

"base") {
  echo "-------------------- 启动 bryce-cloud 基础环境 --------------------"
  docker compose up -d cloud-mysql cloud-redis cloud-nacos cloud-grafana cloud-loki cloud-zipkin cloud-sentinel-dashboard cloud-xxl-job-admin cloud-rabbitmq
};;

"module") {
   echo "-------------------- 启动 bryce-cloud 程序模块 --------------------"
    docker compose up -d cloud-gateway cloud-auth cloud-module-system cloud-module-storage cloud-module-monitor
};;

"stop") {
   echo "-------------------- 关闭 bryce-cloud 环境、模块 --------------------"
   docker compose stop
};;

"rm") {
   echo "-------------------- 删除 bryce-cloud 环境、模块 --------------------"
   docker compose rm
};;

"ls") {
   echo "-------------------- 查看 bryce-cloud 环境、模块状态 --------------------"
   docker compose ls
};;

"help") {
  echo "Usage: sh deploy.sh [ port｜copy | remove | base | module | stop | rm | status ]"
};;

esac