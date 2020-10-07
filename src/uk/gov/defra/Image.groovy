package uk.gov.defra

class Image implements Serializable {

  String registry
  ImageMap imageMap
  String imageName
  String version
  String tagName
  boolean isDevelopment

  Image(String registry, ImageMap imageMap, String imageName, String version, String tagName = '', boolean isDevelopment = false) {
    this.registry = registry
    this.imageMap = imageMap
    this.imageName = imageName
    this.version = version
    this.tagName = tagName != '' ? tagName : imageName
    this.isDevelopment = isDevelopment
  }

  boolean isLatest() {
    return imageMap.latest
  }

  String target() {
    return isDevelopment ? 'development' : 'production'
  }

  String fullName(boolean latest = false) {
    String tag = latest ? 'latest' : "${version}-${tagName}${imageMap.tag}"
    String repository = isDevelopment ? "${imageName}-development" : imageName
    return "${registry}/${repository}:${tag}"
  }

}
