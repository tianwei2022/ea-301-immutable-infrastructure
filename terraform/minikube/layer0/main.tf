
resource "shell_script" "github_repository" {
  lifecycle_commands {
    create = "minikube start"
    read   = "minikube staus"
    update = "minikube start"
    delete = "minikube stop"
  }
}