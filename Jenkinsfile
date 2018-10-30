#!/usr/bin/env groovy

def version = 'feature/AOS-2785'
fileLoader.withGit('https://git.aurora.skead.no/scm/ao/aurora-pipeline-scripts.git', version) {
   jenkinsfile = fileLoader.load('templates/leveransepakke')
   openshift = fileLoader.load('openshift/openshift')
}

def result = openshift.getDatabaseInfo('demo-development','reference','reference')
println result


def systemtest = [
  auroraConfigEnvironment : 'st-refapp',
  path : 'src/systemtest',
  applicationUnderTest : "referanse",
  npmScripts : ['test'],
  gatling : [
    "appDir" : "gatling"
  ]
]

def config = [
  affiliation: "paas",
  testStages:[systemtest],
  piTests: false,
  credentialsId: "github"
  ]

/*
jenkinsfile.run(version, config)
*/

