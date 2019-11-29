pipeline {
    agent any
    stages {
        stage('START') {
            steps {
                echo 'Hello!! World~!!!!'
            }
        }

        stage('Git Pull') {
            steps {
                echo "Git Pull"
                git branch: 'master',
                        credentialsId: 'Jenkins',
                        url: 'https://github.com/haepyung/gradlebase.git'
            }
        }
        stage('Build') {
            steps {
                echo "Build"
                sh 'chmod +x ./gradlew'
                sh('./gradlew build')
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