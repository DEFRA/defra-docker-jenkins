String call() {
  String repoName = getRepoName()
  return sh(returnStdout: true, script:
      "curl https://api.github.com/repos/DEFRA/$repoName/pulls?state=open | \
      jq '.[] | \
      select(.head.ref == \"$BRANCH_NAME\") | \
      .number'"
    ).trim()
}
