package com.moses.classdiary.api;

import com.moses.classdiary.dto.lessonmemo.LessonMemoDateDto;
import com.moses.classdiary.dto.lessonmemo.LessonMemoDto;
import com.moses.classdiary.dto.lessonmemo.LessonMemoUpdateDto;
import com.moses.classdiary.service.LessonMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo")
public class LessonMemoApiController {
    private final LessonMemoService lessonMemoService;

    /**
     * 수업 기록 조회 API
     * @param lessonMemoDateDto - 조회하려는 날짜 DTO
     * @return 해당 날짜의 수업 기록 DTO 리스트
     */
    @GetMapping("/")
    public ResponseEntity<List<LessonMemoDto>> getLessonMemos(@RequestBody LessonMemoDateDto lessonMemoDateDto) {
        return ResponseEntity.ok(lessonMemoService.getLessonMemos(lessonMemoDateDto));
    }

    /**
     * 수업 기록 수정 API
     * @param lessonMemoUpdateDto - 수정할 내용이 담긴 수업 기록 DTO
     * @return 수업 기록 DTO 그대로 리턴
     */
    @PostMapping("/")
    public ResponseEntity<LessonMemoUpdateDto> updateLessonMemos(@RequestBody LessonMemoUpdateDto lessonMemoUpdateDto) {
        return ResponseEntity.ok(lessonMemoService.updateLessonMemos(lessonMemoUpdateDto));
    }
}
