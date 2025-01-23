package sparta.coding.club.prehomework.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import sparta.coding.club.prehomework.model.entity.Brand;
import sparta.coding.club.prehomework.model.entity.Category;
import sparta.coding.club.prehomework.model.entity.Product;

import java.math.BigDecimal;
import java.math.BigInteger;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class ReqModifyProduct {
    private BigInteger productId;
    private String category;
    private String brandName;
    private String productName;
    private long price;

    public Product toEntity(Brand brand){
        return Product.builder()
                .name(productName)
                .category(Category.fromDisplayName(category))
                .price(BigDecimal.valueOf(price))
                .brand(brand)
                .build();

    }
}
