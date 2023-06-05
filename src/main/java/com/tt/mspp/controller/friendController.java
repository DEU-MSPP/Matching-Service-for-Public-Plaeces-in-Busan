package com.tt.mspp.controller;

import com.tt.mspp.dao.FriendDAO;
import com.tt.mspp.dto.FriendDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class friendController {

    HttpServletRequest request;

    @GetMapping("/friend")
    public String friend(HttpSession session, Model model) {
        String sessionId = (String) session.getAttribute("sessionid");
        FriendDAO dao = FriendDAO.getInstance();
        List<FriendDTO> list = dao.getFriendList(sessionId);
        ArrayList<String> friends = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getF_id1().equals(sessionId)) {            //id1이 로그인한 사용자라면 id2가 상대방
                friends.add(list.get(i).getF_id2());
            } else {                                               //id1이 로그인한 사용자가 아니라면 id1이 상대방
                friends.add(list.get(i).getF_id1());
            }
        }
        model.addAttribute("friends", friends);
        return "friend";
    }

    @PostMapping("/addFriend.do")
    public String addFriend(HttpSession session, @RequestParam("f_id1") String f_id1){

        //친구 신청시 이미 친구인지 확인하고 신청하기.
        //인덱스 시퀀스 설정하기.
        //친구 신청 실패 성공시 알림 띄우기

        String sessionId = (String) session.getAttribute("sessionid");
        FriendDAO dao = FriendDAO.getInstance();
        Boolean result = dao.addFriend(f_id1, sessionId);
        if(result = true){
            return "friend";
        }
        else{
            return "friend";
        }
    }



}
