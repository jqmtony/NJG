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
 * ����ȷ�ϵ� -> �Ӳ��϶���������
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
		FilterInfo filterInfo = new FilterInfo(); //������������
		filterInfo.getFilterItems().add(new FilterItemInfo("billState", MaterialBillStateEnum.ALL_ARRIVED_VALUE, CompareType.NOTEQUALS));
		
		// ���ݲ��Ϻ�ͬ�Ĺ�Ӧ�̣��ҷ������й���   added by owen_wen 2010-8-30
		filterInfo.getFilterItems().add(new FilterItemInfo("suppliers.id", 
				((ContractBillInfo)parentUI.prmtMaterialContractBill.getValue()).getPartB().getId().toString()));
		
		// ֻ������ͨ���Ķ��������ܱ����˳���   added by owen_wen 2010-8-30
		filterInfo.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED"));
		
		evi.setFilter(filterInfo);
		if (mainQuery == null) {
			mainQuery = new EntityViewInfo();
		}
		mainQuery.setFilter(filterInfo); //��ӹ�������
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
     * �����ݱ����������Ĵ�����Ӻ�ͬ��š�ɾ�� ����������>���������� ��¼
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
			// ��Ӳ��Ϻ�ͬ���			
			row.getCell("contractNumber").setValue(((ContractBillInfo)parentUI.prmtMaterialContractBill.getValue()).getNumber());
		}
	}
    
    /**
     * ���Զ�����Ϊ0������ȥ���������0����ʾ0
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
    	// orderEntryID_sum4RevAmt ��������¼�е�����ID, �ۼ��ջ����� 
    	HashMap orderEntryID_sum4RevAmt = new  HashMap();
    	Object objId = null;
    	for(int i =0;i<tblMain.getRowCount3();i++){
    		Boolean isSelected =(Boolean)tblMain.getCell(i,"chk").getValue();
    		if(isSelected.booleanValue()){
    			objId = tblMain.getCell(i,"entryID").getValue(); //���÷�¼��ID��ȷ�ϵ��ķ�¼��Ӧ�����ķ�¼�� Modified By Owen_wen 2010-8-30
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