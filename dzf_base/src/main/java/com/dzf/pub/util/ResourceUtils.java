package com.dzf.pub.util;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class ResourceUtils {
public interface IServletOutputStreamAction{
	public Object doAction(ServletOutputStream out);
}
public static Object doSOStreamAExec(HttpServletResponse hsr, IServletOutputStreamAction action) throws IOException{
	ServletOutputStream out=null;
	Object obj=null;
	try{
		out=hsr.getOutputStream();
		obj=action.doAction(out);
	}finally{
		if(out!=null){
			out.close();
		}
	}
	return obj;
}
}