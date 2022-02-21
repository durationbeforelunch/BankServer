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

    // Тест маппинг
    @GetMapping
    public String showIndexPage() {
        return "index";
    }

    /**
     *
     * @param dto нужен для "автозаполнения" имени пользователя, после удачной регистрации
     * @return возвращаем стандартную страницу для входа в систему
     *
    * */
    @GetMapping("/login")
    public ModelAndView showLoginPage(@ModelAttribute(MODEL_NAME) AccountCreateDto dto) {
        return new ModelAndView("/login", MODEL_NAME, dto);
    }

    /**
     *
     * @param model для передачи аттрибутов в модель, здесь для передачи объекта dto
     * @return возвращает представление с переданным объектом dto который в дальнейшем будет инициализирован
     *
     * */
    @GetMapping("/signup")
    public String showRegistrationPage(Model model) {
        AccountCreateDto dto = new AccountCreateDto();
        model.addAttribute(MODEL_NAME, dto);
        return "signup";
    }

    /**
     *
     * @param dto здесь посредством PostMapping принимаем в параметрах уже готовый объект
     * @return перенаправляем по пути /login, заодно передаем туда аттрибуты модели и объект dto
     *
     * */
    @PostMapping("/signup")
    public ModelAndView registerUserAccount(@ModelAttribute(MODEL_NAME) AccountCreateDto dto) {
        accountService.createAccount(dto);
        return new ModelAndView("/login", MODEL_NAME, dto);
    }

}
