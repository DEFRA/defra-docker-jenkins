import uk.gov.defra.ImageMap
import uk.gov.defra.Image

def call(Map config=[:]) {
  String version = config.imageMap.version
  Image image = new Image(DOCKER_REGISTRY, config.imageMap, config.imageName, config.version)
  Image developmentImage = new Image(DOCKER_REGISTRY, config.imageMap, config.imageName, config.version, true)

  if (!tagExists(image.fullName)) {
    stage("Build images (${version})") {
      buildImage(developmentImage)
      buildImage(image)
      if (image.isLatest) {
        buildImage(developmentImage, true)
        buildImage(image, true)
      }
    }
    stage("Push images (${version})") {
      pushImage(developmentImage.fullName)
      pushImage(image.fullName)
      if (image.isLatest) {
        pushImage(developmentImage.fullName(true))
        pushImage(image.fullName(true))
      }
    }
  }
}
