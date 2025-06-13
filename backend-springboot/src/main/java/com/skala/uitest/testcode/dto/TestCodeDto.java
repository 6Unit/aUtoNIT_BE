package com.skala.uitest.testcode.dto;

import com.skala.uitest.testcode.domain.TestCode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCodeDto {
    private String testcodeId;
    private String testcaseId;
    private String testcodeYaml;
    private String testcodeTs;
    private LocalDateTime createdAt;

    public static TestCodeDto fromEntity(TestCode entity) {
        return TestCodeDto.builder()
                .testcodeId(entity.getTestcodeId())
                .testcaseId(entity.getTestcaseId())
                .testcodeYaml(entity.getTestcodeYaml())
                .testcodeTs(entity.getTestcodeTs())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
