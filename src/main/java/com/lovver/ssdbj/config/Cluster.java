package com.lovver.ssdbj.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Cluster implements Serializable {
	
	private String id;
	private boolean notfound_master_retry;
	
	private List<ClusterSsdbNode> lstSsdbNode=new ArrayList<ClusterSsdbNode>(2);
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isNotfound_master_retry() {
		return notfound_master_retry;
	}
	public void setNotfound_master_retry(boolean notfound_master_retry) {
		this.notfound_master_retry = notfound_master_retry;
	}
	public List<ClusterSsdbNode> getLstSsdbNode() {
		return lstSsdbNode;
	}
	public void setLstSsdbNode(List<ClusterSsdbNode> lstSsdbNode) {
		this.lstSsdbNode = lstSsdbNode;
	}
	
	public void addNode(ClusterSsdbNode node){
		this.lstSsdbNode.add(node);
	}
	
	public void removeNode(ClusterSsdbNode node){
		this.lstSsdbNode.remove(node);
	}
	
	public void removeNode(String node_id){
		for(SsdbNode node:lstSsdbNode){
			if(node.getId().equals(node_id)){
				this.lstSsdbNode.remove(node);
			}
		}
	}
	
	
}
