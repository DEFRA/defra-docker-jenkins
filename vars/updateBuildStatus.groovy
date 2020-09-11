void call(String message, String state) {
  String repoUrl = getRepoUrl
  String commitSha = getCommitSha
  step([
    $class: 'GitHubCommitStatusSetter',
    reposSource: [$class: 'ManuallyEnteredRepositorySource', url: repoUrl],
    commitShaSource: [$class: 'ManuallyEnteredShaSource', sha: commitSha],
    errorHandlers: [[$class: 'ShallowAnyErrorHandler']],
    statusResultSource: [ $class: 'ConditionalStatusResultSource',
      results: [[$class: 'AnyBuildResult', message: message, state: state]]],
  ])
}
