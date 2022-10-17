#!/usr/bin/env bash

echo "install minikube"

FILE=/usr/local/bin/minikube
if test -f "$FILE"; then
    echo "$FILE exists."
else
    brew install minikube
fi

minikube start --memory=8192 --cpus=2
