package com.moses.classdiary.dto.performanceassessment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceAssessmentUpdateDto {
    private String title;
    private List<PerformanceAssessmentDto> performanceAssessmentDtoList;
}
