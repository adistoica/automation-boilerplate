pipeline {
    agent any
     tools {
            maven "maven"
        }
    stages {
        stage('Checkout') {
            steps {
                git([url: 'https://github.com/adistoica/automation-boilerplate.git', branch: 'main'])
            }
        }

        stage('Execute') {
            steps {
               sh "mvn -Dmaven.test.failure.ignore=true clean test"
            }
        }

        stage('Publish Results') {
            steps {
                step([$class: 'Publisher', reportFilenamePattern: "**/testng-results.xml"])
            }
        }
    }
}