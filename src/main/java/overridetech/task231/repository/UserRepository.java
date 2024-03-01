package overridetech.task231.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import overridetech.task231.model.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public List<User> findAllByOrderByIdAsc();

    @Query(value = "SELECT name FROM users", nativeQuery = true)
    public List<String> findAllUserNames();

    public List<User> findByName(String name);

    public User findUserByName(String name);

    @Transactional
    void deleteByName(String name);
}
