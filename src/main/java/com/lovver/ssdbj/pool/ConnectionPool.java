package com.lovver.ssdbj.pool;

import java.util.Properties;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class ConnectionPool  extends GenericObjectPool<PoolConnection> {

	private ConnectionPool(PooledObjectFactory<PoolConnection> factory,
			GenericObjectPoolConfig config, AbandonedConfig abandonedConfig) {
		super(factory, config, abandonedConfig);
	}
	

	public ConnectionPool(String host, int port, String user, Properties props){
		this(new PooledConnectionFactory<PoolConnection>(host,port,user,props),new GenericObjectPoolConfig(),new AbandonedConfig());
	}
}
