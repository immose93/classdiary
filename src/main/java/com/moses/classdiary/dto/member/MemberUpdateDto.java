package com.moses.classdiary.dto.member;

import com.moses.classdiary.entity.Member;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberUpdateDto extends SignUpMemberDto {
   public MemberUpdateDto(Member member) {
       this.username = member.getUsername();
       this.password = "";
       this.name = member.getName();
       this.email = member.getEmail();
       this.schoolName = member.getSchoolName();
       this.grade = member.getGrade();
       this.classNum = member.getClassNum();
   }
}
