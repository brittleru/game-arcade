package com.arcade.controller;

import com.arcade.entity.user.User;
import com.arcade.service.user.UserService;
import com.arcade.validateentity.CheckedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final static Logger logger = Logger.getLogger(RegistrationController.class.getName());

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/show-register")
    public String showRegister(Model model) {
        model.addAttribute("newUser", new CheckedUser());

        return "auth/register-form";
    }

    @PostMapping("/process-register")
    public String processRegister(
            @Valid @ModelAttribute("newUser") CheckedUser checkedUser,
            BindingResult bindingResult,
            Model model
    ) {
        String userName = checkedUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        if (bindingResult.hasErrors()) {
            return "auth/register-form";
        }

        User existing = null;

        try {
            existing = userService.findByUsername(userName);
        }
        catch (Exception e) {
            logger.warning(e.getMessage());
        }

        if (existing != null) {
            model.addAttribute("newUser", new CheckedUser());
            model.addAttribute("registrationError", "User name already exists.");
            logger.warning("Sorry username: " + userName + " already exists.");
            return "auth/register-form";
        }

        userService.save(checkedUser);
        logger.info("Successfully created user: " + userName);

        return "auth/register-confirmation";
    }


}
