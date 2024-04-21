package com.blogapplication.services.Impl;

import com.blogapplication.services.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // get file name
        String name= file.getOriginalFilename();

        //random name genarating of file
        String randonID= UUID.randomUUID().toString();
        String filename1 = randonID.concat(name.substring(name.lastIndexOf(".")));

        //above you can strict only for image extenstions file currently it will upload every file.

        //create full path
        String filePath=path+ File.separator+filename1;


        //create folder if not created
        File f =new File(path);

        if(!f.exists()){
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return filename1;
    }

    @Override
    public InputStream getImage(String path, String fileName) throws FileNotFoundException {

        String fullpath = path+File.separator+fileName;
        InputStream is = new FileInputStream(fullpath) ;

        //DB Logic to return Input Stream

        return is;
    }
}
