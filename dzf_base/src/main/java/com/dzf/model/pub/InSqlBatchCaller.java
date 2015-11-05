/*
 * Created on 2005-6-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dzf.model.pub;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.dzf.pub.BusinessException;

/**
 * @author liujian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InSqlBatchCaller
{
	ArrayList pks = new ArrayList();
	public final int GROUP_COUNT = 500;
	int group_size = GROUP_COUNT;
	
	/**
	 * @param pks
	 */
	public InSqlBatchCaller(ArrayList pks)
	{
		super();
		if(pks!=null);
			this.pks = pks;
	}	
	
	/**
	 * @param pks
	 * @param group_size
	 */
	public InSqlBatchCaller(ArrayList pks, int group_size)
	{
		super();
		if(pks!=null);
			this.pks = pks;
		if(group_size>10)
			this.group_size = group_size;
		else
			this.group_size = GROUP_COUNT;
	}
	
	/**
	 * @param pks
	 */
	public InSqlBatchCaller(String[] pks)
	{
		if(pks!=null)
			this.pks.addAll(Arrays.asList(pks));
	}

	public InSqlBatchCaller(String[] pks, int group_size)
	{
		if(pks!=null)
			this.pks.addAll(Arrays.asList(pks));
		if(group_size>10)
			this.group_size = group_size;
		else
			this.group_size = GROUP_COUNT;		
	}

	public Object execute(IInSqlBatchCallBack callBack) throws BusinessException,SQLException
	{
//		Object result = null;
//		int n = 1;
//		for (int i = 0; i < pks.size(); i++,n++)
//		{
//			if(n==group_size||i==pks.size()-1)
//			{
//				StringBuffer buf = new StringBuffer();
//				buf.append("(");
//				for (int j = 0; j < n; j++)
//				{
//					buf.append("'");
//					buf.append(pks.get(i-n+j+1));
//					if(j==n-1)
//						buf.append("')");
//					else
//						buf.append("',");
//					
//				}
//				result = callBack.doWithInSql(buf.toString());
//				n = 0;
//			}
//		}
//		return result;
		Object result = null;
		int n = 0;
		StringBuffer buf = null;
		for (int i = 0; i < pks.size(); i++)
		{
			n++;
			if(n==1)
			{
				buf = new StringBuffer();
				buf.append("(");
			}
			buf.append("'");
			buf.append(pks.get(i));
			if(n!=group_size&&i!=pks.size()-1)
			{
				buf.append("',");
			}
			else
			{
				buf.append("')");
				result = callBack.doWithInSql(buf.toString());
				n = 0;
			}
			
		}
		return result;
	}		
		
}
