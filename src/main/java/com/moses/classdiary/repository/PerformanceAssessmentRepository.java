package com.moses.classdiary.repository;

import com.moses.classdiary.entity.Member;
import com.moses.classdiary.entity.PerformanceAssessment;
import com.moses.classdiary.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceAssessmentRepository extends JpaRepository<PerformanceAssessment, Long> {
    List<PerformanceAssessment> findAllByMemberAndTitleOrderByStudent(Member member, String title);
    void deleteAllByMemberAndTitle(Member member, String title);
    List<PerformanceAssessment> findAllByStudent(Student student);
}
