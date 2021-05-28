package com.moses.classdiary.repository;

import com.moses.classdiary.entity.Lesson;
import com.moses.classdiary.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByMemberAndTypeOrderByDayAscPeriodAsc(Member member, String type);
    void deleteAllBySubjectTitle(String subjectTitle);
}
