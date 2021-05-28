package com.moses.classdiary.api;

import com.moses.classdiary.dto.timetable.TimetableDto;
import com.moses.classdiary.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timetable")
public class TimetableApiController {
    private final TimetableService timetableService;

    /**
     * 시간표 조회 API
     * @param timetableType - 시간표 유형
     * @return 시간표 DTO
     */
    @GetMapping("/")
    public ResponseEntity<TimetableDto> getTimetable(@RequestBody String timetableType) {
        return ResponseEntity.ok(timetableService.getTimetable(timetableType));
    }

    /**
     * 시간표 수정 API
     * @param timetableDto - 수정할 정보가 담긴 시간표 DTO
     * @return 시간표 DTO 그대로 리턴
     */
    @PostMapping("/")
    public ResponseEntity<TimetableDto> updateTimetable(@RequestBody TimetableDto timetableDto) {
        return ResponseEntity.ok(timetableService.updateTimetable(timetableDto));
    }
}
