package com.moses.classdiary.api;

import com.moses.classdiary.dto.member.SignUpMemberDto;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 회원가입 API
     * @param signUpMemberDto - 회원가입 정보가 담긴 DTO
     * @return Member가 담긴 ResponseEntity
     */
    @PostMapping("/signup")
    public ResponseEntity<Member> signup(@RequestBody @Valid SignUpMemberDto signUpMemberDto){
        return ResponseEntity.ok(memberService.signup(signUpMemberDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Member> getMyUserInfo(){
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Member> getUserInfo(@PathVariable String username){
        return ResponseEntity.ok(memberService.getUserWithAuthorities(username).get());
    }

}
