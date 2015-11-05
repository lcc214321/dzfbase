/*
 * �������� 2005-9-16
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.dzf.pub.framework.uti;



import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import com.dzf.pub.lang.DZFBoolean;

public class DZFBooleanConvertor implements Converter {
	public DZFBooleanConvertor() {

		this.defaultValue = null;
		this.useDefault = true;

	}

	public DZFBooleanConvertor(Object defaultValue) {

		this.defaultValue = defaultValue;
		this.useDefault = true;

	}

	private Object defaultValue = null;

	private boolean useDefault = true;

	public Object convert(Class type, Object value) {

		if (value == null) {
			return null;
		}

		try {
			return (DZFBoolean.valueOf(value.toString()));
		} catch (Exception e) {
			if (useDefault) {
				return (defaultValue);
			} else {
				throw new ConversionException(e);
			}
		}

	}

}
