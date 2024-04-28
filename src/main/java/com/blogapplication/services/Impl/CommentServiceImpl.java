package com.blogapplication.services.Impl;

import com.blogapplication.entity.Comment;
import com.blogapplication.entity.Post;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.paylods.CommentDto;
import com.blogapplication.repository.CommentRepo;
import com.blogapplication.repository.PostRepo;
import com.blogapplication.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post_id",postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setCpost(post);

        Comment savedcomment= this.commentRepo.save(comment);

        return this.modelMapper.map(savedcomment,CommentDto.class);


    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment com=this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentID",commentId));

        this.commentRepo.delete(com);
    }
}
