FROM java:8
VOLUME /tmp
ADD ./target/service-b.jar /app.jar
RUN /bin/bash -c 'touch /app.jar'
EXPOSE 8070
ENTRYPOINT ["java", "-jar", "/app.jar"]