FROM mamohr/centos-java
LABEL gradleT1
ADD ./build/libs/gradlebase-0.0.1-SNAPSHOT.jar /var/app.jar
ENTRYPOINT ["java", "-jar", "var/app.jar"]