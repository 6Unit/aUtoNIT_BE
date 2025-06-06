package com.skala.uitest.backend_springboot.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDto {
    private String projectName;
    private String projectCode;
}
