module "self_built" {
  count = local.is_selfbuilt ? 1 : 0

  source = "./self-built"

  namespace = var.namespace
}
