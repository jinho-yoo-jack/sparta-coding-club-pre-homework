package sparta.coding.club.prehomework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.coding.club.prehomework.model.entity.Category;
import sparta.coding.club.prehomework.model.entity.Product;

import java.math.BigInteger;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, BigInteger> {
    List<Product> findAllByBrandId(BigInteger brandId);
    List<Product> findAllByCategory(Category category);
}
