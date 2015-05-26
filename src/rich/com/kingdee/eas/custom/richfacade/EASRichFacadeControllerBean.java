package com.kingdee.eas.custom.richfacade;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.richbase.ExamTypeInfo;
import com.kingdee.eas.custom.richbase.ReceTypeInfo;
import com.kingdee.eas.custom.richbase.SaleTypeInfo;
import com.kingdee.eas.custom.richinf.IRichExamTempTab;
import com.kingdee.eas.custom.richinf.IRichExamed;
import com.kingdee.eas.custom.richinf.RichExamTempTabCollection;
import com.kingdee.eas.custom.richinf.RichExamTempTabFactory;
import com.kingdee.eas.custom.richinf.RichExamTempTabInfo;
import com.kingdee.eas.custom.richinf.RichExamedEntryDjrentryInfo;
import com.kingdee.eas.custom.richinf.RichExamedEntryFactory;
import com.kingdee.eas.custom.richinf.RichExamedEntryInfo;
import com.kingdee.eas.custom.richinf.RichExamedFactory;
import com.kingdee.eas.custom.richinf.RichExamedInfo;
import com.kingdee.eas.custom.richinf.client.Utils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.hr.rec.util.DateUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class EASRichFacadeControllerBean extends AbstractEASRichFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.custom.richfacade.EASRichFacadeControllerBean");

	protected String[] _saveTempData(Context ctx, String xmlString) throws BOSException {
		super._saveTempData(ctx, xmlString);
		String[] strReturn = new String[] {"Y", "", ""};
		Document document;
		try {
			document = DocumentHelper.parseText(xmlString);
			Element root = document.getRootElement();
			List bills = root.elements("TempTable");
			if(bills.size() < 1) {
				logger.info("体检明细xml字符串不包含数据或者xml字符串标签不正确");
				return new String[] {"N", "002", "体检明细xml字符串不包含数据或者xml字符串标签不正确"};
			}
			
			Iterator iterator = bills.iterator();
			CoreBaseCollection  tempColl = new CoreBaseCollection();
			IRichExamTempTab itemp = RichExamTempTabFactory.getLocalInstance(ctx);
			StringBuilder repeat = new StringBuilder("");
			while(iterator.hasNext()) {
				//表头信息
				Element bill = (Element) iterator.next();
				Element billHead = bill.element("billHead");
				String ywdjbh = billHead.element("ywdjbh").getText().trim();//单据编号
				String bizDate = billHead.element("bizdate").getText().trim();//业务日期
				String djjg = billHead.element("djjg").getText().trim();//到检机构
				String kpjg = billHead.element("kpjg").getText().trim();//开票机构
				
				String zjjg = billHead.element("zjjg").getText().trim();//中介单位
				String qydw = billHead.element("qydw").getText().trim();//签约单位
				String djdw = billHead.element("djdw").getText().trim();//到检单位
				String kpdw = billHead.element("kpdw").getText().trim();//开票单位
				String skdw = billHead.element("skdw").getText().trim();//付款单位
				String xsy = billHead.element("xsy").getText().trim();//销售员
				String tjlb = billHead.element("tjlb").getText().trim();//体检类别
				String xslb = billHead.element("xslb").getText().trim();//销售类别
				String sklb = billHead.element("sklb").getText().trim();//收款类别
				
				CtrlUnitInfo ctrlUnitInfo = Utils.getCtrlUnitInfo(ctx,djjg);//根据到检机构-判断管理单元
				ContextUtil.setCurrentCtrlUnit(ctx, ctrlUnitInfo);
				if(ctrlUnitInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+djjg+" 管理单元不存在"};
				}
				CompanyOrgUnitInfo djjgInfo = Utils.getCompanyOrgUnit(ctx, djjg);//到检机构
				if(djjgInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+djjg+"到检机构不存在"};
				}
				CompanyOrgUnitInfo kpjgInfo = Utils.getCompanyOrgUnit(ctx, kpjg);//开票机构
				if(kpjgInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+kpjg+"开票机构不存在"};
				}
				
				CustomerInfo zjjgInfo = Utils.getCustomerInfo(ctx, zjjg);
				if(zjjgInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+zjjg+"中介单位不存在"};
				}
				CustomerInfo qydwInfo = Utils.getCustomerInfo(ctx, qydw);
				if(qydwInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+qydw+"签约单位不存在"};
				}
				CustomerInfo djdwInfo = Utils.getCustomerInfo(ctx, djdw);
				if(djdwInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+qydw+"到检单位不存在"};
				}
				CustomerInfo kpdwInfo = Utils.getCustomerInfo(ctx, kpdw);
				if(kpdwInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+kpdw+"开票单位不存在"};
				}
				CustomerInfo skdwInfo = Utils.getCustomerInfo(ctx, skdw);
				if(skdwInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+skdw+"付款单位不存在"};
				}
				PersonInfo personInfo = Utils.getPersonInfo(ctx, xsy);
				if(personInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+xsy+"销售员不存在"};
				}
				
				ExamTypeInfo examTypeInfo = Utils.getExamTypeInfo(ctx, tjlb);
				if(examTypeInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+tjlb+"体检类别不存在"};
				}
				SaleTypeInfo saleTypeInfo = Utils.getSaleTypeInfo(ctx, xslb);
				if(saleTypeInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+xslb+"销售类别不存在"};
				}
				ReceTypeInfo receTypeInfo = Utils.getReceTypeInfo(ctx, sklb);
				if(receTypeInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+sklb+"收款类别不存在"};
				}
				String ldh = billHead.element("ldh").getText().trim();//
				String fph = billHead.element("fph").getText().trim();//
				String beizhu = billHead.element("beizhu").getText().trim();//
				String djr = billHead.element("djr").getText().trim();//
				String djtcbm = billHead.element("djtcbm").getText().trim();//
				String djtcmc = billHead.element("djtcmc").getText().trim();//
				String djxmbm = billHead.element("djxmbm").getText().trim();//
				String djxmmc = billHead.element("djxmmc").getText().trim();//
				String jxbs = billHead.element("jxbs").getText().trim();//
				String klj = billHead.element("klj").getText().trim();//
				String zkl = billHead.element("zkl").getText().trim();//
				String jsje = billHead.element("jsje").getText().trim();//
				String se = billHead.element("se").getText().trim();//
				String jshj = billHead.element("jshj").getText().trim();//
				String flag = billHead.element("flag").getText().trim();//
				String kh = billHead.element("kh").getText().trim();//
				
				RichExamTempTabInfo info = new RichExamTempTabInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				info.setYwdjbh(ywdjbh);
				info.setLdh(ldh);
				info.setBizdate(Utils.parseCustomDateString(bizDate, "yyyy-MM-dd"));
				info.setQydw(qydw);
				info.setDjdw(djdw);
				info.setKpdw(kpdw);
				info.setSkdw(skdw);
				info.setFph(fph);
				info.setXsy(xsy);
				info.setTjlb(tjlb);
				info.setBeizhu(beizhu);
				info.setDjr(djr);
				info.setDjtcbm(djtcbm);
				info.setDjtcmc(djtcmc);
				info.setDjxmbm(djxmbm);
				info.setDjxmmc(djxmmc);
				info.setXslb(xslb);
				info.setSklb(sklb);
				info.setJxbs(jxbs);
				info.setKlj(klj);
				info.setZkl(zkl);
				info.setJsje(jsje);
				info.setSe(se);
				info.setJshj(jshj);
				info.setFlag("1".equals(flag)?true:false);
				info.setDjjg(djjg);
				info.setKpjg(kpjg);
				info.setZjjg(zjjg);
				info.setKh(kh);
				info.setDjrq(new Date());
				
				itemp.save(info);
			}
		}catch(Exception e){
		}
		return strReturn;
	}

	protected String[] _saveExamBill(Context ctx, Date date, String String) throws BOSException {
		super._saveExamBill(ctx, date, String);
		String d = DateUtil.format(date, "yyyy-MM-dd");
		String sql = "select max(CFXsy) xsy,max(CFLdh) ldh,max(CFDjjg) djjg " +
				" ,max(cfywdjbh) ywdjbh" +
				" ,max(cfbizDate) bizDate" +
				" ,max(cfdjjg) djjg" +
				" ,max(cfzjjg) zjjg" +
				" ,max(cfkpjg) kpjg" +
				" ,max(cfqydw) qydw" +
				" ,max(cfdjdw) djdw" +
				" ,max(cfkpdw) kpdw" +
				" ,max(cfskdw) skdw" +
				" ,max(cftjlb) tjlb" +
				" ,max(cffph) fph" +
				" from CT_RIC_RichExamTempTab " +
				" where CFBizdate= '"+d+"'" +
				" group by CFXsy,CFLdh,CFDjjg,cfkpjg,cfqydw,cfdjdw,cfkpdw,cfskdw,cftjlb" ;
		IRowSet rs = DbUtil.executeQuery(ctx, sql);
		Map hcMap = new HashMap();
		try {
			IRichExamTempTab itemp = RichExamTempTabFactory.getLocalInstance(ctx);
			IRichExamed iRichExamed = RichExamedFactory.getLocalInstance(ctx);
			while(rs.next()){
				String xsy = rs.getString("xsy");
				String ldh = rs.getString("ldh");
				String djjg = rs.getString("djjg");
				
				String ywdjbh = rs.getString("ywdjbh");//单据编号
				Date bizDate = rs.getDate("bizDate");//业务日期
				djjg = rs.getString("djjg");//到检机构
				String kpjg = rs.getString("kpjg");//开票机构
				
				String zjjg = rs.getString("zjjg");//中介单位
				
				String qydw = rs.getString("qydw");//签约单位
				String djdw = rs.getString("djdw");//到检单位
				String kpdw = rs.getString("kpdw");//开票单位
				String skdw = rs.getString("skdw");//收款单位
				String tjlb = rs.getString("tjlb");//体检类别
				String fph = rs.getString("fph");//
				CtrlUnitInfo ctrlUnitInfo = Utils.getCtrlUnitInfo(ctx,"01");//根据到检机构-判断管理单元
				ContextUtil.setCurrentCtrlUnit(ctx, ctrlUnitInfo);
				if(ctrlUnitInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为01 管理单元不存在"};
				}
				CompanyOrgUnitInfo djjgInfo = Utils.getCompanyOrgUnit(ctx, djjg);//到检机构
				if(djjgInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+djjg+"到检机构不存在"};
				}
				CompanyOrgUnitInfo kpjgInfo = Utils.getCompanyOrgUnit(ctx, kpjg);//开票机构
				if(kpjgInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+kpjg+"开票机构不存在"};
				}
				CustomerInfo zjjgInfo = Utils.getCustomerInfo(ctx, zjjg);
				if(zjjgInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+zjjg+"中介单位不存在"};
				}
				
				CustomerInfo qydwInfo = Utils.getCustomerInfo(ctx, qydw);
				if(qydwInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+qydw+"签约单位不存在"};
				}
				CustomerInfo djdwInfo = Utils.getCustomerInfo(ctx, djdw);
				if(djdwInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+qydw+"到检单位不存在"};
				}
				CustomerInfo kpdwInfo = Utils.getCustomerInfo(ctx, kpdw);
				if(kpdwInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+kpdw+"开票单位不存在"};
				}
				CustomerInfo skdwInfo = Utils.getCustomerInfo(ctx, skdw);
				if(skdwInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+skdw+"付款单位不存在"};
				}
				PersonInfo personInfo = Utils.getPersonInfo(ctx, xsy);
				if(personInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+xsy+"销售员不存在"};
				}
				
				ExamTypeInfo examTypeInfo = Utils.getExamTypeInfo(ctx, tjlb);
				if(examTypeInfo == null) {
					return new String[] {"N", ywdjbh, ",编码为"+tjlb+"体检类别不存在"};
				}
				
				RichExamedInfo examInfo = new RichExamedInfo();
				examInfo.setBizDate(bizDate);
				examInfo.setDjDate(bizDate);
				examInfo.setKpCompany(kpjgInfo);
				examInfo.setDjCompany(djjgInfo);
				examInfo.setKpUnit(kpdwInfo);
				examInfo.setZjjg(zjjgInfo);
				examInfo.setQyUnit(qydwInfo);
				examInfo.setDjUnit(djdwInfo);
				examInfo.setFkUnit(skdwInfo);
				examInfo.setLdNumber(ldh);
				examInfo.setYwNumber(ywdjbh);
				examInfo.setSales(personInfo);
				examInfo.setTjType(examTypeInfo);
				examInfo.setFpNumber(fph);
				sql = "select max(cfdjr) djr,max(cfdjtcbm) djtcbm,max(cfdjtcmc) djtcmc " +
				" ,max(cfxslb) xslb" +
				" ,max(cfsklb) sklb" +
				" ,sum(TO_NUMBER(cfjshj)) jshj" +
				" from CT_RIC_RichExamTempTab " +
				" where CFBizdate= '"+d+"' and CFXsy='"+xsy+"' and CFLdh='"+ldh+"' and CFDjjg='"+djjg+"' and cfkpjg='"+kpjg+"' " +
						" and cfqydw='"+qydw+"' and cfdjdw='"+djdw+"' and cfkpdw='"+kpdw+"' and cfskdw='"+skdw+"' and cftjlb='"+tjlb+"'" +
				" group by cfdjr,CFDjtcbm" ;
				IRowSet djrrs = DbUtil.executeQuery(ctx, sql);
				while(djrrs.next()){
					RichExamedEntryInfo item = new RichExamedEntryInfo ();
					String djr = djrrs.getString("djr");
					String djtcbm = djrrs.getString("djtcbm");
					String djtcmc = djrrs.getString("djtcmc");
					String xslb = djrrs.getString("xslb");//销售类别
					String sklb = djrrs.getString("sklb");//收款类别
					String jshj = djrrs.getString("jshj");
					
					item.setDjr(djr);
					item.setDjtcNumber(djtcbm);
					item.setDjctName(djtcmc);
					SaleTypeInfo saleTypeInfo = Utils.getSaleTypeInfo(ctx, xslb);
					if(saleTypeInfo == null) {
						return new String[] {"N", ywdjbh, ",编码为"+xslb+"销售类别不存在"};
					}
					ReceTypeInfo receTypeInfo = Utils.getReceTypeInfo(ctx, sklb);
					if(receTypeInfo == null) {
						return new String[] {"N", ywdjbh, ",编码为"+sklb+"收款类别不存在"};
					}
					item.setSkType(receTypeInfo);
					item.setSlType(saleTypeInfo);
					item.setJsAmount(UIRuleUtil.getBigDecimal(jshj));
					RichExamTempTabCollection djrXMDetail = itemp.getRichExamTempTabCollection("where xsy='"+xsy+"' and bizdate='"+d+"' " +
													" and ldh='"+ldh+"' and djjg='"+djjg+"' and djr='"+djr+"' and djtcbm='"+djtcbm+"' ");
					for (int j = 0; j < djrXMDetail.size(); j++) {
						RichExamTempTabInfo tempDetail = djrXMDetail.get(j);
						RichExamedEntryDjrentryInfo detail = new RichExamedEntryDjrentryInfo();
						detail.setDjxmbm(tempDetail.getDjxmbm());
						detail.setDjxmmc(tempDetail.getDjxmmc());
						detail.setJxbs("1".equals(tempDetail.getJxbs()));
						detail.setKlj(UIRuleUtil.getBigDecimal(tempDetail.getKlj()));
						detail.setZkl(UIRuleUtil.getBigDecimal(tempDetail.getZkl()));
						detail.setJsje(UIRuleUtil.getBigDecimal(tempDetail.getJsje()));
						detail.setSe(UIRuleUtil.getBigDecimal(tempDetail.getSe()));
						detail.setJshj(UIRuleUtil.getBigDecimal(tempDetail.getJshj()));
						detail.setBeizhu(tempDetail.getBeizhu());
						
						item.getDjrentry().add(detail);
					}
					examInfo.getEntrys().add(item);
					
					iRichExamed.save(examInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String[] {"Y", "", ""};
	}
    
	/**生成红冲到检单（数据标示为1，并可根据业务单据编号找以前的到检单，那么复制以前的到检单后，将数据变为负数，为红冲）
	 * @throws BOSException 
	 * @throws EASBizException */
	void createHCBill(Context ctx,String ywdjbh) throws EASBizException, BOSException{
		RichExamedEntryInfo info = RichExamedEntryFactory.getLocalInstance(ctx).getRichExamedEntryInfo("where ywdjbh='"+ywdjbh+"'");
	}
    
}