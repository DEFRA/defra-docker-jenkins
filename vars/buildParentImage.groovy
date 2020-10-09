import uk.gov.defra.ImageMap

def call(Map config=[:]) {
  node {
    checkout scm
    try {
      String prTag
      ImageMap[] imageMaps
      boolean isBuildable

      stage('Set build variables') {
        prTag = getPrTag()
        imageMaps = config.imageMaps
        isBuildable = BRANCH_NAME == 'master' || BRANCH_NAME == 'main' || prTag != ''
      }

      if (isBuildable) {
        stage('Set GitHub status pending') {
          updateBuildStatus('Build started', 'PENDING')
        }
        imageMaps.each { ImageMap imageMap ->
          buildImages imageName: config.imageName, version: config.version, tagName: config.tagName, imageMap: imageMap, prTag: prTag
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
