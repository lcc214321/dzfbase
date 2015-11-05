/*
 * 创建日期 2005-9-16
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.dzf.pub.framework.uti;

import java.util.Calendar;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import com.dzf.pub.lang.DZFDateTime;

public class DZFDateTimeConvertor implements Converter {

	private Object defaultValue = null;

	private boolean useDefault = true;

	/**
	 * Create a {@link Converter} that will throw a {@link ConversionException}
	 * if a conversion error occurs.
	 */
	public DZFDateTimeConvertor() {

		this.defaultValue = null;
		this.useDefault = true;

	}

	public DZFDateTimeConvertor(Object defaultValue) {

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
				return new DZFDateTime((java.util.Date) value);
			} else if (value instanceof Calendar) {
				return new DZFDateTime(((Calendar) value).getTimeInMillis());
			}
			return (new DZFDateTime(value.toString().intern(), false));
		} catch (Exception e) {
			if (useDefault) {
				return (defaultValue);
			} else {
				throw new ConversionException(e);
			}
		}

	}

}
