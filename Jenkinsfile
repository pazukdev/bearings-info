pipeline {
    agent any
    tools {
        maven 'Maven 3.6.1'
    }
    stages {
        stage('build backend and run tests') {
            steps {
                script {
                    sh 'cd backend'
                    sh 'mvn clean install'
                }
            }
        }

        stage ('up docker containers') {
            steps {
                script {
                    sh 'cd ..'
                    sh 'sudo docker-compose up --no-start'
                    sh 'sudo docker-compose down' // to remove container
                }
            }
        }

        stage ("Extract test results") {
            cobertura coberturaReportFile: 'path-to/coverage.xml'
        }
    }
}