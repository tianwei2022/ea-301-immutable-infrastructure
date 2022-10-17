# ea-301-immutable-infrastructure

## 背景

![bookinginfo](https://istio.io/latest/docs/examples/bookinfo/noistio.svg)

## 需求

Tech Stack

- Kubernetes
- Terraform
- Github Actions + Github Packages
- ArgoCD
- Kustomize
- Java/Node/Ruby/Python ...

## 使用说明

本教程希望基于案例，通过学习 Terraform、ArgoCD 等技术来掌握 IaC、CI/CD 的实现方式，了解 I0 阶段如何搭建自动化、可重复执行、易于管理的不可变基础设施

此处使用 Minikube 搭建的本地集群来模拟线上集群，部分服务采用集群内搭建（如 Mongodb、Mysql、Jenkins）

### 开发环境

- Fork 当前代码仓库 `ea-301-<your-repo-name>`
- `./scripts/setup_macos` 安装必要工具和软件
- `minikube start` 启动集群

### 应用运行环境

#### 使用 terraform 管理依赖服务

- 创建 namespace
- 部署 mysql (用 terraform 控制 k8s 文件)
  - 将连接信息暴露成 secrets

#### 使用 kustomize 生成 manifest

- 创建 manifest 文件

### 基础设施

#### CI

- Github Actions + Github Packages
  - 注册 token ...
- 通过 skaffold 控制编译范围

#### CD

- ArgoCD
  - terraform + helm 安装

## 新的需求

### 创建新的环境

qa 环境，不同的环境变量

### 使用新的依赖

stg 环境， mongo，不同的资源数量

#### terragrunt

- 管理多环境

### 使用云平台 （仅作为 demo）

prod 环境，使用 AWS/aliyun 作为底层 provider
