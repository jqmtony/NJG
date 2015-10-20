/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.ResultSetMetaData;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.commonquery.client.IPromptBoxFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditBillCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.CopySupplierEntryCollection;
import com.kingdee.eas.fdc.contract.CopySupplierEntryFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IChangeAuditBill;
import com.kingdee.eas.fdc.contract.SupplierContentEntryCollection;
import com.kingdee.eas.fdc.contract.SupplierContentEntryFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.ListUIController;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 设计变更审批表
 * output class name
 */
public class DesignChangeAuditListUI extends AbstractDesignChangeAuditListUI
{
    private static final Logger logger = CoreUIObject.getLogger(DesignChangeAuditListUI.class);

	private ChangeAuditListFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private FilterInfo mainFilterInfo = null;
    
    private int selectInvit = 0;

	public DesignChangeAuditListUI() throws Exception
    {
        super();
        actionRemove.setBindWorkFlow(false);
        actionAheadDisPatch.setBindWorkFlow(false);
    }
    

    /**
     * 重写了快速定位中的字段属性by renliang at 2010-5-12
     */
   protected String[] getLocateNames() {
//	   return new String[] { "number", "name", "changeReason.name", "auditType.name", "specialtyType.name", "contractName", "partB",
//				"auditMoney", };
	   return new String[] { "number", "name" };
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return ChangeAuditBillFactory.getRemoteInstance();
	}
	//如果审批单生成的指令单已经被拆分过，则不能删除审批单   by Cassiel_peng
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
//		SelectorItemCollection itemCollection=new SelectorItemCollection();
//		itemCollection.add("suppEntry");
//		itemCollection.add("suppEntry.contractChange.contractBill.isCoseSplit");
//		ChangeAuditBillInfo	info=ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(getSelectedKeyValue()),itemCollection);
//    		if(info!=null){
//    			boolean isCostSplit =false;
//    			for(int i=0;i<info.getSuppEntry().size();i++){
//    				ContractChangeBillInfo entryInfo = info.getSuppEntry().get(i).getContractChange();
//    				if(entryInfo!=null&&entryInfo.getContractBill()!=null&&entryInfo.getContractBill().isIsCoseSplit()){
//    					isCostSplit = FDCSplitClientHelper.isBillSplited(entryInfo.getId().toString(), "t_con_conchangesplit", "FContractChangeID");
//    					if(isCostSplit){
//    						break;//只要有一条指令单被拆分过就需要提示
//    					}
//    				}
//    				if(entryInfo!=null&&entryInfo.getContractBill()!=null&&!entryInfo.getContractBill().isIsCoseSplit()){//非成本类拆分
//    					isCostSplit=FDCSplitClientHelper.isBillSplited(entryInfo.getId().toString(), "T_CON_ConChangeNoCostSplit", "FContractChangeID");
//    					if(isCostSplit){
//    						break;//只要有一条指令单被拆分过就需要提示
//    					}
//    				}
//    			}//不管是成本类拆分还是非成本类拆分，只要该指令单被拆分过就得给客户提示
//    			if(isCostSplit){
//    				MsgBox.showWarning("此变更签证申请生成的指令单已经拆分，不能删除！");
//    				SysUtil.abort();
//    			}
//    		}
    	checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}
	
    
	protected void audit(List ids) throws Exception {
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();		
    	if(ids!=null){
    		bill.audit(ids);
    	}
	}

	protected void unAudit(List ids) throws Exception {
		
//		for (int i = 0; i < ids.size(); i++) {
//			String settId=(String) ids.get(i);
//			FilterInfo filterSett = new FilterInfo();
//			filterSett.getFilterItems().add(
//					new FilterItemInfo("contractChange.id", settId));
//			filterSett.getFilterItems().add(
//					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
//							CompareType.NOTEQUALS));
//			boolean hasSettleSplit = false;
//			if (ConChangeSplitFactory.getRemoteInstance().exists(filterSett)
//					|| ConChangeNoCostSplitFactory.getRemoteInstance()
//					.exists(filterSett)) {
//				hasSettleSplit = true;
//			}
//			if (hasSettleSplit) {
//				MsgBox.showWarning("变更单已经拆分,不能反审批!");
//				SysUtil.abort();
//				return;
//			}
//		}
		
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();		
    	if(ids!=null){
    		bill.unAudit(ids);
    	}
	}
	
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.DesignChangeAuditEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ChangeAuditBillFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ChangeAuditBillInfo objectValue = new com.kingdee.eas.fdc.contract.ChangeAuditBillInfo();
		return objectValue;
	}
	/**
	 * 描述：冻结表列
	 */
	protected void freezeTableColumn() {
		/*FDCHelper.formatTableNumber(getMainTable(), "amount");	
		// 合同名称
		int name_col_index = getMainTable().getColumn("billName")
				.getColumnIndex();
		getMainTable().getViewManager().setFreezeView(-1, name_col_index+1);*/
	}

	public void onShow() throws Exception{
		super.onShow();
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);
//		actionWorkFlowG.setVisible(false);
//		menuWorkFlow.setVisible(false);
		actionAheadDisPatch.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_emend"));
		actionAheadDisPatch.setEnabled(true);
		actionDisPatch.setVisible(false);
		actionQuery.setVisible(true);
	}

	protected void checkBillState(String state, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("changeState");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());

			if (!element.getString(getBillStatePropertyName()).equals(state)) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}
	
	protected String getBillStatePropertyName() {
		return "changeState";
	}
	
	protected String getStateForAudit() {
		return ChangeBillStateEnum.SUBMIT_VALUE;
	}
	
	protected String getStateForUnAudit() {
		return ChangeBillStateEnum.AUDIT_VALUE;
	}

	protected String[] getBillStateForEditOrRemove() {
		return new String[] { ChangeBillStateEnum.SAVED_VALUE,
				ChangeBillStateEnum.SUBMIT_VALUE,ChangeBillStateEnum.REGISTER_VALUE };
	}

	public void actionDisPatch_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		super.actionDisPatch_actionPerformed(e);
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();		
    	if(selectedIdValues!=null){
    		bill.disPatch(FDCHelper.list2Set(selectedIdValues));
    		showOprtOKMsgAndRefresh();
    	}
	}
	
	/**
	 * 检查变更签证申请对应的合同拆分是否已经审批，并且根据参数是否启用，给出信息提示。
	 * @param idList
	 * @throws BOSException
	 * @throws EASBizException
	 * @author owen_wen 2010-12-10
	 */
	private void checkContractSplitIsAuditAndShowMsg(List idList) throws BOSException, EASBizException{
		for (int i = 0; i < idList.size(); i++){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter  = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", idList.get(i)));
			view.setFilter(filter);
			view.getSelector().add(new SelectorItemInfo("contractBill.id"));
			view.getSelector().add(new SelectorItemInfo("contractBill.isCoseSplit"));
			
			ChangeSupplierEntryCollection cse = ChangeSupplierEntryFactory.getRemoteInstance().getChangeSupplierEntryCollection(view);
			
			for (int j = 0; j < cse.size(); j++){
				boolean conSplitIsAudited = FDCSplitClientHelper.checkContractSplitIsAudited(cse.get(j).getContractBill().getId().toString(), 
						cse.get(j).getContractBill().isIsCoseSplit());
				if (conSplitIsAudited) // 合同拆分未审批
					FDCSplitClientHelper.getParamValueAndShowMsg("conSplitNotAudit", this);
			}
		}
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		EntityViewInfo colView = (EntityViewInfo) mainQuery.clone();;
    	mainFilterInfo = colView.getFilter();
    	
    	int selectIndex = this.kDTable1.getSelectManager().getActiveRowIndex();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filInfo = new FilterInfo();
		if(selectIndex!=-1)
		{
			String orderId = this.kDTable1.getCell(selectIndex, "id").getValue().toString();
			filInfo.getFilterItems().add(new FilterItemInfo("sourceBillId",orderId));
		}
		else
		{
			filInfo.getFilterItems().add(new FilterItemInfo("id","999"));
		}
    	view.setFilter(filInfo);
    	
    	IQueryExecutor exec = super.getQueryExecutor(queryPK, view);
    	exec.option().isAutoTranslateBoolean = true;
        exec.option().isAutoTranslateEnum = true;
    	return exec;
    }
	
	
//	审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// 获取用户选择的块
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		
		// 启用了参数 “合同变更、合同结算的拆分是否自动引用合同拆分的成本耳目、拆分比例。” 才继续判断
		if (FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), FDCConstants.FDC_PARAM_IMPORTCONSPLIT))
			this.checkContractSplitIsAuditAndShowMsg(idList);
		
		boolean hasMutex = false;
		try {

			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");

			super.actionAudit_actionPerformed(e);

		} catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
	}
	
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
		getFilterUI();
       super.actionQuery_actionPerformed(e);
    }
	
	protected void checkBeforeRemove() throws Exception {
		super.checkBeforeRemove();
	}
	
	protected boolean confirmRemove(){
		 List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				 getKeyFieldName());
		 Set idSet = ContractClientUtils.listToSet(idList);
		 
		 EntityViewInfo view = new EntityViewInfo();
		 FilterInfo filter = new FilterInfo();
		 filter.getFilterItems().add(
				 new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		 view.setFilter(filter);
		 view.getSelector().add("id");
		 view.getSelector().add(getBillStatePropertyName());
		 CoreBaseCollection coll;
		 try {
			 coll = getRemoteInterface().getCollection(view);
			 
			 for (Iterator iter = coll.iterator(); iter.hasNext();) {
				 CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
				 String billState = element.getString(getBillStatePropertyName());
				 if(billState.equals(ChangeBillStateEnum.SUBMIT_VALUE)){
					 int isYes = MsgBox.showConfirm2(this, ChangeAuditUtil.getRes("hasChange"));
					 if (MsgBox.isYes(isYes)) {	
						 return true;
					 }else{
						 return false;
					 }
				 }else{
					 return super.confirmRemove();
				 }
			 }
		 } catch (BOSException e) {
			 handUIExceptionAndAbort(e);
		 }
		 return super.confirmRemove();
	 }

	public void actionAheadDisPatch_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBeforAhead();
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();
		Set idSet = ContractClientUtils.listToSet(selectedIdValues);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("changeState");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			UIContext uiContext = new UIContext(this); 
			uiContext.put(UIContext.ID, element.getId());
			IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
					ChangeAuditAheadEditUI.class.getName(),	uiContext, null , null);       
			uiWin.show();
		}
		refreshList();
		super.actionAheadDisPatch_actionPerformed(e);
	}
	
	private void checkBeforAhead() throws Exception{
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();
		Set idSet = ContractClientUtils.listToSet(selectedIdValues);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("changeState");
		view.getSelector().add("entrys.*");
		view.getSelector().add("suppEntry.mainSupp.*");
		view.getSelector().add("suppEntry.contractBill.*");
		view.getSelector().add("suppEntry.*");
		view.getSelector().add("suppEntry.entry.*");
		view.getSelector().add("suppEntry.copySupp.*");
		view.getSelector().add("suppEntry.reckonor.id");
		ChangeAuditBillCollection coll = bill.getChangeAuditBillCollection(view);
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ChangeAuditBillInfo element = (ChangeAuditBillInfo) iter.next();
			if(element.getChangeState().equals(ChangeBillStateEnum.Saved)
					||element.getChangeState().equals(ChangeBillStateEnum.Register)
					||element.getChangeState().equals(ChangeBillStateEnum.Submit)
					||element.getChangeState().equals(ChangeBillStateEnum.Auditting)){
				if(element.getEntrys().size()==0||element.getSuppEntry().size()==0){
					MsgBox.showWarning(this, ChangeAuditUtil.getRes("notAhead"));
					SysUtil.abort();
				}else{
					ChangeSupplierEntryCollection collSupp = element.getSuppEntry();
					int count = collSupp.size();
					for(int i=0;i<count;i++){
						ChangeSupplierEntryInfo info = collSupp.get(i);
						if(info.getMainSupp()==null){
							MsgBox.showWarning(this, ChangeAuditUtil.getRes("partAhead"));
							SysUtil.abort();
						}
						if(info.getContractBill()==null){
							MsgBox.showWarning(this, ChangeAuditUtil.getRes("partAhead"));
							SysUtil.abort();
						}
						if(info.getCostAmount()==null||info.getCostAmount().equals(FDCHelper.ZERO)){
							MsgBox.showWarning(this, ChangeAuditUtil.getRes("costAhead"));
							SysUtil.abort();
						}
						if(info.getReckonor()==null){
							MsgBox.showWarning(this, ChangeAuditUtil.getRes("costAhead"));
							SysUtil.abort();
						}
						if(info.getId()!=null){
							EntityViewInfo vi = new EntityViewInfo();
							FilterInfo fi = new FilterInfo();
							FilterItemCollection it = fi.getFilterItems();	
							if(info.getId()!=null)
								it.add(new FilterItemInfo("parent.id", info.getId().toString(),CompareType.EQUALS));
							vi.setFilter(fi);
							vi.getSelector().add("content.*");
							SupplierContentEntryCollection c = SupplierContentEntryFactory.getRemoteInstance().getSupplierContentEntryCollection(vi);
					        int entryNum = c.size();
					        if(entryNum==0){
								MsgBox.showWarning(this, ChangeAuditUtil.getRes("partAhead"));
								SysUtil.abort();
					        }
					        
					        EntityViewInfo vie = new EntityViewInfo();
							FilterInfo fil = new FilterInfo();
							FilterItemCollection itl = fil.getFilterItems();
							if(info.getId()!=null)
								itl.add(new FilterItemInfo("parent.id", info.getId().toString(),CompareType.EQUALS));
							vie.setFilter(fil);
							vie.getSelector().add("copySupp.*");
							CopySupplierEntryCollection collCopy = CopySupplierEntryFactory.getRemoteInstance().getCopySupplierEntryCollection(vie);							
					        int copyNum = collCopy.size();
					        if(copyNum==0){
								MsgBox.showWarning(this, ChangeAuditUtil.getRes("partAhead"));
								SysUtil.abort();
					        }
						}
					}
				}
			}
			else{
				MsgBox.showWarning(this, ChangeAuditUtil.getRes("notAhead"));
				SysUtil.abort();
			}
		}
	}

	public void onLoad() throws Exception {		
		super.onLoad();
		actionQuery.setEnabled(true);
		actionQuery.setVisible(true);
		boolean isDispatch = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_ALLOWDISPATCH);
		if(!isDispatch){//中渝模式，不需要下发
			actionAheadDisPatch.setVisible(false);
			actionAheadDisPatch.setEnabled(false);
		}
		
		actionSetRespite.setVisible(false);
		actionCancelRespite.setVisible(false);
		actionAheadDisPatch.setVisible(false);
		
		KDTSortManager sortManager = new KDTSortManager(kDTable1){
			public void sort(int colIndex) {
				kDTable1.getMergeManager().removeAllMergeBlock();
				super.sort(colIndex);
				mergerTable(kDTable1,new String[] { "id" }, getRequestMergeColumnKeys());
			}
		};   
		sortManager.setSortAuto(true);   
		sortManager.setClickCount(1);   
		for(int i = 0; i<kDTable1.getColumnCount();i++){  
		    this.kDTable1.getColumn(i).setSortable(true);   
		}  
	}	
	
	
    public static void mergerTable(KDTable table, String coloum[], String mergeColoum[])
    {
        int merger = 0;
        for(int i = 0; i < table.getRowCount(); i++)
            if(i > 0)
            {
                boolean isMerge = true;
                for(int j = 0; j < coloum.length; j++)
                {
                    Object curRow = table.getRow(i).getCell(coloum[j]).getValue();
                    Object lastRow = table.getRow(i - 1).getCell(coloum[j]).getValue();
                    if(getString(curRow).equals("") || getString(lastRow).equals("") || !getString(curRow).equals(getString(lastRow)))
                    {
                        isMerge = false;
                        merger = i;
                    }
                }

                if(isMerge)
                {
                    for(int j = 0; j < mergeColoum.length; j++)
                        table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));

                }
            }

    }
    
    private static String getString(Object value)
    {
        if(value == null)
            return "";
        if(value != null && value.toString().trim().equals(""))
            return "";
        else
            return value.toString();
    }
	
	protected void updateButtonStatus() {}	
	
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			
			int selectIndex = this.kDTable1.getSelectManager().getActiveRowIndex();
			if(selectIndex==-1)
			{
				MsgBox.showWarning("请先选择设计变更申请单！");
				SysUtil.abort();
			}
			String changeAuditReqId = this.kDTable1.getCell(selectIndex, "id").getValue().toString();
			
			FilterInfo filInfo = new FilterInfo();
			filInfo.getFilterItems().add(new FilterItemInfo("sourceBillId",changeAuditReqId));
			try {
				if(ChangeAuditBillFactory.getRemoteInstance().exists(filInfo)){
					MsgBox.showWarning("已经存在审批表，不允许新增！");
					SysUtil.abort();
				}
			} catch (EASBizException e1) {
				e1.printStackTrace();
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
			uiContext.put("changeAuditReqId", changeAuditReqId);
		}
		
		selectInvit = this.kDTable1.getSelectManager().getActiveRowIndex();
    }
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		actionRemove.setEnabled(true);
		IRow row;
		if (this.tblMain.getSelectManager().getActiveRowIndex()!= -1) {
			if((tblMain.getSelectManager().getBlocks().size() > 1)
				||(e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() > 0)){
				actionSetRespite.setEnabled(true);
				actionCancelRespite.setEnabled(true);
			}else{
				row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
//				if(Boolean.TRUE.equals(row.getCell("isRespite").getValue())){
//					actionSetRespite.setEnabled(false);
//					actionCancelRespite.setEnabled(true);
//				}else{
//					actionSetRespite.setEnabled(true);
//					actionCancelRespite.setEnabled(false);
//				}
				if(row.getCell("changeState")!=null && 
						ChangeBillStateEnum.AUDIT_VALUE.equalsIgnoreCase(
								((BizEnumValueInfo)row.getCell("changeState").getValue()).getValue().toString())
					&& tblMain.getSelectManager().getBlocks().size() == 1){
					actionSetRespite.setEnabled(false);
					actionCancelRespite.setEnabled(true);
				}
				
				isEditByState(row);
			}
		}
	}
	
	/**
	 * 描述：根据状态判断修改按钮是否灰显
	 * @throws Exception
	 * @Author：jian_cao
	 * @CreateTime：2013-2-19
	 */
	private void isEditByState(IRow row) throws Exception {

		String billState = ((BizEnumValueInfo) row.getCell("changeState").getValue()).getValue().toString();
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if (billState.equals(states[i])) {
				pass = true;
				break;
			}
		}
		this.actionEdit.setEnabled(pass);
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
//		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		// FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
//		super.actionAddNew_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", null);
		prepareUIContext(uiContext, e);
		getUIContext().putAll(uiContext);
		IUIWindow uiWindow = null;
		((ListUIController)super.getUIController()).actionAddNew();
		uiWindow = ((ListUIController)super.getUIController()).getNavigator().getUIWindow();
		Window win = SwingUtilities.getWindowAncestor((Component)uiWindow.getUIObject());
		if(!win.isActive() && (win instanceof JFrame) && ((JFrame)win).getExtendedState() == 1)
			((JFrame)win).setExtendedState(0);
		if(uiWindow != null && isDoRefresh(uiWindow)){
			if(UtilRequest.isPrepare("ActionRefresh", this))
				prepareRefresh(null).callHandler();
			refresh(e);
			setPreSelecteRow();
		}
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		FDCClientHelper.checkAuditor(getSelectedIdValues(), "T_CON_ChangeAuditBill");
		super.actionUnAudit_actionPerformed(e);
	}


	public void actionSetRespite_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(getBillListTable(), getKeyFieldName(),false);
		if(idList.size()!=0&&idList.get(0) != null){
			((IFDCBill)getRemoteInterface()).setRespite(idList, true);
			MsgBox.showWarning("操作成功！已审批状态的单据不会启用暂缓");
			refreshList();
		}
	}
	
	public void actionCancelRespite_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(getBillListTable(), getKeyFieldName(),true);
		if(idList.get(0) != null){
			((IFDCBill)getRemoteInterface()).setRespite(idList, false);
			showOprtOKMsgAndRefresh();
		}
	}
	protected void execQuery() {
		super.execQuery();
//		this.getMainQuery().setFilter(new FilterInfo());
		
		try {
			treeProject_valueChanged(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.kDTable1.getSelectManager().select(selectInvit, selectInvit);
	}
	
	protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getType()==0||e.getRowIndex()==-1||e.getClickCount()!=2)
    		return;
        String billId = UIRuleUtil.getString(this.kDTable1.getCell(e.getRowIndex(), "id").getValue());
        IUIWindow uiWindow = null;
		UIContext context = new UIContext(this);
		context.put("ID",billId);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChangeAuditRequestEditUI.class.getName(), context, null, OprtState.VIEW);
		uiWindow.show();
	}
	
	protected void kDTable1_tableSelectChanged(KDTSelectEvent e)throws Exception {
		super.kDTable1_tableSelectChanged(e);
		tblMain.removeRows();
	}
	
	protected void treeProject_valueChanged(TreeSelectionEvent e)throws Exception {
//		super.treeProject_valueChanged(e);
        this.kDTable1.removeRows();
    	EntityViewInfo view = new EntityViewInfo();
		try {
			FilterInfo filter = getTreeFilter();
			if(view.getFilter()!=null){
				view.getFilter().mergeFilter(filter,"and");
			}else{
				view.setFilter(filter);
			}
			if(mainFilterInfo!=null)
	        {
	    		if(view.getFilter()!=null)
	    		{
	    			view.getFilter().mergeFilter(mainFilterInfo, "and");
	    		}
	    		else
	    		{
	    			view.setFilter(mainFilterInfo);
	    		}
	        }
			filterByBillState(view);
		} catch (Exception e1) {
			// @AbortException
			logger.error(e1.getMessage(), e1); 
			e1.printStackTrace();
		}
    	
    	showQueryDate(this.kDTable1, "com.kingdee.eas.fdc.contract.app.ChangeAuditRequestQuery",view);
    	
    	mergerTable(this.kDTable1, new String[] { "id" }, getRequestMergeColumnKeys());
        if(this.kDTable1.getRowCount() > 0)
        {
        	this.kDTable1.getSelectManager().select(0, 0);
            btnAddNew.setEnabled(true);
        } 
        else
            btnAddNew.setEnabled(false);
	}
	
	private int showQueryDate(KDTable tblMain,String queryName,EntityViewInfo evi) throws Exception
    {
    	tblMain.setEnabled(false);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	IMetaDataPK queryPK = new MetaDataPK(queryName);
    	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(queryPK );
    	exec.option().isAutoIgnoreZero = true;
    	exec.option().isAutoTranslateBoolean = true;
    	exec.option().isAutoTranslateEnum = true; 
        exec.setObjectView(evi);
    	IRowSet rowSet = exec.executeQuery();
    	IRow row = null;
    	tblMain.removeRows();
    	int maxLevel = 0;
        for(int i=0; rowSet.next(); i++)
        {
        	row = tblMain.addRow();
        	ResultSetMetaData o_dbMd = rowSet.getMetaData();
        	int cols = o_dbMd.getColumnCount(); //取得query的字段数
        	Object[] colname = new Object[cols]; //取得query的字段名称
            for (int j = 1; j < cols+1; j++) {
              colname[j-1] = o_dbMd.getColumnName(j);
              if(colname[j-1]!=null && row.getCell(colname[j-1].toString())!=null
            		  && rowSet.getObject(colname[j-1].toString())!=null)
            		  row.getCell(colname[j-1].toString()).setValue(rowSet.getObject(colname[j-1].toString()));
            }
        }
        return maxLevel+1;
    }
	
	// 返回需要融合的列，除了“合同名称”，“合同乙方”，“变更金额”外，其余列需要融合
	public String[] getMergeColumnKeys() {
		return new String[] {"id","curProject.id","period.number","orgUnit.name","number","name","bookedDate","curProject.name","changeState","auditType.name","totalCost","billType","putForwardTime","designChangeAmount","totalChangeAmount","contractAmPro","reworkVisa","reaDesc","creator.name","createTime","auditor.name","auditTime"};
	}
	
	public String[] getRequestMergeColumnKeys() {
		return new String[] {"curProject.id","period.number","orgUnit.name","number","name","bookedDate","curProject.name","changeState","auditType.name","billType","putForwardTime","quality","timeLi","sale","cost","reaDesc","creator.name","createTime","auditor.name","auditTime"};
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		//初始化查询功能
		commonQueryDialog = new CommonQueryDialog();
        if(getUIWindow() == null)
        	commonQueryDialog.setOwner((Component)getUIContext().get("OwnerWindow"));
        else
        	commonQueryDialog.setOwner(this);
        commonQueryDialog.setUiObject(this);
        commonQueryDialog.setParentUIClassName(getMetaDataPK().getFullName());
        commonQueryDialog.setQueryObjectPK(new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ChangeAuditRequestQuery"));
        commonQueryDialog.setTitle(getUITitle() + " - " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Query_Filter"));
        if(orgContextManager != null)
        {
            if(orgContextManager.getOwner() != null && (orgContextManager.getOwner() instanceof CustomerQueryPanel))
                commonQueryDialog.addUserPanel((CustomerQueryPanel)orgContextManager.getOwner());
            orgContextManager.init(getUIContext());
        }
        IPromptBoxFactory promptBoxFactory = getPromptBoxFactory();
        if(promptBoxFactory != null)
            commonQueryDialog.setPromptBoxFactory(promptBoxFactory);
        commonQueryDialog.setMaxReturnCountVisible(true);
        commonQueryDialog.setVisibleTableCols(getQueryShowField());
        
        commonQueryDialog.addUserPanel(this.getFilterUI());
        commonQueryDialog.setWidth(400);
        return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ChangeAuditListFilterUI(this, this.actionOnLoad);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		filterUI.setAuthorizedOrgs(this.authorizedOrgs);
		filterUI.setCompany(null);
		filterUI.setProject(null);
		DefaultKingdeeTreeNode projectNode = this.getProjSelectedTreeNode();
		if (projectNode != null && projectNode.getUserObject() != null && projectNode.getUserObject() instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode.getUserObject();
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				FullOrgUnitInfo company = null;
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					company = ((OrgStructureInfo) projTreeNodeInfo).getUnit();
				} else {
					company = (FullOrgUnitInfo) projTreeNodeInfo;
				}
				filterUI.setCompany(company);
			} else if (projTreeNodeInfo instanceof CurProjectInfo) {
				filterUI.setProject((CurProjectInfo) projTreeNodeInfo);
			}
		}
		return this.filterUI;
	}
}