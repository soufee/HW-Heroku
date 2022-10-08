package ci.ashamaz.hwheroku.service;


import ci.ashamaz.hwheroku.entity.Account;

import java.util.List;

public interface AccountService {

    Account findByName(String name);

    List<Account> findAll();

    Account findById(Long id);

    Account getOne(Long id);

    Account findByEmail(String email);

    Account save(Account account);

    void delete(Account account);

    void addNewAccount(Account account);

}
