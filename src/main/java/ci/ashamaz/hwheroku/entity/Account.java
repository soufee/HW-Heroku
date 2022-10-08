package ci.ashamaz.hwheroku.entity;

import ci.ashamaz.hwheroku.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonIgnore
    @ToString.Exclude
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    @JoinTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<RoleEnum> roles = new ArrayList<>();

    private boolean enabled;

    public String getUsername() {
        return email;
    }

    public boolean isAdmin() {
        return roles.contains(RoleEnum.ADMIN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return enabled == account.enabled
                && Objects.equals(id, account.id)
                && name.equals(account.name)
                && password.equals(account.password)
                && email.equals(account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, enabled);
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + '}';
    }
}
