package com.derrick.controller;

import com.derrick.model.Signup;
import com.derrick.services.SignupInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller

public class SignupController {
    @Autowired
    SignupInterface signupInterface;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/uploadfile")
    public String uploading(){
        return "upload";
    }

    @GetMapping("/admin")
    public String admin(){ return "admin";
    }
    @GetMapping("/gallery")
    public String gallerypage(){ return "gallery";
    }
    @GetMapping("/gallery2")
    public String gallerypage2(){ return "gallery2";
    }


    @GetMapping("/login")
    public String homepage(Model model){
        Signup signup=new Signup();
        model.addAttribute("login",signup);
       return "login";
    }

    @GetMapping("/Signup")
    public String registration(Model model){
        Signup signup=new Signup();
        model.addAttribute("signup",signup);
        return "Signup";
    }
@PostMapping("/registerAccount")
    public String saveAccount(@ModelAttribute("signup") Signup theSignup ) {

    Signup signup = signupInterface.saveAccount(theSignup);
    if (signup != null) {
        return "redirect:/login";
    } else {
        System.out.println("not registerd please try again");
        return "Signup";
    }
}
//
//    // ANSI escape code for color reset
//    private static final String ANSI_RESET = "\u001B[0m";
//    // ANSI escape code for white color
//    private static final String ANSI_WHITE = "\u001B[37m";

    @PostMapping("/authentication")
    public String userAuthentication(@RequestParam String email,@RequestParam String password){
       boolean result=   signupInterface.userCheck(email,password);
        if (result){
            if(email.contains("admin") && password.contains("123")){
                return "redirect:/admin";
            }else {
                return "redirect:/employee_page";
            }

        }else {
            System.out.println("Wrong Input and Password");
//            System.out.println(ANSI_WHITE + "This message is in white." + ANSI_RESET);
            return "redirect:/login";
        }

    }
}
