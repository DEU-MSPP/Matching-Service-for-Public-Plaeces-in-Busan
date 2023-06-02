package com.tt.mspp.controller;

import com.tt.mspp.dao.DAO;
import com.tt.mspp.dao.MatchDAO;
import com.tt.mspp.dao.RoomDAO;
import com.tt.mspp.dto.RoomDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.tt.mspp.dto.PlaceDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class matchController {

    @GetMapping("/match")
    public String match(Model model) {
        RoomDAO dao = RoomDAO.getInstance();
        List<RoomDTO> roomList = dao.getRoomList();
        model.addAttribute("roomList", roomList);

        DAO dao1 = DAO.getInstance();
        List<PlaceDTO> placelist = dao1.getPlaceListType("1");
        model.addAttribute("roomList", roomList);
        return "match";
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
