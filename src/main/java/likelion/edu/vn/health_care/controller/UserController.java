package likelion.edu.vn.health_care.controller;

import likelion.edu.vn.health_care.model.request.UserRequest;
import likelion.edu.vn.health_care.model.response.UserResponse;
import likelion.edu.vn.health_care.security.UserInfoService;
import likelion.edu.vn.health_care.service.FileUploadService;
import likelion.edu.vn.health_care.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FileUploadService fileUpload;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRequest user) {

        try {

            String response = userInfoService.addUser(user);

            return ResponseHandler.generateResponse(HttpStatus.CREATED, false, "Register success", response);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);

        }
    }

    @GetMapping("/get-user")
    public ResponseEntity<Object> getUser() {

        UserResponse userResponse = userInfoService.getUserDetails();
        if (userResponse != null) {
            return ResponseEntity.ok(userResponse);
        }
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Error", null);

    }

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestParam String email, @RequestParam String password) {
        try {

            String token = userInfoService.authenticateUser(email, password);

            if (token != null) {
                return ResponseHandler.generateResponse(HttpStatus.OK, false, "Login success", token);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Fail", e.getMessage());

        }

        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Fail", null);

    }

    @PostMapping("/doctor/register")
    public ResponseEntity<Object> registerDoctor(@RequestBody UserRequest user) {
        try {
            String response = userInfoService.addDoctor(user);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, false, "Register success", response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody UserRequest user, @RequestParam("image") MultipartFile multipartFile,
                                         Model model) {
        try {
            UserResponse userResponse = userInfoService.updateUser(user);

            if (userResponse != null) {
                return ResponseEntity.ok(userResponse);
            }
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Fail", null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        System.err.println("Delete " + id);
        try {

            String response = userInfoService.deleteUser(id);
            if (response != null && !response.isEmpty()) {
                return ResponseEntity.ok(response);
            }
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Fail", null);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @PostMapping("/upload-image")
    public String uploadFile(@RequestParam("image") MultipartFile multipartFile,
                             Model model) throws Exception {
        String imageURL = fileUpload.uploadFile(multipartFile);
        model.addAttribute("imageURL", imageURL);
        return imageURL;
    }

}
