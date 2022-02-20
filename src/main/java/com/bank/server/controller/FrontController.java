package com.bank.server.controller;

import com.bank.server.dto.AccountCreateDto;
import com.bank.server.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FrontController {

    private final IAccountService accountService;
    public static final String MODEL_NAME = "account";

    @GetMapping
    public String viewIndexPage() {
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView viewLoginPage(@ModelAttribute(MODEL_NAME) AccountCreateDto dto) {
        return new ModelAndView("/login", MODEL_NAME, dto);
    }

    @GetMapping("/signup")
    public String viewTest(Model model) {
        AccountCreateDto dto = new AccountCreateDto();
        model.addAttribute(MODEL_NAME, dto);
        return "signup";
    }

    @PostMapping("/signup")
    public ModelAndView registerUserAccount(@ModelAttribute(MODEL_NAME) AccountCreateDto dto) {
        accountService.createAccount(dto);
        return new ModelAndView("/login", MODEL_NAME, dto);
    }

}
