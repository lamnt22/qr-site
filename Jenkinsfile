pipeline {
    agent any
    tools{
        maven 'maven_3'
    }
    stages{
	    stage('File Permission') {
	        steps {
	            sh "sudo chmod -R 777 /var/lib/jenkins/workspace/qrmenu-api"
	        }
	    }
	    stage('Checkout') {
		steps {
		     checkout scm
		}
	    }
        stage('Build Maven'){
            steps{
	       sh 'mvn clean install'
	       sh 'cp -f ./target/*.jar /var/www/qrsite/api'
	       sh 'sudo chmod -R 777 /var/www/qrsite/api'
            }
        }
        stage('Restart service'){
            steps{
                script{
                    sh 'sh /var/www/qrsite/api/qrmenu.sh stop'
                    sh 'sh /var/www/qrsite/api/qrmenu.sh start'
                }
            }
        }
    }
}
