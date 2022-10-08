package ci.ashamaz.hwheroku.repo;

import ci.ashamaz.hwheroku.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByName(String name);

    Account getByEmail(String email);

}
