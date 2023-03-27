variable "argocd_namespace" {
  type        = string
  description = "The name of the target ArgoCD Namespace"
}
variable "destination_namespace" {
  type        = string
  description = ""
}
variable "repo_url" {
  type        = string
  description = "Source of the Helm application manifests"
}
variable "target_revision" {
  type        = string
  description = "Revision of the Helm application manifests to use"
  default     = ""
}
variable "path" {
  type        = string
  description = ""
  default     = ""
}
variable "name" {
  type        = string
  description = "The name of this application"
}
variable "project" {
  type        = string
  description = "The project that this ArgoCD application will be placed into."
}
variable "destination_server" {
  type        = string
  description = ""
  default     = "https://kubernetes.default.svc"
}
variable "automated_prune" {
  type        = bool
  description = "Specifies if resources should be pruned during auto-syncing"
  default     = false
}
variable "automated_self_heal" {
  type        = bool
  description = "Specifies if partial app sync should be executed when resources are changed only in target Kubernetes cluster and no git change detected"
  default     = false
}
variable "sync_options" {
  type        = list(string)
  description = "A list of sync options to apply to the application"
  default     = []
}
variable "sync_option_validate" {
  type        = bool
  description = "disables resource validation (equivalent to 'kubectl apply --validate=true')"
  default     = false
}
variable "sync_option_create_namespace" {
  type        = bool
  description = "Namespace Auto-Creation ensures that namespace specified as the application destination exists in the destination cluster."
  default     = true
}
variable "retry_limit" {
  type        = number
  description = "Number of failed sync attempt retries; unlimited number of attempts if less than 0"
  default     = 5
}
variable "retry_backoff_duration" {
  type        = string
  description = "The amount to back off. Default unit is seconds, but could also be a duration (e.g. `2m`, `1h`)"
  default     = "5s"
}
variable "retry_backoff_factor" {
  type        = number
  description = "A factor to multiply the base duration after each failed retry"
  default     = 2
}
variable "retry_backoff_max_duration" {
  type        = string
  description = "The maximum amount of time allowed for the backoff strategy"
  default     = "3m"
}
variable "ignore_differences" {
  type        = list(object({ kind : string, group : string, name : string, jsonPointers : list(string) }))
  description = "Ignore differences at the specified json pointers"
  default     = []
}
variable "labels" {
  type        = map(string)
  description = ""
  default     = {}
}