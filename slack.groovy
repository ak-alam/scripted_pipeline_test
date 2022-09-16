def slackNotification(){
    def COLOR_MAP = [
    'SUCCESS': 'good',
    'FAILURE': 'danger',
    ]
    slackSend channel: '#akbar-jenkins',
                      color: COLOR_MAP[currentBuild.currentResult],
                      message: "*${currentBuild.currentResult}:* ${env.JOB_NAME} build ${env.BUILD_NUMBER}"
}