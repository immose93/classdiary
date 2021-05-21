package com.moses.classdiary.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    /**
     * Member 의 PK 를 리턴해주는 메소드
     * @return username
     */
    public static Long getCurrentMemberId(){
        // SecurityContext 에서 Authentication 객체를 가져옴
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }

        return Long.parseLong(authentication.getName());
    }
}
