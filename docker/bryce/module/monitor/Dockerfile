# 基础镜像
FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre

# 作者
LABEL org.opencontainers.image.authors="Bryce Han"
# 复制jar
COPY ./jar/bryce-cloud-monitor-*.jar /app.jar
# 工作目录
WORKDIR /

# 设置时区
ENV TZ=Asia/Shanghai
# 暴露端口
EXPOSE 9014
# 启动System服务
ENTRYPOINT java \
	-server \
	-Dserver.port=9014 \
	-Dspring.profiles.active=prod \
	-Djava.security.egd=file:/dev/./urandom \
	-Dfile.encoding=UTF-8 \
	-Duser.timezone=${TZ} \
	-XX:+HeapDumpOnOutOfMemoryError \
	-jar \
	/app.jar
