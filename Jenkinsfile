#!/usr/bin/env groovy

def git
def maven
def os
def utilities
fileLoader.withGit('https://git.sits.no/git/scm/ao/aurora-pipeline-scripts.git', 'master') {
  git = fileLoader.load('git/git')
  maven = fileLoader.load('maven/maven')
  os = fileLoader.load('openshift/openshift')
  utilities = fileLoader.load('utilities/utilities')
}

milestone 1

node {
  stage('Checkout') {
    maven.setVersion('Maven 3')
    checkout scm
    maven.bumpVersion()
    maven.tag('ci_aos', 'ci_aos')
  }
  stage('Compile') {
    maven.compile()
    utilities.createCheckStylePublisher()
    stash excludes: 'target/', includes: '**', name: 'source'
  }
}

parallel 'jacoco': {
  stage('Jacoco') {
    node {
      unstash 'source'
      maven.jacoco()
      utilities.createJUnitResultArchiver()
      utilities.createJacocoPublisher()
    }
  }
}, 'Sonar': {
  stage('Sonar') {
    node {
      unstash 'source'
      maven.sonar('http://aurora/magsonar')
    }
  }
}

node {
  stage('PITest') {
    println("PITest")
    unstash 'source'
    maven.pitest()
    utilities.createPitPublisher()
  }
}

node {
  stage('Deploy to nexus') {
    unstash 'source'
    maven.deploy()
  }
}

node {
  stage('Openshift Build') {
    os.buildLeveransepakkePom()
  }
}