package overridetech.task231.service;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import overridetech.task231.model.Role;
import overridetech.task231.model.User;
import overridetech.task231.model.UserDto;
import overridetech.task231.repository.RoleRepository;
import overridetech.task231.repository.UserRepository;
import overridetech.task231.util.UserMapper;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
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


    public JSONArray testRestConsume(Long userId) {
        String url = "https://cleaner.dadata.ru/api/v1/clean/address";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token 772d963b48e6ded3d8f90348789f47b7ae5e8b66");
        headers.set("X-Secret", "79f91022a7e2214c934329ec08effca00a60e9fb");
        headers.set("Accept", "application/json; charset=UTF-8");

        String body = userRepository.findById(userId).get().getAddress();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(body);

        HttpEntity<JSONArray> requestForCoordinates = new HttpEntity<>(jsonArray, headers);
        ResponseEntity<JSONArray> responseForCoordinates = restTemplate().postForEntity(url, requestForCoordinates, JSONArray.class);

        Map<String, Object> jsonMap = (Map<String, Object>) responseForCoordinates.getBody().get(0);
        String geoLat = jsonMap.get("geo_lat").toString();
        String geoLon = jsonMap.get("geo_lon").toString();

        url = "http://suggestions.dadata.ru/suggestions/api/4_1/rs/geolocate/postal_unit";

        Map<String, Double> geoMap = new HashMap<>();
        geoMap.put("lat", Double.parseDouble(geoLat));
        geoMap.put("lon", Double.parseDouble(geoLon));
        geoMap.put("radius_meters", 1000.0);

        HttpEntity<Map<String, Double>> requestForPostAddr = new HttpEntity(geoMap, headers);
        ResponseEntity<JSONObject> responseForPostAddr = restTemplate().postForEntity(url, requestForPostAddr, JSONObject.class);
        ArrayList<Object> overallPostOfficeInfo = (ArrayList<Object>) responseForPostAddr.getBody().get("suggestions");
        Map<String, Object> singlePostOfficeInfo = new HashMap<>();
        JSONArray essentialPostOfficeInfo = new JSONArray();
        for (Object o : overallPostOfficeInfo) {
            singlePostOfficeInfo = (Map<String, Object>) ((Map<String, Object>) o).get("data");
            essentialPostOfficeInfo.add(singlePostOfficeInfo);
        }

        return essentialPostOfficeInfo;
    }

}
