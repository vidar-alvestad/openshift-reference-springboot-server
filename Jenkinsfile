#!/usr/bin/env groovy

def version  = 'feature/AOS-2708'
fileLoader.withGit('https://git.aurora.skead.no/scm/ao/aurora-pipeline-scripts.git', version) {
   jenkinsfile = fileLoader.load('templates/leveransepakke')
}

def systemtest = [
  auroraConfigEnvironment : 'st-refapp',
  path : 'src/systemtest',
  applicationUnderTest : "referanse",
  testStages: [
    [
      stageName : 'postman',
      stageType : 'postman',
      npmCommand : 'test',
    ],
    [
      stageName : 'gatling',
      stageType: 'gatling',
      appDir   : 'gatling'
    ]
  ]
]

def config = [
  affiliation: "paas",
  testStages:[systemtest],
  piTests: false,
  credentialsId: "github"
  suggestVersionAndTagReleases: [
          [branch: 'master', versionHint: '2.0'],
          [branch: 'release/v1', versionHint: '1.0']]
  ]

jenkinsfile.run(version, config)


