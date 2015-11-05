package com.dzf.pub.lang;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public final class DZFDateConvert  extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			return null;
		}
		DZFDate zf = null;
		String dateString = values[0];
		if(dateString != null  && !"".equals(dateString)){
			zf = new DZFDate(dateString);
		}
		return zf;
	}

	@Override
	public String convertToString(Map context, Object o) {
		if (o != null && o instanceof DZFDate) {
			return o.toString();
		}
		return null;
	}

}
