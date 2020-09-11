String[] call(String image) {
  return sh(script: "curl https://index.docker.io/v1/repositories/$image/tags", returnStdout: true)
}
