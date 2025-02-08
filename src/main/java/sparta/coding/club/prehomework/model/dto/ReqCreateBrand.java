package sparta.coding.club.prehomework.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.coding.club.prehomework.model.entity.Brand;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReqCreateBrand {
    private String brandName;

    public Brand toEntity() {
        return new Brand(brandName);
    }
}
