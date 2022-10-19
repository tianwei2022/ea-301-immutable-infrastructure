#!/usr/bin/env bash

VAR_GIT_ROOT_DIR=$(git rev-parse --show-toplevel)

read -p "please enter the service name: " SERVICE_NAME
read -p "please enter the overlay name: " OVERLAY_NAME
read -p "please enter the image name: " IMAGE_NAME


OVERLAY_PATH="$VAR_GIT_ROOT_DIR/apps/$SERVICE_NAME/k8s/overlays/$OVERLAY_NAME"
MANIFEST_PATH="$VAR_GIT_ROOT_DIR/build/$OVERLAY_NAME/$SERVICE_NAME"

mkdir -p $MANIFEST_PATH

export SERVICE_NAME=$SERVICE_NAME
export OVERLAY_NAME=$OVERLAY_NAME
export IMAGE_NAME=$IMAGE_NAME

kustomize build $OVERLAY_PATH | envsubst >| $MANIFEST_PATH/k8s.yaml