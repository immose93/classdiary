package com.moses.classdiary.dto.timetable;

import com.moses.classdiary.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimetableDto {
    private String[][] timetable;
    private String type;

    public TimetableDto(List<Lesson> lessons) {
        this.type = lessons.get(0).getType();
        this.timetable = new String[6][5];
        for (Lesson lesson : lessons) {
            timetable[lesson.getPeriod() - 1][lesson.getDay() - 1] = lesson.getSubjectTitle();
        }
    }
}
