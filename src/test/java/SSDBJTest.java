import java.awt.List;
import java.util.ArrayList;
import java.util.Properties;

import com.lovver.ssdbj.core.SSDBDriver;
import com.lovver.ssdbj.core.impl.SSDBConnection;



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
		SSDBConnection conn= dd.connect("192.168.0.226:8888", info);
		ArrayList params=new ArrayList();
		params.add("kkk".getBytes());
		conn.execute("get",params );
		System.out.println();
	}

}
