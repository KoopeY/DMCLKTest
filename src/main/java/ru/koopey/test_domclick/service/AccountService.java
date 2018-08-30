package ru.koopey.test_domclick.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.koopey.test_domclick.entity.Account;
import ru.koopey.test_domclick.exeptions.AccountNotFoundException;
import ru.koopey.test_domclick.exeptions.OperationException;
import ru.koopey.test_domclick.interfaces.IAccountService;
import ru.koopey.test_domclick.model.JSONResponse;
import ru.koopey.test_domclick.repository.AccountRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountService implements IAccountService {
    private static Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public JSONResponse deposit(String toAccount, Double sum) throws AccountNotFoundException {
        Account account = getAccount(toAccount);
        account.deposit(sum);
        accountRepository.save(account);

        return JSONResponse.builder()
                .success(true)
                .message(String.format("Счет %s успешно пополнен на %f", toAccount, sum))
                .build();
    }

    @Override
    public JSONResponse withdraw(String fromAccount, Double sum) throws AccountNotFoundException, OperationException {
        Account account = getAccount(fromAccount);
        account.withdraw(sum);
        accountRepository.save(account);

        return JSONResponse.builder()
                .success(true)
                .message(String.format("Со счета %s успешно снято %f", fromAccount, sum))
                .build();
    }

    @Override
    public JSONResponse transfer(String fromAccount, String toAccount, Double sum) throws AccountNotFoundException, OperationException {
        if (fromAccount.equals(toAccount)) {
            throw new OperationException("Перевод внутри одного счета");
        }

        withdraw(fromAccount, sum);
        deposit(toAccount, sum);

        return JSONResponse.builder()
                .success(true)
                .message(String.format("Со счета %s на счет %s успешно переведено %f", fromAccount, toAccount, sum))
                .build();
    }

    private Account getAccount(String sAccount) throws AccountNotFoundException {
        Account account = accountRepository.findAccountByAccount(sAccount);
        if (account == null) {
            throw new AccountNotFoundException(String.format("Счета %s не существует", sAccount));
        }
        return account;
    }
}