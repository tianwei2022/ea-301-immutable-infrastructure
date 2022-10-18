#!/usr/bin/env bash

VAR_GIT_ROOT_DIR=$(git rev-parse --show-toplevel)
VAR_CONFIG_FILE="$VAR_GIT_ROOT_DIR/config.properties"

read -p "please enter the image name: " IMAGE_NAME
read -p "please enter the image description: " IMAGE_DESC

source $VAR_CONFIG_FILE
FULL_IMAGE_NAME="ghcr.io/$OWNER_NAME/$IMAGE_NAME:latest"

echo "start to build your image: $FULL_IMAGE_NAME"
echo "..."

docker build \
    --label "org.opencontainers.image.source=$REPO_URL" \
    --label "org.opencontainers.image.description=$IMAGE_DESC" \
    -t $FULL_IMAGE_NAME \
    .

docker push $FULL_IMAGE_NAME