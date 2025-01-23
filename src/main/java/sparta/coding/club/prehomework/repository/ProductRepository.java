package sparta.coding.club.prehomework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.coding.club.prehomework.model.entity.Category;
import sparta.coding.club.prehomework.model.entity.Product;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, BigInteger> {
    List<Product> findAllByBrandId(BigInteger brandId);
    List<Product> findAllByCategory(Category category);

    Optional<Product> findByCategoryAndBrandId(Category category, BigInteger brandId);
}
