package com.moses.classdiary.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginMemberDto {
    @NotNull(message = "아이디는 필수 입력 값입니다.")
    private String username;    // 회원 ID

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[`~!@#$%^&*()_+]).{8,20}$",
            message = "비밀번호는 영문자, 숫자, 특수문자(`~!@#$%^&*()_+)가 각 1자리 이상이면서 8자 이상 20자 이하여야 합니다.")
    private String password;    // 비밀번호
}
