package com.lovver.ssdbj.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class ConnectionPool<T>  extends GenericObjectPool<T> {

	public ConnectionPool(PooledObjectFactory<T> factory,
			GenericObjectPoolConfig config, AbandonedConfig abandonedConfig) {
		super(factory, config, abandonedConfig);
	}
	

}
