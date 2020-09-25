FROM java:8
VOLUME /tmp
ADD ./target/monitor.jar /app.jar
RUN /bin/bash -c 'touch /app.jar'
EXPOSE 8040
ENTRYPOINT ["java", "-jar", "/app.jar"]