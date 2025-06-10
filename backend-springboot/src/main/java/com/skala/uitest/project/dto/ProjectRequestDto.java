package com.skala.uitest.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequestDto {
    private Long projectId;
    private String projectName;
    private String projectCode;
}
