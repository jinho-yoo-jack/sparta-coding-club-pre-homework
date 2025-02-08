package sparta.coding.club.prehomework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import sparta.coding.club.prehomework.model.dto.ReqCreateBrand;
import sparta.coding.club.prehomework.model.dto.RespCreateBrand;
import sparta.coding.club.prehomework.model.entity.Brand;
import sparta.coding.club.prehomework.repository.BrandRepository;
import sparta.coding.club.prehomework.repository.ProductRepository;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTests {
    @MockitoBean
    private AdminService adminService;

    @Test
    void addNewBrand_CorrectConstraintValue_True() {
        BrandRepository brandRepository = Mockito.mock(BrandRepository.class);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        adminService = new AdminService(brandRepository, productRepository);

        // Given
        ReqCreateBrand createBrand = new ReqCreateBrand("Z");
        RespCreateBrand expectedMessage = RespCreateBrand.builder().brandId("99").brandName("Z").build();
        Brand createdBrand = new Brand(BigInteger.valueOf(99), "Z");
        Mockito.when(brandRepository.save(any())).thenReturn(createdBrand); // Mock saving the brand

        // When
        RespCreateBrand respCreateBrand = adminService.addNewBrand(createBrand); // Call the method

        // Then
        Mockito.verify(brandRepository, Mockito.times(1)).save(any()); // Verify save was called on brandRepository

        Assertions.assertEquals(respCreateBrand, expectedMessage);
    }


}
