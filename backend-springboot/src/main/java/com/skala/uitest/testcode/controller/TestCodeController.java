package com.skala.uitest.testcode.controller;

import com.skala.uitest.testcode.dto.TestCodeUpdateRequestDto;
import com.skala.uitest.testcode.service.TestCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testcode")
@RequiredArgsConstructor
@Tag(name = "TestCode API", description = "테스트코드 생성 및 관리 API")
public class TestCodeController {

    private final TestCodeService testCodeService;

    @Operation(summary = "테스트코드 자동 생성", description = "여러 테스트케이스 ID를 기반으로 FastAPI에 테스트코드 생성을 요청합니다.")
    @PostMapping("/generate")
    public ResponseEntity<?> generateTestCodes(@RequestBody List<String> testcaseIds) {
        return testCodeService.delegateTestCodeGeneration(testcaseIds);
    }

    @Operation(summary = "yaml 개별 조회", description = "특정 테스트코드 YAML 파일 원문 조회")
    @GetMapping("/{testcaseId}/yaml")
    public ResponseEntity<String> getYaml(@PathVariable String testcaseId) {
        return testCodeService.getYamlByTestcaseId(testcaseId);
    }

    @Operation(summary = "ts 개별 조회", description = "특정 테스트코드 TS 파일 원문 조회")
    @GetMapping("/{testcaseId}/ts")
    public ResponseEntity<String> getTs(@PathVariable String testcaseId) {
        return testCodeService.getTsByTestcaseId(testcaseId);
    }

    @Operation(summary = "YAML 수정", description = "테스트케이스 ID에 해당하는 테스트코드 YAML을 수정합니다.")
    @PutMapping("/{testcaseId}")
    public ResponseEntity<TestCodeUpdateRequestDto> updateYaml(
            @PathVariable String testcaseId,
            @RequestBody TestCodeUpdateRequestDto dto) {
        TestCodeUpdateRequestDto updated = testCodeService.updateYamlByTestcaseId(testcaseId, dto);
        return ResponseEntity.ok(updated);
    }
}
