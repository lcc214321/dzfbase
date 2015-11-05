package com.dzf.pub.lang;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class DZFBooleanConvert extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			return null;
		}
		DZFBoolean zf = null;
		String dateString = values[0];
		if(dateString != null){
			if("是".equals(dateString) || "true".equalsIgnoreCase(dateString)
					|| "Y".equalsIgnoreCase(dateString)){
				zf = new DZFBoolean("Y");
			}else{
				zf = new DZFBoolean("N");
			}
		}
		return zf;
	}

	@Override
	public String convertToString(Map context, Object o) {
		String result = "否";
		if (o != null && o instanceof DZFBoolean) {
			DZFBoolean db = (DZFBoolean)o;
			if(db.booleanValue()){
				result = "是";
			}
		}
		return result;
	}

}
