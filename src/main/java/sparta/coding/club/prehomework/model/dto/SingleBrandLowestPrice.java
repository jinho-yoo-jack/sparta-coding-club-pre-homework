package sparta.coding.club.prehomework.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SingleBrandLowestPrice {
    private String brandName;
    private List<DisplayProduct> categories;
    private String totalPrice;
}
