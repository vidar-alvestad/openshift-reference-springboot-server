#!/usr/bin/env groovy

def version  = 'feature/AOS-2708'
fileLoader.withGit('https://git.aurora.skead.no/scm/ao/aurora-pipeline-scripts.git', version) {
   jenkinsfile = fileLoader.load('templates/leveransepakke')
}

def config = [
  pipelineScript              : pipelineScript,
  scriptVersion               : version,
  affiliation                 : "paas",
  downstreamSystemtestJob     : [ jobName: 'systemtest-refapp',  branch: env.BRANCH_NAME],
  piTests: false,
  credentialsId: "github",
  suggestVersionAndTagReleases: [
          [branch: 'master', versionHint: '2.0'],
          [branch: 'release/v1', versionHint: '1.0']]
  ]

jenkinsfile.run(version, config)


