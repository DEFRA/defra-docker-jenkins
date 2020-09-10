import uk.gov.defra.ImageMap

def call(Map config=[:]) {
  String prTag = getPrTag
  ImageMap[] imageMaps = config.imageMaps
  boolean isBuildable = BRANCH_NAME == 'master' || prTag != ''

  node {
    checkout scm
    try {
      if (isBuildable) {
        stage('Set GitHub status pending') {
          updateGithubCommitStatus('Build started', 'PENDING')
        }
        imageMaps.each { ImageMap imageMap ->
          buildImages imageName: config.imageName, version: config.version, imageMap: imageMap
        }
        stage('Set GitHub status success') {
          updateGithubCommitStatus('Build successful', 'SUCCESS')
        }
      }
  } catch (err) {
      stage('Set GitHub status failure') {
        updateGithubCommitStatus(err.message, 'FAILURE')
      }
      throw err
    }
  }
}
