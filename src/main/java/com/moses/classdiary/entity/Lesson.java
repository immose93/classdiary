package com.moses.classdiary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id @GeneratedValue
    private Long id;            // PK, 식별자
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;      // 선생님
    private String type;        // 시간표 유형 (기초, 전담, 특별실)
    private int day;            // 요일 (1~5 = 월~금)
    private int period;         // 교시 (1~6)
    private String subjectTitle;// 과목명

    public Lesson(Member member, String type, int day, int period, String subjectTitle) {
        this.member = member;
        this.type = type;
        this.day = day;
        this.period = period;
        this.subjectTitle = subjectTitle;
    }
}
