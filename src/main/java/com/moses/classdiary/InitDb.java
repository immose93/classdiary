package com.moses.classdiary;

import com.moses.classdiary.entity.*;
import com.moses.classdiary.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
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
        initService.initAttendance(member);
        initService.initTimetable(member);
        initService.initPerformanceAssessment(member);
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;
        private final StudentRepository studentRepository;
        private final SurveyRepository surveyRepository;
        private final AttendanceRepository attendanceRepository;
        private final LessonRepository lessonRepository;
        private final PerformanceAssessmentRepository performanceAssessmentRepository;

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

        public void initAttendance(Member member) {
            List<Attendance> attendances = new ArrayList<>();
            List<Student> students = studentRepository.findStudentsByMemberOrderByNumber(member);
            for (Student student : students) {
                Attendance attendance = new Attendance(student, LocalDate.now());
                attendances.add(attendance);
            }
            attendanceRepository.saveAll(attendances);
        }

        public void initTimetable(Member member) {
            List<Lesson> lessons = new ArrayList<>();
            List<String> subjectTitles = new ArrayList<>();
            subjectTitles.add("수학");
            subjectTitles.add("영어");
            subjectTitles.add("국어");
            subjectTitles.add("과학");
            subjectTitles.add("사회");
            subjectTitles.add("체육");
            subjectTitles.add("음악");
            int cnt = 0;

            for (int i = 1; i <= 5; i++) {
                for (int j = 1; j <= 6; j++) {
                    if (cnt % 8 == 7) {
                        cnt++;
                        continue;
                    }
                    lessons.add(new Lesson(member, "기초", i, j, subjectTitles.get(cnt++%8)));
                }
            }
            lessonRepository.saveAll(lessons);
        }

        public void initPerformanceAssessment(Member member) {
            List<Student> students = studentRepository.findStudentsByMemberOrderByNumber(member);
            List<PerformanceAssessment> performanceAssessmentList = new ArrayList<>();
            for (Student student : students) {
                performanceAssessmentList.add(new PerformanceAssessment("수학 1차 수행", member, student, PerformanceAssessment.Grade.UNKNOWN));
            }
            performanceAssessmentRepository.saveAll(performanceAssessmentList);
        }
    }
}
