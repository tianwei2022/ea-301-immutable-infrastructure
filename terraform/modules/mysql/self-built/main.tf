
resource "random_password" "password" {
  length = 16
}

resource "kubernetes_secret" "database_secret" {
  metadata {
    name      = "mysql-credentials"
    namespace = var.namespace
  }

  data = {
    rootpasswd = random_password.password.result
  }
}

resource "kubernetes_deployment" "mysql" {
  metadata {
    name      = "mysql"
    namespace = var.namespace
    labels = {
      managed_by = "terraform"
      app        = "mysqldb"
      version    = "v1"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app     = "mysqldb"
        version = "v1"
      }
    }

    template {
      metadata {
        labels = {
          app     = "mysqldb"
          version = "v1"
        }
      }

      spec {
        container {
          name  = "mysqldb"
          image = "ghcr.io/anddd7/mysql:latest"

          port {
            container_port = 3306
          }

          env {
            name = "MYSQL_ROOT_PASSWORD"

            value_from {
              secret_key_ref {
                name = "mysql-credentials"
                key  = "rootpasswd"
              }
            }
          }

          args = ["--default-authentication-plugin", "mysql_native_password"]

          volume_mount {
            name       = "var-lib-mysql"
            mount_path = "/var/lib/mysql"
          }
        }

        volume {
          name = "var-lib-mysql"

          empty_dir {
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "mysql_service" {
  metadata {
    name      = "mysqldb"
    namespace = var.namespace
    labels = {
      managed_by = "terraform"
      app        = "mysqldb"
      service    = "mysqldb"
    }
  }

  spec {
    port {
      port = 3306
      name = "tcp"
    }

    selector = {
      app = "mysqldb"
    }
  }
}
