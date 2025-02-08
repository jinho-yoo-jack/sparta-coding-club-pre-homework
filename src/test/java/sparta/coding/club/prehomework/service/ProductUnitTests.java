package sparta.coding.club.prehomework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import sparta.coding.club.prehomework.model.entity.Brand;
import sparta.coding.club.prehomework.model.entity.Category;
import sparta.coding.club.prehomework.model.entity.Product;
import sparta.coding.club.prehomework.repository.ProductRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductUnitTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void findAll() {
        List<Product> products = productRepository.findAll();
        Assertions.assertFalse(products.isEmpty());
    }

    @Test
    void findLowestPriceByCategory_IncludeProductId48_True() {
        List<Product> allProducts = productRepository.findAll();
        Map<String, Product> result = allProducts.stream()
            .collect(groupingBy(
                i -> i.getCategory().getDisplayName(),
                Collectors.collectingAndThen(
                    Collectors.minBy((Comparator.comparing(Product::getPrice))),
                    opt -> opt.orElse(null) // 상품이 없는 경우 Null
                )
            ));
        Product actual = result.get("액세서리");
        Product expected = new Product(BigInteger.valueOf(48), Category.ACCESSORY, "ACCESSORY", new BigDecimal("1900.00"), new Brand(BigInteger.valueOf(6), "F"), null);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void findLowestPriceByBrand_ReturnBrandIsD_True() {
        List<Product> allProducts = productRepository.findAll();
        Map<String, BigDecimal> r = allProducts.stream().collect(
            Collectors.groupingBy(i -> i.getBrand().getName(),
                Collectors.reducing(BigDecimal.ZERO, Product::getPrice, BigDecimal::add))
        );
        Map.Entry<String, BigDecimal> actual = Collections.min(r.entrySet(), Comparator.comparing(Map.Entry::getValue));
        Map.Entry<String, BigDecimal> expected =
            new AbstractMap.SimpleEntry<String, BigDecimal>("D", new BigDecimal("36100.00"));

        Assertions.assertEquals(expected.getKey(), actual.getKey());
        Assertions.assertEquals(expected.getValue(), actual.getValue());

    }

    @Test
    void calculateTotalPriceByCategoryByLowestPrice_34100_True() {
        List<Product> allProducts = productRepository.findAll();
        BigDecimal actualTotalPrice = allProducts.stream()
            .collect(groupingBy(
                i -> i.getCategory().getDisplayName(),
                Collectors.collectingAndThen(
                    Collectors.minBy((Comparator.comparing(Product::getPrice))),
                    opt -> opt.orElse(null) // 상품이 없는 경우 Null
                )
            )).entrySet().stream().map(Map.Entry::getValue).map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expected = BigDecimal.valueOf(34_100);
        ;
        Assertions.assertEquals(0, (actualTotalPrice.compareTo(expected)));
    }

    @Test
    void calculateMinPriceByCategory_CategoryTop_10000() {
        List<Product> filteredProducts = productRepository.findAllByCategory(Category.TOP);
        Product actual = Collections.min(filteredProducts, Comparator.comparing(Product::getPrice));
        BigDecimal actualPrice = actual.getPrice();
        BigDecimal expectedPrice = BigDecimal.valueOf(10_000);
        Assertions.assertEquals(0, (actualPrice.compareTo(expectedPrice)));
    }

}
