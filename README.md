# ea-301-immutable-infrastructure

## 背景

![bookinginfo](https://istio.io/latest/docs/examples/bookinfo/noistio.svg)

## 需求

Tech Stack

- Kubernetes
- Terraform
- Github Actions
- ArgoCD
- Kustomize
- Java/Node/Ruby/Python ...

## 使用说明

本教程希望基于案例，通过学习 Terraform、ArgoCD 等技术来掌握 IaC、CI/CD 的实现方式，了解 I0 阶段如何搭建自动化、可重复执行、易于管理的不可变基础设施

此处使用 Minikube 搭建的本地集群来模拟线上集群，部分服务采用集群内搭建（如 Mongodb、Mysql、Jenkins）

### 0. 准备仓库

- Github Fork 当前代码仓库
- 配置 Github Token, `export CR_PAT="ghp_xxxxxxxxx"`
  - <https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry>
  - <https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token>

### 1. 开发环境

- `./scripts/setup_macos` 安装必要工具和软件
- 修改 `config.properties` 内容

### 2. 基础设施

#### 启动集群

通过 terraform 启动集群 和 安装必要软件

layer0，layer1 

### 3. 应用服务

#### 使用 kustomize 管理配置信息

- 创建 manifest 文件

### 4. CI/CD

#### CI

Github Actions

- image registry: Github Packages
- manifest repo: Github Repository

#### CD

##### terraform

- 创建 namespace
- 部署 mysql (用 terraform 控制 k8s 文件)
  - 将连接信息暴露成 secrets

##### argocd

通过 argocd 管理应用服务

- 进入 argocd，sync

## 新的需求

### 创建新的环境

qa 环境，不同的环境变量

### 使用新的依赖

stg 环境， mongo，不同的资源数量

#### terragrunt

- 管理多环境

### 使用云平台 （仅作为 demo）

prod 环境，使用 AWS/aliyun 作为底层 provider
