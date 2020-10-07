String call(String fileName) {
  return sh(returnStdout: true, script: "cat $fileName").trim()
}
