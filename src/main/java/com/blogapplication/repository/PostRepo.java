package com.blogapplication.repository;

import com.blogapplication.entity.Category;
import com.blogapplication.entity.Post;
import com.blogapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    //two custom method

    List<Post> findByUser(User user);

    //List<Post> findByCategory(Category category);

    //method for pagination
    Page<Post> findByCategory(Category category , Pageable p);

    List<Post> findByTitleContaining(String title);
}
