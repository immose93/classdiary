package com.moses.classdiary.api;

import com.moses.classdiary.dto.member.*;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/member/signup")
    public SignUpMemberResponse signup(@RequestBody @Valid SignUpMemberRequest request){
        Member member = new Member();
        member.setUsername(request.getUsername());
        member.setPassword(request.getPassword());
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setSchoolName(request.getSchoolName());
        member.setGrade(request.getGrade());
        member.setClassNum(request.getClassNum());

        Long id = memberService.signup(member);
        return new SignUpMemberResponse(id);
    }

}
