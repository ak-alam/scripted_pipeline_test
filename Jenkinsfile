pipeline {
    agent any
    parameters {
    booleanParam 'delete_stack' 
    string description: 'Specify Cloudformation stack name', name: 'stack_name'
    string description: 'Specify Cloudformation template name', name: 'template_name'
    string description: 'Specify Cloudformation json parameter file name', name: 'param_file_name'
    }
        
    stages {
        // stage('test') {
        //     steps {
        //       sh 'pwd'
        //     }
        // }
        stage('source') {
            steps {
                git branch: 'main', credentialsId: 'github-token', url: 'https://github.com/ak-alam/scripted_pipeline_test.git'
            }
        }
        stage('Template Validation') {
             when {
                    //skip if build param (delete_stack) is check.
                    expression { params.delete_stack == false}
                }
            steps {
                script {
                    try {
                        echo "Valid Template."
                        // cfnValidate(file:'/var/lib/jenkins/workspace/declarative-cf/s3.yaml')
                        cfnValidate(file:"${env.WORKSPACE}/${params.template_name}.yaml")

                    }
                    catch(Exception e) {
                        catchError (buildResult: 'UNSTABLE', stageResult: 'FAILURE'){
                            echo "Template Validation failed: catchError"
                            sh 'exit 1'
                        }
                    }
                }
            }
        }
        stage('create & update') {
            when {
                    //skip if build param (delete_stack) is check.
                    expression { params.delete_stack == false}
                }
            steps {
                // cfnUpdate(stack:'akbar-jenkins-stack', file:'/var/lib/jenkins/workspace/declarative-cf/s3.yaml', paramsFile:'/var/lib/jenkins/workspace/declarative-cf/params.json', pollInterval:1000)
                cfnUpdate(stack:"${params.stack_name}", file:"${env.WORKSPACE}/${params.template_name}.yaml", paramsFile:"${env.WORKSPACE}/${params.param_file_name}.json", pollInterval:1000)

            }
        }
        stage('delete stack'){
            when {
                expression { params.delete_stack == true }
            }

            steps {
                cfnDelete(stack:"${params.stack_name}", pollInterval:1000)

            }
        }
    }
}
