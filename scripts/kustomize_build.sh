#!/usr/bin/env bash

VAR_GIT_ROOT_DIR=$(git rev-parse --show-toplevel)

read -p "please enter the service name: " SERVICE_NAME
read -p "please enter the platform name: " PLATFORM
read -p "please enter the env name: " ENV
read -p "please enter the image name: " IMAGE_NAME

sh ${VAR_GIT_ROOT_DIR}/scripts/kustomize_build_ci.sh $SERVICE_NAME $PLATFORM $ENV $IMAGE_NAME