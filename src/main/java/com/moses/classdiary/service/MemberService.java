package com.moses.classdiary.service;

import com.moses.classdiary.dto.attendance.AttendanceDto;
import com.moses.classdiary.dto.member.SignUpMemberDto;
import com.moses.classdiary.dto.student.StudentDto;
import com.moses.classdiary.entity.Attendance;
import com.moses.classdiary.entity.Authority;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.entity.Student;
import com.moses.classdiary.repository.AttendanceRepository;
import com.moses.classdiary.repository.MemberRepository;
import com.moses.classdiary.repository.StudentRepository;
import com.moses.classdiary.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    /**
     * 회원 가입
     * @param signUpMemberDto - 회원가입 DTO
     * @return 가입한 회원 객체
     */
    @Transactional
    public Member signup(SignUpMemberDto signUpMemberDto){
        // DTO -> Member
        Member member = new Member();
        member.setUsername(signUpMemberDto.getUsername());
        member.setName(signUpMemberDto.getName());
        member.setEmail(signUpMemberDto.getEmail());
        member.setSchoolName(signUpMemberDto.getSchoolName());
        member.setGrade(signUpMemberDto.getGrade());
        member.setClassNum(signUpMemberDto.getClassNum());
        member.setActivated(true);

        // 이미 가입되어 있는 아이디인지 확인
        if(memberRepository.findOneWithAuthoritiesByUsername(member.getUsername()).orElse(null) != null){
            throw new RuntimeException("이미 가입되어 있는 회원입니다.");
        }

        // 비밀번호 암호화
        member.setPassword(passwordEncoder.encode(signUpMemberDto.getPassword()));

        // 권한 설정
        member.setAuthorities(Collections.singleton(new Authority("ROLE_USER")));

        // DB에 저장
        return memberRepository.save(member);
    }

    /**
     * username을 기준으로 Member를 가져오는 메소드
     * @param username - username
     * @return Member 객체
     */
    @Transactional(readOnly = true)
    public Optional<Member> getUserWithAuthorities(String username){
        return memberRepository.findOneWithAuthoritiesByUsername(username);
    }

    /**
     * 현재 SecutiryContext에 저장된 username의 정보(Member)만 가져옴
     * @return Member
     */
    @Transactional(readOnly = true)
    public Optional<Member> getMyUserWithAuthorities(){
        return SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneWithAuthoritiesByUsername);
    }

    @Transactional
    public List<AttendanceDto> getAttendances(Member member, LocalDateTime date) {
        // id로 학생 목록 가져오기
        List<Student> students = studentRepository.findStudentsByMember(member);
        // 번호순 정렬
        students.sort(Comparator.comparing(Student::getNumber));
        // 학생 id와 date로 해당 날짜 출결 상태 가져오기
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        for(Student student : students){
            Optional<Attendance> attendance = attendanceRepository.findByStudentAndDate(student, date);
            // 해당 날짜에 대한 출결 정보가 없으면 생성
            if(attendance.isEmpty()){
                attendance = Optional.of(attendanceRepository.save(new Attendance(student, date)));
            }
            // Attendance -> DTO
            AttendanceDto attendanceDto = new AttendanceDto(
                    attendance.get().getStudent().getNumber(),
                    attendance.get().getStudent().getName(),
                    attendance.get().getStatus(),
                    attendance.get().getTemperature1(),
                    attendance.get().getTemperature2()
            );
            // 리스트에 DTO 추가
            attendanceDtoList.add(attendanceDto);
        }
        return attendanceDtoList;
    }

    @Transactional
    public Optional<Member> setAttendances(Member member, List<AttendanceDto> attendanceDtoList) {

        return Optional.of(member);
    }

    /**
     * 학생 데이터 DB에 저장
     * @param member - 담임 선생님
     * @param studentDtoList - 학생 데이터 DTO 목록
     * @return Member
     */
    @Transactional
    public Member setStudents(Member member, List<StudentDto> studentDtoList) {
        for (StudentDto studentDto : studentDtoList){
            // student의 이름과 member(담임)로 기존 학생 불러오기
            Optional<Student> student = studentRepository.findStudentByNameAndMember(studentDto.getName(), member);
            // 기존에 없는 학생이면 새로 추가
            if (student.isEmpty()){
                Student newStudent = new Student(
                        studentDto.getName(),
                        studentDto.getNumber(),
                        studentDto.getGender(),
                        studentDto.getContact(),
                        member
                );
                studentRepository.save(newStudent);
            }   // 기존에 있는 학생이면 정보 수정
            else {
                student.get().setName(studentDto.getName());
                student.get().setNumber(studentDto.getNumber());
                student.get().setGender(studentDto.getGender());
                student.get().setContact(studentDto.getContact());
            }
        }
        return member;
    }

    /**
     * 반 학생들 데이터 불러오기
     * @param member - 담임 객체
     * @return List<StudentDto>
     */
    public List<StudentDto> getStudents(Member member) {
        List<Student> findStudents = studentRepository.findStudentsByMember(member);
        return findStudents.stream()
                .map(s -> new StudentDto(s.getNumber(), s.getName(), s.getGender(), s.getContact()))
                .collect(Collectors.toList());
    }

    /**
     * 회원 탈퇴
     * @param member - 탈퇴하려는 회원 객체
     */
    public void delete(Member member) {
        memberRepository.delete(member);
    }
}
