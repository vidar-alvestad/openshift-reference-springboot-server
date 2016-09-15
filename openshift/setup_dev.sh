#! /bin/bash

oc delete project mfp-openshift-referanse-springboot-server
sleep 5
oc new-project mfp-openshift-referanse-springboot-server
sleep 1

oc new-app --template=aurora-development-3.0 \
    -p APP_NAME=openshift-referanse-springboot-server \
    -p ARTIFACT_ID=openshift-referanse-springboot-server \
    -p GROUP_ID=ske.aurora.openshift.referanse \
    -p VERSION=0.0.1-SNAPSHOT \
    -p MAX_MEMORY=512M \
    -l app=openshift-referanse-springboot-server

oc start-build openshift-referanse-springboot-server

oc policy add-role-to-user edit system:serviceaccount:mfp-jenkins:jenkins-builder -n mfp-openshift-referanse-springboot-server