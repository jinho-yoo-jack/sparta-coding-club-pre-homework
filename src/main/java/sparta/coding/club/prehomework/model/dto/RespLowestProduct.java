package sparta.coding.club.prehomework.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RespLowestProduct {
    private String totalPrice;
    private List<DisplayProduct> products;
}
