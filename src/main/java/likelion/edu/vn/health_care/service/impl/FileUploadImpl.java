package likelion.edu.vn.health_care.service.impl;

import com.cloudinary.Cloudinary;
import likelion.edu.vn.health_care.entity.UserEntity;
import likelion.edu.vn.health_care.model.mapper.UserMapper;
import likelion.edu.vn.health_care.security.UserInfoService;
import likelion.edu.vn.health_care.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadImpl implements FileUploadService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserMapper userMapper;


    @Override
    public String uploadFile(MultipartFile multipartFile) throws Exception {

        UserEntity currentUser = userInfoService.getUserEntity();

        if (currentUser != null) {
            String urlImage = cloudinary.uploader()
                    .upload(multipartFile.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url")
                    .toString();

            currentUser.setAvatarUrl(urlImage);

            userInfoService.saveUser(currentUser);
            return urlImage;

        }
        return "Error";

    }
}
