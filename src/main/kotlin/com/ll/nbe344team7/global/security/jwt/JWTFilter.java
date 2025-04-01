package com.ll.nbe344team7.global.security.jwt;

import com.ll.nbe344team7.global.redis.RedisRepository;
import com.ll.nbe344team7.global.security.dto.CustomUserData;
import com.ll.nbe344team7.global.security.dto.CustomUserDetails;
import com.ll.nbe344team7.global.security.exception.SecurityException;
import com.ll.nbe344team7.global.security.exception.SecurityExceptionCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * jwt 를 검증 및 CustomUserDetails 저장하는  클래스
 *
 * @since 2025-03-26
 * @author 이광석
 */
public class JWTFilter extends OncePerRequestFilter {

    final private JWTUtil jwtUtil;
    final private RedisRepository redisRepository;

    public JWTFilter(JWTUtil jwtUtil,RedisRepository redisRepository) {
        this.jwtUtil = jwtUtil;
        this.redisRepository = redisRepository;
    }

    /**
     * Jwt를 검증 및 CustomUserDetails 저장하는 메소드
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     *
     * @since 2025-03-26
     * @author 이광석
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        List<String> noCertifiedUrls = new ArrayList<>();
        noCertifiedUrls.add("/api/auth/login");
        noCertifiedUrls.add("/h2-console");
        noCertifiedUrls.add("/api/auth/register");
        noCertifiedUrls.add("/");
        noCertifiedUrls.add("/ws/**");

        for (String noCertifiedUrl : noCertifiedUrls){
            if(request.getServletPath().contains(noCertifiedUrl)){
                filterChain.doFilter(request,response);
                return;
            }
        }


       String accessToken = request.getHeader("access");

       String refreshToken = getRefreshToken(request.getCookies());



       //토큰 존재 확인
       if(accessToken==null){
           filterChain.doFilter(request,response);
           return;
       }

       // 토큰 만료 확인
       try{
           jwtUtil.isExpired(accessToken);
       }catch (ExpiredJwtException e){
           throw new SecurityException(SecurityExceptionCode.ACCESSTOKEN_IS_EXPIRED);
       }

       // 토큰 종류 확인
       if(!jwtUtil.getCategory(accessToken).equals("access")){
           throw new SecurityException(SecurityExceptionCode.NOT_ACCESSTOKEN);
       }

       //Db와 비교
       if(!accessToken.equals(redisRepository.get(refreshToken))){
           redisRepository.delete(refreshToken);
           throw new SecurityException(SecurityExceptionCode.TOKEN_MISMATCH);
       }

        String username = jwtUtil.getUsername(accessToken);
        Long memberId= jwtUtil.getMemberId(accessToken);
        String role = jwtUtil.getRole(accessToken);


        CustomUserData customUserData = new CustomUserData(memberId,username,role,"tmp");

        CustomUserDetails customUserDetails = new CustomUserDetails(customUserData);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails,null,customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request,response);

    }


    /**
     * refresh 토큰 추출 메소드
     * @param cookies
     * @return
     * @author 이광석
     * @since 2025-03-28
     */
    private String getRefreshToken(Cookie[] cookies){

        for(Cookie cookie: cookies){
            if(cookie.getName().equals("refresh")){
                return cookie.getValue();
            }
        }

        throw new SecurityException(SecurityExceptionCode.NOT_FOUND_REFRESHTOKEN);
    }


}
