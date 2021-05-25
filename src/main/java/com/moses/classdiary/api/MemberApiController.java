package com.moses.classdiary.api;

import com.moses.classdiary.dto.member.MemberResponseDto;
import com.moses.classdiary.dto.student.StudentDto;
import com.moses.classdiary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 회원탈퇴 API
     */
    @PostMapping("/delete")
    public void delete(){
        memberService.delete();
    }

    /**
     * 현재 로그인 된 정보 조회 API
     * @return MemberResponseDto 가 담긴 ResponseEntity
     */
    @GetMapping("/")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo(){
        return ResponseEntity.ok(memberService.getMyInfo());
    }

    /**
     * username 회원 정보 조회 API
     * @param username - 조회하려는 회원의 아이디
     * @return - 회원 정보
     */
    @GetMapping("/{username}")
    //@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable String username){
        return ResponseEntity.ok(memberService.getMemberInfo(username));
    }

    /**
     * 반 학생들 정보 수정 API
     * @param studentDtoList - 학생 정보
     * @return 반 학생들 정보
     */
    @PostMapping("/students")
    public ResponseEntity<List<StudentDto>> setStudentsInfo(@RequestBody @Valid List<StudentDto> studentDtoList){
        memberService.setStudents(studentDtoList);
        return ResponseEntity.ok(memberService.getStudents());
    }

    /**
     * 반 학생들 정보 조회 API
     * @return 반 학생들 정보
     */
    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getStudentsInfo(){
        return ResponseEntity.ok(memberService.getStudents());
    }
}
