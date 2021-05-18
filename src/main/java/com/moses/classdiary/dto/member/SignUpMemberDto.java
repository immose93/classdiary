package com.moses.classdiary.dto.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpMemberDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username;    // 회원 ID

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[`~!@#$%^&*()_+]).{8,20}$",
            message = "비밀번호는 영문자, 숫자, 특수문자(`~!@#$%^&*()_+)가 각 1자리 이상이면서 8자 이상 20자 이하여야 합니다.")
    private String password;    // 비밀번호

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;        // 이름

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;       // 이메일

    private String schoolName;  // 학교명
    private Integer grade;      // 학년
    private Integer classNum;   // 반
}
