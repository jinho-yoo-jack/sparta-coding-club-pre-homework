package sparta.coding.club.prehomework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sparta.coding.club.prehomework.model.dto.ReqCreateBrand;
import sparta.coding.club.prehomework.model.dto.ReqCreateProduct;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void newBrand_CorrectConstraintValue_2XX() throws Exception {
        ReqCreateBrand requestMessage = new ReqCreateBrand("Z");
        String requestJson = objectMapper.writeValueAsString(requestMessage);

        mockMvc.perform(post("/api/admin/v1/create/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // Expect HTTP 200 OK status
            .andExpect(jsonPath("$.data.brandName").value("Z")) // Optionally, check the response body (adjust as needed)
            .andExpect(jsonPath("$.data.brandId").exists()); //
    }

    @Test
    @Order(2)
    void newProduct_CorrectConstraintValue_2XX() throws Exception {
        ReqCreateProduct requestMessage = ReqCreateProduct.builder()
            .brandName("Z")
            .category("상의")
            .productName("상의")
            .price(10_000)
            .build();

        String requestJson = objectMapper.writeValueAsString(requestMessage);

        mockMvc.perform(post("/api/admin/v1/create/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // Expect HTTP 200 OK status
            .andExpect(jsonPath("$.data.brandName").value("Z"))
            .andExpect(jsonPath("$.data.category").value("상의"))
            .andExpect(jsonPath("$.data.productName").value("상의"))
            .andExpect(jsonPath("$.data.price").value("10,000"));
    }

    @Test
    void newProduct_NotSuchElements_5XX() throws Exception {
        ReqCreateProduct requestMessage = ReqCreateProduct.builder()
            .brandName("S")
            .category("상의")
            .productName("상의")
            .price(10_000)
            .build();

        String requestJson = objectMapper.writeValueAsString(requestMessage);

        mockMvc.perform(post("/api/admin/v1/create/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is5xxServerError()) // Expect HTTP 200 OK status
            .andExpect(jsonPath("$.error").exists());
    }


}
