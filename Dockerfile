FROM mamohr/centos-java
ADD ./build/libs/gradlebase-0.0.1-SNAPSHOT.jar /var/app.jar
ENV JAVA_OPTS=
ENTRYPOINT ["java", "-jar", "/var/app.jar"]