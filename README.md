ssdbj
=====

SSDB nosql 数据库java驱动 

## Who's using SSDBJ?
*[职通网] (http://www.zhitongjob.com)
![职通网](http://www.zhitongjob.com/images/logo.png)

##Java 单db连接demo
...java
public class SSDBJTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		SSDBDriver dd= new SSDBDriver();
		Properties info = new Properties();
		info.setProperty("user", "test");
		info.setProperty("password", "ddd");
		info.setProperty("loginTimeout", "300");
		info.setProperty("tcpKeepAlive", "true");
		info.setProperty("protocolName", "ssdb");
		info.setProperty("protocolVersion", "ddd");
		
		info.setProperty("SSDB_HOST", "192.168.0.226");
		info.setProperty("SSDB_PORT", "8888");
		SSDBConnection conn= dd.connect(info);
		ArrayList<byte[]> setparams=new ArrayList<byte[]>(){
			{
				add("joliny".getBytes());
				add("kkk".getBytes());
				add("是的发生地发生1231sdfsfg23".getBytes());
			}
		};
		conn.execute("hset",setparams);
		
		ArrayList params=new ArrayList();
		params.add("joliny".getBytes());
		params.add("kkk".getBytes());
		BaseResultSet<byte[]> rs=conn.execute("hget",params );
		System.out.println(new String(rs.getResult()));
	}
}

##Java 单db数据库连接池Demo
...java
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
				BaseResultSet<byte[]> rs= conn.execute("hget",params );
				System.out.println(new String(rs.getResult()));
				
				
				ArrayList mset_params=new ArrayList();
				mset_params.add("a".getBytes());
				mset_params.add("aaaaa1".getBytes());
				mset_params.add("b".getBytes());
				mset_params.add("bbbbbb2".getBytes());
				conn.executeUpdate("multi_set", mset_params);
				
				ArrayList mget_params=new ArrayList();
				mget_params.add("a".getBytes());
				mget_params.add("b".getBytes());
				
				BaseResultSet<Map<byte[],byte[]>> m_rs= conn.execute("multi_get",mget_params );
				Map<byte[],byte[]> items=m_rs.getResult();
				Iterator<byte[]> ite=items.keySet().iterator();
				while(ite.hasNext()){
					byte[] key=ite.next();
					System.out.println(new String(key)+"====="+new String(items.get(key)));
				}
				
				ArrayList<byte[]> scan_params=new ArrayList();
				scan_params.add("".getBytes());
				scan_params.add("".getBytes());
				scan_params.add("10".getBytes());
				BaseResultSet<Map<byte[],byte[]>> scan_rs=conn.execute("scan",scan_params );
				Map<byte[],byte[]> scan_items=scan_rs.getResult();
				Iterator<byte[]> scan_ite=scan_items.keySet().iterator();
				while(scan_ite.hasNext()){
					byte[] key=scan_ite.next();
					System.out.println(new String(key)+"====="+new String(scan_items.get(key)));
				}
			}finally{
				conn.close();
			}
		}
	}
}

##Java 多个db集群测试
...java
public class SSDBClusterTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ArrayList params=new ArrayList();
		params.add("joliny");
		params.add("kkk");
		BaseResultSet<byte[]> rs= SSDBJ.execute("userinfo_cluster",SSDBCmd.HGET,params);
		System.out.println(new String(rs.getResult()));
	}
}
...xml
<?xml version="1.0" encoding="UTF-8"?>
<ssdbj>
	<ssdb_node id="m_userinfo"   master="true"  host="192.168.0.226" port="8888"  user="" password="abcdefghijklmnopqrstuvwxyz1234567890" loginTimeout="3" tcpKeepAlive="true" protocolName="ssdb" 
		maxTotal="200" maxIdle="10" minIdle="5" testWhileIdle="true"
	/>
	<ssdb_node id="s_userinfo1"  master="false" host="192.168.0.226" port="8889"  user="" password="abcdefghijklmnopqrstuvwxyz1234567890" loginTimeout="3" tcpKeepAlive="true" protocolName="ssdb" 
		maxTotal="200" maxIdle="10" minIdle="5" testWhileIdle="true"
	/>
	<!-- 
	<ssdb_node id="s_userinfo2"  master="false" host="192.168.0.226" port="8890"  user="" password="abcdefghijklmnopqrstuvwxyz1234567890" loginTimeout="3" tcpKeepAlive="true" protocolName="ssdb" />
 	-->
	<clusters>
		<cluster id="userinfo_cluster" notfound_master_retry="true" error_retry_times="3" error_master_retry="true" retry_interval="500" balance="random_weight">
			<ssdb_node weight="1" rwMode="w">m_userinfo</ssdb_node>
			<ssdb_node weight="2" rwMode="r">s_userinfo1</ssdb_node>
			<!-- 
			<ssdb_node weight="3" rwMode="r">s_userinfo2</ssdb_node> 
			-->
		</cluster>
	</clusters>
</ssdbj>