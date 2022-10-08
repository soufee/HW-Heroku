package ci.ashamaz.hwheroku.service;


import ci.ashamaz.hwheroku.entity.Account;
import ci.ashamaz.hwheroku.enums.RoleEnum;
import ci.ashamaz.hwheroku.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService{
    private final AccountRepo accountRepository;

    @Override
    public Account findByName(String name) {
        return accountRepository.findByName(name);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account getOne(Long id) {
        return accountRepository.getReferenceById(id);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.getByEmail(email);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
        if (findById(account.getId()) != null) {
            throw new IllegalStateException("Не удалось удалить пользователя " + account.getUsername());
        }
    }

    @Override
    public void addNewAccount(Account account) {
        Account byEmail = findByEmail(account.getEmail());
        if (byEmail != null) {
            throw new IllegalArgumentException("User with email " + account.getEmail() + " is registered");
        }
        account.setEnabled(true);
        account.getRoles().add(RoleEnum.USER);
        accountRepository.save(account);
    }
}
