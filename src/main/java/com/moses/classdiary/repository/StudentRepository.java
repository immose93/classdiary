package com.moses.classdiary.repository;

import com.moses.classdiary.entity.Member;
import com.moses.classdiary.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByNameAndMember(String name, Member member);
    List<Student> findStudentsByMember(Member member);
}
