package com.tt.mspp.controller;

import com.tt.mspp.dao.DAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.tt.mspp.dto.PlaceDTO;

import java.util.ArrayList;
import java.util.List;

@Controller
public class indexController {

    @GetMapping("/index")
    public String index() {
        DAO dao = DAO.getInstance();
        List<PlaceDTO> placelist = dao.getPlaceList();
        String index = "";
        String address = "";
        String name = "";
        String reserve = "";
        int type = 0;
        String phone = "";

        //이중배열
        //ArrayList<Integer> list = new ArrayList<String>();

        for (int i = 0; i < placelist.size(); i++) {
            index = placelist.get(i).getP_index();
            address = placelist.get(i).getP_address();
            name = placelist.get(i).getP_name();
            reserve = placelist.get(i).getP_reserve();
            type = placelist.get(i).getP_type();
            phone = placelist.get(i).getP_phone();

        }

        return "index";
    }
}
