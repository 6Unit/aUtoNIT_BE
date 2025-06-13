package com.skala.uitest.testcode.domain;

import com.skala.uitest.testcase.domain.TestCase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_code")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCode {

    @Id
    @Column(name = "testcode_id", length = 45, nullable = false)
    private String testcodeId;

    @Lob
    @Column(name = "testcode_yaml", nullable = false, columnDefinition = "LONGTEXT")
    private String testcodeYaml;

    @Lob
    @Column(name = "testcode_ts", nullable = false, columnDefinition = "LONGTEXT")
    private String testcodeTs;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testcase_id", nullable = false)
    private TestCase testCase;

    // 편의 메서드 (testcase_id 접근용)
    public String getTestcaseId() {
        return this.testCase != null ? this.testCase.getTestcaseId() : null;
    }
}
