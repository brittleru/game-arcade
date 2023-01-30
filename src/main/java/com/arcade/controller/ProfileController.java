package com.arcade.controller;

import com.arcade.dto.UserDto;
import com.arcade.dto.converter.UserBasicInfoConverter;
import com.arcade.dto.converter.UserConverter;
import com.arcade.entity.user.Role;
import com.arcade.entity.user.User;
import com.arcade.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final UserBasicInfoConverter userBasicInfoConverter;

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
        List<String> roles = new ArrayList<>(5);
        for (Role role : user.getRoles()) {
            roles.add(role.getName().toLowerCase().replace("role_", ""));
        }
        model.addAttribute("roles", roles);

        return "user/profile";
    }

    // TODO: add edit page for users so they can change their first and last name and edit their photos
    @GetMapping("/edit/{userName}")
    public String getEditUserPage(@PathVariable("userName") String userName, Model model, Principal principal) {
        if (!userName.equals(principal.getName())) {
            return getUserPage(model);
        }
        User user = userService.findByUsername(userName);
        model.addAttribute("userDto", userConverter.fromEntityToDto(user));
        return "user/edit-profile-form";
    }

    @PostMapping("/user/save")
    public String saveEditedUser(@ModelAttribute("user") UserDto user) {
        User modelUser = userService.findByUsername(user.getUsername());
        if (user.getFirstName().equals(modelUser.getFirstName()) &&
                user.getLastName().equals(modelUser.getLastName())) {
            return "redirect:/profile/user/" + user.getUsername();
        }
        userService.updateNames(modelUser, user.getFirstName(), user.getLastName());
        return "redirect:/profile/user/" + user.getUsername();
    }

    @PostMapping("/upload/picture")
    public String uploadImageForUser(Authentication authentication) {
        return "redirect:/profile/user/" + authentication.getName();
    }

}
