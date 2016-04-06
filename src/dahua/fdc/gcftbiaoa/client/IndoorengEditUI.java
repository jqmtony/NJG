/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.Count;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorengCollection;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorengEntryCollection;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorengEntryInfo;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorengFactory;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.scm.sd.channel.web.AduitAction;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.bos.ctrl.data.process.dataset.group.aggregate.COUNT;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;

/**
 * output class name
 */
public class IndoorengEditUI extends AbstractIndoorengEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(IndoorengEditUI.class);
    private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";
	private final static String CANTUNAUDIT = "cantUnAudit";
	private final static String CANTAUDIT = "cantAudit";
    
	
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning)
	throws Exception {
		// 检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = editData != null && editData.getState() != null
		&& editData.getState().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

		if (getOprtState().equals(STATUS_EDIT)) {
			String warn = null;
			if (state.equals(FDCBillStateEnum.AUDITTED)) {
				warn = CANTUNAUDITEDITSTATE;
			} else {
				warn = CANTAUDITEDITSTATE;
			}
			MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			SysUtil.abort();
		}
	}
	public void onLoad() throws Exception {
		State.setEnabled(false);
		txtVersion.setEditable(false);
		contProjectName.setVisible(true);
		chklasted.setEnabled(false);
		contauditTime.setEnabled(false);
	
		super.onLoad();
		chkMenuItemSubmitAndAddNew.setSelected(false); //连续新增设置不可编辑
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
//		initui();
		kdtEntrys.getColumn("Sumproportion").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("DecorateAreaIndex").getStyleAttributes().setLocked(true);
//		kdtEntrys.getColumn("danwei").getStyleAttributes().setLocked(true);
		
//		kdtEntrys.getRow(0).getStyleAttributes().setLocked(true);
//		kdtEntrys.getRow(1).getStyleAttributes().setLocked(true);
//		kdtEntrys.getRow(2).getStyleAttributes().setLocked(true);
//		kdtEntrys.getRow(12).getStyleAttributes().setLocked(true);
//		kdtEntrys.getRow(26).getStyleAttributes().setLocked(true);
//		kdtEntrys.getRow(33).getStyleAttributes().setLocked(true);
	}
	private void initui() throws BOSException{
		KDTMergeManager mergeManager = kdtEntrys.getMergeManager();	//融合管理器类
//    	this.kdtEntrys.getIndexColumn().getStyleAttributes().setHided(true);//隐藏行号
    	
    	mergeManager.mergeBlock(0, 7, 2,9);
    	mergeManager.mergeBlock(0, 10, 2,10);
    	mergeManager.mergeBlock(3, 7, 11,10);
    	mergeManager.mergeBlock(42, 7, 42,10);
    	
     	this.kdtEntrys.getColumn("Sumproportion").getStyleAttributes().setNumberFormat("0.00%");
	}
//  审核反审核
	public void actionAduit_actionPerformed(ActionEvent e) throws Exception {
		ControlState();
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED,CANTAUDIT);
		super.actionAduit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED,CANTUNAUDIT);
		super.actionUnAduit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
		btnSubmit.setEnabled(true);
	}

	public void onShow() throws Exception {
		super.onShow();
		
		KDTSortManager sortMange = kdtEntrys.getSortMange();	//排序管理器
		sortMange.setSortAuto(false);
		kdtEntrys_detailPanel.getAddNewLineButton().setVisible(false);
		kdtEntrys_detailPanel.getRemoveLinesButton().setVisible(false);
		kdtEntrys_detailPanel.getInsertLineButton().setVisible(false);
	}
	
	public void ControlState(){
		if(editData.getState().equals(FDCBillStateEnum.SAVED)){
			MsgBox.showWarning("请先提交，再审核");
			abort();
		}
	}
    /**
     * output class constructor
     */
    public IndoorengEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        if(editData.getEntrys().size() > 0){
    		//第1行
        	kdtEntrys.getRow(0).getCell("ComePrice").setValue("其中工程处实施内容装修面积指标为：");
        	kdtEntrys.getRow(0).getCell("ComePrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
        	kdtEntrys.getRow(3).getCell("ComePrice").setValue("————");
        	kdtEntrys.getCell(3, "ComePrice").getStyleAttributes().setLocked(true);
        	kdtEntrys.getRow(3).getCell("ComePrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
        	//最后1行
        	kdtEntrys.getRow(42).getCell("ComePrice").setValue("————");
        }
        
        int maxDepth = 0;
        for (int i = 0; i <kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			int treeLv = UIRuleUtil.getString(row.getCell("key").getValue()).split("!").length-1;
			row.setTreeLevel(treeLv);
			
			if(treeLv>maxDepth)
				maxDepth = treeLv;
			
			if(treeLv<3){
				row.getStyleAttributes().setLocked(true);
				row.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			}
		}
        
        kdtEntrys.getRow(26).getStyleAttributes().setLocked(true);
        kdtEntrys.getRow(26).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
        kdtEntrys.getRow(33).getStyleAttributes().setLocked(true);
        kdtEntrys.getRow(33).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
        kdtEntrys.getRow(42).getStyleAttributes().setLocked(false);
        kdtEntrys.getRow(42).getStyleAttributes().setBackground(Color.white);
    	kdtEntrys.getCell(0, "other").getStyleAttributes().setLocked(false);
    	kdtEntrys.getCell(0, "other").getStyleAttributes().setBackground(Color.white);
        kdtEntrys.getColumn("title").getStyleAttributes().setLocked(true);
        kdtEntrys.getTreeColumn().setDepth(maxDepth);
        kdtEntrys.repaint();
        
        try {
			initui();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
//    	editData.getSoft();
//    	editData.setSoft(txtSoft.getBigDecimalValue());
        super.storeFields();
    }
    protected void verifyInput(ActionEvent e) throws Exception {
    	if(this.prmtRoom.getValue() == null){
    		FDCMsgBox.showWarning("房型不能为空");
    		SysUtil.abort();
    	}//校验版本号设置式样f7不可编辑
    	if(txtVersion.getText().equals("1")){
    		FilterInfo filInfo = new FilterInfo();
    		filInfo.getFilterItems().add(new FilterItemInfo("ProjectName.id",editData.getProjectName().getId()));
    		filInfo.getFilterItems().add(new FilterItemInfo("Room.id",editData.getRoom().getId()));
    		if(editData.getId()!=null){
    			filInfo.getFilterItems().add(new FilterItemInfo("id",editData.getId(),CompareType.NOTEQUALS));
    		}
    		if(IndoorengFactory.getRemoteInstance().exists(filInfo)){
				MsgBox.showWarning("已有该房型的单据，请修改房型，再提交。");
				SysUtil.abort();
			}
    	}
    	super.verifyInput(e);
    }
    
	/**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
    }

    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	for (int j = 0; j <kdtEntrys.getRowCount(); j++) {
			IRow rowx = kdtEntrys.getRow(j);
			
			if(rowx.getStyleAttributes().isLocked()){
				rowx.getCell("Price").setValue(BigDecimal.ZERO);
				rowx.getCell("DecorateArea").setValue(BigDecimal.ZERO);
				if(!(j==0 || j==1 || j==2)){
					rowx.getCell("ComePrice").setValue(BigDecimal.ZERO);
					rowx.getCell("Areaproportion").setValue(BigDecimal.ZERO);
					
				}
				
			}
		}
    	Map sumMap=new HashMap();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			String number = UIRuleUtil.getString(row.getCell("key").getValue()).trim();
			sumMap.put(number, row);
			int level = row.getTreeLevel();
			if(number.indexOf("!")>0){ 
	       		String pnumber=number.substring(0, number.lastIndexOf("!"));
	       		for(int k=0;k<level;k++){
	       			if(sumMap.get(pnumber)!=null){
	       				IRow prow=(IRow) sumMap.get(pnumber);
	       				
	       				if(prow.getCell("Price").getValue()!=null){
	       					if(prow.getRowIndex() == 1)
		       					prow.getCell("Price").setValue(kdtEntrys.getCell(2,"Price").getValue());
	       					else
	       						prow.getCell("Price").setValue(FDCHelper.add(prow.getCell("Price").getValue(), row.getCell("Price").getValue()));
	       				}else{
	       					if(prow.getRowIndex() == 1)
		       					prow.getCell("Price").setValue(kdtEntrys.getCell(2,"Price").getValue());
	       					else
	       						prow.getCell("Price").setValue(row.getCell("Price").getValue());
	       				}
	       				
	       				if(prow.getCell("DecorateArea").getValue()!=null)
	       					if(prow.getRowIndex() == 1)
		       					prow.getCell("DecorateArea").setValue(kdtEntrys.getCell(2,"DecorateArea").getValue());
	       					else
	       					prow.getCell("DecorateArea").setValue(FDCHelper.add(prow.getCell("DecorateArea").getValue(), row.getCell("DecorateArea").getValue()));
	       				else{
	       					if(prow.getRowIndex() == 1)
		       					prow.getCell("DecorateArea").setValue(kdtEntrys.getCell(2,"DecorateArea").getValue());
	       					else
	       					prow.getCell("DecorateArea").setValue(row.getCell("DecorateArea").getValue());
	       				}
	       				
	       				prow.getCell("DecorateAreaIndex").setValue(FDCHelper.divide(prow.getCell("Price").getValue(), prow.getCell("DecorateArea").getValue(), 4, 4));
	       				row.getCell("DecorateAreaIndex").setValue(FDCHelper.divide(row.getCell("Price").getValue(), row.getCell("DecorateArea").getValue(), 4, 4));
	       				
	       				prow.getCell("Sumproportion").setValue(FDCHelper.divide(prow.getCell("Price").getValue(), prow.getCell("Price").getValue(), 4, 4));
	       				
	       				if(!(prow.getRowIndex()==0 || prow.getRowIndex()==1 || prow.getRowIndex()==2)){
	       					if(prow.getCell("ComePrice").getValue()!=null)
		       					prow.getCell("ComePrice").setValue(FDCHelper.add(prow.getCell("ComePrice").getValue(), row.getCell("ComePrice").getValue()));
							else
								prow.getCell("ComePrice").setValue(row.getCell("ComePrice").getValue());
							if(prow.getCell("Areaproportion").getValue()!=null)
								prow.getCell("Areaproportion").setValue(FDCHelper.add(prow.getCell("Areaproportion").getValue(), row.getCell("Areaproportion").getValue()));
							else
								prow.getCell("Areaproportion").setValue(row.getCell("Areaproportion").getValue());
	       				}
	       			}
	       			if(pnumber.indexOf("!")>0)
   						pnumber=pnumber.substring(0, pnumber.lastIndexOf("!"));
   				}
	       		
			}
		}
		//一级汇总如下
		kdtEntrys.getCell(0,"Price").setValue(FDCHelper.add(kdtEntrys.getCell(1,"Price").getValue(), kdtEntrys.getCell(42,"Price").getValue()));
		BigDecimal value = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0,"Price").getValue());
		kdtEntrys.getCell(0,"DecorateArea").setValue(FDCHelper.add(kdtEntrys.getCell(1,"DecorateArea").getValue(), kdtEntrys.getCell(42,"DecorateArea").getValue()));
		for(int i = 1; i < this.kdtEntrys.getRowCount(); i++){
			IRow row = this.kdtEntrys.getRow(i);
			row.getCell("Sumproportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), value, 4, 4));
			
		}
		txtSoft.setValue(UIRuleUtil.getBigDecimal(kdtEntrys.getCell(42, "DecorateAreaIndex").getValue()));
		txtHard.setValue(UIRuleUtil.getBigDecimal(kdtEntrys.getCell(1, "DecorateAreaIndex").getValue()));
		txtSalesAreaIndex.setValue(UIRuleUtil.getBigDecimal(kdtEntrys.getCell(0, "DecorateAreaIndex").getValue()));
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
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
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
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	BigDecimal Price1 = UIRuleUtil.getBigDecimal(kdtEntrys.getCell(2, "Price").getValue());
    	BigDecimal Price2 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(12, "Price").getValue());
    	BigDecimal DecorateArea1 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(2, "DecorateArea").getValue());
    	BigDecimal DecorateArea2 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(12, "DecorateArea").getValue());
    	if(Price1.compareTo(Price2)!=0){
    		MsgBox.showWarning("1.1的造价与1.2的造价不相等，请修改提交。");
    		SysUtil.abort();
    	}
    	if(DecorateArea1.compareTo(DecorateArea2)!=0){
    		MsgBox.showWarning("1.1的装修面积与1.2的装修面积不相等，请修改提交。");
    		SysUtil.abort();
    	}
//        BigDecimal Price3 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(14, "Price").getValue());
//        BigDecimal Price4 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(17, "Price").getValue());
//        BigDecimal GreenArea3 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(14, "GreenArea").getValue());
//        BigDecimal GreenArea4 = UIRuleUtil.getBigDecimal( kdtEntrys.getCell(17, "GreenArea").getValue());
//        if(Price3.compareTo(Price4)!=0){
//        	MsgBox.showWarning("2.1的造价与1.2的造价不相等，请修改提交。");
//        	SysUtil.abort();
//        }
//        if(GreenArea3.compareTo(GreenArea4)!=0){
//        	MsgBox.showWarning("1.1的硬景与1.2的硬景不相等，请修改提交。");
//        	SysUtil.abort();
//        }
        super.actionSubmit_actionPerformed(e);
    }
    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
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
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
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
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }
  

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.gcftbiaoa.IndoorengFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }
    
    public void addEntryData(IndoorengEntryCollection entrColl,String str,String key){
    	IndoorengEntryInfo entryInfo = new IndoorengEntryInfo();
    	entryInfo.put("title", str);
    	entryInfo.put("key", key);
    	entrColl.add(entryInfo);
    }
    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
    	if(getUIContext().get("IndoorengInfo")!=null){
    		IndoorengInfo info = (IndoorengInfo)getUIContext().get("IndoorengInfo");
    		info.setId(null);

    		info.setState(FDCBillStateEnum.SAVED);
    		info.setVersion(info.getVersion()+1);
    		info.setLasted(false);
    		for (int i = 0; i < info.getEntrys().size(); i++) 
    			info.getEntrys().get(i).setId(null);
    		return info;
    	}else{
        com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.IndoorengInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));

		CurProjectInfo projInfo = (CurProjectInfo)getUIContext().get("project"); 
		if(projInfo==null){
			MsgBox.showWarning("请选择节点");
			abort();
		}else{
			objectValue.setProjectName(projInfo);
		}
		IndoorengEntryCollection entrColl = objectValue.getEntrys();
//		addEntryData(entrColl, "装修工程", "1");
//		addEntryData(entrColl, "1.硬装", "1!1");
//		addEntryData(entrColl, "1.1按房间划分", "1!1");
//		addEntryData(entrColl, "1.1.1客餐厅", "1!1!1");
//		addEntryData(entrColl, "1.1.2主卧", "1!1!2");
//		addEntryData(entrColl, "1.1.3次卧", "1!1!3");
//		addEntryData(entrColl, "1.1.4客卧", "1!1!4");
//		addEntryData(entrColl, "1.1.5书房", "1!1!5");
//		addEntryData(entrColl, "1.1.6厨房", "1!1!6");
//		addEntryData(entrColl, "1.1.7主卫", "1!1!7");
//		addEntryData(entrColl, "1.1.8次卫", "1!1!8");
//		addEntryData(entrColl, "1.1.9阳台、露台及其他", "1!1!9");
//		addEntryData(entrColl, "1.2按部品部件划分", "1!2");
//		addEntryData(entrColl, "1.2.1底板", "1!2!1");
//		addEntryData(entrColl, "1.2.2地砖", "1!2!2");
//		addEntryData(entrColl, "1.2.3大理石", "1!2!3");
//		addEntryData(entrColl, "1.2.4墙砖", "1!2!4");
//		addEntryData(entrColl, "1.2.5墙纸", "1!2!5");
//		addEntryData(entrColl, "1.2.6乳胶漆", "1!2!6");
//		addEntryData(entrColl, "1.2.7木制品、木饰面", "1!2!7");
//		addEntryData(entrColl, "1.2.8地暖系统", "1!2!8");
//		addEntryData(entrColl, "1.2.9空调系统", "1!2!9");
//		addEntryData(entrColl, "1.2.10恒湿系统", "1!2!10");
//		addEntryData(entrColl, "1.2.11新风系统", "1!2!11");
//		addEntryData(entrColl, "1.2.12净、软水系统", "1!2!12");
//		addEntryData(entrColl, "1.2.13橱柜", "1!2!13");
//		addEntryData(entrColl, "1.2.14厨房设备", "1!2!14");
//		addEntryData(entrColl, "1.2.14.1油烟机", "1!2!14!1");
//		addEntryData(entrColl, "1.2.14.2煤气灶", "1!2!14!2");
//		addEntryData(entrColl, "1.2.14.3消毒柜", "1!2!14!3");
//		addEntryData(entrColl, "1.2.14.4烤箱", "1!2!14!4");
//		addEntryData(entrColl, "1.2.14.5水槽及龙头", "1!2!14!5");
//		addEntryData(entrColl, "1.2.14.6其他", "1!2!14!6");
//		addEntryData(entrColl, "1.2.15.洁具", "1!2!15");
//		addEntryData(entrColl, "1.2.15.1座便器", "1!2!15!1");
//		addEntryData(entrColl, "1.2.15.2卫洗丽", "1!2!15!2");
//		addEntryData(entrColl, "1.2.15.3台盆及龙头", "1!2!15!3");
//		addEntryData(entrColl, "1.2.15.4浴缸及龙头", "1!2!15!4");
//		addEntryData(entrColl, "1.2.15.5淋浴及龙头", "1!2!15!5");
//		addEntryData(entrColl, "1.2.15.6其他", "1!2!15!6");
//		addEntryData(entrColl, "1.2.16智能系统", "1!2!16");
//		addEntryData(entrColl, "1.2.17其他", "1!2!17");
//		addEntryData(entrColl, "2.软装", "1!1");
		addEntryData(entrColl, "装修工程", "1");
		addEntryData(entrColl, "1.硬装", "1!1");
		addEntryData(entrColl, "1.1按房间划分", "1!1!1");
		addEntryData(entrColl, "1.1.1客餐厅", "1!1!1!1");
		addEntryData(entrColl, "1.1.2主卧", "1!1!1!2");
		addEntryData(entrColl, "1.1.3次卧", "1!1!1!3");
		addEntryData(entrColl, "1.1.4客卧", "1!1!1!4");
		addEntryData(entrColl, "1.1.5书房", "1!1!1!5");
		addEntryData(entrColl, "1.1.6厨房", "1!1!1!6");
		addEntryData(entrColl, "1.1.7主卫", "1!1!1!7");
		addEntryData(entrColl, "1.1.8次卫", "1!1!1!8");
		addEntryData(entrColl, "1.1.9阳台、露台及其他", "1!1!1!9");
		addEntryData(entrColl, "1.2按部品部件划分", "1!1!2");
		addEntryData(entrColl, "1.2.1底板", "1!1!2!1");
		addEntryData(entrColl, "1.2.2地砖", "1!1!2!2");
		addEntryData(entrColl, "1.2.3大理石", "1!1!2!3");
		addEntryData(entrColl, "1.2.4墙砖", "1!1!2!4");
		addEntryData(entrColl, "1.2.5墙纸", "1!1!2!5");
		addEntryData(entrColl, "1.2.6乳胶漆", "1!1!2!6");
		addEntryData(entrColl, "1.2.7木制品、木饰面", "1!1!2!7");
		addEntryData(entrColl, "1.2.8地暖系统", "1!1!2!8");
		addEntryData(entrColl, "1.2.9空调系统", "1!1!2!9");
		addEntryData(entrColl, "1.2.10恒湿系统", "1!1!2!10");
		addEntryData(entrColl, "1.2.11新风系统", "1!1!2!11");
		addEntryData(entrColl, "1.2.12净、软水系统", "1!1!2!12");
		addEntryData(entrColl, "1.2.13橱柜", "1!1!2!13");
		addEntryData(entrColl, "1.2.14厨房设备", "1!1!2!14");
		addEntryData(entrColl, "1.2.14.1油烟机", "1!1!2!14!1");
		addEntryData(entrColl, "1.2.14.2煤气灶", "1!1!2!14!2");
		addEntryData(entrColl, "1.2.14.3消毒柜", "1!1!2!14!3");
		addEntryData(entrColl, "1.2.14.4烤箱", "1!1!2!14!4");
		addEntryData(entrColl, "1.2.14.5水槽及龙头", "1!1!2!14!5");
		addEntryData(entrColl, "1.2.14.6其他", "1!1!2!14!6");
		addEntryData(entrColl, "1.2.15.洁具", "1!1!2!15");
		addEntryData(entrColl, "1.2.15.1座便器", "1!1!2!15!1");
		addEntryData(entrColl, "1.2.15.2卫洗丽", "1!1!2!15!2");
		addEntryData(entrColl, "1.2.15.3台盆及龙头", "1!1!2!15!3");
		addEntryData(entrColl, "1.2.15.4浴缸及龙头", "1!1!2!15!4");
		addEntryData(entrColl, "1.2.15.5淋浴及龙头", "1!1!2!15!5");
		addEntryData(entrColl, "1.2.15.6其他", "1!1!2!15!6");
		addEntryData(entrColl, "1.2.16智能系统", "1!1!2!16");
		addEntryData(entrColl, "1.2.17其他", "1!1!2!17");
		addEntryData(entrColl, "2.软装", "1!1");
		
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setVersion(1); 
		return objectValue;
    	}
    }
}