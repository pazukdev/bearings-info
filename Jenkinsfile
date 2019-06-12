pipeline {
    agent any
    tools {
        maven 'Maven 3.6.1'
    }
    stages {

        stage ('up docker containers') {
            steps {
                sh 'docker-compose up'
            }
        }

    }
}