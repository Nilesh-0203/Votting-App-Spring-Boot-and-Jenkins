pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Backend Test') {
            steps {
                sh '''
                    cd backend
                    chmod +x mvnw
                    ./mvnw clean test
                '''
            }
        }

    }
}