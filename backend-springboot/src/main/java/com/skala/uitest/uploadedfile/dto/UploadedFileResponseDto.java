package com.skala.uitest.uploadedfile.dto;

import com.skala.uitest.uploadedfile.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadedFileResponseDto {
    private FileType type;
    private String name;
}
