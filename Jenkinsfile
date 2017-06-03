#!/usr/bin/env groovy

def jenkinsfile
def version='feature/AOS-1592'
fileLoader.withGit('https://git.aurora.skead.no/scm/ao/aurora-pipeline-scripts.git', version) {
   jenkinsfile = fileLoader.load('templates/leveransepakke')
}

def overrides = [piTests: false, credentialsId: "github_bjartek"]
jenkinsfile.run(version, overrides)