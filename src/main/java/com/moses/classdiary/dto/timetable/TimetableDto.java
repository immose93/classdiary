package com.moses.classdiary.dto.timetable;

import com.moses.classdiary.entity.Lesson;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class TimetableDto {
    private String[][] timetable;
    private String type;

    public TimetableDto(List<Lesson> lessons) {
        this.type = lessons.get(0).getType();
        this.timetable = new String[7][6];
        for (Lesson lesson : lessons) {
            timetable[lesson.getPeriod()][lesson.getDay()] = lesson.getSubjectTitle();
        }
    }
}
