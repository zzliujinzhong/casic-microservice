# ==================== 使用说明 ====================

# 本地快速启动 (推荐用于开发)
# 1. 先启动基础设施 (Nacos + MySQL + Redis + RabbitMQ)
docker-compose up -d nacos mysql redis rabbitmq

# 2. 等待 Nacos 启动后，在 Nacos 控制台 (http://localhost:8848/nacos, 默认密码 nacos/nacos)
#    导入 deploy/nacos-config/nacos-shared-config.yaml 中的共享配置

# 3. 编译并启动微服务
mvn clean package -DskipTests
java -jar casic-infra/casic-gateway/target/casic-gateway.jar
java -jar casic-infra/casic-auth/target/casic-auth.jar
java -jar casic-service/casic-contract/target/casic-contract.jar

# Docker 一键部署 (推荐用于测试/生产)
docker-compose up -d

# SkyWalking 链路追踪 (可选)
# 下载 agent: https://skywalking.apache.org/downloads/
# 启动服务时添加: -javaagent:/path/to/skywalking-agent/skywalking-agent.jar
