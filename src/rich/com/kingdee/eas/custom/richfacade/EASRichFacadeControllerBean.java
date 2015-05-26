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
				logger.info("�����ϸxml�ַ������������ݻ���xml�ַ�����ǩ����ȷ");
				return new String[] {"N", "002", "�����ϸxml�ַ������������ݻ���xml�ַ�����ǩ����ȷ"};
			}
			
			Iterator iterator = bills.iterator();
			CoreBaseCollection  tempColl = new CoreBaseCollection();
			IRichExamTempTab itemp = RichExamTempTabFactory.getLocalInstance(ctx);
			StringBuilder repeat = new StringBuilder("");
			while(iterator.hasNext()) {
				//��ͷ��Ϣ
				Element bill = (Element) iterator.next();
				Element billHead = bill.element("billHead");
				String ywdjbh = billHead.element("ywdjbh").getText().trim();//���ݱ��
				String bizDate = billHead.element("bizdate").getText().trim();//ҵ������
				String djjg = billHead.element("djjg").getText().trim();//�������
				String kpjg = billHead.element("kpjg").getText().trim();//��Ʊ����
				
				String zjjg = billHead.element("zjjg").getText().trim();//�н鵥λ
				String qydw = billHead.element("qydw").getText().trim();//ǩԼ��λ
				String djdw = billHead.element("djdw").getText().trim();//���쵥λ
				String kpdw = billHead.element("kpdw").getText().trim();//��Ʊ��λ
				String skdw = billHead.element("skdw").getText().trim();//���λ
				String xsy = billHead.element("xsy").getText().trim();//����Ա
				String tjlb = billHead.element("tjlb").getText().trim();//������
				String xslb = billHead.element("xslb").getText().trim();//�������
				String sklb = billHead.element("sklb").getText().trim();//�տ����
				
				CtrlUnitInfo ctrlUnitInfo = Utils.getCtrlUnitInfo(ctx,djjg);//���ݵ������-�жϹ���Ԫ
				ContextUtil.setCurrentCtrlUnit(ctx, ctrlUnitInfo);
				if(ctrlUnitInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+djjg+" ����Ԫ������"};
				}
				CompanyOrgUnitInfo djjgInfo = Utils.getCompanyOrgUnit(ctx, djjg);//�������
				if(djjgInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+djjg+"�������������"};
				}
				CompanyOrgUnitInfo kpjgInfo = Utils.getCompanyOrgUnit(ctx, kpjg);//��Ʊ����
				if(kpjgInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+kpjg+"��Ʊ����������"};
				}
				
				CustomerInfo zjjgInfo = Utils.getCustomerInfo(ctx, zjjg);
				if(zjjgInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+zjjg+"�н鵥λ������"};
				}
				CustomerInfo qydwInfo = Utils.getCustomerInfo(ctx, qydw);
				if(qydwInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+qydw+"ǩԼ��λ������"};
				}
				CustomerInfo djdwInfo = Utils.getCustomerInfo(ctx, djdw);
				if(djdwInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+qydw+"���쵥λ������"};
				}
				CustomerInfo kpdwInfo = Utils.getCustomerInfo(ctx, kpdw);
				if(kpdwInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+kpdw+"��Ʊ��λ������"};
				}
				CustomerInfo skdwInfo = Utils.getCustomerInfo(ctx, skdw);
				if(skdwInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+skdw+"���λ������"};
				}
				PersonInfo personInfo = Utils.getPersonInfo(ctx, xsy);
				if(personInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+xsy+"����Ա������"};
				}
				
				ExamTypeInfo examTypeInfo = Utils.getExamTypeInfo(ctx, tjlb);
				if(examTypeInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+tjlb+"�����𲻴���"};
				}
				SaleTypeInfo saleTypeInfo = Utils.getSaleTypeInfo(ctx, xslb);
				if(saleTypeInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+xslb+"������𲻴���"};
				}
				ReceTypeInfo receTypeInfo = Utils.getReceTypeInfo(ctx, sklb);
				if(receTypeInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+sklb+"�տ���𲻴���"};
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
				
				String ywdjbh = rs.getString("ywdjbh");//���ݱ��
				Date bizDate = rs.getDate("bizDate");//ҵ������
				djjg = rs.getString("djjg");//�������
				String kpjg = rs.getString("kpjg");//��Ʊ����
				
				String zjjg = rs.getString("zjjg");//�н鵥λ
				
				String qydw = rs.getString("qydw");//ǩԼ��λ
				String djdw = rs.getString("djdw");//���쵥λ
				String kpdw = rs.getString("kpdw");//��Ʊ��λ
				String skdw = rs.getString("skdw");//�տλ
				String tjlb = rs.getString("tjlb");//������
				String fph = rs.getString("fph");//
				CtrlUnitInfo ctrlUnitInfo = Utils.getCtrlUnitInfo(ctx,"01");//���ݵ������-�жϹ���Ԫ
				ContextUtil.setCurrentCtrlUnit(ctx, ctrlUnitInfo);
				if(ctrlUnitInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ01 ����Ԫ������"};
				}
				CompanyOrgUnitInfo djjgInfo = Utils.getCompanyOrgUnit(ctx, djjg);//�������
				if(djjgInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+djjg+"�������������"};
				}
				CompanyOrgUnitInfo kpjgInfo = Utils.getCompanyOrgUnit(ctx, kpjg);//��Ʊ����
				if(kpjgInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+kpjg+"��Ʊ����������"};
				}
				CustomerInfo zjjgInfo = Utils.getCustomerInfo(ctx, zjjg);
				if(zjjgInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+zjjg+"�н鵥λ������"};
				}
				
				CustomerInfo qydwInfo = Utils.getCustomerInfo(ctx, qydw);
				if(qydwInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+qydw+"ǩԼ��λ������"};
				}
				CustomerInfo djdwInfo = Utils.getCustomerInfo(ctx, djdw);
				if(djdwInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+qydw+"���쵥λ������"};
				}
				CustomerInfo kpdwInfo = Utils.getCustomerInfo(ctx, kpdw);
				if(kpdwInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+kpdw+"��Ʊ��λ������"};
				}
				CustomerInfo skdwInfo = Utils.getCustomerInfo(ctx, skdw);
				if(skdwInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+skdw+"���λ������"};
				}
				PersonInfo personInfo = Utils.getPersonInfo(ctx, xsy);
				if(personInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+xsy+"����Ա������"};
				}
				
				ExamTypeInfo examTypeInfo = Utils.getExamTypeInfo(ctx, tjlb);
				if(examTypeInfo == null) {
					return new String[] {"N", ywdjbh, ",����Ϊ"+tjlb+"�����𲻴���"};
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
					String xslb = djrrs.getString("xslb");//�������
					String sklb = djrrs.getString("sklb");//�տ����
					String jshj = djrrs.getString("jshj");
					
					item.setDjr(djr);
					item.setDjtcNumber(djtcbm);
					item.setDjctName(djtcmc);
					SaleTypeInfo saleTypeInfo = Utils.getSaleTypeInfo(ctx, xslb);
					if(saleTypeInfo == null) {
						return new String[] {"N", ywdjbh, ",����Ϊ"+xslb+"������𲻴���"};
					}
					ReceTypeInfo receTypeInfo = Utils.getReceTypeInfo(ctx, sklb);
					if(receTypeInfo == null) {
						return new String[] {"N", ywdjbh, ",����Ϊ"+sklb+"�տ���𲻴���"};
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
    
	/**���ɺ�嵽�쵥�����ݱ�ʾΪ1�����ɸ���ҵ�񵥾ݱ������ǰ�ĵ��쵥����ô������ǰ�ĵ��쵥�󣬽����ݱ�Ϊ������Ϊ��壩
	 * @throws BOSException 
	 * @throws EASBizException */
	void createHCBill(Context ctx,String ywdjbh) throws EASBizException, BOSException{
		RichExamedEntryInfo info = RichExamedEntryFactory.getLocalInstance(ctx).getRichExamedEntryInfo("where ywdjbh='"+ywdjbh+"'");
	}
    
}