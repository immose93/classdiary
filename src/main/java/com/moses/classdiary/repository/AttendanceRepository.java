package com.moses.classdiary.repository;

import com.moses.classdiary.entity.Attendance;
import com.moses.classdiary.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByStudentAndDate(Student student, LocalDateTime dateTime);
}
