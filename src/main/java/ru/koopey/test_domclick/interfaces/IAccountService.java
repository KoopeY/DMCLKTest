package ru.koopey.test_domclick.interfaces;

import ru.koopey.test_domclick.exeptions.AccountNotFoundException;
import ru.koopey.test_domclick.exeptions.OperationException;
import ru.koopey.test_domclick.model.JSONResponse;

public interface IAccountService {
    JSONResponse deposit(String account, Double sum) throws AccountNotFoundException;
    JSONResponse withdraw(String account, Double sum) throws AccountNotFoundException, OperationException;
    JSONResponse transfer(String fromAccount, String toAccount, Double sum) throws AccountNotFoundException, OperationException;
}
