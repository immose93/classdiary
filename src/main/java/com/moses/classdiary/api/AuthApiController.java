package com.moses.classdiary.api;

import com.moses.classdiary.dto.jwt.TokenDto;
import com.moses.classdiary.dto.member.LoginMemberDto;
import com.moses.classdiary.jwt.JwtFilter;
import com.moses.classdiary.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthApiController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 로그인 API
     * @param loginMemberDto - 로그인 정보가 담긴 DTO
     * @return TokenDto가 담긴 ResponseEntity
     */
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginMemberDto loginMemberDto){

        // loginMemberDto로 토큰 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginMemberDto.getUsername(), loginMemberDto.getPassword());

        // authenticate 메소드 -> CustomUserDetailsService의 loadUserByUsername 메소드 실행하여 Authentication 객체 생성
        // 생성된 객체를 SecutiryContext에 저장
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // tokenProvider로 jwt 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        // HTTP Header에 jwt 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // Response body에도 TokenDto로 jwt 추가
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
