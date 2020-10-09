library("defra-docker-jenkins@$BRANCH_NAME")

node {
  checkout scm

  String versionFileName = 'VERSION'
  String prTag = ''
  String repoName
  String versionTag
  
  try {
    stage('Set PR and version variables') {
      repoName = getRepoName()
      prTag = getPrTag()
      versionTag = getFileVersion(versionFileName)
    }

    if (prTag != '') {
      stage('Verify version incremented') {
        verifyVersionIncremented(versionFileName)
      }
    } else {
      stage('Trigger GitHub release') {
        withCredentials([
          string(credentialsId: 'github-defradigitalci-user', variable: 'gitToken')
        ]) {
          boolean releaseSuccess = triggerRelease(versionTag, repoName, versionTag, gitToken)

          if (releaseSuccess) {
            addSemVerTags(versionTag, repoName)
          }
        }
      }
    }
  } catch(err) {
    throw err
  }
}
