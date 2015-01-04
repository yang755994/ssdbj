package com.lovver.ssdbj;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lovver.ssdbj.cluster.SSDBCluster;
import com.lovver.ssdbj.config.Cluster;
import com.lovver.ssdbj.config.parse.XMLConfigParse;
import com.lovver.ssdbj.core.SSDBCmd;
import com.lovver.ssdbj.core.impl.SSDBResultSet;
import com.lovver.ssdbj.exception.SSDBJConfigException;
import com.lovver.ssdbj.loadbalance.LoadBalance;
import com.lovver.ssdbj.loadbalance.LoadBalanceFactory;
import com.lovver.ssdbj.pool.SSDBDataSource;
import com.lovver.ssdbj.pool.SSDBPoolConnection;

public class SSDBJ {
	private static String DEFAULT_SSDBJ_FILE="/ssdbj.xml";
	static{
		load(null);
	}
	
	private static void load(String ssdbj_file){
		if(StringUtils.isEmpty(ssdbj_file)){
			ssdbj_file=DEFAULT_SSDBJ_FILE;
		}
		
		XMLConfigParse parse = new XMLConfigParse();
		try {
			List<Cluster> lstCluster=parse.loadSSDBJ(ssdbj_file);
			SSDBCluster.initCluster(lstCluster);
		} catch (SSDBJConfigException e) {
			e.printStackTrace();
		}
	}
	
	
	private static LoadBalanceFactory balanceFactory=LoadBalanceFactory.getInstance();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SSDBResultSet execute(String cluster_id,SSDBCmd cmd,List<String> params) throws Exception{
		LoadBalance lb = balanceFactory.createLoadBalance(cluster_id);
		SSDBPoolConnection conn=null;
		try{
			SSDBDataSource ds=null;
			if(cmd.getSlave()){
				ds=lb.getReadDataSource(cluster_id);
			}else{
				ds=lb.getWriteDataSource(cluster_id);
			}
			conn=ds.getConnection();
			List<byte[]> bP=new ArrayList<byte[]>();
			for(String p:params){
				bP.add(p.getBytes());
			}
			return (SSDBResultSet) conn.execute(cmd.getCmd(), bP);
		}catch(Exception e){
			throw e;
		}finally{
			conn.close();
		}
	}
	
}
