package com.dzf.pub.lang;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class DZFBooleanSerializer implements ObjectSerializer {

	public final static DZFBooleanSerializer instance = new DZFBooleanSerializer();
	
	@Override
	public void write(JSONSerializer serializer, Object object,
			Object fieldName, Type fieldType, int features) throws IOException {
		 SerializeWriter out = serializer.getWriter();

//	        if (object == null) {
//	            out.writeNull();
//	            return;
//	        }
	        String v = null;
	        DZFBoolean db = (DZFBoolean)object;
	        if( db !=null && db.booleanValue()){
	        	v = "是";
	        }else{
	        	v = "否";
	        }
	        
	        out.writeString(v);
	}
	

}
