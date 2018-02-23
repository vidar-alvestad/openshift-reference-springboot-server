#!/usr/bin/env groovy
@Library('aurora-pipeline-libraries@feature/AOS-1731')

def props = getDefaultProps()
def systemtest = [
        auroraConfigEnvironment : 'frl1-refapp-utv',
        path : 'src/systemtest',
        applicationUnderTest : "referanse",
        npmScripts : ['test'],
        gatling: [
          appDir : "gatling",
          options: ['-Dgatling.concurrentUsers=2']
        ],
        cucumber: [
          appDir: 'my-directory'
        ],
        webdriver: [
          appDir : 'my-directory'
        ],
        mvnCommands: ['gatling:execute -Dgatling.simulationClass=computerdatabase.BasicSimulation']
      ]
props.testStages = [systemtest]
props.affiliation = 'paas'

node {
  checkoutAndPreparationStage(this, env, props)

  compileStage(this, env, props, { this.echo 'A TEST WRITEOUT'})

  jacocoOrTestStage(this, env, props)
  //jacocoStage(this, env, props)
  //testStage(this, env, props)

  if (props.sonarQube) {
    sonarqubeStage(this, env, props)
  }

  if (props.piTests) {
    piTestStage(this, env, props)
  }

  if ('aurora-nexus' == props.deployTo) {
    deployAuroraNexusStage(this, env, props)
  }

  if ('maven-central' == props.deployTo) {
    deployMavenCentralStage(this, env, props)
  }


  if (props.testStages) {

      def testId = UUID.randomUUID().toString().substring(0, 20)
      echo "TestId $testId"

      buildTempReleaseStage(this, env, testId, props)

      //openshift.performTestStages(props.testStages, props.affiliation, testId, git.getCommitId(), npm, maven, utilities)
      performTestStages(this, env, props, testId)

      retagTempReleaseStage(this, env, testId, props)

  } else if (props.openShiftBuild) {
    //stage('OpenShift Build') {
      //openshift.buildLeveransepakkePom(props.pomPath)
    //}
  } else {
    echo "[INFO] Skipping OpenShift build"
  }


}
