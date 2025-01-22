package sparta.coding.club.prehomework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sparta.coding.club.prehomework.model.entity.Category;

public class CategoryEnumTests {
    @Test
    void getDisplayName(){
        Assertions.assertEquals(Category.TOP.getDisplayName(), "상의");
    }

    @Test
    void stringToEnum(){
        Category c = Category.fromDisplayName("상의");
        Assertions.assertEquals(Category.TOP, c);
    }

    @Test
    void toEnumString(){
        String top = Category.TOP.toString();
        Category c = Category.valueOf(top);
        System.out.println(top);
        System.out.println(c.getDisplayName());
    }
}
