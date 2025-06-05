package com.skala.uitest.backend_springboot;

import com.skala.uitest.backend_springboot.domain.Project;
import com.skala.uitest.backend_springboot.repository.ProjectRepository;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testInsertProject() {
        Project p = Project.builder()
            .projectName("테스트 프로젝트")
            .projectCode("TEST123")
            .build();

        projectRepository.save(p);
        System.out.println("✅ MariaDB에 프로젝트 저장 완료");
    }

    @Test
    void testInsertMongoLog() {
        Document log = new Document()
            .append("projectId", 1)
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
        mongoTemplate.getCollection("connection_test").insertOne(doc);
        System.out.println("✅ MongoDB 연결 성공");
    }
}
