package com.moses.classdiary.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;            // PK, 식별자
    private String username;    // 회원 ID
    private String password;    // 비밀번호
    @Transient
    private String passwordChk; // 비밀번호 확인
    private String name;        // 이름
    private String email;       // 이메일
    private String schoolName;  // 학교명
    private Integer grade;      // 학년
    private Integer classNum;   // 반

    @OneToMany(mappedBy = "member")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Survey> surveys = new ArrayList<>();
}
