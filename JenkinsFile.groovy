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

        stage('dockerfile add jar') {
            agent any
            steps {
                sh 'docker build -t gradlet2 .'
                sh 'docker run -d -p 8809:8809 gradlet2'
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