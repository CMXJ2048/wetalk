# 用户管理 API 规范

本规范定义“用户管理”相关的数据模型与前后端 REST 接口。面向前后端分离开发，返回值为 JSON。

## 一、数据模型

- id (Long): 系统自增唯一ID。
- account (String): 用户自定义的唯一账户名（登录用）。
- email (String): 邮箱（唯一）。
- password (String): 密码（仅在请求中以明文提交，服务端持久化为BCrypt哈希；响应中不返回）。
- displayName (String): 可重复的用户名（展示昵称）。
- avatarUrl (String): 用户头像URL。用户可：
  - 自行上传（将文件上传到后端，后端返回URL）；
  - 选择系统头像：`/picture_base/{1..n}.jpg` 下的相对路径，例如 `picture_base/1.jpg`。

说明：后端实体层会用 `passwordHash` 持久化存储，API 层只接受 `password` 字段输入，不在响应中回传密码。

## 二、通用约定

- Base URL: `/api/users`
- Content-Type: `application/json`
- 认证：已接入JWT。`POST /api/auth/login` 返回 `token`，写接口需携带 `Authorization: Bearer <token>`。读接口（GET /api/users/**、/uploads/**、/picture_base/**）可匿名。
- 错误：返回 `{ code, message }`，HTTP 状态码与语义保持一致（400/401/404 等）。

## 三、接口定义

### 1. 创建用户（注册）
- POST `/api/users` （已实现）
- Body:
```json
{
  "account": "cmxj",
  "email": "xxx@example.com",
  "password": "plainPassword",
  "displayName": "陈某某",
  "avatarUrl": "picture_base/1.jpg"  
}
```
- Response 201:
```json
{
  "id": 1,
  "account": "cmxj",
  "email": "xxx@example.com",
  "displayName": "陈某某",
  "avatarUrl": "picture_base/1.jpg"
}
```
- 约束：`account` 唯一、`email` 唯一，`password` 至少8位。

### 2. 获取用户详情
- GET `/api/users/{id}` （已实现）
- Response 200:
```json
{
  "id": 1,
  "account": "cmxj",
  "email": "xxx@example.com",
  "displayName": "陈某某",
  "avatarUrl": "picture_base/1.jpg"
}
```

### 3. 列表查询（分页）
- GET `/api/users?page=0&size=20&keyword=cm`（当前为简单列表，分页与搜索待实现）
- Response 200:
```json
{
  "content": [
    { "id":1, "account":"cmxj", "email":"...", "displayName":"...", "avatarUrl":"..." }
  ],
  "page": 0,
  "size": 20,
  "total": 123
}
```

### 4. 更新用户
- PUT `/api/users/{id}` （已实现）
- Body（部分或全部字段，可忽略未更新字段）:
```json
{
  "email": "new@example.com",
  "displayName": "新昵称",
  "avatarUrl": "picture_base/2.jpg"
}
```
- Response 200: 同“获取用户详情”。

### 5. 修改密码
- PUT `/api/users/{id}/password`（已实现）
- Body:
```json
{ "oldPassword": "old", "newPassword": "newStrongPassword" }
```
- Response 204

### 6. 检查账户名/邮箱是否可用
- GET `/api/users/availability?account=cmxj`
- 或 GET `/api/users/availability?email=xxx@example.com`（已实现）
- Response 200:
```json
{ "available": true }
```

### 7. 头像上传（可选）
- POST `/api/users/{id}/avatar` (multipart/form-data)
- Form: `file`=图片文件
- Response 200:
```json
{ "avatarUrl": "/uploads/avatars/xxxxx.jpg" }
```

## 四、示例错误
```json
{
  "code": "ACCOUNT_EXISTS",
  "message": "Account already exists"
}
```

## 五、后续
- 接入JWT鉴权（/api/auth/login 返回token），受保护写接口需携带Authorization: Bearer ...
- 增加头像存储（本地/对象存储）与基础图片选择接口（读取 picture_base 目录映射）。

## 六、好友与频道（需求设计草案）

### 好友
- 搜索用户：GET `/api/users?keyword=xxx`（已支持，按 account/displayName 模糊，最多20条，返回 UserPublicDto）
- 添加好友：POST `/api/friends` Body: { friendId }（userId 来自鉴权）
- 删除好友：DELETE `/api/friends/{friendId}`（userId 来自鉴权）
- 我的好友列表：GET `/api/friends` → [UserPublicDto]（userId 来自鉴权）

数据建议：
- friends 表：id, user_id, friend_user_id, created_at（双向关系可插入两条或逻辑对称查询）

### 频道（群聊）
- 创建频道：POST `/api/channels` Body: { name, memberIds[] }（owner 来自鉴权；校验成员上限 ≤ 100）
- 加入频道：POST `/api/channels/{id}/members` Body: { userId }
- 退出频道：DELETE `/api/channels/{id}/members/{userId}`
- 列出我在的频道：GET `/api/channels`（userId 来自鉴权）
- 查询频道成员：GET `/api/channels/{id}/members`

数据建议：
- channels 表：id, name, owner_user_id, created_at, max_members(=100)
- channel_members 表：id, channel_id, user_id, joined_at（唯一约束 channel_id+user_id）

聊天记录（本地）
- 仅存本地端，不入库；可设计本地持久化（IndexedDB/sqlite/文件）并通过 WebSocket 实时分发消息。
- 服务端只管理在线通道与成员状态、消息转发，不持久化消息。
