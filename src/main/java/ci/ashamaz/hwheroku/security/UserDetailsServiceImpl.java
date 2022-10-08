package ci.ashamaz.hwheroku.security;

import ci.ashamaz.hwheroku.entity.Account;
import ci.ashamaz.hwheroku.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = Optional.ofNullable(accountService.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("Account " + username + " not found."));

        return new UserDetailsImpl(
                account.getEmail(),
                account.getPassword(),
                account.isEnabled(),
                account.getRoles()
                );
    }
}
