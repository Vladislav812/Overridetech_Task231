package overridetech.task231.service;

import overridetech.task231.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    List<User> findAllByOrderByIdAsc();

    User findById(long id);

    void deleteById(long id);

    void deleteByName(String name);

    User findUserByName(String name);
}
