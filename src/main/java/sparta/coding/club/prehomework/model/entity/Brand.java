package sparta.coding.club.prehomework.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table(name = "brand")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(unique = true, nullable = false)
    private String name;

    public Brand(String name) {
        this.name = name;
    }

    public Brand(BigInteger id, String name) {
        this.id = id;
        this.name = name;
    }
}
