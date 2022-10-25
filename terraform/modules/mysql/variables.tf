locals {
  is_selfbuilt = var.platform == "colima" || var.platform == "minikube"
}

variable "platform" {}
variable "environment" {}
variable "namespace" {}
