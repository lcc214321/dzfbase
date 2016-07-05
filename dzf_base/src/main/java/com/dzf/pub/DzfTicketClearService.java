package com.dzf.pub;

import java.util.TimerTask;

public class DzfTicketClearService extends TimerTask {


	public DzfTicketClearService() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//清理hashmap
		SSOServerUtils.autoClearTicket();
	}
}
