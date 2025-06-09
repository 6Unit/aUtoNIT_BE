package com.skala.uitest.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectLoginRequestDto {
    private Long projectId;
    private String projectCode;
}
