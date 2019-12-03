pipeline {

    environment {
        registry = "mamohr/centos-java"
        registryCredential = 'docker'
    }
    agent none
    stages {
        stage('Build') {
            agent any
            steps {
                echo "Build"
                sh 'chmod +x ./gradlew'
                sh('./gradlew build')
            }
        }

        stage('dockerfile build') {
            agent any
            steps {
                sh 'docker build -t gradlet1 .'
                sh 'docker run -it -d gradlet1'
            }
        }

        /*
        stage('Build docker ps') {
            agent any
            steps {
                sh 'docker build -t gradleT1 ./'
                sh 'docker run -it -d gradleT1'
            }
        }

         */
    }

    //마지막 어떻게 할껀지
    post {
        always {
            echo 'build done!!!!!'
        }
        failure {
            echo 'build Fail!!!!!'
            //mail to: team@gmail.com, subject: 'Pipeline fail email'
        }
    }
}