import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.lovver.ssdbj.core.BaseConnection;
import com.lovver.ssdbj.core.impl.SSDBResultSet;
import com.lovver.ssdbj.pool.ConnectionPool;
import com.lovver.ssdbj.pool.PoolConnection;
import com.lovver.ssdbj.pool.PooledConnectionFactory;



public class SSDBJTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
//		SSDBDriver dd= new SSDBDriver();
//		Properties info = new Properties();
//		info.setProperty("user", "test");
//		info.setProperty("password", "ddd");
//		info.setProperty("loginTimeout", "300");
//		info.setProperty("tcpKeepAlive", "true");
//		info.setProperty("protocolName", "ssdb");
//		info.setProperty("protocolVersion", "ddd");
//		
//		info.setProperty("SSDB_HOST", "192.168.0.226");
//		info.setProperty("SSDB_PORT", "8888");
//		SSDBConnection conn= dd.connect(info);
//		ArrayList<byte[]> setparams=new ArrayList<byte[]>(){
//			{
//				add("joliny".getBytes());
//				add("kkk".getBytes());
//				add("是的发生地发生1231sdfsfg23".getBytes());
//			}
//		};
//		conn.execute("hset",setparams);
//		
//		ArrayList params=new ArrayList();
//		params.add("joliny".getBytes());
//		params.add("kkk".getBytes());
//		SSDBResultSet rs=conn.execute("hget",params );
//		System.out.println(new String(rs.getResult()));
		
		
		Properties info = new Properties();
		info.setProperty("password", "ddd");
		info.setProperty("loginTimeout", "300");
		info.setProperty("tcpKeepAlive", "true");
		info.setProperty("protocolName", "ssdb");
		info.setProperty("protocolVersion", "ddd");
		PooledConnectionFactory<BaseConnection> factory = new PooledConnectionFactory("192.168.0.226",8888,null,info);
		ConnectionPool pools = new ConnectionPool(factory,new GenericObjectPoolConfig(),new AbandonedConfig());
		PoolConnection conn=(PoolConnection) pools.borrowObject();
		
		ArrayList params=new ArrayList();
		params.add("joliny".getBytes());
		params.add("kkk".getBytes());
		SSDBResultSet rs=(SSDBResultSet) conn.execute("hget",params );
		System.out.println(new String(rs.getResult()));
	}

}
