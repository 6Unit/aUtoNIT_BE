package com.skala.uitest.project.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;  // 타입을 Long으로 변경, auto increment 지원

    @Column(length = 45, nullable = false)
    private String projectName;

    @Column(length = 45, nullable = false)
    private String projectCode;
}

