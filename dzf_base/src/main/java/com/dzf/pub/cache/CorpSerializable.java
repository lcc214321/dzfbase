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

public class CorpSerializable extends AbstractSerializable<CorpVO> {

	public CorpSerializable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSerializable(CorpVO svo, NetObjectOutputStream nos) throws IOException {
		write(nos,svo.getBegindate());
		
		DZFBoolean dzfb=svo.getBbuildic();
//		write(nos,dzfb);
//		write(nos,svo.getHoldflag());
//		write(nos,svo.getIshasaccount());
//		write(nos,svo.getIsseal());
//		write(nos,svo.getIsaccountcorp());	
//		write(nos,svo.getIsdatacorp());
//		write(nos,svo.getIscurr());
		
		writeBytes(nos, dzfb,svo.getHoldflag(),svo.getIshasaccount(),svo.getIsseal(),svo.getIsaccountcorp(),svo.getIsdatacorp(),svo.getIscurr());
		
		write(nos,svo.getIcbegindate());////库存启用日期
		nos.write(svo.getPk_corp().toString().getBytes());
		write(nos,svo.getPk_currency());
		writeByte(nos,svo.getTaxpayertype());
		write(nos,svo.getUnitcode());
		write(nos,svo.getUnitname());
		write(nos,svo.getBusibegindate());
		write(nos,svo.getBusienddate());
		write(nos,svo.getPhone1());
		write(nos,svo.getPhone2());
		write(nos,svo.getPhone3());
	
	}

	@Override
	public CorpVO getSerializable( NetObjectInputStream nos) throws IOException {
		CorpVO cvo=new CorpVO();
		cvo.setBegindate(reader(nos));
		DZFBoolean[] bs=readerDZFBooleans(nos);
		cvo.setBbuildic(bs[0]);
		cvo.setHoldflag(bs[1]);
	cvo.setIshasaccount(bs[2]);
	cvo.setIsseal(bs[3]);
	cvo.setIsaccountcorp(bs[4]);	
	cvo.setIsdatacorp(bs[5]);
	cvo.setIscurr(bs[6]);
//		cvo.setBbuildic(readerDZFBoolean(nos));
//		cvo.setHoldflag(readerDZFBoolean(nos));
//	cvo.setIshasaccount(readerDZFBoolean(nos));
//	cvo.setIsseal(readerDZFBoolean(nos));
//	cvo.setIsaccountcorp(readerDZFBoolean(nos));	
//	cvo.setIsdatacorp(readerDZFBoolean(nos));
//	cvo.setIscurr(readerDZFBoolean(nos));
	
	cvo.setIcbegindate(reader(nos));
	cvo.setPk_corp(readerString(nos,6));
	cvo.setPk_currency(readerString(nos, -1));	
	cvo.setTaxpayertype((int) nos.readByte());

	
		cvo.setUnitcode(readerString(nos, -1));
	
		cvo.setUnitname(readerString(nos, -1));
		cvo.setBusibegindate(reader(nos));
		cvo.setBusienddate(reader(nos));
		cvo.setPhone1(readerString(nos, -1));
		cvo.setPhone2(readerString(nos, -1));
		cvo.setPhone3(readerString(nos, -1));
		
		return cvo;
	}
	
}
