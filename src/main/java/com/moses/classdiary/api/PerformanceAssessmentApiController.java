package com.moses.classdiary.api;

import com.moses.classdiary.dto.performanceassessment.PerformanceAssessmentDto;
import com.moses.classdiary.dto.performanceassessment.PerformanceAssessmentUpdateDto;
import com.moses.classdiary.service.PerformanceAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/performance_assessment")
public class PerformanceAssessmentApiController {
    private final PerformanceAssessmentService performanceAssessmentService;

    /**
     * 평가 항목 추가 API
     * @param title - 평가 항목 이름
     * @return 평가 기록 DTO
     */
    @PostMapping("/add")
    public ResponseEntity<List<PerformanceAssessmentDto>> addPerformanceAssessment(@RequestBody String title) {
        return ResponseEntity.ok(performanceAssessmentService.addPerformanceAssessment(title));
    }

    /**
     * 평가 항목 리스트 조회 API
     * @return 평가 항목 이름 리스트
     */
    @GetMapping("/")
    public ResponseEntity<List<String>> getPerformanceAssessmentList() {
        return ResponseEntity.ok(performanceAssessmentService.getPerformanceAssessmentList());
    }

    /**
     * 평가 항목 조회 API
     * @param title - 평가 항목 이름
     * @return 평가 기록 DTO
     */
    @GetMapping("/get")
    public ResponseEntity<List<PerformanceAssessmentDto>> getPerformanceAssessment(@RequestBody String title) {
        return ResponseEntity.ok(performanceAssessmentService.getPerformanceAssessment(title));
    }

    /**
     * 평가 기록 수정 API
     * @param performanceAssessmentUpdateDto - 수정사항이 반영된 평가 기록 DTO
     * @return 수정사항이 반영된 평가 기록 DTO 리턴
     */
    @PostMapping("/update")
    public ResponseEntity<PerformanceAssessmentUpdateDto> updatePerformanceAssessment(@RequestBody PerformanceAssessmentUpdateDto performanceAssessmentUpdateDto) {
        return ResponseEntity.ok(performanceAssessmentService.updatePerformanceAssessment(performanceAssessmentUpdateDto));
    }

    /**
     * 평가 항목 삭제 API
     * @param title - 삭제할 평가 항목 이름
     * @return - 삭제 후 평가 항목 이름 리스트
     */
    @PostMapping("/delete")
    public ResponseEntity<List<String>> deletePerformanceAssessment(@RequestBody String title) {
        return ResponseEntity.ok(performanceAssessmentService.deletePerformanceAssessment(title));
    }
}
