package com.moses.classdiary.repository;

import com.moses.classdiary.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByMemberIdAndDate(Long memberId, LocalDate dateTime);
}
