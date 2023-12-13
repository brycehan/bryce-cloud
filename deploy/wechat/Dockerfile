FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre

LABEL org.opencontainers.image.authors="Bryce Han"
COPY ./bryce-cloud-wechat/target/bryce-cloud-wechat.jar /app.jar

WORKDIR /

# 设置时区
ENV TZ=Asia/Shanghai

EXPOSE 8092

ENTRYPOINT java \
	-server \
	-Dserver.port=8092 \
	-Dspring.profiles.active=${spring_profiles_active:-prod} \
	-Djava.security.egd=file:/dev/./urandom \
	-Dfile.encoding=UTF-8 \
	-Duser.timezone=GMT+08 \
	-XX:+HeapDumpOnOutOfMemoryError \
	-jar \
	/app.jar
