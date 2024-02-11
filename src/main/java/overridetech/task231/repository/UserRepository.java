package overridetech.task231.repository;

import org.springframework.data.repository.CrudRepository;
import overridetech.task231.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
   public List<User> findAllByOrderByIdAsc();
}
