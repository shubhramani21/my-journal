package com.example.Journal.Control;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Journal.Entity.User;
import com.example.Journal.Service.UserService;

@Controller
public class PageControl {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/entries")
    public String entriesPage(Model model) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String userName = authentication.getName();

        final User user = userService.findByUserName(userName);

        model.addAttribute("username", user.getFirstName() != null ? user.getFirstName() : null);
        return "entries";
    }

    @GetMapping("/entries/new")
    public String newEntryPage(){
        return "create-entry";
    }


    @GetMapping("/entries/edit/{id}")
    public String editEntryPage(Model model, @PathVariable ObjectId id){


        model.addAttribute("entryId", id.toString());
        return "edit-entry";
    }
}
