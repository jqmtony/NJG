/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.PcontractTrackBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PcontractTrackBillEditUI extends AbstractPcontractTrackBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PcontractTrackBillEditUI.class);
    private final String LONGNUMBER = "longNumber";// 长编码
	private final String HEADNUMBER = "headNumber";// 长级长编码
	private Date nowDate = null;
    
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
    	kdtEntrys.addKDTEditListener(new KDTEditAdapter(){
    		public void editStopped(KDTEditEvent e) {
    			kdtEntrys_editStopped(e);
    		}
    	});
    	//
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		nowDate = dateFormat.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
    }
    
    public void kdtEntrys_editStopped(KDTEditEvent e) {
    	int coldex = e.getColIndex();
    	int rowdex = e.getRowIndex();
    	if(coldex==kdtEntrys.getColumnIndex("sgtRealDate") && kdtEntrys.getCell(rowdex,coldex).getValue()!=null){
    		Date realDate = (Date)kdtEntrys.getCell(rowdex,coldex).getValue();
    		if(kdtEntrys.getCell(rowdex,"sgtDate").getValue()!=null && realDate.compareTo((Date)kdtEntrys.getCell(rowdex,"sgtDate").getValue())>0){
    			kdtEntrys.getCell(rowdex,"sgtOverdue").setValue(Boolean.TRUE);
    			kdtEntrys.getCell(rowdex,"sgtOverdue").getStyleAttributes().setBackground(Color.RED);
    		}else{
    			kdtEntrys.getCell(rowdex,"sgtOverdue").setValue(Boolean.FALSE);
    			kdtEntrys.getCell(rowdex,"sgtOverdue").getStyleAttributes().setBackground(Color.WHITE);
    		}
    	}else if(coldex==kdtEntrys.getColumnIndex("csPlanDate") && kdtEntrys.getCell(rowdex,coldex).getValue()!=null){
    		Date realDate = (Date)kdtEntrys.getCell(rowdex,coldex).getValue();
    		if(realDate.compareTo(nowDate) <= 0){
    			MsgBox.showInfo("计划时间应在当前时间之后！");
    			kdtEntrys.getCell(rowdex,coldex).setValue(null);
    		}
    	}else if(coldex==kdtEntrys.getColumnIndex("startPlanDate") && kdtEntrys.getCell(rowdex,coldex).getValue()!=null){
    		Date realDate = (Date)kdtEntrys.getCell(rowdex,coldex).getValue();
    		if(realDate.compareTo(nowDate) <= 0){
    			MsgBox.showInfo("计划时间应在当前时间之后！");
    			kdtEntrys.getCell(rowdex,coldex).setValue(null);
    		}
    	}else if(coldex==kdtEntrys.getColumnIndex("endPlanDate") && kdtEntrys.getCell(rowdex,coldex).getValue()!=null){
    		Date realDate = (Date)kdtEntrys.getCell(rowdex,coldex).getValue();
    		if(realDate.compareTo(nowDate) <= 0){
    			MsgBox.showInfo("计划时间应在当前时间之后！");
    			kdtEntrys.getCell(rowdex,coldex).setValue(null);
    		}
    	}else if(coldex==kdtEntrys.getColumnIndex("csendPlanDate") && kdtEntrys.getCell(rowdex,coldex).getValue()!=null){
    		Date realDate = (Date)kdtEntrys.getCell(rowdex,coldex).getValue();
    		if(realDate.compareTo(nowDate) <= 0){
    			MsgBox.showInfo("计划时间应在当前时间之后！");
    			kdtEntrys.getCell(rowdex,coldex).setValue(null);
    		}
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
    	setRedBg();
    }
    
    protected void btnGrabData_actionPerformed(ActionEvent e) throws Exception {
//    	String table = " select cbill.FAUDITTIME,cbill.FProgrammingContract from T_CON_ContractBill cbill left join T_FDC_ContractType ctype on ctype.fid=cbill.FCONTRACTTYPEID where ctype.fnumber in('施工003','建安008') ";
//    	String sql = "select t.FAUDITTIME,t.FProgrammingContract from ("+table+") t " +"where not exists(select 1 from ("+table+") FProgrammingContract=t.FProgrammingContract and FAUDITTIME<t.FAUDITTIME)";
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select pcont.fid,pcont.FLEVEL,pcont.FLONGNUMBER,pcparent.FLONGNUMBER,pcont.fname_l2,pcType.CFHyType,pcont.Famount,pcont.FreservedChangeRate,pcont.FcontrolAmount, ");
    	builder.appendSql("pcont.CFSgtDate,pcont.CFContSignDate,pcont.CFStartDate,pcont.CFEndDate,pcont.CFCsendDate,contbill.FAUDITTIME,contbillend.FAUDITTIME ");
    	//pcont.CFStartDate,pcont.CFEndDate,pcont.CFCsendDate
//    	builder.appendSql("case when ctype.fnumber in('施工003','建安008') then max(contbill.FAUDITTIME) else null end, ");
//    	builder.appendSql("case when ctype.fnumber in('施工003','建安008') then max(contbill.FAUDITTIME) else null end ");
    	builder.appendSql("from T_CON_ProgrammingContract pcont left join T_CON_Programming program on pcont.FPROGRAMMINGID=program.fid ");
    	builder.appendSql("left join T_CON_ProgrammingContract pcparent on pcparent.fid=pcont.fparentid ");
    	builder.appendSql("left join (select max(cbill.FAUDITTIME) FAUDITTIME,cbill.FProgrammingContract from T_CON_ContractBill cbill left join T_FDC_ContractType ctype on ctype.fid=cbill.FCONTRACTTYPEID ");
    	builder.appendSql("where ctype.flongnumber in('003','008','fb!1001','fb!1003','fb!1004','zb!1000') group by cbill.FProgrammingContract) contbill on pcont.fid=contbill.FProgrammingContract ");
//    	builder.appendSql("left join T_FDC_ContractType ctype on ctype.fid=contbill.FCONTRACTTYPEID ");
    	builder.appendSql("left join (select max(cbill.FAUDITTIME) FAUDITTIME,cbill.FProgrammingContract from T_CON_ContractBill cbill left join T_FDC_ContractType ctype on ctype.fid=cbill.FCONTRACTTYPEID ");
    	builder.appendSql("where ctype.flongnumber in('004','fb!1002') group by cbill.FProgrammingContract) contbillend on pcont.fid=contbillend.FProgrammingContract ");
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
				row.getCell(LONGNUMBER).setValue(rs.getString(3));
				row.getCell(HEADNUMBER).setValue(rs.getString(4));
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
				row.getCell("csRealDate").setValue(rs.getDate(15));
				row.getCell("csendRealDate").setValue(rs.getDate(16));
			}
		}
    	setRedBg();
    }
    
    private void setRedBg(){
    	IRow row = null;
    	Date pcPlanDate = null;
    	for(int i = 0; i < kdtEntrys.getRowCount3(); i++) {
    		row = kdtEntrys.getRow(i);
    		if(!isLeaf((String)row.getCell(LONGNUMBER).getValue(),kdtEntrys,HEADNUMBER)){
//    			row.getStyleAttributes().setLocked(true);
    			row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
    			continue;
    		}
    		//施工图交接时间
			if(row.getCell("sgtRealDate").getValue()!=null && row.getCell("sgtDate").getValue()!=null && 
					((Date)row.getCell("sgtRealDate").getValue()).compareTo((Date)row.getCell("sgtDate").getValue())>0){
				row.getCell("sgtOverdue").setValue(Boolean.TRUE);
    			row.getCell("sgtOverdue").getStyleAttributes().setBackground(Color.RED);
			}
			//实际合同签订时间
			if(row.getCell("csDate").getValue()!=null){
				pcPlanDate = (Date)row.getCell("csDate").getValue();
				if(row.getCell("csRealDate").getValue()==null && nowDate.compareTo(pcPlanDate)>0){
					row.getCell("csOverdue").setValue(Boolean.TRUE);
	    			row.getCell("csOverdue").getStyleAttributes().setBackground(Color.RED);
	    			row.getCell("csPlanDate").getStyleAttributes().setLocked(false);
	    			row.getCell("csPlanDate").getStyleAttributes().setBackground(kdtEntrys.getRequiredColor());
				}else if(row.getCell("csRealDate").getValue()!=null && ((Date)row.getCell("csRealDate").getValue()).compareTo(pcPlanDate)>0){
					row.getCell("csOverdue").setValue(Boolean.TRUE);
	    			row.getCell("csOverdue").getStyleAttributes().setBackground(Color.RED);
				}
			}
			//实际开工时间
			if(row.getCell("startDate").getValue()!=null){
				pcPlanDate = (Date)row.getCell("startDate").getValue();
				if(row.getCell("startRealDate").getValue()==null && nowDate.compareTo(pcPlanDate)>0){
					row.getCell("startOverdue").setValue(Boolean.TRUE);
	    			row.getCell("startOverdue").getStyleAttributes().setBackground(Color.RED);
	    			row.getCell("startPlanDate").getStyleAttributes().setLocked(false);
	    			row.getCell("startPlanDate").getStyleAttributes().setBackground(kdtEntrys.getRequiredColor());
				}else if(row.getCell("startRealDate").getValue()!=null && ((Date)row.getCell("startRealDate").getValue()).compareTo(pcPlanDate)>0){
					row.getCell("startOverdue").setValue(Boolean.TRUE);
	    			row.getCell("startOverdue").getStyleAttributes().setBackground(Color.RED);
				}
			}
			//实际竣工时间
			if(row.getCell("endDate").getValue()!=null){
				pcPlanDate = (Date)row.getCell("endDate").getValue();
				if(row.getCell("endRealDate").getValue()==null && nowDate.compareTo(pcPlanDate)>0){
					row.getCell("endOverdue").setValue(Boolean.TRUE);
	    			row.getCell("endOverdue").getStyleAttributes().setBackground(Color.RED);
	    			row.getCell("endPlanDate").getStyleAttributes().setLocked(false);
	    			row.getCell("endPlanDate").getStyleAttributes().setBackground(kdtEntrys.getRequiredColor());
				}else if(row.getCell("endRealDate").getValue()!=null && ((Date)row.getCell("endRealDate").getValue()).compareTo(pcPlanDate)>0){
					row.getCell("endOverdue").setValue(Boolean.TRUE);
	    			row.getCell("endOverdue").getStyleAttributes().setBackground(Color.RED);
				}
			}
			
			//实际合同签订完成时间
			if(row.getCell("csendDate").getValue()!=null){
				pcPlanDate = (Date)row.getCell("csendDate").getValue();
				if(row.getCell("csendRealDate").getValue()==null && nowDate.compareTo(pcPlanDate)>0){
					row.getCell("csendOverdue").setValue(Boolean.TRUE);
	    			row.getCell("csendOverdue").getStyleAttributes().setBackground(Color.RED);
	    			row.getCell("csendPlanDate").getStyleAttributes().setLocked(false);
	    			row.getCell("csendPlanDate").getStyleAttributes().setBackground(kdtEntrys.getRequiredColor());
				}else if(row.getCell("csendRealDate").getValue()!=null && ((Date)row.getCell("csendRealDate").getValue()).compareTo(pcPlanDate)>0){
					row.getCell("csendOverdue").setValue(Boolean.TRUE);
	    			row.getCell("csendOverdue").getStyleAttributes().setBackground(Color.RED);
				}
			}
			
		}
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    	setRedBg();
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmit_actionPerformed(e);
    	setRedBg();
    }
    
    /**
	 * 判断是否为叶子节点
	 * @param parentLongNumber
	 * @param table
	 * @param colHeadNumber
	 * @return
	 */
	private boolean isLeaf(String parentLongNumber, KDTable table, String colHeadNumber) {
		boolean result = true;
		if (parentLongNumber == null || parentLongNumber.length() == 0)
			return result;
		int rowCount = table.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String number = getCellValue(table, i, colHeadNumber);
			if (number == null)
				continue;
			if (number.startsWith(parentLongNumber)) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	private String getCellValue(KDTable table, int rowIndex, String colName) {
		if (table == null || colName == null)
			return null;
		ICell cell = table.getCell(rowIndex, colName);
		if (cell == null)
			return null;
		Object value = cell.getValue();
		String result = null;
		if (value instanceof CellTreeNode)
			result = (String) ((CellTreeNode) value).getValue();
		else
			result = value == null ? "" : value.toString();
		return result == null ? null : result.trim();
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
    	IRow row = null;
    	Calendar cal = Calendar.getInstance();
    	Date nowDate = null;
    	cal.setTime(new Date());
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	try {
			nowDate = dateFormat.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
		} catch (ParseException e1) {
			handUIException(e1);
		}
    	Date pcPlanDate = null;
    	for(int i = 0; i < kdtEntrys.getRowCount3(); i++) {
    		row = kdtEntrys.getRow(i);
    		//施工图交接时间
//			if(row.getCell("sgtRealDate").getValue()!=null && row.getCell("sgtDate").getValue()!=null && 
//					((Date)row.getCell("sgtRealDate").getValue()).compareTo((Date)row.getCell("sgtDate").getValue())>0){
//				row.getCell("sgtOverdue").setValue(Boolean.TRUE);
//    			row.getCell("sgtOverdue").getStyleAttributes().setBackground(Color.RED);
//			}else{
//				row.getCell("sgtOverdue").setValue(Boolean.FALSE);
//    			row.getCell("sgtOverdue").getStyleAttributes().setBackground(Color.WHITE);
//			}
			//实际合同签订时间
			if(row.getCell("csDate").getValue()!=null){
				pcPlanDate = (Date)row.getCell("csDate").getValue();
				if(row.getCell("csRealDate").getValue()==null && nowDate.compareTo(pcPlanDate)>0 && row.getCell("csPlanDate").getValue()==null){
					MsgBox.showInfo("第"+(i+1)+"行的计划合同签订时间不能为空！");
					SysUtil.abort();
				}
			}
			//实际开工时间
			if(row.getCell("startDate").getValue()!=null){
				pcPlanDate = (Date)row.getCell("startDate").getValue();
				if(row.getCell("startRealDate").getValue()==null && nowDate.compareTo(pcPlanDate)>0 && row.getCell("startPlanDate").getValue()==null){
					MsgBox.showInfo("第"+(i+1)+"行的计划开工时间不能为空！");
					SysUtil.abort();
				}
			}
			//实际竣工时间
			if(row.getCell("endDate").getValue()!=null){
				pcPlanDate = (Date)row.getCell("endDate").getValue();
				if(row.getCell("endRealDate").getValue()==null && nowDate.compareTo(pcPlanDate)>0 && row.getCell("endPlanDate").getValue()==null){
					MsgBox.showInfo("第"+(i+1)+"行的计划竣工时间不能为空！");
					SysUtil.abort();
				}
			}
			//实际合同签订完成时间
			if(row.getCell("csendDate").getValue()!=null){
				pcPlanDate = (Date)row.getCell("csendDate").getValue();
				if(row.getCell("csendRealDate").getValue()==null && nowDate.compareTo(pcPlanDate)>0 && row.getCell("csendPlanDate").getValue()==null){
					MsgBox.showInfo("第"+(i+1)+"行的计划合同签订完成时间不能为空！");
					SysUtil.abort();
				}
			}
			
		}
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
        setRedBg();
        MsgBox.showInfo("审核成功！");
//        ((EditUI)getUIWindow().getUIObject()).setSaved(true);
        if(!isSaved())
        	setSaved(true);
    }

    /**
     * output actionUnaudit_actionPerformed
     */
    public void actionUnaudit_actionPerformed(ActionEvent e) throws Exception
    {
    	FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
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
        editData.setIsNew(false);
        actionEdit.setEnabled(true);
        actionRemove.setEnabled(true);
        loadData();
        setRedBg();
        MsgBox.showInfo("反审核成功！");
//        ((EditUI)getUIWindow().getUIObject()).setSaved(true);
        if(!isSaved())
        	setSaved(true);
    }

    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
    	super.actionEdit_actionPerformed(e);
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
        	objectValue.setBizDate(new java.util.Date());
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