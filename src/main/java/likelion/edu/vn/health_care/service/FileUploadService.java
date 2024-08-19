package likelion.edu.vn.health_care.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    String uploadFile(MultipartFile multipartFile) throws Exception;
}
