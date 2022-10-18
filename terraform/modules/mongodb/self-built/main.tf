resource "kubernetes_deployment" "mongodb" {
  metadata {
    name      = "mongodb-v1"
    namespace = var.namespace
    labels = {
      managed_by = "terraform"
      app        = "mongodb"
      version    = "v1"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app     = "mongodb"
        version = "v1"
      }
    }

    template {
      metadata {
        labels = {
          app     = "mongodb"
          version = "v1"
        }
      }

      spec {
        container {
          name  = "mongodb"
          image = "ghcr.io/tw-devops-community/mongodb:latest"

          port {
            container_port = 27017
          }

          volume_mount {
            name      = "data-db"
            mountPath = "/data/db"
          }
        }

        volume {
          name     = "data-db"
          emptyDir = {}
        }
      }
    }
  }
}

resource "kubernetes_service" "mongodb_service" {
  metadata {
    name      = "mongodb"
    namespace = var.namespace
    labels = {
      managed_by = "terraform"
      app        = "mongodb"
      service    = "mongodb"
    }
  }

  spec {
    port {
      port = 27017
      name = "mongodb"
    }

    selector {
      app = "mongodb"
    }
  }
}
