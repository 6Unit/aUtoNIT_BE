package com.skala.uitest.testcase.controller;

import com.skala.uitest.scenario.dto.ScenarioDto;
import com.skala.uitest.testcase.dto.TestCaseCreateRequestDto;
import com.skala.uitest.testcase.dto.TestCaseDetailDto;
import com.skala.uitest.testcase.dto.TestCaseDto;
import com.skala.uitest.testcase.dto.TestCaseListDto;
import com.skala.uitest.testcase.dto.TestCaseUpdateRequestDto;
import com.skala.uitest.testcase.service.TestCaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testcase")
@RequiredArgsConstructor
@Tag(name = "TestCase API", description = "테스트케이스 관리 API")
public class TestCaseController {

    private final TestCaseService testCaseService;

    @Operation(summary = "테스트케이스 저장", description = "FastAPI에서 생성된 테스트케이스들을 저장합니다.")
    @PostMapping
    public void saveTestCases(@RequestBody List<TestCaseDto> dtos) {
        testCaseService.saveTestCases(dtos);
    }

    @Operation(summary = "테스트케이스 목록 조회", description = "시나리오 ID 기준으로 테스트케이스 목록을 조회합니다.")
    @GetMapping("/scenario/{scenarioId}")
    public List<TestCaseListDto> getByScenario(@PathVariable String scenarioId) {
        return testCaseService.getTestCasesByScenario(scenarioId);
    }


    @Operation(summary = "테스트케이스 단건 조회", description = "테스트케이스 ID 기준으로 주요 정보를 조회합니다.")
    @GetMapping("/{id}")
    public TestCaseDetailDto getOne(@PathVariable String id) {
        return testCaseService.getTestCaseDetailById(id);
    }

    @Operation(summary = "테스트케이스 단일 추가", description = "특정 시나리오에 단일 테스트케이스를 추가합니다.")
    @PostMapping("/{scenarioId}")
    public ResponseEntity<TestCaseDto> createTestCase(
            @PathVariable String scenarioId,
            @RequestBody TestCaseCreateRequestDto dto) {
        
        TestCaseDto saved = testCaseService.createTestCaseFromScenario(scenarioId, dto);
        return ResponseEntity.ok(saved);
    }
    
    @Operation(summary = "테스트케이스 수정", description = "테스트케이스 ID를 기준으로 단일 항목을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<TestCaseUpdateRequestDto> update(@PathVariable String id, @RequestBody TestCaseUpdateRequestDto dto) {
        TestCaseUpdateRequestDto updated = testCaseService.updateTestCase(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "테스트케이스 삭제", description = "테스트케이스 ID를 기준으로 단일 항목을 삭제합니다.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        testCaseService.deleteTestCase(id);
    }
}
