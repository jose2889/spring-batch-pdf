package com.hernandez.batch.services;

import java.io.IOException;

public interface IUploadFileService {
	
	public void deleteAll();
	
	public void init() throws IOException;
}
