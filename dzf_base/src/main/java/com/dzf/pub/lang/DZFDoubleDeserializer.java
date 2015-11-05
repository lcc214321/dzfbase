package com.dzf.pub.lang;

import java.lang.reflect.Type;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

public class DZFDoubleDeserializer implements ObjectDeserializer{

	public final static DZFDoubleDeserializer instance = new DZFDoubleDeserializer();
	
	@Override
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        
        String name = (String) parser.parse();
        
        if (name == null) {
            return null;
        }
        
        return (T) new DZFDouble(name) ;
    }
	
	@Override
	public int getFastMatchToken() {
//      return JSONToken.LITERAL_STRING;
		return 0;
	}
}
