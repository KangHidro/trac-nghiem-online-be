package com.stc.tracnghiemonline.controllers;

import com.stc.tracnghiemonline.services.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:57 PM
 * Filename  : UserController
 */
@RestController
@RequestMapping("/rest/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
