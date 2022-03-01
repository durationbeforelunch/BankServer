package com.bank.server.controller;

import com.bank.server.dto.AccountCreateDto;
import com.bank.server.dto.AccountInfoDto;
import com.bank.server.entity.Account;
import com.bank.server.exception.AccountAlreadyExists;
import com.bank.server.exception.AccountNotFoundException;
import com.bank.server.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/*
*
* Контроллер отвечающий за отображаение представлений (View)
* обычный Thymeleaf View Resolver
*
* */

@Controller
@RequiredArgsConstructor
public class FrontController {

    private final IAccountService accountService;
    public static final String MODEL_NAME = "account";

    @GetMapping
    public String showIndexPage(Model model, Principal principal) {

        String userName = principal.getName();

        try {

            AccountInfoDto infoDto = new AccountInfoDto(accountService.findByUsername(userName));
            accountService.setLastLoginNow(userName);
            model.addAttribute(MODEL_NAME, infoDto);
            return "index";

        } catch (AccountNotFoundException e) {
            return "redirect:/index?error";
        }

    }

    /**
     *
     * @return возвращаем стандартную страницу для входа в систему
     *
    * */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    /**
     *
     * @param model для передачи аттрибутов в модель, здесь для передачи объекта dto
     * @return возвращает представление с переданным объектом dto который в дальнейшем будет инициализирован
     *
     * */
    @GetMapping("/signup")
    public ModelAndView showRegistrationPage() {
        return new ModelAndView("signup", MODEL_NAME, new AccountCreateDto());
    }

    /**
     *
     * @param dto здесь посредством PostMapping принимаем в параметрах уже готовый объект
     * @return перенаправляем по пути /login, заодно передаем туда аттрибуты модели и объект dto
     *
     * */
    @PostMapping("/signup")
    public String registerUserAccount(HttpServletRequest request, @ModelAttribute(MODEL_NAME) AccountCreateDto dto) {
        try {
            accountService.createAccount(dto);
            accountService.authenticate(request, dto);
        } catch (AccountAlreadyExists e) {
            return "redirect:/signup?error";
        } catch (AccountNotFoundException e) {
            return "error/404";
        } catch (ServletException e) {
            e.printStackTrace();
            return "error/500";
        }
        return "/";
    }

    @GetMapping("/control-panel")
    @PreAuthorize("hasAuthority('developers:write')")
    public String showControlPanel(Model model) {

        List<Account> accounts = accountService.findAll();

        model.addAttribute("accounts", accounts);

        return "control-panel";
    }

    @GetMapping("/control-panel/edit/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public String showControlPanel(@PathVariable("id") Integer id) {

        try {
            accountService.findById(id);
        } catch (AccountNotFoundException e) {
            return "/error/404";
        }

        return "edit";

    }

    @GetMapping("/transfer")
    @PreAuthorize("hasAuthority('developers:read')")
    public String showTransferView() {
        return "transfer";
    }

}
