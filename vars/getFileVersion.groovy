String call(String fileName) {
  return ctx.sh(returnStdout: true, script: "cat $fileName").trim()
}
