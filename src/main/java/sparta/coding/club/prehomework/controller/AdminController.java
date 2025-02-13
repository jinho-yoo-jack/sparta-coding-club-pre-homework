package sparta.coding.club.prehomework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.coding.club.prehomework.global.ApiResponse;
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
    public ResponseEntity<ApiResponse<RespCreateBrand>> createNewBrand(@RequestBody ReqCreateBrand newBrand) {
        return ResponseEntity.ok(ApiResponse.success(adminService.addNewBrand(newBrand)));
    }

    @PostMapping("/v1/create/product")
    public ResponseEntity<ApiResponse<RespCreateProduct>> createNewProduct(@RequestBody ReqCreateProduct newProduct) {
        return ResponseEntity.ok(ApiResponse.success(adminService.addNewProduct(newProduct)));
    }

    @PostMapping("/v1/modify/product")
    public ResponseEntity<ApiResponse<RespUpdateProduct>> modifyProduct(@RequestBody ReqModifyProduct productInfo) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateProduct(productInfo)));
    }

    @PostMapping("/v2/modify/product")
    public ResponseEntity<ApiResponse<RespUpdateProduct>> modifyProductV2(@RequestBody ReqModifyProduct productInfo) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateProductV2(productInfo)));
    }

    @PostMapping("/v1/delete/product")
    public ResponseEntity<ApiResponse<RespDeletedProduct>> deleteProduct(@RequestParam BigInteger productId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.deleteProduct(productId)));
    }
}
