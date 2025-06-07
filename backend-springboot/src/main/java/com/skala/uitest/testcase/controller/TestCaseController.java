package com.skala.uitest.testcase.controller;

import com.skala.uitest.testcase.dto.TestCaseDto;
import com.skala.uitest.testcase.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testcase")
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

    // 전체 시나리오 대상 생성
    @PostMapping
    public void createAll(@RequestBody List<TestCaseDto> dtos) {
        testCaseService.createTestCasesBulk(dtos);
    }

    // 특정 시나리오에 대한 일부 생성
    @PostMapping("/sub")
    public void createSub(@RequestParam String scenarioId, @RequestBody List<TestCaseDto> dtos) {
        testCaseService.createTestCasesByScenario(dtos, scenarioId);
    }

    // 시나리오별 조회
    @GetMapping
    public List<TestCaseDto> getByScenario(@RequestParam String scenarioId) {
        return testCaseService.getTestCasesByScenario(scenarioId);
    }

    // 단건 수정
    @PatchMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody TestCaseDto dto) {
        testCaseService.updateTestCase(id, dto);
    }

    // 단건 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        testCaseService.deleteTestCase(id);
    }
}
