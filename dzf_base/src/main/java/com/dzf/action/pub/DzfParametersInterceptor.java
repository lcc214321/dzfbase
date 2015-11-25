package com.dzf.action.pub;

import java.util.Map;

import com.dzf.pub.Logger;
import com.dzf.pub.StringUtil;
import com.dzf.pub.SuperVO;
import com.dzf.pub.Field.FieldMapping;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;

public class DzfParametersInterceptor extends ParametersInterceptor {

	public DzfParametersInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Map<String, Object> retrieveParameters(ActionContext ac) {
		// TODO Auto-generated method stub
		Map<String, Object> map= super.retrieveParameters(ac);
		Object action = ac.getActionInvocation().getAction();
		if(action instanceof BaseAction){
			Class cs=action.getClass();
		try {
			BaseAction ba=(BaseAction) action;
			cs=ba.doGetClass();
			SuperVO svo= (SuperVO) cs.newInstance();
			Map<String, String> m=FieldMapping.getFieldMappingF(svo);
		String[] strs=map.keySet().toArray(new String[0]);
		int len=strs==null?0:strs.length;
		String s=null;
		String s1=null;
		Object value=null;
		for (int i = 0; i < len; i++) {
			s=strs[i];
			value=map.get(s);
			s=s.replace("data.","");
			s1=m.get(s);
			if(StringUtil.isEmptyWithTrim(s1)==false){//&&s1.equals(s)==false
				s=s1;
			map.put("data."+s, value);
			//map.remove(strs[i]);
			}
			
		}
			m=null;
		} catch (Exception e) {
			Logger.error(this, e.getMessage(), e);
		}
		}
		return map;
	}
		
}
