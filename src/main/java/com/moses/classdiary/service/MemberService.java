package com.moses.classdiary.service;

import com.moses.classdiary.dto.member.SignUpMemberDto;
import com.moses.classdiary.entity.Authority;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.repository.MemberRepository;
import com.moses.classdiary.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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

}
