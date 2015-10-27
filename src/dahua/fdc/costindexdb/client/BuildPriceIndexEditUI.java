/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
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
import com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexCollection;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexEntryCollection;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexEntryInfo;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexFactory;
import com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexInfo;
import com.kingdee.eas.fdc.costindexdb.database.FieldType;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BuildPriceIndexEditUI extends AbstractBuildPriceIndexEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuildPriceIndexEditUI.class);
    private Map<String,KDTable> tables = null;
    private Map<String,CostAccountPriceIndexEntryCollection> priceIndexEntrys = null;
    /**
     * output class constructor
     */
    public BuildPriceIndexEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initEntrys();
    	rebuildCostAccount();
    	BuildPriceIndexEntryCollection indexEntrys = editData.getEntrys();
    	for(int i = 0; i < indexEntrys.size(); i++) {
			if(indexEntrys.get(i).isIsInput())
				initIndexTable(indexEntrys.get(i).getAccountNumber());
		}
    	loadIndexData();
    }

	private void initEntrys() {
		tables = new HashMap<String,KDTable>();
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
	}
	
	public void removeIndexTable(CostAccountInfo cainfo){
		try {
//			ICostAccountPriceIndex icapi = CostAccountPriceIndexFactory.getRemoteInstance();
			CostAccountPriceIndexCollection capcoll = null;
//			capcoll=icapi.getCostAccountPriceIndexCollection("where id in(select a.fid from CT_DAT_CostAccountPriceIndex a left join T_FDC_CostAccount b on a.CFCOSTACCOUNTID=b.fid where b.flongnumber='"+cainfo.getLongNumber()+"')");
//			IIndexType iit = IndexTypeFactory.getRemoteInstance();
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
//			filterInfo.getFilterItems().add(new FilterItemInfo("costAccount.id", cainfo.getId().toString()));
			filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", editData.getProjectId()));
			filterInfo.getFilterItems().add(new FilterItemInfo("costAccount.longnumber", cainfo.getLongNumber()));
			viewInfo.setFilter(filterInfo);
			SelectorItemCollection sic = new SelectorItemCollection();
	    	sic.add("indexType.id");
	    	sic.add("indexType.number");
	    	sic.add("indexType.name");
			viewInfo.setSelector(sic);
			capcoll=CostAccountPriceIndexFactory.getRemoteInstance().getCostAccountPriceIndexCollection(viewInfo);
			String kdcName = null;
			for (int i = 0; i < capcoll.size(); i++) {
				capcoll.get(i).getIndexType();
				kdcName=capcoll.get(i).getIndexType().getNumber()+cainfo.getLongNumber();
				Component[] components = kDTabbedPane1.getComponents();
				for (int j = 0; j < components.length; j++) {
					if(kdcName.equals(components[j].getName())){
						kDTabbedPane1.remove(components[j]);
						tables.remove(kdcName);
						break;
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} 
		
	}
	
	public void initIndexTable(CostAccountInfo cainfo){
		try {
//			ICostAccountPriceIndex icapi = CostAccountPriceIndexFactory.getRemoteInstance();
			CostAccountPriceIndexCollection capcoll = null;
//			capcoll=icapi.getCostAccountPriceIndexCollection("where id in(select a.fid from CT_DAT_CostAccountPriceIndex a left join T_FDC_CostAccount b on a.CFCOSTACCOUNTID=b.fid where b.flongnumber='"+cainfo.getLongNumber()+"')");
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", editData.getProjectId()));
			filterInfo.getFilterItems().add(new FilterItemInfo("costAccount.longnumber", cainfo.getLongNumber()));
//			filterInfo.getFilterItems().add(new FilterItemInfo("costAccount.id", cainfo.getId().toString()));
			viewInfo.setFilter(filterInfo);
			viewInfo.setSelector(getCostPriceSelectors());
			capcoll=CostAccountPriceIndexFactory.getRemoteInstance().getCostAccountPriceIndexCollection(viewInfo);
			CostAccountPriceIndexInfo capinfo = null;
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
			for (int i = 0; i < capcoll.size(); i++) {
//				capinfo = icapi.getCostAccountPriceIndexInfo(new ObjectUuidPK(capcoll.get(i).getId()),getCostPriceSelectors());
				capinfo = capcoll.get(i);
				table = new KDTable();
				entrycoll = capinfo.getEntrys();
				row = table.addHeadRow();
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
						
					}else if(FieldType.BUILDNUM.equals(entryInfo.getFieldType())){
						initColumnForF7(icol,"com.kingdee.eas.fdc.costindexdb.database.app.BuildNumberQuery");
					}
					if(entryInfo.isFieldInput()){
						icol.setRequired(true);
					}
				}
				kdc = new KDContainer();
				key = capinfo.getIndexType().getNumber()+cainfo.getLongNumber();
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
				priceIndexEntrys.put(key,entrycoll);
			}
		} catch (BOSException e1) {
			handUIException(e1);
		}  
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
						if(FieldType.TEXT.equals(entryInfo.getFieldType())){
							row.getCell(j).setValue(textMap.get(recordSeq).getTextData());
						}else if(FieldType.NUMBER.equals(entryInfo.getFieldType())){
							row.getCell(j).setValue(numberMap.get(recordSeq).getNumberData());
						}else if(FieldType.DATE.equals(entryInfo.getFieldType())){
							row.getCell(j).setValue(dateMap.get(recordSeq).getDateData());
						}else if(FieldType.PRODUCTYPE.equals(entryInfo.getFieldType())){
							row.getCell(j).setValue(productMap.get(recordSeq).getProductType());
						}else if(FieldType.BASEUNIT.equals(entryInfo.getFieldType())){
							row.getCell(j).setValue(baseUnitMap.get(recordSeq).getBaseUnit());
						}else if(FieldType.COMPUTE.equals(entryInfo.getFieldType())){
							
						}else if(FieldType.BUILDNUM.equals(entryInfo.getFieldType())){
							row.getCell(j).setValue(buildMap.get(recordSeq).getBuildNumber());
						}
					}
				}
			}
		}
	}
	
	public void getTextMaps(BuildPriceIndexEindexDataEtextDataCollection colls,Map<String,BuildPriceIndexEindexDataEtextDataInfo> map){
		map.clear();
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}
	
	public void getNumberMaps(BuildPriceIndexEindexDataEnumberDataCollection colls,Map<String,BuildPriceIndexEindexDataEnumberDataInfo> map){
		map.clear();
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}
	
	public void getDateMaps(BuildPriceIndexEindexDataEdateDataCollection colls,Map<String,BuildPriceIndexEindexDataEdateDataInfo> map){
		map.clear();
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}
	
	public void getProductMaps(BuildPriceIndexEindexDataEproductTypeCollection colls,Map<String,BuildPriceIndexEindexDataEproductTypeInfo> map){
		map.clear();
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}
	
	public void getBaseUnitMaps(BuildPriceIndexEindexDataEbaseUnitCollection colls,Map<String,BuildPriceIndexEindexDataEbaseUnitInfo> map){
		map.clear();
		for(int i = colls.size()-1; i >=0; i--) {
			map.put(colls.get(i).getRecordSeq(),colls.get(i));
		}
	}
	
	public void getBuildMaps(BuildPriceIndexEindexDataEbuildNumberCollection colls,Map<String,BuildPriceIndexEindexDataEbuildNumberInfo> map){
		map.clear();
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
    {	storeIndexData();
        super.storeFields();
    }

    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
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