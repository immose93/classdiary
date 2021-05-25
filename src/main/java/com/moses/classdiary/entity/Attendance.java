package com.moses.classdiary.entity;

import com.moses.classdiary.dto.attendance.AttendanceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Attendance {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
    private Student student;
    private Long memberId;
    private LocalDate date; // 출결 날짜
    @Enumerated
    private AttendanceStatus status;    // 출결 상태
    private Double temperature1;  // 체온 1차 측정
    private Double temperature2;  // 체온 2차 측정

    public Attendance(Student student, LocalDate date) {
        setStudent(student);
        this.memberId = student.getMember().getId();
        this.date = date;
        this.status = AttendanceStatus.UNKNOWN;
        this.temperature1 = 0.0;
        this.temperature2 = 0.0;
    }

    /**
     * 학생 지정 메소드
     * @param student - 학생 객체
     */
    public void setStudent(Student student) {
        this.student = student;
        if(!student.getAttendances().contains(this)){
            student.addAttendance(this);
        }
    }

    public void update(AttendanceDto attendanceDto) {
        this.status = attendanceDto.getStatus();
        this.temperature1 = attendanceDto.getTemperature1();
        this.temperature2 = attendanceDto.getTemperature2();
    }

    // 미확인, 출석, 지각(미인정), 지각(질병), 결석(미인정), 결석(질병), 결과(미인정), 결과(질병), 조퇴(미인정), 조퇴(질병)
    public enum AttendanceStatus {
        UNKNOWN,
        PASS,
        UNRECOGNIZED_LATE, ILLNESS_LATE,
        UNRECOGNIZED_ABSENCE, ILLNESS_ABSENCE,
        UNRECOGNIZED_CLASS_ABSENCE, ILLNESS_CLASS_ABSENCE,
        UNRECOGNIZED_EARLY_LEAVE, ILLNESS_EARLY_LEAVE
    }
}
