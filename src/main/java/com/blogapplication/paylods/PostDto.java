package com.blogapplication.paylods;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;

    @NotEmpty
    @Size(min = 4, message = "Min Size of category title is 4" )
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Min Size of category title is 4" )
    private String content;

    //private String imageName="default.png";
    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

}
