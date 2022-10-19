resource "kubernetes_namespace" "namespace" {
  metadata {
    name = var.namespace
  }
}

module "mysql" {
  source = "../../modules/mysql/self-built"

  namespace = var.namespace
}

resource "kubernetes_config_map" "ratings" {
  metadata {
    name      = "ratings-terraform"
    namespace = var.namespace
  }

  data = {
    DB_TYPE           = module.mysql.db_type
    MYSQL_DB_HOST     = module.mysql.mysql_db_host
    MYSQL_DB_PORT     = module.mysql.mysql_db_port
    MYSQL_DB_USER     = module.mysql.mysql_db_user
    MYSQL_DB_PASSWORD = module.mysql.mysql_db_password
    # MONGO_DB_URL      = "mongodb://mongodb:27017/test"
  }
}
