def call() {
  return sh(returnStdout: true, script: 'git config --get remote.origin.url').trim()
}
