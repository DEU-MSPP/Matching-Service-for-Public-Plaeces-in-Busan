package com.tt.mspp.controller;

import com.tt.mspp.dao.DAO;
import com.tt.mspp.dao.MatchDAO;
import com.tt.mspp.dao.RoomDAO;
import com.tt.mspp.dto.RoomDTO;
import com.tt.mspp.dto.MatchDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.tt.mspp.dto.PlaceDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class matchController {

    @GetMapping("/match")
    public String match(Model model) {
        RoomDAO dao = RoomDAO.getInstance();
        List<RoomDTO> roomList = dao.getRoomList();


        DAO dao1 = DAO.getInstance();
        List<PlaceDTO> placelist = dao1.getPlaceListType("1");


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
        model.addAttribute("roomList", roomList);
        model.addAttribute("placelist", placelist);
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
}
