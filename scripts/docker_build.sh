#!/usr/bin/env bash

VAR_GIT_ROOT_DIR=$(git rev-parse --show-toplevel)
VAR_GIT_REVISION=$(git rev-parse --verify --short HEAD 2>/dev/null)
VAR_CONFIG_FILE="$VAR_GIT_ROOT_DIR/config.properties"

read -p "please enter the service name: " SERVICE_NAME
read -p "please enter the service description: " SERVICE_DESC

source $VAR_CONFIG_FILE
IMAGE_NAME="ghcr.io/$OWNER_NAME/$SERVICE_NAME:$VAR_GIT_REVISION"

echo "start to build your image: $IMAGE_NAME"
echo "..."

docker build \
    --label "org.opencontainers.image.source=$REPO_URL" \
    --label "org.opencontainers.image.description=$SERVICE_DESC" \
    -t $IMAGE_NAME \
    .

docker push $IMAGE_NAME