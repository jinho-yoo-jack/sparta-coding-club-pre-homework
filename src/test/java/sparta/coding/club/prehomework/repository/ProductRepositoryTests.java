package sparta.coding.club.prehomework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sparta.coding.club.prehomework.model.entity.Product;

import java.util.List;

@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void findAllByDeletedAtIsNullTest(){
        List<Product> allProducts = productRepository.findAll();
        System.out.println(allProducts.size());

    }
}
