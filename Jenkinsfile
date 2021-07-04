pipeline {
    agent any
    tools {
        maven 'MAVEN'
        jdk 'JDK'
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
