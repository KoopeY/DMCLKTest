package ru.koopey.test_domclick.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.koopey.test_domclick.exeptions.AccountNotFoundException;
import ru.koopey.test_domclick.exeptions.OperationException;
import ru.koopey.test_domclick.model.JSONResponse;
import ru.koopey.test_domclick.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    /**
     * Депозит на счет
     * @param to - счет
     * @param sum - сумма депозита
     * @return JSONResponse - результат операции
     */
    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public JSONResponse deposit(@RequestParam(value="to") String to,
                                @RequestParam(value="sum") Double sum) throws AccountNotFoundException {
        return accountService.deposit(to, sum);
    }

    /**
     * Снятие с счета
     * @param from - счет
     * @param sum - сумма снятия
     * @return JSONResponse - результат операции
     */
    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public JSONResponse withdraw(@RequestParam(value="from") String from,
                                 @RequestParam(value="sum") Double sum) throws AccountNotFoundException, OperationException {
        return accountService.withdraw(from, sum);
    }

    /**
     * Перевод со счета на счет
     * @param from - счет, с которого производится перевод
     * @param to - счет, на который поступает перевод
     * @param sum - сумма перевода
     * @return JSONResponse - результат операции
     */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public JSONResponse transfer(@RequestParam(value="from") String from,
                                 @RequestParam(value="to") String to,
                                 @RequestParam(value="sum") Double sum) throws AccountNotFoundException, OperationException {
        return accountService.transfer(from, to, sum);
    }
}
