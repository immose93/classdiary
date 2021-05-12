package com.moses.classdiary.service;

import com.moses.classdiary.entity.Member;
import com.moses.classdiary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * @param member - Member 객체
     * @return 회원 식별자(PK)
     */
    public Long signup(Member member){
        validateDuplicateMember(member);    // 중복 회원 검증
        // 비밀번호 암호화

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검증
     * @param member - Member 객체
     */
    private void validateDuplicateMember(Member member) {
        // 실무에서는 멀티쓰레드 환경 등으로 인해 두명이 동시에 가입할 수도 있어서, member의 username에 유니크 제약조건을 걸어야 함
        List<Member> findMembers = memberRepository.findByUsername(member.getUsername());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
