import java.util.ArrayList;
import java.util.Properties;

import com.lovver.ssdbj.core.impl.SSDBResultSet;
import com.lovver.ssdbj.pool.SSDBDataSource;
import com.lovver.ssdbj.pool.SSDBPoolConnection;


public class SSDBPoolTest {
	static SSDBDataSource ds=null; 
	static{
		Properties info = new Properties();
		info.setProperty("password", "ddd");
		info.setProperty("loginTimeout", "300");
		info.setProperty("tcpKeepAlive", "true");
		info.setProperty("protocolName", "ssdb");
		info.setProperty("protocolVersion", "1.0");
		ds = new SSDBDataSource("192.168.0.226",8888,null,info);
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings({  "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		SSDBPoolConnection conn=null;
		for(int i=0;i<100000;i++){
			try{
				conn= ds.getConnection();
				ArrayList params=new ArrayList();
				params.add("joliny".getBytes());
				params.add("kkk".getBytes());
				SSDBResultSet rs=(SSDBResultSet) conn.execute("hget",params );
				System.out.println(new String(rs.getResult()));
			}finally{
				conn.close();
			}
		}
	}

}
