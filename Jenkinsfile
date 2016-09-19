#!groovy

  node {
    stage('Checkout') {
      def isMaster = env.BRANCH_NAME == "master"
      def branchShortName = env.BRANCH_NAME.split("/").last()
      checkout scm

      // Set version in pom.xml
      echo "My branch is: ${env.BRANCH_NAME} "
      if (isMaster) {
        sh "./mvnw ske-cd:suggest-version versions:set -Dcd.version.accesibleFromProperty=newVersion -DgenerateBackupPoms=false"

        sh "git tag -a ${newVersion} -m 'Release ${newVersion} on master'"
        sh "git push --follow-tags"
      } else {
        sh "./mvnw versions:set -DnewVersion=${branchShortName}-SNAPSHOT -DgenerateBackupPoms=false -B"
      }
      // Set build name
      pom = readMavenPom file: 'pom.xml'
      currentBuild.displayName = "$pom.version (${currentBuild.number})"

      stash excludes: 'target/', includes: '**', name: 'source'
    }

    stage('Compile') {
      sh "./mvnw compile"
      step(
          [$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''])
    }
  }


  node {
    stage('Deploy to nexus') {
      unstash 'source'
      sh "./mvnw deploy -DskipTests"
    }
  }


  stage('Deploy to Openshift') {
    timeout(time: 7, unit: 'DAYS') {
      input message: 'Approve deployment?'
    }
  }
  node {
    def os
    fileLoader.withGit('https://ci_map@git.sits.no/git/scm/ao/aurora-pipeline-scripts.git', 'master') {
      os = fileLoader.load('openshift/openshift')
    }
    // Get artifact version
    unstash 'source'
    pom = readMavenPom file: 'pom.xml'

    os.buildVersion('mfp-openshift-referanse-springboot-server', pom.artifactId,   pom.version)
  }
