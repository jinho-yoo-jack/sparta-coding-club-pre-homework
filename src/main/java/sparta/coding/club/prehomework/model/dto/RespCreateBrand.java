package sparta.coding.club.prehomework.model.dto;

import lombok.Builder;
import lombok.Data;
import sparta.coding.club.prehomework.model.entity.Brand;

@Data
@Builder
public class RespCreateBrand {
    private String brandId;
    private String brandName;

    public static RespCreateBrand of(Brand brand) {
        return RespCreateBrand.builder()
                .brandId(brand.getId().toString())
                .brandName(brand.getName())
                .build();
    }
}
