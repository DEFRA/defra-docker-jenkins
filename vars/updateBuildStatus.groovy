
void call(String message, String state) {
  step([
    $class: 'GitHubCommitStatusSetter',
    reposSource: [$class: 'ManuallyEnteredRepositorySource', url: getRepoUrl],
    commitShaSource: [$class: 'ManuallyEnteredShaSource', sha: getCommitSha],
    errorHandlers: [[$class: 'ShallowAnyErrorHandler']],
    statusResultSource: [ $class: 'ConditionalStatusResultSource',
      results: [[$class: 'AnyBuildResult', message: message, state: state]]],
  ])
}
