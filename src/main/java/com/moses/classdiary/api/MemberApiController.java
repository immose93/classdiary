package com.moses.classdiary.api;

import com.moses.classdiary.dto.member.SignUpMemberDto;
import com.moses.classdiary.dto.student.StudentDto;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/delete")
    public void delete(){
        Member member = memberService.getMyUserWithAuthorities().get();
        memberService.delete(member);
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

    @PostMapping("/user/students")
    public ResponseEntity<Member> setStudentsInfo(@RequestBody @Valid List<StudentDto> studentDtoList){
        Member member = memberService.getMyUserWithAuthorities().get();
        return ResponseEntity.ok(memberService.setStudents(member, studentDtoList));
    }

    @GetMapping("/user/students")
    public ResponseEntity<List<StudentDto>> getStudentsInfo(){
        Member member = memberService.getMyUserWithAuthorities().get();
        return ResponseEntity.ok(memberService.getStudents(member));
    }
}
