package com.lovver.ssdbj.pool;

import java.util.Properties;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class SSDBConnectionPools  extends GenericObjectPool<SSDBPoolConnection> {

	private SSDBConnectionPools(PooledObjectFactory<SSDBPoolConnection> factory,
			GenericObjectPoolConfig config, AbandonedConfig abandonedConfig) {
		super(factory, config, abandonedConfig);
	}
	

	public SSDBConnectionPools(String host, int port, String user, Properties props){
		this(new SSDBPooledConnectionFactory<SSDBPoolConnection>(host,port,user,props),new GenericObjectPoolConfig(),new AbandonedConfig());
	}
}
