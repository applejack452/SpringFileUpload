package com.spring.fileupload.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.spring.fileupload.dao.FileDao;
import com.spring.fileupload.dto.FileInfo;
import com.spring.fileupload.util.FileTransfer;

@Service("fileService")
public class FileService {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileTransfer")
	private FileTransfer fileTransfer;
	
	@Resource(name="fileDao")
	private FileDao fileDao;
	
	public void fileUpload(HttpServletRequest req) throws Exception {
		List<Map<String,Object>> listMap = fileTransfer.parseInsertFileInfo(req);
		
		for(int i=0, size=listMap.size(); i<size; i++){
        	fileDao.fileUpload(listMap.get(i));
        }
	}
	
	public List<FileInfo> getFileList() throws Exception {		
		return fileDao.getFileList();
	}
	
	public Map<String,Object> getFileInfo(String stored_file_name) throws Exception {
		return fileDao.getFileInfo(stored_file_name);
	}
	
	public void fileDelete(String stored_file_name) throws Exception {
		fileDao.fileDelete(stored_file_name);
	}
}
