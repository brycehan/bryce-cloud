# 基础镜像
FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre

# 镜像标签
LABEL org.opencontainers.image.authors="Bryce Han"
# 复制文件
COPY ./jar/bryce-cloud-auth-*.jar /app.jar
# 设置工作目录
WORKDIR /

# 设置时区
ENV TZ=Asia/Shanghai
# 暴露端口
EXPOSE 9011
# 启动认证服务
ENTRYPOINT java \
	-server \
	-Dserver.port=9011 \
	-Dspring.profiles.active=prod \
	-Djava.security.egd=file:/dev/./urandom \
	-Dfile.encoding=UTF-8 \
	-Duser.timezone=${TZ} \
	-XX:+HeapDumpOnOutOfMemoryError \
	-jar \
	/app.jar
