# buildParentImage

Pipeline to build parent image and push to DockerHub

## Parameters

- `config <Map>` - Jenkins pipeline config map
  - `imageMap <uk.gov.defra.ImageMap>` - instance of ImageMap class
  - `imageName <String>` - name of image without tags or repository
  - `tagName <String> (Optional)` - short name of source image, to be injected into image tag
  - `version <String>` - version to tag image with
