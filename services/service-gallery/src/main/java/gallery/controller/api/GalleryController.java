package gallery.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gallery.domain.Gallery;
import gallery.dto.JsonResult;
import gallery.service.GalleryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class GalleryController {
	private final GalleryService galleryService;

	public GalleryController(GalleryService galleryService) {
		this.galleryService = galleryService;
	}
	
	@GetMapping
	public ResponseEntity<JsonResult<List<Gallery>>> get() {
		log.info("Request[GET /]");

		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(galleryService.getImages()));
	}
	
	@PostMapping
	public ResponseEntity<JsonResult<Gallery>> create(@RequestBody Gallery gallery) {
		log.info("Request[POST /], RequestBody[Content-Type: application/json, {}]", gallery);

		galleryService.addImage(gallery);
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(gallery));
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<JsonResult<Long>> delete(@PathVariable Long id) {
		log.info("Request[DELETE /], Parameters[id(PATH):{}]", id);

		galleryService.removeImage(id);
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(id));
	}
	
}
