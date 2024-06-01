package com.example.back_end.infrastructure.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.back_end.infrastructure.constant.CloudinaryTypeFolder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
@Log4j2
public class CloudinaryUpload {

    private final Cloudinary cloudinaryConfig;

    @Autowired
    public CloudinaryUpload(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    public String uploadFile(MultipartFile gif, CloudinaryTypeFolder folderName) {
        try {
            File uploadedFile = convertMultiPartToFile(gif);

            Map uploadParams = ObjectUtils.asMap("folder", folderName.toString());

            Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, uploadParams);

            boolean isDeleted = uploadedFile.delete();

            if (isDeleted) {
                log.info("Temporary file {} successfully deleted", uploadedFile.getName());
            } else {
                log.warn("Temporary file {} doesn't exist or could not be deleted", uploadedFile.getName());
            }

            return uploadResult.get("url").toString();

        } catch (Exception e) {
            log.error("An error occurred during file upload", e);
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
