package com.moses.classdiary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Survey {
    @Id @GeneratedValue
    private Long id;        // PK

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;  // 담당 선생님 (FK)

    private String title;   // 안내장 이름

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubmissionInfo> submissionInfos = new ArrayList<>(); // 안내장 제출여부 리스트

    public Survey(Member member, String title) {
        this.member = member;
        this.title = title;
    }
}
