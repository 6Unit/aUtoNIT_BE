package com.skala.uitest.project.controller;

import com.skala.uitest.project.dto.ProjectLoginRequestDto;
import com.skala.uitest.project.dto.ProjectLoginResponseDto;
import com.skala.uitest.project.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "인증 관련 API")
public class LoginController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "프로젝트 로그인", description = "프로젝트명과 코드를 사용하여 로그인")
    public ProjectLoginResponseDto login(@RequestBody ProjectLoginRequestDto dto) {
        return authService.login(dto);
    }
}
