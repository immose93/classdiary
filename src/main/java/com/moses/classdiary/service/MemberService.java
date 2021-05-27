package com.moses.classdiary.service;

import com.moses.classdiary.dto.member.MemberResponseDto;
import com.moses.classdiary.dto.member.MemberUpdateDto;
import com.moses.classdiary.dto.student.StudentDto;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.entity.Student;
import com.moses.classdiary.repository.MemberRepository;
import com.moses.classdiary.repository.StudentRepository;
import com.moses.classdiary.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * username 을 기준으로 Member 를 가져오는 메소드
     * @param username - username
     * @return Member 객체
     */
    @Transactional(readOnly = true)
    public MemberResponseDto getMemberInfo(String username){
        return memberRepository.findByUsername(username)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("해당 유저 정보가 없습니다."));
    }

    /**
     * 현재 SecurityContext 에 저장된 유저의 정보를 가져오는 메소드
     * @return Member
     */
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo(){
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    /**
     * 학생 데이터 DB에 저장
     * @param studentDtoList - 학생 데이터 DTO 목록
     */
    @Transactional
    public void setStudents(List<StudentDto> studentDtoList) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        for (StudentDto studentDto : studentDtoList){
            // student 의 이름과 member(담임)로 기존 학생 불러오기
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
    }

    /**
     * 반 학생들 데이터 불러오기
     * @return List<StudentDto>
     */
    @Transactional(readOnly = true)
    public List<StudentDto> getStudents() {
        log.info("SecurityContext current username : " + SecurityUtil.getCurrentMemberId());
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<Student> findStudents = studentRepository.findStudentsByMemberOrderByNumber(member);
        return findStudents.stream()
                .map(StudentDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void delete() {
        memberRepository.deleteById(SecurityUtil.getCurrentMemberId());
    }

    /**
     * 학생 추가 메소드
     * @param studentDto - 추가할 학생 DTO
     * @return 추가 후 학생 목록
     */
    @Transactional
    public List<StudentDto> addStudent(StudentDto studentDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        Student student = new Student(
                studentDto.getName(),
                studentDto.getNumber(),
                studentDto.getGender(),
                studentDto.getContact(),
                member
        );
        studentRepository.save(student);
        return studentRepository.findStudentsByMemberOrderByNumber(member).stream()
                .map(StudentDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 회원 수정 정보 조회 메소드
     * @return 수정을 위한 회원 정보 DTO
     */
    @Transactional(readOnly = true)
    public MemberUpdateDto getUpdateMemberInfo() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        // 비밀번호는 ""로 보냄
        return new MemberUpdateDto(member);
    }

    /**
     * 회원 정보 수정 메소드
     * @param memberUpdateDto - 수정할 내용이 담긴 회원 정보 DTO
     * @return 받았던 DTO 그대로 리턴
     */
    @Transactional
    public MemberUpdateDto updateMember(MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();

        // 수정 불가능한 항목 수정 시 예외처리
        if (!member.getUsername().equals(memberUpdateDto.getUsername())){
            throw new RuntimeException("회원 ID는 변경이 불가능합니다.");
        } else if (!member.getName().equals(memberUpdateDto.getName())) {
            throw new RuntimeException("회원 이름은 변경이 불가능합니다.");
        }

        // 수정 반영 (변경 감지로 DB에 반영됨)
        member.setPassword(passwordEncoder.encode(memberUpdateDto.getPassword()));
        member.setEmail(memberUpdateDto.getEmail());
        member.setSchoolName(memberUpdateDto.getSchoolName());
        member.setGrade(memberUpdateDto.getGrade());
        member.setClassNum(memberUpdateDto.getClassNum());

        return memberUpdateDto;
    }
}
