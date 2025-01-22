package sparta.coding.club.prehomework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sparta.coding.club.prehomework.model.entity.Product;
import sparta.coding.club.prehomework.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@SpringBootTest
public class ProductServiceTests {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    void findAll() {
        List<Product> products = productRepository.findAll();
        Assertions.assertFalse(products.isEmpty());
    }

    @Test
    void findLowestPriceByCategory() {
        List<Product> allProducts = productRepository.findAll();
        Map<String, Product> result = allProducts.stream()
                .collect(groupingBy(
                        i -> i.getCategory().getDisplayName(),
                        Collectors.collectingAndThen(
                                Collectors.minBy((Comparator.comparing(Product::getPrice))),
                                opt -> opt.orElse(null) // 상품이 없는 경우 Null
                        )
                ));
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void searchMinimumPriceBrand() {
        List<Product> allProducts = productRepository.findAll();
        Map<String, List<Product>> r = allProducts.stream().collect(
                Collectors.groupingBy(i -> i.getBrand().getName()));
        r.entrySet().stream().map(Map.Entry::getValue).flatMap(List::stream).forEach(System.out::println);
    }

    @Test
    void totalPrice() {
        List<Product> allProducts = productRepository.findAll();
        Map<String, Product> filteredProducts = allProducts.stream()
                .collect(groupingBy(
                        i -> i.getCategory().getDisplayName(),
                        Collectors.collectingAndThen(
                                Collectors.minBy((Comparator.comparing(Product::getPrice))),
                                opt -> opt.orElse(null) // 상품이 없는 경우 Null
                        )
                ));

        BigDecimal totalPrice = filteredProducts.entrySet().stream().map(Map.Entry::getValue)
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Assertions.assertTrue(totalPrice.compareTo(BigDecimal.valueOf(34_100)) == 0);
    }

}
