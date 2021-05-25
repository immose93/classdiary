package com.moses.classdiary.dto.attendance;

import com.moses.classdiary.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private Integer studentNumber;
    private String studentName;
    private Attendance.AttendanceStatus status;
    private double temperature1;
    private double temperature2;

    public AttendanceDto(Attendance attendance) {
        this.studentNumber = attendance.getStudent().getNumber();
        this.studentName = attendance.getStudent().getName();
        this.status = attendance.getStatus();
        this.temperature1 = attendance.getTemperature1();
        this.temperature2 = attendance.getTemperature2();
    }
}
