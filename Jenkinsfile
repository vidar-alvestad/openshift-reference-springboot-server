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
      echo "YOYOYO!!"
      // TODO: newVersion property is not set correctly
      // groovy.lang.MissingPropertyException: No such property: newVersion for class: groovy.lang.Binding
      // echo "newVersion: ${newVersion}"
      // sh "git tag -a ${newVersion} -m 'Release ${newVersion} on master'"
      // sh "git push --follow-tags"
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

parallel 'jacoco': {
  stage('Jacoco') {
    node {
      unstash 'source'
      sh "./mvnw jacoco:prepare-agent test jacoco:report -B"

      step([$class: 'JUnitResultArchiver', testResults: '**/surefire-reports/TEST-*.xml'])
      publishHTML([allowMissing: true, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target/site/jacoco/', reportFiles: 'index.html', reportName: 'Code Coverage'])
      // step([$class: 'JacocoPublisher', execPattern:'**/target/**.exec', classPattern: '**/classes', sourcePattern: '**/src/main/java'])
    }
  }
}, 'PITest': {
  stage('PITest') {
    node {
      println("PITest")
      unstash 'source'
      sh "./mvnw test pitest:mutationCoverage -B"
    }
  }
}/*, 'Sonar': {
  stage('Sonar') {
    node {
      def sonarServerUrl = 'http://aurora/magsonar'
      unstash 'source'
      sh "./mvnw sonar:sonar -D sonar.host.url=${sonarServerUrl} -Dsonar.language=java -Dsonar.branch=${env.BRANCH_NAME} -B"
    }
  }
}*/

node {
  stage('Deploy to nexus') {
    unstash 'source'
    sh "./mvnw deploy -DskipTests"
  }
}

try {
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
} catch (error) {
  currentBuild.result = 'SUCCESS'
}
