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

}
