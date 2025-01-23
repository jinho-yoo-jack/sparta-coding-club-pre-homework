package sparta.coding.club.prehomework.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import sparta.coding.club.prehomework.model.entity.Brand;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class ReqCreateBrand {
    private String brandName;

    public Brand toEntity() {
        return new Brand(brandName);
    }
}
