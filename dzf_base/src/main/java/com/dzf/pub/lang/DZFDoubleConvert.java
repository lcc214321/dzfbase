package com.dzf.pub.lang;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class DZFDoubleConvert extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			return null;
		}
		DZFDouble zf = null;
		String dateString = values[0];
		if(dateString != null && !"".equals(dateString)){
			zf = new DZFDouble(dateString);
		}
		return zf;
	}

	@Override
	public String convertToString(Map context, Object o) {
		if (o != null && o instanceof DZFDouble) {
			return o.toString();
		}
		return null;
	}

}
