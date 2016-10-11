#!groovy

def git
def maven
def os
def utilities
fileLoader.withGit('https://git.sits.no/git/scm/ao/aurora-pipeline-scripts.git', 'feature/AOS-478') {
  git = fileLoader.load('git/git')
  maven = fileLoader.load('maven/maven')
  os = fileLoader.load('openshift/openshift')
  utilities = fileLoader.load('utilities/utilities')
}

milestone 1

node {
  stage('Checkout') {
    checkout scm
    gitCommit = git.getCommitId()
    maven.bumpVersion()
    stash excludes: 'target/', includes: '**', name: 'source'
  }

  stage('Compile') {
    maven.compile()
    utilities.getCheckstyleReports()
  }
}

parallel 'jacoco': {
  stage('Jacoco') {
    node {
      unstash 'source'
      maven.jacoco()
      utilities.getJacocoReports()
      utilities.getSurefireReports()
    }
  }
}, 'Sonar': {
  stage('Sonar') {
    node {
      unstash 'source'
      maven.sonar()
    }
  }
}

node {
  stage('PITest') {
    println("PITest")
    unstash 'source'
    maven.pitest()
    utilities.getPitReports()
  }
}

node {
  stage('Deploy to nexus') {
    unstash 'source'
    maven.deploy()
  }
}


try {
  stage('Deploy to Openshift') {
    timeout(time: 7, unit: 'DAYS') {
      input message: 'Approve deployment?'
    }
    milestone 2
  }
  node {
    // Get artifact version
    unstash 'source'
    pom = readMavenPom file: 'pom.xml'

    os.buildVersion('mfp-openshift-referanse-springboot-server', 'springboot-server', pom.version)
  }
} catch (error) {
  currentBuild.result = 'SUCCESS'
}
