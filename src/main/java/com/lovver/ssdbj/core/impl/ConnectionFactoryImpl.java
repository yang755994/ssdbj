package com.lovver.ssdbj.core.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.lovver.ssdbj.core.ConnectionFactory;
import com.lovver.ssdbj.core.ProtocolConnection;
import com.lovver.ssdbj.core.SSDBStream;
import com.lovver.ssdbj.exception.SSDBException;

public class ConnectionFactoryImpl extends ConnectionFactory{

	@Override
	public ProtocolConnection openConnectionImpl(String host, int port,
			String user,Properties info)
			throws SSDBException {
		
        //  - the TCP keep alive setting
        boolean requireTCPKeepAlive = (Boolean.valueOf(info.getProperty("tcpKeepAlive")).booleanValue());
        
		try {
			SSDBStream ssdbStream=new SSDBStream(host,port);
			 // Enable TCP keep-alive probe if required.
			ssdbStream.getSocket().setKeepAlive(requireTCPKeepAlive);

            // Do authentication (until AuthenticationOk).
            doAuthentication(ssdbStream, user, info.getProperty("password"));
			ProtocolConnection conn=new ProtocolConnectionImpl(ssdbStream,user,info);
			return conn;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void doAuthentication(SSDBStream ssdbStream, final String user,
			final String password) throws SSDBException {
//		List<byte[]> params=new ArrayList<byte[]>(){
//			{
//				add("kkk".getBytes());
//				add("jjjj".getBytes());
//			}
//		};
//		ssdbStream.sendCommand("set",params);
//		ssdbStream.receive();
//		
//		List<byte[]> gparams=new ArrayList<byte[]>(){
//			{
//				add("tt".getBytes());
//			}
//		};
//		ssdbStream.sendCommand("get",gparams);
//		ssdbStream.receive();
	}

}
