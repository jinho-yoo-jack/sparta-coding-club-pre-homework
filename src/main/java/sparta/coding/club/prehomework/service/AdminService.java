package sparta.coding.club.prehomework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.coding.club.prehomework.global.exception.FailInsertToPersistence;
import sparta.coding.club.prehomework.model.dto.*;
import sparta.coding.club.prehomework.model.entity.Brand;
import sparta.coding.club.prehomework.model.entity.Category;
import sparta.coding.club.prehomework.model.entity.Product;
import sparta.coding.club.prehomework.repository.BrandRepository;
import sparta.coding.club.prehomework.repository.ProductRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public RespCreateBrand addNewBrand(ReqCreateBrand newBrand) {
        return RespCreateBrand.of(brandRepository.save(newBrand.toEntity()));
    }

    @Transactional
    public RespCreateProduct addNewProduct(ReqCreateProduct newProduct) {
        Brand brand = findBrandByName(newProduct.getBrandName());
        BigInteger brandId = brand.getId();
        productRepository.findByCategoryAndBrandId(Category.fromDisplayName(newProduct.getCategory()), brandId)
                .ifPresentOrElse(p -> {
                    throw new IllegalArgumentException("Already Exist Product For Brand.");
                }, () -> productRepository.save(newProduct.toEntity(brand)));

        return RespCreateProduct.of(productRepository.findByCategoryAndBrandId(Category.fromDisplayName(newProduct.getCategory()), brandId)
                .orElseThrow(() -> new FailInsertToPersistence("Fail Add New Product")));
    }

    @Transactional
    public RespUpdateProduct updateProduct(ReqModifyProduct productInfo) {
        Product prevProduct = productRepository.findById(productInfo.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product Not Found"));

        Brand brand = findBrandByName(productInfo.getBrandName());
        BigInteger afterBrandId = brand.getId();
        productRepository.findByCategoryAndBrandId(Category.fromDisplayName(productInfo.getCategory()), afterBrandId)
                .ifPresentOrElse(p -> {
                    throw new IllegalArgumentException("Already Exist Product For Brand.");
                }, () -> {
                    prevProduct.setDeletedAt(Date.from(Instant.now()));
                    productRepository.save(productInfo.toEntity(brand));
                });

        return RespUpdateProduct.of(productRepository.findByCategoryAndBrandId(Category.fromDisplayName(productInfo.getCategory()), afterBrandId)
                .orElseThrow(() -> new FailInsertToPersistence("Fail Add New Product")));
    }

    @Transactional
    public RespUpdateProduct updateProductV2(ReqModifyProduct productInfo) {
        Product prevProduct = productRepository.findById(productInfo.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product Not Found"));

        Product afterProduct = Product.builder()
                .id(prevProduct.getId())
                .category(Category.fromDisplayName(productInfo.getCategory()))
                .name(productInfo.getProductName())
                .brand(prevProduct.getBrand().getName().equals(productInfo.getBrandName()) ? prevProduct.getBrand() :
                        brandRepository.findByName(productInfo.getProductName().toUpperCase()).orElseThrow(() -> new NoSuchElementException("Not brand")))
                .price(BigDecimal.valueOf(productInfo.getPrice()))
                .build();

        productRepository.save(afterProduct);
        return RespUpdateProduct.of(afterProduct);
    }

    public RespDeletedProduct deleteProduct(BigInteger id) {
        Product p = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product Not Found"));
        p.setDeletedAt(Date.from(Instant.now()));
        return RespDeletedProduct.of(productRepository.save(p));
    }

    private Brand findBrandByName(String brandName) {
        return brandRepository.findByName(brandName)
                .orElseThrow(() -> new NoSuchElementException("Not Found Brand. Please add the brand first."));
    }
}
