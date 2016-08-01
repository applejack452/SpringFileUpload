package com.spring.fileupload.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component
public class FileTransfer {
	
	private static final String filePath = "C:\\Study\\fileUpload\\";
	
	public List<Map<String, Object>> parseInsertFileInfo(HttpServletRequest req) throws Exception{
		MultipartHttpServletRequest multipartHttpServletReq = (MultipartHttpServletRequest)req;
		Iterator<String> iterator = multipartHttpServletReq.getFileNames();
		
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		String fileType = null;
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> listMap = null;
		
		File file = new File(filePath);
		if(file.exists() == false){
			file.mkdirs();
		}
		
		while(iterator.hasNext()){
			multipartFile = multipartHttpServletReq.getFile(iterator.next());
			if(multipartFile.isEmpty() == false){
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = getRandomString() + originalFileExtension;
				//fileType = originalFileExtension.substring(1);
				fileType = multipartFile.getContentType();
				
				file = new File(filePath + storedFileName);
				multipartFile.transferTo(file);
				
				listMap = new HashMap<String,Object>();
				listMap.put("original_file_name", originalFileName);
				listMap.put("stored_file_name", storedFileName);
				listMap.put("file_size", multipartFile.getSize());
				listMap.put("file_type", fileType);
				list.add(listMap);
			}
		}
		System.out.println(list.toString());
		return list;
	}
	
	public static String getFilePath(){
		return filePath;
	}
	
	public String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
