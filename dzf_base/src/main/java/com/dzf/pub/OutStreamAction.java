package com.dzf.pub;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;

public class OutStreamAction extends BaseAppAction{

	public OutStreamAction() {
		// TODO Auto-generated constructor stub
	}
	public Object getInputPut(){
		Object obj=null;
		HttpServletRequest hr=getRequest();
		ServletInputStream is=null;
		try {
			is = hr.getInputStream();
			NetObjectInputStream nis=new NetObjectInputStream(is);
			obj=nis.readObject();
		} catch (Exception e) {
			
			throw new DZFWarpException(e);
		}finally{
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					throw new DZFWarpException(e);
				}
		}
	
		return obj;
	}
	public void OutPut(Object obj){
		
		HttpServletResponse hr=getResponse();
		ServletOutputStream os=null;
		try {
			os=hr.getOutputStream();
			NetObjectOutputStream nis=new NetObjectOutputStream(os);
			nis.writeObject(obj);
			nis.flush();
			nis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new DZFWarpException(e);
		}finally{
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					throw new DZFWarpException(e);
				}
		}

	}
}
