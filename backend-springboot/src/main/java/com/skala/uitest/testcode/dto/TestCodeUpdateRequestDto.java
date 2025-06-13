package com.skala.uitest.testcode.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCodeUpdateRequestDto {
    private String testcodeYaml;
}
