/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.costkf.CQGSFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringEntryInfo;
import com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringFactory;
import com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.bos.ctrl.data.process.dataset.group.aggregate.COUNT;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;

/**
 * output class name
 */
public class DecorationEngineeringEditUI extends AbstractDecorationEngineeringEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(DecorationEngineeringEditUI.class);
    
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
		contcompany.setEnabled(false);
		state.setEnabled(false);
		txtVersion.setEditable(false);
		contProjectName.setVisible(true);
		chklasted.setEnabled(false);
	
		super.onLoad();
		chkMenuItemSubmitAndAddNew.setSelected(false); //连续新增设置不可编辑
		//列不可编辑
		kdtEntrys.getColumn("renovationAreaIndex").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("proportion").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("sumRenovationAreaIndex").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("avgRenovationAreaIndex").getStyleAttributes().setLocked(true);
		
		kdtEntrys.getRow(0).getStyleAttributes().setLocked(true);
		kdtEntrys.getCell(0, "decorationArea").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(0, "renovationNumber").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(0, "sumDecorationArea").getStyleAttributes().setLocked(false);
		
		kdtEntrys.getRow(3).getStyleAttributes().setLocked(true);
		kdtEntrys.getCell(3, "decorationArea").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(3, "renovationNumber").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(3, "sumDecorationArea").getStyleAttributes().setLocked(false);
		
		kdtEntrys.getRow(6).getStyleAttributes().setLocked(true);
		kdtEntrys.getCell(6, "decorationArea").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(6, "renovationNumber").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(6, "sumDecorationArea").getStyleAttributes().setLocked(false);
		
		kdtEntrys.getRow(9).getStyleAttributes().setLocked(true);
		kdtEntrys.getCell(9, "decorationArea").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(9, "renovationNumber").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(9, "sumDecorationArea").getStyleAttributes().setLocked(false);
		
		kdtEntrys.getRow(12).getStyleAttributes().setLocked(true);
		kdtEntrys.getCell(12, "decorationArea").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(12, "renovationNumber").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(12, "sumDecorationArea").getStyleAttributes().setLocked(false);
		
		kdtEntrys.getRow(15).getStyleAttributes().setLocked(true);
		kdtEntrys.getCell(15, "decorationArea").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(15, "renovationNumber").getStyleAttributes().setLocked(false);
		kdtEntrys.getCell(15, "sumDecorationArea").getStyleAttributes().setLocked(false);
		
		
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
		initui();
    	setStyeEnabl();
	}
	
	
	private void initui() throws BOSException{
		// TODO Auto-generated method stub
		KDTMergeManager mergeManager = kdtEntrys.getMergeManager();	//融合管理器类
    	this.kdtEntrys.getIndexColumn().getStyleAttributes().setHided(true);//隐藏行号
    	
    	mergeManager.mergeBlock(0, 3, 2, 3);//装修面积
    	mergeManager.mergeBlock(0, 7, 2, 7);//装修套数
    	mergeManager.mergeBlock(0, 9, 2, 9);//总装修面积
    	mergeManager.mergeBlock(3, 3, 5, 3);
    	mergeManager.mergeBlock(3, 7, 5, 7);
    	mergeManager.mergeBlock(3, 9, 5, 9);
    	mergeManager.mergeBlock(6, 3, 8, 3);
    	mergeManager.mergeBlock(6, 7, 8, 7);
    	mergeManager.mergeBlock(6, 9, 8, 9);
    	mergeManager.mergeBlock(9, 3, 11, 3);
    	mergeManager.mergeBlock(9, 7, 11, 7);
    	mergeManager.mergeBlock(9, 9, 11, 9);
    	mergeManager.mergeBlock(12, 3, 14, 3);
    	mergeManager.mergeBlock(12, 7, 14, 7);
    	mergeManager.mergeBlock(12, 9, 14, 9);
    	mergeManager.mergeBlock(15, 3, 17, 3);
    	mergeManager.mergeBlock(15, 7, 17, 7);
    	mergeManager.mergeBlock(15, 9, 17, 9);
    	mergeManager.mergeBlock(0, 11, 17, 11);//平均装修面积
    	
    	//行宽
    	int i;
    	for(i=0;i<kdtEntrys.getRowCount();i++)
    	{
    		kdtEntrys.getRow(i).setHeight(20);
    	}
    
    	KDFormattedTextField kdtEntrys_proportion_TextField = new KDFormattedTextField();
        kdtEntrys_proportion_TextField.setName("kdtEntrys_proportion_TextField");
        kdtEntrys_proportion_TextField.setVisible(true);
        kdtEntrys_proportion_TextField.setEditable(true);
        kdtEntrys_proportion_TextField.setHorizontalAlignment(2);
        kdtEntrys_proportion_TextField.setDataType(2);
        kdtEntrys_proportion_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntrys_proportion_CellEditor = new KDTDefaultCellEditor(kdtEntrys_proportion_TextField);
        this.kdtEntrys.getColumn("proportion").setEditor(kdtEntrys_proportion_CellEditor);
        this.kdtEntrys.getColumn("proportion").getStyleAttributes().setNumberFormat("0.00%");
	}

	//    审核反审核
	public void actionAduit_actionPerformed(ActionEvent e) throws Exception {
		ControlState();
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED,CANTAUDIT);
		super.actionAduit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED,CANTUNAUDIT);
		super.actionUnAudit_actionPerformed(e);
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
	
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception { //分录值改变事件
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		
		String columnKey = this.kdtEntrys.getColumnKey(colIndex);
		if(columnKey.equals("decorationArea")){
			Object value = e.getValue();
			this.kdtEntrys.getCell(rowIndex+1, colIndex).setValue(value);
			this.kdtEntrys.getCell(rowIndex+2, colIndex).setValue(value);
		}
		if(columnKey.equals("sumDecorationArea")){
			Object value = e.getValue();
			this.kdtEntrys.getCell(rowIndex+1, colIndex).setValue(value);
			this.kdtEntrys.getCell(rowIndex+2, colIndex).setValue(value);
		}
		
		IRow row = this.kdtEntrys.getRow(rowIndex);
		
		do {
			if(UIRuleUtil.isNull(row.getCell("key").getValue()))
				break;
			else
				row = this.kdtEntrys.getRow(row.getRowIndex()-1);
		} while (true);
		
		BigDecimal Price = BigDecimal.ZERO;
		BigDecimal renovationAreaIndex = BigDecimal.ZERO;
		BigDecimal sumPrice = BigDecimal.ZERO;
		BigDecimal sumRenovationAreaIndex = BigDecimal.ZERO;
		
		
		row = this.kdtEntrys.getRow(row.getRowIndex()+1);
		row.getCell("renovationAreaIndex").setValue(FDCHelper.divide(row.getCell("Price").getValue(), row.getCell("decorationArea").getValue(), 2, 4));
		Price = FDCHelper.add(Price, row.getCell("Price").getValue());
		renovationAreaIndex = FDCHelper.add(renovationAreaIndex, row.getCell("renovationAreaIndex").getValue());
		row.getCell("sumRenovationAreaIndex").setValue(FDCHelper.divide(row.getCell("sumPrice").getValue(), row.getCell("sumDecorationArea").getValue(), 2, 4));
		sumPrice = FDCHelper.add(sumPrice, row.getCell("sumPrice").getValue());
		sumRenovationAreaIndex = FDCHelper.add(sumRenovationAreaIndex, row.getCell("sumRenovationAreaIndex").getValue());
		
		row = this.kdtEntrys.getRow(row.getRowIndex()+1);
		row.getCell("renovationAreaIndex").setValue(FDCHelper.divide(row.getCell("Price").getValue(), row.getCell("decorationArea").getValue(), 2, 4));
		Price = FDCHelper.add(Price, row.getCell("Price").getValue());
		renovationAreaIndex = FDCHelper.add(renovationAreaIndex, row.getCell("renovationAreaIndex").getValue());
		row.getCell("sumRenovationAreaIndex").setValue(FDCHelper.divide(row.getCell("sumPrice").getValue(), row.getCell("sumDecorationArea").getValue(), 2, 4));
		sumPrice = FDCHelper.add(sumPrice, row.getCell("sumPrice").getValue());
		sumRenovationAreaIndex = FDCHelper.add(sumRenovationAreaIndex, row.getCell("sumRenovationAreaIndex").getValue());
		
	
		
		row = this.kdtEntrys.getRow(row.getRowIndex()-2);
		row.getCell("Price").setValue(Price);
		row.getCell("renovationAreaIndex").setValue(renovationAreaIndex);
		row.getCell("sumPrice").setValue(sumPrice);
		row.getCell("sumRenovationAreaIndex").setValue(sumRenovationAreaIndex);
		
		
		row.getCell("proportion").setValue(1);
		
		row = this.kdtEntrys.getRow(row.getRowIndex()+1);
		row.getCell("proportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
		
		row = this.kdtEntrys.getRow(row.getRowIndex()+1);
		row.getCell("proportion").setValue(FDCHelper.divide(row.getCell("Price").getValue(), Price, 4, 4));
		
		
		IRow row1 = this.kdtEntrys.getRow(rowIndex);
		do {
			if(UIRuleUtil.isNull(row1.getCell("key").getValue()))
				break;
			else
				row1 = this.kdtEntrys.getRow(row1.getRowIndex()-1);
		} while (true);
		BigDecimal sumPrice1 = BigDecimal.ZERO;
		BigDecimal sumDecorationArea = BigDecimal.ZERO;
		BigDecimal avg = BigDecimal.ZERO;
		//修改还需
		row1 = this.kdtEntrys.getRow(row1.getRowIndex()+1);
		sumPrice1 = FDCHelper.add(sumPrice1, row1.getCell("sumPrice").getValue());
		sumDecorationArea = FDCHelper.add(sumDecorationArea, row1.getCell("sumDecorationArea").getValue());
		row1 = this.kdtEntrys.getRow(row1.getRowIndex()+1);
		sumPrice1 = FDCHelper.add(sumPrice1, row1.getCell("sumPrice").getValue());
		row1 = this.kdtEntrys.getRow(row1.getRowIndex()-2);
		avg=FDCHelper.divide(sumPrice1,sumDecorationArea,4,4);
		avg=FDCHelper.add(avg, BigDecimal.ZERO);
		row1.getCell("avgRenovationAreaIndex").setValue(FDCHelper.divide(avg, 2, 4, 4));
		
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
    public DecorationEngineeringEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields(){
    	super.loadFields();
    	if(editData.getEntrys().size() > 0){
    		//第1行
        	kdtEntrys.getRow(0).getCell("title").setValue("1.地下门厅");
        	//第2行
        	kdtEntrys.getRow(1).getCell("title").setValue("1.1硬装");
        	kdtEntrys.getRow(1).getCell("key").setValue("a");
        	//第3行
        	kdtEntrys.getRow(2).getCell("title").setValue("1.2软装");
        	kdtEntrys.getRow(2).getCell("key").setValue("b");
        	//第4行
        	kdtEntrys.getRow(3).getCell("title").setValue("2.一层门厅");
        	//第5行
        	kdtEntrys.getRow(4).getCell("title").setValue("2.1硬装");
        	kdtEntrys.getRow(4).getCell("key").setValue("a");
        	//第6行
        	kdtEntrys.getRow(5).getCell("title").setValue("2.2软装");
        	kdtEntrys.getRow(5).getCell("key").setValue("b");
        	//第7行
        	kdtEntrys.getRow(6).getCell("title").setValue("3.标准层");
        	//第8行
        	kdtEntrys.getRow(7).getCell("title").setValue("3.1硬装");
        	kdtEntrys.getRow(7).getCell("key").setValue("a");
        	//第9行
        	kdtEntrys.getRow(8).getCell("title").setValue("3.2软装");
        	kdtEntrys.getRow(8).getCell("key").setValue("b");
        	//第10行
        	kdtEntrys.getRow(9).getCell("title").setValue("4.楼梯间");
        	//第11行
        	kdtEntrys.getRow(10).getCell("title").setValue("4.1硬装");
        	kdtEntrys.getRow(10).getCell("key").setValue("a");
        	//第12行
        	kdtEntrys.getRow(11).getCell("title").setValue("4.2软装");
        	kdtEntrys.getRow(11).getCell("key").setValue("b");
        	//第13行
        	kdtEntrys.getRow(12).getCell("title").setValue("5.架空层");
        	//第14行
        	kdtEntrys.getRow(13).getCell("title").setValue("5.1硬装");
        	kdtEntrys.getRow(13).getCell("key").setValue("a");
        	//第15行
        	kdtEntrys.getRow(14).getCell("title").setValue("5.2软装");
        	kdtEntrys.getRow(14).getCell("key").setValue("b");;
        	//第16行
        	kdtEntrys.getRow(15).getCell("title").setValue("6.其他");
        	//第17行
        	kdtEntrys.getRow(16).getCell("title").setValue("6.1硬装");
        	kdtEntrys.getRow(16).getCell("key").setValue("a");;
        	//第18行
        	kdtEntrys.getRow(17).getCell("title").setValue("6.2软装");
        	kdtEntrys.getRow(17).getCell("key").setValue("b");
    	}
    	
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    protected void verifyInput(ActionEvent e) throws Exception {
    	if(this.prmtStyle.getValue() == null){
    		FDCMsgBox.showWarning("式样不能为空");
    		SysUtil.abort();
    	}//校验版本号设置式样f7不可编辑
    	if(txtVersion.getText().equals("1")){
    		FilterInfo filInfo = new FilterInfo();
    		filInfo.getFilterItems().add(new FilterItemInfo("ProjectName.id",editData.getProjectName().getId()));
    		filInfo.getFilterItems().add(new FilterItemInfo("Style.id",editData.getStyle().getId()));
    		if(editData.getId()!=null){
    			filInfo.getFilterItems().add(new FilterItemInfo("id",editData.getId(),CompareType.NOTEQUALS));
    			if(DecorationEngineeringFactory.getRemoteInstance().exists(filInfo)){
    				MsgBox.showWarning("已有单据不能新增");
    				SysUtil.abort();
    			}
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
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        
			super.actionSubmit_actionPerformed(e);
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
    
    protected void initDataStatus() {
    	super.initDataStatus();
    }
    
    protected void initDapButtons() throws Exception {
    	super.initDapButtons();
    	setStyeEnabl();
    }
    //判断版本号并且设置式样不可编辑
    private void setStyeEnabl(){
    	if(txtVersion.getText().equals("1"))
    		prmtStyle.setEnabled(true);
    	else
    		prmtStyle.setEnabled(false);
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
        return com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringFactory.getRemoteInstance();
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
    	if(getUIContext().get("DecorationEngineeringInfo")!=null){
			DecorationEngineeringInfo info = (DecorationEngineeringInfo)getUIContext().get("DecorationEngineeringInfo");
			info.setId(null);
			
			info.setState(FDCBillStateEnum.SAVED);
			info.setVersion(info.getVersion()+1);
			info.setLasted(false);
			for (int i = 0; i < info.getEntrys().size(); i++) 
				info.getEntrys().get(i).setId(null);
			return info;
		}else{
        com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setCompany(SysContext.getSysContext().getCurrentFIUnit().getName());//新增单据时带出当前公司
		
		CurProjectInfo projInfo = (CurProjectInfo)getUIContext().get("project"); 
		if(projInfo==null){
			MsgBox.showWarning("请选择节点");
			abort();
		}else{
				objectValue.setProjectName(projInfo);
		}
		
		for(int i = 0; i < 18; i++) {
			objectValue.getEntrys().add(new DecorationEngineeringEntryInfo());
		}
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setVersion(1); 
		return objectValue;
		}
    }

}