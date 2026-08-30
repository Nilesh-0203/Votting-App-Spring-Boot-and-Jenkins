pipeline {

    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Check Java') {
            steps {
                sh '''
                    java -version
                    echo "JAVA_HOME=$JAVA_HOME"
                    ./backend/mvnw -version
                '''
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
