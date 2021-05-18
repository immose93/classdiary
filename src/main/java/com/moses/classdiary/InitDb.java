package com.moses.classdiary;

import com.moses.classdiary.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public  void dbInit1(){
            Member member = new Member();
            member.setUsername("test");
            member.setPassword("test123@");
            member.setName("테스트");
            member.setEmail("test@gmail.com");
            member.setSchoolName("테스트초");
            member.setGrade(5);
            member.setClassNum(3);
            em.persist(member);
        }
    }
}
