package com.Ecommerce_BE.Service.ServiceImp;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final String uploadDir = "C:/uploadFile";

    public String saveFile(MultipartFile file) throws IOException {

        try
        {
            String fileName = file.getOriginalFilename();

            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath))
            {
                Files.createDirectories(uploadPath);
            }

            Path fileFath = uploadPath.resolve(fileName);
            file.transferTo(fileFath.toFile());

            return fileFath.toString();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

    }














}
