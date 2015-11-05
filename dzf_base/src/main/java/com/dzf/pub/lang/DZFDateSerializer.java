package com.dzf.pub.lang;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class DZFDateSerializer implements ObjectSerializer {

	public final static DZFDateSerializer instance = new DZFDateSerializer();
	
	@Override
	public void write(JSONSerializer serializer, Object object,
			Object fieldName, Type fieldType, int features) throws IOException {
		 SerializeWriter out = serializer.getWriter();

	        if (object == null) {
	            out.writeNull();
	            return;
	        }
	        
	        out.writeString(object.toString());
	}
	

}
