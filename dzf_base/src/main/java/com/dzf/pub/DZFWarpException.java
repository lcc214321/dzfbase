package com.dzf.pub;

import org.springframework.dao.DataAccessException;


/**
 * 1、接口异常,service接口实现的异常 
 * 2、其它性质异常
 */
public class DZFWarpException extends DataAccessException {

	public DZFWarpException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DZFWarpException(String msg) {
		super(msg);
	}

	public DZFWarpException(Throwable cause) {
		super("dzf异常",cause);
	}
}