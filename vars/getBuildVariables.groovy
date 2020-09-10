def getBuildVariables() {
  imageRepositoryDevelopmentLatest = "${DOCKER_REGISTRY}/$imageNameDevelopment"
  imageRepositoryProductionLatest = "${DOCKER_REGISTRY}/$imageNameProduction"
}
