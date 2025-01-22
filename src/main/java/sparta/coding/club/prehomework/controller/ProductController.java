package sparta.coding.club.prehomework.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.coding.club.prehomework.model.dto.RespLowestPrice;
import sparta.coding.club.prehomework.model.dto.RespLowestProduct;
import sparta.coding.club.prehomework.model.dto.RespMinMaxPrice;
import sparta.coding.club.prehomework.service.ProductService;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping("/v1/lowest-products-by-category")
    public ResponseEntity<RespLowestProduct> getLowestProductsByCategory() {
        return ResponseEntity.ok(productService.fetchLowerProductByCategory());
    }

    @GetMapping("/v1/lowest-price-by-brand")
    public ResponseEntity<RespLowestPrice> getProductsAsOnlyOneCategory() {
        return ResponseEntity.ok(productService.fetchLowestPriceByBrand());
    }

    @GetMapping("/v2/lowest-price-by-brand")
    public ResponseEntity<RespLowestPrice> getProductsAsOnlyOneCategoryJdbc() {
        return ResponseEntity.ok(productService.fetchLowestPriceByBrandV2());
    }

    @GetMapping("/v1/min-max-price-by-category")
    public ResponseEntity<RespMinMaxPrice> getMinMaxPriceBy(@RequestParam String category){
        return ResponseEntity.ok(productService.fetchMinMaxPriceByCategory(category));
    }
}
