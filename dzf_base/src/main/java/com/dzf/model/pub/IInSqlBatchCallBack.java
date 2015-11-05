/*
 * Created on 2005-6-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dzf.model.pub;

import java.sql.SQLException;

import com.dzf.pub.BusinessException;

/**
 * @author liujian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IInSqlBatchCallBack
{
	public Object doWithInSql(String inSql) throws BusinessException,SQLException;
}
