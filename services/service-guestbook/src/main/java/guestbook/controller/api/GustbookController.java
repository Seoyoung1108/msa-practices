package guestbook.controller.api;

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
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import guestbook.domain.Guestbook;
import guestbook.dto.JsonResult;
import guestbook.service.GuestbookService;


@Slf4j
@RestController
@RequestMapping("/")
public class GustbookController {
	private final GuestbookService guestbookService;

	public GustbookController(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}

	@GetMapping
	public ResponseEntity<JsonResult<List<Guestbook>>> read(@RequestParam(value="id", required=true, defaultValue="0") Long startId) {
		log.info("Request[GET /], Parameters[id:{}]", startId);

		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(guestbookService.getContentsList(startId)));
	}

	@PostMapping
	public ResponseEntity<JsonResult<Guestbook>> create(@RequestBody Guestbook guestbook) {
		log.info("Request[POST /], RequestBody[Content-Type: application/json, {}]", guestbook);

		guestbookService.addContents(guestbook);
		guestbook.setPassword("");
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(guestbook));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<JsonResult<Long>> delete(@PathVariable Long id, @RequestParam(required=true, defaultValue="") String password) {
		log.info("Request[DELETE /], Parameters[id(PATH):{}], RequestBody[Content-Type: application/x-www-form-urlencoded, password:{}]", id, password);

		boolean result = guestbookService.deleteContents(id, password);
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(result ? id : null));
	}
	
}