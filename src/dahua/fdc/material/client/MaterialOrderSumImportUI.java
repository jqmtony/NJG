/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.fd2.gui.util.MsgBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryCollection;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryFactory;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumEntryFactory;
import com.kingdee.eas.fdc.material.MaterialEnterSumEntryInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumImportInfo;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryInfo;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo;
import com.kingdee.eas.fdc.material.PartAMaterialCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryFactory;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialFactory;
import com.kingdee.eas.fdc.material.PartAMaterialInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 从进场计划汇总表中导入
 */
public class MaterialOrderSumImportUI extends AbstractMaterialOrderSumImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialOrderSumImportUI.class);
    private MaterialOrderBizBillEditUI editUI;
    /**
     * output class constructor
     */
    public MaterialOrderSumImportUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	
	 public void actionView_actionPerformed(ActionEvent e) throws Exception{
		 //do nothing
	 }
	 /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception{
    	int activeRow = tblMain.getSelectManager().getActiveRowIndex();
		int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
		if(activeRow!=-1){
			ICell cell = tblMain.getRow(activeRow).getCell("chk");
			Boolean isSelected = (Boolean)cell.getValue();
			
			if(isSelected.booleanValue()){
				for(int i = 0;i<selectedRows.length;i++){
					tblMain.getCell(selectedRows[i],"chk").setValue(Boolean.FALSE);
				}
			}
			else{
				for(int i = 0;i<selectedRows.length;i++){
					tblMain.getCell(selectedRows[i],"chk").setValue(Boolean.TRUE);
				}
			}
		}
    }
    
    
    public void onLoad() throws Exception {
		super.onLoad();
		setComponentState();
		this.btnAddNew.setVisible(false);
		this.btnView.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnRefresh.setVisible(false);
		this.btnLocate.setVisible(false);
		this.btnQuery.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnAddNew.setVisible(false);
		editUI = (MaterialOrderBizBillEditUI)getUIContext().get(UIContext.OWNER);
		SupplierInfo supInfo = (SupplierInfo)getUIContext().get("suppliers");
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo(); //建立过滤条件
		filterInfo.getFilterItems().add(new FilterItemInfo("entrys.supplier.id", supInfo.getId(),CompareType.EQUALS));
		filterInfo.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		evi.setFilter(filterInfo);
		if (mainQuery == null) {
			mainQuery = new EntityViewInfo();
		}
		mainQuery.setFilter(filterInfo); //添加过滤条件
		this.execQuery();
	}
    
	public void setComponentState(){
		//设置表格多选择模式
		tblMain.getSelectManager().setSelectMode(0x0A);
	}

	//确定导入
	protected void btnImport_actionPerformed(ActionEvent e) throws Exception {
		 HashSet set = getImportDataSet();
	      if(set==null || set.size()==0)
	      {
	    	  MsgBox.show("您还没有选择需要导入的记录!");
	      }
	      else{
	    	  doImport(set);
	          destroyWindow();
	      }
	}

	private void doImport(HashSet set) throws EASBizException, BOSException
	{
		if (editUI == null || set == null)
			return;
		MaterialOrderBizBillInfo editData = (MaterialOrderBizBillInfo) editUI.getEditData();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			MaterialEnterSumImportInfo importData = (MaterialEnterSumImportInfo) iterator.next();
			MaterialOrderBizBillEntryInfo entryInfo = new MaterialOrderBizBillEntryInfo();
			entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
			if (importData.getMaterialBill() != null) {// 带出单价
				if (importData.getMaterialBill().getId() != null) {
					try {
						PartAMaterialCollection materialPartColl = PartAMaterialFactory.getRemoteInstance().getPartAMaterialCollection(
										"where contractBill.id = '" + importData.getMaterialBill().getId() + "'");
						if (UIRuleUtil.isNotNull(materialPartColl)
								&& materialPartColl.size() > 0) {
							for (int i = 0; i < materialPartColl.size(); i++) {
								PartAMaterialInfo materialPartInfo = materialPartColl.get(i);
								if (materialPartInfo != null) {
									EntityViewInfo evi = new EntityViewInfo();
									FilterInfo filterInfo = new FilterInfo(); // 建立过滤条件
									filterInfo.getFilterItems().add(new FilterItemInfo("parent.id", materialPartInfo.getId(), CompareType.EQUALS));
									filterInfo.getFilterItems().add(new FilterItemInfo("material.id", importData.getMaterial().getId(), CompareType.EQUALS));
									evi.setFilter(filterInfo);
									PartAMaterialEntryCollection materialPartEntryColl = PartAMaterialEntryFactory.getRemoteInstance().getPartAMaterialEntryCollection(evi);
									if (UIRuleUtil.isNotNull(materialPartEntryColl) && materialPartEntryColl.size() > 0) {
										for (int j = 0; j < materialPartEntryColl .size(); j++) {
											PartAMaterialEntryInfo entry = materialPartEntryColl.get(j);
											if (entry != null) {
												entryInfo.setPrice(entry.getPrice());// 得到材料明细的单价
											}
										}
									}
								}
							}
						}
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}

			}
			
			entryInfo.setCurProject(importData.getCurProject());
			entryInfo.setMaterialNumber(importData.getMaterial());
			entryInfo.setMaterialModel(importData.getModel());
			entryInfo.setUnit(importData.getUnit());
			entryInfo.setQuantity(importData.getQuantity());
			entryInfo.setArriveDate(importData.getEnterTime());
			
			MaterialEnterPlanEntryInfo enterPlan = getEnterPlan(importData, entryInfo);
			if(enterPlan!=null && enterPlan.getParent()!=null){
				entryInfo.setAriverAddr(enterPlan.getParent().getAddress());
			}
			
			entryInfo.setContractUnit(importData.getContractUnit());
			entryInfo.setContractBill(importData.getContractBill());
			entryInfo.setMaterialContract(importData.getMaterialBill());
			entryInfo.setSourceBill(importData.getEntryID());// 来源单据ID
			entryInfo.setArriveDate(importData.getEnterTime()); //进场日期			
			
			CurrencyInfo info = FDCClientHelper.getCurrencyInfo(FDCHelper.getBaseCurrency(null).getId());
			entryInfo.setCurrency(info);
			entryInfo.setExRate(FDCConstants.ONE);
			
			// 导入时，自动带出原币金额和本币金额
			if (entryInfo.getQuantity()!=null && entryInfo.getPrice()!= null){
				entryInfo.setAmount(entryInfo.getQuantity().multiply(entryInfo.getPrice()));
				entryInfo.setOriginalAmount(entryInfo.getQuantity().multiply(entryInfo.getPrice()));
			}
			
			editData.getEntrys().add(entryInfo);
			SupplierInfo supInfo = (SupplierInfo) getUIContext().get("suppliers");
			editData.setSuppliers(supInfo);
			editData.setNumber(getUIContext().get("number").toString());
		}
		editUI.loadFields();
		try {
			editUI.carryMaterialName(); // 刷新带出物料名称
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  材料订货单的分录的“送货地点”字段。取值相对应的材料进场计划单中的项目地址，可修改。 by cassiel 2010-08-26
	 *  材料进场计划汇总分录ID 找到sourceID，就是对应了材料进场计划汇总分录的材料进场计划分录的ID
	 * @param importData
	 * @param entryInfo
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private MaterialEnterPlanEntryInfo getEnterPlan(MaterialEnterSumImportInfo importData, MaterialOrderBizBillEntryInfo entryInfo) 
		throws EASBizException, BOSException {
		String enterSumID = importData.getEntryID();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("sourceBill"));
		selector.add(new SelectorItemInfo("id"));
		MaterialEnterSumEntryInfo sumEntry = null;
		sumEntry = MaterialEnterSumEntryFactory.getRemoteInstance().getMaterialEnterSumEntryInfo(new ObjectUuidPK(enterSumID));
		
		String sumEntrySourceID = sumEntry.getSourceBill();
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection _selector = new SelectorItemCollection();
		_selector.add(new SelectorItemInfo("parent.address"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", sumEntrySourceID));
		view.setFilter(filter);
		view.setSelector(_selector);
		MaterialEnterPlanEntryCollection enterEntryColls = null;
		enterEntryColls = MaterialEnterPlanEntryFactory.getRemoteInstance().getMaterialEnterPlanEntryCollection(view);
		MaterialEnterPlanEntryInfo enterPlan = enterEntryColls.get(0);
		return enterPlan;
	}

	private HashSet getImportDataSet(){
		HashSet importSet = new HashSet();
		for(int i=0;i<tblMain.getRowCount();i++ ){
			Boolean isSelected = (Boolean)tblMain.getRow(i).getCell("chk").getValue();
		    if(isSelected.booleanValue()){
		    	Object id = null;
		    	Object number = null;
		    	Object name = null;
		    	MaterialEnterSumImportInfo info  = new MaterialEnterSumImportInfo();
		    	CurProjectInfo curProject = new CurProjectInfo();
		    	id = tblMain.getRow(i).getCell("entrys.curProject.id").getValue();
		    	number = tblMain.getRow(i).getCell("entrys.curProject.number").getValue();
		    	name =tblMain.getRow(i).getCell("entrys.curProject.name").getValue();
		    	setBaseDataInfo(curProject,id,number,name);
		    	info.setCurProject(curProject);
		    	
		    	SupplierInfo supplier= new SupplierInfo();
		    	id = tblMain.getRow(i).getCell("entrys.supplier.id").getValue();
		    	number = tblMain.getRow(i).getCell("entrys.supplier.number").getValue();
		    	name = tblMain.getRow(i).getCell("entrys.supplier.name").getValue();
		    	setBaseDataInfo(supplier,id,number,name);
		    	info.setSupplier(supplier);
		    	
		    	MaterialInfo material = new MaterialInfo();
		    	id = tblMain.getRow(i).getCell("entrys.material.id").getValue();
		    	number = tblMain.getRow(i).getCell("entrys.material.number").getValue();
		    	name = tblMain.getRow(i).getCell("entrys.materialName").getValue();
		    	setBaseDataInfo(material,id,number,name);
		    	info.setMaterial(material);
		    	
		    	Object objModel = tblMain.getRow(i).getCell("entrys.model").getValue();
		    	if(objModel!=null){
		    		info.setModel(objModel.toString());
		    	}
		    	
		    	MeasureUnitInfo unit = new MeasureUnitInfo();
		    	id = tblMain.getRow(i).getCell("entrys.unit.id").getValue();
		    	number = tblMain.getRow(i).getCell("entrys.unit.number").getValue();
		    	name = tblMain.getRow(i).getCell("entrys.unit.name").getValue();
		    	setBaseDataInfo(unit,id,number,name);
		    	info.setUnit(unit);
		    	
		    	BigDecimal objQtyNum = FDCHelper.ZERO;
		    	BigDecimal objOderQtyNum = FDCHelper.ZERO;
		    	Object objQty = tblMain.getRow(i).getCell("entrys.planQuantity").getValue();
		    	if(objQty != null){
		    		objQtyNum = new BigDecimal(objQty.toString());//汇总表计划数量
		    	}
		    	Object objOderQty = tblMain.getRow(i).getCell("entrys.orderQuantity").getValue();
		    	if(objOderQty != null){
		    		objOderQtyNum = new BigDecimal(objOderQty.toString());//汇总表已订货数量
		    	}
		    	info.setQuantity(objQtyNum.subtract(objOderQtyNum));//未订货数量=计划数量-已订货数量
		    	
		    	
		    	Object objEnterTime = tblMain.getRow(i).getCell("entrys.enterTime").getValue();
		    	if(objEnterTime!=null){
		    		SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyy-MM-dd");  
		    		try{
		    		info.setEnterTime(simpleDateFormat.parse(objEnterTime.toString()));
		    		}catch(Exception e )
		    		{
		    			e.printStackTrace();
		    		}
		    	}
		    	
		    	SupplierInfo contractUnit = new SupplierInfo();
		    	id = tblMain.getRow(i).getCell("entrys.contractUnit.id").getValue();
		    	number = tblMain.getRow(i).getCell("entrys.contractUnit.number").getValue();
		    	name = tblMain.getRow(i).getCell("entrys.contractUnit.name").getValue();
		    	setBaseDataInfo(contractUnit,id,number,name);
		    	info.setContractUnit(contractUnit);
		    	
		    	ContractBillInfo contractBill = new ContractBillInfo();
		    	id = tblMain.getRow(i).getCell("contractBill.id").getValue();
		    	number = tblMain.getRow(i).getCell("contractBill.number").getValue();
		    	name = tblMain.getRow(i).getCell("contractBill.name").getValue();
		    	setContractBillInfo(contractBill,id,number,name);
		    	info.setContractBill(contractBill);
		    	
		    	ContractBillInfo materialBill = new ContractBillInfo();
		    	id = tblMain.getRow(i).getCell("materialBill.id").getValue();
		    	number = tblMain.getRow(i).getCell("materialBill.number").getValue();
		    	name = tblMain.getRow(i).getCell("materialBill.name").getValue();
		    	setContractBillInfo(materialBill,id,number,name);
		    	info.setMaterialBill(materialBill);
		    	
		    	info.setEntryID(tblMain.getRow(i).getCell("entrys.id").getValue().toString());
		    	
		    	importSet.add(info);
		    }
		}
		return importSet;
	}
	
	private void setContractBillInfo(ContractBillInfo billInfo,Object id ,Object number,Object name){
		if(id!=null)
			billInfo.setId(BOSUuid.read(id.toString()));
		 if(number!=null)
			 billInfo.setNumber(number.toString());
		 if(name!=null)
             billInfo.setName(name.toString());
	}
	
	 private void setBaseDataInfo(DataBaseInfo info,Object id ,Object number,Object name){
		 if(id!=null)
			 info.setId(BOSUuid.read(id.toString()));
		 if(number!=null)
			 info.setNumber(number.toString());
		 if(name!=null)
			 info.setName(name.toString());
	  }
	
	
	//取消导入
	protected void btnImportCancel_actionPerformed(ActionEvent e)
			throws Exception {
		destroyWindow();
	}

	protected void chkSelectAll_stateChanged(ChangeEvent e) throws Exception {
		super.chkSelectAll_stateChanged(e);
		if(this.chkSelectAll.getSelectState()==32){
			for(int i=0;i<tblMain.getRowCount3();i++){
				tblMain.getCell(i, "chk").setValue(Boolean.TRUE);
			}
		}
		else{
			for(int i=0;i<tblMain.getRowCount3();i++){
				tblMain.getCell(i, "chk").setValue(Boolean.FALSE);
			}
		}
	}
}