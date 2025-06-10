package com.skala.uitest.metadata.repository;

import com.skala.uitest.metadata.domain.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    Optional<Metadata> findByProjectId(String projectId);
}