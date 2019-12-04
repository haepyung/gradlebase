pipeline {
    agent any
    stages {
        stage('[STEP_1] show Infos') {
            steps {
                //sh 'printenv' -- 쓸수 있는 전체 정보
                echo 'search branch Name:: '${env.GIT_BRANCH}
            }
        }

        stage('[STEP_2] gradle build') {
            steps {
                echo "Build"
                sh 'chmod +x ./gradlew'
                sh('./gradlew build')
            }
        }

        stage('[STEP_3] dockerfile build') {
            steps {
                sh 'docker stop gradlebuild || true && docker rm gradlebuild || true'
                sh 'docker rmi gradletsource || true'
                sh 'docker build -t gradletsource .'
                sh 'docker run --name gradlebuild -d -p 8888:8888 gradletsource'
            }
        }
    }

    post {
        always {
            echo 'build done!!!!!'
        }
        failure {
            echo 'build Fail!!!!!'
            // mail to: team@gmail.com, subject: 'Pipeline fail email'
        }
    }
}