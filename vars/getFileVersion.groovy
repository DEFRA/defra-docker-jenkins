String call() {
  return ctx.sh(returnStdout: true, script: "cat $fileName").trim()
}
