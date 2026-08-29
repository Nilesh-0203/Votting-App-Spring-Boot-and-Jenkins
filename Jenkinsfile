pipeline {
    agent any
    tools {
        maven 'Maven3'   // must match the name you gave it in step 1
        jdk 'jdk17'       // only if you also configure a JDK tool; otherwise remove this line
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-creds')
        BACKEND_IMAGE  = "nilesh0203/poll-backend"
        FRONTEND_IMAGE = "nilesh0203/poll-frontend"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven Build - Backend') {
            steps {
                dir('backend') {           // remove dir() if backend is repo root
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Unit Tests') {
            steps {
                dir('backend') {
                    sh 'mvn test'
                }
            }
        }

        stage('Docker Build - Backend') {
            steps {
                dir('backend') {
                    sh "docker build -t ${BACKEND_IMAGE}:${BUILD_NUMBER} -t ${BACKEND_IMAGE}:latest ."
                }
            }
        }

        stage('Docker Build - Frontend') {
            steps {
                dir('frontend') {          // path to your Angular project
                    sh "docker build -t ${FRONTEND_IMAGE}:${BUILD_NUMBER} -t ${FRONTEND_IMAGE}:latest ."
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh "docker push ${BACKEND_IMAGE}:latest"
                sh "docker push ${FRONTEND_IMAGE}:latest"
            }
        }

        stage('Deploy') {
            steps {
                dir('deploy') {             // folder containing docker-compose.yml
                    sh '''
                        docker-compose pull
                        docker-compose down
                        docker-compose up -d
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully — app deployed..'
        }
        failure {
            echo 'Pipeline failed — check console output.'
        }
    }
}


