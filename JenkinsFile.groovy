pipeline {
    agent any
    stages {
        stage('[STEP_1] show Infos') {
            steps {
                //sh 'printenv' -- 쓸수 있는 전체 정보
                echo "search branch ID:: ${env.BUILD_ID}, Name:: ${env.GIT_BRANCH}"
            }
        }

        stage('[STEP_2] gradle build') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew build'
            }
        }

        stage ('Test 3: Master') {
            when { ${env.GIT_BRANCH} == 'origin/master' }
            steps {
                echo 'I only execute on the master branch.'
            }
        }

        stage ('Test 3: Dev') {
            when { not { branch 'master' } }
            steps {
                echo 'I execute on non-master branches.'
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