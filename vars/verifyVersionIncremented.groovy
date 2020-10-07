void call(String fileName) {
  String currentVersion = getFileVersion(fileName)
  String previousVersion = getPreviousFileVersion(fileName, currentVersion)
  errorOnNoVersionIncrement(previousVersion, currentVersion)
}

String getPreviousFileVersion(String fileName, String currentVersion) {
  String majorVersion = currentVersion.split('\\.')[0]
  // if there are no existing versions of the MAJOR version no SHA will exist
  String previousVersionSha = sh(returnStdout: true, script: "git ls-remote origin -t $majorVersion | cut -f 1").trim()
  return previousVersionSha
    ? sh(returnStdout: true, script: "git show $previousVersionSha:$fileName").trim()
    : ''
}

void errorOnNoVersionIncrement(String previousVersion, String currentVersion){
  String cleanPreviousVersion = extractSemVerVersion(previousVersion)
  String cleanCurrentVersion = extractSemVerVersion(currentVersion)
  if (hasIncremented(cleanPreviousVersion, cleanCurrentVersion)) {
    echo("Version increment valid '$previousVersion' -> '$currentVersion'.")
  } else {
    error("Version increment invalid '$previousVersion' -> '$currentVersion'.")
  }
}

String extractSemVerVersion(String versionTag) {
  String[] splitTag = versionTag.split(/^v-/)
  return splitTag.length > 1 ? splitTag[1] : versionTag
}

boolean hasIncremented(String currentVersion, String newVersionList) {
  // For a newly created empty repository currentVersion will be empty on first
  // merge to master consider 'newVersionList' the first version and return true
  if (currentVersion == '') {
    return true
  }
  try {
    int[] currentVersionList = currentVersion.tokenize('.').collect { it.toInteger() }
    int[] newVersionListList = newVersionList.tokenize('.').collect { it.toInteger() }
    return currentVersionList.size() == 3 &&
            newVersionListList.size() == 3 &&
            [0, 1, 2].any { newVersionListList[it] > currentVersionList[it] }
  }
  catch (Exception ex) {
    return false
  }
}
