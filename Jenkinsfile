pipeline {
    agent any
    tools {
        maven 'Maven 3.6.1'
    }
    stages {
        stage('build backend') {
            steps {
                script {
                    bat 'cd backend && start mvn clean install -DskipTests'
                }
            }
        }

        stage ('up docker containers') {
            steps {
                sh 'sudo docker-compose up'
            }
        }
    }
}