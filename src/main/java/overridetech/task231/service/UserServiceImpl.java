package overridetech.task231.service;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import overridetech.task231.model.Role;
import overridetech.task231.model.User;
import overridetech.task231.dto.UserDto;
import overridetech.task231.repository.RoleRepository;
import overridetech.task231.repository.UserRepository;
import overridetech.task231.util.UserMapper;

import java.util.*;

import static overridetech.task231.configuration.SecurityConfiguration.getPasswordEncoder;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DaDataService daDataService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);
        if (user != null) return user;
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    @Override
    public void saveUser(User user) {
        Set<Role> currentRoles = new HashSet<>();
        for (Role role : user.getCurrentRoles()) {
            currentRoles.add(roleRepository.findById(role.getId()).get());
        }
        user.setCurrentRoles(currentRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Modifying
    public void patchUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).get();
        userMapper.updateUserFromDto(userDto, user);
        userRepository.save(user);
    }

    @Override
    public List<User> findAllByOrderByIdAsc() {
        return (List<User>) userRepository.findAllByOrderByIdAsc();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUser(JSONObject jsonObject) {
        Long id = Long.parseLong((String) jsonObject.get("id"));
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        userRepository.deleteByName(name);
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public List<Role> getAllRoles() {
        return new ArrayList<Role>(roleRepository.findAll());
    }


    public JSONArray getNearestPostOffices(Long userId) {
        return daDataService.getNearestPostOffices(userId);
    }

    @Override
    public void prepopulateDB() {
        List<String> roles = new ArrayList<>(List.of("ROLE_admin", "ROLE_user", "ROLE_seller", "ROLE_manager", "ROLE_moderator"));
        for (String role : roles) {
            Role dbRole = new Role();
            dbRole.setTitle(role);
            roleRepository.save(dbRole);
        }

        List<String> names = List.of("Name1", "Name2", "Name3", "Name4", "Psina Sutulaya", "Name5");
        List<Integer> ages = List.of(21, 22, 23, 24, 30, 44);
        List<String> emails = List.of("email1@gmail.com", "email2@gmail.com", "email3@gmail.com", "email4@gmail.com", "psina@mail.ru", "email5@gmail.com");
        List<String> passwords = List.of("111", "222", "333", "444", "psina", "555");
        List<String> addresses = List.of("мск ленинский пр 88 ", "Москва кедрова 19 ", " спб Лиговский 245", "мск осипенко 22", "Санкт-Петербург невский 65", "Казан горьког 29");
        List<Set<Role>> currentRoles = List.of(
                Set.of(new Role(1L), new Role(2L), new Role(3L), new Role(4L)),
                Set.of(new Role(1L)),
                Set.of(new Role(2L)),
                Set.of(new Role(3L)),
                Set.of(new Role(3L)),
                Set.of(new Role(2L))
        );

        for (int i = 0; i < names.size(); i++) {
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
