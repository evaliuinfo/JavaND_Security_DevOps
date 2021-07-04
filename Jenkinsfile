pipeline {
    agent any
    tools {
        maven 'Maven 3.1.1'
        jdk 'jdk8'
    }
    stages {
        stage('initialize') {
	    steps{
 	        sh 'echo "Start Ecommerce app"'
	    }
        }
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}
