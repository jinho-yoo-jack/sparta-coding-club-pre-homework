package sparta.coding.club.prehomework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sparta.coding.club.prehomework.model.entity.Category;
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
        Map<String, BigDecimal> r = allProducts.stream().collect(
            Collectors.groupingBy(i -> i.getBrand().getName(),
                Collectors.reducing(BigDecimal.ZERO, Product::getPrice, BigDecimal::add)));
        Map.Entry<String, BigDecimal> r1 =  Collections.min(r.entrySet(), Comparator.comparing(Map.Entry::getValue));
        System.out.println(r1.getKey());
        System.out.println(r1.getValue());
    }

    Comparator<Map.Entry<String, BigDecimal>> comparator = new Comparator<Map.Entry<String, BigDecimal>>() {
        @Override
        public int compare(Map.Entry<String, BigDecimal> e1, Map.Entry<String, BigDecimal> e2) {
            return e1.getValue().compareTo(e2.getValue());
        }
    };

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

    @Test
    void getMinMaxPriceByCategory(){
        List<Product> filteredProducts = productRepository.findAllByCategory(Category.TOP);
        Product min = filteredProducts.stream().min((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())).orElseThrow();
        Product max = filteredProducts.stream().max((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())).orElseThrow();

        Assertions.assertTrue(min.getPrice().compareTo(BigDecimal.valueOf(10_000)) == 0);
        Assertions.assertTrue("C".equals(min.getBrand().getName().toUpperCase()));
    }

}
