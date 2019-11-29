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

        stage('Build docker image') {
            steps {
                withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                    sh 'docker build -t $registry'
                }
            }
        }

        stage('Clean docker image') {
            steps{
                sh "docker rmi $registry"
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

pipeline {
    /* insert Declarative Pipeline here */
}