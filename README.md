- **项目描述**: 开发了一个火车票预订系统，涵盖票务查询、预订、支付和订单管理等模块。
- **技术栈**: Spring Boot, Spring MVC, MyBatis-Plus, Redis, JWT, Maven, MySQL, Lombok, FastJSON, Swagger, Vue 3, JavaScript, Pinia, Vue Router, Element Plus, ECharts, Axios
- **责任和任务**:
  - **后端开发**:
    - 使用Spring Boot构建后端服务，处理票务查询、预订、支付和订单管理功能。
    - 集成MyBatis-Plus进行数据库操作，设计复杂的数据库结构以支持高并发查询。
    - 使用Redis进行缓存和会话存储，加速数据访问并减轻数据库负担。
    - 实现JWT用户认证和授权，确保系统安全。
    - 使用Swagger生成API文档，方便测试和开发。
  - **功能实现**:
    - 实现了火车票的查询、预订、支付和订单管理功能，用户可以通过系统查看和预订火车票。
    - 使用深度优先搜索(DFS)算法查找从起点到终点的所有可能路径，确保路径的连贯性和准确性。
    - 实现购物车功能，支持用户添加、修改和删除购物车中的票务信息。
    - 实现用户和管理员的权限管理，不同角色具有不同的操作权限。
**前端链接https://github.com/michealmachine/train_ticket_front**

## 使用 Docker Compose 运行项目

1. 确保已安装 Docker 和 Docker Compose。
2. 在项目根目录下创建 `docker-compose.yml` 文件，并添加以下内容：

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: train_ticket
      MYSQL_USER: user
      MYSQL_PASSWORD: password
    ports:
      - "3306:3306"
    networks:
      - train_ticket_network

  redis:
    image: redis:latest
    ports:
      - "6379:6379"
    networks:
      - train_ticket_network

networks:
  train_ticket_network:
    driver: bridge
```

3. 在项目根目录下运行以下命令启动服务：

```sh
docker-compose up -d
```

4. 服务启动后，可以通过以下命令查看运行状态：

```sh
docker-compose ps
```


