FROM mamohr/centos-java
ADD ./build/libs/gradlebase-0.0.1-SNAPSHOT.jar /var/app.jar
CMD [docker build -tag gradleT1 ./]
CMD [docker run -it -d gradleT1]

