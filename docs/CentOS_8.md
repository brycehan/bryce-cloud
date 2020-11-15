# 开启 ipvs
```
sudo dnf -y install ipvsadm ipset
sudo cat > /etc/sysconfig/modules/ipvs.modules << EOF
modprobe -- ip_vs
modprobe -- ip_vs_rr
modprobe -- ip_vs_wrr
modprobe -- ip_vs_sh
modprobe -- nf_conntrack_ipv4
EOF

sudo chmod 755 /etc/sysconfig/modules/ipvs.modules
sudo bash /etc/sysconfig/modules/ipvs.modules
sudo lsmod | grep ip_vs
```
# 修改 hostname
```
hostnamectl set-hostname rancher-server
hostname
```
# ipvs 安装
```
#!/bin/bash

echo "Start"

sudo su - << FOE

# Stop firewall and selinux
sudo systemctl disable --now firewalld
sudo /usr/sbin/setenforce 0
sudo sed -i 's/SELINUX=enforcing/SELINUX=permissive/g' /etc/selinux/config

# Config iptables
echo "br_netfilter" > /etc/modules-load.d/br_netfilter.conf
cat > /etc/sysctl.d/k8s.cnf << EOF
net.ipv4.ip_forward = 1
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-ip6tables = 1
EOF

sudo modprobe br_netfilter
sysctl --system

# Open ipvs
sudo dnf -y install ipvsadm ipset
sudo cat > /etc/sysconfig/modules/ipvs.modules << EOF
modprobe -- ip_vs
modprobe -- ip_vs_rr
modprobe -- ip_vs_wrr
modprobe -- ip_vs_sh
modprobe -- nf_conntrack_ipv4
EOF

sudo chmod 755 /etc/sysconfig/modules/ipvs.modules
sudo bash /etc/sysconfig/modules/ipvs.modules
sudo lsmod | grep -e ip_vs -e nf_conntrack_ipv4

FOE
```
# Docker 安装
```
# Add Docker Repo
sudo dnf config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
# Install Docker-CE
sudo dnf makecache timer
sudo dnf -y install --nobest docker-ce
# Enable Docker
sudo systemctl enable --now docker
# Config Docker
sudo mkdir -p /etc/docker
sudo cat > /etc/docker/daemon.json << EOF
{
  "registry-mirrors": [
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com"
  ]
}

EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```
# Rancher docker Install 
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
# Podman 配置 mirror
1. 在 ~/.config/containers 下面新建 registries.conf
```
unqualified-search-registries = ["docker.io"]

[[registry]]
prefix = "docker.io"
location = "mirror.baidubce.com"
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
# 常用命令
```
systemctl status NetworkManager
ip addr
rm -fr rancher/
docker rm -f b05be3312383
```
1. 进入容器内部, 2fc48be79267 为 Container ID
```
 docker exec -it 2fc48be79267 /bin/bash
```
2. 重启网卡
```
nmcli c reload
```
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
# Docker Registry
1. 生成认证
```
mkdir ~/certs
openssl req -newkey rsa:4096 -nodes -sha256 -keyout /root/certs/domain.key  -x509 -days 3650 -out /root/certs/domain.crt
```
2. 复制认证到 docker
```
mkdir -p /etc/docker/certs.d/registry.brycehan.com:5000
cp /root/certs/domain.crt  /etc/docker/certs.d/registry.brycehan.com\:5000/ca.crt
```
3. 复制认证到本机，使操作系统信任我们的自签名证书
```
cat /root/certs/domain.crt >> /etc/pki/tls/certs/ca-bundle.crt
```
4. 启动仓库镜像
```
docker run -d -p 5000:5000 \
--privileged=true \
-v /root/docker/registry:/var/lib/registry \
-v /root/certs/:/root/certs \ 
-e REGISTRY_HTTP_TLS_CERTIFICATE=/root/certs/domain.crt \
-e REGISTRY_HTTP_TLS_KEY=/root/certs/domain.key \
registry:2.7.0
```
5. 修改当前host
```
vi /etc/hosts
```
```
192.168.0.103 registry.brycehan.com
```
6. 添加 http basic authentication (高版本需要yum install -y apache2-utils)
```
mkdir /root/auth
docker run --entrypoint htpasswd  registry:2.7.0 -Bbn testuser testpassword > /root/auth/htpasswd
```
7. 启动 http basic authentication 仓库
```
docker run -d -p 5000:5000 \
--restart=always \
--privileged=true \
--name registry \
-v /root/docker/registry:/var/lib/registry \
-e "REGISTRY_AUTH=htpasswd" \
-e "REGISTRY_AUTH_HTPASSWD_REALM=Registry Realm" \
-v /root/auth:/root/auth \
-e "REGISTRY_AUTH_HTPASSWD_PATH=/root/auth/htpasswd" \
-v /root/certs/:/root/certs \
-e REGISTRY_HTTP_TLS_CERTIFICATE=/root/certs/domain.crt \
-e REGISTRY_HTTP_TLS_KEY=/root/certs/domain.key \
registry:2.7.0
```
8. 登录仓库
```
 docker login localhost:5000
```
9. 其他服务器用这个私有仓库，直接复制 docker 的仓库服务器下的 /etc/docker/cert.d/ 下的 registry.brycehan.com:5000/ca.cert 目录和文件，到自己的 /etc/docker/cert.d/ 目录下即可。
# 证书格式转换
```
openssl x509 -in domain.crt -out domain.pem
```
