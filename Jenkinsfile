node {
    stage('Code Pull'){
        git branch: 'main', credentialsId: 'github-auth', url: 'https://github.com/ak-alam/scripted_pipeline_test.git'
    }
    stage('Check') { 
        try {
        def status = sh(returnStatus: true, script: 'aws cloudformation describe-stacks --stack-name akbar-s3 --output text --region "us-east-2"')
        sh "echo error_status: $status"
        println status
        if ( status.equals(255) ) {
            sh "echo Stack Not Exist! Creating New Stack"
            sh "echo error_status on creation: $status"
            sh 'aws cloudformation create-stack --stack-name akbar-s3 \
                --template-body file:///var/lib/jenkins/workspace/scripted-cf/s3.yaml \
                --parameters ParameterKey=bucketName,ParameterValue=akbar-jenkins --region "us-east-2"'

        } else {
            sh "echo Stack Exist! Updating Stack."
            sh "echo error_status on update: $status"
            sh 'aws cloudformation update-stack --stack-name akbar-s3 \
                --template-body file:///var/lib/jenkins/workspace/scripted-cf/s3.yaml \
                --parameters ParameterKey=bucketName,ParameterValue=akbar-jenkins-new --region "us-east-2"'
        }
        } catch (Exception e){
            // println "Exception occurred: + e.toString()"
            sh 'echo No updates are to be performed!'
        } 
    }
}
