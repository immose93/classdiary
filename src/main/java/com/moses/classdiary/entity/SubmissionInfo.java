package com.moses.classdiary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionInfo {
    @Id @GeneratedValue
    private Long id;
    private Integer studentNumber;
    private String studentName;
    private Boolean submit;

    public SubmissionInfo(Integer studentNumber, String studentName, Boolean submit) {
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.submit = submit;
    }

    public void update(SubmissionInfo submissionInfo) {
        this.submit = submissionInfo.submit;
    }
}
