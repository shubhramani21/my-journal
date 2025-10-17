package com.example.Journal.Control;

import org.bson.types.ObjectId;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageControl {

    @GetMapping("/")
    public String index(){
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }
    

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/entries")
    public String entriesPage(Model model){
        // final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // final String username = authentication.getName();

        // model.addAttribute("username", username);

        return "entries";
    }

    @GetMapping("/entries/new")
    public String newEntryPage(){
        return "create-entry";
    }


    @GetMapping("/entries/edit/{id}")
    public String editEntryPage(Model model, @PathVariable ObjectId id){
        SecurityContextHolder.getContext().getAuthentication();
        // final String username = authentication.getName();

        model.addAttribute("entryId", id.toString());
        return "edit-entry";
    }

    // @GetMapping("/home")
    // public String homePage(Model model){
    //     final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     final String username = authentication.getName();

    //      model.addAttribute("username", username);

    //     return "home";
    // }

}
