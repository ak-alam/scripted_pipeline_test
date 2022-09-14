node {
    stage('Check') { 
        
        def status = sh(returnStatus: true, script: 'aws cloudformation describe-stacks --stack-name akbar-vpc --output text --region "us-east-2"')
        sh "echo erro_statue: $status"
    }
}