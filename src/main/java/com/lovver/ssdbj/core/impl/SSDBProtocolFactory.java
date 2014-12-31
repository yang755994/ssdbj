package com.lovver.ssdbj.core.impl;

import java.io.InputStream;
import java.io.OutputStream;

import com.lovver.ssdbj.core.SSDBProtocol;

public class SSDBProtocolFactory {
	
	
	public static SSDBProtocol createSSDBProtocolImpl(String protocol,OutputStream os,InputStream is){
		if(null==protocol){
			return new SSDBProtocolImpl(os,is);
		}
		return null;
	}
}
