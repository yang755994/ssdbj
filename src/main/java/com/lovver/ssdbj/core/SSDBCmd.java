package com.lovver.ssdbj.core;

public enum SSDBCmd {
	
	SET("set"),
	DEL("del"),
	GET("get"),
	SCAN("scan"),
	RSCAN("rscan"),
	INCR("incr"),
	HSET("hset"),
	HDEL("hdel"),
	HGET("hget"),
	HSCAN("hscan"),
	HRSCAN("hrscan"),
	HINCR("hincr"),
	ZSET("zset"),
	ZDEL("zdel"),
	ZGET("zget"),
	ZSCAN("zscan"),
	ZRSCAN("zrscan"),
	ZINCR("zincr"),
	MULTI_GET("multi_get"),
	MULTI_SET("multi_set"),
	MULTI_DEL("multi_del"),
	;
	
	private SSDBCmd(String cmd){
		this.cmd=cmd;
	}
	private String  cmd;
	
	public String getCmd(){
		return cmd;
	}
	
}
