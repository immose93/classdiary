package com.moses.classdiary;

import com.moses.classdiary.entity.Authority;
import com.moses.classdiary.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Collections;

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
        private final PasswordEncoder passwordEncoder;

        public  void dbInit1(){

            em.persist(new Authority("ROLE_USER"));
            em.persist(new Authority("ROLE_ADMIN"));

            Member member = new Member();
            member.setUsername("test");
            member.setPassword(passwordEncoder.encode("test123@"));
            member.setName("테스트");
            member.setEmail("test@gmail.com");
            member.setSchoolName("테스트초");
            member.setGrade(5);
            member.setClassNum(3);
            member.setAuthorities(Collections.singleton(new Authority("ROLE_USER")));
            member.setActivated(true);
            em.persist(member);
        }
    }
}
