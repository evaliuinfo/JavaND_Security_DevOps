Jenkinsfile (Declarative Pipeline)
pipeline {
 agent { docker { image 'evaliuinfo/docker-build-ecommerce' } }
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
