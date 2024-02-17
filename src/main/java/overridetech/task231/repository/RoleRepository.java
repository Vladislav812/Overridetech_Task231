package overridetech.task231.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import overridetech.task231.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    public Role findRoleByTitle(String title);

}
