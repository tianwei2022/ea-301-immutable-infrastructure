
resource "shell_script" "cluster" {
  lifecycle_commands {
    create = "colima start --kubernetes"
    read   = "colima status"
    update = "colima start --kubernetes"
    delete = "colima stop"
  }
}