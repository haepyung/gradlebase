FROM mamohr/centos-java
ADD /var/jenkins_home/workspace/test/gradlebase-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]