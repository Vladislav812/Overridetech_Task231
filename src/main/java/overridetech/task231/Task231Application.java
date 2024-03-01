package overridetech.task231;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import overridetech.task231.model.Role;
import overridetech.task231.model.User;
import overridetech.task231.repository.RoleRepository;
import overridetech.task231.service.UserService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Task231Application {


    public static void main(String[] args) {
        SpringApplication.run(Task231Application.class, args);
    }

}
