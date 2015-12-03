/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.programming.client.CostAccountPromptBox;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostCollection;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostFactory;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostInfo;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryCollection;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryFactory;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryInfo;
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
import com.kingdee.eas.fdc.costindexdb.ContractStationEnum;
import com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillCollection;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryCollection;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryDetailCollection;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryDetailFactory;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryDetailInfo;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryFactory;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryInfo;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillFactory;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitDataType;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexCollection;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexEntryCollection;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexEntryInfo;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexFactory;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexInfo;
import com.kingdee.eas.fdc.costindexdb.database.FieldType;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

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
    private BaseAndSinglePointEntryCollection bsentrys = null;
    //单项要素
    private BaseAndSinglePointEcostCollection bsecosts = null;
    //楼号拆分单
    BuildSplitBillCollection bscoll = null;
    //用于保存科目与页签的关系，在删除页签时候用到
    Map<String,String[]> costTabMap = null;
    //保存公式内容，table的name属性加上列的key属性作为键
    Map<String,String> contentMap = null;
    
    /**
     * 获取所有基本要素、单项要素楼号集合 ，以MAP的形式缓存
     */
    private Map<String,BigDecimal> BaseDXForMAP = new HashMap<String, BigDecimal>();
    
    /**
     * 获取合同价 ，以MAP的形式缓存
     */
    private Map<String,BigDecimal> ContractPriceSplitForMAP = new HashMap<String, BigDecimal>();
    /**
     * 获取专业要素拆分 ，以MAP的形式缓存
     */
    private Map<String,BigDecimal> ZYSplitForMAP = new HashMap<String, BigDecimal>();
    
    /**
     * output class constructor
     */
    public BuildPriceIndexEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	if("ADDNEW".equals(getOprtState())){
    		String contractStationType = (String)getUIContext().get("contractStationType");
    		if("sign".equals(contractStationType)){
//    			objectValue.setContractStation(ContractStationEnum.contractSign);
    			contractStation.setSelectedItem(ContractStationEnum.contractSign);
    		}else if("settle".equals(contractStationType)){
//    			objectValue.setContractStation(ContractStationEnum.contractEnd);
    			contractStation.setSelectedItem(ContractStationEnum.contractEnd);
    		}else if("change".equals(contractStationType)){
//    			objectValue.setContractStation(ContractStationEnum.contractChange);
    			contractStation.setSelectedItem(ContractStationEnum.contractChange);
    		}
    	}
    	costTabMap = new HashMap<String,String[]>();
    	contentMap = new HashMap<String,String>();
    	//得到成本科目与指标的关联表，用于根据明细科目创建指标表
    	capcoll=CostAccountPriceIndexFactory.getRemoteInstance().getCostAccountPriceIndexCollection("select costAccount.id,costAccount.longnumber,costAccount.isLeaf,number,name,indexType.id,indexType.number,indexType.name,Entrys.fieldName,Entrys.fieldHide,Entrys.fieldInput,Entrys.fcontent,Entrys.fieldType");
    	String sql="select fid from CT_COS_BaseAndSinglePoint where CFPROJECTID='"+editData.getProjectId()+"' and CFISLATEST=1 and CFPOINTBILLSTATUS='4AUDITTED'";
    	bsentrys=BaseAndSinglePointEntryFactory.getRemoteInstance().getBaseAndSinglePointEntryCollection("select pointName,pointValue where parent.id in("+sql+")");
    	bsecosts=BaseAndSinglePointEcostFactory.getRemoteInstance().getBaseAndSinglePointEcostCollection("select costAccount.id,costAccount.longNumber,costAccount.isLeaf,pointName,pointValue where parent.id in("+sql+")");
    	//capinfo.getIndexType().getNumber()+"@"+cainfo.getLongNumber();
    	
    	getBaseDXForMAP();
    	getContractPriceSplitForMAP();
    	getZYSplitForMAP();
		
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("projectName.id", editData.getProjectId()));
		viewInfo.setFilter(filterInfo);
		SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("costAccount.id");
    	sic.add("costAccount.longNumber");
    	sic.add("costAccount.isLeaf");
    	sic.add("sourceNumber");
    	sic.add("dataType");
    	sic.add("contractLevel");
    	sic.add("entrys.id");
    	sic.add("entrys.pointName");
    	sic.add("entrys.dataValue");
    	sic.add("entrys.Details.id");
    	sic.add("entrys.Details.buildNumber.id");
    	sic.add("entrys.Details.buildNumber.name");
    	sic.add("entrys.Details.buildNumber.number");
    	sic.add("entrys.Details.modelBuild");
    	sic.add("entrys.Details.dataValue");
		viewInfo.setSelector(sic);
    	bscoll=BuildSplitBillFactory.getRemoteInstance().getBuildSplitBillCollection(viewInfo);
    	setSmallButtonStatus();
    }
    
    /**
     * 获取所有基本要素、单项要素楼号集合 ，以MAP的形式缓存
     * @throws BOSException 
     */
    private void getBaseDXForMAP() throws BOSException{
    	BuildSplitBillEntryDetailCollection splitBillCollection = BuildSplitBillEntryDetailFactory.getRemoteInstance().getBuildSplitBillEntryDetailCollection("select parent1.parent.costAccount.longnumber,parent1.parent.dataType,parent1.pointName,buildNumber.id,dataValue where parent1.parent.dataType in ('singlePoint','basePoint') and modelBuild=1 and parent1.parent.projectName.id='"+editData.getProjectId()+"'");
    	for (int i = 0; i < splitBillCollection.size(); i++) {
    		BuildSplitBillEntryDetailInfo entryInfo = splitBillCollection.get(i);
    		BuildNumberInfo buildInfo = entryInfo.getBuildNumber();  
    		String pointName = entryInfo.getParent1().getPointName(); 
    		BuildSplitDataType dataType = entryInfo.getParent1().getParent().getDataType();
    		
    		CostAccountInfo costAccount = entryInfo.getParent1().getParent().getCostAccount();
    		String costAccountLongNumber = "";
    		if(costAccount!=null)
    			costAccountLongNumber = costAccount.getLongNumber();
    		
    		BigDecimal dataValue = UIRuleUtil.getBigDecimal(entryInfo.getDataValue()); 
    		if(buildInfo==null)
    			continue;//数据类型(单项要素XXX)+要素名称+典型楼号的ID+科目长编码
    		BaseDXForMAP.put(dataType.getAlias()+pointName+buildInfo.getId()+costAccountLongNumber, dataValue);
		}
    	//直接获取要素 这样存放到时候可以根据分录是否有典型楼号 获取Value
		for(int i = bsentrys.size()-1; i >=0; i--) {
			BaseAndSinglePointEntryInfo einfo = bsentrys.get(i);
			BaseDXForMAP.put("基本要素"+einfo.getPointName(),einfo.getPointValue());
		}
        
        //获得单项要素
		for(int i = bsecosts.size()-1; i >=0; i--) {
			BaseAndSinglePointEcostInfo einfo = bsecosts.get(i);
			BaseDXForMAP.put("单项要素"+einfo.getPointName()+einfo.getCostAccount().getLongNumber(),einfo.getPointValue());
		}
    }
    
    /**
     * 获取合同价 ，以MAP的形式缓存
     * @throws BOSException 
     */
    private void getContractPriceSplitForMAP() throws BOSException{
    	BuildSplitBillEntryDetailCollection splitBillCollection = BuildSplitBillEntryDetailFactory.getRemoteInstance().getBuildSplitBillEntryDetailCollection("select parent1.parent.contractLevel,parent1.parent.costAccount.longnumber,parent1.parent.dataType,parent1.pointName,buildNumber.id,dataValue where parent1.parent.dataType ='contract' and modelBuild=1 and parent1.parent.projectName.id='"+editData.getProjectId()+"'");
    	for (int i = 0; i < splitBillCollection.size(); i++) {//获取楼号拆分数据
    		BuildSplitBillEntryDetailInfo entryInfo = splitBillCollection.get(i);
    		BuildNumberInfo buildInfo = entryInfo.getBuildNumber();  
    		String pointName = entryInfo.getParent1().getPointName();
    		BuildSplitDataType dataType = entryInfo.getParent1().getParent().getDataType();
    		
    		CostAccountInfo costAccount = entryInfo.getParent1().getParent().getCostAccount();
    		String costAccountLongNumber = "";
    		if(costAccount!=null)
    			costAccountLongNumber = costAccount.getLongNumber();
    		
    		BigDecimal dataValue = UIRuleUtil.getBigDecimal(entryInfo.getDataValue()); 
    		if(buildInfo==null)
    			continue;//数据类型(单项要素XXX)+要素名称+典型楼号的ID+科目长编码
    		ContractPriceSplitForMAP.put(dataType.getAlias()+pointName+buildInfo.getId()+costAccountLongNumber, dataValue);
		}
    	//直接获取要素 这样存放到时候可以根据分录是否有典型楼号 获取Value
    	BuildSplitBillEntryCollection entryCollection = BuildSplitBillEntryFactory.getRemoteInstance().getBuildSplitBillEntryCollection("select parent.contractLevel,parent.costAccount.longnumber,parent.dataType,pointName,buildNumber.id,dataValue where parent.dataType ='contract' and parent.projectName.id='"+editData.getProjectId()+"'");
    	for (int i = 0; i < entryCollection.size(); i++) {//获取楼号拆分数据
    		BuildSplitBillEntryInfo entryInfo = entryCollection.get(i);
    		String pointName = entryInfo.getPointName();
    		BuildSplitDataType dataType = entryInfo.getParent().getDataType();
    		
    		CostAccountInfo costAccount = entryInfo.getParent().getCostAccount();
    		String costAccountLongNumber = "";
    		if(costAccount!=null)
    			costAccountLongNumber = costAccount.getLongNumber();
    		
    		BigDecimal dataValue = UIRuleUtil.getBigDecimal(entryInfo.getDataValue()); 
    		//数据类型(单项要素XXX)+要素名称+科目长编码
    		ContractPriceSplitForMAP.put(dataType.getAlias()+pointName+costAccountLongNumber, dataValue);
		}
    }
    
    /**
     * 获取专业要素拆分 ，以MAP的形式缓存
     * @throws BOSException 
     */
    private void getZYSplitForMAP() throws BOSException{
    	BuildSplitBillEntryDetailCollection splitBillCollection = BuildSplitBillEntryDetailFactory.getRemoteInstance().getBuildSplitBillEntryDetailCollection("select parent1.parent.contractLevel,parent1.parent.costAccount.longnumber,parent1.parent.dataType,parent1.pointName,buildNumber.id,dataValue where parent1.parent.dataType ='professPoint' and modelBuild=1 and parent1.parent.projectName.id='"+editData.getProjectId()+"'");
    	for (int i = 0; i < splitBillCollection.size(); i++) {//获取楼号拆分数据
    		BuildSplitBillEntryDetailInfo entryInfo = splitBillCollection.get(i);
    		BuildNumberInfo buildInfo = entryInfo.getBuildNumber();  
    		String pointName = entryInfo.getParent1().getPointName();
    		BuildSplitDataType dataType = entryInfo.getParent1().getParent().getDataType();
    		
    		CostAccountInfo costAccount = entryInfo.getParent1().getParent().getCostAccount();
    		String costAccountLongNumber = "";
    		if(costAccount!=null)
    			costAccountLongNumber = costAccount.getLongNumber();
    		
    		BigDecimal dataValue = UIRuleUtil.getBigDecimal(entryInfo.getDataValue()); 
    		if(buildInfo==null)
    			continue;//数据类型(单项要素XXX)+要素名称+典型楼号的ID+科目长编码
    		ZYSplitForMAP.put(dataType.getAlias()+pointName+buildInfo.getId()+costAccountLongNumber, dataValue);
    	}
    	//直接获取要素 这样存放到时候可以根据分录是否有典型楼号 获取Value
    	BuildSplitBillEntryCollection entryCollection = BuildSplitBillEntryFactory.getRemoteInstance().getBuildSplitBillEntryCollection("select parent.contractLevel,parent.costAccount.longnumber,parent.dataType,pointName,buildNumber.id,dataValue where parent.dataType ='professPoint' and parent.projectName.id='"+editData.getProjectId()+"'");
    	for (int i = 0; i < entryCollection.size(); i++) {//获取楼号拆分数据
    		BuildSplitBillEntryInfo entryInfo = entryCollection.get(i);
    		String pointName = entryInfo.getPointName();
    		BuildSplitDataType dataType = entryInfo.getParent().getDataType();
    		
    		CostAccountInfo costAccount = entryInfo.getParent().getCostAccount();
    		String costAccountLongNumber = "";
    		if(costAccount!=null)
    			costAccountLongNumber = costAccount.getLongNumber();
    		
    		BigDecimal dataValue = UIRuleUtil.getBigDecimal(entryInfo.getDataValue()); 
    		//数据类型(单项要素XXX)+要素名称+科目长编码
    		ZYSplitForMAP.put(dataType.getAlias()+pointName+costAccountLongNumber, dataValue);
    	}
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
	
	public void initIndexTable(CostAccountInfo cainfo){
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
		for (int i = 0; i < entrycolls.size(); i++) {
			table = new KDTable();
			capinfo = entrycolls.get(i);
			entrycoll = capinfo.getEntrys();
			row = table.addHeadRow();
			key = capinfo.getIndexType().getNumber()+"@"+cainfo.getLongNumber();
			table.setName(key);
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
					initColumnForF7(icol,"com.kingdee.eas.fdc.costindexdb.database.app.BuildNumberQuery");
				}
				if(entryInfo.isFieldInput()){
					icol.setRequired(true);
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
				key = recordSeq.substring(0,recordSeq.indexOf('@'));
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
    			initIndexTable(cainfo);
    		}else
    			removeIndexTable(cainfo);
    	}
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
    			if(table == kdtEntrys)
    				row.getCell("isInput").setValue(Boolean.FALSE);
    			else{
    				boolean flse = false;
    		    	for(int i = 0; i < table.getColumnCount(); i++) {
    		    		if(table.getColumnKey(i).startsWith("BUILDNUM")){
    		    			flse = true;
    		    		}
    		    	}
    				//判断这个table有没有公式列
    				for(int i = 0; i < table.getColumnCount(); i++) {
    					if(table.getColumnKey(i).startsWith("COMPUTE")){
    						//得到公式内容，解析内容，填充数据
    						praseContent(contentMap.get(table.getName()+"@"+table.getColumnKey(i)),i,row,flse,table);
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
    			if(table == kdtEntrys)
    				row.getCell("isInput").setValue(Boolean.FALSE);
    			else{
    				boolean flse = false;
    		    	for(int i = 0; i < table.getColumnCount(); i++) {
    		    		if(table.getColumnKey(i).startsWith("BUILDNUM")){
    		    			flse = true;
    		    		}
    		    	}
    				//判断这个table有没有公式列
    				for(int i = 0; i < table.getColumnCount(); i++) {
    					if(table.getColumnKey(i).startsWith("COMPUTE")){
    						//得到公式内容，解析内容，填充数据
    						praseContent(contentMap.get(table.getName()+"@"+table.getColumnKey(i)),i,row,flse,table);
    					}
					}
    			}
    		}else if("removeLine".equals(type)){
    			if(table.getSelectManager().size() == 0){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        int top = table.getSelectManager().get().getTop();
    	        if(table.getRow(top) == null){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        table.removeRow(top);
    		}
    	}
    }
    
    private void table_editStopped(KDTEditEvent e) {
    	KDTable table = (KDTable)e.getSource();
    	if(e.getColIndex()==-1||e.getRowIndex()==-1)return;
    	IRow row = table.getRow(e.getRowIndex());
    	
    	boolean flse = false;
    	for(int i = 0; i < table.getColumnCount(); i++) {
    		if(table.getColumnKey(i).startsWith("BUILDNUM")){
    			flse = true;
    		}
    	}
    	for(int i = 0; i < table.getColumnCount(); i++) {
			if(table.getColumnKey(i).startsWith("COMPUTE")){
				//得到公式内容，解析内容，填充数据
				praseContent(contentMap.get(table.getName()+"@"+table.getColumnKey(i)),i,row,flse,table);
			}
		}
	}
    
    //解析公式内容，并将得到的数据填充到单元格
    private void praseContent(String content, int colIndex, IRow row,boolean isDx,KDTable table){
    	FormulaUtils fus = new FormulaUtils(content);
		if(UIRuleUtil.isNull(content)||!fus.checkValid())
			return ;
		String buildId = "P24AAABhw1g4d2rQ";
		String costNumber = table.getName().substring(table.getName().indexOf("@")+1, table.getName().length());
		Set<String> variableForSet = getVariableForSet(content);
		Iterator<String> iterator = variableForSet.iterator();
		Map<String, BigDecimal> resultForMap = new HashMap<String, BigDecimal>();
		
		String resformat = content;
		while(iterator.hasNext()){
			String next = iterator.next().trim();
			int index = next.indexOf(".");
			
			String type = next.substring(0, index);
			String ysName = next.substring(index+1, next.length());
			
			BigDecimal amount = BigDecimal.ZERO;
			if(type.equals("合同")&&ysName.equals("合同价")){
				if(isDx)
					amount = UIRuleUtil.getBigDecimal(ContractPriceSplitForMAP.get("合同"+ysName+buildId+costNumber));
				else
					amount = UIRuleUtil.getBigDecimal(ContractPriceSplitForMAP.get("合同"+ysName+costNumber));
			}
			
			if(type.equals("基本要素")){
				if(isDx)
					amount = UIRuleUtil.getBigDecimal(BaseDXForMAP.get("基础要素"+ysName+buildId));
				else
					amount = UIRuleUtil.getBigDecimal(BaseDXForMAP.get("基础要素"+ysName));
			}
			
			resformat = resformat.replaceAll(next, String.valueOf(amount));
			resultForMap.put(next, amount);
		}
		FormulaUtils formulaUtils = new FormulaUtils(resformat);
		row.getCell(colIndex).setValue(formulaUtils.getResult());
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
    	for(int i = 0; i < indexEntrys.size(); i++) {
			if(indexEntrys.get(i).isIsInput())
				initIndexTable(indexEntrys.get(i).getAccountNumber());
		}
    	loadIndexData();
    	if(OprtState.VIEW.equals(getOprtState())){
    		setBuutonAndTableState(false);
    	}
    	
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
//        Map ctx = getUIContext();
        ContractBillInfo cbinfo = (ContractBillInfo)getUIContext().get("contractInfo");
        //合同阶段    “合同签订“”合同变更”“合同结算”
        objectValue.setSourceBillId((String)getUIContext().get("sourceBillId"));
        String contractStationType = (String)getUIContext().get("contractStationType");
        objectValue.setContractId(cbinfo.getId().toString());
        objectValue.setProjectId(cbinfo.getCurProject().getId().toString());
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
        return objectValue;
    }

}