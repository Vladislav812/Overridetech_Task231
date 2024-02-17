package overridetech.task231.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import overridetech.task231.model.User;
import overridetech.task231.repository.UserRepository;

@Service
public class UserDetailsSerivceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);
//        System.out.println("is being authenticated: " + user.getName());
//        System.out.println("pass = " + user.getPassword());
//        user.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));
        if (user != null) return user;
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }
}
