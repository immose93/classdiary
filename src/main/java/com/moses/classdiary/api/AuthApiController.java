package com.moses.classdiary.api;

import com.moses.classdiary.dto.jwt.TokenDto;
import com.moses.classdiary.dto.jwt.TokenRequestDto;
import com.moses.classdiary.dto.member.LoginMemberDto;
import com.moses.classdiary.dto.member.MemberResponseDto;
import com.moses.classdiary.dto.member.SignUpMemberDto;
import com.moses.classdiary.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {
    private final AuthService authService;

    /**
     * 회원가입 API
     * @param signUpMemberDto - 회원가입 정보가 담긴 DTO
     * @return MemberResponseDto 가 담긴 ResponseEntity
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody @Valid SignUpMemberDto signUpMemberDto){
        return ResponseEntity.ok(authService.signup(signUpMemberDto));
    }

    /**
     * 로그인 API
     * @param loginMemberDto - 로그인 정보가 담긴 DTO
     * @return TokenDto가 담긴 ResponseEntity
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginMemberDto loginMemberDto){
        return ResponseEntity.ok(authService.login(loginMemberDto));
    }

    /**
     * 토큰 재발급 API
     * @param tokenRequestDto - 토큰 DTO
     * @return 재발급 된 토큰이 담긴 ResponseEntity
     */
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
