package com.blogapplication.controllers;

import com.blogapplication.paylods.CategoryDto;
import com.blogapplication.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    //create
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categorydto){

        CategoryDto createCategory = this.categoryService.createCategory(categorydto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

    //update
    @PutMapping("update/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categorydto, @PathVariable Integer catId){

        CategoryDto updatedCategory = this.categoryService.updateCategory(categorydto, catId);
        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/delete/{catId}")
    public ResponseEntity<?> deleteCategory( @PathVariable Integer catId){

        this.categoryService.deleteCategory(catId);
        return new ResponseEntity(Map.of("message", "Category Deleted Successfully"),HttpStatus.OK);
    }

    //get one
    @GetMapping("/get/{catId}")
    public ResponseEntity<CategoryDto> getCategory( @PathVariable Integer catId){

        CategoryDto categoryDto = this.categoryService.getCategory(catId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    //get all
    @GetMapping("/get/all")
    public ResponseEntity<List<CategoryDto>> getCategories(){

        /*
         * List<CategoryDto> categories = this.categoryService.getCategories(); return
         * ResponseEntity.ok(categories);
         */
        return ResponseEntity.ok(this.categoryService.getCategories());
    }
}
