package com.lovver.ssdbj.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import com.lovver.ssdbj.core.impl.SSDBProtocolFactory;
import com.lovver.ssdbj.exception.SSDBException;

public class SSDBStream {
    private final String host;
    private final int port;
    private Socket socket;
    private InputStream ssdb_input;
    private OutputStream ssdb_output;
    private SSDBProtocol protocol;
    
    
    
    public SSDBStream(String host, int port) throws IOException
    {
        this.host = host;
        this.port = port;
        changeSocket(new Socket(host, port));
    }
    
    public void changeSocket(Socket socket) throws IOException {
        this.socket = socket;
        socket.setTcpNoDelay(true);
        ssdb_input = new BufferedInputStream(socket.getInputStream(),8192);
        ssdb_output = new BufferedOutputStream(socket.getOutputStream(), 8192);
        protocol=SSDBProtocolFactory.createSSDBProtocolImpl(null,ssdb_output,ssdb_input);
    }


    public void flush() throws IOException {
        ssdb_output.flush();
    }
    
    public void close() throws IOException    {
        ssdb_output.close();
        ssdb_input.close();
        socket.close();
    }
    
    public List<byte[]> receive() throws SSDBException{ 
    	return protocol.receive();
    }
    
    public void sendCommand(String  cmd,List<byte[]> params) throws SSDBException{ 
    	this.protocol.sendCommand(cmd, params);
    }
    
    /**
     * Send an array of bytes to the backend
     *
     * @param buf The array of bytes to be sent
     * @exception IOException if an I/O error occurs
     */
    public void Send(byte buf[]) throws IOException    {
        ssdb_output.write(buf);
    }
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket connection) {
		this.socket = connection;
	}
	

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
}
