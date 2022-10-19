
resource "shell_script" "cluster" {
  lifecycle_commands {
    create = "minikube start"
    read   = "minikube staus"
    update = "minikube start"
    delete = "minikube stop"
  }
}