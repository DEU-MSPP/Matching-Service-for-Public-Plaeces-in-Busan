package com.tt.mspp.controller;

import com.tt.mspp.dao.DAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.tt.mspp.dto.PlaceDTO;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;


import java.util.ArrayList;
import java.util.List;

@Controller
public class indexController {

    @GetMapping("/")
    public String index(Model model) {
        DAO dao = DAO.getInstance();
        List<PlaceDTO> placelist = dao.getPlaceList();
        String index = "";
        String address = "";
        String name = "";
        String reserve = "";
        int type = 0;
        String phone = "";

        String[] markerTitles = new String[placelist.size()];
        String[] markerAddresses = new String[placelist.size()];
        int[] markerType = new int[placelist.size()];
        for (int i = 0; i < placelist.size(); i++) {
            markerTitles[i] = placelist.get(i).getP_name();
            markerAddresses[i] = placelist.get(i).getP_address();
            markerType[i] = placelist.get(i).getP_type();
            //index = placelist.get(i).getP_index();
            //address = placelist.get(i).getP_address();
            //name = placelist.get(i).getP_name();
            //reserve = placelist.get(i).getP_reserve();
            //type = placelist.get(i).getP_type();
            //phone = placelist.get(i).getP_phone();
        }
        model.addAttribute("markerTitles", markerTitles);
        model.addAttribute("markerAddresses", markerAddresses);
        model.addAttribute("markerType", markerType);

        return "index";
    }

}


