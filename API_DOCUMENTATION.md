# 智能学习辅助系统 - 后端 API 文档 (完整版)

## 1. 简介

本文档描述了智能学习辅助系统后端提供的 RESTful API 接口。

*   **基础 URL:** `http://localhost:8080/api` (本地开发环境) - **注意:** 部分旧接口可能未使用 `/api` 前缀，文档中会标明。
*   **认证方式:** JWT (JSON Web Token)。除明确标明无需认证的接口外，所有接口都需要在请求的 `Authorization` Header 中传递 `Bearer <YOUR_JWT_TOKEN>`。Token 在登录成功后获取。
*   **数据格式:** 请求体和响应体主要使用 `application/json` 格式。文件上传使用 `multipart/form-data`。
*   **标准响应结构 (`Result<T>`):**
    ```json
    {
        "code": 0, // 0 表示成功，非 0 (通常为 1) 表示失败
        "msg": "操作成功", // 描述信息
        "data": {} // 实际返回的数据 (可能是对象、列表、字符串或 null)
    }
    ```

## 2. 认证与员工基础接口 (`/api`)

### 2.1 用户登录

*   **接口:** `POST /api/login`
*   **描述:** 用户使用用户名和密码登录，成功后返回包含 JWT 的 data 对象。
*   **认证:** 无需认证。
*   **请求体 (JSON):** (`Emp` 对象或类似结构)
    ```json
    {
        "username": "admin",
        "password": "admin"
    }
    ```
*   **成功响应 (200 OK):**
    ```json
    {
        "code": 0,
        "msg": "操作成功",
        "data": {
            "token": "eyJhbGciOiJIUzUxMiJ9..."
        }
    }
    ```
*   **失败响应:** `{ "code": 1, "msg": "用户名或密码错误", "data": null }`

### 2.2 用户注册

*   **接口:** `POST /api/register`
*   **描述:** 注册新员工。
*   **认证:** 无需认证。
*   **请求体 (JSON):** (`Emp` 对象，需包含数据库 `emp` 表定义的 `NOT NULL` 且无默认值的字段)
    ```json
    {
        "username": "newuser",
        "password": "newpassword",
        "name": "新用户",
        "phone": "13012345678", // 根据数据库约束填写
        "gender": 1, // 假设 1 男 2 女
        "deptId": 1, // 确保部门存在
        "hireDate": "2024-01-01"
        // ... 其他必填字段
    }
    ```
*   **成功响应 (200 OK):** `{ "code": 0, "msg": "操作成功", "data": null }`
*   **失败响应:** `{ "code": 1, "msg": "用户名已存在" | "学号已存在" | "手机号格式错误" | "必填字段不能为空", "data": null }`

### 2.3 修改密码

*   **接口:** `PUT /api/password`
*   **描述:** 修改当前登录用户的密码。
*   **认证:** **需要 JWT 认证** (`Authorization: Bearer <token>`)。
*   **请求体 (JSON):** (`PasswordChangeRequest` 对象)
    ```json
    {
        "oldPassword": "current_password",
        "newPassword": "new_secure_password"
    }
    ```
*   **成功响应 (200 OK):** `{ "code": 0, "msg": "密码修改成功", "data": null }`
*   **失败响应:** `{ "code": 1, "msg": "旧密码错误" | "无法获取当前用户信息", "data": null }`

## 3. 员工管理接口 (`/api/emps`)

**认证:** 所有员工管理接口都需要 JWT 认证。

### 3.1 查询员工列表 (分页/条件)

*   **接口:** `GET /api/emps`
*   **查询参数:**
    *   `page` (Integer, 可选, 默认 1)
    *   `pageSize` (Integer, 可选, 默认 10)
    *   `name` (String, 可选): 姓名 (模糊查询)
    *   `gender` (Integer, 可选): 性别
    *   `begin` (Date, 可选, 格式 yyyy-MM-dd): 入职日期开始
    *   `end` (Date, 可选, 格式 yyyy-MM-dd): 入职日期结束
*   **成功响应 (200 OK):** (`Result<PageResult<Emp>>`)
    ```json
    { "code": 0, "msg": "操作成功", "data": { "total": ..., "rows": [ ... ] } }
    ```

### 3.2 查询全部员工 (无分页)

*   **接口:** `GET /api/emps/list`
*   **成功响应 (200 OK):** (`Result<List<Emp>>`)
    ```json
    { "code": 0, "msg": "操作成功", "data": [ ... ] }
    ```

### 3.3 根据 ID 查询员工

*   **接口:** `GET /api/emps/{id}`
*   **路径参数:** `{id}` (Integer): 员工 ID。
*   **成功响应 (200 OK):** (`Result<Emp>`)
*   **失败响应:** `{ "code": 1, "msg": "员工不存在", "data": null }` (取决于业务逻辑)

### 3.4 新增员工

*   **接口:** `POST /api/emps`
*   **描述:** 注意，此接口可能与 `/api/register` 功能重叠，请确认业务逻辑。此接口通常用于管理员添加员工。
*   **请求体 (JSON):** (`Emp` 对象，注意必填项和唯一约束)
*   **成功响应 (200 OK):** (标准成功响应)
*   **失败响应:** (类似注册接口的错误)

### 3.5 更新员工

*   **接口:** `PUT /api/emps`
*   **请求体 (JSON):** (`Emp` 对象，**必须包含 `id`**)
*   **成功响应 (200 OK):** (标准成功响应)
*   **失败响应:** `{ "code": 1, "msg": "更新时必须提供员工ID", "data": null }` 或其他约束错误。

### 3.6 批量删除员工

*   **接口:** `DELETE /api/emps`
*   **查询参数:** `ids` (String): 逗号分隔的员工 ID 列表 (e.g., `?ids=1,2,3`)
*   **成功响应 (200 OK):** (标准成功响应)

## 4. 部门管理接口 (`/api/depts`)

**认证:** 所有部门管理接口都需要 JWT 认证。

### 4.1 查询部门列表

*   **接口:** `GET /api/depts`
*   **成功响应 (200 OK):** (`Result<PageResult<Dept>>`)
    ```json
    { 
        "code": 0, 
        "msg": "操作成功", 
        "data": { 
            "total": 5,
            "rows": [
                { "id": 1, "name": "学工部", "createTime": "...", "updateTime": "..." }, 
                { "id": 2, "name": "教研部", "createTime": "...", "updateTime": "..." },
                // ... 其他部门对象
            ] 
        }
    }
    ```

### 4.2 根据 ID 查询部门

*   **接口:** `GET /api/depts/{id}`
*   **路径参数:** `{id}` (Integer): 部门 ID。
*   **成功响应 (200 OK):** (`Result<Dept>`)
*   **失败响应:** `{ "code": 1, "msg": "部门不存在", "data": null }` (取决于业务逻辑)

### 4.3 新增部门

*   **接口:** `POST /api/depts`
*   **请求体 (JSON):** (`Dept` 对象)
    ```json
    { "name": "新的部门名称" }
    ```
*   **成功响应 (200 OK):** (标准成功响应)

### 4.4 更新部门

*   **接口:** `PUT /api/depts`
*   **请求体 (JSON):** (`Dept` 对象，**必须包含 `id`**)
    ```json
    { "id": 3, "name": "更新后的部门名称" }
    ```
*   **成功响应 (200 OK):** (标准成功响应)

### 4.5 根据 ID 删除部门

*   **接口:** `DELETE /api/depts/{id}`
*   **路径参数:** `{id}` (Integer): 部门 ID。
*   **成功响应 (200 OK):** (标准成功响应)
*   **失败响应:** (可能因关联员工或其他约束导致失败)

## 5. 班级管理接口 (`/api/classes`)

**认证:** 所有班级管理接口都需要 JWT 认证。

### 5.1 查询班级列表 (分页/条件)

*   **接口:** `GET /api/classes`
*   **查询参数:**
    *   `page` (Integer, 可选, 默认 1)
    *   `pageSize` (Integer, 可选, 默认 10)
    *   `className` (String, 可选): 班级名称 (模糊)
    *   `classStatus` (String, 可选): 状态
    *   `startTimeStart` (Date, 可选, yyyy-MM-dd)
    *   `startTimeEnd` (Date, 可选, yyyy-MM-dd)
*   **成功响应 (200 OK):** (`Result<PageResult<ClassInfo>>`)

### 5.2 根据 ID 查询班级

*   **接口:** `GET /api/classes/{id}`
*   **路径参数:** `{id}` (Integer): 班级 ID。
*   **成功响应 (200 OK):** (`Result<ClassInfo>`)
*   **失败响应:** `{ "code": 1, "msg": "班级不存在", "data": null }`

### 5.3 新增班级

*   **接口:** `POST /api/classes`
*   **请求体 (JSON):** (`ClassInfo` 对象，除 `id`, `updateTime` 外的字段)
*   **成功响应 (200 OK):** (标准成功响应)

### 5.4 更新班级

*   **接口:** `PUT /api/classes`
*   **请求体 (JSON):** (`ClassInfo` 对象，**必须包含 `id`**)
*   **成功响应 (200 OK):** (标准成功响应)
*   **失败响应:** `{ "code": 1, "msg": "更新时必须提供班级ID", "data": null }`

### 5.5 根据 ID 删除班级

*   **接口:** `DELETE /api/classes/{id}`
*   **路径参数:** `{id}` (Integer): 班级 ID。
*   **成功响应 (200 OK):** (标准成功响应)
*   **失败响应:** `{ "code": 1, "msg": "删除班级失败: 班级下存在学员，无法删除", "data": null }` 或其他错误。

## 6. 学员管理接口 (`/api/students`)

**认证:** 所有学员管理接口都需要 JWT 认证。

### 6.1 查询学员列表 (分页/条件)

*   **接口:** `GET /api/students`
*   **查询参数:**
    *   `page` (Integer, 可选, 默认 1)
    *   `pageSize` (Integer, 可选, 默认 10)
    *   `name` (String, 可选): 姓名 (模糊)
    *   `studentNumber` (String, 可选): 学号
    *   `education` (String, 可选): 学历
    *   `classId` (Integer, 可选): 班级 ID
*   **成功响应 (200 OK):** (`Result<PageResult<StudentInfo>>`) (其中 StudentInfo 对象的 `gender` 字段现在是 Integer: 1 代表男, 2 代表女)

### 6.2 根据 ID 查询学员

*   **接口:** `GET /api/students/{id}`
*   **路径参数:** `{id}` (Integer): 学员 ID。
*   **成功响应 (200 OK):** (`Result<StudentInfo>`) (其中 `gender` 字段是 Integer)

### 6.3 新增学员

*   **接口:** `POST /api/students`
*   **请求体 (JSON):** (`StudentInfo` 对象, `gender` 字段应为 Integer: 1=男, 2=女)
    ```json
    {
        "name": "周芷若",
        "gender": 2, // <-- 注意：现在是 Integer
        "studentNumber": "S2401003", 
        "phone": "13122334455",
        "classId": 1 
        // ... 其他字段
    }
    ```
*   **成功响应 (200 OK):** (标准成功响应)

### 6.4 更新学员

*   **接口:** `PUT /api/students`
*   **请求体 (JSON):** (`StudentInfo` 对象, **必须包含 `id`**, `gender` 字段为 Integer: 1=男, 2=女)
    ```json
    {
        "id": 6, 
        "gender": 1, // <-- 注意：现在是 Integer
        "phone": "13655556666"
        // ... 其他字段
    }
    ```
*   **成功响应 (200 OK):** (标准成功响应)

### 6.5 根据 ID 删除学员

*   **接口:** `DELETE /api/students/{id}`
*   **路径参数:** `{id}` (Integer): 学员 ID。
*   **成功响应 (200 OK):** (标准成功响应)
*   **失败响应:** (可能因数据库错误)

## 7. 文件上传接口 (`/api/upload`)

### 7.1 文件上传

*   **接口:** `POST /api/upload`
*   **描述:** 上传单个文件。
*   **认证:** **需要 JWT 认证**。
*   **请求体:** `multipart/form-data`
    *   参数名: `file`
    *   参数值: (选择要上传的文件)
*   **成功响应 (200 OK):**
    ```json
    {
        "code": 0,
        "msg": "操作成功",
        "data": "https://objectstorageapi.hzh.sealos.run/<bucket>/<uuid_filename>" // 返回文件的可访问 URL
    }
    ```
*   **失败响应:** `{ "code": 1, "msg": "文件上传失败：...", "data": null }` 