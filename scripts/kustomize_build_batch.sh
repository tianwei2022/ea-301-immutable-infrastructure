#!/usr/bin/env bash

VAR_GIT_ROOT_DIR=$(git rev-parse --show-toplevel)

SERVICE_NAME=$1
OVERLAY_NAME="$2-$3"
IMAGE_NAME=$4

OVERLAY_PATH="$VAR_GIT_ROOT_DIR/apps/$SERVICE_NAME/k8s/overlays/$OVERLAY_NAME"
MANIFEST_PATH="$VAR_GIT_ROOT_DIR/build/$OVERLAY_NAME/$SERVICE_NAME"

mkdir -p "$MANIFEST_PATH"

export SERVICE_NAME=$SERVICE_NAME
export OVERLAY_NAME=$OVERLAY_NAME
export IMAGE_NAME=$IMAGE_NAME

kustomize build "$OVERLAY_PATH" | envsubst >| "$MANIFEST_PATH"/k8s.yaml

#!/usr/bin/env bash

service=('details' 'productpage' 'ratings' 'reviews')
images=(
  'test'
  'test'
  'test'
  'test'
)
platform=('minikube' 'colima')
env=('dev')

for i in "${!service[@]}"; do
  s=${service[i]}
  image=${images[i]}

  for p in "${platform[@]}"; do
    for e in "${env[@]}"; do
      echo "$s" "$p" "$e" "$image"
    done
  done
done
