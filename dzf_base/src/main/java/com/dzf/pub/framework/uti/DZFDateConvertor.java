/*
 * 创建日期 2005-9-16
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.dzf.pub.framework.uti;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import com.dzf.pub.lang.DZFDate;


public class DZFDateConvertor implements Converter {

	private Object defaultValue = null;

	private boolean useDefault = true;

	public DZFDateConvertor() {

		this.defaultValue = null;
		this.useDefault = true;

	}

	public DZFDateConvertor(Object defaultValue) {

		this.defaultValue = defaultValue;
		this.useDefault = true;

	}

	public Object convert(Class type, Object value) {

		if (value == null) {
			if (useDefault) {
				return (defaultValue);
			} else {
				throw new ConversionException("No value specified");
			}
		}

		try {
			if (value instanceof java.util.Date) {
				return DZFDate.getDate((Date) value);
			} else if (value instanceof Calendar) {
				return DZFDate.getDate(((Calendar) value).getTime());
			}
			return DZFDate.getDate(value.toString().intern());
		} catch (Exception e) {
			if (useDefault) {
				return (defaultValue);
			} else {
				throw new ConversionException(e);
			}
		}

	}
}
