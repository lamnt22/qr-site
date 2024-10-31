package com.arius.qrmenu.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

	void deleteAllFiles();

	byte[] readFileContent(String fillName);

	Stream<Path> loadAll();

	String storeFile(MultipartFile file);

}
