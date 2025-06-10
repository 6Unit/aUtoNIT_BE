# 1단계: 빌드용 Maven 이미지
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# pom.xml과 소스 복사
COPY pom.xml .
COPY src ./src

# 의존성 다운로드 및 패키징
RUN mvn clean package -DskipTests

# 2단계: 실행용 JRE 이미지
FROM eclipse-temurin:17-jre

WORKDIR /app

# 빌드된 JAR 복사
COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/application.yaml application.yaml
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/application.yaml"]
