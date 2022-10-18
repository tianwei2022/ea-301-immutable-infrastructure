resource "kubernetes_namespace" "namespace" {
  metadata {
    name = var.namespace
  }
}

module "mysql" {
  source = "../../modules/mysql/self-built"

  namespace = var.namespace
}

