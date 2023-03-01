locals {
  namespace   = "kind-local"
  platform    = "kind"
  environment = "local"
}

variable "db_password" {}


# 使用 provider - kubernetes_namespace 创建名为 local.namespece 的命名空间
# Task 3.1
resource "kubernetes_namespace" "ns" {
  metadata {
    name = local.namespace
  }
}


module "mysqldb" {
  depends_on = [kubernetes_namespace.ns]  # Task 3.2

  source = "../../../module/mysql"

  platform    = local.platform
  namespace   = local.namespace
  db_password = var.db_password
}

module "nginx-ingress" {
  depends_on = [kubernetes_namespace.namespace]

  source = "./ingress"

  environment = local.environment
  namespace   = local.namespace
}

module "book-service" {
  depends_on = [module.mysqldb]

  source    = "../../../../apps/book-service/terraform/kind-local"
  namespace = local.namespace

  db_host     = module.mysqldb.mysql_db_host
  db_password = module.mysqldb.mysql_db_password
  db_port = module.mysqldb.mysql_db_port
  db_user = module.mysqldb.mysql_db_user
}

# module "order-service" {
#   depends_on = [module.mysqldb]

#   source = "../../../../apps/order-service/terraform/kind-local"
#   namespace = local.namespace
  
#   db_host = module.mysqldb.mysql_db_host
#   db_password = module.mysqldb.mysql_db_password
#   db_port = module.mysqldb.mysql_db_port
#   db_user = module.mysqldb.mysql_db_user
# }

# module "web-app" {
#   source = "../../../../apps/web-app/terraform/kind-local"
#   namespace = local.namespace
# }
