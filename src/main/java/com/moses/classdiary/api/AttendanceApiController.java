package com.moses.classdiary.api;

import com.moses.classdiary.dto.attendance.AttendanceDateDto;
import com.moses.classdiary.dto.attendance.AttendanceDto;
import com.moses.classdiary.dto.attendance.AttendanceUpdateDto;
import com.moses.classdiary.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceApiController {

    private final AttendanceService attendanceService;

    /**
     * 출결 목록 조회 API
     * @param attendanceDateDto - 날짜
     * @return 출결 목록
     */
    @GetMapping("/")
    public ResponseEntity<List<AttendanceDto>> getAttendances(@RequestBody AttendanceDateDto attendanceDateDto) {
        return ResponseEntity.ok(attendanceService.getAttendances(attendanceDateDto.getDate()));
    }

    /**
     * 출결 목록 저장 API
     * @param attendanceUpdateDto - 날짜와 출결 목록 저장 정보가 담긴 DTO
     * @return 반영 후 출결 목록
     */
    @PostMapping("/")
    public ResponseEntity<List<AttendanceDto>> updateAttendances(@RequestBody AttendanceUpdateDto attendanceUpdateDto){
        attendanceService.updateAttendances(attendanceUpdateDto.getDate(), attendanceUpdateDto.getAttendanceDtoList());
        return ResponseEntity.ok(attendanceService.getAttendances(attendanceUpdateDto.getDate()));
    }
}
