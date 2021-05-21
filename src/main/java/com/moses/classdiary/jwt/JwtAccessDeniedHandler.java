package com.moses.classdiary.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 필요한 권한이 존재하지 않는 경우 403 Forbidden 에러를 리턴
     * @param request - req
     * @param response - res
     * @param accessDeniedException - accessDeniedException
     * @throws IOException - IOException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
