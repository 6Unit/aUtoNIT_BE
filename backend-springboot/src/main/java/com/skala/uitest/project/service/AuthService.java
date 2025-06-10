package com.skala.uitest.project.service;

import com.skala.uitest.project.dto.ProjectLoginRequestDto;
import com.skala.uitest.project.dto.ProjectLoginResponseDto;
import com.skala.uitest.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ProjectRepository projectRepository;

    public ProjectLoginResponseDto login(ProjectLoginRequestDto dto) {
        boolean exists = projectRepository
                .findByProjectNameAndProjectCode(dto.getProjectName(), dto.getProjectCode())
                .isPresent();

        if (exists) {
            return new ProjectLoginResponseDto(true, "로그인 성공");
        } else {
            return new ProjectLoginResponseDto(false, "프로젝트 ID 또는 코드가 올바르지 않습니다.");
        }
    }
}
