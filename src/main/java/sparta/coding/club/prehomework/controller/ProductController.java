package sparta.coding.club.prehomework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.coding.club.prehomework.model.dto.RespLowestProduct;
import sparta.coding.club.prehomework.service.ProductService;

@RestController("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping("lowest-products-by-category")
    public ResponseEntity<RespLowestProduct> getLowestProductsByCategory() {
        return ResponseEntity.ok(productService.fetchLowerProductByCategory());
    }

    @GetMapping("one-category-product")
    public ResponseEntity<String> getProductsAsOnlyOneCategory() {
        return ResponseEntity.ok("SUCCESS");
    }
}
