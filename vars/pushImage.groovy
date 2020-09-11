def call(String image) {
  withCredentials([
    usernamePassword(credentialsId : DOCKERHUB_CREDENTIALS_ID,
      usernameVariable: 'username', passwordVariable: 'password')
  ]) {
    sh "docker login --username $username --password $password"
    // sh "docker push $image"
  }
}
