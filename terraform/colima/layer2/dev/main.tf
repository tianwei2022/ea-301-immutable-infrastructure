resource "kubernetes_namespace" "namespace" {
  metadata {
    name = "colima-dev"
  }
}

module "ratings" {
  source = "../../../../apps/ratings/terraform/colima-dev"
}
