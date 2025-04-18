# 基础镜像
FROM public.ecr.aws/docker/library/eclipse-temurin:21-jre

# 镜像标签
LABEL org.opencontainers.image.authors="Bryce Han"
# 拷贝jar包
COPY ./jar/bryce-cloud-gateway-*.jar /app.jar
# 设置工作目录
WORKDIR /

# 设置时区
ENV TZ=Asia/Shanghai
# 暴露端口
EXPOSE 8090
# 启动容器
ENTRYPOINT java \
	-server \
	-Dserver.port=8090 \
	-Dspring.profiles.active=prod \
	-Djava.security.egd=file:/dev/./urandom \
	-Dfile.encoding=UTF-8 \
	-Duser.timezone=${TZ} \
	-XX:+HeapDumpOnOutOfMemoryError \
	-jar \
	/app.jar
