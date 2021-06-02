package com.moses.classdiary.dto.performanceassessment;

import com.moses.classdiary.entity.PerformanceAssessment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceAssessmentDto {
    private int studentNum;
    private String studentName;
    private PerformanceAssessment.Grade grade;
    private String title;

    public PerformanceAssessmentDto(PerformanceAssessment performanceAssessment) {
        this.studentNum = performanceAssessment.getStudent().getNumber();
        this.studentName = performanceAssessment.getStudent().getName();
        this.grade = performanceAssessment.getGrade();
        this.title = performanceAssessment.getTitle();
    }
}
