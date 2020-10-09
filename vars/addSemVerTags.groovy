void call(String version, String repoName) {
  String[] versionList = version.tokenize('.')
  assert versionList.size() == 3

  String majorTag = "${versionList[0]}"
  String minorTag = "${versionList[0]}.${versionList[1]}"
  String commitSha = getCommitSha()

  tagCommit(minorTag, commitSha, repoName)
  tagCommit(majorTag, commitSha, repoName)
}

void tagCommit(String tag, String commitSha, String repoName) {
  dir('attachTag') {
    sshagent(['defra-docker-jenkins-github-deploy-key']) {
      git(credentialsId: 'defra-docker-jenkins-github-deploy-key', url: "git@github.com:DEFRA/${repoName}.git")
      sh("git push origin :refs/tags/$tag")
      sh("git tag -f $tag $commitSha")
      sh("git push origin $tag")
    }
    deleteDir()
  }
}
