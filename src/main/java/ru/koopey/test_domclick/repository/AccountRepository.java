package ru.koopey.test_domclick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.koopey.test_domclick.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByAccount(String account);
}
