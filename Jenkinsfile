#!/usr/bin/env groovy

def jenkinsfile
def version='v3.1.0'
fileLoader.withGit('https://git.aurora.skead.no/scm/ao/aurora-pipeline-scripts.git', version) {
   jenkinsfile = fileLoader.load('templates/leveransepakke')
}

def overrides = [
  piTests: false,
  // We had to disable sonar integration because Jenkins terminated the report generation and failed the build
  // completely. There is a Jira task (https://aurora/jira/browse/AOS-1951) to investigate the issue.
  sonarQube: false,
  credentialsId: "github"
]
jenkinsfile.run(version, overrides)
