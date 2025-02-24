package mysite.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import mysite.dto.JsonResult;
import mysite.service.FileUploadService;
import mysite.service.GalleryService;
import mysite.domain.Gallery;

@Slf4j
@RestController
@RequestMapping("/api/gallery")
public class GalleryController {
	
	private final GalleryService galleryService;
	private final FileUploadService FileUploadService;

	public GalleryController(FileUploadService FileUploadService, GalleryService galleryService) {
		this.FileUploadService = FileUploadService;
		this.galleryService = galleryService;
	}

	@GetMapping
	public ResponseEntity<JsonResult<List<Gallery>>> get() {
		log.info("Request[GET /api/gallery]");

		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(galleryService.getImages()));
	}
	
	@PostMapping
	public ResponseEntity<JsonResult<Gallery>> create(Gallery gallery, MultipartFile file) {
		log.info("Request[POST /api/gallery], RequestBody[Content-Type: multipart/form-data, {}, {}]", gallery, file.getOriginalFilename());

		gallery.setImage(FileUploadService.saveImage(file, "gallery"));
		galleryService.addImage(gallery);

		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(gallery));
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<JsonResult<Long>> delete(@PathVariable Long id) {
		log.info("Request[DELETE /api/gallery], Parameters[id(PATH):{}]", id);

		galleryService.removeImage(id);
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(id));
	}
}
