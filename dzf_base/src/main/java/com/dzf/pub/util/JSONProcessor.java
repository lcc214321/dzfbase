package com.dzf.pub.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.LabelFilter;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.dzf.pub.lang.DZFBoolean;
import com.dzf.pub.lang.DZFBooleanSerializer;
import com.dzf.pub.lang.DZFDate;
import com.dzf.pub.lang.DZFDateSerializer;
import com.dzf.pub.lang.DZFDateTime;
import com.dzf.pub.lang.DZFDateTimeSerializer;
import com.dzf.pub.lang.DZFDouble;
import com.dzf.pub.lang.DZFDoubleQfSerializer;
import com.dzf.pub.lang.DZFDoubleSerializer;

public class JSONProcessor extends JSON{
	 public static String toJSONString(Object object,SerializeFilter filter, SerializerFeature... features) {
	    	return toJSONString(object,new HashMap<String, String>(),filter,features);
	 }

    public static String toJSONString(Object object,Map<String, String> fieldMapping, SerializeFilter filter, SerializerFeature... features) {
    	return toJSONString(object, fieldMapping,  false, filter,  features);
    }
	 public static String toJSONString(Object object,SerializeFilter filter, boolean isqff,SerializerFeature... features) {
	    	return toJSONString(object,new HashMap<String, String>(),isqff,filter,features);
	 }
    public static String toJSONString(Object object,Map<String, String> fieldMapping, boolean isqff, SerializeFilter filter, SerializerFeature... features) {
    	DzfSerializeWriter out = new DzfSerializeWriter();
        try {
        	if(filter instanceof FastjsonFilter){
        		FastjsonFilter ft=(FastjsonFilter) filter;
    		ft.getExcludes().add("tableName");
			ft.getExcludes().add("primarykey");
			ft.getExcludes().add("parentPKFieldName");
			ft.getExcludes().add("pKFieldName");
			ft.getExcludes().add("attributeNames");
			ft.getExcludes().add("order");
        	}
    		SerializeConfig config = getSerializeConfig(isqff);
    	//	out.setFieldMapping(fieldMapping);
    		DzfJSONSerializer serializer = new DzfJSONSerializer((SerializeWriter)out, config);
    		//out.setSerializer( serializer);
    	
            for (com.alibaba.fastjson.serializer.SerializerFeature feature : features) {
                serializer.config(feature, true);
            }
            serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            setFilter(serializer, filter);
            serializer.write(object);
            return out.toString();
        } finally {
            out.close();
        }
    }
	protected static SerializeConfig getSerializeConfig(boolean isqff) {
		SerializeConfig config = SerializeConfig.getGlobalInstance();
		if(isqff)
			config.put(DZFDouble.class, DZFDoubleQfSerializer.instance);
		else
		config.put(DZFDouble.class, DZFDoubleSerializer.instance);
		config.put(DZFBoolean.class, DZFBooleanSerializer.instance);
		config.put(DZFDate.class, DZFDateSerializer.instance);
		config.put(DZFDateTime.class, DZFDateTimeSerializer.instance);
		return config;
	}
    private static void setFilter(JSONSerializer serializer, SerializeFilter filter) {
        if (filter == null) {
            return;
        }
        
        if (filter instanceof PropertyPreFilter) {
            serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
        }

        if (filter instanceof NameFilter) {
            serializer.getNameFilters().add((NameFilter) filter);
        }

        if (filter instanceof ValueFilter) {
            serializer.getValueFilters().add((ValueFilter) filter);
        }

        if (filter instanceof PropertyFilter) {
            serializer.getPropertyFilters().add((PropertyFilter) filter);
        }

        if (filter instanceof BeforeFilter) {
            serializer.getBeforeFilters().add((BeforeFilter) filter);
        }

        if (filter instanceof AfterFilter) {
            serializer.getAfterFilters().add((AfterFilter) filter);
        }
        
        if (filter instanceof LabelFilter) {
            serializer.getLabelFilters().add((LabelFilter) filter);
        }
    }
	
}
