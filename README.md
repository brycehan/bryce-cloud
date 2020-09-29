# cloud
本项目是一个基于Spring Boot、Spring Cloud等框架构建的微服务项目。

# 应用架构
该项目包含 7 个服务
* discovery - 服务注册与发现
* config - 配置中心
* gateway - 网关
* monitor - 服务监控
* auth - OAuth2 认证
* service-a - 业务服务 A
* service-b - 业务服务 B

# 启动项目
* 使用 Docker 快速启动
    1. 配置 Docker 环境
    2. mvn clean package -DskipTests 打包项目和 Docker 镜像
    3. 在项目根目录下执行 docker-compose up -d 启动所有项目
* 本地手动启动
    1. 配置rabbitmq
    2. 修改 hosts 将主机名指向本地
    127.0.0.1 discovery config rabbitmq gateway monitor auth service-a service-b
    或者修改各服务配置文件中的相应主机名为本地IP
    3. 启动 discovery、config、monitor
    4. 启动 gateway、auth、service-a、service-b

# 接口测试
1. 密码模式获取 Token
```
curl -X POST -vu client:secret http://auth:8050/oauth/token -H "Accept: application/json" -d "username=admin&password=123456&grant_type=password"
```
返回如下格式数据：
```
{"access_token":"5ce01020-0665-4053-bdd8-0ac8af7372a5","token_type":"bearer","refresh_token":"be9c21eb-54d4-4c74-81e3-b049af76d737","expires_in":3571,"scope":"read write"}
```
2. 使用 access token 访问 service a 接口
```
curl -i -H "Authorization: Bearer 5ce01020-0665-4053-bdd8-0ac8af7372a5" http://service-a:8060/
```
返回如下格式数据：
```
service service-a ( 192.168.56.1:8060 ) is OK.
```
3. 使用 access token 访问 service b 接口
```
curl -i -H "Authorization: Bearer 5ce01020-0665-4053-bdd8-0ac8af7372a5" http://service-b:8070/
```
返回如下格式数据：
```
service service-b (192.168.56.1:8070) is OK.<br/>service service-a ( 192.168.56.1:8060 ) is OK.
```
4. 使用 refresh token 刷新 token
```
curl -X POST -vu client:secret http://auth:8050/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=be9c21eb-54d4-4c74-81e3-b049af76d737"
```
返回如下格式数据：
```
{"access_token":"465beb52-11e6-421e-a9fc-e2715fd76b45","token_type":"bearer","refresh_token":"be9c21eb-54d4-4c74-81e3-b049af76d737","expires_in":3599,"scope":"read write"}
```
5. 刷新配置
```
curl -X POST -vu user:password http://localhost:8888/actuator/bus-refresh
```
