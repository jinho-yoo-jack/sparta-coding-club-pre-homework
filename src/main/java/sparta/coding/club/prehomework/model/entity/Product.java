package sparta.coding.club.prehomework.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.coding.club.prehomework.model.dto.DisplayProduct;
import sparta.coding.club.prehomework.repository.CategoryConverter;

import java.math.*;

import static sparta.coding.club.prehomework.config.BigDecimalUtils.addComma;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Convert(converter = CategoryConverter.class)
    private Category category;

    private String name;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;


    public DisplayProduct toLowestProduct() {
        return DisplayProduct.builder()
                .category(category.getDisplayName())
                .brandName(brand.getName())
                .price(addComma(price))
                .build();
    }

}
