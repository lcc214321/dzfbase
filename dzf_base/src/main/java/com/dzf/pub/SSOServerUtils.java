package com.dzf.pub;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.UrlBase64;

import com.dzf.model.pub.DZFSessionVO;
import com.dzf.pub.cache.RsaKeyCache;
import com.dzf.pub.cache.TicketCache;
import com.dzf.pub.framework.rsa.RSACoder;


public class SSOServerUtils {

	private Logger log = Logger.getLogger(this.getClass());
	
	
	private static Map<String, DZFSessionVO> TICKET_AND_NAME = new ConcurrentHashMap<String, DZFSessionVO>(); //key：ticket, value :pk_user

	private static Boolean isUsingSSOServer = null;
       
	public static void putTicket(String ticket, DZFSessionVO dzfsessionvo)
	{
		boolean bReturn = TicketCache.getInstance().put(ticket, dzfsessionvo);
		if (bReturn == false)
		{
			TICKET_AND_NAME.put(ticket, dzfsessionvo);
		}
	}
	public static DZFSessionVO getTicket(String ticket)
	{
		DZFSessionVO dzfsessionvo = TicketCache.getInstance().get(ticket);
		if (dzfsessionvo == null)
		{
			dzfsessionvo = TICKET_AND_NAME.get(ticket);
			if (dzfsessionvo != null)
			{
				TICKET_AND_NAME.remove(ticket);	//每张票只会使用一次
			}
		}
		return dzfsessionvo;
	}
    public static String encryptByPublicKey(String str) 
    {
    	try {
    		return new String(UrlBase64.encode(RSACoder.encryptByPublicKey(str.getBytes(), RsaKeyCache.getInstance().getPublicKey())));
    	}
    	catch (Exception e)
    	{
    		Logger log = Logger.getLogger(new SSOServerUtils().getClass());
    		log.error(e);
    		
    	}
    	return null;
    }
    public static String decryptByPrivatekey(String str)
    {
    	try {

    		return new String(RSACoder.decryptByPrivateKey(UrlBase64.decode(str), RsaKeyCache.getInstance().getPrivateKey()));
    		
    	}
    	catch (Exception e)
    	{
    		Logger log = Logger.getLogger(new SSOServerUtils().getClass());
    		log.error(e);
    	}
    	return null;
    }
    public static void autoClearTicket()
    {
    	List<String> removekeys = new ArrayList<String>();
    	for (String key : TICKET_AND_NAME.keySet())
    	{
    		DZFSessionVO session = TICKET_AND_NAME.get(key);
    		if (System.currentTimeMillis() -session.getLasttime() > 30 * 60 * 1000) //清理超过30分钟的ticket
    		{
    			removekeys.add(key);
    		}
    	}
    	if (removekeys.size() > 0)
    	{
    		for (String key : removekeys)
    		{
    			TICKET_AND_NAME.remove(key);
    		}
    	}
    }
    public static Boolean useSSOServer()
    {
    	if (isUsingSSOServer == null)
    	{
	    	String path = new SSOServerUtils().getClass().getClassLoader().getResource("").getPath();
			
			
			String regex = "(WEB-INF)[\\/\\\\]{1}(classes)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(path);
			
			if(m.find()) {
				
				path = path.substring(0, m.start());
			}
	
			String os = System.getProperties().getProperty("os.name");
			if (path.startsWith("/") && os != null && os.toLowerCase().startsWith("win"))
			{
				path = path.substring(1);
			}
			File f = new File(path + "login.jsp");
	
			isUsingSSOServer = (f.exists() == false);
    	}
    	return isUsingSSOServer;
    }

}