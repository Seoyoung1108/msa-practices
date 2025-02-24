package emaillist.controller.api;

import java.text.MessageFormat;
import java.util.Map;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import emaillist.domain.Email;
import emaillist.dto.JsonResult;
import lombok.extern.slf4j.Slf4j;


@SuppressWarnings("unchecked")
@Slf4j
@RestController
@RequestMapping("/email")
public class EmaillistController {
	
	private final RestTemplate restTemplate;
	
	public EmaillistController(@LoadBalanced RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@GetMapping
	public ResponseEntity<?> read(@RequestParam(value="kw", required=true, defaultValue="") String keyword) {
		log.info("Request[GET /email], Parameters[kw:{}]", keyword);
		
		Map<String, Object> response = restTemplate.getForObject("http://service-emaillist/?kw="+keyword, Map.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Email email) {
		log.info("Request[POST /email], RequestBody[Content-Type: application/json, {}]", email);
		
		Map<String, Object> response = restTemplate.postForObject("http://service-emaillist/", email, Map.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		log.info("Request[DELETE /email], Parameters[id(PATH):{}]", id);
		
		restTemplate.delete(MessageFormat.format("http://service-emaillist/{0}", id));
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(id));
	}	
}