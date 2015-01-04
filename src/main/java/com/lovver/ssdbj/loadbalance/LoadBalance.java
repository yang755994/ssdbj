package com.lovver.ssdbj.loadbalance;

import com.lovver.ssdbj.pool.SSDBPoolConnection;

public interface LoadBalance {
	public SSDBPoolConnection getConnection();
}
