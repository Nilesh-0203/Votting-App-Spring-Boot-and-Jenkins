pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

<<<<<<< HEAD
=======
        stage('Check Java') {
            steps {
                sh '''
                    java -version
                    echo "JAVA_HOME=$JAVA_HOME"
                '''
            }
        }

>>>>>>> c64e648 (Fix Maven wrapper permissions)
        stage('Backend Test') {
            steps {
                sh '''
                    cd backend
                    chmod +x mvnw
                    ./mvnw -version
                    ./mvnw clean test
                '''
            }
        }

    }
}