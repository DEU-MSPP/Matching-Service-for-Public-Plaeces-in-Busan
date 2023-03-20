package com.tt.mspp.controller;

import com.tt.mspp.dao.DAO;
import com.tt.mspp.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller // 컨트롤러 선언
public class FirstController {
    @GetMapping("/hi")  // hi라는 url을 만나면 greetings를 찾아서 반환
    public String niceToMeetYou(Model model) {
        model.addAttribute("username","정현수");
        return "greetings";  //templates/greetings.mustache -> 찾아서 브라우저로 전송
    }

    @GetMapping("/bye")  // hi라는 url을 만나면 greetings를 찾아서 반환
    public String goodBye(Model model) {

        //DB 연결
        DAO dao = DAO.getInstance();
        List<UserDTO> userlist = dao.getUserList();


        model.addAttribute("username",userlist.get(0).getU_name());
        return "goodbye";  //templates/greetings.mustache -> 찾아서 브라우저로 전송
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@ModelAttribute UserDTO userdto) {
        DAO dao = DAO.getInstance();
        dao.InsertUser(userdto);
        return "success";
    }

    /* DB 사용법
       1. post맵핑, reponse 바디 쓰기
       2. 입력 form 값에서 id를 dto와 똑같이
       3. dao로 sql문 실행
       예시)
    @PostMapping("/register")
    @ResponseBody
    public String register(@ModelAttribute UserDTO userdto) {
        DAO dao = DAO.getInstance();
        dao.InsertUser(userdto);
        return "success";
    }*/

}
