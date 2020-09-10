String getPrTag() {
  return sh(returnStdout: true, script:
      "curl https://api.github.com/repos/DEFRA/$getRepoName/pulls?state=open | \
      jq '.[] | \
      select(.head.ref == \"$BRANCH_NAME\") | \
      .number'"
    ).trim()
}
