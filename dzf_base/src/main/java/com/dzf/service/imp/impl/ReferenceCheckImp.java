/*
 * Created on 2005-10-25
 *
 */
package com.dzf.service.imp.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzf.dao.bs.SingleObjectBO;
import com.dzf.pub.BusinessException;
import com.dzf.pub.lang.DZFBoolean;
import com.dzf.service.imp.IReferenceCheck;


/**
 * @author liujian 引用检查的服务实现类. 调用NewReferenceManagerDMO.其实可以让后者直接实现
 *         IReferenceCheck.
 */
@Service("refchecksrv")
public class ReferenceCheckImp implements IReferenceCheck {

	private SingleObjectBO singleObjectBO = null;

	public SingleObjectBO getSingleObjectBO() {
		return singleObjectBO;
	}

	@Autowired
	public void setSingleObjectBO(SingleObjectBO singleObjectBO) {
		this.singleObjectBO = singleObjectBO;
	}
	
	public Set<String> getBasePkReferencedInCorp(String tableName,
			List<String> basePks, String pk_corp) throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).getReferencedBasePksInCorp(
				tableName, basePks, pk_corp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.itf.uap.bd.refcheck.IReferenceCheck#getIsReferencedByKeys(java.lang.String,
	 *      java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	public HashMap getIsReferencedByKeys(String tableName, String[] keys)
			throws BusinessException {
		if (tableName == null)
			throw new IllegalArgumentException("talbeName cann't be null");
		if (keys == null || keys.length == 0)
			return new HashMap();
		HashMap result = new HashMap();
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			boolean referenceFlag = isReferenced(tableName, key);
			result.put(key, DZFBoolean.valueOf(referenceFlag));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public HashMap getIsReferencedByKeysWhenModify(String tableName,
			String[] keys) throws BusinessException {
		if (tableName == null)
			throw new IllegalArgumentException("talbeName cann't be null");
		if (keys == null || keys.length == 0)
			return new HashMap();
		HashMap result = new HashMap();
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			boolean referenceFlag = isReferencedWhenModify(tableName, key);
			result.put(key, DZFBoolean.valueOf(referenceFlag));
		}
		return result;
	}

	public String[] getReferencedKeys(String tableName, String[] keys)
			throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).getReferencedKeys(tableName, keys,
				false);
	}

	public String[] getReferencedKeysWhenModify(String tableName, String[] keys)
			throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).getReferencedKeys(tableName, keys,
				true);
	}

	public boolean isBasePkReferencedInCorp(String tableName,
			List<String> basePks, String pk_corp) throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).isBasePksReferencedInCorp(
				tableName, pk_corp, basePks, false);
	}

	public boolean isBasePkReferencedInCorp(String tableName, String basePk,
			String pk_corp) throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).isBasePkReferencedInCorp(tableName,
				pk_corp, basePk, false);
	}

	public boolean isBasePkReferencedInCorp(String tableName, String basePk,
			String pk_corp, String[] excludedTableNames)
			throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).isBasePkReferencedInCorp(tableName,
				pk_corp, basePk, excludedTableNames, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.itf.uap.bd.refcheck.IReferenceCheck#isReferenced(java.lang.String,
	 *      java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	public boolean isReferenced(String tableName, ArrayList keys)
			throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO())
				.isReferenced(tableName, keys, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.itf.uap.bd.refcheck.IReferenceCheck#isReferenced(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isReferenced(String tableName, String key)
			throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).isReferenced(tableName, key, false);
	}

	public boolean isReferenced(String tableName, String key,
			String[] excludedTableNames) throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).isReferenced(tableName, key,
				excludedTableNames, false);
	}

	@SuppressWarnings("unchecked")
	public boolean isReferencedWhenModify(String tableName, ArrayList keys)
			throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).isReferenced(tableName, keys, true);
	}

	public boolean isReferencedWhenModify(String tableName, String key)
			throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).isReferenced(tableName, key, true);
	}

	public boolean isReferencedWhenModify(String tableName, String key,
			String[] excludedTableNames) throws BusinessException {
		return new NewReferenceManagerDMO(getSingleObjectBO()).isReferenced(tableName, key,
				excludedTableNames, true);
	}

}
