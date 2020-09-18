# Ubuntu环境下安装
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