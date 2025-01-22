# 使用 OpenJDK 作為基礎映像
FROM openjdk:17-jdk-slim

# 添加 MySQL 連線檢測腳本
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# 設置工作目錄
WORKDIR /app

# 將 JAR 檔案複製到容器中
# COPY springboot-ShopMall-0.0.1-SNAPSHOT.jar app.jar
COPY target/springboot-ShopMall-0.0.1-SNAPSHOT.jar /app/app.jar


# 暴露容器中的 8080 埠（Spring Boot 預設埠）
EXPOSE 8080

# 設定啟動指令
# ./wait-for-it.sh：確保依賴的服務（例如 MySQL）已經可用，避免因為依賴服務未啟動而導致錯誤。
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

