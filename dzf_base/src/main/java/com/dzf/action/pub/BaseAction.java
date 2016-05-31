package com.dzf.action.pub;


import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.DzfTypeUtils;
import com.dzf.pub.IGlobalConstants;
import com.dzf.pub.SuperVO;
import com.dzf.pub.cache.CorpCache;
import com.dzf.pub.cache.UserCache;
import com.dzf.pub.util.FastjsonFilter;
import com.dzf.pub.util.JSONProcessor;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 基础ACTION,其他ACTION继承此ACTION来获得writeJson和ActionSupport的功能
 * 
 * 基本的CRUD已实现，子类继承BaseAction的时候，提供setService方法即可
 * 
 * 注解@Action后，访问地址就是命名空间+类名(全小写，并且不包括Action后缀)
 * 
 * 
 */
@ParentPackage("basePackage")
@Namespace("/")
@Action
public class BaseAction<T> extends ActionSupport {
	private static final Logger logger = Logger.getLogger(BaseAction.class);

	protected int page = 1;// 当前页
	protected int rows = 10;// 每页显示记录数
	protected String sort;// 排序字段
	protected String order = "asc";// asc/desc
	protected String q;// easyui的combo和其子类过滤时使用

	protected String id;// 主键
	protected String ids;// 主键集合，逗号分割
	protected T data;// 数据模型(与前台表单name相同，name="data.xxx")

	 public Class<T> doGetClass() {
	        Type genType = this.getClass().getGenericSuperclass();
	        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
	      return (Class<T>) params[0];
	    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * 主表和子表的相同字段的别名保持一致
	 * [0]----代表主表
	 */
	public void writeJsonByFilter(Object object,Map<String, String> m) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			String json;
			String User_Agent = getRequest().getHeader("User-Agent");
			if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1) {
				json = JSONProcessor.toJSONString(object,m,filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.BrowserCompatible);
			} else {
				json = JSONProcessor.toJSONString(object,m, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			}
			logger.info("转换后的JSON字符串：" + json);
			getResponse().setContentType("text/html;charset=utf-8");
			getResponse().getWriter().write(json);
			getResponse().getWriter().flush();
			getResponse().getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public void writeJsonByFilter(Object object, String[] includesProperties, String[] excludesProperties,SuperVO convervo) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			
			if (excludesProperties != null && excludesProperties.length > 0) {
				filter.getExcludes().addAll(Arrays.<String> asList(excludesProperties));
			}
	
			if (includesProperties != null && includesProperties.length > 0) {
				filter.getIncludes().addAll(Arrays.<String> asList(includesProperties));
			}
			logger.info("对象转JSON：要排除的属性[" + excludesProperties + "]要包含的属性[" + includesProperties + "]");
			if(convervo == null){
				Class cs=doGetClass();
				convervo = (SuperVO) cs.newInstance();
			}
			String json;
			String User_Agent = getRequest().getHeader("User-Agent");
			if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1) {
				// 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
				json = JSONProcessor.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.BrowserCompatible);
			} else {
				// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd hh24:mi:ss
				// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
				json = JSONProcessor.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			}
			logger.info("转换后的JSON字符串：" + json);
			getResponse().setContentType("text/html;charset=utf-8");
			getResponse().getWriter().write(json);
			getResponse().getWriter().flush();
			getResponse().getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public void writeJson(Object object) {
		writeJsonByFilter(object, null, null,null);
	}

	/**
	 * 转换指定的VO--json
	 * @param object
	 * @param convertvo
	 */
	public void writeJson(Object object,SuperVO convertvo) {
		writeJsonByFilter(object, null, null,convertvo);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 */
	public void writeJsonByIncludesProperties(Object object, String[] includesProperties) {
		writeJsonByFilter(object, includesProperties, null,null);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public void writeJsonByExcludesProperties(Object object, String[] excludesProperties) {
		writeJsonByFilter(object, null, excludesProperties,null);
	}

	/**
	 * 获得request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获得response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获得session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

//	/**
//	 * 获得一个对象
//	 */
//	public void getById() {
//		if (!StringUtils.isBlank(id)) {
//			writeJson(service.getById(id));
//		} else {
//			Json j = new Json();
//			j.setMsg("主键不可为空！");
//			writeJson(j);
//		}
//	}

//	/**
//	 * 查找一批对象
//	 */
//	public void find() {
//		HqlFilter hqlFilter = new HqlFilter(getRequest());
//		writeJson(service.findByFilter(hqlFilter, page, rows));
//	}

//	/**
//	 * 查找所有对象
//	 */
//	public void findAll() {
//		HqlFilter hqlFilter = new HqlFilter(getRequest());
//		writeJson(service.findByFilter(hqlFilter));
//	}

//	/**
//	 * 查找分页后的grid
//	 */
//	public void grid() {
//		Grid grid = new Grid();
//		HqlFilter hqlFilter = new HqlFilter(getRequest());
//		grid.setTotal(service.countByFilter(hqlFilter));
//		grid.setRows(service.findByFilter(hqlFilter, page, rows));
//		writeJson(grid);
//	}

//	/**
//	 * 查找grid所有数据，不分页
//	 */
//	public void gridAll() {
//		Grid grid = new Grid();
//		HqlFilter hqlFilter = new HqlFilter(getRequest());
//		List<T> l = service.findByFilter(hqlFilter);
//		grid.setTotal((long) l.size());
//		grid.setRows(l);
//		writeJson(grid);
//	}

//	/**
//	 * 获得treeGrid，treeGrid由于提供了pid的扩展，所以不分页
//	 */
//	public void treeGrid() {
//		HqlFilter hqlFilter = new HqlFilter(getRequest());
//		writeJson(service.findByFilter(hqlFilter));
//	}

//	/**
//	 * 保存一个对象
//	 */
//	public void save() {
//		Json json = new Json();
//		if (data != null) {
//			service.save(data);
//			json.setSuccess(true);
//			json.setMsg("新建成功！");
//		}
//		writeJson(json);
//	}

//	/**
//	 * 更新一个对象
//	 */
//	public void update() {
//		Json json = new Json();
//		String reflectId = null;
//		try {
//			if (data != null) {
//				reflectId = (String) FieldUtils.readField(data, "id", true);
//			}
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		if (!StringUtils.isBlank(reflectId)) {
//			T t = service.getById(reflectId);
//			BeanUtils.copyNotNullProperties(data, t, new String[] { "createdatetime" });
//			service.update(t);
//			json.setSuccess(true);
//			json.setMsg("更新成功！");
//		}
//		writeJson(json);
//	}

//	/**
//	 * 删除一个对象
//	 */
//	public void delete() {
//		Json json = new Json();
//		if (!StringUtils.isBlank(id)) {
//			T t = service.getById(id);
//			service.delete(t);
//			json.setSuccess(true);
//			json.setMsg("删除成功！");
//		}
//		writeJson(json);
//	}
	
	/**
	 * 行编辑获取AJAX传入的data，转换SUPERVO
	 * （仅支持单行编辑模式）
	 * */
	public T getActionVO(Class classz){
		
		
//		Map vmap = (HashMap<String,String[]>)getRequest().getParameterMap();
//		Enumeration<String> e = getRequest().getParameterNames();
//		SuperVO da = null;
		try {
			SuperVO vo= (SuperVO) classz.newInstance();
			vo=DzfTypeUtils.cast(getRequest(), vo);
			data=(T) vo;
//			while(e.hasMoreElements()){
//				String namekey =e.nextElement();
//				String[] values =getRequest().getParameterValues(namekey);
//					((SuperVO)data).setAttributeValue(namekey, values==null?null:values[0]);	
//				}
			return data;
			
		} catch (Exception e1) {
			logger.error(e1);
			e1.printStackTrace();
		} 
		return null;
	}

	/**
	 * 获取登录公司信息
	 * */
	public CorpVO getLoginCorpInfo(){
		String corp=(String) getRequest().getSession().getAttribute("login_corp");
		CorpVO cvo=CorpCache.getInstance().get(null, corp);
		return cvo;
	}
	
	/**
	 * 获取登录公司信息
	 * */
	public UserVO getLoginUserInfo(){
		String corp=(String) getRequest().getSession().getAttribute(IGlobalConstants.login_corp);
		String userid=(String) getRequest().getSession().getAttribute(IGlobalConstants.login_user);
		return UserCache.getInstance().get(userid, corp);
	}
	
	/**
	 * 获取登录公司信息
	 * */
	public String getLoginDate(){
		return (String)getRequest().getSession().getAttribute("login_date");
	}
	
}
