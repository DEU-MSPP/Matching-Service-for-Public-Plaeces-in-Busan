package com.tt.mspp.controller;

import com.tt.mspp.dao.DAO;
import com.tt.mspp.dto.PlaceDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class indexController {

    @GetMapping("/")
    public String index(@RequestParam(value="index", required=false) String index, @RequestParam(value = "type", required = false) String type, Model model) {
        DAO dao = DAO.getInstance();
        if (type != null && !type.isEmpty()) {
            List<PlaceDTO> placelist = dao.getPlaceListType(type);
            model.addAttribute("placelist", placelist); //지도에 표시할 마커
        } else {
            List<PlaceDTO> placelist = dao.getPlaceList();
            model.addAttribute("placelist", placelist); //지도에 표시할 마커
        }
        if (index != null && !index.isEmpty()) {
            List<PlaceDTO> placelist2 = dao.getPlaceList(index); //select index로 한줄만
            model.addAttribute("placelist2", placelist2); //상세 정보 출력
        }
        return "index";
    }
}


