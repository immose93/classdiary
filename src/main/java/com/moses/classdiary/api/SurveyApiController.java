package com.moses.classdiary.api;

import com.moses.classdiary.dto.survey.SurveyDto;
import com.moses.classdiary.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/survey")
@RequiredArgsConstructor
public class SurveyApiController {

    private final SurveyService surveyService;

    /**
     * 안내장 목록 조회 API
     * @return 안내장 목록
     */
    @GetMapping("/list")
    public ResponseEntity<List<String>> getSurveyList(){
        return ResponseEntity.ok(surveyService.getSurveyList());
    }

    /**
     * 안내장 추가 API
     * @param surveyTitle - 추가될 안내장의 제목
     * @return 추가된 안내장의 제목
     */
    @PostMapping("/add")
    public ResponseEntity<SurveyDto> addSurvey(@RequestBody String surveyTitle) {
        return ResponseEntity.ok(surveyService.addSurvey(surveyTitle));
    }

    /**
     * 안내장 삭제 API
     * @param surveyTitle - 삭제될 안내장의 제목
     * @return 삭제 후 안내장 목록
     */
    @PostMapping("/delete")
    public ResponseEntity<List<String>> deleteSurvey(@RequestBody String surveyTitle) {
        return ResponseEntity.ok(surveyService.deleteSurvey(surveyTitle));
    }

    /**
     * 안내장 제출여부 목록 조회 API
     * @param surveyTitle - 안내장 제목
     * @return 안내장 제출여부 목록 정보가 담긴 SurveyDto
     */
    @GetMapping("/")
    public ResponseEntity<SurveyDto> getSurvey(@RequestBody String surveyTitle){
        return ResponseEntity.ok(surveyService.getSurvey(surveyTitle));
    }

    /**
     * 안내장 제출여부 목록 수정 API
     * @param surveyDto - 수정된 안내장 제출여부 정보
     * @return 수정 반영된 안내장 제출여부 목록 SurveyDto
     */
    @PostMapping("/update")
    public ResponseEntity<SurveyDto> updateSurvey(@RequestBody SurveyDto surveyDto) {
        return ResponseEntity.ok(surveyService.updateSurvey(surveyDto));
    }
}
