pipeline {
    environment {
        registry = "mamohr/centos-java"
        registryCredential = 'docker'
    }
    agent any
    stages {
        stage('Build') {
            steps {
                echo "Build"
                sh 'chmod +x ./gradlew'
                sh('./gradlew build')
            }
        }

        stage('Build docker ps') {
            steps {
                    sh 'docker ps -a'
                }
        }
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