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
  }
}

parallel 'jacoco': {
  stage('Jacoco') {
    node {
      unstash 'source'
      sh "./mvnw jacoco:prepare-agent test jacoco:report -B"
      step([$class: 'JacocoPublisher', execPattern:'**/target/**.exec', classPattern: '**/classes', sourcePattern: '**/src/main/java'])
      //publishHTML(
      //   target: [reportDir: 'build/reports/jacoco/jacocoRootTestReport/html', reportFiles: 'index.html', reportName: 'Code Coverage'])
      //step(
      //    [$class: 'JacocoPublisher', execPattern: 'build/jacoco/*.exec', classPattern: 'build/classes/main', sourcePattern: 'src/main/java'])
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
}, 'Sonar': {
  stage('Sonar') {
    node {
      def sonarServerUrl = 'http://aurora/magsonar'
      unstash 'source'
      sh "./mvnw sonar:sonar -D sonar.host.url=${sonarServerUrl} -Dsonar.language=java -Dsonar.branch=${env.BRANCH_NAME} -B"
    }
  }
}

timeout(time:5, unit:'DAYS') {
  input message:'Approve deployment?'
}

node {
  def os
  fileLoader.withGit('https://ci_map@git.sits.no/git/scm/ao/aurora-pipeline-scripts.git', 'master') {
    os = fileLoader.load('openshift/openshift')
  }

  stage('Deploy to nexus') {
    unstash 'source'
    sh "./mvnw clean deploy"
    junit '**/target/surefire-reports/TEST-*.xml'
  }

  stage('Deploy to Openshift') {
    os.buildVersion('mfp-openshift-referanse-springboot-server', 'openshift-referanse-springboot-server',
        '0.0.1-SNAPSHOT')
  }
}

