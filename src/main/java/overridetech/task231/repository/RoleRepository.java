package overridetech.task231.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import overridetech.task231.model.Role;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    public Role findRoleByTitle(String title);

    //    @Query(value = "select title from roles", nativeQuery = true)
//    List<Role> findAllList();

    Set<Role> findAll();

    Set<Role> findByIdIn(List<Long> idList);
}
