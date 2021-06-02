package com.moses.classdiary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceAssessment {
    @Id @GeneratedValue
    private Long id;        // 평가 기록 식별자 (PK)
    private String title;   // 평가 항목 이름
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;  // 담당 선생님 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;// 학생 (FK)
    @Enumerated
    private Grade grade;    // 평가 등급

    /**
     * 평가 등급
     * [미평가, 매우잘함, 잘함, 보통, 노력요함]
     */
    public enum Grade {
        UNKNOWN,
        VERY_GOOD,
        GOOD,
        NORMAL,
        EFFORT_REQUIRED
    }

    public PerformanceAssessment(String title, Member member, Student student, Grade grade) {
        this.title = title;
        this.member = member;
        this.student = student;
        this.grade = grade;
    }

    public void update(Grade grade) {
        this.grade = grade;
    }
}
