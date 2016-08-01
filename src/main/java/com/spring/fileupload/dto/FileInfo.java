package com.spring.fileupload.dto;

import java.util.Date;

public class FileInfo {
	
	private String original_file_name;
	private String stored_file_name;
	private String file_type;
	private int file_size;
	private Date reg_date;
	
	public String getOriginal_file_name() {
		return original_file_name;
	}
	public void setOriginal_file_name(String original_file_name) {
		this.original_file_name = original_file_name;
	}
	public String getStored_file_name() {
		return stored_file_name;
	}
	public void setStored_file_name(String stored_file_name) {
		this.stored_file_name = stored_file_name;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public int getFile_size() {
		return file_size;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	
	@Override
	public String toString() {
		return "FileInfo [original_file_name=" + original_file_name
				+ ", stored_file_name=" + stored_file_name + ", file_type="
				+ file_type + ", file_size=" + file_size + ", reg_date="
				+ reg_date + "]";
	}
}
