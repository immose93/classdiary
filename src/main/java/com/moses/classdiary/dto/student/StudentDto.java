package com.moses.classdiary.dto.student;

import com.moses.classdiary.entity.Contact;
import com.moses.classdiary.entity.Gender;
import com.moses.classdiary.entity.Student;
import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Enumerated;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Integer number; // 번호
    private String name;    // 이름
    @Enumerated
    private Gender gender;  // 성별
    @Embedded
    private Contact contact;// 연락처(학생, 비상1, 비상2)

    public StudentDto(Student student) {
        this.number = student.getNumber();
        this.name = student.getName();
        this.gender = student.getGender();
        this.contact = student.getContact();
    }
}
