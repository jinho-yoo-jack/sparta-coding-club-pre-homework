package sparta.coding.club.prehomework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.coding.club.prehomework.model.entity.*;

import java.math.BigInteger;
import java.util.*;

public interface ProductRepository extends JpaRepository<Product, BigInteger> {
    List<Product> findAllByBrandId(BigInteger brandId);

    List<Product> findAllByCategory(Category category);

    Optional<Product> findByCategoryAndBrandId(Category category, BigInteger brandId);
}
