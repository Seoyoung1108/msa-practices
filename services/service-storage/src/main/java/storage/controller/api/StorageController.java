package storage.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import storage.dto.JsonResult;
import storage.service.FileUploadService;

@Slf4j
@RestController
@RequestMapping("/")
public class StorageController {

    private final FileUploadService fileUploadService;

    public StorageController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping
    public ResponseEntity<JsonResult<String>> upload(MultipartFile file) {
		log.info("Request[POST /], RequestBody[Content-Type: multipart/form-data, {}]", file.getOriginalFilename());

        return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(fileUploadService.saveImage(file)));
    }
}
