package sparta.coding.club.prehomework.model.entity;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public enum Category {
    TOP("상의"),
    OUTER("아우터"),
    PANTS("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORY("액세서리");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public static Category fromDisplayName(String displayName) {
        return switch (displayName) {
            case "상의" -> TOP;
            case "아우터" -> OUTER;
            case "바지" -> PANTS;
            case "스니커즈" -> SNEAKERS;
            case "가방" -> BAG;
            case "모자" -> HAT;
            case "양말" -> SOCKS;
            case "액세서리" -> ACCESSORY;
            default -> throw new NoSuchElementException(displayName);
        };
    }
}
