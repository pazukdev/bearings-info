pipeline {
    agent any
    tools {
        maven 'Maven 3.6.1'
    }
    stages {

        stage ('build backend') {
            steps {
                sh 'mvn clean -Dmaven.test.failure.ignore=true install'
            }
            post {
                success {
                    junit 'backend/target/surefire-reports/**/*.xml'
                }
            }
        }

        stage ('build frontend') {
            steps {
                dir("/frontend") {
                    sh "npm install"
                }
            }
        }
    }
}