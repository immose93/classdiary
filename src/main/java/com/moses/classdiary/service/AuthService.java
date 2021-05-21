package com.moses.classdiary.service;

import com.moses.classdiary.dto.jwt.TokenDto;
import com.moses.classdiary.dto.jwt.TokenRequestDto;
import com.moses.classdiary.dto.member.LoginMemberDto;
import com.moses.classdiary.dto.member.MemberResponseDto;
import com.moses.classdiary.dto.member.SignUpMemberDto;
import com.moses.classdiary.entity.Authority;
import com.moses.classdiary.entity.Member;
import com.moses.classdiary.entity.RefreshToken;
import com.moses.classdiary.jwt.TokenProvider;
import com.moses.classdiary.repository.MemberRepository;
import com.moses.classdiary.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     * @param signUpMemberDto - 회원가입 DTO
     * @return 가입한 회원 객체
     */
    @Transactional
    public MemberResponseDto signup(SignUpMemberDto signUpMemberDto){
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
        memberRepository.save(member);

        return MemberResponseDto.of(member);
    }

    @Transactional
    public TokenDto login(LoginMemberDto loginMemberDto) {
        // 로그인 정보를 기반으로 Authentication Token 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginMemberDto.getUsername(), loginMemberDto.getPassword());

        // 검증 (사용자 비밀번호 체크)
        //      authenticate 메소드 -> CustomUserDetailsService 의 loadUserByUsername 메소드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 jwt 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // Refresh Token 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        // 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // DB 에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // DB 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}
