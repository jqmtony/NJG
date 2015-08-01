/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryInfo;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.fdc.material.MaterialRptFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 材料合同台账
 */
public class MaterialContractRptUI extends AbstractMaterialContractRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialContractRptUI.class);
    
    
    private RetValue retValue = null;
    /**
     * output class constructor
     */
    public MaterialContractRptUI() throws Exception
    {
        super();
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        
    }

  
    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	
    	if (node == null) return;
    	
		String cuId = null;
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if(projectInfo!=null&&projectInfo.getCU()!=null)
				cuId=projectInfo.getCU().getId().toString();
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() != null) {
				FullOrgUnitInfo info = oui.getUnit();
				if(info.getCU()!=null)
					cuId = info.getCU().getId().toString();
			}
		}
		FDCClientUtils.initSupplierF7(this, this.txtSupplier, cuId == null ? SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():cuId);
		
    	if(!isSelectLeafPrj())
    		return;
    	
		if (getSelectObjId() == null) {
			return;
		}
		
		fetchData();
		fillTable();
    }

	protected String getEditUIName() {
		return MaterialConfirmBillEditUI.class.getName();
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e){
		super.prepareUIContext(uiContext, e);
		
        int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
        MaterialConfirmBillInfo info = (MaterialConfirmBillInfo)tblMain.getRow(selectRows[0]).getUserObject();
		uiContext.put("contractBillId", info.getMainContractBill().getId().toString());
	}

	protected void fetchData() throws Exception {
		RetValue param = new RetValue();
		Set ids = new HashSet();
		ids.add(this.getSelectObjId());
		param.put("curProjectIds", ids);
		if(txtSupplier.getValue()!=null){
			Set supplierIds = new HashSet();
			supplierIds.add(((SupplierInfo)txtSupplier.getValue()).getId().toString());
			param.put("supplierIds", supplierIds);
		}
		
		retValue = MaterialRptFacadeFactory.getRemoteInstance().getMaterialContractValues(param);
	}

	protected void txtSupplier_dataChanged(DataChangeEvent e) throws Exception {
		treeMain_valueChanged(null);
	}

	protected void fillTable() throws Exception {
		this.tblMain.removeRows();
		
		MaterialConfirmBillCollection materialConfirmBills = (MaterialConfirmBillCollection)retValue.get("MaterialConfirmBillCollection");
		Map rowMap = new HashMap();
		BigDecimal sumAmount = FDCHelper.ZERO;
		BigDecimal sumPayAmount = FDCHelper.ZERO;
		BigDecimal sumNotPayAmount = FDCHelper.ZERO;
		
		
		for(Iterator it = materialConfirmBills.iterator();it.hasNext();){
    		MaterialConfirmBillInfo billInfo = (MaterialConfirmBillInfo)it.next();
    		
			for(int i=0;i<billInfo.getEntrys().size();i++){
				MaterialConfirmBillEntryInfo entryInfo = billInfo.getEntrys().get(i);
				String rowMapKey = billInfo.getMainContractBill().getId().toString();
				
				if(billInfo.getMaterialContractBill()!=null)
					rowMapKey = rowMapKey + "_" + billInfo.getMaterialContractBill().getId().toString();
				else
					rowMapKey = rowMapKey + "_null";
				
				rowMapKey = rowMapKey + "_" + entryInfo.getMaterial().getId().toString();
				
				IRow row = null;
				BigDecimal quantity = FDCHelper.toBigDecimal(entryInfo.getQuantity());
				BigDecimal amount   = FDCHelper.toBigDecimal(entryInfo.getAmount());
				BigDecimal payAmount  = FDCHelper.toBigDecimal(entryInfo.get("paidAmt"));
				
				sumAmount = sumAmount.add(amount);
				sumPayAmount = sumPayAmount.add(payAmount);
				
				if(rowMap.containsKey(rowMapKey)){
					row = (IRow)rowMap.get(rowMapKey);
					quantity = quantity.add(FDCHelper.toBigDecimal(row.getCell("quantity").getValue()));
					amount   = amount.add(FDCHelper.toBigDecimal(row.getCell("amount").getValue()));
					payAmount  = payAmount.add(FDCHelper.toBigDecimal(row.getCell("payAmount").getValue()));
					
				}else{
					row = tblMain.addRow();
					row.setUserObject(billInfo);
					rowMap.put(rowMapKey, row);
				}
				BigDecimal notPayAmount = amount.subtract(payAmount);
				BigDecimal price = FDCHelper.ZERO;
				
					
				if(quantity.compareTo(FDCHelper.ZERO)!=0)
				{
					row.getCell("quantity").setValue(quantity);
					price = amount.divide(quantity, 2, BigDecimal.ROUND_HALF_UP);
				}
				if(amount.compareTo(FDCHelper.ZERO)!=0)
					row.getCell("amount").setValue(amount);
				if(payAmount.compareTo(FDCHelper.ZERO)!=0)
					row.getCell("payAmount").setValue(payAmount);
				if(notPayAmount.compareTo(FDCHelper.ZERO)!=0)
					row.getCell("notPayAmount").setValue(notPayAmount);
				if(price.compareTo(FDCHelper.ZERO)!=0)
					row.getCell("price").setValue(price);
				
				
				row.getCell("materialContractBill").setValue(billInfo.getMaterialContractBill().getNumber());
				row.getCell("materialContractName").setValue(billInfo.getMaterialContractBill().getName());
				row.getCell("materialSupplier").setValue(billInfo.getMaterialContractBill().getPartB().getName());
				if(entryInfo.getMaterial()!=null){
					row.getCell("materialNumber").setValue(entryInfo.getMaterial().getNumber());
					row.getCell("materialName").setValue(entryInfo.getMaterial().getName());
					row.getCell("model").setValue(entryInfo.getMaterial().getModel());
					row.getCell("unit").setValue(entryInfo.getMaterial().getBaseUnit().getName());
				}
				row.getCell("section").setValue(entryInfo.getSection());
				
				row.getCell("useContractBill").setValue(billInfo.getMainContractBill().getNumber());
				row.getCell("useContractName").setValue(billInfo.getMainContractBill().getName());
				row.getCell("useSupplier").setValue(billInfo.getMainContractBill().getPartB().getName());
				row.getCell("id").setValue(billInfo.getId().toString());				
			}
    		
    	}
		
		//设置合计行
		KDTFootManager footRowManager= tblMain.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){

			footRowManager = new KDTFootManager(tblMain);
			footRowManager.addFootView();
			tblMain.setFootManager(footRowManager);
			
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			
		}else{
			footRow=tblMain.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=tblMain.addFootRow(1);
			}
		}
		footRow.getCell(0).setValue("合计");
		footRow.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("left"));
		footRow.getCell("amount").setValue(sumAmount);
		footRow.getCell("payAmount").setValue(sumPayAmount);
		sumNotPayAmount = sumAmount.subtract(sumPayAmount);
		footRow.getCell("notPayAmount").setValue(sumNotPayAmount);
	}

	protected void initTable() {
		
		FDCClientUtils.initSupplierF7(this, this.txtSupplier, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	
    	tblMain.removeRows();
    	
		tblMain.getColumn("price").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("quantity").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("quantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("payAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("payAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("notPayAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("notPayAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}

	protected void initWorkButton() {		
		super.initWorkButton();		
		this.actionAddNew.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
	}
	
	protected ICoreBase getBizInterface() throws Exception
    {
        return MaterialConfirmBillFactory.getRemoteInstance();
    }
	
	/**
	 * 处理右键导出的中断，因为没有绑定Query，直接返回-1
	 * @author owen_wen 2010-12-15
	 */
	public int getRowCountFromDB() {
		return -1;
	}
}