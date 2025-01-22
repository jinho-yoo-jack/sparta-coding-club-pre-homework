package sparta.coding.club.prehomework.model.dto;

import lombok.Data;
import sparta.coding.club.prehomework.model.entity.Product;

import java.util.List;

@Data
public class RespLowestProduct {
    private String totalPrice;
    private List<LowestProduct> products;
}
