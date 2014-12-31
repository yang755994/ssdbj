import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.lovver.ssdbj.core.BaseConnection;
import com.lovver.ssdbj.core.impl.SSDBResultSet;
import com.lovver.ssdbj.pool.ConnectionPool;
import com.lovver.ssdbj.pool.PoolConnection;
import com.lovver.ssdbj.pool.PooledConnectionFactory;


public class SSDBPoolTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings({  "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		Properties info = new Properties();
		info.setProperty("password", "ddd");
		info.setProperty("loginTimeout", "300");
		info.setProperty("tcpKeepAlive", "true");
		info.setProperty("protocolName", "ssdb");
		info.setProperty("protocolVersion", "1.0");
//		PooledConnectionFactory<BaseConnection> factory = new PooledConnectionFactory("192.168.0.226",8888,null,info);
		ConnectionPool pools = new ConnectionPool("192.168.0.226",8888,null,info);
		PoolConnection conn=(PoolConnection) pools.borrowObject();
		
		ArrayList params=new ArrayList();
		params.add("joliny".getBytes());
		params.add("kkk".getBytes());
		SSDBResultSet rs=(SSDBResultSet) conn.execute("hget",params );
		pools.returnObject(conn);
		System.out.println(new String(rs.getResult()));
	}

}
