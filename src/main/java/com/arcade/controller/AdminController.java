package com.arcade.controller;

import com.arcade.entity.user.Role;
import com.arcade.entity.user.User;
import com.arcade.service.rest.AdminService;
import com.arcade.service.user.RoleService;
import com.arcade.validateentity.CheckedUser;
import com.arcade.validateentity.CheckedUserForAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class.getName());

    private static final String USERS_FORM_HTML = "admin/users-form";

    private final AdminService adminService;
    private final RoleService roleService;

    @Autowired
    public AdminController(AdminService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @GetMapping("/users/all")
    public String getUsersList() {
        return "admin/users";
    }

    @GetMapping("/users-form")
    public String getUsersForm(Model model) {
        model.addAttribute("user", new CheckedUserForAdmin());
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("allRoles", roles);

        return USERS_FORM_HTML;
    }

    // TODO: too much logic in controller
    @PostMapping("/user/save")
    public String saveUser(
            @Valid @ModelAttribute("user") CheckedUserForAdmin user,
            BindingResult bindingResult,
            Model model
    ) {
        String userName = user.getUserName();
        String email = user.getEmail();
        List<Role> roles = roleService.findAllRoles();

        logger.info("Processing registration form for: " + userName);

        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roles);
            return USERS_FORM_HTML;
        }

        User existingUserName = userExistingHelper(adminService.getUserByUsernameIfDifferentById(userName, user.getId()));
        User existingUserEmail = userExistingHelper(adminService.getUserByEmailIfDifferentById(email, user.getId()));

        if (existingUserName != null || existingUserEmail != null) {
            model.addAttribute("newUser", new CheckedUser());
            model.addAttribute("registrationError");
            String userOrEmail = existingUserName != null ? " username: " + userName :
                    (existingUserEmail != null ? " email: " + email : "test");
            logger.warning("Sorry" + userOrEmail + " already exits");
            model.addAttribute("allRoles", roles);
            return USERS_FORM_HTML;
        }


        logger.info("Successfully added user from admin panel: " + userName);


        return "redirect:/admin/users/all";
    }

    // TODO: extract this into an helper class
    private static User userExistingHelper(User user) {
        User existing = null;
        try {
            existing = user;
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

        return existing;
    }

}
