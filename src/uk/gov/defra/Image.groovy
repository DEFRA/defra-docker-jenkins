package uk.gov.defra

class Image implements Serializable {

  String registry
  ImageMap imageMap
  String imageName
  String version
  boolean isDevelopment

  Image(String registry, ImageMap imageMap, String imageName, String version, boolean isDevelopment = false) {
    this.imageMap = imageMap
    this.imageName = imageName
    this.version = version
    this.isDevelopment = isDevelopment
  }

  public boolean isLatest() {
    return imageMap.latest
  }

  public String target() {
    return isDevelopment ? 'development' : 'production'
  }

  public String fullName(boolean latest = false) {
    String tag = latest ? 'latest' : "${imageMap.version}-${imageName}${imageMap.tag}"
    String repository = isDevelopment ? "${imageName}-development" : imageName
    return "${registry}/${repository}:${tag}"
  }

}
