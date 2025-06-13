package com.skala.uitest.testcode.service;

import com.skala.uitest.testcode.dto.TestCodeUpdateRequestDto;
import com.skala.uitest.testcode.repository.TestCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCodeService {

    private final RestTemplate restTemplate;
    private final TestCodeRepository testCodeRepository;

    // ✅ 테스트코드 생성 요청
    public ResponseEntity<?> delegateTestCodeGeneration(List<String> testcaseIds) {
        String fastApiUrl = "http://localhost:8000/generate-and-save-testcodes";  // ✅ FastAPI 경로

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<List<String>> request = new HttpEntity<>(testcaseIds, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(fastApiUrl, request, String.class);

            return ResponseEntity.ok(response.getBody());

        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("FastAPI 호출 실패: " + e.getMessage());
        }
    }

    // ✅ YAML 조회
    public ResponseEntity<String> getYamlByTestcaseId(String testcaseId) {
        return testCodeRepository.findByTestCase_TestcaseId(testcaseId)
                .map(tc -> ResponseEntity.ok(tc.getTestcodeYaml()))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ TS 조회
    public ResponseEntity<String> getTsByTestcaseId(String testcaseId) {
        return testCodeRepository.findByTestCase_TestcaseId(testcaseId)
                .map(tc -> ResponseEntity.ok(tc.getTestcodeTs()))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ YAML 수정
    public TestCodeUpdateRequestDto updateYamlByTestcaseId(String testcaseId, TestCodeUpdateRequestDto dto) {
        return testCodeRepository.findByTestCase_TestcaseId(testcaseId)
                .map(tc -> {
                    tc.setTestcodeYaml(dto.getTestcodeYaml());
                    testCodeRepository.save(tc);
                    return TestCodeUpdateRequestDto.builder()
                            .testcodeYaml(tc.getTestcodeYaml())
                            .build();
                })
                .orElseThrow(() -> new IllegalArgumentException("해당 testcaseId가 없습니다: " + testcaseId));
    }
}
