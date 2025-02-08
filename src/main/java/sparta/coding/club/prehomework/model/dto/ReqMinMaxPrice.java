package sparta.coding.club.prehomework.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqMinMaxPrice {

    @NotBlank(message = "Required")
    String category;
}
