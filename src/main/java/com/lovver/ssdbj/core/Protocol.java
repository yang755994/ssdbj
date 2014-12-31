package com.lovver.ssdbj.core;

import java.util.List;

import com.lovver.ssdbj.exception.SSDBException;


public interface Protocol {
	public String getProtocol();
	public void sendCommand(String cmd,List<byte[]> params)throws SSDBException ;
	public List<byte[]> receive() throws SSDBException;
}
