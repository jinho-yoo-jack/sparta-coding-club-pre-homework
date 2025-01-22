package sparta.coding.club.prehomework.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespMinMaxPrice {
    private String category;
    private List<DisplayProduct> minPrice;
    private List<DisplayProduct> maxPrice;
}
