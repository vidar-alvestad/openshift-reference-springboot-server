#!/usr/bin/env groovy
@Library('aurora-pipeline-libraries@feature/AOS-1731')

def props = getDefaultProps()
def systemtest = [
        auroraConfigEnvironment : 'frl1-refapp-utv',
        path : 'src/systemtest',
        applicationUnderTest : "referanse",
        npmScripts : ['test'],
        //gatling: [
          //appDir : "gatling",
          //options: ['-Dgatling.concurrentUsers=2']
        //],
        //cucumber: [
          //appDir: 'my-directory'
        //],
        //webdriver: [
          //appDir : 'my-directory'
        //],
        //mvnCommands: ['gatling:execute -Dgatling.simulationClass=computerdatabase.BasicSimulation']
      ]
props.testStages = [systemtest]
props.affiliation = 'paas'

node {
  checkoutAndPreparationStage(props)

  compileStage(props, { this.echo 'A TEST WRITEOUT'})

  jacocoOrTestStage(props)
  //jacocoStage(props)
  //testStage(props)

  if (props.sonarQube) {
    sonarqubeStage(props)
  }

  if (props.piTests) {
    piTestStage(props)
  }

  if ('aurora-nexus' == props.deployTo) {
    deployAuroraNexusStage(props)
  }

  if ('maven-central' == props.deployTo) {
    deployMavenCentralStage(props)
  }


  if (props.testStages) {

      def testId = UUID.randomUUID().toString().substring(0, 20)
      echo "TestId $testId"

      buildTempReleaseStage(testId, props)

      //openshift.performTestStages(props.testStages, props.affiliation, testId, git.getCommitId(), npm, maven, utilities)
      performTestStages(props, testId)

      retagTempReleaseStage(testId, props)

  } else if (props.openShiftBuild) {
      openshiftBuildStage(props)
  } else {
    echo "[INFO] Skipping OpenShift build"
  }


}
