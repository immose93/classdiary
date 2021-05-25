package com.moses.classdiary.repository;

import com.moses.classdiary.entity.Member;
import com.moses.classdiary.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findAllByMember(Member member);
    Optional<Survey> findByTitle(String title);
    void deleteByTitle(String title);
}
