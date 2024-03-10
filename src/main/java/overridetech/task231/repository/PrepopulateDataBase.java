package overridetech.task231.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import overridetech.task231.model.Role;
import overridetech.task231.model.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static overridetech.task231.configuration.SecurityConfiguration.getPasswordEncoder;

@Component
public class PrepopulateDataBase {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    public void prepopulateDB() {
        List<String> roles = new ArrayList<>(List.of("ROLE_admin", "ROLE_user", "ROLE_manager","ROLE_viewer","ROLE_editor", "ROLE_moderator"));
        for (String role : roles) {
            Role dbRole = new Role();
            dbRole.setTitle(role);
            roleRepository.save(dbRole);
        }

        List<String> names = List.of("Name1", "Name2", "Name3", "Name4", "Psina Sutulaya");
        List<Integer> ages = List.of(21,22,23,24,30);
        List<String> emails = List.of("email1@gmail.com", "email2@gmail.com", "email3@gmail.com", "email4@gmail.com", "psina@mail.ru");
        List<String> passwords = List.of("111", "222", "333", "444", "psina");
        List<String> addresses = List.of("мск ленинский пр 88 ", "Москва кедрова 19 ", " спб Лиговский 245", "мск осипенко 22", "Санкт-Петербург невский 65");
        List<Set<Role>> currentRoles = List.of(
                Set.of(new Role(1L),new Role(2L),new Role(3L), new Role(4L)),
                Set.of(new Role(1L)),
                Set.of(new Role(2L)),
                Set.of(new Role(2L)),
                Set.of(new Role(5L))
                );

        for (int i = 0; i< names.size(); i++) {
            User user = new User();
            user.setName(names.get(i));
            user.setAge(ages.get(i));
            user.setEmail(emails.get(i));
            user.setPassword(getPasswordEncoder().encode(passwords.get(i)));
            user.setAddress(addresses.get(i));
            user.setCurrentRoles(currentRoles.get(i));
            userRepository.save(user);
        }
    }
}
