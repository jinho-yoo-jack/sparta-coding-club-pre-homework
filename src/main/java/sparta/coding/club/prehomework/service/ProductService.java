package sparta.coding.club.prehomework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.coding.club.prehomework.config.BigDecimalUtils;
import sparta.coding.club.prehomework.model.dto.LowestProduct;
import sparta.coding.club.prehomework.model.dto.RespLowestProduct;
import sparta.coding.club.prehomework.model.entity.Brand;
import sparta.coding.club.prehomework.model.entity.Product;
import sparta.coding.club.prehomework.repository.BrandRepository;
import sparta.coding.club.prehomework.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    @Transactional
    public RespLowestProduct fetchLowerProductByCategory() {
        RespLowestProduct response = new RespLowestProduct();
        List<Product> allProducts = productRepository.findAll();
        Map<String, Product> filteredProducts = findLowestProductByCategory(allProducts);
        List<LowestProduct> lowestProducts = filteredProducts.entrySet().stream().map(Map.Entry::getValue)
                .map(Product::toLowestProduct).toList();
        response.setTotalPrice(BigDecimalUtils.addComma(calculateTotalPrice(filteredProducts)));
        response.setProducts(lowestProducts);
        return response;
    }

    public void fetchProductsOnlyOneCategory() {
        Brand brand = brandRepository.findByName("D").orElseThrow(() -> new RuntimeException("Brand not found"));
        List<Product> filteredProducts = productRepository.findAllByBrandId(brand.getId());
    }

    private BigDecimal calculateTotalPrice(Map<String, Product> filteredProducts) {
        return filteredProducts.entrySet().stream().map(Map.Entry::getValue)
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, Product> findLowestProductByCategory(List<Product> allProducts) {
        return allProducts.stream()
                .collect(Collectors.groupingBy(
                        it -> it.getCategory().getDisplayName(),
                        Collectors.collectingAndThen(
                                Collectors.minBy((Comparator.comparing(Product::getPrice))),
                                opt -> opt.orElse(null) // 상품이 없는 경우 Null
                        )
                ));
    }

}
