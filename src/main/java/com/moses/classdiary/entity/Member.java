package com.moses.classdiary.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Member implements UserDetails {

    @Id @GeneratedValue
    private Long id;            // PK, 식별자
    private String username;    // 회원 ID
    private String password;    // 비밀번호
    private String name;        // 이름
    private String email;       // 이메일
    private String schoolName;  // 학교명
    private Integer grade;      // 학년
    private Integer classNum;   // 반

    @OneToMany
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Survey> surveys = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "member_authority")
    private Set<Authority> authorities;

    private boolean activated;  // 활성화 여부

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
