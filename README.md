# Defra Docker Jenkins

Shared Jenkins library for Defra Docker parent images.

## Overview

Common scripts for use in Jenkins pipelines used in build and publishing of Docker parent images.

## Versioning

The library is versioned following the principle of the
[semantic versioning specification](https://semver.org/). When updating the
library you will need to increment the version number in the
[VERSION](VERSION) file.
Upon merge to the master branch a new GitHub release will automatically be
created, tagged with the `MAJOR`, `MINOR` and `PATCH` versions. For example
version `2.4.6` will have the tags `2`, `2.4` and `2.4.6`.

**Note:** Due to the way versions of Jenkins shared libraries are resolved, a
single numeric tag such as `6` is not precise enough to prevent other branches
or SHAs from being matched and that version being used rather than the expected
tagged version of the library. To prevent this from causing problems the
SemVer version is prepended with a `v-`. Doing so means the tag will not be
mistakenly resolved against a SHA (alphanumeric only) although it might still
be matched against a branch. However, this is much less likely and is under the
control of the developers.
The unique tag doesn't need to be `v-` and is easily changed
by updating the code in `version.extractSemVerVersion()`. The version is
specified in [VERSION](VERSION) and would also need to be updated.

## Usage

A default build configuration is available for all Docker parent images.

To use the image, the repository must be hosted on GitHub and contain a `Jenkinsfile`.

The Jenkinsfile should declare an array instance of the `uk.gov.defra.ImageMap` class to provide version mapping information and a call to the common pipeline `buildParentImage`

Example Jenkinsfile:
```
@Library('defra-docker-jenkins@v-1') _

import uk.gov.defra.ImageMap

ImageMap[] imageMaps = [
  [version: '8.17.0', tag: '8.17.0-alpine', latest: false],
  [version: '12.18.3', tag: '12.18.3-alpine3.12', latest: true],
]

buildParentImage imageName: 'node',
  imageMaps: imageMaps,
  version: '1.2.1'
```

### Using a specified library version

Jenkinsfiles consume the library using the `@library` annotation, including an optional tag or branch.
SemVer tags are created for each release, so the `MAJOR`, `MINOR` or `PATCH`
version of a release can be targeted.

```
@Library('defra-library')
@Library('defra-library@master')
@Library('defra-library@3.1.2')
@Library('defra-library@3.1')
@Library('defra-library@3')
```

## Available methods for build configuration

The groovy scripts within [vars](vars) are documented with a corresponding
`.md` file. There is also a corresponding `.txt` file, this contains basic html
which links back to the markdown file and will be rendered within the Jenkins
UI as per the
[documentation](https://www.jenkins.io/doc/book/pipeline/shared-libraries/#directory-structure).

An additional file to ease navigation within the directory is also provided -
[README](./vars/README.md)

## Setup Jenkins for use with the library

### Add global pipeline library

As per the
[documentation](https://www.jenkins.io/doc/book/pipeline/shared-libraries/#using-libraries)
the library needs to be setup as a global pipeline library. The name needs to
be set to `defra-docker-jenkins` (for the examples above to work). This feature is
enabled through the
[Shared Groovy Libraries](https://plugins.jenkins.io/workflow-cps-global-lib/)
plugin.

### Configure environment variables

A number of environment variables are used by the library. The following table
includes the list of variables that need to be setup as global properties.

| Name                             | Description                                                                                          | Value                                                               |
| ----                             | -----------                                                                                          | -----                                                               |
| `DOCKER_REGISTRY`                | Domain of Docker registry where non-public images are pushed to and pulled from                      | `defradigital`                                                       |
| `DOCKER_REGISTRY_CREDENTIALS_ID` | Name of credential stored in Jenkins containing the credentials used to access the `DOCKER_REGISTRY` | :see_no_evil:                                                       |
| `HELM_CHART_REPO_PUBLIC`         | Path to the public Helm chart repository                                                             | https://raw.githubusercontent.com/defra/ffc-helm-repository/master/ |
