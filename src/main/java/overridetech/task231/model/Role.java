package overridetech.task231.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
//@DynamicUpdate
//@DynamicInsert
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
//
//    @ManyToMany
//    private User user;


    @Override
    public String getAuthority() {
        return title;
    }

    @Override
    public String toString() {
        return "id: " + id + ", title: " + title;
    }

}
