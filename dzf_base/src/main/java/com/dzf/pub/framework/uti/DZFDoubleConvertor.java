/*
 * 创建日期 2005-9-16
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.dzf.pub.framework.uti;


import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import com.dzf.pub.lang.DZFDouble;


public class DZFDoubleConvertor implements Converter {


	public DZFDoubleConvertor() {

		this.defaultValue = null;
		this.useDefault = true;

	}


	public DZFDoubleConvertor(Object defaultValue) {

		this.defaultValue = defaultValue;
		this.useDefault = true;

	}


	private Object defaultValue = null;


	private boolean useDefault = true;

	
	public Object convert(Class type, Object value) {

		if (value == null) {
			if (useDefault) {
				return (defaultValue);
			} else {
				throw new ConversionException("No value specified");
			}
		}

		try {
			
			if (value instanceof Number){
				Number tmpNum=(Number)value;
				if (tmpNum.doubleValue()==0)
					return DZFDouble.ZERO_DBL;
				else if (tmpNum.doubleValue()==1){
					return DZFDouble.ONE_DBL;
				}else
					return new DZFDouble(tmpNum.toString().intern());
			}
			return new DZFDouble(value.toString().intern());
		} catch (Exception e) {
			if (useDefault) {
				return (defaultValue);
			} else {
				throw new ConversionException(e);
			}
		}

	}

}
