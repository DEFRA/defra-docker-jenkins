boolean tagExists(String image, String tag) {
  String[] existingTags = getImageTags(image)
  if (existingTags.contains(tag)) {
    echo 'tag exists in repository'
    return true
  }
  return false
}
