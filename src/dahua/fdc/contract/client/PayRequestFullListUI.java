/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonFilterPanel;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentFullListUI;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PayRequestFullListUI extends AbstractPayRequestFullListUI
{
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;   
	
	private Map proLongNameMap=new HashMap();
	/**
	 * output class constructor
	 */
	public PayRequestFullListUI() throws Exception {
		super();
	}
	protected boolean isShowAttachmentAction() {
		
		return false;
	}
	
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
    	    	
    	super.actionView_actionPerformed(e);
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e)
    {
    	super.prepareUIContext(uiContext,e);   	
    	
    	uiContext.put("PayRequestFullListUI",Boolean.TRUE);
    }
    
	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
//		super.actionTraceUp_actionPerformed(e);
		int[] selectedRows = KDTableUtil.getSelectedRows(getMainTable());
		if(selectedRows.length>0){
			for(int i=0;i<selectedRows.length;i++){
				IRow row=getMainTable().getRow(selectedRows[i]);
				String id=row.getCell("contractId").getValue().toString();
				if(PayReqUtils.isContractBill(id)){
					ContractPayPlanEditUI.showEditUI(this,id, "VIEW");
				}else{
					MsgBox.showWarning(this, "没有合同付款计划");
				}
			}
		}else{
			MsgBox.showWarning(this, "没有合同付款计划");
		}
	}
    
    
	public void actionClose_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		int[] selectedRows = KDTableUtil.getSelectedRows(getMainTable());
		for(int i=0;i<selectedRows.length;i++){
			IRow row=getMainTable().getRow(selectedRows[i]);
			String id=row.getCell("id").getValue().toString();
			boolean hasClosed=((Boolean)row.getCell("hasClosed").getValue()).booleanValue();
			if(id==null||hasClosed){
				MsgBox.showWarning(getRes("billhasClosed"));
				SysUtil.abort();
			}
		}
		
		PayRequestBillInfo billInfo=new PayRequestBillInfo();
		for(int i=0;i<selectedRows.length;i++){
			IRow row=getMainTable().getRow(selectedRows[i]);
			String id=row.getCell("id").getValue().toString();
			billInfo.setId(BOSUuid.read(id));
			billInfo.setHasClosed(true);
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("hasClosed");
			getBizInterface().updatePartial(billInfo, selector);
//			actionClose.setVisible(false);
//			actionClose.setEnabled(false);
//			actionUnClose.setVisible(true);
//			actionUnClose.setEnabled(true);
			row.getCell("hasClosed").setValue(Boolean.TRUE);
		}
		MsgBox.showInfo(getRes("closeSuccess"));

	}
	public void actionUnClose_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		int[] selectedRows = KDTableUtil.getSelectedRows(getMainTable());
		for(int i=0;i<selectedRows.length;i++){
			IRow row=getMainTable().getRow(selectedRows[i]);
			String id=row.getCell("id").getValue().toString();
			boolean hasClosed=((Boolean)row.getCell("hasClosed").getValue()).booleanValue();
			if(id==null||!hasClosed){
				MsgBox.showWarning(getRes("billhasUnClosed"));
				SysUtil.abort();
			}
			IObjectPK pk=new ObjectUuidPK(BOSUuid.read(id));
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("amount");
			selector.add("entrys.amount");
			PayRequestBillInfo billInfo=(PayRequestBillInfo)getBizInterface().getValue(pk, selector);
			
			BigDecimal amount=FDCHelper.toBigDecimal(billInfo.getAmount());
			BigDecimal paidAmount=FDCHelper.ZERO;
			PayRequestBillEntryInfo entryInfo;
			for(Iterator iter=billInfo.getEntrys().iterator();iter.hasNext();){
				entryInfo=(PayRequestBillEntryInfo)iter.next();
				paidAmount=paidAmount.add(entryInfo.getAmount());
			}
			
			// 无文本合同允许录入负数，允许多次付款，所以做比较时，改用绝对值比较，不影响正数合同的逻辑
			// added by Owen_wen 2011-05-23 R110130-022
			if (amount.abs().compareTo(paidAmount.abs()) <= 0) {
				MsgBox.showWarning(getRes("billCantClosed"));
				SysUtil.abort();
			}
		}
		
		PayRequestBillInfo billInfo=new PayRequestBillInfo();
		for(int i=0;i<selectedRows.length;i++){
			IRow row=getMainTable().getRow(selectedRows[i]);
			String id=row.getCell("id").getValue().toString();
			billInfo.setId(BOSUuid.read(id));
			billInfo.setHasClosed(false);
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("hasClosed");
			getBizInterface().updatePartial(billInfo, selector);
//			actionClose.setVisible(true);
//			actionClose.setEnabled(true);
//			actionUnClose.setVisible(false);
//			actionUnClose.setEnabled(false);
			row.getCell("hasClosed").setValue(Boolean.FALSE);
		}
		MsgBox.showInfo(getRes("unCloseSuccess"));


	}
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return PayRequestBillEditUI.class.getName();
	}

	protected void execQuery() {
		super.execQuery();
		auditMap.clear();
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"originalAmount","amount"});
//		FDCClientHelper.initTable(tblMain);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PayRequestBillFactory.getRemoteInstance();
	}

	/****
	 * 给commonFilterPanel加自定义的监听事件
	 * 
	 * @param panel
	 */
	public void addKDLisener(final CommonFilterPanel panel){
		if(panel==null || panel.getKdtTable()==null){
    		return ;
    	}
		/***
    	 * 更改下拉框中的数据
    	 */
        panel.getKdtTable().addKDTEditListener(new KDTEditListener(){

			public void editStarting(KDTEditEvent e) {
				// TODO 自动生成方法存根
				int ri = e.getRowIndex();
				 if (e.getColIndex() == 3){
					 if(panel==null || panel.getKdtTable()==null || panel.getKdtTable().getCell(ri, 3)==null ||
							 panel.getKdtTable().getCell(ri, 3).getEditor() == null){
	                		return ;
					 }
					 if(panel.getKdtTable().getCell(ri, 3).getEditor().getComponent() instanceof KDComboBox){
						 KDComboBox comBox = (KDComboBox) panel.getKdtTable().getCell(ri, 3).getEditor().getComponent();
						 if(comBox != null){
							 for(int i=comBox.getItemCount()-1;i>1;i--){
								 if(comBox.getItemAt(i)!=null && comBox.getItemAt(i) instanceof FDCBillStateEnum){
									 FDCBillStateEnum state = (FDCBillStateEnum)comBox.getItemAt(i);
									 if(state.equals(FDCBillStateEnum.VISA)||
											 state.equals(FDCBillStateEnum.ANNOUNCE)||
											 state.equals(FDCBillStateEnum.CANCEL)){
										 comBox.removeItemAt(i);
									 }
								 }
							 }
						 }
					 }
					 
				 }
			}

			public void editStarted(KDTEditEvent e) {
				// TODO 自动生成方法存根
				
			}

			public void editValueChanged(KDTEditEvent e) {
				// TODO 自动生成方法存根
				
			}

			public void editStopping(KDTEditEvent e) {
				// TODO 自动生成方法存根
				
			}

			public void editStopped(KDTEditEvent e) {
				// TODO 自动生成方法存根
				
			}

			public void editCanceled(KDTEditEvent e) {
				// TODO 自动生成方法存根
				
			}
			
		});
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		try {
			commonQueryDialog.init();
			commonQueryDialog.getCommonQueryParam().setDirty(false);
		} catch (UIException e) {
			handUIExceptionAndAbort(e);
		}
		addKDLisener(commonQueryDialog.getCommonFilterPanel());
		
		
		
/*		
		IPromptBoxFactory factory=new DefaultPromptBoxFactory(){
    		public KDPromptBox create(String queryName,
    				EntityObjectInfo entity, String propertyName)
    		{
    			// TODO Auto-generated method stub
    			return super.create(queryName, entity, propertyName);
    		}
    		public KDPromptBox create(String queryName, QueryInfo mainQuery,
    				String queryFieldName)
    		{
    			final KDBizPromptBox f7 = (KDBizPromptBox)super.create(queryName, mainQuery, queryFieldName);
    			if(queryName.equalsIgnoreCase("com.kingdee.eas.fdc.contract.app.ContractF7Query")){
					f7.addSelectorListener(new SelectorListener(){
						public void willShow(SelectorEvent e)
						{
		    				f7.getQueryAgent().resetRuntimeEntityView();
							EntityViewInfo view=new EntityViewInfo();
							FilterInfo filter=new FilterInfo();
//							FilterItemCollection filterItems = filter.getFilterItems();
							
//							filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
							view.setFilter(filter);
//							f7.setEntityViewInfo(null);
							f7.setEntityViewInfo(view);
							
						}
					});

    			}
				return f7;
    		}
    	};
		
    	commonQueryDialog.setPromptBoxFactory(factory);
*/
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new PayRequestFullFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		if(getUIContext().get("MyFilter") instanceof FilterInfo){
			return false;
		}else
			return true;
	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}

	public void onLoad() throws Exception {
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();
		FDCClientHelper.initTableListener(tblMain,this);
	
		FDCClientHelper.addSqlMenu(this,this.menuEdit);
		
		if(auditMap==null){
			auditMap=new HashMap();
		}
		
		/**
		 * 奥园要求在付款申请单序时簿和付款申请单查询界面添加一列 "是否存在附件"
		 * 如果存在附件就填充到界面表格中  by Cassiel_peng
		 */
		if(attachMap==null){
			attachMap=new HashMap();
		}
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){

			public void afterDataFill(KDTDataRequestEvent e) {
				if(attachMap==null){
					attachMap=new HashMap();
				}
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					IRow row = tblMain.getRow(i);
					String idkey = row.getCell("id").getValue().toString();
					if(attachMap.containsKey(idkey)){
							row.getCell("hasAttachment").setValue(Boolean.TRUE);
					}else{
						row.getCell("hasAttachment").setValue(Boolean.FALSE);
					}
					if(auditMap.containsKey(idkey)){
						//Add by zhiyuan_tang 2010/07/29 只在单据是审批或审批中状态时才给审批人和审批日期赋值
						if ("已审批".equals(row.getCell("state").getValue().toString()) || "审批中".equals(row.getCell("state").getValue().toString())) {
							MultiApproveInfo info = (MultiApproveInfo)auditMap.get(idkey);
							row.getCell("auditor.name").setValue(info.getCreator().getName());
							row.getCell("auditDate").setValue(info.getCreateTime());
						}
					}
				}
			}
		});
		
		this.proLongNameMap=FDCHelper.createTreeDataMap(CurProjectFactory.getRemoteInstance(),"name","\\");
		super.onLoad();
		this.actionAddNew.setEnabled(false);
		this.actionAddNew.setVisible(false);
		//this.actionEdit.setEnabled(false);
		//this.actionEdit.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionRemove.setVisible(false);
//		this.actionLocate.setEnabled(false);
//		this.actionLocate.setVisible(false);
		//this.actionCreateTo.setEnabled(false);
		//this.actionCreateTo.setVisible(false);
		//this.actionTraceDown.setEnabled(false);
		//this.actionTraceDown.setVisible(false);
		//new update by renliang 2010-5-26
		this.actionCreateTo.setEnabled(true);
		this.actionCreateTo.setVisible(true);
		this.actionTraceDown.setEnabled(true);
		this.actionTraceDown.setVisible(true);
		this.actionTraceUp.setEnabled(true);
		this.actionTraceUp.setVisible(true);
		this.actionEdit.setEnabled(true);
		this.actionEdit.setVisible(true);
		
		//this.actionTraceUp.setEnabled(false);
		///this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setEnabled(false);
		this.actionCopyTo.setVisible(false);

		this.actionMultiapprove.setEnabled(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setEnabled(false);
		this.actionNextPerson.setVisible(false);
		actionClose.setEnabled(true);
		actionUnClose.setEnabled(true);
		
		menuEdit.setVisible(true);
		menuEdit.setEnabled(true);
		this.menuItemEdit.setEnabled(true);
		this.menuItemEdit.setVisible(true);
		
		this.menuBiz.setEnabled(true);
		this.menuBiz.setVisible(true);
		
		this.actionAuditResult.setEnabled(true);
		this.actionAuditResult.setVisible(true);
		
		
		//new add by renliang 2010-5-26
		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);
		
		FDCClientHelper.initTable(tblMain);
		if (null != tblMain.getColumn("contractId")) {
			tblMain.getColumn("contractId").getStyleAttributes().setHided(true);
		}
	}
	/**
	 * 该付款申请单是否存在附件  by Cassiel_peng
	 */
	private Map attachMap=new HashMap();
	//最新审批人 审批时间 by cassiel_peng 2010-05-26
	private Map auditMap = new HashMap();
	public void onGetRowSet(IRowSet rowSet) {
		try {
//			final BOSObjectType type = new ContractWithoutTextInfo().getBOSType();
			
			Set attachIds=new HashSet();
			rowSet.beforeFirst();
			while (rowSet.next()) {
				//奥园要求在付款申请单序时簿和付款申请单查询界面添加一列 "是否存在附件"  by Cassiel_peng
				String boID=rowSet.getString("id");
				attachIds.add(boID);
				
				String displayName="";
				String id=rowSet.getString("curProject.id");
				String orgName=rowSet.getString("orgUnit.name");
				if(orgName!=null){
					displayName+=orgName;
				}
				String proName = (String) this.proLongNameMap.get(id);
				if(proName!=null){
					displayName+="\\"+proName;
				}
				rowSet.updateString("curProject.name",displayName);
				
				//判断来源
				String contractId = rowSet.getString("contractId");
				if(PayReqUtils.isContractBill(contractId)){
					rowSet.updateString("source","合同");
				}else{
					rowSet.updateString("source","无文本");
				}
				
				
				/*			因关联的无文本合同删除后会出错故
				 * 
				 * 			改变PayRequestBillQuery绑定contractNumber及contractName到查询
				*/
/*				String contractId=rowSet.getString("contractId");
				BOSObjectType type = BOSUuid.read(contractId).getType();
				if(type.equals(new ContractBillInfo().getBOSType())){
					ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
					rowSet.updateString("contractNumber",info.getNumber());
					rowSet.updateString("contractName",info.getName());
				}else{
					ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
					rowSet.updateString("contractNumber",info.getNumber());
					rowSet.updateString("contractName",info.getName());
				}*/
//				//删除无文本合同内的保证,提交状态下的申请单
//				String contractId=rowSet.getString("contractId");
//				if(contractId!=null&&BOSUuid.read(contractId).getType().equals(type)){
//					String state=rowSet.getString("state");
//					if(FDCBillStateEnum.SAVED.getAlias().equals(state)||FDCBillStateEnum.SUBMITTED.getAlias().equals(state)){
//						rowSet.deleteRow();
//					}
//				}
			}
			if (attachIds.size() > 0) {
				attachMap = PayReqTableHelper.handleAttachment(attachIds);
				auditMap = PayReqTableHelper.handleAuditPersonTime(attachIds);
			}
			
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}finally{
			try {
				rowSet.beforeFirst();
			} catch (SQLException e) {
				handUIExceptionAndAbort(e);
			}
		}
		super.onGetRowSet(rowSet);
	}
	protected String getEditUIModal() {
		// TODO 自动生成方法存根
		return UIFactoryName.NEWTAB;
	}
    protected boolean isIgnoreCUFilter() {
        return true;
    }
	private String getRes(String resName)
	{
		return PayReqUtils.getRes(resName);
	}
	protected void initWorkButton()
	{
		super.initWorkButton();
        actionClose.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_close"));
        actionUnClose.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_fclose"));   
	}
	
	protected void beforeExcutQuery(EntityViewInfo ev)
	{
		if(getUIContext().get("MyFilter") instanceof FilterInfo){
			FilterInfo myFilter=(FilterInfo)getUIContext().get("MyFilter");
			ev.setFilter(myFilter);
//			isNeedDefaultFilter=false;
			actionQuery.setVisible(false);
			actionAttachment.setVisible(false);
			actionPrint.setVisible(false);
			actionPrintPreview.setVisible(false);
			actionWorkFlowG.setVisible(false);
			menuWorkFlow.setVisible(false);
			return;
		}
		super.beforeExcutQuery(ev);
		
	}
	
	public void onShow() throws Exception {
		super.onShow();
/*		this.actionRemove.setEnabled(false);
		menuItemRemove.setEnabled(false);
		btnRemove.setEnabled(false);
		this.actionRemove.setVisible(false);
		ActionMap actionMap2 = getActionMap();
		Object[] allKeys = actionMap2.allKeys();
		for(int i=0;i<allKeys.length;i++){
			if(actionMap2.get(allKeys[i]).equals(actionRemove)){
				actionMap2.remove(allKeys[i]);
			}
		}*/
//		getActionMap().get("EASRemove").setEnabled(false);
//		getActionMap().remove("EASRemove");
//		getActionMap().get("EASRemove").setEnabled(false);
	}
	
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	return;
    }
    
	/**
     * 设置是否显示合计行
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return true;
    }
    
    /**
	 * 新增加审批方法by renliang 2010-5-26
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {

		// 检查单据状态
		
		 //super.actionAudit_actionPerformed(e);
		 
		checkBillState(new String[] { getStateForAudit() },
				"selectRightRowForAudit");

		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		if (idList != null && idList.size() > 0) {
			IPayRequestBill payRequestBill = (IPayRequestBill) getBizInterface();
			payRequestBill.audit(idList);
			// super.actionAudit_actionPerformed(e);

			showOprtOKMsgAndRefresh();
		} else {
			MsgBox.showWarning(this, "请先选中行！");
			SysUtil.abort();
		}

	}

	/**
	 * 新增加关联生成方法 by renliang 2010-5-26
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		
		//单据状态的check
		checkBeforeCreateTo();
		super.actionCreateTo_actionPerformed(e);
		
	}
	
	/**
     * 
     * @return
     */
    protected KDTable getBillListTable() {

		return this.tblMain;
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
    
    /**
     * 关联生成功能状态的检查
     */
	private void checkBeforeCreateTo() {
		
		checkSelected(getBillListTable());

		// 状态是否符合转换
		boolean flag = false;
		// 已经生成过付款单
		boolean hasBotped = false;
		String number = "";

		KDTable table = getBillListTable();
		ArrayList blocks = table.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		while (iter.hasNext()) {
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; !flag && rowIndex <= bottom; rowIndex++) {
				ICell cell = table.getRow(rowIndex).getCell(getKeyFieldName());

				// 记录选中的分录ID
				if (cell.getValue() != null) {
					try {
						SelectorItemCollection selector = new SelectorItemCollection();
						selector.add("state");
						selector.add("number");
						selector.add("hasClosed");

						String id = cell.getValue().toString();
						PayRequestBillInfo info = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(id)), selector);
						if (!info.getState().equals(FDCBillStateEnum.AUDITTED) || info.isHasClosed()) {
							flag = true;
						}

					} catch (Exception e) {
						handUIException(e);
						flag = true;
					}
				} else {
					flag = true;
				}
			}

			if (flag || hasBotped)
				break;

		}

		if (flag) {
			MsgBox.showWarning(this, PayReqUtils.getRes("canntCreatTo"));
			SysUtil.abort();
		}

		if (hasBotped) {
			MsgBox.showWarning(this, number + "已经生成过付款单");
			SysUtil.abort();
		}
	}

	/**
	 * 新增加下翻方法 by renliang 2010-5-26
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		String id = this.getSelectedKeyValue();
		filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID", id));

		Map uiContxt = new HashMap();
		uiContxt.put(UIContext.OWNER, this);
		PaymentFullListUI.showUI(view, uiContxt);

	}

	/**
	 * 
	 * 描述：提示操作成功
	 * 
	 * @author:renliang
	 * @date 2010-5-19
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		//FDCClientUtils.showOprtOK(this);
		MsgBox.showWarning(this, "操作成功,付款申请单已关闭！");
		refreshList();
		SysUtil.abort();
	}



	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}

	/**
	 * 单据状态的检查
	 * @param states
	 * @param res
	 * @throws Exception
	 */
	protected void checkBillState(String[] states, String res) throws Exception {

		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		if (idList == null) {
			MsgBox.showWarning(this, "请选中行");
			abort();
			return;
		}

		Set idSet = ContractClientUtils.listToSet(idList);
		Set stateSet = FDCHelper.getSetByArray(states);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getBizInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());

			if (!stateSet.contains(element
					.getString(getBillStatePropertyName()))) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}

	/**
	 * 
	 * 描述：单据状态属性名称，基类提供缺省实现
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	protected String getBillStatePropertyName() {
		return "state";
	}
	
	/**
	 * 启用修改功能
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit();
		super.actionEdit_actionPerformed(e);
	}
	
	 /**
     * 修改前状态的检查
     * @throws renliang
     * @date 2010-5-26
     */
    private void checkBeforeEdit() throws Exception{
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		if(idList==null){
			MsgBox.showWarning(this, "请选中行");
			abort();
			return ;
		}
		
		if(idList.size()>1){
			MsgBox.showWarning(this, "您选择了多行记录，请重新选择！");
			abort();
			return ;
		}
	
	   String id = idList.get(0).toString();
	   PayRequestBillInfo info =PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
	   
	   if(!info.getState().equals(FDCBillStateEnum.SAVED) && !info.getState().equals(FDCBillStateEnum.SUBMITTED)){
		   MsgBox.showWarning(this, "您当前选择的单据的状态不适合修改操作！");
			abort();
	   }
	   
    }
    
    protected String[] getLocateNames() {
    	 String[] locateNames = new String[12];
         locateNames[0] = "bookedDate";
         locateNames[1] = "number";
         locateNames[2] = "contractNumber";
         locateNames[3] = "contractName";
         locateNames[4] = "supplier.name";
         locateNames[5] = "payDate";
         locateNames[6] = "originalAmount";
         locateNames[7] = "amount";
         locateNames[8] = "creator.name";
         locateNames[9] = "createTime";
         locateNames[10] = "auditor.name";
         locateNames[11] = "auditDate";
         return locateNames;
    }
	
}