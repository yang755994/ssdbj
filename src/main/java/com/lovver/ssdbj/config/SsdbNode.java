package com.lovver.ssdbj.config;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SsdbNode implements Serializable {
	private String id;
	private boolean master;
	private String host;
	private int port;
	private String user;
	private String password;
	private int loginTimeout;
	private boolean tcpKeepAlive;
	private String protocolName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isMaster() {
		return master;
	}
	public void setMaster(boolean master) {
		this.master = master;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getLoginTimeout() {
		return loginTimeout;
	}
	public void setLoginTimeout(int loginTimeout) {
		this.loginTimeout = loginTimeout;
	}
	public boolean isTcpKeepAlive() {
		return tcpKeepAlive;
	}
	public void setTcpKeepAlive(boolean tcpKeepAlive) {
		this.tcpKeepAlive = tcpKeepAlive;
	}
	public String getProtocolName() {
		return protocolName;
	}
	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}
	
}
