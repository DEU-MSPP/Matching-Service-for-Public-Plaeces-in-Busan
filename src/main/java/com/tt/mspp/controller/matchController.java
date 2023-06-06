package com.tt.mspp.controller;

import com.tt.mspp.dao.*;
import com.tt.mspp.dto.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class matchController {

    @GetMapping("/match")
    public String match(Model model, HttpSession session) {
        RoomDAO dao = RoomDAO.getInstance();
        List<RoomDTO> roomList = dao.getRoomList();


        DAO dao1 = DAO.getInstance();
        List<PlaceDTO> placelist = dao1.getPlaceListType("1");
        List<PlaceDTO> matchingPlaces = new ArrayList<>();
        for (PlaceDTO place : placelist) {
            String p_index = place.getP_index();
            for (RoomDTO room : roomList) {
                String r_p_index = Integer.toString(room.getR_p_index());
                if (p_index.equals(r_p_index)) {
                    matchingPlaces.add(place);
                    break;
                }
            }
        }


        MatchDAO dao2 = MatchDAO.getInstance();
        List<MatchDTO> matchList = dao2.getMatchList();

        for (RoomDTO room : roomList) {
            String r_index = room.getR_index();
            if (r_index != null) {
                for (MatchDTO match : matchList) {
                    String m_r_index = match.getM_r_index();
                    if (m_r_index != null && m_r_index.equals(r_index)) {
                        room.setCount(match.getCount());
                        break;
                    }
                }
            }
        }

        String sessionId = (String) session.getAttribute("sessionid");
        FriendDAO dao3 = FriendDAO.getInstance();
        List<FriendDTO> list = dao3.getFriendList(sessionId);
        List<RoomDTO> friendRoomList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i).getF_id1().equals(sessionId) && list.get(i).getF_ok().equals("1")) || (list.get(i).getF_id2().equals(sessionId) && list.get(i).getF_ok().equals("1"))) {        //id1이 로그인한 사용자라면 id2가 상대방
                for (RoomDTO room : roomList) {
                    if (room.getR_id().equals(list.get(i).getF_id1())) {
                        friendRoomList.add(room);
                    }
                }
            }
        }

        model.addAttribute("roomList", roomList);
        model.addAttribute("placelist", matchingPlaces);
        model.addAttribute("friendRoomList", friendRoomList);
        return "match";
    }

    @PostMapping("/match.do")
    public String insertMatch(HttpSession session, @RequestParam("r_index") String r_index, Model model) {

        String id = (String) session.getAttribute("sessionid");

        MatchDAO dao = MatchDAO.getInstance();
        boolean result = dao.insertMatch(r_index, id);
        if (result) {
            model.addAttribute("alertMessage", "성공적으로 매칭에 참여되셨습니다.");
        }
        return "redirect:/match";
    }

    @PostMapping("/matchCancel.do")
    public String cancelMatch(HttpSession session, @RequestParam("r_index") String r_index, Model model) {

        String id = (String) session.getAttribute("sessionid");

        MatchDAO dao = MatchDAO.getInstance();
        boolean result = dao.deleteMatch(r_index, id);
        if (result) {
            model.addAttribute("alertMessage", "성공적으로 매칭에 참여되셨습니다.");
        }
        return "redirect:/matchManage";
    }

    @GetMapping("/matchManage")
    public String matchManage(Model model, HttpSession session) {
        RoomDAO dao = RoomDAO.getInstance();
        List<RoomDTO> roomList = dao.getRoomList();
        List<RoomDTO> myRoom = new ArrayList<>();

        String id = (String) session.getAttribute("sessionid");

        for (RoomDTO room : roomList) {
            String r_id = room.getR_id();
            if (r_id != null && id != null && id.equals(r_id)) {
                myRoom.add(room);
            }
        }

        MatchDAO dao2 = MatchDAO.getInstance();
        List<MatchDTO> matchList = dao2.getMatchList();
        List<RoomDTO> myMatch = new ArrayList<>();


        for (MatchDTO match : matchList) {
            String m_id = match.getM_id();
            if (m_id != null && id != null && id.equals(m_id)) {
                String m_r_index = match.getM_r_index();
                for (RoomDTO room : roomList) {
                    String r_index = room.getR_index();
                    if (r_index != null && m_r_index.equals(r_index)) {
                        myMatch.add(room);
                    }
                }
            }
        }

        model.addAttribute("myRoom", myRoom);
        model.addAttribute("myMatch", myMatch);
        return "matchManage";
    }

    @GetMapping("/room")
    public String room(Model model) {
        DAO dao = DAO.getInstance();
        List<PlaceDTO> placelist = dao.getPlaceListType("1");
        model.addAttribute("placelist", placelist); //지도에 표시할 마커
        return "room";
    }

    @PostMapping("/room.do")
    public String insertRoom(HttpSession session, @RequestParam("day") String day, @RequestParam("stime") String stime,
                             @RequestParam("etime") String etime, @RequestParam("place") String place,
                             @RequestParam("max") int max, @RequestParam("discribe") String discribe, @RequestParam("p_index") String p_index, Model model) {

        String id = (String) session.getAttribute("sessionid");

        RoomDAO dao = RoomDAO.getInstance();
        boolean result = dao.insertRoom(id, day, stime, etime, place, max, discribe, Integer.parseInt(p_index));
        if (result) {
            model.addAttribute("alertMessage", "성공적으로 매칭방이 생성되었습니다.");
        }
        return "redirect:/match";
    }

    @PostMapping("/roomCancel.do")
    public String cancelRoom(@RequestParam("r_index") String r_index, Model model) {

        RoomDAO dao = RoomDAO.getInstance();
        boolean result = dao.deleteRoom(r_index);
        if (result) {
            System.out.println("삭제 성공");
        }
        return "redirect:/matchManage";
    }


    @PostMapping("/matchComment")
    public String matchComment(@RequestParam("r_index") String r_index, Model model) {

        CommentDAO dao = CommentDAO.getInstance();
        List<CommentDTO> commentList = dao.getCommentList(r_index);

        model.addAttribute("c_r_index1", r_index);
        model.addAttribute("commentList", commentList);
        return "matchComment";
    }

    @PostMapping("/commentDelete.do")
    public String deleteComment(@RequestParam("c_index") String c_index, HttpSession session, Model model) {
        String id = (String) session.getAttribute("sessionid");

        System.out.println("같음");
        CommentDAO dao = CommentDAO.getInstance();
        boolean result = dao.deleteComment(c_index);
        return "redirect:/match";
    }

    @PostMapping("/commentInsert.do")
    public String insertCommnet(@RequestParam("c_r_index") String c_r_index, @RequestParam("c_context") String c_context, HttpSession session) {
        System.out.println(c_r_index);
        String id = (String) session.getAttribute("sessionid");

        CommentDAO dao = CommentDAO.getInstance();
        boolean result = dao.insertComment(c_r_index, id, c_context);
        return "redirect:/match";
    }
}
