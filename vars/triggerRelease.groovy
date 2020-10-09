boolean call(String versionTag, String repoName, String releaseDescription, String token) {
  if (releaseExists(versionTag, repoName, token)) {
    echo("Release $versionTag already exists")
    return false
  }

  echo("Triggering release $versionTag for $repoName")
  boolean result = false
  result = sh(returnStdout: true, script: "curl -s -X POST -H 'Authorization: token $token' -d '{ \"tag_name\" : \"$versionTag\", \"name\" : \"Release $versionTag\", \"body\" : \" Release $releaseDescription\" }' https://api.github.com/repos/DEFRA/$repoName/releases")
  
  if (releaseExists(ctx, versionTag, repoName, token)) {
    echo('Release Successful')
  } else {
    throw new Exception('Release failed')
  }
  return true
}

boolean releaseExists(String versionTag, String repoName, String token){
  try {
    def value = sh(returnStdout: true, script: "curl -s -H 'Authorization: token $token' https://api.github.com/repos/DEFRA/$repoName/releases/tags/$versionTag | jq '.tag_name'").trim().replaceAll (/"/, '') == versionTag ? true : false
    echo value
    return value
  }
  catch(Exception ex) {
    echo('Failed to check release status on github')
    throw new Exception (ex)
  }
}
