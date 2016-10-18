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

maven.setVersion('Maven 3')
milestone 1


node {
  stage('Checkout') {
    checkout scm
    maven.bumpVersion()
    stash excludes: 'target/', includes: '**', name: 'source'
  }

  stage('Compile') {
    maven.compile()
    utilities.createCheckStylePublisher()
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
      maven.sonar()
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
