package com.skala.uitest.uploadedfile.repository;
import com.skala.uitest.project.domain.Project;
import com.skala.uitest.uploadedfile.domain.UploadedFile;
import com.skala.uitest.uploadedfile.enums.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface UploadedFileRepository extends JpaRepository<UploadedFile, String> {

    // 특정 프로젝트의 특정 파일 유형이 존재하는지
    boolean existsByProject_ProjectIdAndFileType(Long projectId, FileType fileType);

    Optional<UploadedFile> findByProjectAndFileType(Project project, FileType fileType);
    Optional<UploadedFile> findByProject_ProjectIdAndFileType(Long projectId, FileType fileType);

    // 필요 시: 전체 파일 목록 조회 (옵션)
    List<UploadedFile> findAllByProject_ProjectId(Long projectId);
}
