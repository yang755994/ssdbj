package com.lovver.ssdbj.core.impl;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.core.CommandExecutor;
import com.lovver.ssdbj.core.Protocol;
import com.lovver.ssdbj.core.ProtocolConnection;
import com.lovver.ssdbj.core.SSDBStream;
import com.lovver.ssdbj.exception.SSDBException;

/**
 * 协议级别连接，根据不同的协议产生不同的连接
 * 
 * @author jobell.jiang
 */
public class ProtocolConnectionImpl implements ProtocolConnection {

	private SSDBStream stream;
	private Properties props;
	private String user;
	private CommandExecutor executor;
    private Protocol protocol;

	private boolean closed = false;

	public ProtocolConnectionImpl(SSDBStream stream, String user,
			Properties infos) {
		this.stream = stream;
		this.user = user;
		this.props = infos;
		this.executor=null;
	}

	@Override
	public void close() {
		if (closed)
			return;

		try {
			stream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		closed = true;
	}

	@Override
	public boolean isClose() {
		return closed;
	}

	@Override
	public boolean isConnection() {
		return false;
	}

	@Override
	public CommandExecutor getCommandExecutor() {
		return executor;
	}

	@Override
	public BaseResultSet execute(String cmd,List<byte[]> params) throws SSDBException{
		sendCommand(cmd, params);
		List<byte[]>result=receive();
		System.out.println(new String(result.get(1)));
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SSDBException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SSDBException {
		return false;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProtocolVersion() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
    public List<byte[]> receive() throws SSDBException{ 
    	return protocol.receive();
    }
    
    public void sendCommand(String  cmd,List<byte[]> params) throws SSDBException{ 
    	this.protocol.sendCommand(cmd, params);
    }

}
