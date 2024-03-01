package overridetech.task231.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import overridetech.task231.model.User;
import overridetech.task231.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user) {
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
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }


    @Override
    public void deleteByName(String name) {
        userRepository.deleteByName(name);
    }
}
