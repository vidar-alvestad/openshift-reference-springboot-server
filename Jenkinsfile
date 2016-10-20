#!groovy

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
    stash includes: '**', name: 'source'
  }
}

node {
  stage('Jacoco') {
      unstash 'source'
      maven.jacoco()
      utilities.createJUnitResultArchiver()
      utilities.createJacocoPublisher()
    }
  }

 node {
  stage('Sonar') {

      unstash 'source'
      maven.sonar('http://aurora/magsonar')
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
      input id: 'DeployOpenShift', message: 'Approve deployment?'
      echo "Deploy to Openshift"
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
