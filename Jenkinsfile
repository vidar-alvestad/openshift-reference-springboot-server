node {
    stage('Checkout') {
        checkout scm
    }

    stage('Build') {
       sh "./mvnw clean build"
    }

    stage('Generate Reports') {
       step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/**/*.xml'])
    }
}