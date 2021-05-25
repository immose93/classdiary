package com.moses.classdiary.dto.survey;

import com.moses.classdiary.entity.SubmissionInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SurveyDto {
    private String title;   // 안내장 이름
    private List<SubmissionInfo> submissionInfos;   // 안내장 제출여부 리스트
}
