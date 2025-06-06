package com.skala.uitest.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectLoginResponseDto {
    private boolean success;
    private String message;
}
