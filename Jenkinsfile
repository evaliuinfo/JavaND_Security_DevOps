pipeline {
    agent any
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
