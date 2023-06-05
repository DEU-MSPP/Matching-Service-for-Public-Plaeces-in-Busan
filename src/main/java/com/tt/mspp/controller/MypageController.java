package com.tt.mspp.controller;

import com.tt.mspp.dao.DAO;
import com.tt.mspp.dao.MatchDAO;
import com.tt.mspp.dto.MatchDTO;
import com.tt.mspp.dto.PlaceDTO;
import com.tt.mspp.dto.RoomDTO;
import com.tt.mspp.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MypageController {
    @GetMapping("/mypage")
    public String mypage(Model model, HttpSession session) {
        String id = (String) session.getAttribute("sessionid");
        DAO dao = DAO.getInstance();
        List<UserDTO> userList = dao.getUserList();
        List<UserDTO> userInfo = new ArrayList<>();
        for(UserDTO user : userList){
            String u_id = user.getU_id();
            if (id.equals(u_id)) {
                userInfo.add(user);
                break;
            }
        }
        model.addAttribute("userInfo", userInfo);
        return "mypage";
    }

    @PostMapping("/mypage.do")
    public String insertMatch(@RequestParam("u_id") String u_id, @RequestParam("u_pw") String u_pw,
                              @RequestParam("u_name") String u_name, @RequestParam("u_nick") String u_nick,
                              @RequestParam("u_phone") String u_phone) {

        DAO dao = DAO.getInstance();
        boolean result = dao.updateUser(u_id, u_pw, u_name, u_nick, u_phone);
        if(result){
            System.out.println("정보 수정 성공");
        }
        return "redirect:/";
    }
}


