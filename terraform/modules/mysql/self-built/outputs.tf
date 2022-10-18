output "db_type" {
  value = "mysql"
}
output "mysql_db_host" {
  value = kubernetes_service.mysql_service.metadata.0.name
}
output "mysql_db_port" {
  value = kubernetes_service.mysql_service.spec.0.port.0.port
}
output "mysql_db_user" {
  value = "root"
}
output "mysql_db_password" {
  value = random_password.password.result
}
