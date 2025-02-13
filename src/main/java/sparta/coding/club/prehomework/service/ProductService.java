package sparta.coding.club.prehomework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.coding.club.prehomework.config.BigDecimalUtils;
import sparta.coding.club.prehomework.model.dto.*;
import sparta.coding.club.prehomework.model.entity.*;
import sparta.coding.club.prehomework.repository.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final JdbcProductRepository jdbcProductRepository;

    @Transactional
    public RespLowestProduct fetchLowerProductByCategory() {
        RespLowestProduct response = new RespLowestProduct();
        List<Product> allProducts = productRepository.findAll();
        Map<String, Product> filteredProducts = findLowestPriceByCategory(allProducts);
        List<DisplayProduct> displayProducts = filteredProducts.entrySet().stream().map(Map.Entry::getValue)
                .map(Product::toLowestProduct)
                .toList();
        response.setTotalPrice(BigDecimalUtils.addComma(calculateTotalPrice(filteredProducts)));
        response.setProducts(displayProducts);
        return response;
    }

    private BigDecimal calculateTotalPrice(Map<String, Product> filteredProducts) {
        return filteredProducts.entrySet().stream().map(Map.Entry::getValue)
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, Product> findLowestPriceByCategory(List<Product> allProducts) {
        return allProducts.stream()
                .collect(Collectors.groupingBy(
                        it -> it.getCategory().getDisplayName(),
                        Collectors.collectingAndThen(
                                Collectors.minBy((Comparator.comparing(Product::getPrice))),
                                opt -> opt.orElse(null) // 상품이 없는 경우 Null
                        )
                ));
    }

    @Transactional(readOnly = true)
    public RespLowestPrice fetchLowestPriceByBrand() {
        List<Product> allProducts = productRepository.findAll();
        Map<String, BigDecimal> calculateTotalPriceByBrand = calculateTotalPriceByBrand(allProducts);
        Map.Entry<String, BigDecimal> brandAndLowestTotalPrice = Collections.min(calculateTotalPriceByBrand.entrySet(), Comparator.comparing(Map.Entry::getValue));
        Brand brand = brandRepository.findByName(brandAndLowestTotalPrice.getKey()).orElseThrow(() -> new RuntimeException("Brand not found"));
        List<DisplayProduct> filteredProducts = productRepository.findAllByBrandId(brand.getId()).stream()
                .map(Product::toLowestProduct).toList();
        String totalPrice = BigDecimalUtils.addComma(brandAndLowestTotalPrice.getValue());
        SingleBrandLowestPrice singleBrandLowestPrice = SingleBrandLowestPrice.builder()
                .brandName(brand.getName())
                .categories(filteredProducts)
                .totalPrice(totalPrice)
                .build();
        return RespLowestPrice.builder().lowestPrice(singleBrandLowestPrice).build();
    }

    private Map<String, BigDecimal> calculateTotalPriceByBrand(List<Product> allProducts) {
        return allProducts.stream().collect(
                Collectors.groupingBy(i -> i.getBrand().getName(),
                        Collectors.reducing(BigDecimal.ZERO, Product::getPrice, BigDecimal::add)));
    }

    @Transactional(readOnly = true)
    public RespLowestPrice fetchLowestPriceByBrandV2() {
        BigInteger singleBrandIdLowestPrice = jdbcProductRepository.findBrandIdByMinPriceGroupByBrand();
        Brand brand = brandRepository.findById(singleBrandIdLowestPrice).orElseThrow(() -> new RuntimeException("Brand not found"));
        List<Product> filteredProducts = productRepository.findAllByBrandId(singleBrandIdLowestPrice);
        List<DisplayProduct> displayProducts = filteredProducts.stream().map(Product::toLowestProduct).toList();
        String totalPrice = BigDecimalUtils.addComma(filteredProducts.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        SingleBrandLowestPrice singleBrandLowestPrice = SingleBrandLowestPrice.builder()
                .brandName(brand.getName())
                .categories(displayProducts)
                .totalPrice(totalPrice)
                .build();
        return RespLowestPrice.builder().lowestPrice(singleBrandLowestPrice).build();
    }

    @Transactional(readOnly = true)
    public RespMinMaxPrice fetchMinMaxPriceByCategory(String categoryName) {
        List<Product> filteredProducts = productRepository.findAllByCategory(Category.fromDisplayName(categoryName));
        Product min = filteredProducts.stream().min((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())).orElseThrow();
        Product max = filteredProducts.stream().max(Comparator.comparing(Product::getPrice)).orElseThrow();
        return RespMinMaxPrice.builder()
                .category(categoryName)
                .maxPrice(List.of(max.toLowestProduct()))
                .minPrice(List.of(min.toLowestProduct()))
                .build();
    }




}
