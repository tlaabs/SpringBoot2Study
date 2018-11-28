package com.web.controller;

import com.web.annotation.SocialUser;
import com.web.domain.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginComplete(@SocialUser User user){
        return "redirect:/board/list";
    }

    //인증 성공적으로 처리된 후 리다이렉트되는 경로
//    @GetMapping(value = "/{facebook|google|kakao}/complete")
//    public String loginComplete(HttpSession session) {
//        OAuth2Authentication authentication = (OAuth2Authentication)
//                SecurityContextHolder.getContext().getAuthentication();
//        Map<String, String> map = (HashMap<String, String>) authentication.getUserAuthentication().getDetails(); //리소스 서버에서 받아온 개인정보를 Map 타입으로 받는다.
//        session.setAttribute("user", User.builder()
//                .name(map.get("name"))
//                .email(map.get("email"))
//                .principal(map.get("id"))
//                .socialType(SocialType.FACEBOOK)
//                .createdDate(LocalDateTime.now())
//                .build()
//        );
//        return "redirect:/board/list";
//    }
}
