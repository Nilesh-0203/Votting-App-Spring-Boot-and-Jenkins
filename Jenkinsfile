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
                    echo "===== JAVA ====="
                    java -version

                    echo "===== JAVAC ====="
                    javac -version

                    echo "===== JAVA_HOME ====="
                    echo $JAVA_HOME

                    echo "===== MAVEN ====="
                    cd backend
                    chmod +x mvnw
                    ./mvnw -version
                '''
            }
        }

        stage('Backend Test') {
            steps {
                sh '''
                    cd backend
                    ./mvnw clean test
                '''
            }
        }

    }
}
