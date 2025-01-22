package sparta.coding.club.prehomework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;

@SpringBootTest
public class JdbcProductRepositoryTests {
    @Autowired
    private JdbcProductRepository jdbcProductRepository;

    @Test
    void getBrandIdTests(){
        BigInteger brandId = jdbcProductRepository.findBrandIdByMinPriceGroupByBrand();
        System.out.println(brandId);
    }
}
