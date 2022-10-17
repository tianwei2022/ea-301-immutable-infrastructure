# ea-301-immutable-infrastructure

## 背景

现有一个电商项目，由一个WEB前端应用程序，两个后端应用程序和一个数据库构成。
材料
https://github.com/tw-devops-community/ea-301-immutable-infrastructure

## 需求

（确定的 大平台的 适合大多数场景的）

Tech Stack
PaaS: Kubernetes - Minikube
IaC: Terraform
CI:  Github Actions
CD: ArgoCD
Minikube -> cluster (2-3)
Application, db -> k8s
docker/helm

## 操作指南

### 准备环境

Kubernetes (minikube) on MacOS

Fork 当前代码仓库

#### 本地环境

`./scripts/setup_macos` 安装必要工具和软件

#### 示例代码

