package overridetech.task231.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
    //@DynamicUpdate
    //@DynamicInsert
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "\"name\" cannot be empty!")
    private String name;

    @PositiveOrZero(message = "\"age\" cannot be negative!")
    private int age;

    @NotBlank(message = "password cannot be empty!")
    private String password;

    //    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
//            message = "this is not valid email!")
    @NotBlank(message = "\"email\" cannot be empty!")
    @Email(message = "this is not valid email!")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> currentRoles;

    public String getRolesString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Role> iterator = currentRoles.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().getAuthority());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public Set<Long> getRolesId() {
        Set<Long> set = new HashSet<>();
        Iterator<Role> iterator = currentRoles.iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next().getId());
        }
        return set;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return currentRoles;
    }

    @Override
    public String getUsername() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
