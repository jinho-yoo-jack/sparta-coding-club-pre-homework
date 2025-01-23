package sparta.coding.club.prehomework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.coding.club.prehomework.model.dto.*;
import sparta.coding.club.prehomework.service.AdminService;
import sparta.coding.club.prehomework.service.ProductService;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    private final ProductService productService;

    @PostMapping("/v1/create/brand")
    public ResponseEntity<RespCreateBrand> createNewBrand(@RequestBody ReqCreateBrand newBrand) {
        return ResponseEntity.ok(adminService.addNewBrand(newBrand));
    }

    @PostMapping("/v1/create/product")
    public ResponseEntity<RespCreateProduct> createNewProduct(@RequestBody ReqCreateProduct newProduct) {
        return ResponseEntity.ok(adminService.addNewProduct(newProduct));
    }

    @PostMapping("/v1/modify/product")
    public ResponseEntity<RespUpdateProduct> modifyProduct(@RequestBody ReqModifyProduct productInfo) {
        return ResponseEntity.ok(adminService.updateProduct(productInfo));
    }

    @PostMapping("/v1/delete/product")
    public ResponseEntity<RespDeletedProduct> deleteProduct(@RequestParam BigInteger productId) {
        return ResponseEntity.ok(adminService.deleteProduct(productId));
    }
}
