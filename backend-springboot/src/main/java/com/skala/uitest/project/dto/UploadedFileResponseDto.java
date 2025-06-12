package com.skala.uitest.project.dto;

import com.skala.uitest.project.enums.FileType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadedFileResponseDto {
    private FileType type;
    private String name;
}
