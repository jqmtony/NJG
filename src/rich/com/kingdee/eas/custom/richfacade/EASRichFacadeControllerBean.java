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
				logger.info("体检明细xml字符串不包含数据或者xml字符串标签不正确");
				return new String[] {"N", "002", "体检明细xml字符串不包含数据或者xml字符串标签不正确"};
			}
			
			
			Iterator iterator = bills.iterator();//校验前，需插入临时表数据
			Iterator iterator2 = bills.iterator();//校验后，需插入中间表数据
			IRichExamTempTab itemp = RichExamTempTabFactory.getLocalInstance(ctx);
			StringBuilder repeat = new StringBuilder("");
			
	    	StringBuffer SQL = new StringBuffer(" insert into CT_RIC_RichExamTempTab (" +
					"FNUMBER, FSIMPLENAME, FID, FCREATORID, FCREATETIME, FLASTUPDATEUSERID, FLASTUPDATETIME, FCONTROLUNITID, CFYWDJBH, CFBIZDATE, CFLDH, CFQYDW, CFDJDW, CFKPDW, CFSKDW, CFDJPZH, CFKPPZH, CFSKPZH, CFBIZNUMBER, CFFPH, CFZJE, CFXSY, CFTJLB, CFBIZSTATE, CFBEIZHU, CFDJTCMC, CFDJR, CFDJTCBM, CFDJXMBM, CFDJXMMC, CFXSLB, CFSKLB, CFJXBS, CFKLJ, CFZKL, CFJSJE, CFSE, CFJSHJ, CFDJJG, CFKPJG, CFDJRQ, FNAME_L1, FNAME_L2, FNAME_L3, FDESCRIPTION_L1, FDESCRIPTION_L2, FDESCRIPTION_L3, CFFLAG, CFKH, CFZJJG) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    	/**
	         * 创建临时表 
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
	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，到检机构【"+rowset.getString("cfdjjg")+"】不存在！"+"\n");
	    		if(rowset.getString("kpjg")==null)
	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，开票机构【"+rowset.getString("cfkpjg")+"】不存在！"+"\n");
//	    		if(rowset.getString("zjdw")==null)
//	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，中介单位【"+rowset.getString("cfzjjg")+"】不存在！"+"\n");
	    		if(rowset.getString("qydw")==null)
	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，签约单位【"+rowset.getString("cfqydw")+"】不存在！"+"\n");
	    		if(rowset.getString("djdw")==null)
	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，到检单位【"+rowset.getString("cfdjdw")+"】不存在！"+"\n");
	    		if(rowset.getString("kpdw")==null)
	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，开票单位【"+rowset.getString("cfkpdw")+"】不存在！"+"\n");
	    		if(rowset.getString("fkdw")==null)
	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，付款单位【"+rowset.getString("cfskdw")+"】不存在！"+"\n");
	    		if(rowset.getString("xsy")==null)
	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，销售员【"+rowset.getString("cfxsy")+"】不存在！"+"\n");
	    		if(rowset.getString("xslb")==null)
	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，销售类别【"+rowset.getString("cfxslb")+"】不存在！"+"\n");
	    		if(rowset.getString("tjlb")==null)
	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，体检类别【"+rowset.getString("cftjlb")+"】不存在！"+"\n");
	    		if(rowset.getString("sklb")==null)
	    			sb.append("业务单据编号【"+rowset.getString("CFYWDJBH")+"】，收款类别【"+rowset.getString("cfsklb")+"】不存在！"+"\n");
	    	}
			pool.releaseTable(tableName);
	    	if(!"".equals(sb.toString()))
	    		return new String[] {"N","" ,sb.toString() };
	    	
	    	strReturn = saveData(ctx, iterator2, SQL.toString(),"0");
	    	if(strReturn[0].equals("N"))
	    		return strReturn;
		}catch(Exception e){
			return new String[] {"N","" ,"校验出错！" };
		}
		return strReturn;
	}
	
	/**
	 * @iterator 接口数据集
	 * @SQL 数据插入sql语句
	 * @isTemp 临时表标示，临时表保存编码作为校验
	 * 接口数据插入到临时表
	 * */
	String[] saveData(Context ctx, Iterator iterator,String SQL,String isTemp){
		String end = null;//最后一批数据
		String ywdjbh = null;//单据编号
		String bizDate = null;//业务日期
		String djjg = null;//到检机构
		String kpjg = null;//开票机构
		
		String zjjg = null;//中介单位
		String qydw = null;//签约单位
		String djdw = null;//到检单位
		String kpdw = null;//开票单位
		String skdw = null;//付款单位
		String xsy = null;//销售员
		String tjlb = null;//体检类别
		String xslb = null;//销售类别
		String sklb = null;//收款类别
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
		String isLast = "";//是否最后一次,Y为最后一批 ，""为非最后一批
		
		//创建者
    	String createUserID = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
    	//创建时间
    	Timestamp createTime = new Timestamp(new Date().getTime());
    	//最后修改者
    	String lastUpdateUserID = createUserID;
    	//最后修改时间
    	Timestamp lastUpdateTime = createTime;
    	//CU
    	String orgUtilID = ContextUtil.getCurrentCtrlUnit(ctx).getId().toString();
    	//业务日期
    	Timestamp bizTime = createTime;
    	//是否曾经生效
    	int haseffected = 0;
    	//修改者
    	String modifierID = lastUpdateUserID;
    	//修改时间
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
				//表头信息
				bill = (Element) iterator.next();
				billHead = bill.element("billHead");
				if(billHead.element("ywdjbh").getText()!=null)
					ywdjbh = billHead.element("ywdjbh").getText().replace("", "");//业务单据编号
				else
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+",不能为空！" };
				if(billHead.element("bizdate").getText()!=null)
					bizDate = billHead.element("bizdate").getText().replace("", "");//业务日期
				if(billHead.element("djjg").getText()!=null){
					djjg = billHead.element("djjg").getText().replace("", "");//到检机构
					if("1".equals(isTemp)){
						int index = djjg.indexOf("|");
						if(index>0)
							djjg = djjg.substring(0,index);
						else
							return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 到检机构格式不符【编码 | 名称】！" };
					}
				}else{
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+",到检机构不能为空！" };
				}
				if(billHead.element("kpjg").getText()!=null){
					kpjg = billHead.element("kpjg").getText().replace("", "");//开票机构
					if("1".equals(isTemp)){
						int index = kpjg.indexOf("|");
						if(index>0)
							kpjg = kpjg.substring(0,index);
						else
							return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 开票机构格式不符【编码 | 名称】！" };
					}
				}else{
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+",开票机构不能为空！" };
				}
				if(billHead.element("qydw").getText()!=null){
					qydw = billHead.element("qydw").getText().replace("", "");//签约单位
					if("1".equals(isTemp)){
						int index = qydw.indexOf("|");
						if(index>0)
							qydw = qydw.substring(0,index);
						else
							return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 签约单位格式不符【编码 | 名称】！" };
					}
				}else{
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+",签约单位不能为空！" };
				}
				if(billHead.element("djdw").getText()!=null){
					djdw = billHead.element("djdw").getText().replace("", "");//到检单位
					if("1".equals(isTemp)){
						int index = djdw.indexOf("|");
						if(index>0)
							djdw = djdw.substring(0,index);
						else
							return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 到检单位格式不符【编码 | 名称】！" };
					}
				}else{
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 到检单位不能为空！" };
				}
				if(billHead.element("kpdw").getText()!=null){
					kpdw = billHead.element("kpdw").getText().replace("", "");//开票单位
					if("1".equals(isTemp)){
						int index = kpdw.indexOf("|");
						if(index>0)
							kpdw = kpdw.substring(0,index);
						else
							return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 开票单位格式不符【编码 | 名称】！" };
					}
				}else{
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+",开票单位不能为空！" };
				}
				if(billHead.element("skdw").getText()!=null){
					skdw = billHead.element("skdw").getText().replace("", "");//付款单位
					if("1".equals(isTemp)){
						int index = skdw.indexOf("|");
						if(index>0)
							skdw = skdw.substring(0,index);
						else
							return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 付款单位格式不符【编码 | 名称】！" };
					}
				}else{
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+",付款单位不能为空！" };
				}
				if(billHead.element("xsy").getText()!=null){
					xsy = billHead.element("xsy").getText().replace("", "");//销售员
					if("1".equals(isTemp)){
						int index = xsy.indexOf("|");
						if(index>0)
							xsy = xsy.substring(0,index);
						else
							return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 销售员格式不符【编码 | 名称】！" };
					}
				}else{
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+",销售员不能为空！" };
				}
				if(billHead.element("tjlb").getText()!=null){
					tjlb = billHead.element("tjlb").getText().replace("", "");//体检类别
					if("1".equals(isTemp)){
						int index = tjlb.indexOf("|");
						if(index>0)
							tjlb = tjlb.substring(0,index);
						else
							return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 体检类别格式不符【编码 | 名称】！" };
					}
				}else{
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+",体检类别不能为空！" };
				}
				if(billHead.element("xslb").getText()!=null){
					xslb = billHead.element("xslb").getText().replace("", "");//销售类别
					if("1".equals(isTemp)){
						int index = xslb.indexOf("|");
						if(index>0)
							xslb = xslb.substring(0,index);
						else
							return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 销售类别格式不符【编码 | 名称】！" };
					}
				}else{
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+",销售类别不能为空！" };
				}
				if(billHead.element("sklb").getText()!=null){
					sklb = billHead.element("sklb").getText().replace("", "");//收款类别
					if("1".equals(isTemp)){
						int index = sklb.indexOf("|");
						if(index>0)
							sklb = sklb.substring(0,index);
						else
							return new String[] {"N","" ,"业务单据编号"+ywdjbh+", 收款类别格式不符【编码 | 名称】！" };
					}
				}else{
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+",收款类别不能为空！" };
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
					return new String[] {"N","" ,"业务单据编号"+ywdjbh+", "+bizDate+"日期格式有误，无法转换成yyyy-MM-dd" };
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
				// 加入执行计划，如果超过100，先执行一次
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
			return new String[] {"N","" ,"执行数据插入时报错" };
		} finally {
			com.kingdee.util.db.SQLUtils.cleanup(ps, conn);
		}
		return new String[] {isLast,"" ,"" };
		
	}

	//生成到检单
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
				CtrlUnitInfo ctrlUnitInfo = getCtrlUnitInfo(ctx,"01");//根据到检机构-判断管理单元
				ContextUtil.setCurrentCtrlUnit(ctx, ctrlUnitInfo);
				CompanyOrgUnitInfo djjgInfo = getCompanyOrgUnit(ctx, djjg);//到检机构
				CompanyOrgUnitInfo kpjgInfo = getCompanyOrgUnit(ctx, kpjg);//开票机构
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
				
				BigDecimal amount = new BigDecimal(0);//到检单总金额
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
					//到检单分录
					RichExamedEntryInfo item = new RichExamedEntryInfo ();
					String djr = djrrs.getString("djr");
					String djtcbm = djrrs.getString("djtcbm");
					String djtcmc = djrrs.getString("djtcmc");
					String xslb = djrrs.getString("xslb");//销售类别
					String sklb = djrrs.getString("sklb");//收款类别
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
						detail.setTempID(tempDetail.getId().toString());//中间表ID
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
    
	/**生成红冲到检单（数据标示为1，并可根据业务单据编号找以前的到检单，那么复制以前的到检单后，将数据变为负数，为红冲）
	 * @throws BOSException 
	 * @throws EASBizException */
	void createHCBill(Context ctx,String ywdjbh) throws EASBizException, BOSException{
		RichExamedEntryInfo info = RichExamedEntryFactory.getLocalInstance(ctx).getRichExamedEntryInfo("where ywdjbh='"+ywdjbh+"'");
	}
	/**
	 * 根据编码获取客户
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
	 * 根据分类编码获取供应商基本分类
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
	 * 根据编码获取供应商分类标准
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
	 * 根据编码获取财务组织
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
	 * 根据编码获取管理单元
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
	 * 根据编码获取采购组织
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
	 * 根据编码获取组织单元
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
	 * 根据编码获取币别信息
	 * @throws BOSException 
	 */
	public CurrencyInfo getCurrencyInfo(Context ctx, String number) throws BOSException {
		CurrencyInfo currencyInfo = null;
		String oql = "select id where number='" + number + "'";
		currencyInfo = iCurrency.getCurrencyCollection(oql).get(0);
		return currencyInfo;
	}
	
	/**
	 * 根据编码获取人员信息
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
	 * 根据编码获取体检类别
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
	 * 根据编码获取销售类别
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
	 * 根据编码获取收款类别
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