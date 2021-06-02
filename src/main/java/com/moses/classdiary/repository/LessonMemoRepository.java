package com.moses.classdiary.repository;

import com.moses.classdiary.entity.LessonMemo;
import com.moses.classdiary.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LessonMemoRepository extends JpaRepository<LessonMemo, Long> {
    List<LessonMemo> findAllByMemberAndDateOrderByPeriod(Member member, LocalDate date);
    void deleteAllByMemberAndSubjectTitleAndDayAndPeriod(Member member, String subjectTitle, int day, int period);
}
