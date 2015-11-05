package com.dzf.pub;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;




public class BusinessException extends DataAccessException {
	
	static final long serialVersionUID = -35466L;
	
	Logger logger = Logger.getLogger(this.getClass());

    private String hint;

	private String errorCodeString = ""; 
 

//    public BusinessException() {
//        super();
//    }


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


    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }


    public BusinessException(Throwable cause) {
        super("",cause);
    }

	public String getErrorCodeString() {
		return errorCodeString;
	}

	public void setErrorCodeString(String errorCode) {
		this.errorCodeString = errorCode;
	}

}
