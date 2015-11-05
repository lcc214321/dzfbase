package com.dzf.pub.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.dzf.model.pub.Grid;
import com.dzf.model.pub.Json;
import com.dzf.pub.SuperVO;
import com.dzf.pub.Field.FieldMapping;

public class DzfJSONSerializer extends JSONSerializer {

	public DzfJSONSerializer() {
		// TODO Auto-generated constructor stub
	}

	public DzfJSONSerializer(SerializeWriter out) {
		super(out);
		// TODO Auto-generated constructor stub
	}

	public DzfJSONSerializer(SerializeConfig config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	public DzfJSONSerializer(SerializeWriter out, SerializeConfig config) {
		super(out, config);
		// TODO Auto-generated constructor stub
	}
//	private List<Object> list=new ArrayList<Object>();
//		
//	public Object getObject() {
//		if(list.size()>0)
//			return list.get(list.size()-1);
//		else return null;
//	}
	@Override
	public void setObject(Object object,Callback cb) {
		Object o = getObj(object);
		
		Object o1=null;
		if(object==null){
//			if(list.size()>0)
//				list.remove(list.size()-1);
		}else if(object instanceof SuperVO ){
			o1=object;
		//	list.add(object);
		}else if(o!=null &&o instanceof SuperVO){
			o1=o;
		}else if(object instanceof SuperVO[]){
			Object[] os=(Object[])object;
			int len=os==null?0:os.length;
			if(len>0){
				o1=os[0];
				//list.add(os[0]);
			}
		}else if(o!=null &&o instanceof SuperVO[]){
			Object[] os=(Object[])o;
			int len=os==null?0:os.length;
			if(len>0){
				o1=os[0];
				//list.add(os[0]);
			}
		}else if(object instanceof List){
			List os=(List)object;
			int len=os==null?0:os.size();
			if(len>0){
				o1=os.get(0);
				//list.add(os[0]);
			}
		}else if(o!=null &&o instanceof List){
			List os=(List)o;
			int len=os==null?0:os.size();
			if(len>0){
				o1=os.get(0);
				//list.add(os[0]);
			}
		}
		
			if(cb!=null){
			if(o1!=null&& getWriter() instanceof DzfSerializeWriter){
				DzfSerializeWriter dw=(DzfSerializeWriter)getWriter();
				Map<String,String> m=dw.getFieldMapping();
				dw.setFieldMapping(FieldMapping.getFieldMapping((SuperVO) o1));
				cb.run();
				((DzfSerializeWriter)getWriter()).setFieldMapping(m);
			}else
				cb.run();
			
			
			//list.remove(list.size()-1);
		}
	}


	public Object getObj(Object obj){
		Object o1 = null;
		if(obj instanceof Json ){
			o1 =((Json)obj).getRows();
		}else if(obj instanceof Grid){
			o1 =((Grid)obj).getRows();
		}
		return o1;
	}
}
