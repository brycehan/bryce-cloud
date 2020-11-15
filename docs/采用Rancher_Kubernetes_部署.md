# 部署环境
vm，机器上装3台 minimal 版的 CentOS 8.2
# 网络和软件准备
1. 三台 CentOS 配置静态 IP，根据实际配置即可
```
ip addr
vi /etc/sysconfig/network-scripts/ifcfg-enp0s3
```
如下配置
```
BOOTPROTO=static
IPADDR=192.168.0.103
GATEWAY=192.168.0.1
NETMASK=255.255.255.0
DNS1=192.168.1.1
```
配置后，重启网络
```
nmcli c reload
```
2. 三台 CentOS 执行如下脚本 k8s-initial.sh
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

FOE
```

# 修改 host
1. 主机名和 ip 根据自己情况设置即可
```
vi /etc/hosts
```
添加
```
192.168.0.103 rancher-server
192.168.0.104 k8s-node-01
192.168.0.105 k8s-node-02
```
2. 修改 hostname
```
hostnamectl set-hostname rancher-server
```
3. 查看 hostname
```
hostname
```
# Rancher 安装
1. 在 rancher-server 上安装 rancher
```
sudo docker pull rancher/rancher:v2.4.8
sudo mkdir -p /opt/data_volume/rancher/rancher
sudo mkdir -p /opt/data_volume/rancher/auditlog
sudo docker run -d --restart=unless-stopped -p 80:80 -p 443:443 \
--name rancher \
-v /opt/data_volume/rancher/rancher:/var/lib/rancher \
-v /opt/data_volume/rancher/auditlog:/var/log/auditlog \
rancher/rancher:v2.4.8
```
2. 访问 rancher-server 的 https 进入页面配置
设置中文
配置密码
添加集群
添加节点
### 注意：
1. 将集群的 kube-proxy 设置为 ipvs
2. 添加 k8s 节点，集群只有一个节点时，需要将 Etcd、Control、Worker都选中，在对应机器上执行即可
3. rancher docker tag 有 stable, latest

# 安装 kubectl
为了方便，再在 rancher-server 节点上安装一个 kubectl
```

cat > /etc/yum.repos.d/kubernetes.repo << EOF
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF

yum install -y kubectl
```
再将 rancher 界面中 k8s集群首页的 kubeconfig 复制到 kubectl 的主机上
```
sudo mkdir -p ~/.kube
sudo vim ~/.kube/config
```
然后运行下 kubectl 验证下
```
kubectl get namespaces
```
# cloud 项目部署
1. 安装 rabbitmq
```
#---- 定义 RabbitMQ 部署 ----
apiVersion: apps/v1
kind: Deployment
metadata:
  name: rabbit
spec:
  replicas: 1
  selector:
    matchLabels:
      app: rabbit
  strategy:
    rollingUpdate:
      maxSurge: 25%
      maxUnavailable: 25%
    type: RollingUpdate
  template:
    metadata:
      labels:
        app: rabbit
    spec:
      containers:
      - image: /rabbitmq:latest
        imagePullPolicy: IfNotPresent
        name: rabbit
        ports:
        - containerPort: 15672
          name: rabbit15672
          protocol: TCP        
        - containerPort: 5672
          name: rabbit5672
          protocol: TCP            
---
#---- 定义 rabbit 的代理服务，serviceName 一定要和代码中的一致 ----
apiVersion: v1
kind: Service
metadata:
  name: rabbitmq
spec:
  ports:
  - name: rabbit35672
    nodePort: 35672
    port: 15672
    protocol: TCP
    targetPort: 15672
  - name: rabbit45672
    nodePort: 45672
    port: 5672
    protocol: TCP
    targetPort: 5672   
  selector:
    app: rabbit
  type: NodePort                     
```
同时为了测试方便，再暴露出nodePort 端口出来，这样 k8s 外部环境就可以访问了

待执行完上面的 yaml 安装后，访问 35672 就可以访问 rabbitmq 的后台管理页面了user/bitnami 为默认的用户名和密码

2. maven 打包, 找到项目 pom.xml 所在的根目录，执行如下命令， push 到 maven plugin 的 docker Host (开启 Docker Remote API) 上
```
mvn package -Dmaven.test.skip=true 
```
3. 登录docker Host 的 终端，再登录私有 registry , push 项目镜像到 docker 的私有registry
```
docker login registry.brycehan.com:5000
```
```
docker tag cloud/discovery registry.brycehan.com:5000/cloud/discovery
docker tag cloud/config registry.brycehan.com:5000/cloud/config
docker tag cloud/gateway registry.brycehan.com:5000/cloud/gateway
docker tag cloud/monitor registry.brycehan.com:5000/cloud/monitor
docker tag cloud/auth registry.brycehan.com:5000/cloud/auth
docker tag cloud/service-a registry.brycehan.com:5000/cloud/service-a
docker tag cloud/service-b registry.brycehan.com:5000/cloud/service-b
docker push registry.brycehan.com:5000/cloud/discovery
docker push registry.brycehan.com:5000/cloud/config
docker push registry.brycehan.com:5000/cloud/gateway
docker push registry.brycehan.com:5000/cloud/monitor
docker push registry.brycehan.com:5000/cloud/auth
docker push registry.brycehan.com:5000/cloud/service-a
docker push registry.brycehan.com:5000/cloud/service-b
```
4. push 成功后，可以通过如下命令查看
```
https://registry.brycehan.com:5000/v2/_catalog
```
5. push 失败时（http: server gave HTTP response to HTTPS client）, 在 /etc/docker/daemon.json 新添加
```
{
  "insecure-registries": [
    "192.168.0.103:8082"
  ]
} 
```
