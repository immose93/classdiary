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
    private Attendance.AttendanceStatus attendanceStatus;
    private double temperature1;
    private double temperature2;
}
