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
    public List<AttendanceDto> getAttendances(@RequestParam("date") LocalDateTime date) {
        Member member = memberService.getMyUserWithAuthorities().get();
        return memberService.getAttendances(member, date);
    }

    @PostMapping("/attendance")
    public Optional<Member> setAttendance(List<AttendanceDto> attendanceDtoList){
        Member member = memberService.getMyUserWithAuthorities().get();
        return memberService.setAttendances(member, attendanceDtoList);
    }
}
