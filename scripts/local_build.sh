#!/usr/bin/env bash

if [ -z "$REPO_URL" ]; then
  read -rp "please enter the url of repo: " REPO_URL
  export REPO_URL=$REPO_URL
fi

if [ -z "$OWNER_NAME" ]; then
  read -rp "please enter the username: " OWNER_NAME
  export OWNER_NAME=$OWNER_NAME
fi

VAR_GIT_ROOT_DIR=$(git rev-parse --show-toplevel)
VAR_GIT_REVISION=$(git rev-parse --verify --short HEAD 2>/dev/null)

read -rp "please enter the service name: " SERVICE_NAME
read -rp "please enter the service description: " SERVICE_DESC

#-----------------------------------------------------------------------------------------

IMAGE_NAME="ghcr.io/$OWNER_NAME/$SERVICE_NAME-locally:$VAR_GIT_REVISION"

echo "start to build your image: $IMAGE_NAME"

echo "$CR_PAT" | docker login ghcr.io -u "$OWNER_NAME" --password-stdin

docker build \
  --label "org.opencontainers.image.source=$REPO_URL" \
  --label "org.opencontainers.image.description=$SERVICE_DESC" \
  -t "$IMAGE_NAME" \
  .

docker push "$IMAGE_NAME"

#-----------------------------------------------------------------------------------------

echo "..."

read -rp "please enter the platform name, e.g. minikube, colima: " PLATFORM
read -rp "please enter the env name, e.g. dev, qa, stg, prod: " ENV

echo "start to build your kustomize: $SERVICE_NAME"

sh "$VAR_GIT_ROOT_DIR"/scripts/kustomize_build_ci.sh "$SERVICE_NAME" "$PLATFORM" "$ENV" "$IMAGE_NAME"
