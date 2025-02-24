package mysite.service;

import java.util.List;
import org.springframework.stereotype.Service;

import mysite.repository.GalleryRepository;
import mysite.domain.Gallery;

@Service
public class GalleryService {
	private final GalleryRepository galleryRepository;

	public GalleryService(GalleryRepository galleryRepository) {
		this.galleryRepository = galleryRepository;
	}

	public List<Gallery> getImages() {
		return galleryRepository.findAll();
	}
	
	public boolean removeImage(Long id) {
		return galleryRepository.delete(id) == 1;
	}
	
	public boolean addImage(Gallery gallery) {
		return galleryRepository.insert(gallery) == 1;
	}
}
