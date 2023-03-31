/*
* 2023.03.20
* 로그인 컨트롤러
*/
package com.tt.mspp.controller;

import com.tt.mspp.dao.DAO;
import com.tt.mspp.dto.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;


@WebServlet
@RequestScope
@Controller
public class loginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/kakaoLogin")
    public String KakaoRegister(HttpServletRequest request, HttpServletResponse res,HttpSession session) throws ServletException, IOException {
        String id = request.getParameter("id");
        System.out.println(id);
        DAO dao = DAO.getInstance();
        UserDTO userdto = new UserDTO();
        userdto.setU_id(id);
        userdto.setU_path("K");

        System.out.println(userdto.getU_id());
        System.out.println(userdto.getU_path());

        int num = dao.KakaoLogin(userdto);
        if (num == 0) {           //0번재 없으면 id 넣고, 로그인 유형 카톡으로 해서 회원가입 끝
            dao.KakaoRegister(userdto);
            session.setAttribute("sessionid", userdto.getU_id());
        } else if (num == 1) {       //1번째 기존 정보 있어서 바로 로그인 및 세션 유지
            session.setAttribute("sessionid", userdto.getU_id());
        }
        return "index";
    }

    //로그인 버튼
    @PostMapping("/loginAuth")
    public String loginAuth(@ModelAttribute UserDTO userdto, HttpSession session) {
        DAO dao = DAO.getInstance();
        boolean check;
        check = dao.CheckLogin(userdto);
        if (check) {//check가 ture면 로그인 성공
            session.setAttribute("sessionid", userdto.getU_id());
            System.out.print((String) session.getAttribute("sessionid"));
            return "index";
        } else                    //check가 false면 로그인 실패
            return "/login/loginFail";
    }

    //회원가입 버튼
    @PostMapping("/register")
    public String register(@ModelAttribute UserDTO userdto) {
        DAO dao = DAO.getInstance();
        int check;
        check = dao.InsertUser(userdto);
        if(check==1)               //check가 ture면 회원가입 성공
            return "/login/registerSuccess";
        else                       //check가 false면 회원가입 실패
            return "/login/registerFail";
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

    /* 스크립트 바로 띄우는 법
     return "<script>alert('이미 존재하는 아이디입니다.');</script>";
     */

       /*
        String user_id = "test123" ;
        String user_name = "튜나" ;
        // 세션값 설정
        session.setAttribute("user_id", user_id);
        session.setAttribute("user_name", user_name);
        // 세션 유지시간 설정(초단위)
        // 60 * 30 = 30분
        session.setMaxInactiveInterval(30*60);
        // 세션 시간을 무한대로 설정
        session.setMaxInactiveInterval(-1);
        // 세션에 저장된 값 가져오기
        session.getAttribute("user_id");
        session.getAttribute("user_name");
        // 세션값 삭제
        session.removeAttribute("user_id");
        // 세션 전체 제거, 무효화
        session.invalidate();
   */
}
