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
    public String index(Model model) {
        DAO dao = DAO.getInstance();
        List<PlaceDTO> placelist = dao.getPlaceList();
        model.addAttribute("placelist", placelist); //지도에 표시할 마커
        return "index";
    }

    //마커 클릭시 상세정보 출력
    @GetMapping("/index2")
    public String index_2(@RequestParam("index") String index, Model model) {
        DAO dao = DAO.getInstance();
        List<PlaceDTO> placelist = dao.getPlaceList();
        model.addAttribute("placelist", placelist); //지도에 표시할 마커

        List<PlaceDTO> placelist2 = dao.getPlaceList(index); //select index로 한줄만
        model.addAttribute("placelist2", placelist2); //상세 정보 출력
        return "index";
    }

    //카테고리 선택시 지도에 해당 타입만 출력
    @GetMapping("/index3")
    public String index_3(@RequestParam("type") String type,Model model) {
        DAO dao = DAO.getInstance();
        List<PlaceDTO> placelist = dao.getPlaceListType(type);
        model.addAttribute("placelist", placelist); //지도에 표시할 마커
        return "index";
    }
}


