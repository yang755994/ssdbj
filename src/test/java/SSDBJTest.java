import java.util.ArrayList;
import java.util.Properties;

import com.lovver.ssdbj.core.SSDBDriver;
import com.lovver.ssdbj.core.impl.SSDBConnection;
import com.lovver.ssdbj.core.impl.SSDBResultSet;



public class SSDBJTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		SSDBDriver dd= new SSDBDriver();
		Properties info = new Properties();
		info.setProperty("user", "test");
		info.setProperty("password", "ddd");
		info.setProperty("tcpKeepAlive", "true");
		info.setProperty("protocolName", "ssdb");
		info.setProperty("protocolVersion", "ddd");
		SSDBConnection conn= dd.connect("192.168.0.226:8888", info);
		ArrayList<byte[]> setparams=new ArrayList<byte[]>(){
			{
				add("kkk".getBytes());
				add("是的发生地发生123123".getBytes());
			}
		};
		conn.execute("set",setparams);
		
		ArrayList params=new ArrayList();
		params.add("kkk".getBytes());
		SSDBResultSet rs=conn.execute("get",params );
		System.out.println(new String(rs.getResult()));
	}

}
