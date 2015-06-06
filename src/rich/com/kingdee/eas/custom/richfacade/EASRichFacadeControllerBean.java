package com.kingdee.eas.custom.richfacade;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
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
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.ICSSPGroup;
import com.kingdee.eas.basedata.master.cssp.ICSSPGroupStandard;
import com.kingdee.eas.basedata.master.cssp.ICustomer;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.ICtrlUnit;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.org.IPurchaseOrgUnit;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitCollection;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.basedata.person.IPerson;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.richbase.ExamTypeFactory;
import com.kingdee.eas.custom.richbase.ExamTypeInfo;
import com.kingdee.eas.custom.richbase.IExamType;
import com.kingdee.eas.custom.richbase.IOrgUnitEntry;
import com.kingdee.eas.custom.richbase.IReceType;
import com.kingdee.eas.custom.richbase.ISaleType;
import com.kingdee.eas.custom.richbase.OrgUnitEntryFactory;
import com.kingdee.eas.custom.richbase.OrgUnitEntryInfo;
import com.kingdee.eas.custom.richbase.ReceTypeFactory;
import com.kingdee.eas.custom.richbase.ReceTypeInfo;
import com.kingdee.eas.custom.richbase.SaleTypeFactory;
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
import com.kingdee.eas.hr.rec.util.DateUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class EASRichFacadeControllerBean extends AbstractEASRichFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.custom.richfacade.EASRichFacadeControllerBean");

    ICustomer iCustomer = null; 
    ICurrency iCurrency = null;
    ICSSPGroup iCssPGroup = null;
    ICSSPGroupStandard iCSSPGroupStandard = null;
    ICompanyOrgUnit iCompanyOrgUnit = null;
    ICtrlUnit iCtrlUnit = null;
    IPurchaseOrgUnit iPurchaseOrgUnit = null;
    IFullOrgUnit iFullOrgUnit = null;
    IPerson iPerson = null;
    IReceType iReceType = null;
    ISaleType iSaleType = null;
    IExamType iExamType = null;
    IOrgUnitEntry iOrgUnitEntry = null;
	protected String[] _saveTempData(Context ctx, String xmlString) throws BOSException {
		super._saveTempData(ctx, xmlString);
		iCustomer = CustomerFactory.getLocalInstance(ctx);
		iCurrency = CurrencyFactory.getLocalInstance(ctx);
		iCssPGroup = CSSPGroupFactory.getLocalInstance(ctx);
		iCSSPGroupStandard = CSSPGroupStandardFactory.getLocalInstance(ctx);
		iCompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
		iCtrlUnit = CtrlUnitFactory.getLocalInstance(ctx);
		iPurchaseOrgUnit = PurchaseOrgUnitFactory.getLocalInstance(ctx);
		iFullOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx);
		iPerson = PersonFactory.getLocalInstance(ctx);
		iReceType = ReceTypeFactory.getLocalInstance(ctx);
		iSaleType = SaleTypeFactory.getLocalInstance(ctx);
		iExamType = ExamTypeFactory.getLocalInstance(ctx);
		iOrgUnitEntry = OrgUnitEntryFactory.getLocalInstance(ctx);
		String[] strReturn = new String[] {"", "", ""};
		Document document;
		try {
			document = DocumentHelper.parseText(xmlString);
			Element root = document.getRootElement();
			List bills = root.elements("TempTable");
			if(bills.size() < 1) {
				logger.info("�����ϸxml�ַ������������ݻ���xml�ַ�����ǩ����ȷ");
				return new String[] {"N", "002", "�����ϸxml�ַ������������ݻ���xml�ַ�����ǩ����ȷ"};
			}
			
			
			Iterator iterator = bills.iterator();//У��ǰ���������ʱ������
			Iterator iterator2 = bills.iterator();//У���������м������
			IRichExamTempTab itemp = RichExamTempTabFactory.getLocalInstance(ctx);
			StringBuilder repeat = new StringBuilder("");
			
	    	StringBuffer SQL = new StringBuffer(" insert into CT_RIC_RichExamTempTab (" +
					"FNUMBER, FSIMPLENAME, FID, FCREATORID, FCREATETIME, FLASTUPDATEUSERID, FLASTUPDATETIME, FCONTROLUNITID, CFYWDJBH, CFBIZDATE, CFLDH, CFQYDW, CFDJDW, CFKPDW, CFSKDW, CFDJPZH, CFKPPZH, CFSKPZH, CFBIZNUMBER, CFFPH, CFZJE, CFXSY, CFTJLB, CFBIZSTATE, CFBEIZHU, CFDJTCMC, CFDJR, CFDJTCBM, CFDJXMBM, CFDJXMMC, CFXSLB, CFSKLB, CFJXBS, CFKLJ, CFZKL, CFJSJE, CFSE, CFJSHJ, CFDJJG, CFKPJG, CFDJRQ, FNAME_L1, FNAME_L2, FNAME_L3, FDESCRIPTION_L1, FDESCRIPTION_L2, FDESCRIPTION_L3, CFFLAG, CFKH, CFZJJG) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    	/**
	         * ������ʱ�� 
	         * @param ctx
	         * @return
	         * @throws BOSException
	         */
	    	String tableName=null;
    		StringBuffer createTableSql = new StringBuffer();
    		createTableSql.append(" Create Table CT_RIC_RichExamTempTab2 ( FName_l1 NVARCHAR(255),FName_l2 NVARCHAR(255),FName_l3 NVARCHAR(255),FNumber NVARCHAR(80),FDescription_l1 NVARCHAR(255),FDescription_l2 NVARCHAR(255),FDescription_l3 NVARCHAR(255),FSimpleName NVARCHAR(80),FID VARCHAR(44) DEFAULT '' NOT NULL ,FCreatorID VARCHAR(44),FCreateTime DateTime,FLastUpdateUserID VARCHAR(44),FLastUpdateTime DateTime,FControlUnitID VARCHAR(44) DEFAULT '11111111-1111-1111-1111-111111111111CCE7AED4',CFYwdjbh NVARCHAR(100),CFBizdate DateTime,CFLdh NVARCHAR(100),CFQydw NVARCHAR(100),CFDjdw NVARCHAR(100),CFKpdw NVARCHAR(100),CFSkdw NVARCHAR(100),CFDjpzh NVARCHAR(100),CFKppzh NVARCHAR(100),CFSkpzh NVARCHAR(100),CFBiznumber NVARCHAR(100),CFFph NVARCHAR(100),CFZje NVARCHAR(100),CFXsy NVARCHAR(100),CFTjlb NVARCHAR(100),CFBizState NVARCHAR(100),CFBeizhu NVARCHAR(100),CFDjtcmc NVARCHAR(100),CFDjr NVARCHAR(100),CFDjtcbm NVARCHAR(100),CFDjxmbm NVARCHAR(100),CFDjxmmc NVARCHAR(100),CFXslb NVARCHAR(100),CFSklb NVARCHAR(100),CFJxbs NVARCHAR(100),CFKlj NVARCHAR(100),CFZkl NVARCHAR(100),CFJsje NVARCHAR(100),CFSe NVARCHAR(100),CFJshj NVARCHAR(100),CFDjjg NVARCHAR(100),CFKpjg NVARCHAR(100),CFDjrq DateTime,CFFlag INT,CFKh NVARCHAR(100),CFZjjg NVARCHAR(100))");
    		TempTablePool pool = TempTablePool.getInstance(ctx);
    		try {
    			tableName =  pool.createTempTable(createTableSql.toString());
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
	    	
	    	StringBuffer SQL2 = new StringBuffer(" insert into "+tableName+" (" +
					"FNUMBER, FSIMPLENAME, FID, FCREATORID, FCREATETIME, FLASTUPDATEUSERID, FLASTUPDATETIME, FCONTROLUNITID, CFYWDJBH, CFBIZDATE, CFLDH, CFQYDW, CFDJDW, CFKPDW, CFSKDW, CFDJPZH, CFKPPZH, CFSKPZH, CFBIZNUMBER, CFFPH, CFZJE, CFXSY, CFTJLB, CFBIZSTATE, CFBEIZHU, CFDJTCMC, CFDJR, CFDJTCBM, CFDJXMBM, CFDJXMMC, CFXSLB, CFSKLB, CFJXBS, CFKLJ, CFZKL, CFJSJE, CFSE, CFJSHJ, CFDJJG, CFKPJG, CFDJRQ, FNAME_L1, FNAME_L2, FNAME_L3, FDESCRIPTION_L1, FDESCRIPTION_L2, FDESCRIPTION_L3, CFFLAG, CFKH, CFZJJG) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    	
	    	strReturn = saveData(ctx, iterator, SQL2.toString(),"1");
	    	if(strReturn[0].equals("N"))
	    		return strReturn;
			String sql = "select t.CFYWDJBH " +
							",t.cfdjjg cfdjjg,djjg.CFYwNumber djjg"+
							",t.cfkpjg cfkpjg,kpjg.CFYwNumber kpjg"+
							",t.cfzjjg cfzjjg,zjdw.fnumber zjdw"+
							",t.cfqydw cfqydw,qydw.fnumber qydw"+
							",t.cfdjdw cfdjdw,djdw.fnumber djdw"+
							",t.cfkpdw cfkpdw,kpdw.fnumber kpdw"+
							",t.cfskdw cfskdw,fkdw.fnumber fkdw"+
							",t.cfxsy cfxsy,xsy.fnumber xsy"+
							",t.cfxslb cfxslb,xslb.fnumber xslb"+
							",t.cftjlb cftjlb,tjlb.fnumber tjlb"+
							",t.cfsklb cfsklb,sklb.fnumber sklb"+
							" from "+tableName+" t"+
							" left join CT_RIC_OrgUnitEntry djjg on djjg.CFYwNumber=t.cfdjjg "+
							" left join CT_RIC_OrgUnitEntry kpjg on kpjg.CFYwNumber=t.cfkpjg "+
							" left join T_bd_customer zjdw on zjdw.fnumber=t.cfzjjg "+
							" left join T_bd_customer qydw on qydw.fnumber=t.cfqydw "+
							" left join T_bd_customer djdw on djdw.fnumber=t.cfdjdw "+
							" left join T_bd_customer kpdw on kpdw.fnumber=t.cfkpdw "+
							" left join T_bd_customer fkdw on fkdw.fnumber=t.cfskdw "+
							" left join T_bd_person xsy on xsy.fnumber=t.cfxsy" +
							" left join CT_RIC_SaleType xslb on xslb.fnumber=t.cfxslb" +
							" left join CT_RIC_ExamType tjlb on tjlb.fnumber=t.cftjlb" +
							" left join CT_RIC_ReceType sklb on sklb.fnumber=t.cfsklb";
	    	IRowSet rowset = DbUtil.executeQuery(ctx, sql);
	    	StringBuffer sb = new StringBuffer();
	    	while(rowset.next()){
	    		if(rowset.getString("djjg")==null)
	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"�������������"+rowset.getString("cfdjjg")+"�������ڣ�"+"\n");
	    		if(rowset.getString("kpjg")==null)
	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"������Ʊ������"+rowset.getString("cfkpjg")+"�������ڣ�"+"\n");
//	    		if(rowset.getString("zjdw")==null)
//	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"�����н鵥λ��"+rowset.getString("cfzjjg")+"�������ڣ�"+"\n");
	    		if(rowset.getString("qydw")==null)
	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"����ǩԼ��λ��"+rowset.getString("cfqydw")+"�������ڣ�"+"\n");
	    		if(rowset.getString("djdw")==null)
	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"�������쵥λ��"+rowset.getString("cfdjdw")+"�������ڣ�"+"\n");
	    		if(rowset.getString("kpdw")==null)
	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"������Ʊ��λ��"+rowset.getString("cfkpdw")+"�������ڣ�"+"\n");
	    		if(rowset.getString("fkdw")==null)
	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"�������λ��"+rowset.getString("cfskdw")+"�������ڣ�"+"\n");
	    		if(rowset.getString("xsy")==null)
	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"��������Ա��"+rowset.getString("cfxsy")+"�������ڣ�"+"\n");
	    		if(rowset.getString("xslb")==null)
	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"�����������"+rowset.getString("cfxslb")+"�������ڣ�"+"\n");
	    		if(rowset.getString("tjlb")==null)
	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"����������"+rowset.getString("cftjlb")+"�������ڣ�"+"\n");
	    		if(rowset.getString("sklb")==null)
	    			sb.append("ҵ�񵥾ݱ�š�"+rowset.getString("CFYWDJBH")+"�����տ����"+rowset.getString("cfsklb")+"�������ڣ�"+"\n");
	    	}
			pool.releaseTable(tableName);
	    	if(!"".equals(sb.toString()))
	    		return new String[] {"N","" ,sb.toString() };
	    	
	    	strReturn = saveData(ctx, iterator2, SQL.toString(),"0");
	    	if(strReturn[0].equals("N"))
	    		return strReturn;
		}catch(Exception e){
			return new String[] {"N","" ,"У�����" };
		}
		return strReturn;
	}
	
	/**
	 * @iterator �ӿ����ݼ�
	 * @SQL ���ݲ���sql���
	 * @isTemp ��ʱ���ʾ����ʱ���������ΪУ��
	 * �ӿ����ݲ��뵽��ʱ��
	 * */
	String[] saveData(Context ctx, Iterator iterator,String SQL,String isTemp){
		String end = null;//���һ������
		String ywdjbh = null;//���ݱ��
		String bizDate = null;//ҵ������
		String djjg = null;//�������
		String kpjg = null;//��Ʊ����
		
		String zjjg = null;//�н鵥λ
		String qydw = null;//ǩԼ��λ
		String djdw = null;//���쵥λ
		String kpdw = null;//��Ʊ��λ
		String skdw = null;//���λ
		String xsy = null;//����Ա
		String tjlb = null;//������
		String xslb = null;//�������
		String sklb = null;//�տ����
		String ldh = null;//
		String fph = null;//
		String beizhu = null;//
		String djr = null;//
		String djtcbm = null;//
		String djtcmc = null;//
		String djxmbm = null;//
		String djxmmc = null;//
		String jxbs = null;//
		String klj = null;//
		String zkl = null;//
		String jsje = null;//
		String se = null;//
		String jshj = null;//
		String flag = null;//
		String kh = null;//
		String isLast = "";//�Ƿ����һ��,YΪ���һ�� ��""Ϊ�����һ��
		
		//������
    	String createUserID = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
    	//����ʱ��
    	Timestamp createTime = new Timestamp(new Date().getTime());
    	//����޸���
    	String lastUpdateUserID = createUserID;
    	//����޸�ʱ��
    	Timestamp lastUpdateTime = createTime;
    	//CU
    	String orgUtilID = ContextUtil.getCurrentCtrlUnit(ctx).getId().toString();
    	//ҵ������
    	Timestamp bizTime = createTime;
    	//�Ƿ�������Ч
    	int haseffected = 0;
    	//�޸���
    	String modifierID = lastUpdateUserID;
    	//�޸�ʱ��
    	Timestamp modifierTime = lastUpdateTime;
    	
		Element bill = null;
		Element billHead = null;
		int iCount = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = EJBFactory.getConnection(ctx);
			ps = conn.prepareStatement(SQL);
			
			while(iterator.hasNext()) {
				//��ͷ��Ϣ
				bill = (Element) iterator.next();
				billHead = bill.element("billHead");
				if(billHead.element("ywdjbh").getText()!=null)
					ywdjbh = billHead.element("ywdjbh").getText().replace("", "");//ҵ�񵥾ݱ��
				else
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+",����Ϊ�գ�" };
				if(billHead.element("bizdate").getText()!=null)
					bizDate = billHead.element("bizdate").getText().replace("", "");//ҵ������
				if(billHead.element("djjg").getText()!=null){
					djjg = billHead.element("djjg").getText().replace("", "");//�������
					if("1".equals(isTemp)){
						int index = djjg.indexOf("|");
						if(index>0)
							djjg = djjg.substring(0,index);
						else
							return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", ���������ʽ���������� | ���ơ���" };
					}
				}else{
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+",�����������Ϊ�գ�" };
				}
				if(billHead.element("kpjg").getText()!=null){
					kpjg = billHead.element("kpjg").getText().replace("", "");//��Ʊ����
					if("1".equals(isTemp)){
						int index = kpjg.indexOf("|");
						if(index>0)
							kpjg = kpjg.substring(0,index);
						else
							return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", ��Ʊ������ʽ���������� | ���ơ���" };
					}
				}else{
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+",��Ʊ��������Ϊ�գ�" };
				}
				if(billHead.element("qydw").getText()!=null){
					qydw = billHead.element("qydw").getText().replace("", "");//ǩԼ��λ
					if("1".equals(isTemp)){
						int index = qydw.indexOf("|");
						if(index>0)
							qydw = qydw.substring(0,index);
						else
							return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", ǩԼ��λ��ʽ���������� | ���ơ���" };
					}
				}else{
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+",ǩԼ��λ����Ϊ�գ�" };
				}
				if(billHead.element("djdw").getText()!=null){
					djdw = billHead.element("djdw").getText().replace("", "");//���쵥λ
					if("1".equals(isTemp)){
						int index = djdw.indexOf("|");
						if(index>0)
							djdw = djdw.substring(0,index);
						else
							return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", ���쵥λ��ʽ���������� | ���ơ���" };
					}
				}else{
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", ���쵥λ����Ϊ�գ�" };
				}
				if(billHead.element("kpdw").getText()!=null){
					kpdw = billHead.element("kpdw").getText().replace("", "");//��Ʊ��λ
					if("1".equals(isTemp)){
						int index = kpdw.indexOf("|");
						if(index>0)
							kpdw = kpdw.substring(0,index);
						else
							return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", ��Ʊ��λ��ʽ���������� | ���ơ���" };
					}
				}else{
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+",��Ʊ��λ����Ϊ�գ�" };
				}
				if(billHead.element("skdw").getText()!=null){
					skdw = billHead.element("skdw").getText().replace("", "");//���λ
					if("1".equals(isTemp)){
						int index = skdw.indexOf("|");
						if(index>0)
							skdw = skdw.substring(0,index);
						else
							return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", ���λ��ʽ���������� | ���ơ���" };
					}
				}else{
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+",���λ����Ϊ�գ�" };
				}
				if(billHead.element("xsy").getText()!=null){
					xsy = billHead.element("xsy").getText().replace("", "");//����Ա
					if("1".equals(isTemp)){
						int index = xsy.indexOf("|");
						if(index>0)
							xsy = xsy.substring(0,index);
						else
							return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", ����Ա��ʽ���������� | ���ơ���" };
					}
				}else{
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+",����Ա����Ϊ�գ�" };
				}
				if(billHead.element("tjlb").getText()!=null){
					tjlb = billHead.element("tjlb").getText().replace("", "");//������
					if("1".equals(isTemp)){
						int index = tjlb.indexOf("|");
						if(index>0)
							tjlb = tjlb.substring(0,index);
						else
							return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", �������ʽ���������� | ���ơ���" };
					}
				}else{
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+",��������Ϊ�գ�" };
				}
				if(billHead.element("xslb").getText()!=null){
					xslb = billHead.element("xslb").getText().replace("", "");//�������
					if("1".equals(isTemp)){
						int index = xslb.indexOf("|");
						if(index>0)
							xslb = xslb.substring(0,index);
						else
							return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", ��������ʽ���������� | ���ơ���" };
					}
				}else{
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+",���������Ϊ�գ�" };
				}
				if(billHead.element("sklb").getText()!=null){
					sklb = billHead.element("sklb").getText().replace("", "");//�տ����
					if("1".equals(isTemp)){
						int index = sklb.indexOf("|");
						if(index>0)
							sklb = sklb.substring(0,index);
						else
							return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", �տ�����ʽ���������� | ���ơ���" };
					}
				}else{
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+",�տ������Ϊ�գ�" };
				}
				if(billHead.element("ldh").getText()!=null)
					ldh = billHead.element("ldh").getText().replace("", "");//
				if(billHead.element("fph").getText()!=null)
					fph = billHead.element("fph").getText().replace("", "");//
				if(billHead.element("beizhu").getText()!=null)
					beizhu = billHead.element("beizhu").getText().replace("", "");//
				if(billHead.element("djr").getText()!=null)
					djr = billHead.element("djr").getText().replace("", "");//
				if(billHead.element("djtcbm").getText()!=null)
					djtcbm = billHead.element("djtcbm").getText().replace("", "");//
				if(billHead.element("djtcmc").getText()!=null)
					djtcmc = billHead.element("djtcmc").getText().replace("", "");//
				if(billHead.element("djxmbm").getText()!=null)
					djxmbm = billHead.element("djxmbm").getText().replace("", "");//
				if(billHead.element("djxmmc").getText()!=null)
					djxmmc = billHead.element("djxmmc").getText().replace("", "");//
				if(billHead.element("jxbs").getText()!=null)
					jxbs = billHead.element("jxbs").getText().replace("", "");//
				if(billHead.element("klj").getText()!=null)
					klj = billHead.element("klj").getText().replace("", "");//
				if(billHead.element("zkl").getText()!=null)
					zkl = billHead.element("zkl").getText().replace("", "");//
				if(billHead.element("jsje").getText()!=null)
					jsje = billHead.element("jsje").getText().replace("", "");//
				if(billHead.element("se").getText()!=null)
					se = billHead.element("se").getText().replace("", "");//
				if(billHead.element("jshj").getText()!=null)
					jshj = billHead.element("jshj").getText().replace("", "");//
				if(billHead.element("flag").getText()!=null)
					flag = billHead.element("flag").getText().replace("", "");//
				if(billHead.element("kh").getText()!=null)
					kh = billHead.element("kh").getText().replace("", "");//
				if(billHead.element("end").getText()!=null){
					if("1".equals(billHead.element("end").getText().replace("", "")))
						isLast = "Y";
				}
			
				ps.setString(1, "");
				ps.setString(2, "");
				ps.setString(3, BOSUuid.create("90615CB8").toString());
				ps.setString(4, createUserID);
				ps.setTimestamp(5, createTime);
				ps.setString(6, lastUpdateUserID);
				ps.setTimestamp(7, lastUpdateTime);
				ps.setString(8, orgUtilID);
				ps.setString(9, ywdjbh);
				if(Utils.parseCustomDateString(bizDate, "yyyy-MM-dd")==null)
					return new String[] {"N","" ,"ҵ�񵥾ݱ��"+ywdjbh+", "+bizDate+"���ڸ�ʽ�����޷�ת����yyyy-MM-dd" };
				ps.setTimestamp(10, new Timestamp(Utils.parseCustomDateString(bizDate, "yyyy-MM-dd").getTime()));
				ps.setString(11, ldh);
				ps.setString(12, qydw);
				ps.setString(13, djdw);
				ps.setString(14, kpdw);
				ps.setString(15, skdw);
				ps.setString(16, "");
				ps.setString(17, "");
				ps.setString(18, "");
				ps.setString(19, ywdjbh);
				ps.setString(20, fph);
				ps.setString(21, "");
				ps.setString(22, xsy);
				ps.setString(23, tjlb);
				ps.setString(24, "");
				ps.setString(25, beizhu);
				ps.setString(26, djtcmc);
				ps.setString(27, djr);
				ps.setString(28, djtcbm);
				ps.setString(29, djxmbm);
				ps.setString(30, djxmmc);
				ps.setString(31, xslb);
				ps.setString(32, sklb);
				ps.setString(33, jxbs);
				ps.setString(34, klj);
				ps.setString(35,zkl);
				ps.setString(36, jsje);
				ps.setString(37, se);
				ps.setString(38, jshj);
				ps.setString(39, djjg);
				ps.setString(40, kpjg);
				ps.setTimestamp(41, lastUpdateTime);
				ps.setString(42, "");
				ps.setString(43, "");
				ps.setString(44, "");
				ps.setString(45, "");
				ps.setString(46, "");
				ps.setString(47, "");
				ps.setString(48, flag);
				ps.setString(49, kh);
				ps.setString(50, zjjg);
				// ����ִ�мƻ����������100����ִ��һ��
				ps.addBatch();
				iCount++;
				if (iCount == 100) {
					iCount = 0;
					ps.executeBatch();
				}
			}
			if (iCount > 0) {
				ps.executeBatch();
			}
		}catch(Exception e){
			return new String[] {"N","" ,"ִ�����ݲ���ʱ����" };
		} finally {
			com.kingdee.util.db.SQLUtils.cleanup(ps, conn);
		}
		return new String[] {isLast,"" ,"" };
		
	}

	//���ɵ��쵥
	protected String[] _saveExamBill(Context ctx, Date date, String String) throws BOSException {
		super._saveExamBill(ctx, date, String);
		iCustomer = CustomerFactory.getLocalInstance(ctx);
		iCurrency = CurrencyFactory.getLocalInstance(ctx);
		iCssPGroup = CSSPGroupFactory.getLocalInstance(ctx);
		iCSSPGroupStandard = CSSPGroupStandardFactory.getLocalInstance(ctx);
		iCompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
		iCtrlUnit = CtrlUnitFactory.getLocalInstance(ctx);
		iPurchaseOrgUnit = PurchaseOrgUnitFactory.getLocalInstance(ctx);
		iFullOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx);
		iPerson = PersonFactory.getLocalInstance(ctx);
		iReceType = ReceTypeFactory.getLocalInstance(ctx);
		iSaleType = SaleTypeFactory.getLocalInstance(ctx);
		iExamType = ExamTypeFactory.getLocalInstance(ctx);
		iOrgUnitEntry = OrgUnitEntryFactory.getLocalInstance(ctx);
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
				CtrlUnitInfo ctrlUnitInfo = getCtrlUnitInfo(ctx,"01");//���ݵ������-�жϹ���Ԫ
				ContextUtil.setCurrentCtrlUnit(ctx, ctrlUnitInfo);
				CompanyOrgUnitInfo djjgInfo = getCompanyOrgUnit(ctx, djjg);//�������
				CompanyOrgUnitInfo kpjgInfo = getCompanyOrgUnit(ctx, kpjg);//��Ʊ����
//				CustomerInfo zjjgInfo = getCustomerInfo(ctx, zjjg);
				
				CustomerInfo qydwInfo = getCustomerInfo(ctx, qydw);
				CustomerInfo djdwInfo = getCustomerInfo(ctx, djdw);
				CustomerInfo kpdwInfo = getCustomerInfo(ctx, kpdw);
				CustomerInfo skdwInfo = getCustomerInfo(ctx, skdw);
				PersonInfo personInfo = getPersonInfo(ctx, xsy);
				ExamTypeInfo examTypeInfo = getExamTypeInfo(ctx, tjlb);
				
				RichExamedInfo examInfo = new RichExamedInfo();
				examInfo.setBizDate(bizDate);
				examInfo.setDjDate(new Date());
				examInfo.setKpCompany(kpjgInfo);
				examInfo.setDjCompany(djjgInfo);
				examInfo.setKpUnit(kpdwInfo);
//				examInfo.setZjjg(zjjgInfo);
				examInfo.setQyUnit(qydwInfo);
				examInfo.setDjUnit(djdwInfo);
				examInfo.setFkUnit(skdwInfo);
				examInfo.setLdNumber(ldh);
				examInfo.setYwNumber(ywdjbh);
				examInfo.setSales(personInfo);
				examInfo.setTjType(examTypeInfo);
				examInfo.setFpNumber(fph);
				
				BigDecimal amount = new BigDecimal(0);//���쵥�ܽ��
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
					//���쵥��¼
					RichExamedEntryInfo item = new RichExamedEntryInfo ();
					String djr = djrrs.getString("djr");
					String djtcbm = djrrs.getString("djtcbm");
					String djtcmc = djrrs.getString("djtcmc");
					String xslb = djrrs.getString("xslb");//�������
					String sklb = djrrs.getString("sklb");//�տ����
					String jshj = djrrs.getString("jshj");
					
					item.setDjr(djr);
					item.setYwdjbh(ywdjbh);
					item.setDjtcNumber(djtcbm);
					item.setDjctName(djtcmc);
					SaleTypeInfo saleTypeInfo = getSaleTypeInfo(ctx, xslb);
					ReceTypeInfo receTypeInfo = getReceTypeInfo(ctx, sklb);
					item.setSkType(receTypeInfo);
					item.setSlType(saleTypeInfo);
					item.setJsAmount(UIRuleUtil.getBigDecimal(jshj));
					amount = amount.add(UIRuleUtil.getBigDecimal(jshj));
					RichExamTempTabCollection djrXMDetail = itemp.getRichExamTempTabCollection("where xsy='"+xsy+"' and bizdate='"+d+"' " +
													" and ldh='"+ldh+"' and djjg='"+djjg+"' and djr='"+djr+"' and djtcbm='"+djtcbm+"' ");
					for (int j = 0; j < djrXMDetail.size(); j++) {
						RichExamTempTabInfo tempDetail = djrXMDetail.get(j);
						RichExamedEntryDjrentryInfo detail = new RichExamedEntryDjrentryInfo();
						detail.setYwdjbh(ywdjbh);
						detail.setDjxmbm(tempDetail.getDjxmbm());
						detail.setDjxmmc(tempDetail.getDjxmmc());
						detail.setJxbs("1".equals(tempDetail.getJxbs()));
						if(tempDetail.getKlj()!=null && !"".equals(tempDetail.getKlj()))
							detail.setKlj(UIRuleUtil.getBigDecimal(tempDetail.getKlj()));
						if(tempDetail.getZkl()!=null && !"".equals(tempDetail.getZkl()))
							detail.setZkl(UIRuleUtil.getBigDecimal(tempDetail.getZkl()));
						if(tempDetail.getJsje()!=null && !"".equals(tempDetail.getJsje()))
							detail.setJsje(UIRuleUtil.getBigDecimal(tempDetail.getJsje()));
						if(tempDetail.getSe()!=null && !"".equals(tempDetail.getSe()))
							detail.setSe(UIRuleUtil.getBigDecimal(tempDetail.getSe()));
						if(tempDetail.getJshj()!=null && !"".equals(tempDetail.getJshj()))
							detail.setJshj(UIRuleUtil.getBigDecimal(tempDetail.getJshj()));
						detail.setBeizhu(tempDetail.getBeizhu());
						detail.setTempID(tempDetail.getId().toString());//�м��ID
						item.getDjrentry().add(detail);
					}
					examInfo.getEntrys().add(item);
					examInfo.setAmount(amount);
					iRichExamed.save(examInfo);
					
					sql = "update CT_RIC_RichExamTempTab set cfdjd='1' where fid in (select CFTempID from CT_RIC_RichExamedDjrentry )";
					DbUtil.execute(ctx, sql);
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
	/**
	 * ���ݱ����ȡ�ͻ�
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public  CustomerInfo getCustomerInfo(Context ctx, String number) throws BOSException, EASBizException {
		CustomerInfo CustomerInfo = null;
		if(number!=null && number.indexOf("|")>0){
			int index = number.indexOf("|");
			number = number.substring(0,index);
			String oql = "select id where number='" + number + "'";
			if(iCustomer.exists(oql))
				CustomerInfo = iCustomer.getCustomerInfo(oql);
		}
		return CustomerInfo;
	}
	/**
	 * ���ݷ�������ȡ��Ӧ�̻�������
	 * @throws BOSException 
	 * @throws EASBizException 
	 */

	public CSSPGroupInfo getCSSPGroupInfo(Context ctx, String number) throws BOSException, EASBizException {
		CSSPGroupInfo cSSPGroupInfo = null;
		String oql = "select id where number='" + number + "'";
		CSSPGroupCollection groupCollection = iCssPGroup.getCSSPGroupCollection(oql);
		if(groupCollection != null)
			cSSPGroupInfo = groupCollection.get(0);
		return cSSPGroupInfo;
	}
	/**
	 * ���ݱ����ȡ��Ӧ�̷����׼
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public CSSPGroupStandardInfo getCSSPGroupStandardInfo(Context ctx, String number) throws BOSException, EASBizException {
		CSSPGroupStandardInfo cSSPGroupStandardInfo = null;
		String oql = "select id where number='" + number + "'";
		CSSPGroupStandardCollection groupStandardCollection = iCSSPGroupStandard.getCSSPGroupStandardCollection(oql);
		if(groupStandardCollection != null)
			cSSPGroupStandardInfo = groupStandardCollection.get(0);
		return cSSPGroupStandardInfo;
	}
	/**
	 * ���ݱ����ȡ������֯
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public CompanyOrgUnitInfo getCompanyOrgUnit(Context ctx, String number) throws BOSException, EASBizException {
		CompanyOrgUnitInfo CompanyOrgInfo = null;
		if(number!=null && number.indexOf("|")>0){
			int index = number.indexOf("|");
			number = number.substring(0,index);
			OrgUnitEntryInfo info = iOrgUnitEntry.getOrgUnitEntryInfo("select EASOrgNumber.id where ywNumber='"+number+"'");
			String oql = "select id  where id='" + info.getEASOrgNumber().getId() + "'";
			if(iCompanyOrgUnit.exists(oql))
				CompanyOrgInfo = iCompanyOrgUnit.getCompanyOrgUnitInfo(oql);
		}
		return CompanyOrgInfo;
	}
	/**
	 * ���ݱ����ȡ����Ԫ
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public CtrlUnitInfo getCtrlUnitInfo(Context ctx, String number) throws BOSException, EASBizException {
		CtrlUnitInfo ctrlUnitInfo = null;
		String oql = "select id where number='" + number + "'";
		if(iCtrlUnit.exists(oql))
			ctrlUnitInfo = iCtrlUnit.getCtrlUnitInfo(oql);
		return ctrlUnitInfo;
	}
	/**
	 * ���ݱ����ȡ�ɹ���֯
	 * @throws BOSException 
	 */
	public PurchaseOrgUnitInfo getPurchaseOrgUnitInfo(Context ctx, String number) throws BOSException {
		PurchaseOrgUnitInfo purchaseOrgUnitInfo = null;
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		PurchaseOrgUnitCollection purchaseOrgUnitCollection = iPurchaseOrgUnit.getPurchaseOrgUnitCollection(evi);
		if(purchaseOrgUnitCollection != null)
			purchaseOrgUnitInfo = purchaseOrgUnitCollection.get(0);
		return purchaseOrgUnitInfo;
	}
	/**
	 * ���ݱ����ȡ��֯��Ԫ
	 * @throws BOSException 
	 */
	public FullOrgUnitInfo getOrgUnitInfo(Context ctx, String number) throws BOSException {
		FullOrgUnitInfo orgUnitInfo = null;
		if(number!=null && number.indexOf("|")>0){
			int index = number.indexOf("|");
			number = number.substring(0,index);
			String oql = "select id where number='" + number + "'";
			FullOrgUnitCollection fullOrgUnitCollection = iFullOrgUnit.getFullOrgUnitCollection(oql);
			if(fullOrgUnitCollection != null)
				orgUnitInfo = fullOrgUnitCollection.get(0);
		}
		return orgUnitInfo;
	}
	/**
	 * ���ݱ����ȡ�ұ���Ϣ
	 * @throws BOSException 
	 */
	public CurrencyInfo getCurrencyInfo(Context ctx, String number) throws BOSException {
		CurrencyInfo currencyInfo = null;
		String oql = "select id where number='" + number + "'";
		currencyInfo = iCurrency.getCurrencyCollection(oql).get(0);
		return currencyInfo;
	}
	
	/**
	 * ���ݱ����ȡ��Ա��Ϣ
	 * @throws BOSException 
	 */
	public PersonInfo getPersonInfo(Context ctx, String number) throws BOSException {
		PersonInfo personInfo = null;
		if(number!=null && number.indexOf("|")>0){
			int index = number.indexOf("|");
			number = number.substring(0,index);
			String oql = "select id where number='" + number + "'";
			personInfo = iPerson.getPersonCollection(oql).get(0);
		}
		return personInfo;
	}
	
	/**
	 * ���ݱ����ȡ������
	 * @throws BOSException 
	 */
	public  ExamTypeInfo getExamTypeInfo(Context ctx, String number) throws BOSException {
		ExamTypeInfo examTypeInfo = null;
		if(number!=null && number.indexOf("|")>0){
			int index = number.indexOf("|");
			String basenumber = number.substring(0,index);
			String name = number.substring(index+1,number.length());
			String oql = "where number='" + basenumber + "'";
			examTypeInfo = iExamType.getExamTypeCollection(oql).get(0);
			if(examTypeInfo==null){
				examTypeInfo = new ExamTypeInfo();
				examTypeInfo.setNumber(basenumber);
				examTypeInfo.setName(name);
				try {
					iExamType.save(examTypeInfo);
				} catch (EASBizException e) {
					e.printStackTrace();
				}
			}
		}
		return examTypeInfo;
	}
	
	/**
	 * ���ݱ����ȡ�������
	 * @throws BOSException 
	 */
	public  SaleTypeInfo getSaleTypeInfo(Context ctx, String number) throws BOSException {
		SaleTypeInfo saleTypeInfo = null;
		if(number!=null && number.indexOf("|")>0){
			int index = number.indexOf("|");
			String basenumber = number.substring(0,index);
			String name = number.substring(index+1,number.length());
			String oql = "where number='" + basenumber+ "'";
			saleTypeInfo = iSaleType.getSaleTypeCollection(oql).get(0);
			if(saleTypeInfo==null){
				saleTypeInfo = new SaleTypeInfo();
				saleTypeInfo.setNumber(basenumber);
				saleTypeInfo.setName(name);
				try {
					iSaleType.save(saleTypeInfo);
				} catch (EASBizException e) {
					e.printStackTrace();
				}
			}
		}
		return saleTypeInfo;
	}
	
	/**
	 * ���ݱ����ȡ�տ����
	 * @throws BOSException 
	 */
	public  ReceTypeInfo getReceTypeInfo(Context ctx, String number) throws BOSException {
		ReceTypeInfo receTypeInfo = null;
		if(number!=null && number.indexOf("|")>0){
			int index = number.indexOf("|");
			String basenumber = number.substring(0,index);
			String name = number.substring(index+1,number.length());
			String oql = "where number='" + basenumber+ "'";
			receTypeInfo = iReceType.getReceTypeCollection(oql).get(0);
			if(receTypeInfo==null){
				receTypeInfo = new ReceTypeInfo();
				receTypeInfo.setNumber(basenumber);
				receTypeInfo.setName(name);
				try {
					iReceType.save(receTypeInfo);
				} catch (EASBizException e) {
					e.printStackTrace();
				}
			}
		}
		return receTypeInfo;
	}
	
}