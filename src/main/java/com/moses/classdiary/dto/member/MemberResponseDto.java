package com.moses.classdiary.dto.member;

import com.moses.classdiary.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String username;
    private String name;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getUsername(), member.getName());
    }
}
