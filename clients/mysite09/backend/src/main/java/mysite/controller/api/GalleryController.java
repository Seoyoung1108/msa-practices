package mysite.controller.api;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import mysite.domain.Gallery;
import mysite.dto.JsonResult;

@Slf4j
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

	private final RestTemplate restTemplate;

	public GalleryController(@LoadBalanced RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping
	public ResponseEntity<?> readAll() {
		log.info("Request[GET /api/gallery]");
		
		Map<String, Object> response = restTemplate.getForObject("http://service-gallery/", Map.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> create(MultipartFile file, Gallery gallery) {
		log.info("Request[POST /api/gallery], RequestBody[Content-Type: application/json, file:{}, gallery:{}]", file.getOriginalFilename(), gallery);
		
		Map<String, Object> response = null;

		try {
			// parts
			HttpHeaders parts = new HttpHeaders();
			parts.setContentType(MediaType.TEXT_PLAIN);
			final ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes()) {
				@Override
				public String getFilename() {
					return file.getOriginalFilename();
				}
			};

			// Multipart Body
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("file", new HttpEntity<>(byteArrayResource, parts));

			// Multipart Header
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			// Multipart Request
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
			Map<String, String> responseUpload = restTemplate.postForObject("http://service-storage/", requestEntity, HashMap.class);

			gallery.setImage(responseUpload.get("data"));
			response = restTemplate.postForObject("http://service-gallery", gallery, Map.class);
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		log.info("Request[DELETE /api/gallery], Parameters[id(PATH):{}]", id);
		
		restTemplate.delete(MessageFormat.format("http://service-gallery/{0}", id));
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(id));
	}
}
