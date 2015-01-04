package com.lovver.ssdbj;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lovver.ssdbj.config.Cluster;
import com.lovver.ssdbj.config.parse.XMLConfigParse;
import com.lovver.ssdbj.core.impl.SSDBResultSet;
import com.lovver.ssdbj.exception.SSDBJConfigException;

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
		} catch (SSDBJConfigException e) {
			e.printStackTrace();
		}
	}
	
	public static SSDBResultSet execute(){
		return null;
	}
	
}
