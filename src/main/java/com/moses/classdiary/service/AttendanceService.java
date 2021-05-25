package com.moses.classdiary.service;

import com.moses.classdiary.dto.attendance.AttendanceDto;
import com.moses.classdiary.entity.Attendance;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.entity.Student;
import com.moses.classdiary.repository.AttendanceRepository;
import com.moses.classdiary.repository.MemberRepository;
import com.moses.classdiary.repository.StudentRepository;
import com.moses.classdiary.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final MemberRepository memberRepository;
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    /**
     * 출결 정보 조회 메소드 (해당 날짜에 대한 출결 정보가 없으면 생성)
     * @param date - 날짜
     * @return AttendanceDto 의 리스트
     */
    @Transactional
    public List<AttendanceDto> getAttendances(LocalDate date) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<Attendance> attendances = attendanceRepository.findAllByMemberIdAndDate(member.getId(), date);

        // 해당 날짜에 대한 출결 정보가 없으면 생성
        if (attendances.isEmpty()) {
            attendances = makeAttendances(member, date);
            attendanceRepository.saveAll(attendances);
        }

        // 해당 날짜의 출결 상태 DTO 로 변환하여 리턴
        return attendances.stream().map(AttendanceDto::new).collect(Collectors.toList());
        /*
        // id로 학생 목록 가져오기
        List<Student> students = studentRepository.findStudentsByMemberOrderByNumber(member);
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

         */
    }

    /**
     * 해당 날짜에 대한 출결 정보 생성 메소드
     * @param member - 회원
     * @param date - 날짜
     * @return - Attendance 의 리스트
     */
    private List<Attendance> makeAttendances(Member member, LocalDate date) {
        List<Student> students = studentRepository.findStudentsByMemberOrderByNumber(member);
        List<Attendance> attendances = new ArrayList<>();
        for (Student student : students) {
            Attendance attendance = new Attendance(student, date);
            attendances.add(attendance);
        }
        return attendances;
    }

    /**
     * 출결 상태 수정 메소드
     * @param date - 해당 날짜
     * @param attendanceDtoList - 수정 정보가 담긴 AttendanceDto 의 리스트
     */
    @Transactional
    public void updateAttendances(LocalDate date, List<AttendanceDto> attendanceDtoList) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();
        List<Attendance> attendances = attendanceRepository.findAllByMemberIdAndDate(member.getId(), date);
        int i = 0;
        for (Attendance attendance : attendances) {
            attendance.update(attendanceDtoList.get(i++));
        }
    }
}
