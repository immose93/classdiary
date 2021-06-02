package com.moses.classdiary.dto.lessonmemo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonMemoUpdateDto {
    private List<LessonMemoDto> lessonMemoDtoList;
    private LocalDate date;
}
