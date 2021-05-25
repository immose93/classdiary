package com.moses.classdiary.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceUpdateDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<AttendanceDto> attendanceDtoList;
}
