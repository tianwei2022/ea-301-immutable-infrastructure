# apiVersion: argoproj.io/v1alpha1
# kind: Application
# metadata:
#   namespace: tools
#   name: $s
#   labels:
#     type: {please replace to service or app}
# spec:
#   destination:
#     name: ''
#     namespace: {your new ENV namespace}
#     server: 'https://kubernetes.default.svc'
#   source:
#     path: new_env/your_folder/$s
#     repoURL: >-
#       https://github.git
#     targetRevision: main
#   project: your_new_env_project
#   syncPolicy:
#     # automated: {} //this is optional, if set that means enable automated
#     syncOptions:
#       - CreateNamespace=false
#       - PrunePropagationPolicy=background