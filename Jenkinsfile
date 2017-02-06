#!/usr/bin/env groovy

def jenkinsfile
fileLoader.withGit('https://git.sits.no/git/scm/ao/aurora-pipeline-scripts.git', 'v2.3.0') {
   jenkinsfile = fileLoader.load('templates/leveransepakke')
}

def overrides = [piTests: false]
jenkinsfile.run('v2.3.0', overrides)