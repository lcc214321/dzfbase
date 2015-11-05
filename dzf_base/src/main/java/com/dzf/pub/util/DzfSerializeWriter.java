/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dzf.pub.util;

import java.util.Map;

import com.alibaba.fastjson.serializer.SerializeWriter;
import com.dzf.pub.StringUtil;

/**
 * @author wenshao[szujobs@hotmail.com]
 */
public final class DzfSerializeWriter extends SerializeWriter {
public Map<String, String> getFieldMapping() {
		return fieldMapping;
	}

private Map<String, String> fieldMapping;

    public void setFieldMapping(Map<String, String> fieldMapping) {
	this.fieldMapping = fieldMapping;
}



	@Override
	public String getFieldName(String key) {
		if(StringUtil.isEmptyWithTrim(key)==false){
			//Map<String, String>	fieldMapping=getFieldMapping();
			if(fieldMapping!=null){
				String s=fieldMapping.get(key);
				if(StringUtil.isEmptyWithTrim(s)==false)
					key=s;
			}
		}
		return key;
	}

	@Override
	public void writeFieldName(String key, boolean checkSpecial) {
		if(StringUtil.isEmptyWithTrim(key)==false){
			//Map<String, String>	fieldMapping=getFieldMapping();
			if(fieldMapping!=null){
				String s=fieldMapping.get(key);
				if(StringUtil.isEmptyWithTrim(s)==false)
					key=s;
			}
		}
		super.writeFieldName(key, checkSpecial);
	}
	
}
