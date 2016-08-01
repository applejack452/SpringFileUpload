package com.spring.fileupload.controller;



import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.fileupload.service.FileService;
import com.spring.fileupload.util.FileTransfer;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FileController {
	
	@Resource(name="fileService")
	private FileService fileService;
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		logger.info("MainPage");
		
		return "fileupload";
	}
	
	//파일 업로드
	@RequestMapping(value = "fileupload", method = RequestMethod.POST)
	public void fileUpload(HttpServletRequest req) throws Exception{
		logger.info("fileupload");
		fileService.fileUpload(req);
	}
	
	@RequestMapping(value = "fileListView")
	public ModelAndView fileListView() throws Exception{
		logger.info("fileListView");
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileList",fileService.getFileList());
		mv.setViewName("fileList");
		
		return mv;
	}
	
	//파일 다운로드
	@RequestMapping(value="fileDownload", method=RequestMethod.POST)
	public void fileDownload(HttpServletRequest req, HttpServletResponse res) throws Exception{
		logger.info("fileDownload");
		String stored_file_name = req.getParameter("fileName");
		Map<String,Object> resultMap = fileService.getFileInfo(stored_file_name);
		
		String originalFileName = (String)resultMap.get("original_file_name");
		String storedFileName = (String)resultMap.get("stored_file_name");
		//String contentType = (String)resultMap.get("file_type");
		
		String filePath = FileTransfer.getFilePath(); 
		byte fileByte[] = FileUtils.readFileToByteArray(new File(filePath + storedFileName));
		
		res.setContentType("application/octet-stream"); //다운로드 위한 타입 (브라우저에서 바로 처리 안하도록)
		res.setContentLength(fileByte.length);
		res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8") + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");		
		res.getOutputStream().write(fileByte);
		res.getOutputStream().flush();
		res.getOutputStream().close();
	}
	
	//파일 다중 다운로드
	@RequestMapping(value="multiFileDownload", method=RequestMethod.POST)
	public void multiFileDownload(HttpServletRequest req, HttpServletResponse res) throws Exception{
		logger.info("multiDownload");
		
		int count = Integer.parseInt(req.getParameter("fileCount"));
		res.setContentType("multipart/x-mixed-replace; boundary=END"); // 크롬에서 해당 기능 제거됨.... 익스도 지원 안됨.... 파폭만 가능....
		
		List<File> files = new ArrayList<File>();
		String filePath = FileTransfer.getFilePath(); 
		
		for(int i=0; i<count; i++){
			System.out.println(req.getParameter(i+""));
			String stored_file_name = req.getParameter("fileName"+i);
			Map<String,Object> resultMap = fileService.getFileInfo(stored_file_name);
			
			String originalFileName = (String)resultMap.get("original_file_name");
			String storedFileName = (String)resultMap.get("stored_file_name");
			
		    files.add(new File(filePath + storedFileName));		    
		}
		
		System.out.println(files.toString());		
				
		ServletOutputStream out = res.getOutputStream();
		
		// Print the boundary string
		out.println();
		out.println("--END"); //첫번째 구분자

		for (File file : files) {
			
		  byte fileByte[] = FileUtils.readFileToByteArray(file);
		  // Print the content type
		  out.println("application/octet-stream");
		  out.println("Content-Disposition: attachment; filename=\"" + file.getName() + "\";");
		  out.println();
		
		  System.out.println("Sending " + file.getName());
		
		  out.write(fileByte);
		
		  // Print the boundary string
		  out.println();
		  out.println("--END"); //두번째 구분자부터.....
		  out.flush();
		  System.out.println("Finisheding file " + file.getName());
		}
		
		// Print the ending boundary string
		out.println("--END--"); //문서의 끝....
		out.flush();
		out.close();
	}
	
	//파일 삭제
	@RequestMapping(value="fileDelete", method=RequestMethod.POST)
	public String fileDelete(HttpServletRequest req) throws Exception{
		logger.info("fileDelete");
		
		String stored_file_name = req.getParameter("fileName");
		fileService.fileDelete(stored_file_name);
		
		//디스크에서 삭제
		String filePath = FileTransfer.getFilePath();
		FileUtils.deleteQuietly(new File(filePath + stored_file_name));
		
		return "redirect:/fileListView";		
	}
	
	//파일 다중 삭제
	@RequestMapping(value="multiFileDelete", method=RequestMethod.POST)
	public String multiFileDelete(HttpServletRequest req) throws Exception{
		logger.info("fileDelete");
		
		int count = Integer.parseInt(req.getParameter("fileCount"));		
		
		String filePath = FileTransfer.getFilePath(); 
		
		for(int i=0; i<count; i++){			
			String stored_file_name = req.getParameter("fileName"+i);
			fileService.fileDelete(stored_file_name);			
			
			FileUtils.deleteQuietly(new File(filePath + stored_file_name));	    
		}		
		
		return "redirect:/fileListView";		
	}
}