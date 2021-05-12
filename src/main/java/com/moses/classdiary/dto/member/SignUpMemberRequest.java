package com.moses.classdiary.dto.member;

import lombok.Data;

@Data
public class SignUpMemberRequest {
    private String username;    // 회원 ID
    private String password;    // 비밀번호
    private String name;        // 이름
    private String email;       // 이메일
    private String schoolName;  // 학교명
    private Integer grade;      // 학년
    private Integer classNum;   // 반
}
