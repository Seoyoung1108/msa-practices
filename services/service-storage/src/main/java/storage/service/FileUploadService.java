package storage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileUploadService {

	@Value("${storage.location}")
	private String storageLocation;

	@Value("${storage.httpd.host}")
	private String host;

	@Value("${storage.httpd.port}")
	private int port;

	public String saveImage(MultipartFile file) throws RuntimeException {
		try {			
			if(file.isEmpty()) {
				throw new RuntimeException("file upload error: image empty");
			}
			
			if(file.isEmpty()) {
				throw new RuntimeException("file upload error: image empty");
			}

			Path pathSave = Files.createDirectories(Paths.get(storageLocation));

			final String saveFilename = UUID
					.randomUUID()
					.toString()
					.concat(".")
					.concat(Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.') + 1));

			Files.write(pathSave.resolve(saveFilename), file.getBytes());

			String[] locations = storageLocation.split("/");
			return MessageFormat.format("http://{0}:{1,number,#}/{2}/{3}", host, port, locations[locations.length-1], saveFilename);			
		} catch(IOException ex) {
			throw new RuntimeException("file upload error:" + ex);
		}
	}	
}
