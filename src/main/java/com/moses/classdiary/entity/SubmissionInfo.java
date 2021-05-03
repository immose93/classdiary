package com.moses.classdiary.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SubmissionInfo {
    @Id @GeneratedValue
    private Long id;
    private Integer studentNumber;
    private String studentName;
    private Boolean submit;
}
