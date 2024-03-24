package overridetech.task231.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import overridetech.task231.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findBySellerIdOrderByIdAsc(Long id);

    List<Product> findAllByOrderByIdAsc();
}
