package sparta.coding.club.prehomework.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import sparta.coding.club.prehomework.model.dto.DisplayProduct;
import sparta.coding.club.prehomework.repository.CategoryConverter;

import java.math.*;
import java.util.Date;

import static sparta.coding.club.prehomework.config.BigDecimalUtils.addComma;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL") // Before @Where(clause = "deleted_at IS NOT NULL")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Convert(converter = CategoryConverter.class)
    private Category category;

    private String name;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;


    public DisplayProduct toLowestProduct() {
        return DisplayProduct.builder()
                .category(category.getDisplayName())
                .brandName(brand.getName())
                .price(addComma(price))
                .build();
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

}
