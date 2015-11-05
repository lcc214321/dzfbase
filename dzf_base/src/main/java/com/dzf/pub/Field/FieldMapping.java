package com.dzf.pub.Field;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dzf.pub.SuperVO;

public class FieldMapping {

	public FieldMapping() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public static Map<String, String> getFieldMappingF(SuperVO svo) {
		Map<String, String> m = getFieldMapping(svo);
		Map<String, String> m1 = new ConcurrentHashMap<String, String>();
		for (String s : m.keySet()) {
			m1.put(m.get(s), s);
		}
		m = null;
		return m1;
	}

	public static String getFieldNameByAlias(SuperVO svo, String alias) {
		Map<String, String> m = getFieldMappingF(svo);
		return m.get(alias);
	}

	public static Map<String, String> getFieldMapping(SuperVO svo) {
		String cname = svo.getClass().getName();
		Map<String, String> m = FieldMappingCache.getInstance().get(cname);
		if (m != null && m.size() > 0)
			return m;
		String[] strs = svo.getAttributeNames();
		m = new HashMap<String, String>();
		int len = strs == null ? 0 : strs.length;
		for (int i = 0; i < len; i++) {
			m.put(strs[i], strs[i]);
		}
		m = getFieldAlias(svo.getClass(), m);
		FieldMappingCache.getInstance().add(cname, m);
		return m;
	}
	
	
	public static Map<String, String> getFieldMapping(String key,SuperVO[] svos) {
		Map<String, String> m = FieldMappingCache.getInstance().get(key);
		if (m != null && m.size() > 0)
			return m;
		m = new HashMap<String, String>();
		for(SuperVO v : svos){
			String[] strs = v.getAttributeNames();
			int len = strs == null ? 0 : strs.length;
			for (int i = 0; i < len; i++) {
				m.put(strs[i], strs[i]);
			}
			m = getFieldAlias(v.getClass(), m);
		}
		FieldMappingCache.getInstance().add(key, m);
		return m;
	}

	private static Map<String, String> getFieldAlias(Class classDef,
			Map<String, String> map) {
		Field[] fields = classDef.getDeclaredFields();
		boolean isPresent = false;
		FieldAlias comment = null;
		for (int i = 0; i < fields.length; i++) {
			isPresent = fields[i].isAnnotationPresent(FieldAlias.class);
			if (isPresent) {
				comment = fields[i].getAnnotation(FieldAlias.class);
				map.put(fields[i].getName(), comment.value());
			}
		}
		return map;
	}
}
