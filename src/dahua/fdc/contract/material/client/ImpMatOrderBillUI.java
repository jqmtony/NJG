/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialBillStateEnum;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 材料确认单 -> 从材料订货单导入
 */
public class ImpMatOrderBillUI extends AbstractImpMatOrderBillUI
{
    private static final Logger logger = CoreUIObject.getLogger(ImpMatOrderBillUI.class);
    HashSet mySet = new HashSet();
    private MaterialConfirmBillEditUI parentUI ;
    /**
     * output class constructor
     */
    public ImpMatOrderBillUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	parentUI = (MaterialConfirmBillEditUI)getUIContext().get(UIContext.OWNER);
    	EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo(); //建立过滤条件
		filterInfo.getFilterItems().add(new FilterItemInfo("billState", MaterialBillStateEnum.ALL_ARRIVED_VALUE, CompareType.NOTEQUALS));
		
		// 根据材料合同的供应商（乙方）进行过滤   added by owen_wen 2010-8-30
		filterInfo.getFilterItems().add(new FilterItemInfo("suppliers.id", 
				((ContractBillInfo)parentUI.prmtMaterialContractBill.getValue()).getPartB().getId().toString()));
		
		// 只有审批通过的订货单才能被过滤出来   added by owen_wen 2010-8-30
		filterInfo.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED"));
		
		evi.setFilter(filterInfo);
		if (mainQuery == null) {
			mainQuery = new EntityViewInfo();
		}
		mainQuery.setFilter(filterInfo); //添加过滤条件
		this.execQuery();
		
		fillOtherDataForTable();		
		this.actionQuery.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionEdit.setVisible(false);
    }
    
    /**
     * 对数据表格进行其它的处理，添加合同编号、删除 “引用数量>订货数量” 记录
     * @author owen_wen 2010-8-30
     */
    private void fillOtherDataForTable() {
		for (int i = 0;  i < tblMain.getRowCount(); i++){
			IRow row = tblMain.getRow(i);
			
			if (row.getCell("sum4RevAmt").getValue()!=null){
				float orderAmt = Float.parseFloat(row.getCell("orderAmt").getValue().toString());
				float sum4RevAmt = Float.parseFloat(row.getCell("sum4RevAmt").getValue().toString());
				
				if (sum4RevAmt >= orderAmt){
					tblMain.removeRow(i);
					i--;
					continue;
				}
			}
			// 添加材料合同编号			
			row.getCell("contractNumber").setValue(((ContractBillInfo)parentUI.prmtMaterialContractBill.getValue()).getNumber());
		}
	}
    
    /**
     * 将自动忽略为0的属性去掉，如果是0就显示0
     */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, viewInfo);
		
		queryExecutor.option().isAutoIgnoreZero = false;
		
		return queryExecutor;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	/**
     * output btnComfirm_actionPerformed method
     */
    protected void btnComfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	// orderEntryID_sum4RevAmt 订货单分录中的物料ID, 累计收货数量 
    	HashMap orderEntryID_sum4RevAmt = new  HashMap();
    	Object objId = null;
    	for(int i =0;i<tblMain.getRowCount3();i++){
    		Boolean isSelected =(Boolean)tblMain.getCell(i,"chk").getValue();
    		if(isSelected.booleanValue()){
    			objId = tblMain.getCell(i,"entryID").getValue(); //改用分录的ID，确认单的分录对应订单的分录。 Modified By Owen_wen 2010-8-30
    			if(objId!=null && !"".equals(objId.toString()) )
    			{
    				if (tblMain.getCell(i,"sum4RevAmt").getValue() == null)
    					orderEntryID_sum4RevAmt.put(objId.toString(), FDCConstants.ZERO);
    				else
    					orderEntryID_sum4RevAmt.put(objId.toString(), tblMain.getCell(i,"sum4RevAmt").getValue());
    			}
    		}
    	}
    	if (orderEntryID_sum4RevAmt.size()>0)
    		parentUI.setSelectedOrderBillIds_sum4RevQty(orderEntryID_sum4RevAmt);
    	
        destroyWindow();
    }

  
    /**
     * output btnCancelSelect_actionPerformed method
     */
    protected void btnCancelSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnCancelSelect_actionPerformed(e);
        destroyWindow();
    }

    /**
     * output chkSelectAll_actionPerformed method
     */
    protected void chkSelectAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.chkSelectAll_actionPerformed(e);
		if(this.chkSelectAll.getSelectState()==32)
		{
			for(int i=0;i<tblMain.getRowCount3();i++){
				tblMain.getCell(i, "chk").setValue(Boolean.TRUE);
			}
		}
		else
		{
			for(int i=0;i<tblMain.getRowCount3();i++){
				tblMain.getCell(i, "chk").setValue(Boolean.FALSE);
			}
		}
    }


	public EntityViewInfo getMainQuery() {
		EntityViewInfo evi = super.getMainQuery();
		FilterInfo filter  = null;
		if(evi.getFilter()==null)
		filter = new FilterInfo();
		else
			filter = evi.getFilter();
		filter.getFilterItems().add(new FilterItemInfo("billState","6",CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("id",mySet,CompareType.NOTINCLUDE));
		evi.setFilter(filter);
		return evi;
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
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
	public void actionView_actionPerformed(ActionEvent e) throws Exception{
		 //do nothing
	}
}