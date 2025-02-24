package mysite.controller.api;

import lombok.extern.slf4j.Slf4j;
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

import mysite.dto.JsonResult;
import mysite.service.GuestbookService;
import mysite.domain.Guestbook;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/guestbook")
public class GuestbookController {
	private final GuestbookService guestbookService;

	public GuestbookController(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}

	@GetMapping
	public ResponseEntity<JsonResult<List<Guestbook>>> read(@RequestParam(value="id", required=true, defaultValue="0") Long startId) {
		log.info("Request[GET /api/guestbook], Parameters[id:{}]", startId);

		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(guestbookService.getContentsList(startId)));
	}

	@PostMapping
	public ResponseEntity<JsonResult<Guestbook>> create(@RequestBody Guestbook guestbook) {
		log.info("Request[POST /api/guestbook], RequestBody[Content-Type: application/json, {}]", guestbook);

		guestbookService.addContents(guestbook);
		guestbook.setPassword("");
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(guestbook));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<JsonResult<Long>> delete(@PathVariable Long id, @RequestParam(required=true, defaultValue="") String password) {
		log.info("Request[POST /api/guestbook], Parameters[id(PATH):{}], RequestBody[Content-Type: application/x-www-form-urlencoded, password:{}]", id, password);

		boolean result = guestbookService.deleteContents(id, password);
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(result ? id : null));
	}
}