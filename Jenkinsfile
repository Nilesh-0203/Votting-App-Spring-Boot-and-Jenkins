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
            which java
            java -version

            echo "===== JAVAC ====="
            which javac
            javac -version

            echo "===== JAVA_HOME ====="
            echo $JAVA_HOME

            echo "===== MAVEN ====="
            cd backend
            ./mvnw -version

            echo "===== JAVAC FROM JAVA_HOME ====="
            $JAVA_HOME/bin/javac -version
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
