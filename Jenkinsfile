pipeline {

    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {

        stage('Check Java') {
            steps {
                sh '''
                    java -version
                    echo "JAVA_HOME=$JAVA_HOME"
                '''
            }
        }

        stage('Backend Test') {
            steps {
                sh '''
            cd backend
            chmod +x mvnw

            echo "===== JAVA ====="
            java -version

            echo "===== JAVA_HOME ====="
            echo $JAVA_HOME

            echo "===== MAVEN ====="
            ./mvnw -version

            echo "===== BUILD ====="
            ./mvnw clean test
        '''
            }
        }

    }
}
