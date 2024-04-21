package com.blogapplication.services.Impl;

import com.blogapplication.entity.Category;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.paylods.CategoryDto;
import com.blogapplication.repository.CategoryRepo;
import com.blogapplication.services.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepo categoryrepo;

    @Autowired
    private ModelMapper modelmapper;
    //in this we are using converting directly entity to DTO or DTO to entity
    //in user service we tried in detail

    //create category
    @Override
    public CategoryDto createCategory(CategoryDto categorydto) {

        Category cat = this.modelmapper.map(categorydto, Category.class);
        Category addedcat = this.categoryrepo.save(cat);

        return this.modelmapper.map(addedcat, CategoryDto.class);

    }


    //update category
    @Override
    public CategoryDto updateCategory(CategoryDto categorydto, Integer categoryId) {

        Category cat = this.categoryrepo.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));

        cat.setCategoryTitle(categorydto.getCategoryTitle());
        cat.setCategoryDescription(categorydto.getCategoryDescription());

        Category updatedcat = this.categoryrepo.save(cat);

        return this.modelmapper.map(updatedcat, CategoryDto.class);
    }

    //Delete category
    @Override
    public void deleteCategory(Integer categoryId) {

        Category cat=this.categoryrepo.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category", "Category ID", categoryId));

        this.categoryrepo.delete(cat);

    }


    //get one category
    @Override
    public CategoryDto getCategory(Integer categoryId) {

        Category cat = this.categoryrepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Category ID", categoryId));

        return this.modelmapper.map(cat, CategoryDto.class);
    }

    //get all category
    @Override
    public List<CategoryDto> getCategories() {


        //List<Category> findallcat = categoryrepo.findAll();
        //return (List<CategoryDto>) this.modelmapper.map(findallcat, CategoryDto.class);


        List<Category> categories = this.categoryrepo.findAll();

        List<CategoryDto> catdtos = categories.stream().map(cat-> this.modelmapper.map(cat, CategoryDto.class)).collect(Collectors.toList());

        return catdtos;
    }

}
