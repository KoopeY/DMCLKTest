package ru.koopey.test_domclick;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.koopey.test_domclick.entity.Account;
import ru.koopey.test_domclick.repository.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestDomclickApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountRepository accountRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void deposit_thenCheck() throws Exception {
	    createAccount("42760000123456789100", 100D);
        mvc.perform(
            post("/account/deposit")
                .param("to", "42760000123456789100")
                .param("sum", "50.5")
        )
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        );

        Account account = accountRepository.findAccountByAccount("42760000123456789100");
        assertThat(account.getAccount()).isEqualTo("42760000123456789100");
        assertThat(account.getBalance()).isEqualTo(150.5);
	}

	@Test
    public void withdraw_thenCheck() throws Exception {
        createAccount("42760000123456789101", 50D);
        mvc.perform(
            post("/account/withdraw")
                .param("from", "42760000123456789101")
                .param("sum", "49.99")
        )
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        );

        Account account = accountRepository.findAccountByAccount("42760000123456789101");
        assertThat(account.getAccount()).isEqualTo("42760000123456789101");
        assertThat(Math.round(account.getBalance() * 100) / 100.0).isEqualTo(0.01);
    }

    @Test
    public void transfer_thenCheck() throws Exception {
        createAccount("42760000123456789102", 50D);
        createAccount("42760000123456789103", 50D);

        mvc.perform(
            post("/account/transfer")
                .param("from", "42760000123456789102")
                .param("to", "42760000123456789103")
                .param("sum", "10")
        )
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        );

        Account fromAccount = accountRepository.findAccountByAccount("42760000123456789102");
        assertThat(fromAccount.getAccount()).isEqualTo("42760000123456789102");
        assertThat(fromAccount.getBalance()).isEqualTo(40);

        Account toAccount = accountRepository.findAccountByAccount("42760000123456789103");
        assertThat(toAccount.getAccount()).isEqualTo("42760000123456789103");
        assertThat(toAccount.getBalance()).isEqualTo(60);
    }

    @Test
    public void transferBetweenSameAccount_thenCheck() throws Exception {
        createAccount("42760000123456789104", 50D);
        mvc.perform(
            post("/account/transfer")
                .param("from", "42760000123456789104")
                .param("to", "42760000123456789104")
                .param("sum", "10")
        )
        .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void depositToNotCreatedAccount_thenCheck() throws Exception {
        mvc.perform(
            post("/account/deposit")
                .param("to", "42760000123456789105")
                .param("sum", "10")
        )
        .andExpect(status().isNotFound());
    }

    @Test
    public void transferNotEnoughMoney_thenCheck() throws Exception {
        createAccount("42760000123456789106", 10D);
        createAccount("42760000123456789107", 10D);

        mvc.perform(
            post("/account/transfer")
                .param("from", "42760000123456789106")
                .param("to", "42760000123456789107")
                .param("sum", "20")
        )
        .andExpect(status().isMethodNotAllowed());
    }

	private void createAccount(String account, Double balance) {
        Account acc = new Account();
        acc.setAccount(account);
        acc.setBalance(balance);
        accountRepository.save(acc);
    }
}
