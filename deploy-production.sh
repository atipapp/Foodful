#!/bin/bash

set -e

cd backend/dinner/dinner-core
docker build -t eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_DINNER}:$TRAVIS_COMMIT .
cd ../../..

cd backend/auth/auth-core
docker build -t eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_AUTH}:$TRAVIS_COMMIT .
cd ../../..

cd backend/gateway
docker build -t eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_GATEWAY}:$TRAVIS_COMMIT .
cd ../..

cd backend/user/user-core
docker build -t eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_USER}:$TRAVIS_COMMIT .
cd ../../..

cd frontend/
docker build -t eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_FRONTEND}:$TRAVIS_COMMIT .
cd ..

echo $GCLOUD_SERVICE_KEY_PRD | base64 --decode -i > ${HOME}/gcloud-service-key.json
gcloud auth activate-service-account --key-file ${HOME}/gcloud-service-key.json

gcloud --quiet config set project $PROJECT_NAME_PRD
gcloud --quiet config set container/cluster $CLUSTER_NAME_PRD
gcloud --quiet config set compute/zone ${CLOUDSDK_COMPUTE_ZONE}
gcloud --quiet container clusters get-credentials $CLUSTER_NAME_PRD


docker push eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_DINNER}
docker push eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_AUTH}
docker push eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_GATEWAY}
docker push eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_USER}
docker push eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_FRONTEND}

yes | gcloud beta container images add-tag eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_DINNER}:$TRAVIS_COMMIT eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_DINNER}:latest
yes | gcloud beta container images add-tag eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_AUTH}:$TRAVIS_COMMIT eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_AUTH}:latest
yes | gcloud beta container images add-tag eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_GATEWAY}:$TRAVIS_COMMIT eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_GATEWAY}:latest
yes | gcloud beta container images add-tag eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_USER}:$TRAVIS_COMMIT eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_USER}:latest
yes | gcloud beta container images add-tag eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_FRONTEND}:$TRAVIS_COMMIT eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_FRONTEND}:latest

kubectl config view
kubectl config current-context

kubectl apply -f $PWD/deployments/production --overwrite=true

kubectl set image deployment/${KUBE_DEPLOYMENT_NAME_DINNER} ${KUBE_DEPLOYMENT_CONTAINER_NAME_DINNER}=eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_DINNER}:$TRAVIS_COMMIT
kubectl set image deployment/${KUBE_DEPLOYMENT_NAME_AUTH} ${KUBE_DEPLOYMENT_CONTAINER_NAME_AUTH}=eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_AUTH}:$TRAVIS_COMMIT
kubectl set image deployment/${KUBE_DEPLOYMENT_NAME_GATEWAY} ${KUBE_DEPLOYMENT_CONTAINER_NAME_GATEWAY}=eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_GATEWAY}:$TRAVIS_COMMIT
kubectl set image deployment/${KUBE_DEPLOYMENT_NAME_USER} ${KUBE_DEPLOYMENT_CONTAINER_NAME_USER}=eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_USER}:$TRAVIS_COMMIT
kubectl set image deployment/${KUBE_DEPLOYMENT_NAME_FRONTEND} ${KUBE_DEPLOYMENT_CONTAINER_NAME_FRONTEND}=eu.gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME_FRONTEND}:$TRAVIS_COMMIT
