package com.moses.classdiary.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Student {
    @Id @GeneratedValue
    private Long id;        // PK, 식별자
    private String name;    // 학생 이름
    private Integer number; // 학번
    @Enumerated
    private Gender gender;  // 성별
    @Embedded
    private Contact contact; // 연락처(학생, 비상1, 비상2)
    @OneToMany
    private List<Attendance> attendances;   // 출결 리스트
    @OneToMany
    private List<Temperature> temperatures; // 체온 리스트
}
