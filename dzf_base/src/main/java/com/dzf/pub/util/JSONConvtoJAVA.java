package com.dzf.pub.util;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dzf.pub.lang.DZFDouble;
import com.dzf.pub.lang.DZFDoubleDeserializer;


public class JSONConvtoJAVA {
	
	
	public static ParserConfig  getParserConfig(){
		ParserConfig config = ParserConfig.getGlobalInstance();
		config.getDerializers().put(DZFDouble.class, DZFDoubleDeserializer.instance);
		return config;
	}
	
}
