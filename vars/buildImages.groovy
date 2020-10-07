import uk.gov.defra.ImageMap
import uk.gov.defra.Image

def call(Map config=[:]) {
  String version
  Image image
  Image developmentImage

  stage("Set image variables") {
    version = config.imageMap.version
    image = new Image(DOCKER_REGISTRY, config.imageMap, config.imageName, config.version, config.tagName)
    developmentImage = new Image(DOCKER_REGISTRY, config.imageMap, config.imageName, config.version, config.tagName, true)
  }
  
  if (!tagExists(image.fullName(), version)) {
    stage("Build images (${version})") {
      buildImage(developmentImage)
      buildImage(image)
      if (image.isLatest()) {
        buildImage(developmentImage, true)
        buildImage(image, true)
      }
    }
  }

  if(config.prTag != '') {
    stage("Push images (${version})") {
      pushImage(developmentImage.fullName())
      pushImage(image.fullName())
      if (image.isLatest()) {
        pushImage(developmentImage.fullName(true))
        pushImage(image.fullName(true))
      }
    }
  }
}
