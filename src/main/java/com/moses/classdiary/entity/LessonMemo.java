package com.moses.classdiary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonMemo {
    @Id @GeneratedValue
    private Long id;        // 수업 기록 식별자 (PK)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;  // 회원 (FK)
    private LocalDate date; // 날짜
    private String subjectTitle; // 과목명
    private int day;    // 요일
    private int period; // 교시

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    public LessonMemo(Member member, LocalDate date, String subjectTitle, int day, int period, String content) {
        this.member = member;
        this.date = date;
        this.subjectTitle = subjectTitle;
        this.day = day;
        this.period = period;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
