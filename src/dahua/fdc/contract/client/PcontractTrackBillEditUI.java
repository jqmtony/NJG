/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.PcontractTrackBillInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PcontractTrackBillEditUI extends AbstractPcontractTrackBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PcontractTrackBillEditUI.class);
    
    /**
     * output class constructor
     */
    public PcontractTrackBillEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	actionAddNew.setVisible(false);
    	actionCopy.setVisible(false);
    	actionFirst.setVisible(false);
    	actionLast.setVisible(false);
    	actionPre.setVisible(false);
    	actionNext.setVisible(false);
    	actionFix.setVisible(false);
    	if(FDCBillStateEnum.AUDITTED.equals(editData.getTrackBillStatus())){
        	actionRemove.setEnabled(false);
        	actionEdit.setEnabled(false);
        }
    }
    
    public void onShow() throws Exception {
    	// TODO Auto-generated method stub
    	super.onShow();
    	kdtEntrys.getStyleAttributes().setLocked(true);
    	kdtEntrys_detailPanel.getAddNewLineButton().setVisible(false);
    	kdtEntrys_detailPanel.getInsertLineButton().setVisible(false);
    	kdtEntrys_detailPanel.getRemoveLinesButton().setVisible(false);
    	kdtEntrys.getColumn("planAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	kdtEntrys.getColumn("planAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); 
    	kdtEntrys.getColumn("contralAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	kdtEntrys.getColumn("contralAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); 
    	kdtEntrys.getColumn("changeRate").getStyleAttributes().setNumberFormat("0.00%");
    	//手动输入的列
    	kdtEntrys.getColumn("sgtRealDate").getStyleAttributes().setLocked(false);
    }
    
    protected void btnGrabData_actionPerformed(ActionEvent e) throws Exception {
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql(" select pcont.fid,pcont.FLEVEL,pcont.FLONGNUMBER,pcparent.FLONGNUMBER,pcont.fname_l2,pcType.CFHyType,pcont.Famount,pcont.FreservedChangeRate,pcont.FcontrolAmount, ");
    	builder.appendSql("pcont.CFSgtDate,pcont.CFContSignDate,pcont.CFStartDate,pcont.CFEndDate,pcont.CFCsendDate ");
    	//pcont.CFStartDate,pcont.CFEndDate,pcont.CFCsendDate
    	builder.appendSql("from T_CON_ProgrammingContract pcont left join T_CON_Programming program on pcont.FPROGRAMMINGID=program.fid ");
    	builder.appendSql("left join T_CON_ProgrammingContract pcparent on pcparent.fid=pcont.fparentid ");
    	builder.appendSql("left join CT_CON_PcType pcType on pcType.fid=pcont.CFHyTypeID where program.FIsLatest=1 ");
    	builder.appendSql("and program.fprojectid='"+editData.getCurProject().getId().toString()+"' order by pcont.FLONGNUMBER");
    	IRowSet rs = builder.executeQuery();
    	kdtEntrys.removeRows();
    	if(rs.size() == 0) {
    		MsgBox.showInfo("没有数据可以提取！");
    		return;
		}else{
			IRow row = null;
			while(rs.next()){
				row = kdtEntrys.addRow();
				row.getCell("pcid").setValue(rs.getString(1));
				row.getCell("level").setValue(rs.getInt(2));
				row.getCell("longNumber").setValue(rs.getString(3));
				row.getCell("headNumber").setValue(rs.getString(4));
				row.getCell("name").setValue(rs.getString(5));
				row.getCell("hyType").setValue(rs.getString(6));
				row.getCell("planAmount").setValue(rs.getBigDecimal(7));
				row.getCell("changeRate").setValue(rs.getBigDecimal(8));
				row.getCell("contralAmount").setValue(rs.getBigDecimal(9));
				row.getCell("sgtDate").setValue(rs.getDate(10));
				row.getCell("csDate").setValue(rs.getDate(11));
				row.getCell("startDate").setValue(rs.getDate(12));
				row.getCell("endDate").setValue(rs.getDate(13));
				row.getCell("csendDate").setValue(rs.getDate(14));
			}
		}
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

    protected void verifyInput(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.verifyInput(e);
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!FDCBillStateEnum.SUBMITTED.equals(editData.getTrackBillStatus())){
    		MsgBox.showInfo("已提交的单据才能进行审核操作！");
    		return;
    	}
        super.actionAudit_actionPerformed(e);
        editData.setTrackBillStatus(FDCBillStateEnum.AUDITTED);
        editData.setIsNew(true);
        loadData();
        setOprtState(OprtState.VIEW);
        actionEdit.setEnabled(false);
        actionRemove.setEnabled(false);
        MsgBox.showInfo("审核成功！");
    }

    /**
     * output actionUnaudit_actionPerformed
     */
    public void actionUnaudit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!FDCBillStateEnum.AUDITTED.equals(editData.getTrackBillStatus())){
    		MsgBox.showInfo("已审核的单据才能进行反审核操作！");
    		return;
    	}
    	if(!editData.isIsNew()){
    		MsgBox.showInfo("最新版的单据才能进行反审核操作！");
    		return;
    	}
    	setOprtState(OprtState.EDIT);
        super.actionUnaudit_actionPerformed(e);
        editData.setTrackBillStatus(FDCBillStateEnum.SAVED);
        actionEdit.setEnabled(true);
        actionRemove.setEnabled(true);
        loadData();
        MsgBox.showInfo("反审核成功！");
    }

    /**
     * output actionFix_actionPerformed
     */
    public void actionFix_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFix_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.PcontractTrackBillFactory.getRemoteInstance();
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
        PcontractTrackBillInfo objectValue = (PcontractTrackBillInfo)getUIContext().get("info");
        if(objectValue == null){
        	objectValue = new PcontractTrackBillInfo();
        	objectValue.setVersion(1);
        	CurProjectInfo cpinfo = (CurProjectInfo)getUIContext().get("treeSelectedObj");
        	objectValue.setCurProject(cpinfo);
        }else{
        	objectValue.setVersion(objectValue.getVersion()+1);
        	objectValue.setId(null);
        	objectValue.setNumber(null);
        	objectValue.setIsNew(false);
        	objectValue.setTrackBillStatus(FDCBillStateEnum.SAVED);
        	objectValue.setBizDate(new Date());
        	objectValue.setCreateTime(null);
        	objectValue.setAuditor(null);
        	objectValue.setAuditTime(null);
        	objectValue.setLastUpdateUser(null);
        	objectValue.setLastUpdateTime(null);
        	for(int i=0;i<objectValue.getEntrys().size();i++){
        		objectValue.getEntrys().get(i).setId(null);
        	}
        }
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        return objectValue;
    }

}