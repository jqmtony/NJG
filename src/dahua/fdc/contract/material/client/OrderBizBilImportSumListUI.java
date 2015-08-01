/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.fd2.gui.util.MsgBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumEntryInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumImportInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class OrderBizBilImportSumListUI extends AbstractOrderBizBilImportSumListUI
{
    private static final Logger logger = CoreUIObject.getLogger(OrderBizBilImportSumListUI.class);
    private MaterialOrderBizBillEditUI editUI;
    /**
     * output class constructor
     */
    public OrderBizBilImportSumListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

   
    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    
	/**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
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
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	 /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
		int activeRow = tblMain.getSelectManager().getActiveRowIndex();
		if(activeRow!=-1){
			ICell cell = tblMain.getRow(activeRow).getCell("number");
			Boolean isSelected = (Boolean)cell.getValue();
			if(false)
			    cell.setValue(Boolean.valueOf(false));
			else
				cell.setValue(Boolean.valueOf(true));
		}
    }

    public void onLoad() throws Exception {
		super.onLoad();
		
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

	private void doImport(HashSet set)
	{
		if(editUI==null || set ==null )
			return ;
	   MaterialEnterSumInfo editData = (MaterialEnterSumInfo)editUI.getEditData();
	   Iterator iterator = set.iterator();
	   while(iterator.hasNext())
	   {
		   MaterialEnterSumImportInfo importData= (MaterialEnterSumImportInfo)iterator.next();;
		   MaterialEnterSumEntryInfo entryInfo = new MaterialEnterSumEntryInfo();
		   entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
		   entryInfo.setCurProject(importData.getCurProject());
		   entryInfo.setSupplier(importData.getSupplier());
		   entryInfo.setMaterial(importData.getMaterial());
		   entryInfo.setModel(importData.getModel());
		   entryInfo.setUnit(importData.getUnit());
		   entryInfo.setQuantity(importData.getQuantity());
		   entryInfo.setOrderQuantity(importData.getOrderQuantity());
		   entryInfo.setEnterTime(importData.getEnterTime());
		   entryInfo.setContractUnit(importData.getContractUnit());
		   entryInfo.setContractBill(importData.getContractBill());
		   entryInfo.setMaterialBill(importData.getMaterialBill());
		   editData.getEntrys().add(entryInfo);
	   }
	   editUI.loadFields();
	   try{
	   editUI.carryMaterialName();  //刷新带出物料名称
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	}
	private HashSet getImportDataSet(){
		HashSet importSet = new HashSet();
		for(int i=0;i<tblMain.getRowCount();i++ ){
			Boolean isSelected = (Boolean)tblMain.getRow(i).getCell("selected").getValue();
		    if(isSelected.booleanValue()){
		    	Object id = null;
		    	Object number = null;
		    	Object name = null;
		    	MaterialEnterSumImportInfo info  = new MaterialEnterSumImportInfo();
		    	CurProjectInfo curProject = new CurProjectInfo();
		    	id = tblMain.getRow(i).getCell("curProjectId").getValue();
		    	number = tblMain.getRow(i).getCell("curProjectNum").getValue();
		    	name =tblMain.getRow(i).getCell("curProject").getValue();
		    	setBaseDataInfo(curProject,id,number,name);
		    	info.setCurProject(curProject);
		    	
		    	SupplierInfo supplier= new SupplierInfo();
		    	id = tblMain.getRow(i).getCell("supplierId").getValue();
		    	number = tblMain.getRow(i).getCell("supplierNum").getValue();
		    	name = tblMain.getRow(i).getCell("supplier").getValue();
		    	setBaseDataInfo(supplier,id,number,name);
		    	info.setSupplier(supplier);
		    	
		    	MaterialInfo material = new MaterialInfo();
		    	id = tblMain.getRow(i).getCell("materialId").getValue();
		    	number = tblMain.getRow(i).getCell("materialNum").getValue();
		    	name = tblMain.getRow(i).getCell("materialName").getValue();
		    	setBaseDataInfo(material,id,number,name);
		    	info.setMaterial(material);
		    	
		    	Object objModel = tblMain.getRow(i).getCell("model").getValue();
		    	if(objModel!=null)
		    	{
		    		info.setModel(objModel.toString());
		    	}
		    	
		    	MeasureUnitInfo unit = new MeasureUnitInfo();
		    	id = tblMain.getRow(i).getCell("unitId").getValue();
		    	number = tblMain.getRow(i).getCell("unitNum").getValue();
		    	name = tblMain.getRow(i).getCell("unit").getValue();
		    	setBaseDataInfo(unit,id,number,name);
		    	info.setUnit(unit);
		    	
		    	Object objQty = tblMain.getRow(i).getCell("quantity").getValue();
		    	if(objQty!=null)
		    		info.setQuantity(new BigDecimal(objQty.toString()));
		    	
		    	
		    	Object objEnterTime = tblMain.getRow(i).getCell("enterTime").getValue();
		    	if(objEnterTime!=null){
		    		SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyy-mm-dd");  
		    		try{
		    		info.setEnterTime(simpleDateFormat.parse(objEnterTime.toString()));
		    		}catch(Exception e )
		    		{
		    			e.printStackTrace();
		    		}
		    	}
		    	
		    	SupplierInfo contractUnit = new SupplierInfo();
		    	id = tblMain.getRow(i).getCell("contractUnitId").getValue();
		    	number = tblMain.getRow(i).getCell("contractUnitNum").getValue();
		    	name = tblMain.getRow(i).getCell("contractUnit").getValue();
		    	setBaseDataInfo(contractUnit,id,number,name);
		    	info.setContractUnit(contractUnit);
		    	
		    	ContractBillInfo contractBill = new ContractBillInfo();
		    	id = tblMain.getRow(i).getCell("contractBillId").getValue();
		    	number = tblMain.getRow(i).getCell("contractBillNum").getValue();
		    	name = tblMain.getRow(i).getCell("contractBill").getValue();
		    	setContractBillInfo(contractBill,id,number,name);
		    	info.setContractBill(contractBill);
		    	
		    	ContractBillInfo materialBill = new ContractBillInfo();
		    	id = tblMain.getRow(i).getCell("materialBillId").getValue();
		    	number = tblMain.getRow(i).getCell("materialBillNum").getValue();
		    	name = tblMain.getRow(i).getCell("materialBill").getValue();
		    	setContractBillInfo(materialBill,id,number,name);
		    	info.setMaterialBill(materialBill);
		    	
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
	
	 private void setBaseDataInfo(DataBaseInfo info,Object id ,Object number,Object name)
	  {
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

    
    

}