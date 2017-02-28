#!/usr/bin/env groovy

def jenkinsfile
fileLoader.withGit('https://git.aurora.skead.no/scm/ao/aurora-pipeline-scripts.git', 'v2.7.2') {
   jenkinsfile = fileLoader.load('templates/leveransepakke')
}

def overrides = [piTests: false]
jenkinsfile.run('v2.7.2', overrides)