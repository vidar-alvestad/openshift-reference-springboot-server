#!/usr/bin/env groovy

//@Library('git.aurora.skead.no/scm/ao/aurora-pipeline-libraries@master') //virker ikke
@Library('aurora-pipeline-libraries@feature/AOS-1731') //virker - "aurora-pipeline-libraries" er navn på definert shared lib i Jenkins
import no.skatteetaten.aurora.jenkins.utils.Maven

echo 'testmessage'


pipeline {
  agent any
  stages {
     //checkoutAndPreparation()
     //compile()

     stage ('prep') {
        steps {
          echo 'inside stage prep'
        }

     }
     stage ('compile') {
        Maven.compile(null)
     }
  }

}