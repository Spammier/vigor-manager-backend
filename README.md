# 智能学习辅助系统 - 后端 (zxvf-backend)

这是智能学习辅助系统的后端服务。

## 主要功能

*   用户认证 (登录/注册/修改密码) - 基于 JWT
*   班级信息管理 (CRUD)
*   学员信息管理 (CRUD)
*   员工信息管理 (CRUD)
*   (可能包含文件上传等其他功能)

## 技术栈

*   Java 21
*   Spring Boot 3.3.x
*   Spring Security (JWT)
*   MyBatis 3
*   MySQL
*   Maven
*   PageHelper (分页插件)
*   Lombok
*   jjwt 0.9.1

## 环境准备

*   JDK 21 或更高版本
*   Maven 3.6 或更高版本
*   MySQL 8.0 或更高版本

## 配置

主要的配置文件是 `src/main/resources/application.properties`。

你需要配置以下关键信息：

1.  **数据库连接:**
    ```properties
    spring.datasource.url=jdbc:mysql://<your_db_host>:<your_db_port>/<your_database_name>?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8...
    spring.datasource.username=<your_db_username>
    spring.datasource.password=<your_db_password>
    ```
    请确保 `<your_db_host>`, `<your_db_port>`, `<your_database_name>`, `<your_db_username>`, `<your_db_password>` 已替换为你的实际数据库信息。

2.  **JWT 密钥:**
    ```properties
    # !!! 请务必替换为一个你自己生成的、安全的、Base64编码的密钥 !!!
    # !!! 下面的示例值仅供测试，不可用于生产环境 !!!
    jwt.secret=U1ZjdlBuUmlkMmRvWWtWbGRFNUNkM05vZFZwellXaGtNMVZyVXpSV2FrWnZlR1ZzTjJKcFl6TmFla1phVjFKdFRWWnZNV1E9 
    jwt.expirationMs=86400000 # Token 过期时间 (毫秒)，默认 24 小时
    ```
    **警告:** `jwt.secret` 的值**必须**在部署前替换为一个你自己生成的、长而随机的、经过 Base64 编码的安全密钥！你可以使用在线工具或代码生成。

3.  **(可选) MinIO 配置:** 如果使用了文件上传功能，请配置 MinIO 相关信息。

## 数据库初始化

项目的数据库表结构定义在项目的 SQL 文件中（或根据代码中的 `CREATE TABLE` 语句手动创建）：

*   `emp` (员工表，包含用户名密码)
*   `emp_expr` (员工经历表，如果存在)
*   `dept` (部门表，如果存在)
*   `class_info` (班级信息表)
*   `student_info` (学员信息表)

请确保在运行应用前已创建这些表。

## 运行应用

1.  **克隆项目** (如果需要)
2.  **配置 `application.properties`** 文件，特别是数据库连接和 JWT 密钥。
3.  **构建项目:** 在项目根目录下打开终端，运行：
    ```bash
    mvn clean package
    ```
4.  **运行应用:**
    ```bash
    java -jar target/hello-0.0.1-SNAPSHOT.jar
    ```
    应用默认启动在 `8080` 端口。

## API 文档

详细的 API 接口文档请参见项目根目录下的 [API_DOCUMENTATION.md](API_DOCUMENTATION.md) 文件。 