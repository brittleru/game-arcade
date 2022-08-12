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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class.getName());

    private static final String USERS_FORM_HTML = "admin/users-form";
    private static final String USER_MODEL_VALUE = "user";
    private static final String ALL_ROLES_MODEL_VALUE = "allRoles";

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
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute(USER_MODEL_VALUE, new CheckedUserForAdmin());
        model.addAttribute(ALL_ROLES_MODEL_VALUE, roles);

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
            model.addAttribute(ALL_ROLES_MODEL_VALUE, roles);
            return USERS_FORM_HTML;
        }

        logger.info("Successfully added user from admin panel: " + userName);

        return "redirect:/admin/users/all";
    }

    // TODO: check this functionality to be optimized (e.g., check for passwords if you change them to be hashed in DB,
    // TODO: check validations.
    @GetMapping("/user/update/{userId}")
    public String updateUser(@PathVariable("userId") int userId, Model model) {
        User user = adminService.findUserById(userId);
        // TODO: refactor this with builder...
        CheckedUserForAdmin checkedUserForAdmin = new CheckedUserForAdmin();
        checkedUserForAdmin.setId(user.getId());
        checkedUserForAdmin.setUserName(user.getUsername());
        checkedUserForAdmin.setPassword(user.getPassword());
        checkedUserForAdmin.setMatchingPassword(user.getPassword());
        checkedUserForAdmin.setEmail(user.getEmail());
        checkedUserForAdmin.setFirstName(user.getFirstName());
        checkedUserForAdmin.setLastName(user.getLastName());
        checkedUserForAdmin.setRoles(user.getRoles());

        List<Role> roles = roleService.findAllRoles();

        model.addAttribute(USER_MODEL_VALUE, checkedUserForAdmin);
        model.addAttribute(ALL_ROLES_MODEL_VALUE, roles);

        return USERS_FORM_HTML;
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
