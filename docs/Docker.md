# Nexus 容器 管理Docker, Maven, Yum, PyPI
```
https://yeasy.gitbook.io/docker_practice/repository/nexus3_registry

docker run -d --name nexus3 --restart=always \
-p 8081:8081 \
-p 8082:8082 \
-p 8083:8083 \
-p 8084:8084 \
-p 8085:8085 \
-p 8086:8086 \
-p 8087:8087 \
--mount src=nexus-data,target=/nexus-data \
sonatype/nexus3
```
# MySQL 安装
```
docker pull mysql
docker run \
    -d \
    -p 3306:3306 \
    -e MYSQL_ROOT_PASSWORD=123456 \
    -v /home/data/mysql/data:/var/lib/mysql:rw \
    -v /home/data/mysql/log:/var/log/mysql:rw \
    -v /etc/localtime:/etc/localtime:ro \
    --name mysql8 \
    --restart=always \
    mysql
```
# RabbitMQ安装
1.查找镜像
```
sudo docker search rabbitmq
```
2.拉取镜像
```
sudo docker pull rabbitmq:management
```
3.启动镜像
```
sudo docker run -d -p 15672:15672 -p 5672:5672 --hostname rabbitmq --name rabbitmq \
-e RABBITMQ_DEFAULT_USER=admin \
-e RABBITMQ_DEFAULT_PASS=123456 \
rabbitmq:management
```
4.访问控制台 http://localhost:15672/
5.输入账号密码并登录 admin 123456
6.创建账号并设置角色为管理员 brycehan 123456
7.创建一个新的虚拟 host 为 brycehan
8.点击 brycehan 用户进入用户配置页面
9.给 brycehan 用户配置该虚拟 host 的权限
10.到此， RabbitMQ的安装和配置完成
# Rancher Install 
```
# Rancher Install 
sudo docker pull rancher/rancher:stable
sudo mkdir -p /opt/data_volume/rancher/rancher
sudo mkdir -p /opt/data_volume/rancher/auditlog
sudo docker run -d --restart=unless-stopped -p 80:80 -p 443:443 \
--name rancher \
-v /opt/data_volume/rancher/rancher:/var/lib/rancher \
-v /opt/data_volume/rancher/auditlog:/var/log/auditlog \
rancher/rancher:stable
```