pipeline {
    agent any
    environment {
        branch_name = "${env.GIT_BRANCH}"
    }
    stages {
        stage('[STEP_1] show Infos') {
            steps {
                //sh 'printenv' -- 쓸수 있는 전체 정보
                echo "search branch ID:: ${env.GIT_BRANCH}, Name:: ${branch_name}"
            }
        }

        stage('[STEP_2] gradle build') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew build'
            }
        }

        stage ('Test 3: Master') {
            when { branch 'master' }
            steps {
                echo 'I execute on master branches.'
            }
        }

        stage ('Test 3: Dev') {
            when { not {branch 'deb'} }
            steps {
                echo 'no have deb branch!!'
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