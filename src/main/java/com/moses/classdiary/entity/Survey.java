package com.moses.classdiary.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Survey {
    @Id @GeneratedValue
    private Long id;        // PK

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;  // 담당 선생님 (FK)

    private String title;   // 안내장 이름

    @OneToMany
    private List<SubmissionInfo> submissionInfos = new ArrayList<>(); // 안내장 제출여부 리스트
}
