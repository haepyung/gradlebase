FROM mamohr/centos-java
USER root
ADD ./build/libs/gradlebase-0.0.1-SNAPSHOT.jar /app.jar
USER Jenkins