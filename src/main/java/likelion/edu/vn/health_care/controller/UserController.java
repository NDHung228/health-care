package likelion.edu.vn.health_care.controller;

import likelion.edu.vn.health_care.model.request.UserRequest;
import likelion.edu.vn.health_care.model.request.UserUpdateRequest;
import likelion.edu.vn.health_care.model.response.UserResponse;
import likelion.edu.vn.health_care.security.UserInfoService;
import likelion.edu.vn.health_care.service.FileUploadService;
import likelion.edu.vn.health_care.service.UserService;
import likelion.edu.vn.health_care.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FileUploadService fileUpload;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRequest user) {

        try {

            String response = userInfoService.addUser(user);

            return ResponseHandler.generateResponse(HttpStatus.CREATED, false, "Register success", response);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), "Register failed");

        }
    }

    @GetMapping("/get-user")
    public ResponseEntity<Object> getUser() {

        UserResponse userResponse = userInfoService.getUserDetails();
        if (userResponse != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", userResponse);
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
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Login Fail", e.getMessage());

        }

        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Login Fail", null);

    }

    @PostMapping("/doctor/register")
    public ResponseEntity<Object> registerDoctor(@RequestBody UserRequest user) {
        System.err.println("Demo");
        try {
            String response = userInfoService.addDoctor(user);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, false, "Register success", response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody UserUpdateRequest user) {
        try {
            UserResponse userResponse = userInfoService.updateUser(user);

            if (userResponse != null) {
                return ResponseHandler.generateResponse(HttpStatus.OK, false, "Update Success", userResponse);
            }
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Update Fail", null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        try {

            String response = userInfoService.deleteUser(id);
            if (response != null && !response.isEmpty()) {
                return ResponseHandler.generateResponse(HttpStatus.OK, false, "Delete Success", response);

            }
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Delete Fail", null);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @PostMapping("/upload-image")
    public ResponseEntity<Object> uploadFile(@RequestParam("image") MultipartFile multipartFile,
                                             Model model) throws Exception {
        String imageURL = fileUpload.uploadFile(multipartFile);
        model.addAttribute("imageURL", imageURL);
        return ResponseHandler.generateResponse(HttpStatus.OK, false, "Update image success", imageURL);
    }

    @GetMapping("/get-all-doctor")
    public ResponseEntity<Object> getAllDoctor() {
        List<UserResponse> listDoctor = userService.getAllDoctor();
        if (listDoctor != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Get all doctor success", listDoctor);

        }
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Get all doctor fail", null);
    }

    @GetMapping("/get-all-patient")
    public ResponseEntity<Object> getAllPatient() {
        List<UserResponse> listDoctor = userService.getAllPatient();
        if (listDoctor != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Get all patient success", listDoctor);

        }
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Get all patient fail", null);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam String name) {
        List<UserResponse> listDoctor = userService.searchName(name);
        if (listDoctor != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Search success", listDoctor);
        }
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "The user you are looking for was not found.", null);
    }

    @GetMapping("/find-user")
    public ResponseEntity<Object> getUserById(@RequestParam int id) {
        System.err.println("This here");
        UserResponse userResponse = userService.getUserById(id);

        if (userResponse != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Get success", userResponse);
        }
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Get fail", null);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        try {
            int current = currentOptional.filter(s -> !s.isEmpty()).map(Integer::parseInt).orElse(1);
            int pageSize = pageSizeOptional.filter(s -> !s.isEmpty()).map(Integer::parseInt).orElse(10);

            Pageable pageable = PageRequest.of(current - 1, pageSize);
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Users retrieved successfully", this.userService.handleGetAll(pageable));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, e.getMessage(), null);
        }
    }
}
