package com.moses.classdiary.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;            // PK, 식별자
    private String username;    // 회원 ID
    private String password;    // 비밀번호
    private String name;        // 이름
    private String email;       // 이메일
    private String schoolName;  // 학교명
    private Integer grade;      // 학년
    private Integer classNum;   // 반

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survey> surveys = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonMemo> lessonMemos = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerformanceAssessment> performanceAssessments = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "member_authority")
    private Set<Authority> authorities;

    private boolean activated;  // 활성화 여부

    /**
     * 학생 추가 메소드
     * @param student - 추가할 학생
     */
    public void addStudent(Student student){
        this.students.add(student);
        if(student.getMember() != this){
            student.setMember(this);
        }
    }
}
