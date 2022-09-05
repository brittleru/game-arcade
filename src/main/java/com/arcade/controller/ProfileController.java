package com.arcade.controller;

import com.arcade.dto.converter.UserBasicInfoConverter;
import com.arcade.entity.user.User;
import com.arcade.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

    private final UserService userService;
    private final UserBasicInfoConverter userBasicInfoConverter;

    private final String profileImagesDir = System.getProperty("user.dir") + "src/main/resources/static/imgs/profile";

    @Autowired
    public ProfileController(UserService userService, UserBasicInfoConverter userBasicInfoConverter) {
        this.userService = userService;
        this.userBasicInfoConverter = userBasicInfoConverter;
    }


    @GetMapping("/user")
    public String getUserPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("userDto", userBasicInfoConverter.fromEntityToDto(user));
        return "user/profile";
    }

    // TODO: add edit page for users so they can change their first and last name and edit their photos
    @GetMapping("/edit")
    public String getEditUserPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("userDto", userBasicInfoConverter.fromEntityToDto(user));

        return "user/edit-profile-form";
    }

}
