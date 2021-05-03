package com.moses.classdiary.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Attendance {
    @Id @GeneratedValue
    private Long id;
    @Temporal(TemporalType.DATE)
    private LocalDateTime date; // 출결 날짜
    @Enumerated
    private AttendanceStatus status;    // 출결 상태
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
