package sparta.coding.club.prehomework.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespLowestPrice {
    private SingleBrandLowestPrice lowestPrice;
}
