package com.moses.classdiary.api;

import com.moses.classdiary.dto.member.*;
import com.moses.classdiary.entity.Authority;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/member/signup")
    public SignUpMemberResponse signup(@RequestBody @Valid SignUpMemberRequest request){
        Member member = new Member();
        member.setUsername(request.getUsername());
        member.setPassword(request.getPassword());
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setSchoolName(request.getSchoolName());
        member.setGrade(request.getGrade());
        member.setClassNum(request.getClassNum());
        member.setAuthorities(Collections.singleton(new Authority("ROLE_USER")));

        Long id = memberService.signup(member);
        return new SignUpMemberResponse(id);
    }

}
