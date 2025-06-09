# AutoNIT_BE

## 실행 방법
`docker-compose up -d`

## 재빌드
`docker-compose up --build -d`

### FastAPI - MariaDB 연동 테스트
.env 설정 후, `python app/test_db.py` 실행    
"✅ MariaDB 연결 성공: 1" 이 출력되면 OK   
안 되면, `python -m app.test_db` 실행

### FastAPI - MongoDB 연동 테스트
`python app/test_mongo.py` 실행    
"✅ MongoDB 연결 성공: ..." 이 출력되면 OK   
안 되면, `python -m app.test_mongo` 실행

### FastAPI
`python app/init_db.py`   
안 되면, `python -m app.init_db` 실행


### Spring Boot - MariaDB/MongoDB 연동 테스트
`cd backend-springboot`   
`./mvnw test` Maven Wrapper를 사용해 테스트 코드를 실행하는 명령어   
로그에 ✅ MariaDB 연결 성공, ✅ MongoDB에 로그 저장 완료 등 메시지 출력되면 OK


## 로컬 개발 시
### spring boot
`docker compose up --build backend-springboot`
http://localhost:8088/swagger-ui/index.html -> 스웨거 확인 가능

### fastapi
http://localhost:8000/docs