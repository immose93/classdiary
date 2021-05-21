package com.moses.classdiary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Student {
    @Id @GeneratedValue
    private Long id;        // PK, 식별자
    private String name;    // 학생 이름
    private Integer number; // 학번
    @Enumerated
    private Gender gender;  // 성별
    @Embedded
    private Contact contact;// 연락처(학생, 비상1, 비상2)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    private Member member; // 담임 선생님
    @OneToMany(mappedBy = "student")
    private List<Attendance> attendances;   // 출결 및 체온 리스트

    public Student(String name, Integer number, Gender gender, Contact contact, Member member) {
        this.name = name;
        this.number = number;
        this.gender = gender;
        this.contact = new Contact(contact);
        this.member = member;
        this.attendances = new ArrayList<>();
    }

    public void setContact(Contact contact) {
        if(this.contact == null) this.contact = new Contact();
        if(contact.getContact() != null) this.contact.setContact(contact.getContact());
        if(contact.getEmergency1() != null) this.contact.setEmergency1(contact.getEmergency1());
        if(contact.getEmergency2() != null) this.contact.setEmergency2(contact.getEmergency2());
    }

    /**
     * 담임 지정 메소드
     * @param member - 담임 객체
     */
    public void setMember(Member member){
        this.member = member;
        if(!member.getStudents().contains(this)){
            member.getStudents().add(this);
        }
    }

    /**
     * 출결 정보 추가 메소드
     * @param attendance - 출결 정보
     */
    public void addAttendance(Attendance attendance){
        this.attendances.add(attendance);
        if(attendance.getStudent() != this){
            attendance.setStudent(this);
        }
    }
}
