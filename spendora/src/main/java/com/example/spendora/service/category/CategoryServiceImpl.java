package com.example.spendora.service.category;

import com.example.spendora.dto.CategoryDto;
import com.example.spendora.model.Category;
import com.example.spendora.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepo categoryRepo;
    @Override
    public boolean existByUserAndCategory(String appUserId, Long categoryId){
        return categoryRepo.existsByAppUserIdAndCategoryId(appUserId, categoryId)
        || categoryRepo.existsById(categoryId);
    }
    @Override
    public List<Category> getAllWithoutUserId() {
        return categoryRepo.findAllByAppUserIsNull();
    }

    @Override
    public Category getByName(String category) {
        return categoryRepo.findByName(category)
                .orElseThrow(() -> new RuntimeException("Category not found with name " + category));
    }
    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }


}
