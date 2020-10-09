boolean call(String versionTag, String repoName, String releaseDescription, String token) {
  boolean releaseExists = releaseExists(versionTag, repoName, token)
  if (releaseExists) {
    echo("Release $versionTag already exists")
    return false
  }

  echo("Triggering release $versionTag for $repoName")
  boolean result = false
  result = sh(returnStdout: true, script: "curl -s -X POST -H 'Authorization: token $token' -d '{ \"tag_name\" : \"$versionTag\", \"name\" : \"Release $versionTag\", \"body\" : \" Release $releaseDescription\" }' https://api.github.com/repos/DEFRA/$repoName/releases")
  
  if (releaseExists) {
    echo('Release Successful')
  } else {
    throw new Exception('Release failed')
  }
  return true
}

boolean releaseExists(String versionTag, String repoName, String token) {
  try {
    return sh(returnStdout: true, script: "curl -s -H 'Authorization: token $token' https://api.github.com/repos/DEFRA/$repoName/releases/tags/$versionTag | jq '.tag_name'").trim().replaceAll (/"/, '') == versionTag ? true : false
  }
  catch(Exception ex) {
    echo('Failed to check release status on github')
    throw new Exception (ex)
  }
}
