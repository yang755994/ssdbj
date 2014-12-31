package com.lovver.ssdbj.core.impl;

import java.util.List;

import com.lovver.ssdbj.core.BaseResultSet;

public class SSDBResultSet implements BaseResultSet {
	private String status;
	private List<byte[]> result;
	
	@Override
	public List<byte[]> getResult() {
		return result;
	}
	
	@Override
	public String getStatus() {
		return status;
	}
	
}
