package com.blogapplication.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.blogapplication.paylods.ApiResponse;
import com.blogapplication.paylods.PostDto;
import com.blogapplication.paylods.PostResponse;
import com.blogapplication.services.ImageService;
import com.blogapplication.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

   /* @Autowired
    private ImageService imageService;
    
    @Value(("${project.image}"))
    private  String path;
*/
    //create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){

        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);

    }


    //get post by user id

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){

        List<PostDto> postsByUser = this.postService.getPostByUser(userId);

        return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);

    }

	/*
	//get post by category id

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){

		List<PostDto> postsByCategory = this.postService.getPostByCategory(categoryId);

		return new ResponseEntity<List<PostDto>>(postsByCategory,HttpStatus.OK);

	}
	*/

    //get post by category id  with pagination

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory
            (@PathVariable Integer categoryId,
             @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
             @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize
    )
    {

        PostResponse postByCategory = this.postService.getPostByCategory(categoryId,pageNumber,pageSize);

        return new ResponseEntity<PostResponse>(postByCategory,HttpStatus.OK);

    }


    //get all posts

	/*
	@GetMapping("/all/posts")
	public ResponseEntity<List<PostDto>> getAllPosts(){
		List<PostDto> allPost = this.postService.getAllPost();
		return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);

	}
	*/

    //get all post with pagination and sorting using field
    @GetMapping("/all/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy
    ){
        PostResponse allPost = this.postService.getAllPost(pageNumber,pageSize,sortBy);
        return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);

    }

    //get one post by post ID
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getOnePost(@PathVariable Integer postId){
        PostDto postById = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postById,HttpStatus.OK);

    }

    //delete a post
    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePost( @PathVariable Integer postId){

        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully !!",true),HttpStatus.OK);
    }

    //update a post
    @PutMapping("/post/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){

        PostDto updatePost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }


    //search a post using any keyword
    @GetMapping("/post/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
        List<PostDto> result = this.postService.searchPosts(keywords);

        return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK) ;

    }

    /*//post image upload method
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId
            ) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);

        String fileName = this.imageService.uploadImage(path, image);

        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);

    }*/



}
