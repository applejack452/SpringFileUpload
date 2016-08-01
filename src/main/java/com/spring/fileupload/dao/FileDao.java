package com.spring.fileupload.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.fileupload.dto.FileInfo;

@Repository("fileDao")
public class FileDao {
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public void fileUpload(Map<String, Object> map) throws Exception{
		sqlSession.insert("file.fileUpload", map);
	}
	
	public List<FileInfo> getFileList() throws Exception{
		return sqlSession.selectList("file.getFileList");
	}
	
	public Map<String,Object> getFileInfo(String stored_file_name){
		return sqlSession.selectOne("file.getFileInfo", stored_file_name);
	}
	
	public void fileDelete(String stored_file_name){
		sqlSession.delete("file.fileDelete", stored_file_name);
	}
}
