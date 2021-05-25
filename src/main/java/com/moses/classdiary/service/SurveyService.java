package com.moses.classdiary.service;

import com.moses.classdiary.dto.survey.SurveyDto;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.entity.Student;
import com.moses.classdiary.entity.SubmissionInfo;
import com.moses.classdiary.entity.Survey;
import com.moses.classdiary.repository.MemberRepository;
import com.moses.classdiary.repository.StudentRepository;
import com.moses.classdiary.repository.SurveyRepository;
import com.moses.classdiary.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;

    /**
     * 안내장 목록 조회 메소드
     * @return 안내장 제목 리스트
     */
    @Transactional(readOnly = true)
    public List<String> getSurveyList() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<Survey> findSurveyList = surveyRepository.findAllByMember(member);
        return findSurveyList.stream()
                .map(Survey::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * 안내장 추가 메소드
     * @param surveyTitle - 추가될 안내장의 제목
     * @return 추가된 안내장 DTO
     */
    @Transactional
    public SurveyDto addSurvey(String surveyTitle) {
        // 안내장 제목으로 이미 등록된 안내장인지 확인
        if (surveyRepository.findByTitle(surveyTitle).orElse(null) != null){
            throw new RuntimeException("이미 등록되어 있는 안내장입니다.");
        }

        // 선생님 = 로그인 된 유저
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        Survey survey = new Survey(member, surveyTitle);
        // 학생리스트로 제출여부 목록 초기화
        List<Student> students = studentRepository.findStudentsByMemberOrderByNumber(member);
        for (Student student : students) {
            survey.getSubmissionInfos().add(new SubmissionInfo(student.getNumber(), student.getName(), false));
        }

        // DB에 추가
        surveyRepository.save(survey);

        return new SurveyDto(survey.getTitle(),survey.getSubmissionInfos());
    }

    /**
     * 안내장 삭제 메소드
     * @param surveyTitle - 삭제할 안내장 제목
     * @return 삭제 후 안내장 제목 리스트
     */
    @Transactional
    public List<String> deleteSurvey(String surveyTitle) {
        surveyRepository.deleteByTitle(surveyTitle);
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        return surveyRepository.findAllByMember(member).stream()
                .map(Survey::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * 안내장 조회 메소드 (안내장 제목으로 조회)
     * @param surveyTitle - 조회할 안내장 제목
     * @return 조회된 안내장 DTO
     */
    @Transactional(readOnly = true)
    public SurveyDto getSurvey(String surveyTitle) {
        Survey survey = surveyRepository.findByTitle(surveyTitle).get();
        return new SurveyDto(survey.getTitle(), survey.getSubmissionInfos());
    }

    /**
     * 안내장 수정 메소드
     * @param surveyDto - 수정에 반영될 안내장 DTO
     * @return 안내장 DTO
     */
    @Transactional
    public SurveyDto updateSurvey(SurveyDto surveyDto) {
        Survey survey = surveyRepository.findByTitle(surveyDto.getTitle()).get();
        int i = 0;
        for (SubmissionInfo submissionInfo : surveyDto.getSubmissionInfos()) {
            survey.getSubmissionInfos().get(i++).update(submissionInfo);
        }

        return surveyDto;
    }
}
