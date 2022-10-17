#!/usr/bin/env bash

REPO_URL=https://github.com/anddd7/ea-301-anddd7
OWNER_NAME=anddd7
IMAGE_NAME=details

docker build \
    --label "org.opencontainers.image.source=${REPO_URL}" \
    --label "org.opencontainers.image.description=123" \
    -t ghcr.io/${OWNER_NAME}/${IMAGE_NAME}:latest \
    .

docker push ghcr.io/${OWNER_NAME}/${IMAGE_NAME}:latest