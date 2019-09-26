package com.hernandez.batch.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get("pdf").toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get("pdf")); 
	}

}
