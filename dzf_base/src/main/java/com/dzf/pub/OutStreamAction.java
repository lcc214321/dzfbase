package com.dzf.pub;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.bs.framework.comn.NetObjectInputStream;
import nc.bs.framework.comn.NetObjectOutputStream;

import com.dzf.pub.BusinessException;

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
			
			throw new BusinessException(e);
		}finally{
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BusinessException(e);
		}finally{
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
}
