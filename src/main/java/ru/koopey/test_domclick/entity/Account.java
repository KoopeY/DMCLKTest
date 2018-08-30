package ru.koopey.test_domclick.entity;

import lombok.Data;
import ru.koopey.test_domclick.exeptions.OperationException;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account")
@Data
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "account")
    private String account;

    @Column(name = "balance")
    private Double balance;

    public void deposit(Double sum) {
        balance += sum;
    }

    public void withdraw(Double sum) throws OperationException {
        if (sum > balance) {
            throw new OperationException(String.format("На счете %s меньше %f", account, sum));
        }
        balance -= sum;
    }
}
