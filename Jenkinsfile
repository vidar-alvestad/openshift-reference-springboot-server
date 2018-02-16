#!/usr/bin/env groovy

//library identifier: 'aurora-pipeline-libraries@feature/AOS-1731', retriever: modernSCM(
//            [$class: 'GitSCMSource',
//             remote: 'https://git.aurora.skead.no/scm/ao/aurora-pipeline-libraries.git',
//             credentialsId: 'aurora-bitbucket']) _

@Library('aurora-pipeline-libraries@feature/AOS-1731')

def version = 'feature/AOS-1731'

LinkedHashMap<String, Serializable> defaultProps = getDefaultProps()
  Map<String, Object> props = [:]
  props.putAll(defaultProps)



node {
  stage ('print stage'){
    echo env.PATH
    echo currentBuild.toString()
  }

  checkoutAndPreparationStage(this, env, props)
}


private static LinkedHashMap<String, Serializable> getDefaultProps() {
  Map<String, Object> defaultProps = [

      // The name of the Maven tool to use during this build
      mavenVersion             : 'Maven 3',

      // The path of the pom-file that contains the Leveransepakke
      pomPath                  : 'pom.xml',

      // Command used to compile with maven
      mavenBuildCommand        : 'compile',

      // Optional properties used by Maven compile phase
      compileProperties        : "",

      // Optional properties used by Maven test phase
      testProperties           : "",

      // Optional properties used by Maven deploy phase
      deployProperties         : "",

      // Path to custom maven settings file
      mavenSettingsFile        : "",

      // Where to deploy? Possible options are 'aurora-nexus' or 'maven-central'
      deployTo                 : 'aurora-nexus',

      // The Id of the credentials object in Jenkins that contains the username and password for the git service
      // account that will be used when tagging releases.
      credentialsId            : 'aurora-bitbucket',

      // Should code analysis reports be run?
      disableAllReports        : false,

      // Enable checkstyle reports
      checkstyle               : true,

      // Enable jacoco reports
      jacoco                   : true,

      // Enable Cucumber reports
      cucumber                 : false,

      // Enable SonarQube integration?
      sonarQube                : true,

      // SonarQube server
      sonarQubeUrl             : 'https://sonar.aurora.skead.no/',

      // Should PI Tests be run?
      piTests                  : false,

      // Deprecated - use openShiftBuild
      // Are you a library?
      library                  : false,

      // Deprecated - use openShiftBuild
      skipOpenShiftBuild       : false,

      // Start OpenShift image build
      openShiftBuild           : true,

      openShiftBaseImage       : 'flange',

      openShiftBaseImageVersion: '8',

      /*if you have testStages to perform specify them as an array of testStage definitions
      If you have multiple test stages repeat the block below
      Example testStages is
      def systemtest = [
        //the name of the AO environment to use as a template for the env under test
        auroraConfigEnvironment : 'st-refapp',
        //the path to where the systemtest scripts are located
        path : 'src/systemtest',
        //the name of the application under test
        applicationUnderTest : "referanse",
        //what NPM scripts to perform
        npmScripts : ['test']
        gatling: [
          //if you have gatling tests in another directory specify it here I.e if you have a gatling folder specify gatling
          appDir : "gatling"
          options: ['-Dgatling.concurrentUsers=2']
        ],
        cucumber: [
          appDir: 'my-directory'
        ],
        webdriver: [
          appDir : 'my-directory'
        ]

        mvnCommands: ['gatling:execute -Dgatling.simulationClass=computerdatabase.BasicSimulation']
      ]
      */
      testStages               : false,

      // Should documentation be generated?
      docs                     : true,

      // Should Jenkins notify HipChat?
      // Valid options are "started, success, failure"
      notifyHipchat            : "",

      // Which HipChat room to notify
      hipchatRoom              : "",

      // Should Jenkins send mail to developers
      // Valid options are "started, success, failure"
      notifyDevelopers         : "",

      //What version of node to use
      nodeVersion              : 'node-6',

      //Where is the NPM registry to install from
      npmRegistry              : '--registry=https://aurora/npm/repository/npm-all/',

      // Should special build artifacts be saved?
      artifacts                : false
  ]
  defaultProps
}


