package overridetech.task231.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrepopulateDataBase {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


}
