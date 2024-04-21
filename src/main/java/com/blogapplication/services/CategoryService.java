package com.blogapplication.services;

import com.blogapplication.paylods.CategoryDto;

import java.util.List;

public interface CategoryService {

    //create
    public CategoryDto createCategory(CategoryDto categorydto);

    //update
    public CategoryDto updateCategory(CategoryDto categorydto, Integer categoryId);

    //delete
    public void deleteCategory(Integer categoryId);

    //get
    public CategoryDto getCategory(Integer categoryId);

    //get all
    public List<CategoryDto> getCategories();
}
