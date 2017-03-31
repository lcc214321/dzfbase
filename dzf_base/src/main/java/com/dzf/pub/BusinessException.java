package com.dzf.pub;

import java.io.Serializable;

import org.apache.log4j.Logger;


/**
 * 正常的业务提示信息提示到前台
 *
 */

public class BusinessException extends DZFWarpException {
	
	static final long serialVersionUID = -35466L;
	
	static Logger logger = Logger.getLogger(BusinessException.class);

    private String hint;

	private String errorCodeString = ""; 


    public BusinessException(String s) {
        super(s);
        logger.error(s);
    }
    

    public BusinessException(String s,String errorCode) {
        super(s);
        setErrorCodeString(errorCode);
    }


    public java.lang.String getHint() {
        return hint;
    }


    public void setHint(java.lang.String newHint) {
        hint = newHint;
    }


	public String getErrorCodeString() {
		return errorCodeString;
	}

	public void setErrorCodeString(String errorCode) {
		this.errorCodeString = errorCode;
	}

}
