library("defra-docker-jenkins@$BRANCH_NAME")

node {
  checkout scm

  String versionFileName = 'VERSION'
  String prTag = ''
  String repoName
  String versionTag
  boolean isBuildable
  
  try {
    stage('Set PR and version variables') {
      repoName = getRepoName()
      prTag = getPrTag()
      versionTag = getFileVersion(versionFileName)
      isBuildable = BRANCH_NAME == 'master' || BRANCH_NAME == 'main' || prTag != ''
    }

    if (isBuildable) {
      stage('Set GitHub status pending') {
        updateBuildStatus('Build started', 'PENDING')
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

      stage('Set GitHub status success') {
        updateBuildStatus('Build successful', 'SUCCESS')
      }
    }
  } catch(err) {
    stage('Set GitHub status failure') {
      updateBuildStatus(err.message, 'FAILURE')
    }
    throw err
  }
}
