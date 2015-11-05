package com.dzf.pub.lang;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class DZFDoubleSerializer implements ObjectSerializer {
	public final static DZFDoubleSerializer instance      = new DZFDoubleSerializer();

    private DecimalFormat decimalFormat = new DecimalFormat("######0.00");

    public DZFDoubleSerializer(){

    }

    public DZFDoubleSerializer(DecimalFormat decimalFormat){
        this.decimalFormat = decimalFormat;
    }

    public DZFDoubleSerializer(String decimalFormat){
        this(new DecimalFormat(decimalFormat));
    }
	@Override
	public void write(JSONSerializer serializer, Object object,
			Object fieldName, Type fieldType, int features) throws IOException {
		 SerializeWriter out = serializer.getWriter();

	        if (object == null) {
	            if (serializer.isEnabled(SerializerFeature.WriteNullNumberAsZero)) {
	                out.write('0');
	            } else {
	                out.writeNull();
	            }
	            return;
	        }

	        double doubleValue = ((DZFDouble) object).doubleValue();

	        if (Double.isNaN(doubleValue)) {
	            out.writeNull();
	        } else if (Double.isInfinite(doubleValue)) {
	            out.writeNull();
	        } else {
	            String doubleText;
	            if (decimalFormat == null) {
	                doubleText = Double.toString(doubleValue);
	                if (doubleText.endsWith(".0")) {
	                    doubleText = doubleText.substring(0, doubleText.length() - 2);
	                }
	            } else {
	                doubleText = decimalFormat.format(doubleValue);
	            }
	            out.append(doubleText);

	            if (serializer.isEnabled(SerializerFeature.WriteClassName)) {
	                out.write('D');
	            }
	        }

	}

}
