package com.example.security01.controller;

import com.example.security01.config.auth.PrincipalDetails;
import com.example.security01.model.User;
import com.example.security01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // view를 리턴함
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(principalDetails.getUser());
        System.out.println(userDetails.getUsername());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
        // oauth 로그인은 아래와 같이 캐스팅이 불가능
        // PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println(oauth.getAttributes());
        return "Oauth 세션 정보 확인하기";
    }

    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 기본폴더 src/main/resources
        // 뷰리졸버 설정 : templates(prefix), mustache(suffix)생략 가능
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails" + principalDetails.getUser());
        return "user" + bCryptPasswordEncoder.encode("hello");
    }

    @GetMapping("admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // 스프링 시큐리티가 낚아챔 => securityConfig을 생성하고나서는 낚아채지 않음
    @GetMapping("loginForm")
    public String login() {
        return "loginForm";
    }

    @GetMapping("joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("join")
    public String join(User user) {
        user.setRoles("ROLE_USER");

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);
        return "redirect:/loginForm";
    }

}
