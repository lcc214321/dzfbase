package com.dzf.pub.util;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class ResourceUtils {
public interface ICloseAction<T>{
	public Object doAction(T out);
}
public static Object doSOStreamAExec(HttpServletResponse hsr, ICloseAction<ServletOutputStream> action) throws IOException{
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