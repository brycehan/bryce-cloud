FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre

LABEL org.opencontainers.image.authors="Bryce Han"
COPY ./bryce-cloud-system/target/bryce-cloud-system.jar /app.jar

WORKDIR /

# 设置时区
ENV TZ=Asia/Shanghai

EXPOSE 8091

ENTRYPOINT java \
	-server \
	-Dserver.port=8091 \
	-Dspring.profiles.active=${spring_profiles_active:-prod} \
	-Djava.security.egd=file:/dev/./urandom \
	-Dfile.encoding=UTF-8 \
	-Duser.timezone=${TZ} \
	-XX:+HeapDumpOnOutOfMemoryError \
	-jar \
	/app.jar
