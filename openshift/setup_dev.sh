#! /bin/bash

oc delete project mfp-openshift-referanse-springboot-server
sleep 5
oc new-project mfp-openshift-referanse-springboot-server
sleep 1

oc new-app --template=aurora-deploy-3.0 \
    -p APP_NAME=demo \
    -p AFFILIATION=aurora \
    -p DOCKER_GROUP=ske_aurora_openshift_referanse \
    -p DOCKER_NAME=openshift-referanse-springboot-server \
    -p TAG=dev \
    -p DATABASE=referanseapp:3f9d8558-0fd2-488e-8f79-fbbdf62b04ff \
    -p MAX_MEMORY=512M \
    -p PROMETHEUS_ENABLED=true