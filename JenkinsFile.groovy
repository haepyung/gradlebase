pipeline {
    agent any
    environment {
        slack_baseurl = "https://haesoonee.slack.com/services/hooks/jenkins-ci/"
        slack_channel = "jenkins"
        slack_token = "l50yPrWd1BLCEN1tfsxZTyc7"
        branch_name = "${env.NODE_NAME}"
    }
    stages {
        stage('STEP_1:: show Infos') {
            steps {
                //sh 'printenv' -- 쓸수 있는 전체 정보
                echo "search branch ID:: ${env.GIT_BRANCH}, Name:: ${branch_name}"
            }
        }

        stage('STEP_2:: gradle build') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew build'
            }
        }

        stage('STEP_3:: dockerfile build') {
            steps {
                sh "docker stop gradlebuild_${branch_name}|| true && docker rm gradlebuild_${branch_name} || true"
                sh "docker rmi gradletsource_${branch_name} || true"
                sh "docker build -t gradletsource_${branch_name} ."
                sh "docker run --name gradlebuild_${branch_name} -d -p 8888:8888 gradletsource_${branch_name}"
            }
        }
    }

    post {
        always {
            echo 'build done!!!!!'
            slackSend baseUrl: "${slack_baseurl}", channel: "${slack_channel}",  color: "good", message: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", token: "${slack_token}"
        }
        failure {
            echo 'build Fail!!!!!'
            slackSend baseUrl: "${slack_baseurl}", channel: "${slack_channel}",  color: "danger", message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", token: "${slack_token}"
        }
    }
}