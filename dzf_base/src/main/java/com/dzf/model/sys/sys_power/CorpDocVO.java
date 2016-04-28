package com.dzf.model.sys.sys_power;

import com.dzf.pub.SuperVO;
import com.dzf.pub.Field.FieldAlias;
import com.dzf.pub.lang.DZFDateTime;

/**
 * 
 * 附件
 *
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class CorpDocVO extends SuperVO {

	@FieldAlias("doc_id")
	private String pk_doc; // 主键

	@FieldAlias("corp_id")
	private String pk_corp; // 客户主键

	@FieldAlias("doc_name")
	private String docName; // 附件名称(中文)

	@FieldAlias("doc_temp")
	private String docTemp; // 附件名称(下载用 非中文)

	@FieldAlias("doc_owner")
	private String docOwner; // 上传人

	@FieldAlias("doc_time")
	private DZFDateTime docTime; // 上传时间

	private Integer dr; // 删除标记

	private DZFDateTime ts; // 时间

	private String vfilepath;// 文件存储路径

	public String getDocTemp() {
		return docTemp;
	}

	public void setDocTemp(String docTemp) {
		this.docTemp = docTemp;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public DZFDateTime getDocTime() {
		return docTime;
	}

	public void setDocTime(DZFDateTime docTime) {
		this.docTime = docTime;
	}

	public String getDocOwner() {
		return docOwner;
	}

	public void setDocOwner(String docOwner) {
		this.docOwner = docOwner;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public DZFDateTime getTs() {
		return ts;
	}

	public void setTs(DZFDateTime ts) {
		this.ts = ts;
	}

	public String getPk_doc() {
		return pk_doc;
	}

	public void setPk_doc(String pk_doc) {
		this.pk_doc = pk_doc;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getVfilepath() {
		return vfilepath;
	}

	public void setVfilepath(String vfilepath) {
		this.vfilepath = vfilepath;
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_corp";
	}

	@Override
	public String getPKFieldName() {
		return "pk_doc";
	}

	@Override
	public String getTableName() {
		return "ynt_corpdoc";
	}
}
