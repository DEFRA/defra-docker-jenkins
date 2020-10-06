import uk.gov.defra.ImageMap

def call(Map config=[:]) {
  ImageMap[] imageMaps = config.imageMaps
  boolean isBuildable = BRANCH_NAME == 'master' || BRANCH_NAME == 'main'

  node {
    checkout scm
    try {
      if (isBuildable) {
        stage('Set GitHub status pending') {
          updateBuildStatus('Build started', 'PENDING')
        }
        imageMaps.each { ImageMap imageMap ->
          echo imageMap
          buildImages imageName: config.imageName, version: config.version, imageMap: imageMap
        }
        stage('Set GitHub status success') {
          updateBuildStatus('Build successful', 'SUCCESS')
        }
      }
  } catch (err) {
      stage('Set GitHub status failure') {
        updateBuildStatus(err.message, 'FAILURE')
      }
      throw err
    }
  }
}
