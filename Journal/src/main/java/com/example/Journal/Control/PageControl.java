package com.example.Journal.Control;

import java.security.Principal;

import com.example.Journal.Service.CustomUserDetails;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageControl {

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
    public String entriesPage(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        if (user != null) {
            model.addAttribute("username", user.getFirstName());
        }
        return "entries";
    }

    @GetMapping({"/entries/new", "/entries/edit/{id}"})
    public String entryForm(Model model, @PathVariable(required = false) ObjectId id) {
        model.addAttribute("entryId", id != null ? id.toString() : null);
        return "form";
    }
}
