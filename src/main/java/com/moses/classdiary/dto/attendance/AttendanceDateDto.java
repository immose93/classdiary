package com.moses.classdiary.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDateDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
