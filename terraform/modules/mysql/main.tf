module "self_built" {
  count = var.platform == "colima" || var.platform == "minikube" ? 1 : 0

  source = "./self-built"

  namespace = var.namespace
}
