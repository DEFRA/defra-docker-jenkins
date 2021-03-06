import uk.gov.defra.Image

void call(Image image, boolean latest = false) {
  String imageName = getImageName(image, latest)

  sh "docker build --no-cache \
    --tag $imageName \
    --build-arg BASE_VERSION=$image.imageMap.tag \
    --build-arg DEFRA_VERSION=$image.version \
    --target ${image.target()} \
    ."
}

String getImageName(Image image, boolean latest = false) {
  return latest ? image.fullName(true) : image.fullName()
}
