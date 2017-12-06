package com.dzf.pub.cache;

import java.io.IOException;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.pub.lang.DZFBoolean;

public class CorpSerializable extends AbstractSerializable<CorpVO> {

	public CorpSerializable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSerializable(CorpVO svo, NetObjectOutputStream nos) throws IOException {
		writeDate(nos,svo.getBegindate());
		
		DZFBoolean dzfb=svo.getBbuildic();
//		write(nos,dzfb);
//		write(nos,svo.getHoldflag());
//		write(nos,svo.getIshasaccount());
//		write(nos,svo.getIsseal());
//		write(nos,svo.getIsaccountcorp());	
//		write(nos,svo.getIsdatacorp());
//		write(nos,svo.getIscurr());
		
		writeBooleans(nos, dzfb,svo.getHoldflag(),svo.getIshasaccount(),svo.getIsseal(),svo.getIsaccountcorp(),svo.getIsdatacorp(),svo.getIscurr(),svo.getIschannel(),svo.getIsfactory());
		
		writeDate(nos,svo.getIcbegindate());////库存启用日期
		write(nos,svo.getPk_corp());
		write(nos,svo.getPk_currency());
		writeInt(nos,svo.getTaxpayertype());
		write(nos,svo.getUnitcode());
		write(nos,svo.getInnercode());
		write(nos,svo.getUnitname());
		writeDate(nos,svo.getBusibegindate());
		writeDate(nos,svo.getBusienddate());
		write(nos,svo.getPhone1());
		write(nos,svo.getPhone2());
		write(nos,svo.getPhone3());
		write(nos,svo.getCorptype());
		write(nos,svo.getChargedeptname());
		write(nos,svo.getDef10());
		writeInt(nos,svo.getCompanyproperty());
		write(nos,svo.getAccountfactoryid());
		write(nos,svo.getIndustry());
		writeInt(nos,svo.getIbuildicstyle());
		write(nos,svo.getDef12());//票通是否认证
		write(nos,svo.getForeignname());//销售代表，存的是汉字
		write(nos,svo.getForeignid());//代理商pk
		write(nos,svo.getAccountcoderule());//科目编码规则
		write(nos,svo.getVyhzc());//优惠政策
		//
		writeDate(nos,svo.getDrzsj());//认证日期
		write(nos,svo.getVschlnd());//首次获利年度
		write(nos,svo.getDef19());//税负预警

		write(nos, svo.getDef4());//单据图片处理方式
		writeInt(nos, svo.getVprovince());//省
		writeInt(nos, svo.getIcostforwardstyle());//成本结转类型
		write(nos, svo.getFathercorp());//上级id
	}

	@Override
	public CorpVO getSerializable( NetObjectInputStream nos) throws IOException {
		CorpVO cvo=new CorpVO();
		cvo.setBegindate(readDate(nos));
		DZFBoolean[] bs=readerDZFBooleans(nos);
		cvo.setBbuildic(bs[0]);
		cvo.setHoldflag(bs[1]);
	cvo.setIshasaccount(bs[2]);
	cvo.setIsseal(bs[3]);
	cvo.setIsaccountcorp(bs[4]);	
	cvo.setIsdatacorp(bs[5]);
	cvo.setIscurr(bs[6]);
	cvo.setIschannel(bs[7]);
	cvo.setIsfactory(bs[8]);
//		cvo.setBbuildic(readerDZFBoolean(nos));
//		cvo.setHoldflag(readerDZFBoolean(nos));
//	cvo.setIshasaccount(readerDZFBoolean(nos));
//	cvo.setIsseal(readerDZFBoolean(nos));
//	cvo.setIsaccountcorp(readerDZFBoolean(nos));	
//	cvo.setIsdatacorp(readerDZFBoolean(nos));
//	cvo.setIscurr(readerDZFBoolean(nos));
	
	cvo.setIcbegindate(readDate(nos));
	cvo.setPk_corp(readerString(nos,-1));
	cvo.setPk_currency(readerString(nos, -1));	
	cvo.setTaxpayertype(readToInt(nos, -1));

	
		cvo.setUnitcode(readerString(nos, -1));
		cvo.setInnercode(readerString(nos, -1));
		cvo.setUnitname(readerString(nos, -1));
		cvo.setBusibegindate(readDate(nos));
		cvo.setBusienddate(readDate(nos));
		cvo.setPhone1(readerString(nos, -1));
		cvo.setPhone2(readerString(nos, -1));
		cvo.setPhone3(readerString(nos, -1));
		cvo.setCorptype(readerString(nos, -1));
		cvo.setChargedeptname(readerString(nos, -1));
		cvo.setDef10(readerString(nos, -1));
			cvo.setCompanyproperty(readToInt(nos, -1));
		cvo.setAccountfactoryid(readerString(nos, -1));
		cvo.setIndustry(readerString(nos, -1));	
		cvo.setIbuildicstyle(readToInt(nos, -1));
		cvo.setDef12(readerString(nos, -1));	//票通是否认证
		cvo.setForeignname(readerString(nos, -1));
		cvo.setForeignid(readerString(nos, -1));
		cvo.setAccountcoderule(readerString(nos, -1));
		cvo.setVyhzc(readerString(nos, -1));//优惠政策
		
		cvo.setDrzsj(readDate(nos));//认证日期
		cvo.setVschlnd(readerString(nos, -1));//首次获利年度
		cvo.setDef19(readerString(nos, -1));//税负预警
		
		cvo.setDef4(readerString(nos, -1));
		cvo.setVprovince(readToInt(nos, -1));//省
		cvo.setIcostforwardstyle(readToInt(nos, -1));//成本结转类型
		cvo.setFathercorp(readerString(nos, -1));//上级id
		return cvo;
	}
	
}
