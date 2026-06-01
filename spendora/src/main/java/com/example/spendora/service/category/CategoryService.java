package com.example.spendora.service.category;

import com.example.spendora.dto.CategoryDto;
import com.example.spendora.model.Category;

import java.util.List;

public interface CategoryService {
    boolean existByUserAndCategory(String appUserId, Long s);
    List<Category> getAllWithoutUserId();

    Category getByName(String category);

    List<Category> getAllCategories();
}
