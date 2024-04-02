package overridetech.task231.service;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import overridetech.task231.model.Role;
import overridetech.task231.model.User;
import overridetech.task231.dto.UserDto;

import java.util.List;

public interface UserService {
    public void saveUser(User user);

    public void patchUser(UserDto userDto);

    public List<User> findAllByOrderByIdAsc();

    public User findById(long id);

    public void deleteUser(JSONObject jsonObject);

    public void deleteByName(String name);

    public User findUserByName(String name);

    public List<Role> getAllRoles();

    public JSONArray getNearestPostOffices(Long userId);

    void prepopulateDB();
}
