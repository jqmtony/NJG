/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractProgrammingInfo;
import com.kingdee.eas.fdc.contract.ContractWithProgramFactory;
import com.kingdee.eas.fdc.contract.ContractWithProgramInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractWithProgramEditUI extends AbstractContractWithProgramEditUI
{
	private ContractBillInfo contractBill = null;
	// 最大版本（修订）
	private Set lastVerIds = new HashSet();
	
	private boolean isModified = false;
	
    /**
     * output class constructor
     */
    public ContractWithProgramEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
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
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	this.verify();
    	Map map = this.getUIContext();
		String contractId = (String) map.get(UIContext.ID);
		ContractBillInfo contractBill = new ContractBillInfo();
		contractBill.setId(BOSUuid.read(contractId));
		
		CoreBaseCollection colls = new CoreBaseCollection();
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row = getDetailTable().getRow(i);
			ContractWithProgramInfo info = (ContractWithProgramInfo)row.getUserObject();
			ContractProgrammingInfo programming = (ContractProgrammingInfo)row.getCell("number").getValue();
			if(info==null || info.getId()==null){
				info = new ContractWithProgramInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
			}
			info.setContractBill(contractBill);
			info.setProgramming(programming);
			info.setDescription((String)row.getCell("desc").getValue());
			colls.add(info);
			
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
		if(colls.size()>0){
			ContractWithProgramFactory.getRemoteInstance().delete(filter);
			ContractWithProgramFactory.getRemoteInstance().save(colls);
			ContractWithProgramHandler.fillTable(contractId, kdtProgramming, false);
		}else{
			ContractWithProgramFactory.getRemoteInstance().delete(filter);
		}
		showSaveSuccess();
		isModified = false;
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
    	if (getDetailTable() != null) {
			addLine(getDetailTable());
			appendFootRow(getDetailTable());
		}
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
    	if(getDetailTable().getRowCount() == 0) {
			MsgBox.showWarning(this, "请先选中行");
			SysUtil.abort();
		}
		
		KDTSelectManager selectManager = getDetailTable().getSelectManager();
		if (selectManager == null || selectManager.size() == 0) {
			MsgBox.showWarning(this, "请先选中行");
			SysUtil.abort();
		}
		for (int i = 0; i < selectManager.size(); i++) {
			KDTSelectBlock selectBlock = selectManager.get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow selectRow = this.getDetailTable().getRow(j);
				if (selectRow.getUserObject() != null) {
					selectRow.getCell("number").setUserObject("delete");
				}
			}
		}
		for (int i = 0; i < getDetailTable().getRowCount(); i++) {
			IRow row = (IRow) getDetailTable().getRow(i);
			if (row.getCell("number").getUserObject() != null) {
				getDetailTable().removeRow(row.getRowIndex());
				i--;
			}
		}
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
    
    protected void setBeforeAction() {
		getDetailTable().setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					for (int i = 0; i < getDetailTable().getSelectManager()
							.size(); i++) {
						KDTSelectBlock block = getDetailTable()
								.getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block
								.getEndRow(); rowIndex++) {
							for (int colIndex = block.getBeginCol(); colIndex <= block
									.getEndCol(); colIndex++) {
								if (colIndex != 0
										|| getDetailTable().getCell(rowIndex,
												colIndex).getStyleAttributes()
												.isLocked()) {
									e.setCancel(true);
									continue;
								}
								KDTEditEvent event = new KDTEditEvent(
										getDetailTable(), null, null, rowIndex,
										colIndex, true, 1);
								try {
									kdtProgramming_editStopped(event);
								} catch (Exception e1) {
									handUIExceptionAndAbort(e1);
								}
							}
						}
					}
				}
			}
		});

		/*
		 * KDTable的KDTEditListener仅在编辑的时候触发，
		 * KDTPropertyChangeListener则是在删除，粘贴等导致单元格value发生变化的时候都会触发。
		 */
		getDetailTable().addKDTPropertyChangeListener(
				new KDTPropertyChangeListener() {
					public void propertyChange(KDTPropertyChangeEvent evt) {
						// 表体单元格值发生变化
						if ((evt.getType() == KDTStyleConstants.BODY_ROW)
								&& (evt.getPropertyName()
										.equals(KDTStyleConstants.CELL_VALUE))) {
							if (getDetailTable().getClientProperty(
									"ACTION_PASTE") != null) {
								// 触发editStop事件
								int rowIndex = evt.getRowIndex();
								int colIndex = evt.getColIndex();
								if (colIndex != 0) {
									// 这里对整个表体cell做了监听，根据需要过滤
									return;
								}
								KDTEditEvent event = new KDTEditEvent(
										getDetailTable());
								event.setColIndex(colIndex);
								event.setRowIndex(rowIndex);
								event.setOldValue(null);
								ICell cell = getDetailTable().getCell(rowIndex,
										colIndex);
								if (cell == null) {
									return;
								}
								event.setValue(cell.getValue());
								try {
									kdtProgramming_editStopped(event);

								} catch (Exception e1) {
									handUIExceptionAndAbort(e1);
								}
							}
						}
					}
				});

	}
    
    protected void kdtProgramming_editStopped(KDTEditEvent e) throws Exception {
    	IRow row = getDetailTable().getRow(e.getRowIndex());
    	if (e.getColIndex() == getDetailTable().getColumn("number")
				.getColumnIndex()) {
    		if(e.getValue()!=null&&e.getValue() instanceof ContractProgrammingInfo){
    			ContractProgrammingInfo programming =(ContractProgrammingInfo)e.getValue();
    			if (e.getOldValue() != null
						&& (((ContractProgrammingInfo) e.getOldValue()).getId()
								.toString()).equals(programming.getId()
								.toString())) {
					return;
    			}else{
    				if(lastVerIds.contains(programming.getId().toString())&&programming.isIsFinal()){
            			row.getCell("number").setValue(programming);
            			row.getCell("name").setValue(programming.getName());
            			row.getCell("date").setValue(programming.getCreateTime());
            			row.getCell("amount").setValue(programming.getProgrammingMoney());
            			row.getCell("isImagePay").setValue(Boolean.valueOf(programming.isIsImagePay()));
//            			row.getCell("desc").setValue(programming.getDescription());
            			row.getCell("id").setValue(programming.getId().toString());	
        			}else{
//        				if(!programming.isIsFinal()){
        					FDCMsgBox.showWarning(this, "您选择的合约规划 "+programming.getNumber()+" 存在修订版本，请先审批修订版本！");
//        				}else if(!programming.isIsLastVersion()){
//        					FDCMsgBox.showWarning(this, "您选择的合约规划 "+programming.getNumber()+" 不是最新版本，请先审批！");
//        				}
            			row.getCell("number").setValue(null);
            			row.getCell("name").setValue(null);
            			row.getCell("date").setValue(null);
            			row.getCell("amount").setValue(null);
            			row.getCell("isImagePay").setValue(null);
//            			row.getCell("desc").setValue(programming.getDescription());
            			row.getCell("id").setValue(null);	
            			SysUtil.abort();
        			}
    				
    			}
    			isModified = true;
    			
    		}
    		if(e.getColIndex()==getDetailTable().getColumnIndex("number")){
        		if(e.getValue()!=null&&!e.getValue().equals(e.getOldValue())){
        			isModified = true;
        		}
        		if(e.getOldValue()!=null&&!e.getOldValue().equals(e.getValue())){
        			isModified = true;
        		}
        		
        	}
		}
    	if(e.getColIndex()==getDetailTable().getColumnIndex("desc")){
    		if(e.getValue()!=null&&!e.getValue().equals(e.getOldValue())){
    			isModified = true;
    		}
    		if(e.getOldValue()!=null&&!e.getOldValue().equals(e.getValue())){
    			isModified = true;
    		}
    	}
    }
    
    protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
    	if(obj==null){
			obj = new ContractWithProgramInfo();
		}else{
			ContractWithProgramInfo info =(ContractWithProgramInfo)obj;
			ContractProgrammingInfo programming =info.getProgramming();
			if(programming!=null){
				row.getCell("number").setValue(programming);
				row.getCell("name").setValue(programming.getName());
				row.getCell("date").setValue(programming.getCreateTime());
				row.getCell("amount").setValue(programming.getProgrammingMoney());
				row.getCell("isImagePay").setValue(Boolean.valueOf(programming.isIsImagePay()));
				row.getCell("desc").setValue(info.getDescription());
				row.getCell("id").setValue(info.getId());
			}
		}
    	row.setUserObject(obj);
    }
    
	protected KDTable getDetailTable() {
		return kdtProgramming;
	}
	
	/**
	 * 根据id显示窗体
	 */
	public static void showEditUI(IUIObject ui, String contractId, String oprt)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, contractId);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ContractWithProgramEditUI.class.getName(), uiContext, null,
						oprt);
		uiWindow.show();
	}
	
	protected IObjectValue createNewDetailData(KDTable table) {
        return new ContractWithProgramInfo();
	}
	
	private void initLastVersionData() throws Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid from t_con_contractprogramming where fcurprojectid=? and fislastversion=1");
		builder.addParam(contractBill.getCurProject().getId().toString());
		IRowSet rs = builder.executeQuery();
		while(rs.next()){
			lastVerIds.add(rs.getString("fid"));
		}
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		Map map = this.getUIContext();
		String contractId = (String) map.get(UIContext.ID);
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("curProject.id");
		
		contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(
				new ObjectUuidPK(BOSUuid.read(contractId)),selector);
		ContractWithProgramHandler.fillTable(contractId, getDetailTable(), false);		
		
		initLastVersionData();
		
		setBeforeAction();
		
		//
		String state = this.getOprtState();
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionAuditResult.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionViewSubmitProccess.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		actionNextPerson.setVisible(false);
		
		boolean isConSplit = isConSplit(contractId);
		if (state.equals("VIEW") || state.equals(STATUS_FINDVIEW) ||isConSplit) {
			this.kdtProgramming.getStyleAttributes().setLocked(true);
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionSubmit.setEnabled(false);
			this.actionSave.setEnabled(false);
		}
		if(!isConSplit){
			setRowLock();
		}
		 getDetailTable().getStyleAttributes().setLocked(false);
		
		KDBizPromptBox prmt = new KDBizPromptBox();
		prmt.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractProgrammingQuery");
		prmt.setVisible(true);
		prmt.setEditable(true);
		prmt.setDisplayFormat("$name$");
		prmt.setEditFormat("$number$");
		prmt.setCommitFormat("$number$");
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("programmingMoney"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("isImagePay"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("isfinal"));
		sic.add(new SelectorItemInfo("isLastVersion"));
		sic.add(new SelectorItemInfo("edition"));
		String sql ="select fprogrammingid from t_con_contractwithprogram ";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isFinal", Boolean.TRUE));
//		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.FALSE));
//		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("id", sql, CompareType.NOTINNER));
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.id", contractBill
						.getCurProject().getId().toString()));
//		filter.setMaskString("((#0 and #1) or #2) and #3 and #4");
//		filter.setMaskString("(#0 or #1) and #2 and #3");
		
		prmt.setEntityViewInfo(view);
		prmt.setSelectorCollection(sic);
		
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(prmt);
		getDetailTable().getColumn("number")
				.setEditor(cellEditor);
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new BizDataFormat("$number$"));
		getDetailTable().getColumn("number")
				.setRenderer(render);
		getDetailTable().getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("number").getStyleAttributes().setLocked(false);
		getDetailTable().getColumn("desc").getStyleAttributes().setLocked(false);
		getDetailTable().getColumn("number").setWidth(100);
		getDetailTable().getColumn("name").setWidth(100);
		getDetailTable().getColumn("desc").setWidth(200);
		
	}
	
	private void setRowLock(){
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row = getDetailTable().getRow(i);
			row.getStyleAttributes().setLocked(false);
		}
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return ContractWithProgramFactory.getRemoteInstance();
	}
	
	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public void setDataObject(IObjectValue dataObject) {
//		super.setDataObject(dataObject);
	}
	
	private boolean isConSplit(String contractId) throws EASBizException, BOSException, SQLException{
		boolean isConSplit = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
		if(FDCUtils.isCostSplit(null, contractId)){
			isConSplit = ContractCostSplitFactory.getRemoteInstance().exists(filter);
		}else{
			isConSplit = ConNoCostSplitFactory.getRemoteInstance().exists(filter);
		}
		return isConSplit;
	}
	
	private boolean isEdited(){
		getDetailTable().getSelectManager().select(0, 0);
		getDetailTable().getSelectManager().select(0, 1);
		if(getDetailTable().getUserObject()!=null){
			return true;
		}
		return false;
	}
	
	private void verify() {
		boolean isImagePay = false;
		Map dataMap = new HashMap();
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row = getDetailTable().getRow(i);
			ContractProgrammingInfo programming = (ContractProgrammingInfo)row.getCell("number").getValue();
			if(programming==null){
				FDCMsgBox.showWarning(this, "第 "+(i+1)+" 行合约规划不能为空!");
				this.abort();
			}
			boolean isImagePayValue = Boolean.valueOf(row.getCell("isImagePay").getValue().toString()).booleanValue();
			if(i==0){
				isImagePay = isImagePayValue;
			}else if(isImagePay!=isImagePayValue){
				FDCMsgBox.showWarning(this, "所选合约规划的 按形象进度付款 属性必须一致");
				this.abort();
			}
			if(dataMap.containsKey(programming.getId().toString())){
				int rowIndex = ((Integer)dataMap.get(programming.getId().toString())).intValue();
				FDCMsgBox.showWarning(this, "第 "+(i+1)+" 与第 "+rowIndex+" 行合约规划重复!");
				this.abort();
			}else{
				dataMap.put(programming.getId().toString(),new Integer(i+1));
			}
			String desc = (String)row.getCell("desc").getValue();
			if(desc!=null&&desc.length()>250){
				FDCMsgBox.showWarning(this, "第 "+(i+1)+" 行备注长度大于250个字符，请修改后保存!");
				this.abort();
			}
		}
	}
	
	public boolean destroyWindow() {
		if (isModified) {
			int option = MsgBox.showConfirm3(this, "数据已修改，是否保存并退出");
			if (option == MsgBox.YES) {
				verify();
				this.btnSave.doClick();
			}else if(option == MsgBox.CANCEL){
				this.abort();
			}
		}
		return super.destroyWindow();
	}
	
}