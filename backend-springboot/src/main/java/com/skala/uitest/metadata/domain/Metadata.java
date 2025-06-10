package com.skala.uitest.metadata.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metadataId;

    private String projectId;
    private String filePath;
    private LocalDateTime analyzedAt;
}
