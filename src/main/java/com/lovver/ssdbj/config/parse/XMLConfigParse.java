package com.lovver.ssdbj.config.parse;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.lovver.ssdbj.config.Cluster;
import com.lovver.ssdbj.config.ClusterSsdbNode;
import com.lovver.ssdbj.config.SsdbNode;
import com.lovver.ssdbj.exception.SSDBJConfigException;

public class XMLConfigParse implements ConfigParser {

	@Override
	public List<Cluster> loadSSDBJ(String conf_file)
			throws SSDBJConfigException {
		List<Cluster> lstCluster = new ArrayList<Cluster>(1);
		try {
			Map<String,SsdbNode> mapSsdbNode=new HashMap<String,SsdbNode>();
			
			
			InputStream is=XMLConfigParse.class.getResourceAsStream(conf_file);
			SAXBuilder builder = new SAXBuilder();
			Document doc;
			doc = builder.build(is);
			Element rootEl = doc.getRootElement();
			
			List<Element> lstSSDBNode=rootEl.getChildren("ssdb_node");//.getChildren("ssdb_node");
			for(Element x_ssdb_node:lstSSDBNode){
				String id=x_ssdb_node.getAttributeValue("id");
				SsdbNode node=new SsdbNode();
				node.setId(id);
				node.setHost(x_ssdb_node.getAttributeValue("host"));
				String port=x_ssdb_node.getAttributeValue("port");
				if(StringUtils.isEmpty(port)){
					port="8888";
				}
				node.setPort(Integer.parseInt(port));
				String loginTimeout=x_ssdb_node.getAttributeValue("loginTimeout");
				if(StringUtils.isEmpty(loginTimeout)){
					loginTimeout="30";
				}
				node.setLoginTimeout(Integer.parseInt(loginTimeout));
				String master=x_ssdb_node.getAttributeValue("master");
				if(StringUtils.isEmpty(master)){
					master="false";
				}
				node.setMaster(new Boolean(master));
				node.setUser(x_ssdb_node.getAttributeValue("user"));
				node.setPassword(x_ssdb_node.getAttributeValue("password")); 
				
				String tcpKeepAlive=x_ssdb_node.getAttributeValue("tcpKeepAlive");
				if(StringUtils.isEmpty(tcpKeepAlive)){
					tcpKeepAlive="true";
				}
				node.setTcpKeepAlive(new Boolean(tcpKeepAlive));
				
				String protocolName=x_ssdb_node.getAttributeValue("protocolName");
				if(StringUtils.isEmpty(protocolName)){
					protocolName="ssdb";
				}
				node.setProtocolName(protocolName);
				mapSsdbNode.put(id, node);
			}
			
			
			List<Element> lstXClusters=rootEl.getChildren("clusters");
			if(lstXClusters==null||lstXClusters.size()>1||lstXClusters.size()==0){
				throw new SSDBJConfigException("ssdbj config file need clusters node and must be only one");
			}
			Element x_clusters=lstXClusters.get(0);
			List<Element> lstXCluster=x_clusters.getChildren("cluster");
			for(Element xCluster:lstXCluster){
				Cluster cluster = new Cluster();
				String cid=xCluster.getAttributeValue("id");
				if(StringUtils.isEmpty(cid)){
					throw new SSDBJConfigException("cluster's node [id] must not empty!");
				}
				cluster.setId(cid);
				String notfound_master_retry=xCluster.getAttributeValue("notfound_master_retry");
				if(StringUtils.isEmpty(notfound_master_retry)){
					notfound_master_retry="false";
				}
				cluster.setNotfound_master_retry(new Boolean(notfound_master_retry));
				List<Element> lstXLBSNode=xCluster.getChildren("ssdb_node");
				for(Element xLBNode:lstXLBSNode){
					String id=xLBNode.getText();
					SsdbNode snode= mapSsdbNode.get(id);;
					ClusterSsdbNode cnode = new ClusterSsdbNode();
					try {
						PropertyUtils.copyProperties(cnode, snode);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
					String weight=xLBNode.getAttributeValue("weight");
					if(StringUtils.isEmpty(weight)){
						weight="1";
					}
					cnode.setWeight(Integer.parseInt(weight));
					String rw=xLBNode.getAttributeValue("rw");
					cnode.setRw(rw);
					cluster.addNode(cnode);
				}
				lstCluster.add(cluster);
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lstCluster;
	}
}
