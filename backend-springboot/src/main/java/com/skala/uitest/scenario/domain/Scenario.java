package com.skala.uitest.scenario.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scenario {

    @Id
    @Column(name = "scenario_id", length = 45, nullable = false)
    private String scenarioId;

    @Column(name = "scenario_name", length = 100, nullable = false)
    private String scenarioName;

    @Column(name = "scenario_description", columnDefinition = "TEXT")
    private String scenarioDescription;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
