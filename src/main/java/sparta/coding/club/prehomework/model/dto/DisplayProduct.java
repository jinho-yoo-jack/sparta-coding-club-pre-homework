package sparta.coding.club.prehomework.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisplayProduct {
    private String category;
    private String brandName;
    private String price;
}
