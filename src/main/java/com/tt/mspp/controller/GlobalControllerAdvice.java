package com.tt.mspp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpSession;



@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private HttpSession session;

    @ModelAttribute("isLogged")
    public boolean isLogged() {
        String sessionId = (String) session.getAttribute("sessionid");
        return sessionId != null;
    }
}