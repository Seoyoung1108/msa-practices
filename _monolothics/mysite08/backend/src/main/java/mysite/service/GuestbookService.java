package mysite.service;

import java.util.List;

import mysite.repository.GuestbookRepository;
import mysite.domain.Guestbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GuestbookService {
	private final GuestbookRepository guestbookRepository;

	public GuestbookService(GuestbookRepository guestbookRepository) {
		this.guestbookRepository = guestbookRepository;
	}

	public List<Guestbook> getContentsList(Long id) {
		return guestbookRepository.findAll(id);
	}
	
	@Transactional
	public boolean deleteContents(Long id, String password) {
		Guestbook guestbook = new Guestbook();
		guestbook.setId(id);
		guestbook.setPassword(password);
		
		return guestbookRepository.deleteByIdAndPassword(guestbook);
	}

	public boolean addContents(Guestbook guestbook) {
		return guestbookRepository.insert(guestbook);
	}
}
