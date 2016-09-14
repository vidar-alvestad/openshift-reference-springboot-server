node {
    def os
    fileLoader.withGit('https://ci_map@git.sits.no/git/scm/ao/aurora-pipeline-scripts.git') {
        os = fileLoader.load('openshift/openshift')
    }

    stage('Checkout') {
        checkout scm
    }

    stage('Build') {
       sh "./mvnw clean deploy"
    }

    stage('Generate Reports') {
       step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/**/*.xml'])
    }

    stage('Deploy to Openshift') {
        os.buildVersion('mfp-openshift-referanse-springboot-server', 'openshift-referanse-springboot-server', '0.0.1-SNAPSHOT')
    }
}