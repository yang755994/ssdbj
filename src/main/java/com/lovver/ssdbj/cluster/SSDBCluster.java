package com.lovver.ssdbj.cluster;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.lovver.ssdbj.config.Cluster;
import com.lovver.ssdbj.config.ClusterSsdbNode;
import com.lovver.ssdbj.loadbalance.LoadBalanceFactory;
import com.lovver.ssdbj.pool.SSDBDataSource;

public class SSDBCluster {
	//map<cluster_id+"|"+ssdbnode_id,ds>
	private static Map<String,SSDBDataSource> mDS=new ConcurrentHashMap<String, SSDBDataSource>(); 
	
	private static LoadBalanceFactory balanceFactory=LoadBalanceFactory.getInstance();
	public static void initCluster(List<Cluster> clusters) {
//		SSDBCluster.clusters=clusters;
		balanceFactory.setClusterConfig(clusters);
		for(Cluster cluster:clusters){
			String cluster_id=cluster.getId();
			
			List<ClusterSsdbNode> lstCNode=cluster.getLstSsdbNode();
			for(ClusterSsdbNode cNode:lstCNode){
				Properties props = new Properties();
//				PropertyUtils.copyProperties(props, cNode);
				props.put("loginTimeout", cNode.getLoginTimeout()+"");
				props.put("password", cNode.getPassword());
				props.put("protocolName", cNode.getProtocolName());
				props.put("tcpKeepAlive", cNode.isTcpKeepAlive()+"");
				props.put("host", cNode.getHost());
				props.put("port", cNode.getPort()+"");
				props.put("user", cNode.getUser());
				
				String host=props.getProperty("host", "localhost");
				int port=Integer.parseInt(props.getProperty("port", "8888"));
				String user=props.getProperty("user");
				SSDBDataSource ds=new SSDBDataSource(host,port,user,props);
				synchronized (mDS) {
					String m_id=cluster_id+"|"+cNode.getId();
					mDS.put(m_id, ds);
				}
			}
			balanceFactory.createLoadBalance(cluster_id);
		}
		
	}
	
	public static SSDBDataSource getDataSource(String cluster_id,String ssdb_node_id){
		return mDS.get(cluster_id+"|"+ssdb_node_id);
	}
}
