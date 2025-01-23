package sparta.coding.club.prehomework.model.dto;

import lombok.Builder;
import lombok.Data;
import sparta.coding.club.prehomework.config.BigDecimalUtils;
import sparta.coding.club.prehomework.model.entity.Product;

import java.math.BigDecimal;

@Data
@Builder
public class RespCreateProduct {
    private String brandName;
    private String category;
    private String productName;
    private String price;

    public static RespCreateProduct of(Product p){
        return RespCreateProduct.builder()
                .brandName(p.getBrand().getName())
                .category(p.getCategory().getDisplayName())
                .productName(p.getName())
                .price(BigDecimalUtils.addComma(p.getPrice()))
                .build();
    }
}
