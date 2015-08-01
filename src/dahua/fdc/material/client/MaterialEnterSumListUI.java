/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.material.MaterialEnterSumEntryInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumFactory;
import com.kingdee.eas.fdc.material.MaterialEnterSumInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MaterialEnterSumListUI extends AbstractMaterialEnterSumListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialEnterSumListUI.class);
    //CommonQueryDialog dialog = null;
    /**
     * output class constructor
     */
    public MaterialEnterSumListUI() throws Exception
    {
        super();
       
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.material.MaterialEnterSumFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.material.MaterialEnterSumInfo objectValue = new com.kingdee.eas.fdc.material.MaterialEnterSumInfo();
		
        return objectValue;
    }

	protected String getEditUIModal() {
		// TODO Auto-generated method stub
		return UIFactoryName.NEWTAB;
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		checkSelected(tblMain);
		checkIsExistAuditedRows();
		audit(getSelectedIdValues(tblMain,"id"),getSelectedRefSetData());
		super.actionAudit_actionPerformed(e);
		showOprtOKMsgAndRefresh();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		checkSelected(tblMain);
		checkIsExistUnAuditRows();
		unAudit(getSelectedIdValues(tblMain,"id"),getSelectedRefSetData());
		super.actionUnAudit_actionPerformed(e);
		showOprtOKMsgAndRefresh();
	}
	protected void audit(List ids,List refList) throws Exception {
		
		//审批
		MaterialEnterSumFactory.getRemoteInstance().audit(ids);
		//反写材料进场计划“已汇总数量”
		MaterialEnterSumFactory.getRemoteInstance().refSetPlanSumQty(refList,FDCBillStateEnum.AUDITTED);
	}
	protected void unAudit(List ids,List refList) throws Exception {
        //反审核		
		MaterialEnterSumFactory.getRemoteInstance().unAudit(ids);
		//反写材料进场计划“已汇总数量”
		MaterialEnterSumFactory.getRemoteInstance().refSetPlanSumQty(refList,FDCBillStateEnum.SUBMITTED);
	}
	public  List getSelectedIdValues(KDTable table, String keyFieldName) {

		List ids = new ArrayList();

		int[] selectedRows = KDTableUtil.getSelectedRows(table);

		for (int i = 0; i < selectedRows.length; i++) {
			if(table.getCell(selectedRows[i], keyFieldName)==null){
				return null;
			}
			String id = (String) table.getCell(selectedRows[i], keyFieldName)
					.getValue();
			ids.add(id);
		}

		return ids;
	}
	protected List getSelectedRefSetData() throws Exception{
		List list = new ArrayList();
		HashMap map = null;
		List selectedBillId = getDistinctSelectIds(tblMain);
		for(int i =0;i<selectedBillId.size();i++){
			String id  = (String)selectedBillId.get(i);
			MaterialEnterSumInfo info = MaterialEnterSumFactory.getRemoteInstance().getMaterialEnterSumInfo(new ObjectUuidPK(id));
            //取分录源单ID和计划数量
			for(int j =0;j<info.getEntrys().size();j++)
            {
            	map = new HashMap();
            	MaterialEnterSumEntryInfo entryInfo = info.getEntrys().get(j);
            	if(entryInfo.getSourceBill() == null || "".equals(entryInfo.getSourceBill()) )
            		break;
            	map.put("sourceBillId",entryInfo.getSourceBill() );
            	map.put("sumQty", entryInfo.getPlanQuantity()==null? new BigDecimal(0):entryInfo.getPlanQuantity());
                list.add(map);          
            }
		}
		return list;
	}
	protected List getDistinctSelectIds(KDTable table)
	{
		ArrayList list = new ArrayList();
		int[] selectedRows = KDTableUtil.getSelectedRows(table);
		for(int i = 0; i < selectedRows.length; i++) {
		   if(table.getCell(selectedRows[i],"id").getValue()==null)
			   break;
		   String id = (String)table.getCell(selectedRows[i],"id").getValue();
		   if(!list.contains(id))
			   list.add(id);
		}
		return list;
	}
	/**
	 * 
	 * 描述：提示操作成功
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		setComponentState();
	}
	protected void setComponentState()
	{
		btnAudit.setEnabled(true);
		btnUnAudit.setEnabled(true);
	}
	/**
	 * 
	 * 描述：检查当前单据的Table是否选中行
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected(KDTable table) {
		if (table.getRowCount()==0 || table.getSelectManager().size() == 0) {
	        MsgBox.showWarning(this, EASResource
	                .getString(FrameWorkClientUtils.strResource
	                        + "Msg_MustSelected"));
	        SysUtil.abort();
	    }
		
	}
   protected void checkIsExistAuditedRows()
   {
	   int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectedRows.length; i++) {
			if(tblMain.getCell(selectedRows[i], "state")!=null){
				Object objState = tblMain.getCell(selectedRows[i], "state").getValue();
				if(objState !=null && "已审批".equals(objState.toString())){
					MsgBox.showInfo("存在已经审批的记录，请修改选择!");	 
					SysUtil.abort();
				}
			}
		}
	  
   }
   protected void checkIsExistUnAuditRows()
   {
	   int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectedRows.length; i++) {
			if(tblMain.getCell(selectedRows[i], "state")!=null){
				Object objState = tblMain.getCell(selectedRows[i], "state").getValue();
				if( objState!=null &&  !"已审批".equals(objState.toString())){
					MsgBox.showInfo("存在未审批的记录，请修改选择!");	 
					SysUtil.abort();
				}
			}
		}
   }

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkIsExistEditRows();
		super.actionEdit_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
	{
		checkIsExistEditRows();
		super.actionRemove_actionPerformed(e);
	}
	   
	protected void checkIsExistEditRows(){
	   int[] selectedRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectedRows.length; i++) {
			if(tblMain.getCell(selectedRows[i], "state")!=null){
				Object objState = tblMain.getCell(selectedRows[i], "state").getValue();
				if(objState !=null && "已审批".equals(objState.toString())){
					MsgBox.showInfo("不允许修改或删除已审批的单据!");	 
					SysUtil.abort();
				}
			}
		}		  
	}   
}