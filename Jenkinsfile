pipeline {
    agent any
    tools {
        maven 'Maven 3.6.1'
    }
    stages {
        stage('build backend') {
            steps {
                sh 'cd backend;'
                + 'mvn clean install -DskipTests'
            }
        }

        stage ('up docker containers') {
            steps {
                sh 'cd ..'
                 + 'sudo docker-compose up'
            }
        }
    }
}