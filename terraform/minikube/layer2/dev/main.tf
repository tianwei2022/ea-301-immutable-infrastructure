resource "kubernetes_namespace" "namespace" {
  metadata {
    name = "minikube-dev"
  }
}

module "ratings" {
  source = "../../../../apps/ratings/terraform/minikube-dev"
}
