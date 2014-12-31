package com.lovver.ssdbj.core.impl;

import com.lovver.ssdbj.core.BaseResultSet;

public class SSDBResultSet implements BaseResultSet {
	
	private String status;
	private byte[] result;
	
	
	public SSDBResultSet(String status, byte[] result) {
		super();
		this.status = status;
		this.result = result;
	}

	@Override
	public byte[] getResult() {
		return result;
	}
	
	@Override
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setResult(byte[] result) {
		this.result = result;
	}
}
