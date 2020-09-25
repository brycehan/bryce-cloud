FROM java:8
VOLUME /tmp
ADD ./target/gateway.jar /app.jar
RUN /bin/bash -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]