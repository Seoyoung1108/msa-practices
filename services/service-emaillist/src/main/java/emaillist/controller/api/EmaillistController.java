package emaillist.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import emaillist.domain.Email;
import emaillist.dto.JsonResult;
import emaillist.repository.EmaillistRepository;

@Slf4j
@RestController
@RequestMapping("/")
public class EmaillistController {
    private final EmaillistRepository emaillistRepository;

    public EmaillistController(EmaillistRepository emaillistRepository) {
        this.emaillistRepository = emaillistRepository;
    }

    @PostMapping
    public ResponseEntity<JsonResult<Email>> create(@RequestBody Email email) {
        log.info("Request[POST /], RequestBody[Content-Type: application/json, {}]", email);

        emaillistRepository.insert(email);
        return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(email));
    }

    @GetMapping
    public ResponseEntity<JsonResult<List<Email>>> read(@RequestParam(value = "kw", required = true, defaultValue = "") String keyword) {
        log.info("Request[GET /], Parameters[kw:{}]", keyword);

        return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(emaillistRepository.findAll(keyword)));
    }
   
    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResult<Long>> delete(@PathVariable Long id) {
        log.info("Request[DELETE /], Parameters[id(PATH):{}]", id);

        emaillistRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(id));
    }
}