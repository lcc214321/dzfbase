 package com.dzf.pub;  
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;  
 
  
public abstract class AbstractDecryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{  
      
    @Override  
    protected String convertProperty(String propertyName,String propertyValue){  
        if(isEncryptPropertyVal(propertyName)){  
            return getDecryptString(propertyValue);//调用解密方法  
        }else{  
            return propertyValue;  
        }  
    }  
      protected abstract String getDecryptString(String v) ;
    private boolean isEncryptPropertyVal(String propertyName){  
        if(propertyName.startsWith("encrypt")){  
            return true;  
        }else{  
            return false;  
        }  
    }  
}  