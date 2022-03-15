package com.arcade.controller;

import com.arcade.entity.user.Role;
import com.arcade.entity.user.User;
import com.arcade.service.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;

    @Autowired
    public AdminController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/users/all")
    public String getUsersList() {
        return "admin/users";
    }

    @GetMapping("/users-form")
    public String getUsersForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("allRoles", roles);

        return "admin/users-form";
    }

    @PostMapping("/user/save")
    public String saveUser(@ModelAttribute("user") User user) {
        return "redirect:/admin/users/all";
    }

}
