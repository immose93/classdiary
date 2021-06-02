package com.moses.classdiary.service;

import com.moses.classdiary.dto.performanceassessment.PerformanceAssessmentDto;
import com.moses.classdiary.dto.performanceassessment.PerformanceAssessmentUpdateDto;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.entity.PerformanceAssessment;
import com.moses.classdiary.entity.Student;
import com.moses.classdiary.repository.MemberRepository;
import com.moses.classdiary.repository.PerformanceAssessmentRepository;
import com.moses.classdiary.repository.StudentRepository;
import com.moses.classdiary.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceAssessmentService {
    private final PerformanceAssessmentRepository performanceAssessmentRepository;
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;

    /**
     * 평가 항목 추가 메소드
     * @param title - 추가할 평가 항목 이름
     * @return 평가 기록 DTO
     */
    @Transactional
    public List<PerformanceAssessmentDto> addPerformanceAssessment(String title) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<Student> students = studentRepository.findStudentsByMemberOrderByNumber(member);
        List<PerformanceAssessment> performanceAssessmentList = new ArrayList<>();
        for (Student student : students) {
            performanceAssessmentList.add(new PerformanceAssessment(title, member, student, PerformanceAssessment.Grade.UNKNOWN));
        }
        performanceAssessmentRepository.saveAll(performanceAssessmentList);
        return performanceAssessmentList.stream().map(PerformanceAssessmentDto::new).collect(Collectors.toList());
    }

    /**
     * 평가 항목 리스트 조회 메소드
     * @return 평가 항목 이름 리스트
     */
    @Transactional(readOnly = true)
    public List<String> getPerformanceAssessmentList() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        Student student = studentRepository.findTopByMember(member);
        return performanceAssessmentRepository.findAllByStudent(student).stream()
                .map(PerformanceAssessment::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * 평가 항목 조회 메소드
     * @param title - 조회할 평가 항목 이름
     * @return 평가 기록 DTO
     */
    @Transactional(readOnly = true)
    public List<PerformanceAssessmentDto> getPerformanceAssessment(String title) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<PerformanceAssessment> performanceAssessmentList = performanceAssessmentRepository.findAllByMemberAndTitleOrderByStudent(member, title);
        log.info(performanceAssessmentList.toString());
        return performanceAssessmentList.stream().map(PerformanceAssessmentDto::new).collect(Collectors.toList());
    }

    /**
     * 평가 기록 수정 메소드
     * @param performanceAssessmentUpdateDto - 수정사항이 담긴 평가 기록 DTO
     * @return 평가 기록 DTO 그대로 리턴
     */
    @Transactional
    public PerformanceAssessmentUpdateDto updatePerformanceAssessment(PerformanceAssessmentUpdateDto performanceAssessmentUpdateDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<PerformanceAssessment> performanceAssessmentList = performanceAssessmentRepository.findAllByMemberAndTitleOrderByStudent(member, performanceAssessmentUpdateDto.getTitle());
        int idx = 0;
        for (PerformanceAssessmentDto performanceAssessmentDto : performanceAssessmentUpdateDto.getPerformanceAssessmentDtoList()) {
            performanceAssessmentList.get(idx++).update(performanceAssessmentDto.getGrade());   // 변경 감지로 DB에 수정사항 반영
        }
        return performanceAssessmentUpdateDto;
    }

    /**
     * 평가 항목 삭제 메소드
     * @param title - 삭제할 평가 항목 이름
     * @return 삭제 후 평가 항목 이름 리스트
     */
    public List<String> deletePerformanceAssessment(String title) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        performanceAssessmentRepository.deleteAllByMemberAndTitle(member, title);
        return getPerformanceAssessmentList();
    }
}
