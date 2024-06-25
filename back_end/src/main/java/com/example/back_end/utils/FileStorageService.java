package com.example.back_end.utils;

import com.example.back_end.configuration.AppConfig;
import com.example.back_end.entity.Picture;
import com.example.back_end.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    public FileStorageService(AppConfig appConfig) {
        this.fileStorageLocation = Paths.get(appConfig.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    public Picture storeFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IOException("Invalid file name");
        }
        // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        Picture picture= new Picture().builder()
                .mimeType(file.getContentType())
                .linkImg(targetLocation.toString())
                .seoFileName(file.getOriginalFilename())
                .altAttribute("Description of "+file.getName())
                .titleAttribute(file.getOriginalFilename())
                .virtualPath(targetLocation.toString())
                .isNew(true)
                .build();
        pictureRepository.save(picture);
        return picture;
    }
}
