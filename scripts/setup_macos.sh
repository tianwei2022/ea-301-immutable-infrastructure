#!/usr/bin/env bash

echo "install skaffold"

FILE=/usr/local/bin/skaffold
if test -f "$FILE"; then
  echo "$FILE exists."
else
  curl -Lo skaffold https://storage.googleapis.com/skaffold/releases/latest/skaffold-darwin-amd64
  sudo install skaffold /usr/local/bin/skaffold
  rm skaffold
fi

echo "install kustomize"

FILE=/usr/local/bin/kustomize
if test -f "$FILE"; then
  echo "$FILE exists."
else
  curl -s "https://raw.githubusercontent.com/kubernetes-sigs/kustomize/master/hack/install_kustomize.sh" | bash
  sudo install kustomize /usr/local/bin/kustomize
  rm kustomize
fi

echo "install terraform"

FILE=/usr/local/bin/terraform
if test -f "$FILE"; then
  echo "$FILE exists."
else
  brew tap hashicorp/tap
  brew install hashicorp/tap/terraform
fi

echo "install kubectl"

FILE=/usr/local/bin/kubectl
if test -f "$FILE"; then
  echo "$FILE exists."
else
  brew install kubectl
fi