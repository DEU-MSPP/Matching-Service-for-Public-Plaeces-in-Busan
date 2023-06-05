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

        ArrayList<String> friends = new ArrayList<>();                 //이미 친구
        ArrayList<String> friends2 = new ArrayList<>();                 //친신 대기중
        ArrayList<String> friends3 = new ArrayList<>();                 //친구신청 받은거

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getF_id1().equals(sessionId) && list.get(i).getF_ok().equals("1")) {            //id1이 로그인한 사용자라면 id2가 상대방
                friends.add(list.get(i).getF_id2());                //이미 친구
            }
            else if(list.get(i).getF_id2().equals(sessionId) && list.get(i).getF_ok().equals("1")){
                friends.add(list.get(i).getF_id1());       //이미 친구
            }

            if(list.get(i).getF_id2().equals(sessionId) && list.get(i).getF_ok().equals("0")) {                                               //id1이 로그인한 사용자가 아니라면 id1이 상대방
                friends2.add(list.get(i).getF_id1());               //친신 대기중
            }

            if(list.get(i).getF_id1().equals(sessionId) && list.get(i).getF_ok().equals("0")){           //내가 친구신청 받은거
                friends3.add(list.get(i).getF_id2());               //누구한테 왔는지
            }
        }
        model.addAttribute("friends", friends);             //이미 친구
        model.addAttribute("friends2", friends2);           //친신 대기중
        model.addAttribute("friends3", friends3);
        return "friend";
    }


    @PostMapping("/addFriend.do")
    public String addFriend(HttpSession session, @RequestParam("f_id1") String f_id1) {

        //친구 신청시 이미 친구인지 확인하고 신청하기.
        //인덱스 시퀀스 설정하기.
        //친구 신청 실패 성공시 알림 띄우기

        String sessionId = (String) session.getAttribute("sessionid");
        FriendDAO dao = FriendDAO.getInstance();

        ArrayList<String> friends = new ArrayList<>();
        List<FriendDTO> list = dao.getFriendList(sessionId);
        boolean no = true;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getF_id1().equals(f_id1) || list.get(i).getF_id2().equals(f_id1)) {
                no = false;
                break;
            }
        }
        if (!sessionId.equals(f_id1) && no == true) {                                  //로그인한 ID랑 친신 ID랑 같으면 안되게
            Boolean result = dao.addFriend(f_id1, sessionId);
        }
        return "redirect:/friend";
    }

    @GetMapping("/refuseFriend.do")
    public String refuseFriend(HttpSession session, @RequestParam("cancel2") String f_id2) {
        String sessionId = (String) session.getAttribute("sessionid");
        FriendDAO dao = FriendDAO.getInstance();
        Boolean result = dao.deleteFriend(sessionId, f_id2);

        return "redirect:/friend";
    }

    @GetMapping("/acceptFriend.do")
    public String acceptFriend(HttpSession session, @RequestParam("cancel2") String f_id2) {
        String sessionId = (String) session.getAttribute("sessionid");
        FriendDAO dao = FriendDAO.getInstance();
        Boolean result = dao.acceptFriend(sessionId, f_id2);

        return "redirect:/friend";
    }

    @GetMapping("/cancelFriend.do")
    public String cancelFriend(HttpSession session, @RequestParam("cancel1") String f_id1) {

        System.out.println(f_id1);
        String sessionId = (String) session.getAttribute("sessionid");
        FriendDAO dao = FriendDAO.getInstance();
        Boolean result = dao.deleteFriend(f_id1, sessionId);

        return "redirect:/friend";
    }



}
