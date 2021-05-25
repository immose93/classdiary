package com.moses.classdiary;

import com.moses.classdiary.entity.*;
import com.moses.classdiary.repository.StudentRepository;
import com.moses.classdiary.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.initAuthorities();
        Member member = initService.initMember();
        initService.initStudent(member);
        initService.initSurvey(member);
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;
        private final StudentRepository studentRepository;
        private final SurveyRepository surveyRepository;

        public void initAuthorities(){
            em.persist(new Authority("ROLE_USER"));
            em.persist(new Authority("ROLE_ADMIN"));
        }

        public Member initMember(){
            Member member = new Member();
            member.setUsername("admin");
            member.setPassword(passwordEncoder.encode("admin123@"));
            member.setName("관리자");
            member.setEmail("admin@gmail.com");
            member.setSchoolName("테스트초");
            member.setGrade(5);
            member.setClassNum(3);
            member.setAuthorities(new HashSet<>(Arrays.asList(new Authority("ROLE_USER"), new Authority("ROLE_ADMIN"))));
            member.setActivated(true);
            em.persist(member);
            return member;
        }

        public void initStudent(Member member){
            List<Student> students = new ArrayList<>();
            for (Integer i = 1; i <= 5; i++){
                Student student = new Student();
                student.setName("학생" + i);
                student.setNumber(i);
                student.setGender(Gender.MALE);
                student.setContact(new Contact("010-1234-987" + i.toString(), "", ""));
                student.setMember(member);
                students.add(student);
            }
            studentRepository.saveAll(students);
        }

        public void initSurvey(Member member) {
            List<Survey> surveys = new ArrayList<>();
            List<Student> students = studentRepository.findStudentsByMemberOrderByNumber(member);

            for (Integer i = 1; i <= 3; i++) {
                Survey survey = new Survey(member, "안내장" + i);
                for (Student student : students) {
                    survey.getSubmissionInfos().add(new SubmissionInfo(student.getNumber(), student.getName(), false));
                }
                surveys.add(survey);
            }

            surveyRepository.saveAll(surveys);
        }
    }
}
