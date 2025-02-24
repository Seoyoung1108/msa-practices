package mysite.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	@Value("${mysite.upload.location}")
	private String uploadLocation;

	@Value("${mysite.static.pathBase}")
	private String staticPathBase;

	public String saveImage(MultipartFile file, String serviceName) throws RuntimeException {
		try {
			if(file.isEmpty()) {
				throw new RuntimeException("file upload error: image empty");
			}

			Path pathSave = Files.createDirectories(Paths.get(uploadLocation + "/" + serviceName));

			final String saveFilename = UUID
					.randomUUID()
					.toString()
					.concat(".")
					.concat(Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.') + 1));

			Files.write(pathSave.resolve(saveFilename), file.getBytes());

			return staticPathBase + "/" + serviceName + "/" + saveFilename;
		} catch(IOException ex) {
			throw new RuntimeException("file upload error:" + ex);
		}
	}
}
