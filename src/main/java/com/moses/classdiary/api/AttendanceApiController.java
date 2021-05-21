package com.moses.classdiary.api;

import com.moses.classdiary.dto.attendance.AttendanceDto;
import com.moses.classdiary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AttendanceApiController {

    private final MemberService memberService;

    @GetMapping("/attendance")
    public List<AttendanceDto> getAttendances(@RequestParam("date") LocalDateTime date) {
        return memberService.getAttendances(date);
    }

    @PostMapping("/attendance")
    public void setAttendance(List<AttendanceDto> attendanceDtoList){
        memberService.setAttendances(attendanceDtoList);
    }
}
