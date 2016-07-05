package com.dzf.pub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.UrlBase64;

import com.dzf.pub.cache.RsaKeyCache;
import com.dzf.pub.cache.TicketCache;
import com.dzf.pub.framework.rsa.RSACoder;
import com.dzf.pub.session.DZFSession;

public class SSOServerUtils {

	private Logger log = Logger.getLogger(this.getClass());
	
	
	private static Map<String, DZFSession> TICKET_AND_NAME = new ConcurrentHashMap<String, DZFSession>(); //key：ticket, value :pk_user

       
	public static void putTicket(String ticket, DZFSession dzfsession)
	{
		boolean bReturn = TicketCache.getInstance().put(ticket, dzfsession);
		if (bReturn == false)
		{
			TICKET_AND_NAME.put(ticket, dzfsession);
		}
	}
	public static DZFSession getTicket(String ticket)
	{
		DZFSession session = TicketCache.getInstance().get(ticket);
		if (session == null)
		{
			session = TICKET_AND_NAME.get(ticket);
			if (session != null)
			{
				TICKET_AND_NAME.remove(ticket);	//每张票只会使用一次
			}
		}
		return session;
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
    		DZFSession session = TICKET_AND_NAME.get(key);
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


}