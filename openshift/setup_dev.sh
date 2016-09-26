#! /bin/bash

oc delete project mfp-openshift-referanse-springboot-server
sleep 5
oc new-project mfp-openshift-referanse-springboot-server
sleep 1

oc new-app --template=aurora-development-3.0 \
    -p APP_NAME=springboot-server \
    -p ARTIFACT_ID=openshift-referanse-springboot-server \
    -p GROUP_ID=ske.aurora.openshift.referanse \
    -p VERSION=0.0.1-SNAPSHOT \
    -p MAX_MEMORY=512M \
    -p PROMETHEUS_ENABLED=true \
    -p DATABASE=referanseapp:3f9d8558-0fd2-488e-8f79-fbbdf62b04ff \
    -l app=springboot-server

oc start-build springboot-server

oc policy add-role-to-user edit system:serviceaccount:mfp-jenkins:jenkins-builder -n mfp-openshift-referanse-springboot-server