package com.lovver.ssdbj.core.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lovver.ssdbj.core.MemoryStream;
import com.lovver.ssdbj.core.SSDBProtocol;
import com.lovver.ssdbj.exception.SSDBException;

public class SSDBProtocolImpl implements SSDBProtocol{
	
	private MemoryStream input = new MemoryStream();
	private OutputStream os;
	private InputStream is;
	
	public SSDBProtocolImpl(OutputStream os,InputStream is){
		this.os=os;
		this.is=is;
	}
	
	public void sendCommand(String cmd,List<byte[]> params)throws SSDBException {
    	MemoryStream buf = new MemoryStream(4096);
		Integer len = cmd.length();
		buf.write(len.toString());
		buf.write('\n');
		buf.write(cmd);
		buf.write('\n');
		for(byte[] bs : params){
			len = bs.length;
			buf.write(len.toString());
			buf.write('\n');
			buf.write(bs);
			buf.write('\n');
		}
		buf.write('\n');
		try{
			os.write(buf.buf, buf.data, buf.size);
			os.flush();
		}catch(Exception e){
			throw new SSDBException(e);
		}
    }
	
    
	public List<byte[]> receive() throws SSDBException{
		try{
			input.nice();
			while(true){
				List<byte[]> ret = parse();
				if(ret != null){
					return ret;
				}
				byte[] bs = new byte[8192];
				int len = is.read(bs);
				//System.out.println("<< " + (new MemoryStream(bs, 0, len)).printable());
				input.write(bs, 0, len);
			}
		}catch(Exception e){
			throw new SSDBException(e);
		}
	}
	
	private List<byte[]> parse(){
		ArrayList<byte[]> list = new ArrayList<byte[]>();
		byte[] buf = input.buf;
		
		int idx = 0;
		while(true){
			int pos = input.memchr('\n', idx);
			//System.out.println("pos: " + pos + " idx: " + idx);
			if(pos == -1){
				break;
			}
			if(pos == idx || (pos == idx + 1 && buf[idx] == '\r')){
				// ignore empty leading lines
				if(list.isEmpty()){
					idx += 1; // if '\r', next time will skip '\n'
					continue;
				}else{
					input.decr(idx + 1);
					return list;
				}
			}
			String str = new String(buf, input.data + idx, pos - idx);
			int len = Integer.parseInt(str);
			idx = pos + 1;
			if(idx + len >= input.size){
				break;
			}
			byte[] data = Arrays.copyOfRange(buf, input.data + idx, input.data + idx + len);
			//System.out.println("len: " + len + " data: " + data.length);
			idx += len + 1; // skip '\n'
			list.add(data);
		}
		return null;		
	} 
}
