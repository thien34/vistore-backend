package com.example.back_end.infrastructure.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.back_end.infrastructure.constant.CloudinaryTypeFolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor
public class CloudinaryUpload {

    private final Cloudinary cloudinaryConfig;

    public String uploadFile(MultipartFile gif, CloudinaryTypeFolder folderName) {
        File uploadedFile = null;
        try {
            uploadedFile = convertMultiPartToFile(gif);

            Map<String, Object> uploadParams = ObjectUtils.asMap(
                    "folder", folderName.toString(),
                    "resource_type", "auto"
            );
            Map<String, Object> uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, uploadParams);

            return uploadResult.get("url").toString();

        } catch (IOException e) {
            log.error("Đã xảy ra lỗi trong quá trình chuyển đổi tệp", e);
            throw new RuntimeException("Không thể chuyển đổi tệp nhiều phần sang tệp", e);
        } catch (Exception e) {
            log.error("Đã xảy ra lỗi trong quá trình tải tệp lên", e);
            throw new RuntimeException(e);
        } finally {
            if (uploadedFile != null && uploadedFile.exists()) {
                boolean isDeleted = uploadedFile.delete();
                if (isDeleted) {
                    log.info("Tệp tạm thời {} đã xóa thành công", uploadedFile.getName());
                } else {
                    log.warn("Tệp tạm thời {} không tồn tại hoặc không thể xóa", uploadedFile.getName());
                }
            }
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("Tên tệp rỗng");
        }

        File convFile = new File(originalFilename);
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
