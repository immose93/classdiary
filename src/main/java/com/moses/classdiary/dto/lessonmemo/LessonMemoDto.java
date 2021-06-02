package com.moses.classdiary.dto.lessonmemo;

import com.moses.classdiary.entity.LessonMemo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonMemoDto {
    private String subjectTitle;
    private int period;
    private String content;

    public LessonMemoDto(LessonMemo lessonMemo) {
        this.subjectTitle = lessonMemo.getSubjectTitle();
        this.period = lessonMemo.getPeriod();
        this.content = lessonMemo.getContent();
    }
}
