resource "kubernetes_namespace" "namespace" {
  metadata {
    name = var.namespace
  }
}

resource "kubernetes_deployment" "mysql" {


}

resource "kubernetes_service" "mysql_service" {

}


resource "kubernetes_deployment" "mongodb" {
  count = 0
}

resource "kubernetes_service" "mongodb_service" {
  count = 0
}
