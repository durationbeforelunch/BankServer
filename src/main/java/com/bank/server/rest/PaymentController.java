package com.bank.server.rest;

import com.bank.server.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/*
*
* rest контроллер, который отвечает за перевод средств
*
* */
//@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;

    /**
     *
     * @param from в GET запросе передаем сперва идентификатор первой сущности с которой нужно осуществить перевод
     * @param to затем передаем идентификатор второй сущности в которую нужно осуществить перевод
     * @param sum сумма передаваемая с первой сущности на вторую
     * @return возращает ответ, удачно ли прошла транзакция или нет
     *
     * */
    @GetMapping("/payment/send")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<String> sendMoney(@RequestParam Integer from, @RequestParam Integer to, @RequestParam BigDecimal sum) {
        return paymentService.transferMoney(from,to,sum);
    }

}
