package com.blogapplication.paylods;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    //using dto beacause , don't want to do directly any operation on entities

    private int id;

    @NotEmpty
    @Size(min = 4 , message = "Username should be more than 4 charecter")
    private String name;

    @Email(message = "Email address is not valid !")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "password must more than 3 charecter and less than 10 character")
    private String password;

    @NotEmpty
    private String about;

}