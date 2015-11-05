package com.dzf.pub.Field;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.jsqlparser.util.ExpressionUtils;

import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.pub.BusinessException;
import com.dzf.pub.StringUtil;
import com.dzf.pub.SuperVO;
import com.dzf.pub.cache.CorpCache;


public class FieldValidateUtils {

	public FieldValidateUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	  public static Map<String,String> getFieldMappingF(SuperVO svo){
		 
		  Map<String,String> m=getFieldMapping(svo);
		  Map<String,String> m1=new ConcurrentHashMap<String, String>();
		  for (String s : m.keySet()) {
			m1.put(m.get(s),s);
		}
		  m=null;
		  return m1;
	  }
	  public static void Validate(SuperVO svo){
		Validate(null,null, svo);
	  }
	  private static SuperVO getSuperVO(final String corp,final SuperVO headvo,final SuperVO svo1){
		  SuperVO svo=svo1;
		  if(StringUtil.isEmptyWithTrim(corp)==false){
			 svo=new SuperVO() {

					@Override
					public Object getAttributeValue(String name) {
						if(name.startsWith("corp.")){
							CorpVO cvo=CorpCache.getInstance().get(null, corp);
							return cvo.getAttributeValue(name.replaceFirst("corp.",""));
						}else 	if(name.startsWith("head.")){
							
							return headvo.getAttributeValue(name.replaceFirst("head.",""));
						}else
						return super.getAttributeValue(name);
					}

					@Override
					public String getParentPKFieldName() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String getPKFieldName() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String getTableName() {
						// TODO Auto-generated method stub
						return null;
					}
		  };}
		  return svo;
	  }
	  public static void Validate(final String userid,final String corp,SuperVO svo){
		 
		  StringBuffer sb=new StringBuffer();
		  
		  String str=Validate(corp,svo,svo.getChildren());
		  if(str!=null)
			  sb.append(str);
		  str=Validate(corp,svo,svo.getChildren());
		  if(str!=null)
			  sb.append(str);
		 if(sb.length()>0)
			 throw new BusinessException(sb.toString());
	  }
	  private static String Validate(String corp,SuperVO headvo,SuperVO[] svo){
		  int len1=svo==null?0:svo.length;
		  if(len1==0)return null;
		  Map<String,String> m= getFieldMapping(svo[0]);
		  String v=null;
		  String[] strs=null;
		  String[] strs1=null;
		  
		//  Object obj=null;
		  int len=0;
		  StringBuffer sb=new StringBuffer();
		  for (String fname : m.keySet()) {
			v=m.get(fname);
			strs1=v.split(";");
			len=strs1==null?0:strs1.length;
			for (int i = 0; i < len; i++) {
				strs=strs1[i].split(":");
				for(int j=0;j<len1;j++)
				if(ExpressionUtils.Validate(getSuperVO(corp, headvo,svo[j]) , strs[1])==false){
					sb.append(strs[0]).append(";");
				}
	
			}
		}
		  return sb.toString();
	  }
	  public static Map<String,String> getFieldMapping(SuperVO svo){
		  String cname=svo.getClass().getName();
		  Map<String,String> m= FieldValidateCache.getInstance().get(cname);
		
		 
			  if(m!=null&&m.size()>0)
				  return m;
	
		//  String[] strs=svo.getAttributeNames();
		  
		   m=new HashMap<String, String>();
//		  int len=strs==null?0:strs.length;
//		 
//		  for (int i = 0; i < len; i++) {
//			m.put(strs[i],strs[i]);
//		  }
		m=getFieldValidate(svo.getClass(),m);
		FieldValidateCache.getInstance().add(cname,m);
		  return m;
	  }
	  private static Map<String,String> getFieldValidate(Class classDef,Map<String,String> map){
		  
	       
	        Field[] fields = classDef.getDeclaredFields();  
	        boolean isPresent = false;
	    	FieldValidate comment =null;
	    	
	        for (int i = 0; i < fields.length; i++) {  
	             isPresent = fields[i]  
	                    .isAnnotationPresent(FieldValidate.class);  
	            if (isPresent) {  
	                // 取注解中的文字说明  
	            	 comment = fields[i]  
	                        .getAnnotation(FieldValidate.class); 
	            	 map.put(fields[i].getName(),comment.value());
	      
	            }  
	        }  
	        return map;
	  }
}
