package com.dzf.pub.cache;

import java.io.IOException;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.pub.StringUtil;
import com.dzf.pub.SuperVO;
import com.dzf.pub.Field.FieldAlias;
import com.dzf.pub.lang.DZFBoolean;
import com.dzf.pub.lang.DZFDate;
import com.dzf.pub.lang.DZFDateTime;
import com.dzf.pub.lang.DZFDouble;

public class CorpSerializable implements IDzfSerializable<CorpVO> {

	public CorpSerializable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSerializable(CorpVO svo, NetObjectOutputStream nos) throws IOException {
		write(nos,svo.getBegindate());
		DZFBoolean dzfb=svo.getBbuildic();
		write(nos,dzfb);
	
		write(nos,svo.getIcbegindate());////库存启用日期
		
		write(nos,svo.getHoldflag());
		write(nos,svo.getIshasaccount());
		write(nos,svo.getIsseal());
		nos.write(svo.getPk_corp().toString().getBytes());
		write(nos,svo.getPk_currency());
		nos.write(svo.getTaxpayertype());
		write(nos,svo.getIsaccountcorp());	
		write(nos,svo.getIsdatacorp());
		write(nos,svo.getIscurr());
		write(nos,svo.getUnitcode());
		write(nos,svo.getUnitname());
		write(nos,svo.getBusibegindate());
		write(nos,svo.getBusienddate());
	
	
	}

	@Override
	public CorpVO getSerializable( NetObjectInputStream nos) throws IOException {
		CorpVO cvo=new CorpVO();
		cvo.setBegindate(reader(nos));
		cvo.setBbuildic(readerDZFBoolean(nos));
		
		cvo.setIcbegindate(reader(nos));
		cvo.setHoldflag(readerDZFBoolean(nos));
	cvo.setIshasaccount(readerDZFBoolean(nos));
	cvo.setIsseal(readerDZFBoolean(nos));
	cvo.setPk_corp(readerString(nos,6));
	cvo.setPk_currency(readerString(nos, -1));	
	cvo.setTaxpayertype(nos.readInt());

		cvo.setIsaccountcorp(readerDZFBoolean(nos));	
		cvo.setIsdatacorp(readerDZFBoolean(nos));
		cvo.setIscurr(readerDZFBoolean(nos));
		cvo.setUnitcode(readerString(nos, -1));
	
		cvo.setUnitname(readerString(nos, -1));
		cvo.setBusibegindate(reader(nos));
		cvo.setBusienddate(reader(nos));
		return cvo;
	}
	private void write(NetObjectOutputStream nos,String str) throws IOException{
		int len=str==null?0:str.length();
		if(len>0)
			nos.write(str.getBytes());
	}
	private void write(NetObjectOutputStream nos,DZFBoolean b) throws IOException{
			if(b==null)b=DZFBoolean.FALSE;
			nos.writeBoolean(b.booleanValue());
			//nos.write(b.toString().getBytes());
	}
	private void write(NetObjectOutputStream nos,DZFDate b) throws IOException{
		long l=0;
		if(b!=null)l=b.toDate().getTime();
		nos.writeLong(l);
	
}
	private DZFDate reader(NetObjectInputStream nos) throws IOException{
		long l=nos.readLong();
	return new DZFDate(l);
	
}
	private DZFBoolean readerDZFBoolean(NetObjectInputStream nos) throws IOException{
	return new DZFBoolean(nos.readBoolean());
	}
	private String readerString(NetObjectInputStream nos,int len) throws IOException{
		if(len<0)len=nos.readInt();
		byte[] bs=new byte[len];
		nos.read(bs);
	return new String(bs);
	}
}
