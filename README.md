# Terraform IaC practice

------
基于本地环境的 Terraform IaC 练习。

本案例以简单的微服务模拟应用程序代码，以本地集群模拟实际集群环境，step by step 学习基于 `Terraform`、 `ArgoCD` 的 K8S 集群下搭建自动化、可重复执行、易于管理的不可变基础设施的基本思路和方法。

在掌握方法后，可自行利用云资源进一步学习，以覆盖本案例未涉及到的内容，如：Terraform 的远程状态管理，云服务的 Terraform Provider 等。

## 内容覆盖

- 使用 Kubernetes 构建软件服务
- 使用 Terraform 构建和管理基础设施
- CI/CD 和 GitOps

## 基础环境

- Mac OS
- docker 运行环境

## 模拟需求

### 应用程序信息

模拟应用程序提供了图书下单服务，由一个 Web Application 和两个后端 Service 组成，服务间关系如下：

  ```terraform
  Web Application
      │
      ├───── 图书信息 ───── BookService
      │
      └───── 订单信息 ───── OrderService
  ```

### 部署要求

- 使用IaC实践部署两套环境
  - kind-local
    - 模拟开发环境，运行调试优先，方便开发和验证
    - 所有服务都可以访问
    - 所有服务的API DOC 可以访问
    - 方便开发，可以使用环境变量设定密码
    - 使用本地打包的 image 部署服务
  - kind-stable
    - 模拟UAT环境
    - 只有BFF服务可以访问，后端服务不能直接访问
    - 所有服务的API DOC 可以访问
    - 密码动态生成
    - 只能部署 image registry 里经过验证的image
- 两套环境的出口路由规则

  ```
  /local/api/xxx   -> kind-local 环境的某服务
  /stable/api/xxx  -> kind-stable 环境的某服务
  ```

## 需求分析

- 作为开发团队
  - 编写可靠的业务代码，持续集成
    - 选用 Github Actions，作为持续集成流水线
    - 编写 K8S 配置文件，使应用程序可以在 K8S 平台部署运行
  - 创建微服务使用的数据库
  - 配置不同环境的业务参数（不敏感信息）和依赖服务的认证信息（敏感信息）
- 作为 devops/平台团队
  - 提供平台和基础设施
    - Github，作为代码仓库
    - K8S集群，以部署来自开发团队的应用程序
    - Ingress 控制器，支持出口路由配置
    - ArgoCD，支持持续部署
  - 环境管理
  - 提供接口，支持开发团队完成服务部署
    - 配置管理的代码惯例
    - 数据库创建接口
  - 服务监控与恢复

## 任务清单

本着小步快跑的方式，我们采用任务分解的方式逐步完成上述的需求。

- [ ] 搭建本地K8S集群, [step-1](./docs/step-1.md)
- [ ] 编写K8S配置文件，部署应用程序到集群, [step-2](./docs/step-2.md)
- [ ] 使用 Terraform 管理应用程序所需的两套基础设施, [step-3](./docs/step-3.md)
- [ ] 部署应用程序到两套环境, [step-4](./docs/step-4.md)
- [ ] 创建 Ingress 控制器，配置出口路由, [step-5](./docs/step-5.md)
- [ ] GitOps 持续部署
  - [ ] 使用 Git 管理 K8S配置文件和 Image 版本, [step-6-1](./docs/step-6-1.md)
  - [ ] 部署 ArgoCD，实现自动化部署, [step-6-2](./docs/step-6-2.md)