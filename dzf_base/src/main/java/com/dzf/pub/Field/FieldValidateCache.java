package com.dzf.pub.Field;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FieldValidateCache {
private static FieldValidateCache fc=new FieldValidateCache();
private SoftReferenceMap<String, String[][]> map=new SoftReferenceMap<String,String[][]>();
//private Map<String, SoftReference<Map<String,String>>> map=new ConcurrentHashMap<String, SoftReference<Map<String, String>>>();//<String, SoftReference<Map<String,String>>>();


public void add(String key,Map<String,String> m){
	int len=m.size();
	String[][] strs=new String[len][];
	String[] ss=m.keySet().toArray(new String[0]);
	for (int i = 0; i < len; i++) {
		strs[i]=new String[2];
		strs[i][0]=ss[i];
		strs[i][1]=m.get(ss[i]);
	}
	map.put(key, strs);
}
public Map<String,String> get(String key){
	String[][] strs= map.get(key);
	Map<String,String> mm=new ConcurrentHashMap<String, String>();
	int len=strs==null?0:strs.length;
	if(strs==null)return null;
	for (int i = 0; i < len; i++) {
		mm.put(strs[i][0],strs[i][1]);
	}
	return mm;
}

	private FieldValidateCache() {

	}
public static FieldValidateCache getInstance(){
	return fc;
}
}
