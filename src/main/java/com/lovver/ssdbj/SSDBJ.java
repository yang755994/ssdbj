package com.lovver.ssdbj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	private static Map<String,Cluster> cachedClusterConf=new ConcurrentHashMap<String, Cluster>();
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
			
			//≈‰÷√cache
			for(Cluster cluster:lstCluster){
				cachedClusterConf.put(cluster.getId(),cluster);
			}
		} catch (SSDBJConfigException e) {
			e.printStackTrace();
		}
	}
	
	
	private static LoadBalanceFactory balanceFactory=LoadBalanceFactory.getInstance();
	
	public static SSDBResultSet execute(String cluster_id,SSDBCmd cmd,String...params ) throws Exception{
		return execute(cluster_id,cmd,Arrays.asList(params));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SSDBResultSet execute(String cluster_id,SSDBCmd cmd,List<String> params) throws Exception{
		LoadBalance lb = balanceFactory.createLoadBalance(cluster_id);
		SSDBPoolConnection conn=null;
		SSDBResultSet rs=null;
		SSDBDataSource ds=null;
		
		List<byte[]> bP=new ArrayList<byte[]>();
		for(String p:params){
			bP.add(p.getBytes());
		}
		
		try{
			if(cmd.getSlave()){
				ds=lb.getReadDataSource(cluster_id);
			}else{
				ds=lb.getWriteDataSource(cluster_id);
			}
			conn=ds.getConnection();
			rs= (SSDBResultSet) conn.execute(cmd.getCmd(), bP);
		}catch(Exception e){
			throw e;
		}finally{
			conn.close();
		}
		
		if(rs.getStatus().equals("not_found")&&cachedClusterConf.get(cluster_id).isNotfound_master_retry()){
			try{
				ds=lb.getWriteDataSource(cluster_id);
				conn=ds.getConnection();
				rs= (SSDBResultSet) conn.execute(cmd.getCmd(), bP);
			}catch(Exception e){
				throw e;
			}finally{
				conn.close();
			}
		}
		return rs;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean executeUpdate(String cluster_id,SSDBCmd  cmd,List<byte[]> params) throws Exception{
		LoadBalance lb = balanceFactory.createLoadBalance(cluster_id);
		SSDBPoolConnection conn=null;
		try{
			SSDBDataSource ds=null;
			ds=lb.getWriteDataSource(cluster_id);
			conn=ds.getConnection();
			return conn.executeUpdate(cmd.getCmd(), params);
		}catch(Exception e){
			throw e;
		}finally{
			conn.close();
		}
	}
	
	public static boolean executeUpdate(String cluster_id,SSDBCmd  cmd,String... params) throws Exception{
		List<byte[]> bP=new ArrayList<byte[]>();
		for(String p:params){
			bP.add(p.getBytes());
		}
		return executeUpdate(cluster_id,cmd,bP);
	}
	
}
