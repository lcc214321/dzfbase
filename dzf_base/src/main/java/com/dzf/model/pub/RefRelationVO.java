package com.dzf.model.pub;

import com.dzf.pub.SuperVO;
import com.dzf.pub.lang.DZFDateTime;
/**
 * 
 * 
 * 创建日期:(2006-6-8)
 * @author:
 */
public class RefRelationVO extends SuperVO {

	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = -8344922859066522121L;
	public String crefid;
	public Integer dr;
	public String referencedtablekey;
	public String referencedtablename;
	public String referencingtablecolumn;
	public String referencingtablename;
	public String referencingTablePkValue;
	public String referencedKeyValue;
	public DZFDateTime ts;
	
	
	
	public String getReferencedKeyValue() {
		return referencedKeyValue;
	}
	public void setReferencedKeyValue(String referencedKeyValue) {
		this.referencedKeyValue = referencedKeyValue;
	}

	
	public String getReferencingTablePkValue() {
		return referencingTablePkValue;
	}
	public void setReferencingTablePkValue(String referencingTablePkValue) {
		this.referencingTablePkValue = referencingTablePkValue;
	}
/**
 * 属性dr的Getter方法.
 *
 * 创建日期:(2006-6-8)
 * @return Integer
 */
public Integer getDr() {
	return dr;
}
/**
 * 属性referencedtablekey的Getter方法.
 *
 * 创建日期:(2006-6-8)
 * @return String
 */
public String getReferencedtablekey() {
	return referencedtablekey;
}
/**
 * 属性referencedtablename的Getter方法.
 *
 * 创建日期:(2006-6-8)
 * @return String
 */
public String getReferencedtablename() {
	return referencedtablename;
}
/**
 * 属性referencingtablecolumn的Getter方法.
 *
 * 创建日期:(2006-6-8)
 * @return String
 */
public String getReferencingtablecolumn() {
	return referencingtablecolumn;
}
/**
 * 属性referencingtablename的Getter方法.
 *
 * 创建日期:(2006-6-8)
 * @return String
 */
public String getReferencingtablename() {
	return referencingtablename;
}
/**
 * 属性ts的Getter方法.
 *
 * 创建日期:(2006-6-8)
 * @return UFDateTime
 */
public DZFDateTime getTs() {
	return ts;
}
/**
 * 属性dr的setter方法.
 *
 * 创建日期:(2006-6-8)
 * @param newDr Integer
 */
public void setDr(Integer newDr) {

	dr = newDr;
}
/**
 * 属性referencedtablekey的setter方法.
 *
 * 创建日期:(2006-6-8)
 * @param newReferencedtablekey String
 */
public void setReferencedtablekey(String newReferencedtablekey) {

	referencedtablekey = newReferencedtablekey;
}
/**
 * 属性referencedtablename的setter方法.
 *
 * 创建日期:(2006-6-8)
 * @param newReferencedtablename String
 */
public void setReferencedtablename(String newReferencedtablename) {

	referencedtablename = newReferencedtablename;
}
/**
 * 属性referencingtablecolumn的setter方法.
 *
 * 创建日期:(2006-6-8)
 * @param newReferencingtablecolumn String
 */
public void setReferencingtablecolumn(String newReferencingtablecolumn) {

	referencingtablecolumn = newReferencingtablecolumn;
}
/**
 * 属性referencingtablename的setter方法.
 *
 * 创建日期:(2006-6-8)
 * @param newReferencingtablename String
 */
public void setReferencingtablename(String newReferencingtablename) {

	referencingtablename = newReferencingtablename;
}
/**
 * 属性ts的setter方法.
 *
 * 创建日期:(2006-6-8)
 * @param newTs UFDateTime
 */
public void setTs(DZFDateTime newTs) {

	ts = newTs;
}
/**
 * 验证对象各属性之间的数据逻辑正确性.
 *
 * 创建日期:(2006-6-8)
 * @exception nc.vo.pub.ValidationException 如果验证失败,抛出
 *     ValidationException,对错误进行解释.
 */
//public void validate() throws ValidationException {
//
//	ArrayList errFields = new ArrayList(); // errFields record those null fields that cannot be null.
//	// 检查是否为不允许空的字段赋了空值,你可能需要修改下面的提示信息:
//	if (crefid == null) {
//		errFields.add(new String("crefid"));
//	}
//	// construct the exception message:
//	StringBuffer message = new StringBuffer();
//	message.append("下列字段不能为空:");
//	if (errFields.size() > 0) {
//		String[] temp = (String[]) errFields.toArray(new String[0]);
//		message.append(temp[0]);
//		for ( int i= 1; i < temp.length; i++ ) {
//			message.append(",");
//			message.append(temp[i]);
//		}
//		// throw the exception:
//		throw new NullFieldException(message.toString());
//	}
//}
/**
 * <p>取得父VO主键字段.
 * <p>
 * 创建日期:(2006-6-8)
 * @return java.lang.String
 */
public java.lang.String getParentPKFieldName() {

	return  null;
}
/**
 * <p>取得表主键.
 * <p>
 * 创建日期:(2006-6-8)
 * @return java.lang.String
 */
public java.lang.String getPKFieldName() {

	return  "crefid";
}
/**
 * <p>返回表名称.
 * <p>
 * 创建日期:(2006-6-8)
 * @return java.lang.String
 */
public java.lang.String getTableName() {

	return "bd_ref_relation";
}
/**
 * 使用主键字段进行初始化的构造子.
 *
 * 创建日期:(2006-6-8)
 */
public RefRelationVO() {
	super();
}
/**
 * 使用主键进行初始化的构造子.
 *
 * 创建日期:(2006-6-8)
 * @param Crefid 主键值
 */
public RefRelationVO(String newCrefid) {
	super();

	// 为主键字段赋值:
	crefid = newCrefid;
}
/**
 * 返回对象标识,用来唯一定位对象.
 *
 * 创建日期:(2006-6-8)
 * @return String
 */
public String getPrimaryKey() {

	return crefid;
}
/**
 * 设置对象标识,用来唯一定位对象.
 *
 * 创建日期:(2006-6-8)
 * @param crefid String 
 */
public void setPrimaryKey(String newCrefid) {

	crefid = newCrefid;
}
/**
 * 返回数值对象的显示名称.
 *
 * 创建日期:(2006-6-8)
 * @return java.lang.String 返回数值对象的显示名称.
 */
public String getEntityName() {

	return "RefRelation";
}
}