#!/usr/bin/env groovy

def maven
def openshift
def utilities

fileLoader.withGit('https://git.sits.no/git/scm/ao/aurora-pipeline-scripts.git', 'v1.1.3') {
  maven = fileLoader.load('maven/maven')
  openshift = fileLoader.load('openshift/openshift')
  utilities = fileLoader.load('utilities/utilities')
}

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
  }
  stage('JaCoCo') {
    maven.jacoco()
    utilities.createJUnitResultArchiver()
    utilities.createJacocoPublisher()
  }
  stage('SonarQube') {
    withSonarQubeEnv {
        maven.run("sonar:sonar -Dsonar.branch=${env.BRANCH_NAME}");
    }
  }
  stage('PIT Mutation Tests') {
    def isMaster = env.BRANCH_NAME == "master"
    echo "My branch is: ${env.BRANCH_NAME} "
    if (isMaster) {
      maven.pitest()
      utilities.createPitPublisher()
    } else {
      echo "Skipping PIT for branch ${env.BRANCH_NAME} "
    }
  }
  stage('Deploy to Nexus') {
    maven.deploy()
  }
  stage('OpenShift Build') {
    openshift.buildLeveransepakkePom()
  }
}


