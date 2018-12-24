FROM java:latest
VOLUME /tmp
ADD /target/system-monitor-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar \
            && pwd'
EXPOSE 8080
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,address=8888,suspend=n,server=y", "/app.jar"]