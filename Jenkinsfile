pipeline {
    stages {
        stage('build') {
            steps {
                sh 'mvn clean install'
                sh 'npm install'
                sh 'npm run dev'
            }
        }
    }
}