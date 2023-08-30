package com.example.ttcn2etest.fireBase;

import com.example.ttcn2etest.controller.BaseController;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FireBaseStorageController extends BaseController {
    private final FirebaseStorageService storageService;
    public FireBaseStorageController(FirebaseStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FirebaseUploadResponse> uploadFile(@Valid @RequestPart("file") MultipartFile file) {
        try {
            String downloadUrl = storageService.uploadFile(file);
            FirebaseUploadResponse response = new FirebaseUploadResponse(downloadUrl);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
