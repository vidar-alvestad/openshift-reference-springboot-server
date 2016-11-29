#!/usr/bin/env groovy

def jenkinsfile
fileLoader.withGit('https://git.sits.no/git/scm/ao/aurora-pipeline-scripts.git', 'v1.0.0') {
   jenkinsfile = fileLoader.load('templates/leveransepakke')
}

jenkinsfile.run('v1.0.0', 'Maven 3', 'ci_aos', 'ci_aos')