package ci.ashamaz.hwheroku.security;

import ci.ashamaz.hwheroku.entity.Account;
import ci.ashamaz.hwheroku.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
   @Autowired
   private AccountService accountService;
    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        Account byEmail = accountService.findByEmail(userDetails.getUsername());
        accountService.save(byEmail);
    }
}
