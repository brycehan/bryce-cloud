# Docker 开启 Remote API 访问 2375 端口
1. 进入到/lib/systemd/system/docker.service
```
sudo vim /lib/systemd/system/docker.service
```
2. 找到 ExecStart 行，修改成下边这样
```
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock
```
保存退出
3. 执行下面命令使修改生效
```
sudo systemctl daemon-reload
sudo systemctl restart docker
```
4. 用浏览器访问查看是否成功
```
http://ip:2375/images/json
```
成功查看docker镜像列表，结束
# Docker 配置镜像加速器
1. 请在 /etc/docker/daemon.json 中写入如下内容（如果文件不存在请新建该文件）
```
{
    "registry-mirrors": [
        "https://hub-mirror.c.163.com",
        "https://mirror.baidubce.com"
    ]
}
```
注意，一定要保存该文件符合json规范。
2. 重新启动服务
```
sudo systemctl daemon-reload
sudo systemctl restart docker
```
配置结束
# 设置环境变量
1. 修改/etc/profile
```
sudo vim /etc/profile
```
```
export KEY=VALUE
```
2. 使修改生效
```
source /etc/profile
```
# 常用命令
```
sudo docker-compose up -d
sudo docker-compose logs serivce-a
sudo systemctl daemon-reload
sudo systemctl restart docker
```
# 系统安装后
```
apt update
apt upgrade
apt install chromium-browser
sudo apt install vim


```
# 启动 Zipkin 绑定 rabbitmq
docker run -d --restart=always -p 9411:9411 --name zipkin \
-e TZ='Asia/Shanghai' \
-e LANG="en_US.UTF-8" \
--link rabbitmq \
-e RABBIT_ADDRESSES=rabbitmq:5672 \
-e RABBIT_USER=admin \
-e RABBIT_PASSWORD=123456 \
openzipkin/zipkin