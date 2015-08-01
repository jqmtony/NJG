/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.fdc.material.MaterialRptFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 项目甲供材料月报表 
 */
public class MaterialContractMonthRptUI extends AbstractMaterialContractMonthRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialContractMonthRptUI.class);
    
    /**
     * output class constructor
     */
    public MaterialContractMonthRptUI() throws Exception
    {
        super();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
//        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	//加上这个判断，否则当日期为空时，刷新或选择树的其它节点时会报中断。Added By Owen_wen 2010-12-06
		FDCClientVerifyHelper.verifyEmpty(this, kDDatePicker1); 
		
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	
    	if (node == null) //node有可能为null，加上防卫语句，Added By Owen_wen 2010-12-06 
    		return; 
    	
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
    	{
    		tblMain.removeRows();
    		return;
    	}
    	String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			tblMain.removeRows();
			return;
		}
		
		fetchData();
		fillTable();
		this.appendFootRow();
    }

    protected String getEditUIName() {
		return MaterialConfirmBillEditUI.class.getName();
	}
    private RetValue retValue = null;
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
		Date selectDate = (Date)this.kDDatePicker1.getValue();	
		
		param.put("selectDate", selectDate);
		
		retValue = MaterialRptFacadeFactory.getRemoteInstance().getMaterialContractMonthValues(param);
	}

	protected void txtSupplier_dataChanged(DataChangeEvent e) throws Exception {
		treeMain_valueChanged(null);
	}

	protected void initTable() {
		
		FDCClientUtils.initSupplierF7(this, this.txtSupplier, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	
		tblMain.getColumn("price").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("quantity").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("quantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("LMQuantity").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("LMQuantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("sumQuantity").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("sumQuantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("LMAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("LMAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblMain.getColumn("sumAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("sumAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}
	
	protected void fillTable() throws Exception {
		this.tblMain.removeRows();
		if(this.retValue==null)
			return;
		Map contractBillMap = (HashMap)retValue.get("contractBillMap");
		Map materialMap     = (HashMap)retValue.get("materialMap");
		ArrayList mainContractList = (ArrayList)retValue.get("mainContractList");
		Map mainContractMap     = (HashMap)retValue.get("mainContractMap");
		for(Iterator it = mainContractList.iterator();it.hasNext();){
			String mainContractId = (String)it.next();
			ArrayList contractMaterialList = (ArrayList)mainContractMap.get(mainContractId);
			for(Iterator mIt = contractMaterialList.iterator();mIt.hasNext();){
				String materialId = (String)mIt.next();
				String key = mainContractId + "_" +materialId;
				ContractBillInfo contract = (ContractBillInfo)contractBillMap.get(mainContractId);
				MaterialInfo material = (MaterialInfo)materialMap.get(materialId);
				RetValue value = (RetValue)retValue.get(key);
				IRow row = tblMain.addRow();
				row.getCell("useSupplier").setValue(contract.getPartB().getName());
				row.getCell("useContractBill").setValue(contract.getNumber());
				row.getCell("useContractName").setValue(contract.getName());
				row.getCell("materialNumber").setValue(material.getNumber());
				row.getCell("materialName").setValue(material.getName());
				row.getCell("model").setValue(material.getModel());
				row.getCell("unit").setValue(material.getBaseUnit().getName());
				row.getCell("price").getStyleAttributes().setNumberFormat(FDCClientHelper.getNumberFormat(material.getPricePrecision(), true));
				row.getCell("price").setValue(value.getBigDecimal("price"));
				row.getCell("amount").getStyleAttributes().setNumberFormat(FDCClientHelper.getNumberFormat(material.getPricePrecision(), true));
				row.getCell("amount").setValue(value.getBigDecimal("amount"));
				row.getCell("quantity").getStyleAttributes().setNumberFormat(FDCClientHelper.getNumberFormat(material.getBaseUnit().getQtyPrecision(), true));
				row.getCell("quantity").setValue(value.getBigDecimal("quantity"));
				row.getCell("LMAmount").getStyleAttributes().setNumberFormat(FDCClientHelper.getNumberFormat(material.getPricePrecision(), true));
				row.getCell("LMAmount").setValue(value.getBigDecimal("LMAmount"));
				row.getCell("LMQuantity").getStyleAttributes().setNumberFormat(FDCClientHelper.getNumberFormat(material.getBaseUnit().getQtyPrecision(), true));
				row.getCell("LMQuantity").setValue(value.getBigDecimal("LMQuantity"));
				row.getCell("sumAmount").getStyleAttributes().setNumberFormat(FDCClientHelper.getNumberFormat(material.getPricePrecision(), true));
				row.getCell("sumAmount").setValue(value.getBigDecimal("sumAmount"));
				row.getCell("sumQuantity").getStyleAttributes().setNumberFormat(FDCClientHelper.getNumberFormat(material.getBaseUnit().getQtyPrecision(), true));
				row.getCell("sumQuantity").setValue(value.getBigDecimal("sumQuantity"));
			}
		}
	}

	protected void initWorkButton() {		
		super.initWorkButton();
		this.actionView.setVisible(false);
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

	public void onLoad() throws Exception {		
		super.onLoad();
		this.kDDatePicker1.setDatePattern("yyyy-MM");
	}
	
	protected boolean isFootVisible()
    {
		return true;
    }
	
	protected IRow appendFootRow() {
		return FDCTableHelper.apendFootRow(tblMain, new String[] {"LMQuantity", "quantity",
				"sumQuantity", "LMAmount", "amount", "sumAmount",});
	}
	
	/**
	 * 处理右键导出的中断，因为没有绑定Query，直接返回-1
	 * @author owen_wen 2010-12-15
	 */
	public int getRowCountFromDB() {
		return -1;
	}
}