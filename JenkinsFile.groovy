pipeline {
    agent any
    stages {
        stage('START') {
            steps {
                echo 'Hello!! World~!!!!'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x ./gradlew build'
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


pipeline {
    /* insert Declarative Pipeline here */
}