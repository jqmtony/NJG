/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.CostAccountPromptBox;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataCollection;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEbaseUnitCollection;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEbaseUnitInfo;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEbuildNumberCollection;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEbuildNumberInfo;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEdateDataCollection;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEdateDataInfo;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEnumberDataCollection;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEnumberDataInfo;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEproductTypeCollection;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEproductTypeInfo;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEtextDataCollection;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEtextDataInfo;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataInfo;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEntryCollection;
import com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEntryInfo;
import com.kingdee.eas.fdc.costindexdb.ContractStationEnum;
import com.kingdee.eas.fdc.costindexdb.ProjectStationEnum;
import com.kingdee.eas.fdc.costindexdb.database.BuildNumberCollection;
import com.kingdee.eas.fdc.costindexdb.database.BuildNumberFactory;
import com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillCollection;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexCollection;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexEntryCollection;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexEntryInfo;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexFactory;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexInfo;
import com.kingdee.eas.fdc.costindexdb.database.FieldType;
import com.kingdee.eas.fdc.costindexdb.database.IBuildNumber;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class BuildPriceIndexEditUI extends AbstractBuildPriceIndexEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuildPriceIndexEditUI.class);
    private Map<String,KDTable> tables = null;
    private Map<String,KDContainer> containers = null;
    private Map<String,CostAccountPriceIndexEntryCollection> priceIndexEntrys = null;
    List<KDWorkButton> buttons = null;
    //成本科目与造价指标关联表
    private CostAccountPriceIndexCollection capcoll = null;
    //基本要素
//    private BaseAndSinglePointEntryCollection bsentrys = null;
    //单项要素
//    private BaseAndSinglePointEcostCollection bsecosts = null;
    //楼号拆分单
    BuildSplitBillCollection bscoll = null;
    //用于保存科目与页签的关系，在删除页签时候用到
    Map<String,String[]> costTabMap = null;
    //保存公式内容，table的name属性加上列的key属性作为键
    Map<String,String> contentMap = null;
    
    /**
     * 获取所有基本要素、单项要素楼号集合 ，以MAP的形式缓存
     */
    private Map<String,BigDecimal> baseAndSingle = new HashMap<String, BigDecimal>();
    
    /**
     * 获取合同价 ，以MAP的形式缓存
     */
    private Map<String,BigDecimal> contractPrice = new HashMap<String, BigDecimal>();
    /**
     * 获取专业要素拆分 ，以MAP的形式缓存
     */
    private Map<String,BigDecimal> zySplit = new HashMap<String, BigDecimal>();
    
    /**
     * output class constructor
     */
    public BuildPriceIndexEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
//    	if("ADDNEW".equals(getOprtState())){
//    		String contractStationType = (String)getUIContext().get("contractStationType");
//    		if("sign".equals(contractStationType)){
////    			objectValue.setContractStation(ContractStationEnum.contractSign);
//    			contractStation.setSelectedItem(ContractStationEnum.contractSign);
//    		}else if("settle".equals(contractStationType)){
////    			objectValue.setContractStation(ContractStationEnum.contractEnd);
//    			contractStation.setSelectedItem(ContractStationEnum.contractEnd);
//    		}else if("change".equals(contractStationType)){
////    			objectValue.setContractStation(ContractStationEnum.contractChange);
//    			contractStation.setSelectedItem(ContractStationEnum.contractChange);
//    		}
//    	}
    	costTabMap = new HashMap<String,String[]>();
    	contentMap = new HashMap<String,String>();
    	//得到成本科目与指标的关联表，用于根据明细科目创建指标表
    	capcoll=CostAccountPriceIndexFactory.getRemoteInstance().getCostAccountPriceIndexCollection("select costAccount.id,costAccount.longnumber,costAccount.isLeaf,number,name,indexType.id,indexType.number,indexType.name,Entrys.fieldName,Entrys.fieldHide,Entrys.fieldInput,Entrys.fcontent,Entrys.fieldType");
		
//    	EntityViewInfo viewInfo = new EntityViewInfo();
//		FilterInfo filterInfo = new FilterInfo();
//		filterInfo.getFilterItems().add(new FilterItemInfo("projectName.id", editData.getProjectId()));
//		viewInfo.setFilter(filterInfo);
//		SelectorItemCollection sic = new SelectorItemCollection();
//    	sic.add("costAccount.id");
//    	sic.add("costAccount.longNumber");
//    	sic.add("costAccount.isLeaf");
//    	sic.add("sourceNumber");
//    	sic.add("dataType");
//    	sic.add("contractLevel");
//    	sic.add("entrys.id");
//    	sic.add("entrys.pointName");
//    	sic.add("entrys.dataValue");
//    	sic.add("entrys.Details.id");
//    	sic.add("entrys.Details.buildNumber.id");
//    	sic.add("entrys.Details.buildNumber.name");
//    	sic.add("entrys.Details.buildNumber.number");
//    	sic.add("entrys.Details.modelBuild");
//    	sic.add("entrys.Details.dataValue");
//		viewInfo.setSelector(sic);
//    	bscoll=BuildSplitBillFactory.getRemoteInstance().getBuildSplitBillCollection(viewInfo);
    	setSmallButtonStatus();
    }
    
    private String getStringFromSet(Set<String> projectIdSet){
    	StringBuffer sb = new StringBuffer();
	    for(Iterator<String> it=projectIdSet.iterator(); it.hasNext();) {
	    	 sb.append("'");
	    	 sb.append(it.next());
	    	 sb.append("',");
	    }
	    if(sb.length() > 1){
	    	sb.setLength(sb.length()-1);
	    	return sb.toString();
	    }
	    return "'999'";
    }
    
    /**
     * 获取所有基本要素、单项要素楼号集合 ，以MAP的形式缓存
     * @throws BOSException 
     * @throws SQLException 
     */
    private void getBaseDXForMAP(String idString) throws BOSException, SQLException{
//    	EntityViewInfo viewInfo = new EntityViewInfo();
//		FilterInfo filterInfo = new FilterInfo();
//		filterInfo.getFilterItems().add(new FilterItemInfo("parent1.parent.projectName.id",projectIdSet,CompareType.INCLUDE));
//		filterInfo.getFilterItems().add(new FilterItemInfo("modelBuild",1));
//		filterInfo.getFilterItems().add(new FilterItemInfo("parent1.parent.dataType","singlePoint"));
//		filterInfo.getFilterItems().add(new FilterItemInfo("parent1.parent.dataType","basePoint"));
//		filterInfo.setMaskString("#0 and #1 and (#2 or #3)");
//		viewInfo.setFilter(filterInfo);
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add("parent1.parent.projectName.id");
//    	sic.add("parent1.parent.costAccount.longnumber");
//    	sic.add("parent1.parent.dataType,parent1.pointName");
//    	sic.add("buildNumber.id");
//    	sic.add("dataValue");
//		viewInfo.setSelector(sic);
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sbill.CFProjectNameID,costAcc.FlongNumber,entry.CFPointName,details.CFBuildNumberID,details.CFDataValue from CT_DAT_BuildSplitBillDetails details ");
		builder.appendSql("left join CT_DAT_BuildSplitBillEntry entry on details.FParentID=entry.fid left join CT_DAT_BuildSplitBill sbill on entry.FParentID=sbill.fid ");
//		builder.appendSql("left join CT_DAT_BuildNumber buildNum on details.CFBuildNumberID=buildNum.fid ");
		builder.appendSql("left join T_FDC_CostAccount costAcc on sbill.CFCostAccountID=costAcc.fid where ");//details.CFModelBuild=1
		builder.appendSql("sbill.CFDataType in ('singlePoint','basePoint') and sbill.CFProjectNameID in ("+idString+")");
		IRowSet rs = builder.executeQuery();
		while(rs.next()){
			String caLongNumber = "";
			if(rs.getString(2) != null)
				caLongNumber = rs.getString(2);
			if(rs.getString(4) == null)
				continue;
			//数据类型--->项目id+要素名称+典型楼号的ID+科目长编码
			baseAndSingle.put(rs.getString(1)+rs.getString(3)+rs.getString(4)+caLongNumber,rs.getBigDecimal(5));
		}
		//获取单项要素 
		builder.clear();
		builder.appendSql("select entry.CFPROJECTID,details.CFPointName,costAcc.FlongNumber,details.CFPointValue from CT_COS_BaseAndSinglePointEcost details ");
		builder.appendSql("left join CT_COS_BaseAndSinglePoint entry on details.FParentID=entry.fid  ");
		builder.appendSql("left join T_FDC_CostAccount costAcc on details.CFCostAccountID=costAcc.fid ");
		builder.appendSql("where entry.CFISLATEST=1 and entry.CFPOINTBILLSTATUS='4AUDITTED' and entry.CFPROJECTID in ("+idString+")");
		//CFPROJECTID='"+editData.getProjectId()+"' and CFISLATEST=1 and CFPOINTBILLSTATUS='4AUDITTED'
		rs = builder.executeQuery();
		while(rs.next()){
			//数据类型--->项目id+要素名称+科目长编码
			String caLongNumber = "";
			if(rs.getString(3) != null)
				caLongNumber = rs.getString(3);
			baseAndSingle.put(rs.getString(1)+rs.getString(2)+caLongNumber,rs.getBigDecimal(4));
		}
		//获得基本要素
		builder.clear();
		builder.appendSql("select entry.CFPROJECTID,details.CFPointName,details.CFPointValue from CT_COS_BaseAndSinglePointEntry details ");
		builder.appendSql("left join CT_COS_BaseAndSinglePoint entry on details.FParentID=entry.fid  ");
		builder.appendSql("where entry.CFISLATEST=1 and entry.CFPOINTBILLSTATUS='4AUDITTED' and entry.CFPROJECTID in ("+idString+")");
		//CFPROJECTID='"+editData.getProjectId()+"' and CFISLATEST=1 and CFPOINTBILLSTATUS='4AUDITTED'
		rs = builder.executeQuery();
		while(rs.next()){
			//数据类型--->项目id+要素名称
			baseAndSingle.put(rs.getString(1)+rs.getString(2),rs.getBigDecimal(3));
		}
//    	BuildSplitBillEntryDetailCollection splitColl=BuildSplitBillEntryDetailFactory.getRemoteInstance().getBuildSplitBillEntryDetailCollection(viewInfo);
//    	for (int i = 0; i < splitColl.size(); i++) {
//    		BuildSplitBillEntryDetailInfo entryInfo = splitColl.get(i);
//    		BuildNumberInfo buildInfo = entryInfo.getBuildNumber();  
//    		String pointName = entryInfo.getParent1().getPointName(); 
//    		BuildSplitDataType dataType = entryInfo.getParent1().getParent().getDataType();
//    		
//    		CostAccountInfo costAccount = entryInfo.getParent1().getParent().getCostAccount();
//    		String costAccountLongNumber = "";
//    		if(costAccount!=null)
//    			costAccountLongNumber = costAccount.getLongNumber();
//    		String projectId = entryInfo.getParent1().getParent().getProjectName().getId().toString();
//    		if(buildInfo==null)
//    			continue;//数据类型(单项要素XXX)+要素名称+典型楼号的ID+科目长编码
//    		baseAndSingle.put(dataType.getAlias()+pointName+buildInfo.getId()+costAccountLongNumber, entryInfo.getDataValue());
//		}
    	
    	//直接获取要素 这样存放到时候可以根据分录是否有典型楼号 获取Value
//		for(int i = bsentrys.size()-1; i >=0; i--) {
//			BaseAndSinglePointEntryInfo einfo = bsentrys.get(i);
//			baseAndSingle.put("基本要素"+einfo.getPointName(),einfo.getPointValue());
//		}
//        
//        //获得单项要素
//		for(int i = bsecosts.size()-1; i >=0; i--) {
//			BaseAndSinglePointEcostInfo einfo = bsecosts.get(i);
//			baseAndSingle.put("单项要素"+einfo.getPointName()+einfo.getCostAccount().getLongNumber(),einfo.getPointValue());
//		}
    }
    
    /**
     * 获取合同价 ，以MAP的形式缓存
     * @throws BOSException 
     * @throws SQLException 
     */
    private void getContractPriceSplitForMAP(String clevel,String idString) throws BOSException, SQLException{
    	FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sbill.CFProjectNameID,costAcc.FlongNumber,entry.CFPointName,details.CFBuildNumberID,details.CFDataValue from CT_DAT_BuildSplitBillDetails details ");
		builder.appendSql("left join CT_DAT_BuildSplitBillEntry entry on details.FParentID=entry.fid left join CT_DAT_BuildSplitBill sbill on entry.FParentID=sbill.fid ");
		builder.appendSql("left join T_FDC_CostAccount costAcc on sbill.CFCostAccountID=costAcc.fid where ");//details.CFModelBuild=1
		builder.appendSql("sbill.CFDataType='contract' and sbill.CFContractLevel='"+clevel+"' and sbill.CFProjectNameID in ("+idString+")");
		IRowSet rs = builder.executeQuery();
		while(rs.next()){
			String caLongNumber = "";
			if(rs.getString(2) != null)
				caLongNumber = rs.getString(2);
			if(rs.getString(4) == null)
				continue;
			//数据类型--->项目id+要素名称+典型楼号的ID+科目长编码
			contractPrice.put(rs.getString(1)+rs.getString(3)+rs.getString(4)+caLongNumber,rs.getBigDecimal(5));
		}
		//数据类型--->项目id+要素名称+科目长编码
		builder.clear();
		builder.appendSql("select sbill.CFProjectNameID,costAcc.FlongNumber,entry.CFPointName,entry.CFDataValue from CT_DAT_BuildSplitBillEntry entry ");
		builder.appendSql("left join CT_DAT_BuildSplitBill sbill on entry.FParentID=sbill.fid ");
		builder.appendSql("left join T_FDC_CostAccount costAcc on sbill.CFCostAccountID=costAcc.fid where ");
		builder.appendSql("sbill.CFDataType='contract' and sbill.CFContractLevel='"+clevel+"' and sbill.CFProjectNameID in ("+idString+")");
		rs = builder.executeQuery();
		while(rs.next()){
			String caLongNumber = "";
			if(rs.getString(2) != null)
				caLongNumber = rs.getString(2);
			//数据类型--->项目id+要素名称+科目长编码
			contractPrice.put(rs.getString(1)+rs.getString(3)+caLongNumber,rs.getBigDecimal(4));
		}
		
//    	String oql="select parent1.parent.contractLevel,parent1.parent.costAccount.longnumber,parent1.parent.dataType,parent1.pointName,buildNumber.id,dataValue where parent1.parent.dataType='contract' and parent1.parent.contractLevel='"+clevel+"' and modelBuild=1 and parent1.parent.projectName.id='"+editData.getProjectId()+"'";
//    	BuildSplitBillEntryDetailCollection splitBillCollection = BuildSplitBillEntryDetailFactory.getRemoteInstance().getBuildSplitBillEntryDetailCollection(oql);
//    	for (int i = 0; i < splitBillCollection.size(); i++) {//获取楼号拆分数据
//    		BuildSplitBillEntryDetailInfo entryInfo = splitBillCollection.get(i);
//    		BuildNumberInfo buildInfo = entryInfo.getBuildNumber();  
////    		String pointName = entryInfo.getParent1().getPointName();
////    		BuildSplitDataType dataType = entryInfo.getParent1().getParent().getDataType();
//    		
//    		CostAccountInfo costAccount = entryInfo.getParent1().getParent().getCostAccount();
//    		String costAccountLongNumber = "";
//    		if(costAccount!=null)
//    			costAccountLongNumber = costAccount.getLongNumber();
//    		
//    		if(buildInfo==null)
//    			continue;//数据类型(单项要素XXX)+要素名称+典型楼号的ID+科目长编码
////    		ContractPriceSplitForMAP.put(dataType.getAlias()+pointName+buildInfo.getId()+costAccountLongNumber, entryInfo.getDataValue());
//    		contractPrice.put(entryInfo.getParent1().getPointName()+buildInfo.getId()+costAccountLongNumber, entryInfo.getDataValue());
//		}
//    	//直接获取要素 这样存放到时候可以根据分录是否有典型楼号 获取Value
//    	oql="select parent.contractLevel,parent.costAccount.longnumber,parent.dataType,pointName,buildNumber.id,dataValue where parent.dataType='contract' and parent.contractLevel='"+clevel+"' and parent.projectName.id='"+editData.getProjectId()+"'";
//    	BuildSplitBillEntryCollection entryCollection = BuildSplitBillEntryFactory.getRemoteInstance().getBuildSplitBillEntryCollection(oql);
//    	for (int i = 0; i < entryCollection.size(); i++) {//获取楼号拆分数据
//    		BuildSplitBillEntryInfo entryInfo = entryCollection.get(i);
////    		String pointName = entryInfo.getPointName();
////    		BuildSplitDataType dataType = entryInfo.getParent().getDataType();
//    		CostAccountInfo costAccount = entryInfo.getParent().getCostAccount();
//    		String costAccountLongNumber = "";
//    		if(costAccount!=null)
//    			costAccountLongNumber = costAccount.getLongNumber();
////    		BigDecimal dataValue = UIRuleUtil.getBigDecimal(entryInfo.getDataValue()); 
//    		//数据类型--->要素名称+科目长编码
////    		ContractPriceSplitForMAP.put(dataType.getAlias()+pointName+costAccountLongNumber, dataValue);
//    		contractPrice.put(entryInfo.getPointName()+costAccountLongNumber, entryInfo.getDataValue());
//		}
    }
    
    /**
     * 获取专业要素拆分 ，以MAP的形式缓存
     * @throws BOSException 
     * @throws SQLException 
     */
    private void getZYSplitForMAP(String clevel,String idString) throws BOSException, SQLException{
    	FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sbill.CFProjectNameID,costAcc.FlongNumber,entry.CFPointName,details.CFBuildNumberID,details.CFDataValue from CT_DAT_BuildSplitBillDetails details ");
		builder.appendSql("left join CT_DAT_BuildSplitBillEntry entry on details.FParentID=entry.fid left join CT_DAT_BuildSplitBill sbill on entry.FParentID=sbill.fid ");
		builder.appendSql("left join T_FDC_CostAccount costAcc on sbill.CFCostAccountID=costAcc.fid where ");//details.CFModelBuild=1
		builder.appendSql("sbill.CFDataType='professPoint' and sbill.CFContractLevel='"+clevel+"' and sbill.CFProjectNameID in ("+idString+")");
		IRowSet rs = builder.executeQuery();
		while(rs.next()){
			String caLongNumber = "";
			if(rs.getString(2) != null)
				caLongNumber = rs.getString(2);
			if(rs.getString(4) == null)
				continue;
			//数据类型--->项目id+要素名称+典型楼号的ID+科目长编码
			zySplit.put(rs.getString(1)+rs.getString(3)+rs.getString(4)+caLongNumber,rs.getBigDecimal(5));
		}
		//数据类型--->项目id+要素名称+科目长编码
		builder.clear();
		builder.appendSql("select sbill.CFProjectNameID,costAcc.FlongNumber,entry.CFPointName,entry.CFDataValue from CT_DAT_BuildSplitBillEntry entry ");
		builder.appendSql("left join CT_DAT_BuildSplitBill sbill on entry.FParentID=sbill.fid ");
		builder.appendSql("left join T_FDC_CostAccount costAcc on sbill.CFCostAccountID=costAcc.fid where ");
		builder.appendSql("sbill.CFDataType='professPoint' and sbill.CFContractLevel='"+clevel+"' and sbill.CFProjectNameID in ("+idString+")");
		rs = builder.executeQuery();
		while(rs.next()){
			String caLongNumber = "";
			if(rs.getString(2) != null)
				caLongNumber = rs.getString(2);
			//数据类型--->项目id+要素名称+科目长编码
			zySplit.put(rs.getString(1)+rs.getString(3)+caLongNumber,rs.getBigDecimal(4));
		}
    	
//    	String oql="select parent1.parent.contractLevel,parent1.parent.costAccount.longnumber,parent1.parent.dataType,parent1.pointName,buildNumber.id,dataValue where parent1.parent.dataType='professPoint' and parent1.parent.contractLevel='"+clevel+"' and modelBuild=1 and parent1.parent.projectName.id='"+editData.getProjectId()+"'";
//    	BuildSplitBillEntryDetailCollection splitBillCollection = BuildSplitBillEntryDetailFactory.getRemoteInstance().getBuildSplitBillEntryDetailCollection(oql);
//    	for (int i = 0; i < splitBillCollection.size(); i++) {//获取楼号拆分数据
//    		BuildSplitBillEntryDetailInfo entryInfo = splitBillCollection.get(i);
//    		BuildNumberInfo buildInfo = entryInfo.getBuildNumber();  
////    		String pointName = entryInfo.getParent1().getPointName();
////    		BuildSplitDataType dataType = entryInfo.getParent1().getParent().getDataType();
//    		CostAccountInfo costAccount = entryInfo.getParent1().getParent().getCostAccount();
//    		String costAccountLongNumber = "";
//    		if(costAccount!=null)
//    			costAccountLongNumber = costAccount.getLongNumber();
//    		
////    		BigDecimal dataValue = UIRuleUtil.getBigDecimal(entryInfo.getDataValue()); 
//    		if(buildInfo==null)
//    			continue;//数据类型(单项要素XXX)+要素名称+典型楼号的ID+科目长编码
//    		zySplit.put(entryInfo.getParent1().getPointName()+buildInfo.getId()+costAccountLongNumber,entryInfo.getDataValue());
//    	}
//    	//直接获取要素 这样存放到时候可以根据分录是否有典型楼号 获取Value
//    	oql="select parent.contractLevel,parent.costAccount.longnumber,parent.dataType,pointName,buildNumber.id,dataValue where parent.dataType='professPoint' and parent.contractLevel='"+clevel+"' and parent.projectName.id='"+editData.getProjectId()+"'";
//    	BuildSplitBillEntryCollection entryCollection = BuildSplitBillEntryFactory.getRemoteInstance().getBuildSplitBillEntryCollection(oql);
//    	for (int i = 0; i < entryCollection.size(); i++) {//获取楼号拆分数据
//    		BuildSplitBillEntryInfo entryInfo = entryCollection.get(i);
////    		String pointName = entryInfo.getPointName();
////    		BuildSplitDataType dataType = entryInfo.getParent().getDataType();
//    		CostAccountInfo costAccount = entryInfo.getParent().getCostAccount();
//    		String costAccountLongNumber = "";
//    		if(costAccount!=null)
//    			costAccountLongNumber = costAccount.getLongNumber();
//    		
////    		BigDecimal dataValue = UIRuleUtil.getBigDecimal(entryInfo.getDataValue()); 
//    		//数据类型(单项要素XXX)+要素名称+科目长编码
//    		zySplit.put(entryInfo.getPointName()+costAccountLongNumber, entryInfo.getDataValue());
//    	}
    }

	private void setSmallButtonStatus() {
		actionAddNew.setVisible(false);
    	actionCopy.setVisible(false);
    	actionFirst.setVisible(false);
    	actionLast.setVisible(false);
    	actionPre.setVisible(false);
    	actionNext.setVisible(false);
    	actionWorkFlowG.setVisible(false);
    	actionCreateFrom.setVisible(false);
    	actionCreateTo.setVisible(false);
    	actionNextPerson.setVisible(false);
    	actionAuditResult.setVisible(false);
    	actionMultiapprove.setVisible(false);
    	actionSubmit.setVisible(false);
	}

	private void initEntrys() {
		containers = new HashMap<String,KDContainer>();
		tables = new HashMap<String,KDTable>();
		buttons = new ArrayList<KDWorkButton>();
		priceIndexEntrys = new HashMap<String,CostAccountPriceIndexEntryCollection>();
		kDContainer1.getContentPane().remove(kdtEntrys_detailPanel);
		kDContainer1.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
		kdtEntrys.addKDTEditListener(new KDTEditAdapter(){
    		public void editStopped(KDTEditEvent e) {
    			kdtEntrys_editStopped(e);
    		}
    	});
		KDWorkButton addLine = new KDWorkButton("新增行");
		KDWorkButton insetLine = new KDWorkButton("插入行");
		KDWorkButton removeLine = new KDWorkButton("删除行");
		addLine.setName("addLine");
		insetLine.setName("insetLine");
		removeLine.setName("removeLine");
		addLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		insetLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
		removeLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		kDContainer1.addButton(addLine);
		kDContainer1.addButton(insetLine);
		kDContainer1.addButton(removeLine);
		MyActionListener baseAL = new MyActionListener(kdtEntrys);
		addLine.addActionListener(baseAL);
		insetLine.addActionListener(baseAL);
		removeLine.addActionListener(baseAL);
		buttons.add(addLine);
		buttons.add(insetLine);
		buttons.add(removeLine);
	}
	
	public void removeIndexTable(CostAccountInfo cainfo){
		//			ICostAccountPriceIndex icapi = CostAccountPriceIndexFactory.getRemoteInstance();
		//			CostAccountPriceIndexCollection capcoll = null;
		//			capcoll=icapi.getCostAccountPriceIndexCollection("where id in(select a.fid from CT_DAT_CostAccountPriceIndex a left join T_FDC_CostAccount b on a.CFCOSTACCOUNTID=b.fid where b.flongnumber='"+cainfo.getLongNumber()+"')");
		//			IIndexType iit = IndexTypeFactory.getRemoteInstance();
		//			EntityViewInfo viewInfo = new EntityViewInfo();
		//			FilterInfo filterInfo = new FilterInfo();
		//			filterInfo.getFilterItems().add(new FilterItemInfo("costAccount.id", cainfo.getId().toString()));
		//			filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", editData.getProjectId()));
		//			filterInfo.getFilterItems().add(new FilterItemInfo("costAccount.longnumber", cainfo.getLongNumber()));
		//			viewInfo.setFilter(filterInfo);
		//			SelectorItemCollection sic = new SelectorItemCollection();
		//	    	sic.add("indexType.id");
		//	    	sic.add("indexType.number");
		//	    	sic.add("indexType.name");
		//			viewInfo.setSelector(sic);
		//			capcoll=CostAccountPriceIndexFactory.getRemoteInstance().getCostAccountPriceIndexCollection(viewInfo);
		String kdcName = null;
		String[] tabNames = costTabMap.get(cainfo.getLongNumber());
		for(int i = 0; i < tabNames.length; i++) {
			kdcName = tabNames[i];
			Component[] comps = kDTabbedPane1.getComponents();
			for(int j = 0; j < comps.length; j++) {
				if(kdcName.equals(comps[j].getName())){
					kDTabbedPane1.remove(comps[j]);
					tables.remove(kdcName);
					break;
				}
			}
		} 
		
	}
	
	public void initIndexTable(CostAccountInfo cainfo, String projectPcIds){
		CostAccountPriceIndexInfo capinfo = null;
		List<CostAccountPriceIndexInfo> entrycolls = new ArrayList<CostAccountPriceIndexInfo>();
		for(int i = capcoll.size()-1; i >=0; i--) {
			capinfo = capcoll.get(i);
			if(capinfo.getCostAccount().getLongNumber().equals(cainfo.getLongNumber())){
				entrycolls.add(capinfo);
			}
		}
		if(entrycolls.size() == 0){
			String caLongNumber = cainfo.getLongNumber();
			while(entrycolls.size() == 0){
				if(caLongNumber.lastIndexOf("!") == -1)
					break;
				caLongNumber = caLongNumber.substring(0,caLongNumber.lastIndexOf("!"));
				for(int i = capcoll.size()-1; i >=0; i--) {
					capinfo = capcoll.get(i);
					if(capinfo.getCostAccount().getLongNumber().equals(caLongNumber)){
						entrycolls.add(capinfo);
					}
				}
			}
		}
		CostAccountPriceIndexEntryCollection entrycoll = null;
		CostAccountPriceIndexEntryInfo entryInfo = null;
		KDWorkButton addLine = null;
		KDWorkButton insetLine = null;
		KDWorkButton removeLine = null;
		KDContainer kdc = null;
		KDTable table = null;
		IColumn icol = null;
		IRow row = null;
		String key = null;
		String[] tabNames = new String[entrycolls.size()];
		String realPorjectId = projectPcIds.substring(0,projectPcIds.indexOf("@"));
		for (int i = 0; i < entrycolls.size(); i++) {
			table = new KDTable();
			capinfo = entrycolls.get(i);
			entrycoll = capinfo.getEntrys();
			row = table.addHeadRow();
			//key有项目ID，指标编码，科目长编码组成 	modify by yxl 20151216
			key = capinfo.getNumber()+"@"+cainfo.getLongNumber();
			table.setName(key);
			table.setID(projectPcIds);
			for (int j = 0; j < entrycoll.size(); j++) {
				entryInfo = entrycoll.get(j);
				icol = table.addColumn();
				row.getCell(icol.getColumnIndex()).setValue(entryInfo.getFieldName());
				if(FieldType.TEXT.equals(entryInfo.getFieldType())){
					initColumnForText(icol);
				}else if(FieldType.NUMBER.equals(entryInfo.getFieldType())){
					initColumnForNum(icol);
				}else if(FieldType.DATE.equals(entryInfo.getFieldType())){
					initColumnForDate(icol);
				}else if(FieldType.PRODUCTYPE.equals(entryInfo.getFieldType())){
					initColumnForF7(icol,"com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
				}else if(FieldType.BASEUNIT.equals(entryInfo.getFieldType())){
					initColumnForF7(icol,"com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
				}else if(FieldType.COMPUTE.equals(entryInfo.getFieldType())){
					initColumnForNum(icol);
					contentMap.put(key+"@"+entryInfo.getFieldType().getName()+j,entryInfo.getFcontent());
				}else if(FieldType.BUILDNUM.equals(entryInfo.getFieldType())){
					initColumnForBuildNum(icol,realPorjectId);
					icol.setRequired(true);
				}
				if(entryInfo.isFieldInput()){
					icol.setRequired(true);
				}
				if(entryInfo.isFieldHide()){
					icol.getStyleAttributes().setHided(true);
				}
				icol.setKey(entryInfo.getFieldType().getName()+j);
			}
			
			table.addKDTEditListener(new KDTEditAdapter(){
	    		public void editStopped(KDTEditEvent e) {
	    			table_editStopped(e);
	    		}
			});
			kdc = new KDContainer();
			kdc.setName(key);
			kdc.getContentPane().setLayout(new BorderLayout(0,0));
			kdc.getContentPane().add(table,BorderLayout.CENTER);
			addLine = new KDWorkButton("新增行");
			insetLine = new KDWorkButton("插入行");
			removeLine = new KDWorkButton("删除行");
			addLine.setName("addLine");
			insetLine.setName("insetLine");
			removeLine.setName("removeLine");
			addLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
			insetLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
			removeLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
			kdc.addButton(addLine);
			kdc.addButton(insetLine);
			kdc.addButton(removeLine);
			MyActionListener baseAL = new MyActionListener(table);
			addLine.addActionListener(baseAL);
			insetLine.addActionListener(baseAL);
			removeLine.addActionListener(baseAL);
			kDTabbedPane1.add(cainfo.getName()+capinfo.getIndexType().getName(),kdc);
			tables.put(key,table);
			containers.put(key,kdc);
			priceIndexEntrys.put(key,entrycoll);
			buttons.add(addLine);
			buttons.add(insetLine);
			buttons.add(removeLine);
			tabNames[i] = key;
		} 
		costTabMap.put(cainfo.getLongNumber(),tabNames);
	}
	
	public void storeIndexData(){
		KDTable table = null;
		Object cellValue = null;
		String key = null;
		BuildPriceIndexEindexDataCollection indexDatas = editData.getEindexData();
		indexDatas.clear();
		BuildPriceIndexEindexDataInfo indexDataInfo = null;
		BuildPriceIndexEindexDataEtextDataInfo textInfo = null;
		BuildPriceIndexEindexDataEdateDataInfo dateInfo = null;
		BuildPriceIndexEindexDataEnumberDataInfo dataInfo = null;
		BuildPriceIndexEindexDataEproductTypeInfo ptInfo = null;
		BuildPriceIndexEindexDataEbaseUnitInfo buInfo = null;
		BuildPriceIndexEindexDataEbuildNumberInfo bnInfo = null; 
		for(Iterator<String> it=tables.keySet().iterator(); it.hasNext();) {
			key = it.next();
			table = tables.get(key);
			if(table != null){
				for(int i = 0; i < table.getRowCount3(); i++) {
					indexDataInfo = new BuildPriceIndexEindexDataInfo();
					indexDataInfo.setRecordSeq(key+"@"+i);
					for (int j = 0; j < table.getColumnCount(); j++) {
						cellValue = table.getCell(i,j).getValue();
						if(cellValue instanceof String){
							textInfo = new BuildPriceIndexEindexDataEtextDataInfo();
							textInfo.setRecordSeq(i+"!"+j);
							textInfo.setTextData((String)cellValue);
							indexDataInfo.getEtextData().add(textInfo);
						}else if(cellValue instanceof Date){
							dateInfo = new BuildPriceIndexEindexDataEdateDataInfo();
							dateInfo.setRecordSeq(i+"!"+j);
							dateInfo.setDateData((Date)cellValue);
							indexDataInfo.getEdateData().add(dateInfo);
						}else if(cellValue instanceof BigDecimal){
							dataInfo = new BuildPriceIndexEindexDataEnumberDataInfo();
							dataInfo.setRecordSeq(i+"!"+j);
							dataInfo.setNumberData((BigDecimal)cellValue);
							indexDataInfo.getEnumberData().add(dataInfo);
						}else if(cellValue instanceof ProductTypeInfo){
							ptInfo = new BuildPriceIndexEindexDataEproductTypeInfo();
							ptInfo.setRecordSeq(i+"!"+j);
							ptInfo.setProductType((ProductTypeInfo)cellValue);
							indexDataInfo.getEproductType().add(ptInfo);
						}else if(cellValue instanceof MeasureUnitInfo){
							buInfo = new BuildPriceIndexEindexDataEbaseUnitInfo();
							buInfo.setRecordSeq(i+"!"+j);
							buInfo.setBaseUnit((MeasureUnitInfo)cellValue);
							indexDataInfo.getEbaseUnit().add(buInfo);
						}else if(cellValue instanceof BuildNumberInfo){
							bnInfo = new BuildPriceIndexEindexDataEbuildNumberInfo();
							bnInfo.setRecordSeq(i+"!"+j);
							bnInfo.setBuildNumber((BuildNumberInfo)cellValue);
							indexDataInfo.getEbuildNumber().add(bnInfo);
						}
					}
					indexDatas.add(indexDataInfo);
				}
			}
		}
	}

	public void loadIndexData(){
		BuildPriceIndexEindexDataCollection indexDatas = editData.getEindexData();
		if(indexDatas.size() > 0){
			Map<String,BuildPriceIndexEindexDataInfo> indexDataMap = new HashMap<String,BuildPriceIndexEindexDataInfo>();
			String key = null;
			String recordSeq = null;
			Map<String,Integer> rowCount = new HashMap<String,Integer>();
			for(int i = 0; i < indexDatas.size(); i++) {
				recordSeq = indexDatas.get(i).getRecordSeq();
				indexDataMap.put(recordSeq,indexDatas.get(i));
				key = recordSeq.substring(0,recordSeq.lastIndexOf('@'));
				if(rowCount.containsKey(key))
					rowCount.put(key,rowCount.get(key)+1);
				else
					rowCount.put(key,1);
			}
			KDTable table = null;
			IRow row = null;
			CostAccountPriceIndexEntryCollection entrycoll = null;
			CostAccountPriceIndexEntryInfo entryInfo = null;
			Map<String,BuildPriceIndexEindexDataEtextDataInfo> textMap = new HashMap<String,BuildPriceIndexEindexDataEtextDataInfo>();
			Map<String,BuildPriceIndexEindexDataEdateDataInfo> dateMap = new HashMap<String,BuildPriceIndexEindexDataEdateDataInfo>();
			Map<String,BuildPriceIndexEindexDataEnumberDataInfo> numberMap = new HashMap<String,BuildPriceIndexEindexDataEnumberDataInfo>();
			Map<String,BuildPriceIndexEindexDataEproductTypeInfo> productMap = new HashMap<String,BuildPriceIndexEindexDataEproductTypeInfo>();
			Map<String,BuildPriceIndexEindexDataEbaseUnitInfo> baseUnitMap = new HashMap<String,BuildPriceIndexEindexDataEbaseUnitInfo>();
			Map<String,BuildPriceIndexEindexDataEbuildNumberInfo> buildMap = new HashMap<String,BuildPriceIndexEindexDataEbuildNumberInfo>();
			BuildPriceIndexEindexDataInfo indexDataInfo = null;
//			BuildPriceIndexEindexDataEtextDataInfo textInfo = null;
//			BuildPriceIndexEindexDataEdateDataInfo dateInfo = null;
//			BuildPriceIndexEindexDataEnumberDataInfo dataInfo = null;
//			BuildPriceIndexEindexDataEproductTypeInfo ptInfo = null;
//			BuildPriceIndexEindexDataEbaseUnitInfo buInfo = null;
//			BuildPriceIndexEindexDataEbuildNumberInfo bnInfo = null; 
			for(Iterator<String> it=tables.keySet().iterator(); it.hasNext();) {
				key = it.next();
				table = tables.get(key);
				entrycoll = priceIndexEntrys.get(key);
				if(rowCount.get(key) == null)
					continue;
				for(int i = 0; i < rowCount.get(key); i++) {
					row = table.addRow();
					indexDataInfo = indexDataMap.get(key+"@"+i);
//					BuildPriceIndexEindexDataEtextDataCollection bp = indexDataInfo.getEtextData();
					getTextMaps(indexDataInfo.getEtextData(),textMap);
					getNumberMaps(indexDataInfo.getEnumberData(),numberMap);
					getDateMaps(indexDataInfo.getEdateData(),dateMap);
					getProductMaps(indexDataInfo.getEproductType(),productMap);
					getBaseUnitMaps(indexDataInfo.getEbaseUnit(),baseUnitMap);
					getBuildMaps(indexDataInfo.getEbuildNumber(),buildMap);
					for(int j = 0; j < table.getColumnCount(); j++) {
						entryInfo = entrycoll.get(j);
						recordSeq = i+"!"+j;
						if(FieldType.TEXT.equals(entryInfo.getFieldType()) && textMap.containsKey(recordSeq)){
							row.getCell(j).setValue(textMap.get(recordSeq).getTextData());
						}else if(FieldType.NUMBER.equals(entryInfo.getFieldType()) && numberMap.containsKey(recordSeq)){
							row.getCell(j).setValue(numberMap.get(recordSeq).getNumberData());
						}else if(FieldType.COMPUTE.equals(entryInfo.getFieldType()) && numberMap.containsKey(recordSeq)){
							row.getCell(j).setValue(numberMap.get(recordSeq).getNumberData());
						}else if(FieldType.DATE.equals(entryInfo.getFieldType()) && dateMap.containsKey(recordSeq)){
							row.getCell(j).setValue(dateMap.get(recordSeq).getDateData());
						}else if(FieldType.PRODUCTYPE.equals(entryInfo.getFieldType()) && productMap.containsKey(recordSeq)){
							row.getCell(j).setValue(productMap.get(recordSeq).getProductType());
						}else if(FieldType.BASEUNIT.equals(entryInfo.getFieldType()) && baseUnitMap.containsKey(recordSeq)){
							row.getCell(j).setValue(baseUnitMap.get(recordSeq).getBaseUnit());
						}else if(FieldType.BUILDNUM.equals(entryInfo.getFieldType()) && buildMap.containsKey(recordSeq)){
							row.getCell(j).setValue(buildMap.get(recordSeq).getBuildNumber());
						}
					}
				}
			}
		}
	}
	
	public void getTextMaps(BuildPriceIndexEindexDataEtextDataCollection colls,Map<String,BuildPriceIndexEindexDataEtextDataInfo> map){
		map.clear();
		if(colls.size() == 0)
			return;
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}
	
	public void getNumberMaps(BuildPriceIndexEindexDataEnumberDataCollection colls,Map<String,BuildPriceIndexEindexDataEnumberDataInfo> map){
		map.clear();
		if(colls.size() == 0)
			return;
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}
	
	public void getDateMaps(BuildPriceIndexEindexDataEdateDataCollection colls,Map<String,BuildPriceIndexEindexDataEdateDataInfo> map){
		map.clear();
		if(colls.size() == 0)
			return;
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}
	
	public void getProductMaps(BuildPriceIndexEindexDataEproductTypeCollection colls,Map<String,BuildPriceIndexEindexDataEproductTypeInfo> map){
		map.clear();
		if(colls.size() == 0)
			return;
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}
	
	public void getBaseUnitMaps(BuildPriceIndexEindexDataEbaseUnitCollection colls,Map<String,BuildPriceIndexEindexDataEbaseUnitInfo> map){
		map.clear();
		if(colls.size() == 0)
			return;
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}
	
	public void getBuildMaps(BuildPriceIndexEindexDataEbuildNumberCollection colls,Map<String,BuildPriceIndexEindexDataEbuildNumberInfo> map){
		map.clear();
		if(colls.size() == 0)
			return;
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}

	private void rebuildCostAccount() {
		//rebuild costaccount
    	CostAccountPromptBox selector = new CostAccountPromptBox(this);
		KDBizPromptBox prmtCostAccount = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				String str = null;
				if (o != null && o instanceof CostAccountInfo) {
					str = ((CostAccountInfo) o).getLongNumber().replace('!', '.');
				}
				return str;
			}
		};
		prmtCostAccount.setSelector(selector);
		prmtCostAccount.setEnabledMultiSelection(false);
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");	
		prmtCostAccount.setCommitFormat("$longNumber$");
		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID));
		if(editData.getProjectId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", editData.getProjectId()));
		}else {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", "error"));
		}
		entityView.setFilter(filter);
		prmtCostAccount.setEntityViewInfo(entityView);
		kdtEntrys.getColumn("accountNumber").setEditor(caEditor);
		ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
		kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$longNumber$"));
		kdtEntrys.getColumn("accountNumber").setRenderer(kdtCostEntries_costAccount_OVR);
	}
    
    public void kdtEntrys_editStopped(KDTEditEvent e) {
    	if(e.getColIndex() == kdtEntrys.getColumnIndex("accountNumber")){
    		CostAccountInfo cainfo=(CostAccountInfo)kdtEntrys.getCell(e.getRowIndex(),e.getColIndex()).getValue();
    		if(cainfo != null) {
    			kdtEntrys.getCell(e.getRowIndex(),"accountName").setValue(cainfo.getName());
    		}
    	}else if(e.getColIndex() == kdtEntrys.getColumnIndex("isInput")){
    		CostAccountInfo cainfo=(CostAccountInfo)kdtEntrys.getCell(e.getRowIndex(),"accountNumber").getValue();
    		if((Boolean)kdtEntrys.getCell(e.getRowIndex(),e.getColIndex()).getValue()){
    			if(cainfo != null)
    				initIndexTable(cainfo,(String)kdtEntrys.getCell(e.getRowIndex(),"projectId").getValue());
    		}else if(cainfo != null)
    			removeIndexTable(cainfo);
    	}
    }
    
    private void initColumnForBuildNum(IColumn icol, String projectId){
    	KDBizPromptBox box = new KDBizPromptBox();
    	box.setQueryInfo("com.kingdee.eas.fdc.costindexdb.database.app.BuildNumberQuery");
    	box.setDisplayFormat("$number$");
    	box.setEditFormat("$number$");
    	box.setCommitFormat("$number$");
    	EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(projectId != null)
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectId));
		entityView.setFilter(filter);
		box.setEntityViewInfo(entityView);
    	KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
    	ObjectValueRender kdtEntrys_baseUnit_OVR = new ObjectValueRender();
    	kdtEntrys_baseUnit_OVR.setFormat(new BizDataFormat("$name$"));
    	icol.setEditor(editor);
    	icol.setRenderer(kdtEntrys_baseUnit_OVR);
    }
    
    private void initColumnForF7(IColumn icol, String queryInfo){
    	KDBizPromptBox box = new KDBizPromptBox();
		box.setQueryInfo(queryInfo);
		box.setDisplayFormat("$number$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
		ObjectValueRender kdtEntrys_baseUnit_OVR = new ObjectValueRender();
        kdtEntrys_baseUnit_OVR.setFormat(new BizDataFormat("$name$"));
		icol.setEditor(editor);
		icol.setRenderer(kdtEntrys_baseUnit_OVR);
    }
    
    private void initColumnForText(IColumn icol){
    	KDTextField txt = new KDTextField();
		txt.setMaxLength(200);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(txt);
		icol.setEditor(editor);
    }

	private void initColumnForNum(IColumn icol){
		KDFormattedTextField kdtEntrys_pointValue_TextField = new KDFormattedTextField();
		kdtEntrys_pointValue_TextField.setHorizontalAlignment(2);
        kdtEntrys_pointValue_TextField.setDataType(1);
        kdtEntrys_pointValue_TextField.setMinimumValue(new BigDecimal("-1.0E26"));
    	kdtEntrys_pointValue_TextField.setMaximumValue(new BigDecimal("1.0E26"));
    	kdtEntrys_pointValue_TextField.setPrecision(2);
    	KDTDefaultCellEditor kdtEntrys_pointValue_CellEditor = new KDTDefaultCellEditor(kdtEntrys_pointValue_TextField);
    	icol.setEditor(kdtEntrys_pointValue_CellEditor);
	}
	
	private void initColumnForDate(IColumn icol){
		KDDatePicker box = new KDDatePicker();
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
		icol.setEditor(editor);
		icol.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
    }
    
    class MyActionListener implements ActionListener{
    	private KDTable table;
    	
    	public MyActionListener() {
		}
    	public MyActionListener(KDTable tab) {
    		table = tab;
		}
    	public void actionPerformed(ActionEvent e) {
    		String type = ((KDWorkButton)e.getSource()).getName();
    		IRow row = null;
    		if("addLine".equals(type)){
    			row = table.addRow();
    			if(table == kdtEntrys){
    				row.getCell("projectId").setValue(editData.getProjectId()+"@999");
    				row.getCell("isInput").setValue(Boolean.FALSE);
    			}else{
//    				boolean flse = false;
//    		    	for(int i = 0; i < table.getColumnCount(); i++) {
//    		    		if(table.getColumnKey(i).startsWith("BUILDNUM")){
//    		    			flse = true;
//    		    			break;
//    		    		}
//    		    	}
    				//判断这个table有没有公式列
    				for(int i = 0; i < table.getColumnCount(); i++) {
    					if(table.getColumnKey(i).startsWith("COMPUTE")){
    						//得到公式内容，解析内容，填充数据
    						praseContent(table.getID(),table.getName(),table.getColumnKey(i),row,-1);
    					}
					}
    			}
    		}else if("insetLine".equals(type)){
    			if(table.getSelectManager().size() > 0) {
    	            int top = table.getSelectManager().get().getTop();
    	            if(isTableColumnSelected(table))
    	            	row = table.addRow();
    	            else
    	            	row = table.addRow(top);
    	        } else {
    	        	row = table.addRow();
    	        }
    			if(table == kdtEntrys){
    				row.getCell("projectId").setValue(editData.getProjectId());
    				row.getCell("isInput").setValue(Boolean.FALSE);
    			}else{
//    				boolean flse = false;
//    		    	for(int i = 0; i < table.getColumnCount(); i++) {
//    		    		if(table.getColumnKey(i).startsWith("BUILDNUM")){
//    		    			flse = true;
//    		    		}
//    		    	}
    				//判断这个table有没有公式列
    				for(int i = 0; i < table.getColumnCount(); i++) {
    					if(table.getColumnKey(i).startsWith("COMPUTE")){
    						//得到公式内容，解析内容，填充数据
    						praseContent(table.getID(),table.getName(),table.getColumnKey(i),row,-1);
    					}
					}
    			}
    		}else if("removeLine".equals(type)){
    			if(table.getSelectManager().size() == 0){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        int top = table.getSelectManager().get().getTop();
    	        row = table.getRow(top);
    	        if(row == null){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        if(table == kdtEntrys && (Boolean)row.getCell("isInput").getValue()){
    	        	MsgBox.showInfo("科目已录入指标数据，不能删除！");
    	            return;
    	        }
    	        table.removeRow(top);
    		}
    	}
    }
    
    private void table_editStopped(KDTEditEvent e) {
    	if(e.getColIndex()==-1||e.getRowIndex()==-1)return;
    	KDTable table = (KDTable)e.getSource();
    	IRow row = table.getRow(e.getRowIndex());
    	if(table.getColumnKey(e.getColIndex()).startsWith("BUILDNUM") && row.getCell(e.getColIndex()).getValue()!=null){
    		String projectId = table.getID();
    		String key = table.getName();
    		for(int i = 0; i < table.getColumnCount(); i++) {
    			if(table.getColumnKey(i).startsWith("COMPUTE")){
    				//得到公式内容，解析内容，填充数据
    				praseContent(projectId,key,table.getColumnKey(i),row,e.getColIndex());
    			}
    		}
    	}
	}
    //根据合约规划的id和成本科目的长编码取得对应的规划金额
    public BigDecimal getPlanAmount(String pcid, String costNumber){
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select pcost.FContractAssign from T_CON_ProgrammingContracCost pcost left join T_FDC_CostAccount cacc on cacc.fid=pcost.FCostAccountID ");
			builder.appendSql("where pcost.FContractID='"+pcid+"' and cacc.FlongNumber='"+costNumber+"' ");
			IRowSet rs = builder.executeQuery();
			if(rs.next())
				return rs.getBigDecimal(1);
		} catch (BOSException e) {
			handUIException(e);
		} catch (SQLException e) {
			handUIException(e);
		}
		return BigDecimal.ZERO;
    }
    
    //解析公式内容，并将得到的数据填充到单元格
    private void praseContent(String projectPcIds, String key, String colName, IRow row, int buildNumIndex){
//    	FormulaUtils fus = new FormulaUtils(content);
//		if(UIRuleUtil.isNull(content)||!fus.checkValid())
//			return ;
		String buildId = "";
    	String content = contentMap.get(key+"@"+colName);
		String costNumber = key.substring(key.indexOf("@")+1);
		String projectId = projectPcIds.substring(0,projectPcIds.indexOf("@"));
		String pcid = projectPcIds.substring(projectPcIds.indexOf("@")+1);//合约规划id
		Set<String> variableForSet = getVariableForSet(content);
		Iterator<String> iterator = variableForSet.iterator();
//		Map<String, BigDecimal> resultForMap = new HashMap<String, BigDecimal>();
		String resformat = content;
		boolean isDx = buildNumIndex==-1?false:true;
		if(isDx){
			BuildNumberInfo binfo = (BuildNumberInfo)row.getCell(buildNumIndex).getValue();
			buildId = binfo.getId().toString();
		}
		boolean isZero = false;
		while(iterator.hasNext()){
			String next = iterator.next().trim();
			int index = next.indexOf(".");
			String type = next.substring(0, index);
			String ysName = next.substring(index+1, next.length()).trim();
			BigDecimal amount = BigDecimal.ZERO;
			if("合同".equals(type) && "合同价".equals(ysName)){
				if(isDx)
					amount = UIRuleUtil.getBigDecimal(contractPrice.get(projectId+ysName+buildId+costNumber));
				else
					amount = UIRuleUtil.getBigDecimal(contractPrice.get(projectId+ysName+costNumber));
			}else if("基本要素".equals(type)){
				if(isDx)
					amount = UIRuleUtil.getBigDecimal(baseAndSingle.get(projectId+ysName+buildId));
				else
					amount = UIRuleUtil.getBigDecimal(baseAndSingle.get(projectId+ysName));
			}else if("单项要素".equals(type)){
				if("规划金额".equals(ysName)){
					amount = UIRuleUtil.getBigDecimal(getPlanAmount(pcid,costNumber));
				}else{
					if(isDx){
						String keyValue = projectId+ysName+buildId+costNumber;
						if(baseAndSingle.containsKey(keyValue))
							amount = UIRuleUtil.getBigDecimal(baseAndSingle.get(keyValue));
						else{
							while(keyValue.lastIndexOf("!") != -1){
								keyValue = keyValue.substring(0,keyValue.lastIndexOf("!"));
								if(baseAndSingle.containsKey(keyValue)){
									amount = UIRuleUtil.getBigDecimal(baseAndSingle.get(keyValue));
									break;
								}
							}
						}
					}else{
						String keyValue = projectId+ysName+costNumber;
						if(baseAndSingle.containsKey(keyValue))
							amount = UIRuleUtil.getBigDecimal(baseAndSingle.get(keyValue));
						else{
							while(keyValue.lastIndexOf("!") != -1){
								keyValue = keyValue.substring(0,keyValue.lastIndexOf("!"));
								if(baseAndSingle.containsKey(keyValue)){
									amount = UIRuleUtil.getBigDecimal(baseAndSingle.get(keyValue));
									break;
								}
							}
						}
					}
				}
			}else if("专业要素".equals(type)){
				if(isDx)
					amount = UIRuleUtil.getBigDecimal(zySplit.get(projectId+ysName+buildId+costNumber));
				else
					amount = UIRuleUtil.getBigDecimal(zySplit.get(projectId+ysName+costNumber));
			}
			if(amount.compareTo(BigDecimal.ZERO) == 0){
				isZero = true;
				break;
			}
			resformat = resformat.replaceAll(next, String.valueOf(amount));
//			resultForMap.put(next, amount);
		}
		if(isZero){
			row.getCell(colName).setValue(BigDecimal.ZERO);
		}else{
			FormulaUtils formulaUtils = new FormulaUtils(resformat);
			row.getCell(colName).setValue(UIRuleUtil.getBigDecimal(formulaUtils.getResult()).setScale(2,RoundingMode.HALF_UP));
		}
//		row.getCell(colName).setValue(formulaUtils.getResult());
    }
    
    
    private Set<String> getVariableForSet(String content){
    	String pattText = "+-*/()";
		char[] charArray = content.toCharArray();
		StringBuffer sb = new StringBuffer();
		Set<String> listSet = new HashSet<String>();
		for (int i = 0; i < charArray.length; i++) {
			String split = String.valueOf(charArray[i]);
			if(pattText.indexOf(split)<0){
				sb.append(split);
			}else{
				if(UIRuleUtil.isNotNull(sb.toString()))
					listSet.add(sb.toString());
				sb = new StringBuffer();
			}
		}
		if(UIRuleUtil.isNotNull(sb.toString()))
			listSet.add(sb.toString());
		return listSet;
    }
    
    private void calcData(String content, int index1, int colIndex, IRow row, String calcWay){
    	//基本要素 basePointMap
    	//单项要素singlePointMap
    	
    	//TODO
    }
    
    
    public SelectorItemCollection getCostPriceSelectors(){
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("number");
    	sic.add("name");
    	sic.add("indexType.id");
    	sic.add("indexType.number");
    	sic.add("indexType.name");
    	sic.add("Entrys.fieldName");
    	sic.add("Entrys.fieldHide");
    	sic.add("Entrys.fieldInput");
    	sic.add("Entrys.fcontent");
    	sic.add("Entrys.fieldType");
    	return sic;
    }
    
    protected void applyDefaultValue(IObjectValue vo) {

    }
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("projectId");
    	sic.add("EindexData.id");
    	sic.add("EindexData.recordSeq");
    	sic.add("EindexData.EtextData.id");
    	sic.add("EindexData.EtextData.recordSeq");
    	sic.add("EindexData.EtextData.textData");
    	sic.add("EindexData.EnumberData.id");
    	sic.add("EindexData.EnumberData.recordSeq");
    	sic.add("EindexData.EnumberData.numberData");
    	sic.add("EindexData.EdateData.id");
    	sic.add("EindexData.EdateData.recordSeq");
    	sic.add("EindexData.EdateData.dateData");
    	sic.add("EindexData.EproductType.id");
    	sic.add("EindexData.EproductType.recordSeq");
    	sic.add("EindexData.EproductType.productType.id");
    	sic.add("EindexData.EproductType.productType.number");
    	sic.add("EindexData.EproductType.productType.name");
    	sic.add("EindexData.EbaseUnit.id");
    	sic.add("EindexData.EbaseUnit.recordSeq");
    	sic.add("EindexData.EbaseUnit.baseUnit.id");
    	sic.add("EindexData.EbaseUnit.baseUnit.number");
    	sic.add("EindexData.EbaseUnit.baseUnit.name");
    	sic.add("EindexData.EbuildNumber.id");
    	sic.add("EindexData.EbuildNumber.recordSeq");
    	sic.add("EindexData.EbuildNumber.buildNumber.id");
    	sic.add("EindexData.EbuildNumber.buildNumber.number");
    	sic.add("EindexData.EbuildNumber.buildNumber.name");
    	return sic;
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	storeIndexData();
    	super.actionSave_actionPerformed(e);
    }
    
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {	
        super.storeFields();
        
    }

    public void onShow() throws Exception {
    	super.onShow();
    	initEntrys();
    	rebuildCostAccount();
    	BuildPriceIndexEntryCollection indexEntrys = editData.getEntrys();
    	BuildPriceIndexEntryInfo entryInfo = null;
    	Set<String> projectIdSet = new HashSet<String>();
    	String projectPcIds = null;
    	for(int i = 0; i < indexEntrys.size(); i++) {
    		entryInfo = indexEntrys.get(i);
			if(entryInfo.isIsInput() && entryInfo.getAccountNumber() != null){
				projectPcIds = entryInfo.getProjectId();
				initIndexTable(entryInfo.getAccountNumber(),projectPcIds);
				projectIdSet.add(projectPcIds.substring(0,projectPcIds.indexOf("@")));
			}
		}
    	loadIndexData();
    	if(OprtState.VIEW.equals(getOprtState())){
    		setBuutonAndTableState(false);
    	}
    	initDatas(projectIdSet);
    	//新增时将默认值带出
//    	if(table.getColumnKey(e.getColIndex()).startsWith("BUILDNUM") && row.getCell(e.getColIndex()).getValue()!=null){
//    		String projectId = table.getID();
//    		String key = table.getName();
//    		for(int i = 0; i < table.getColumnCount(); i++) {
//    			if(table.getColumnKey(i).startsWith("COMPUTE")){
//    				//得到公式内容，解析内容，填充数据
//    				praseContent(projectId,key,table.getColumnKey(i),row,e.getColIndex());
//    			}
//    		}
//    	}
    	if("ADDNEW".equals(getOprtState())){
    		BuildNumberCollection buildColl = null;
    		IBuildNumber ibn = BuildNumberFactory.getRemoteInstance();
    		KDTable table = null;
    		IRow row = null;
        	for(Iterator<String> it=tables.keySet().iterator(); it.hasNext();){
        		table = tables.get(it.next());
        		String projectId = table.getID();
        		String key = table.getName();
        		buildColl = ibn.getBuildNumberCollection("select id,name,number where curProject.id='"+projectId.substring(0,projectId.indexOf("@"))+"'");
        		//第一次循环所有列，检查是否有楼号字段
        		int buildIndex = -1;
        		for(int i = 0; i < table.getColumnCount(); i++) {
        			if(table.getColumnKey(i).startsWith("BUILDNUM")){
        				buildIndex = i;
        				break;
        			}
    			}
        		//第二次循环所有列，赋值
        		if(buildIndex == -1){
        			//没有楼号字段，则新增一行
        			row = table.addRow();
        			for(int i = 0; i < table.getColumnCount(); i++) {
        				if(table.getColumnKey(i).startsWith("COMPUTE")){
            				//得到公式内容，解析内容，填充数据
            				praseContent(projectId,key,table.getColumnKey(i),row,buildIndex);
            			}
        			}
        		}else{
        			//有楼号字段，则新增条数为楼号集合大小
        			for(int j = 0; j < buildColl.size(); j++) {
        				row = table.addRow();
        				for(int i = 0; i < table.getColumnCount(); i++) {
        					if(i == buildIndex)
    							row.getCell(i).setValue(buildColl.get(j));
        					else if(table.getColumnKey(i).startsWith("COMPUTE")){
                				//得到公式内容，解析内容，填充数据
                				praseContent(projectId,key,table.getColumnKey(i),row,buildIndex);
                			}
            			}
					}
        		}
    		}
    	}
    }
    
    private void initDatas(Set<String> projectIdSet) throws BOSException, SQLException {
//    	String sql="select fid from CT_COS_BaseAndSinglePoint where CFPROJECTID='"+editData.getProjectId()+"' and CFISLATEST=1 and CFPOINTBILLSTATUS='4AUDITTED'";
//    	bsentrys=BaseAndSinglePointEntryFactory.getRemoteInstance().getBaseAndSinglePointEntryCollection("select pointName,pointValue where parent.id in("+sql+")");
//    	bsecosts=BaseAndSinglePointEcostFactory.getRemoteInstance().getBaseAndSinglePointEcostCollection("select costAccount.id,costAccount.longNumber,costAccount.isLeaf,pointName,pointValue where parent.id in("+sql+")");
    	//capinfo.getIndexType().getNumber()+"@"+cainfo.getLongNumber();
    	String idString = getStringFromSet(projectIdSet);
    	getBaseDXForMAP(idString);
    	String contractLevel = "";
    	if(ContractStationEnum.contractSign.equals(editData.getContractStation()))
    		contractLevel = "sign";
    	else if(ContractStationEnum.contractChange.equals(editData.getContractStation()))
    		contractLevel = "change";
    	else if(ContractStationEnum.contractEnd.equals(editData.getContractStation()))
    		contractLevel = "endCal";
    	getContractPriceSplitForMAP(contractLevel,idString);
    	getZYSplitForMAP(contractLevel,idString);
		
	}

	private void setBuutonAndTableState(boolean flag){
    	for(Iterator<String> it=tables.keySet().iterator(); it.hasNext();){
			tables.get(it.next()).setEditable(flag);
		}
    	for(int i = 0; i < buttons.size(); i++) {
    		buttons.get(i).setEnabled(flag);
		}
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	KDTable table = null;
    	for(Iterator<String> it=tables.keySet().iterator(); it.hasNext();){
    		table = tables.get(it.next());
    		for(int i = 0; i < table.getColumnCount(); i++) {
				if(table.getColumn(i).isRequired()){
					for(int j = 0; j < table.getRowCount3(); j++) {
						if(FDCHelper.isEmpty(table.getCell(j,i).getValue())){
							kDTabbedPane1.setSelectedComponent(containers.get(table.getName()));
							MsgBox.showInfo("第"+(j+1)+"行的"+table.getHeadRow(0).getCell(i).getValue()+"不能为空！");
							table.getEditManager().editCellAt(j,i);
							SysUtil.abort();
						}
					}
				}
			}
		}
    }
    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        setBuutonAndTableState(true);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.BuildPriceIndexFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo objectValue = new com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setBizDate(new Date());
        objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
        Map ctx = getUIContext();
        ContractBillInfo cbinfo = (ContractBillInfo)ctx.get("contractInfo");
        if(cbinfo == null)
        	return objectValue;
        //合同阶段    “合同签订“”合同变更”“合同结算”
        objectValue.setSourceBillId((String)ctx.get("sourceBillId"));
        String contractStationType = (String)ctx.get("contractStationType");
        objectValue.setContractId(cbinfo.getId().toString());
        objectValue.setProjectId(cbinfo.getCurProject().getId().toString());
        objectValue.setCurProject(cbinfo.getCurProject());
        objectValue.setContractInfo(cbinfo.getNumber()+" "+cbinfo.getName());
//        objectValue.setContractStation()
        //RPC从response获取
        Map param = new HashMap();
		param.put("contractBillId",cbinfo.getId().toString());
		param.put("projectId",cbinfo.getCurProject().getId().toString());
		Map initData = (Map)ActionCache.get("FDCBillEditUIHandler.initData");
		if(initData==null){
			try {
				initData = ((IFDCBill)ContractBillFactory.getRemoteInstance()).fetchInitData(param);
			} catch (EASBizException e) {
				handUIException(e);
			} catch (BOSException e) {
				handUIException(e);
			} catch (Exception e) {
				handUIException(e);
			}
		}
        FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		if(orgUnitInfo==null){
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		objectValue.setOrgName(orgUnitInfo.getDisplayName());
		//签订带出合同拆分的科目，变更带出变更拆分的科目，结算带出结算拆分的科目
		KDTable table = (KDTable)ctx.get("kdtable");
		if(table != null){
			BuildPriceIndexEntryInfo entryInfo = null;
			CostAccountInfo cinfo = null;
			try {
				ICostAccount ica = CostAccountFactory.getRemoteInstance();
				//modify by yxl 分录的项目id原本只存项目id，现在把合约规划的id拼接到项目id的后面，省个字段
				String projectPcIds = null;
				for(int i = 0; i < table.getRowCount3(); i++) {
					if((Integer)table.getCell(i,"level").getValue()==0){
						entryInfo = new BuildPriceIndexEntryInfo();
						cinfo = ica.getCostAccountInfo(new ObjectUuidPK((BOSUuid)table.getCell(i,"costAccount.id").getValue()));
						entryInfo.setAccountNumber(cinfo);
						entryInfo.setAccountName(cinfo.getName());
						projectPcIds = ((BOSUuid)table.getCell(i,"costAccount.curProject.id").getValue()).toString()+"@";
						if(table.getCell(i,"programming").getValue() != null)
							projectPcIds = projectPcIds+((ProgrammingContractInfo)table.getCell(i,"programming").getValue()).getId().toString();
						else
							projectPcIds = projectPcIds+"999";
						entryInfo.setProjectId(projectPcIds);
						entryInfo.setIsInput(true);
						objectValue.getEntrys().add(entryInfo);
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			} catch (EASBizException e) {
				handUIException(e);
			}
		}
		if("sign".equals(contractStationType)){
			objectValue.setContractStation(ContractStationEnum.contractSign);
		}else if("settle".equals(contractStationType)){
			objectValue.setContractStation(ContractStationEnum.contractEnd);
		}else if("change".equals(contractStationType)){
			objectValue.setContractStation(ContractStationEnum.contractChange);
		}
		//“合同签订”“合同变更”时，带出合约规划科目，“合同结算”时，带出合同拆分科目加变更拆分科目
//		if(cbinfo.isHasSettled()){
//			
//		}else{
//			try {
//				ICostAccount ica = CostAccountFactory.getRemoteInstance();
//				String oql="where id in(select a.FCostAccountID from T_CON_ProgrammingContracCost a left join T_CON_ProgrammingContract b on a.FcontractId=b.Fid" +
//				" left join t_con_contractbill c on c.FProgrammingContract=b.Fid where c.Fid='"+cbinfo.getId().toString()+"')";
//				CostAccountCollection cacoll=ica.getCostAccountCollection(oql);
//				if(cacoll.size() > 0){
//					BuildPriceIndexEntryInfo entryInfo = null;
//					for(int i = 0; i < cacoll.size(); i++) {
//						entryInfo = new BuildPriceIndexEntryInfo();
//						entryInfo.setAccountNumber(cacoll.get(i));
//						entryInfo.setAccountName(cacoll.get(i).getName());
//						objectValue.getEntrys().add(entryInfo);
//					}
//				}
//			} catch (BOSException e) {
//				handUIException(e);
//			}
//		}
		objectValue.setProjectStation(ProjectStationEnum.runing);
		objectValue.setBuildPriceBillStatus(FDCBillStateEnum.SAVED);
        return objectValue;
    }

}