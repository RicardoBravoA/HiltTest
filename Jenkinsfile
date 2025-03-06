pipeline {
    agent any
    tools {
        jdk 'JDK17'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/RicardoBravoA/HiltTest.git'
            }
        }
        stage('Build and Test') {
            steps {
                sh './gradlew testDebugUnitTestCoverage'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('SonarQube') {
                        sh '''
                        ./gradlew sonarqube \
                        -Dsonar.projectKey=tu_project_key \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.login=tu_token
                        '''
                    }
                }
            }
        }
    }
}