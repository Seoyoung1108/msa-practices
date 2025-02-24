package mysite.controller.api;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import mysite.domain.Guestbook;

@SuppressWarnings("unchecked")
@Slf4j
@RestController
@RequestMapping("/api/guestbook")
public class GuestbookController {

	private final RestTemplate restTemplate;

	public GuestbookController(@LoadBalanced RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping
	public ResponseEntity<?> get(@RequestParam(value="id", required=true, defaultValue="0") Long startId) {
		log.info("Request[GET /api/guestbook], Parameters[id:{}]", startId);
		
		Map<String, Object> response = restTemplate.getForObject(MessageFormat.format("http://service-guestbook/?id={0}", startId), Map.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody Guestbook guestbook) {
		log.info("Request[POST /api/guestbook], RequestBody[Content-Type: application/json, {}]", guestbook);
		
		Map<String, Object> response = restTemplate.postForObject("http://service-guestbook/", guestbook, Map.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam(required=true, defaultValue="") String password) {
		log.info("Request[DELETE /api/guestbook], Parameters[id(PATH):{}], RequestBody[Content-Type: application/x-www-form-urlencoded, password:{}]", id, password);
		
		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		// Body
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("password", password);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		ResponseEntity<?> response = restTemplate.exchange(MessageFormat.format("http://service-guestbook/{0}", id), HttpMethod.DELETE, requestEntity, HashMap.class);
		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
	}
}