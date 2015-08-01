/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;



/**
 * output class name
 */
public class MaterialConfirmBillListUI extends AbstractMaterialConfirmBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialConfirmBillListUI.class);

    /**
     * output class constructor
     */
    public MaterialConfirmBillListUI() throws Exception
    {
        super();
    }

    protected SelectorItemCollection genBillQuerySelector() {
    	
    	SelectorItemCollection selectors = new SelectorItemCollection();
	    	selectors.add("id");
	    	selectors.add("state");
			selectors.add("number");
			selectors.add("supplyDate");
			selectors.add("materialContractBill.number");
			selectors.add("materialContractBill.name");
			selectors.add("materialContractBill.partB.name");
			selectors.add("materialContractBill.amount");
			selectors.add("materialContractBill.currency.name");
			
			selectors.add("mainContractBill.number");
			selectors.add("mainContractBill.name");
			selectors.add("mainContractBill.partB.name");
			
			selectors.add("description");
			selectors.add("supplyAmt");
	    	selectors.add("toDateSupplyAmt");
	    	selectors.add("confirmAmt");
	    	selectors.add("toDateConfirmAmt");
	    	selectors.add("paidAmt");
	    	selectors.add("confirmAmt");
	    	selectors.add("toDatePaidAmt");
	    	selectors.add("creator.name");
	    	selectors.add("createTime");
	    	selectors.add("auditor.name");
	    	selectors.add("auditTime");
	    	return selectors;
    }
    
    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(getMainTable());
        String billId = getSelectedKeyValue(getMainTable());
        MaterialConfirmBillCollection materialConfirmBillColl = null;
        
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id", billId));
        view.setFilter(filter);
        
        materialConfirmBillColl = MaterialConfirmBillFactory.getRemoteInstance().getMaterialConfirmBillCollection(view);
    	if(materialConfirmBillColl.size() < 1){
    		MsgBox.showWarning(this, "该合同下面没有材料确认单，请新增！");
    		SysUtil.abort();
    	}
        super.actionView_actionPerformed(e);
    }

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected(getMainTable());
        String billId = getSelectedKeyValue(getMainTable());
        
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("id", billId));
        view.setFilter(filter);
        
//        ContractBillInfo con = ContractBillFactory.getRemoteInstance().getContractBillCollection(view).get(0);
//    	if(con.isIsPartAMaterialCon() == true){
//    		MsgBox.showWarning(this, "该合同属于甲供材合同，不能新增材料确认单!");
//    		SysUtil.abort();
//    	}
//		if(con.isHasSettled()) {
//			MsgBox.showWarning(this, "合同已结算，不能新增材料明细单!");
//			SysUtil.abort();
//		}
    	super.actionAddNew_actionPerformed(e);
    }
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(getMainTable());
        String billId = getSelectedKeyValue(getMainTable());
        MaterialConfirmBillCollection materialConfirmBillColl = null;
        
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id", billId));
        view.setFilter(filter);
        
        materialConfirmBillColl = MaterialConfirmBillFactory.getRemoteInstance().getMaterialConfirmBillCollection(view);
    	if(materialConfirmBillColl.size() < 1){
    		MsgBox.showWarning(this, "该合同下面没有材料确认单，请新增！");
    		SysUtil.abort();
    	}
    	checkSelected(this.tblMaterilaConfirm);
    	String materialId = getSelectedKeyValue(this.tblMaterilaConfirm);
    	EntityViewInfo view2 = new EntityViewInfo();
        FilterInfo filter2 = new FilterInfo();
        filter2.getFilterItems().add(new FilterItemInfo("id", materialId));
        view2.setFilter(filter2);
        MaterialConfirmBillCollection coll = MaterialConfirmBillFactory.getRemoteInstance().getMaterialConfirmBillCollection(view2);
        if(coll != null){
        	if(coll.get(0).getState() == FDCBillStateEnum.AUDITTED){
				MsgBox.showWarning(this, "该单据已经审批，不允许修改");
				SysUtil.abort();
        	}
		}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(getMainTable());
        String billId = getSelectedKeyValue(getMainTable());
        MaterialConfirmBillCollection materialConfirmBillColl = null;
        
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id", billId));
        view.setFilter(filter);
        
        materialConfirmBillColl = MaterialConfirmBillFactory.getRemoteInstance().getMaterialConfirmBillCollection(view);
    	if(materialConfirmBillColl.size() < 1){
    		MsgBox.showWarning(this, "该合同下面没有材料确认单！");
    		SysUtil.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

	
	protected void audit(List ids) throws Exception {

		MaterialConfirmBillFactory.getRemoteInstance().audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		
		MaterialConfirmBillFactory.getRemoteInstance().unAudit(ids);
	}
	
	protected void displayBillByContract(EntityViewInfo view)
			throws BOSException {
		
		super.displayBillByContract(view);
		
		MaterialConfirmBillCollection materialConfirmBillCollection = MaterialConfirmBillFactory
				.getRemoteInstance().getMaterialConfirmBillCollection(view);
		for(Iterator iter=materialConfirmBillCollection.iterator(); iter.hasNext();){
			
			MaterialConfirmBillInfo element = (MaterialConfirmBillInfo)iter.next();
			IRow row = getBillListTable().addRow();
			
			row.getCell(MaterialConfirmContants.ID).setValue(element.getId().toString());
			row.getCell(MaterialConfirmContants.STATE).setValue(element.getState());
			row.getCell(MaterialConfirmContants.NUMBER).setValue(element.getNumber());
			row.getCell(MaterialConfirmContants.SUPPLY_DATE).setValue(element.getSupplyDate());
			
			row.getCell(MaterialConfirmContants.MATERIAL_CON_NUMBER).setValue(element.getMaterialContractBill().getNumber());
			row.getCell(MaterialConfirmContants.MATERIAL_CON_NAME).setValue(element.getMaterialContractBill().getName());
			row.getCell(MaterialConfirmContants.MATERIAL_CON_PARTB).setValue(element.getMaterialContractBill().getPartB().getName());
			row.getCell(MaterialConfirmContants.MATERIAL_CON_AMT).setValue(element.getMaterialContractBill().getAmount());
			row.getCell(MaterialConfirmContants.MATERIAL_CON_CURRENCY).setValue(element.getMaterialContractBill().getCurrency().getName());
			
			row.getCell(MaterialConfirmContants.MAIN_CON_NUMBER).setValue(element.getMainContractBill().getNumber());
			row.getCell(MaterialConfirmContants.MAIN_CON_NAME).setValue(element.getMainContractBill().getName());
			row.getCell(MaterialConfirmContants.MAIN_CON_PARTB).setValue(element.getMainContractBill().getPartB().getName());
			
			row.getCell(MaterialConfirmContants.DESC).setValue(element.getDescription());
			row.getCell(MaterialConfirmContants.SUPPLY_AMT).setValue(element.getSupplyAmt());
			row.getCell(MaterialConfirmContants.TODATE_SUPPLY_AMT).setValue(element.getToDateSupplyAmt());
			row.getCell(MaterialConfirmContants.CONFIRM_AMT).setValue(element.getConfirmAmt());
			row.getCell(MaterialConfirmContants.TODATE_CONFIRM_AMT).setValue(element.getToDateConfirmAmt());
			row.getCell(MaterialConfirmContants.PAID_AMT).setValue(element.getPaidAmt());
			row.getCell(MaterialConfirmContants.TODATE_PAID_AMT).setValue(element.getToDatePaidAmt());
			row.getCell(MaterialConfirmContants.CREATOR).setValue(element.getCreator().getName());
			row.getCell(MaterialConfirmContants.CREATE_TIME).setValue(element.getCreateTime());
			if(element.getAuditor()!=null)
				row.getCell(MaterialConfirmContants.AUDITOR).setValue(element.getAuditor().getName());
			if(element.getAuditTime()!=null)	
				row.getCell(MaterialConfirmContants.AUDIT_DATE).setValue(element.getAuditTime());
			
		}
	}
	//
	protected EntityViewInfo genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	
    	String contractId = (String)getMainTable().getCell(top, getKeyFieldName()).getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id", contractId));
    	view.setFilter(filter);
    	view.getSorter().add(new SorterItemInfo(getBillStatePropertyName()));
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);
				
			}
    	}
		return view;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTableProperty();
		// 设置表格自动列排序 add by andy_liu 2012-3-21
		this.tblMaterilaConfirm.setColumnMoveable(true);		
		// 仅可自动排序的列才能自动排序 add by andy_liu 2012-3-21
		for (int i = 0, size = this.tblMaterilaConfirm.getColumnCount(); i < size; i++) {
			this.tblMaterilaConfirm.getColumn(i).setSortable(true);
		}
		KDTSortManager sm = new KDTSortManager(tblMaterilaConfirm);
		sm.setSortAuto(true);
	}
	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		if (!isHasBillTable()) {
			super.tblMain_tableSelectChanged(e);
			return;
		}
		if (e.getSelectBlock() == null)
			return;
		//默认按制单日期降序排序。add by andy_liu 2012-3-21
		updateBilllistData(e, "createTime", SortType.DESCEND);
	}

	/**
	 * @description 排序重绘表格数据
	 * @author andy_liu
	 * @createDate 2012-03-21
	 * @param e
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	private void updateBilllistData(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e, String collName, SortType type)
			throws BOSException {
		// 删除从表数据
		getBillListTable().removeRows(false);
		EntityViewInfo view = genBillQueryView(e);
		if (view == null)
			return;
		SorterItemCollection sorterCion = new SorterItemCollection();
		SorterItemInfo sorter = new SorterItemInfo(collName);
		sorter.setSortType(type);
		sorterCion.add(sorter);
		if (sorterCion == null)
			return;
		view.setSorter(sorterCion);
		if (!displayBillByContract(e, view)) {
			displayBillByContract(view);
		}
		if (getBillListTable() != null && getBillListTable().getRowCount() > 0) {
			getBillListTable().getSelectManager().select(0, 0);
		}
	}
	
	private void initTableProperty(){
		KDFormattedTextField materialConAmtTextField = new KDFormattedTextField(
				KDFormattedTextField.INTEGER);
		materialConAmtTextField.setSupportedEmpty(true);
		materialConAmtTextField.setNegatived(false);
		materialConAmtTextField.setDataType(KDFormattedTextField.INTEGER);
		materialConAmtTextField.setMaximumValue(FDCHelper.MAX_VALUE);
		materialConAmtTextField.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		ICellEditor materialConAmtEditor = new KDTDefaultCellEditor(materialConAmtTextField);
		getBillListTable().getColumn(MaterialConfirmContants.MATERIAL_CON_AMT).setEditor(materialConAmtEditor);
		getBillListTable().getColumn(MaterialConfirmContants.MATERIAL_CON_AMT)
				.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		FDCHelper.formatTableNumber(getBillListTable(), MaterialConfirmContants.MATERIAL_CON_AMT);
		FDCHelper.formatTableNumber(getBillListTable(), MaterialConfirmContants.SUPPLY_AMT);
		FDCHelper.formatTableNumber(getBillListTable(), MaterialConfirmContants.TODATE_SUPPLY_AMT);
		FDCHelper.formatTableNumber(getBillListTable(), MaterialConfirmContants.PAID_AMT);
		FDCHelper.formatTableNumber(getBillListTable(), MaterialConfirmContants.TODATE_PAID_AMT);
		FDCHelper.formatTableNumber(getBillListTable(), MaterialConfirmContants.CONFIRM_AMT);
		FDCHelper.formatTableNumber(getBillListTable(), MaterialConfirmContants.TODATE_CONFIRM_AMT);
	}
	protected KDTable getBillListTable() {
		return this.tblMaterilaConfirm;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return getRemoteInterface();
	}

	protected String getEditUIName() {
		return MaterialConfirmBillEditUI.class.getName();
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return MaterialConfirmBillFactory.getRemoteInstance();
	}

	protected boolean isFootVisible() {
		return false;
	}

	protected void initWorkButton() {
		super.initWorkButton();
	}
	
	/**
	 * 要显示的合同的状态集合,用于过滤合同
	 * @return
	 */
	protected Set getContractBillStateSet() {
		Set set = new HashSet(); 
		set = super.getContractBillStateSet();
		set.add(FDCBillStateEnum.AUDITTED_VALUE);
//		set.add(FDCBillStateEnum.SAVED_VALUE);
//		set.add(FDCBillStateEnum.SUBMITTED_VALUE);
		return set;
	}
	
	/**
	 * 不显示甲供材合同
	 * @return filter
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		
		FilterInfo filter = new FilterInfo();
		Set set = getContractBillStateSet();
		filter = super.getTreeSelectChangeFilter();
		
		filter.getFilterItems().add(new FilterItemInfo("state", set, CompareType.INCLUDE));
//		filter.getFilterItems().add(new FilterItemInfo("isPartAMaterialCon", Boolean.valueOf(true), CompareType.NOTEQUALS));
		
		return filter;
	}
	
	/**
	 * 返回定位字段的集合
	 * 增加定位字段: 合同金额，形成方式，合同性质。 Modified By Owen_wen 2010-09-06
	 * @author zhiyuan_tang 2010/07/12
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "partB.name", "contractType.name", 
				"signDate", "amount", "contractSource", "contractPropert",};
	}
}