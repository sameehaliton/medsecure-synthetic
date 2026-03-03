package com.medsecure.controller;

import com.medsecure.service.UserService;
import javax.servlet.http.HttpServletRequest;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CWE-89: userId flows from HTTP request → service → repository SQL string
    public String getUser(HttpServletRequest request) {
        String userId = request.getParameter("id");
        return userService.findUser(userId);
    }

    // CWE-22: filePath flows from HTTP request → File constructor without canonicalization
    public byte[] getDocument(HttpServletRequest request) throws Exception {
        String filePath = request.getParameter("path");
        return userService.readDocument(filePath);
    }
}
