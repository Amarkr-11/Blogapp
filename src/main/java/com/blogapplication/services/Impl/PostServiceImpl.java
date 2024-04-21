package com.blogapplication.services.Impl;

import com.blogapplication.entity.Category;
import com.blogapplication.entity.Post;
import com.blogapplication.entity.User;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.paylods.PostDto;
import com.blogapplication.paylods.PostResponse;
import com.blogapplication.repository.CategoryRepo;
import com.blogapplication.repository.PostRepo;
import com.blogapplication.repository.UserRepo;
import com.blogapplication.services.PostService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    //creating a post
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {


        //here i am taking user and category from URL or setting some values which should not taken by end user

        User user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user Id", userId));

        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", categoryId));

        //setting values in DTO
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newpost = this.postRepo.save(post);

        return this.modelMapper.map(newpost, PostDto.class );
    }


    //updating a post
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post id", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedpost = this.postRepo.save(post);

        return this.modelMapper.map(updatedpost, PostDto.class);
    }


    //deleting a post
    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post id", postId));
        this.postRepo.delete(post);

    }


    //get all post
    //@Override
    //public List<PostDto> getAllPost() {


    //List<Post> posts = this.postRepo.findAll();
    //List<PostDto> postdtos = posts.stream().map(post ->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    //return postdtos;
    //}



    //get all post by pagination we can see the all post depending on page which we wants and  by sorting also
    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {

        Pageable p =PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));  //here we can give asc and des also using method changing

        Page<Post> pagePost = this.postRepo.findAll(p);

        List<Post> posts = pagePost.getContent();

        List<PostDto> postdtos = posts.stream().map(post ->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postresponse=new PostResponse();

        postresponse.setContent(postdtos);
        postresponse.setPageNumber(pagePost.getNumber());
        postresponse.setPageSize(pagePost.getSize());
        postresponse.setTotalElements(pagePost.getTotalElements());
        postresponse.setTotalPages(pagePost.getTotalPages());
        postresponse.setLastPage(pagePost.isLast());

        return postresponse;
    }

    //get post by post id
    @Override
    public PostDto getPostById(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
        return this.modelMapper.map(post, PostDto.class);
    }


/*
	//get post by category id
	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {

		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);

	//	List<PostDto> postdtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		//return postdtos;
	}
	*/

    //get post by category id with pagination

    @Override
    public PostResponse getPostByCategory(Integer categoryId,Integer pageNumber, Integer pageSize) {

        Pageable p =PageRequest.of(pageNumber, pageSize);

        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));


        Page<Post> pageposts = this.postRepo.findByCategory(category, p);

        List<Post> posts = pageposts.getContent();

        List<PostDto> postdtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postresponse=new PostResponse();

        postresponse.setContent(postdtos);
        postresponse.setPageNumber(pageposts.getNumber());
        postresponse.setPageSize(pageposts.getSize());
        postresponse.setTotalElements(pageposts.getTotalElements());
        postresponse.setTotalPages(pageposts.getTotalPages());
        postresponse.setLastPage(pageposts.isLast());

        return postresponse;
    }



    //get post by user id
    @Override
    public List<PostDto> getPostByUser(Integer userId) {

        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id ", userId));
        List<Post> posts =this.postRepo.findByUser(user);

        List<PostDto> postdtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postdtos;

    }


    //get post by a search
    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDto;
    }

}
