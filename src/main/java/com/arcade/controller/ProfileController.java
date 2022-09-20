package com.arcade.controller;

import com.arcade.dto.UserDto;
import com.arcade.dto.converter.UserBasicInfoConverter;
import com.arcade.dto.converter.UserConverter;
import com.arcade.entity.user.User;
import com.arcade.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final UserBasicInfoConverter userBasicInfoConverter;

    private final String profileImagesDir = System.getProperty("user.dir") + "src/main/resources/static/imgs/profile";

    @Autowired
    public ProfileController(UserService userService, UserConverter userConverter, UserBasicInfoConverter userBasicInfoConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.userBasicInfoConverter = userBasicInfoConverter;
    }

    @GetMapping("/user")
    public String getUserPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("userDto", userBasicInfoConverter.fromEntityToDto(user));
        model.addAttribute("roles", user.getRoles());
        return "redirect:/profile/user/" + user.getUsername();
    }

    @GetMapping("/user/{userName}")
    public String getUserPage(@PathVariable("userName") String userName, Model model) {
        User user = userService.findByUsername(userName);
        model.addAttribute("userDto", userBasicInfoConverter.fromEntityToDto(user));
        model.addAttribute("roles", user.getRoles());
        return "user/profile";
    }

    // TODO: add edit page for users so they can change their first and last name and edit their photos
    @GetMapping("/edit/{userName}")
    public String getEditUserPage(@PathVariable("userName") String userName, Model model) {
        User user = userService.findByUsername(userName);
        model.addAttribute("userDto", userConverter.fromEntityToDto(user));
        return "user/edit-profile-form";
    }

    @PostMapping("/user/save")
    public String saveEditedUser(
            @ModelAttribute("user") UserDto user,
            Model model
    ) {
        // todo: build logic here

        return "redirect:/profile/user/" + user.getUsername();
    }

}
