package com.blogapplication.controllers;

import com.blogapplication.entity.Comment;
import com.blogapplication.paylods.ApiResponse;
import com.blogapplication.paylods.CommentDto;
import com.blogapplication.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //create a comment
    @PostMapping("/post/{postId}/createComment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,
                                                    @PathVariable Integer postId){
        CommentDto createdcomment = this.commentService.createComment(comment, postId);

        return new ResponseEntity<CommentDto>(createdcomment, HttpStatus.CREATED);

    }

    //Delete the comment

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId ){

        this.commentService.deleteComment(commentId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully !!",true),HttpStatus.OK);

    }

}
