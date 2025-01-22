package sparta.coding.club.prehomework.repository;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import sparta.coding.club.prehomework.model.entity.Category;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {
    @Override
    public String convertToDatabaseColumn(Category category) {
        return category.toString();
    }

    @Override
    public Category convertToEntityAttribute(String s) {
        return Category.valueOf(s);
    }
}