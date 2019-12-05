pipeline {
    agent any
    environment {
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
            echo 'step fin!!!!!'
            slackSend color: "good", message: "FIN STEP: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
        }
        success {
            echo 'build success!!!!!'
            slackSend color: "good", message: "SUCCESS BUILD: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
        }
        failure {
            echo 'build Fail!!!!!'
            slackSend color: "good", message: "SUCCESS BUILD: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
        }
    }
}