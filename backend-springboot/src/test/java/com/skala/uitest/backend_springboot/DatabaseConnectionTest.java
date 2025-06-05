package com.skala.uitest.backend_springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.skala.uitest.backend_springboot.domain.Scenario;
import com.skala.uitest.backend_springboot.repository.ScenarioRepository;

import org.bson.Document;
import java.util.Date;


@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Test
    void testInsertScenario() {
        Scenario s = Scenario.builder()
            .title("테스트 시나리오")
            .description("DB 연결 테스트용")
            .build();

        scenarioRepository.save(s);
        System.out.println("✅ MariaDB에 시나리오 저장 완료");
    }

    @Test
    void testInsertMongoLog() {
        Document log = new Document()
            .append("scenarioId", 1)
            .append("result", "success")
            .append("executedAt", new Date());

        mongoTemplate.getCollection("test_logs").insertOne(log);
        System.out.println("✅ MongoDB에 로그 저장 완료");
    }


    @Test
    void testMariaDBConnection() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            assertFalse(conn.isClosed());
            System.out.println("✅ MariaDB 연결 성공");
        }
    }

    @Test
    void testMongoDBConnection() {
        Document doc = new Document("test", "연결확인");
        mongoTemplate.getCollection("test_logs").insertOne(doc);
        System.out.println("✅ MongoDB 연결 성공");
    }
}

