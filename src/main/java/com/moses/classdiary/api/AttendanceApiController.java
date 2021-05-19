package com.moses.classdiary.api;

import com.moses.classdiary.dto.attendance.AttendanceDto;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AttendanceApiController {

    private final MemberService memberService;

    @GetMapping("/attendance")
    public List<AttendanceDto> getAttendances(@RequestParam("id") Long id, @RequestParam("date") LocalDateTime date) {
        return memberService.getAttendances(id, date);
    }

    @PostMapping("/attendance")
    public Optional<Member> setAttendance(@RequestParam("id") Long id, List<AttendanceDto> attendanceDtoList){
        return memberService.setAttendances(id, attendanceDtoList);
    }
}
