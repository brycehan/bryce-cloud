# cloud
spring cloud microservices architecture project

# auth
1.密码模式获取 Token
```
curl -X POST -vu client:secret http://auth:8050/oauth/token -H "Accept: application/json" -d "username=admin&password=123456&grant_type=password"
```
返回如下格式数据：
```
{"access_token":"5ce01020-0665-4053-bdd8-0ac8af7372a5","token_type":"bearer","refresh_token":"be9c21eb-54d4-4c74-81e3-b049af76d737","expires_in":3571,"scope":"read write"}
```
2.使用 access token 访问 service a 接口
```
curl -i -H "Authorization: Bearer 5ce01020-0665-4053-bdd8-0ac8af7372a5" http://service-a:8060/
```
返回如下格式数据：
```
service service-a ( 192.168.56.1:8060 ) is OK.
```
3.使用 access token 访问 service b 接口
```
curl -i -H "Authorization: Bearer 5ce01020-0665-4053-bdd8-0ac8af7372a5" http://service-b:8070/
```
返回如下格式数据：
```
service service-b (192.168.56.1:8070) is OK.<br/>service service-a ( 192.168.56.1:8060 ) is OK.
```
4.使用 refresh token 刷新 token
```
curl -X POST -vu client:secret http://auth:8050/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=be9c21eb-54d4-4c74-81e3-b049af76d737"
```
返回如下格式数据：
```
{"access_token":"465beb52-11e6-421e-a9fc-e2715fd76b45","token_type":"bearer","refresh_token":"be9c21eb-54d4-4c74-81e3-b049af76d737","expires_in":3599,"scope":"read write"}
```
授权码模式
打开浏览器请求
http://auth:8050/oauth/authorize?client_id=auth&response_type=code
输入账号密码
浏览器跳转到
http://localhost:8060/login?code=ITDRNS
curl -X POST -vu auth:123456 -H "Accept: application/json" -d "grant_type=authorization_code&code=ITDRNS" "http://auth:8050/oauth/token"

{"access_token":"2d8b0d98-60c6-49ed-9933-975b4039a671","token_type":"bearer","expires_in":7199,"scope":"user"}
