/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractChangeBill;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractUtil;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * ����:���ǩ֤���б����
 * @author liupd  date:2006-10-13 <p>
 * @version EAS5.1.3
 */
public class ContractChangeBillListUI extends AbstractContractChangeBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractChangeBillListUI.class);
    
    /*
	 * ״̬�е�������
	 */
	private static final int STATE_COLUMN_INDEX = 3;
    
    private KDTSortManager sortManager;
    
    protected Map initParam = new HashMap();
    // ��ͬ������������ϴ�����
    private boolean canUploadForAudited = false;
    
    /**
     * output class constructor
     */
    public ContractChangeBillListUI() throws Exception
    {
        super();
    }
    protected void initTable()
    {
    	super.initTable();
		FDCHelper.formatTableNumber(tblChangeList, "amount");
		FDCHelper.formatTableNumber(tblChangeList, "settleAmount");
		FDCHelper.formatTableDate(tblChangeList, "settleTimed");
		FDCHelper.formatTableDate(tblChangeList, "conductTime");
    }
    private boolean generAfterAudit() {
    	boolean generAfterAudit = false;
    	try {
			generAfterAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
    	return generAfterAudit;
	}
	protected Set getContractBillStateSet() {
		//�ݴ�ĺ�ͬҲ���Ա��
		Set set = super.getContractBillStateSet();
		//set.add(FDCBillStateEnum.SAVED_VALUE);
		//set.add(FDCBillStateEnum.SUBMITTED_VALUE);
		return set;
	}
	
    /**
     * ��д�˿��ٶ�λ�е��ֶ�����by renliang at 2010-5-12
     */
   protected String[] getLocateNames() {
        String[] locateNames = new String[5];
        locateNames[0] = "number";
        locateNames[1] = "contractName";
        locateNames[2] = "partB.name";
        locateNames[3] = "contractType.name";
        locateNames[4] = "signDate";
        return locateNames;
	}
  
    /**
     * 
     * ����������ѡ��ĺ�ͬ��ʾ�����б�
     * @param e
     * @throws BOSException
     * @author:liupd
     * ����ʱ�䣺2006-8-2 <p>
     */
	protected void displayBillByContract(EntityViewInfo view) throws BOSException {
	
		//�����ԭ��������
		view.getSorter().clear();
		
		//��¼ view����״̬�����ã�
		getBillListTable().getColumn(STATE_COLUMN_INDEX).getKDTColumn().setUserObject(view);
		
		//״̬����
		SorterItemInfo stateSorter = new SorterItemInfo("state");
		stateSorter.setSortType(SortType.ASCEND);
		view.getSorter().add(stateSorter);

		//ҵ�����ڽ���
		SorterItemInfo bizDateSorter = new SorterItemInfo("bookedDate");
		bizDateSorter.setSortType(SortType.DESCEND);
		view.getSorter().add(bizDateSorter);
		
		queryChangeBillFillTable(view);

	}

	/**
	 * ��������ѯ������������
	 * @param view
	 * @throws BOSException
	 * @Author��jian_cao
	 * @CreateTime��2012-11-15
	 */
	private void queryChangeBillFillTable(EntityViewInfo view) throws BOSException {
		//Ԥ�����ۼ�
		BigDecimal localAmtAll = FDCHelper.ZERO;
		//�������ۼ�
		BigDecimal settleAmountAll = FDCHelper.ZERO;
    	ContractChangeBillCollection contractChangeBillCollection = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);
    	for (Iterator iter = contractChangeBillCollection.iterator(); iter.hasNext();) {
			ContractChangeBillInfo element = (ContractChangeBillInfo) iter.next();
			IRow row = getBillListTable().addRow(); 
			
			row.getCell("bookedDate").setValue(element.getBookedDate());
			row.getCell("period").setValue(element.getPeriod()); 
			
			row.getCell(ContractChangeBillContants.COL_ID).setValue(element.getId().toString());
			row.getCell(ContractChangeBillContants.COL_STATE).setValue(element.getState());
			
			//��ʱ�������һ��    by Cassiel_peng  2009-8-20
			row.getCell(ContractChangeBillContants.COL_AFTERSIGN_STATE).setValue(element.getForSettAfterSign());
			
			if(element.getChangeAudit()!=null&&element.getChangeAudit().getNumber()!=null){
				row.getCell(ContractChangeBillContants.COL_CHANGEAUDIT).setValue(element.getChangeAudit().getNumber());
			}
			row.getCell(ContractChangeBillContants.COL_CHANGETYPE).setValue(element.getChangeType());
			row.getCell(ContractChangeBillContants.COL_NUMBER).setValue(element.getNumber());
			row.getCell(ContractChangeBillContants.COL_BILLNAME).setValue(element.getName());
			row.getCell(ContractChangeBillContants.COL_AMOUNT).setValue(element.getAmount());
			localAmtAll = FDCHelper.add(localAmtAll, element.getAmount());
			
			if(element.getChangeAudit()!=null){
				EntityViewInfo v = new EntityViewInfo();
				FilterInfo filter  = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("contractChange.id", element.getId().toString(), CompareType.INCLUDE));
				v.setFilter(filter);
				v.getSelector().add(new SelectorItemInfo("reckonor.name"));
				ChangeSupplierEntryCollection info = ChangeSupplierEntryFactory.getRemoteInstance().getChangeSupplierEntryCollection(v);
				if(info.iterator().hasNext()){
					ChangeSupplierEntryInfo test = (ChangeSupplierEntryInfo)info.iterator().next();
					if(test!=null&&test.getReckonor()!=null&&test.getReckonor().getName()!=null)
						row.getCell(ContractChangeBillContants.COL_BUDGETPERSON).setValue(test.getReckonor().getName());
				}
			}
			if(element.getChangeAudit()!=null)
				row.getCell(ContractChangeBillContants.COL_CONDUCTTIME).setValue(element.getChangeAudit().getCreateTime());
			else
				row.getCell(ContractChangeBillContants.COL_CONDUCTTIME).setValue(element.getCreateTime());
			row.getCell(ContractChangeBillContants.COL_CONDUCTDEPT).setValue(element.getConductDept());
			
			row.getCell(ContractChangeBillContants.COL_SETTLEAMOUNT).setValue(element.getBalanceAmount());
			settleAmountAll = FDCHelper.add(settleAmountAll, element.getBalanceAmount());
			
			row.getCell(ContractChangeBillContants.COL_HASSETTLED).setValue(Boolean.valueOf(element.isHasSettled()));
			if(element.isHasSettled()){
				row.getCell(ContractChangeBillContants.COL_SETTLEDTIME).setValue(element.getSettleTime());
			}
			String auditor = element.getAuditor() == null ? "" : element.getAuditor().getName();
			row.getCell(ContractChangeBillContants.COL_AUDITOR).setValue(auditor);
			if(element.getMainSupp()!=null)
				row.getCell(ContractChangeBillContants.COL_MAINSUPP).setValue(element.getMainSupp().getName());
			if(element.getChangeAudit()!=null&&element.getChangeAudit().getId()!=null){
				row.getCell(ContractChangeBillContants.COL_CHANGEAUDIT_ID).setValue(element.getChangeAudit().getId().toString());
			}
		}
    	
    	//��������
        this.setTableSort(); 
        
        //�ϼ�������
        IRow footRow = FDCTableHelper.generateFootRow(getBillListTable());
		footRow.getCell(ContractChangeBillContants.COL_AMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		footRow.getCell(ContractChangeBillContants.COL_AMOUNT).setValue(localAmtAll);
		footRow.getCell(ContractChangeBillContants.COL_SETTLEAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		footRow.getCell(ContractChangeBillContants.COL_SETTLEAMOUNT).setValue(settleAmountAll);
	}
 
	/**
	 * ���������ñ������
	 * @Author��jian_cao
	 * @CreateTime��2012-11-6
	 */
	private void setTableSort() {
		// ���ñ���Զ�������
		this.getBillListTable().setColumnMoveable(true);
		// �����Զ�������в����Զ�����
		for (int i = 0, size = this.getBillListTable().getColumnCount(); i < size; i++) {
			this.getBillListTable().getColumn(i).setSortable(true);
		}
	}
	
	
	/**
	 * 
	 * ���������ɻ�ȡ���ݵ�Selector
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-8-3 <p>
	 */
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("state");
		selectors.add("number");
		selectors.add("name");
		selectors.add("bookedDate");
		selectors.add("amount");
		selectors.add("originalAmount");
		selectors.add("createTime");
		selectors.add("balanceAmount");
		selectors.add("hasSettled");
		selectors.add("settleTime");
		selectors.add("forSettAfterSign");
		selectors.add("settAuditAmt");
		selectors.add("settAuditExRate");
		
		selectors.add("changeType.name");
		selectors.add("budgetPerson.name");
		selectors.add("conductDept.name");
		selectors.add("auditor.name");
		selectors.add("changeReason.name");
		selectors.add("mainSupp.name");
		selectors.add("changeAudit.createTime");
		selectors.add("changeAudit.number");
		selectors.add("changeAudit.suppEntry.reckonor.name");
		
		selectors.add("period.number");
		selectors.add("period.periodNumber");
		selectors.add("period.periodYear");
				
		return selectors;
	}

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(getMainTable());

    	String keyValue = getSelectedKeyValue(getMainTable());
    	
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasSettled");
		
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectStringPK(keyValue), selector);
		
		if(contractBillInfo != null && contractBillInfo.isHasSettled()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("hasSettNotNewChange"));
			SysUtil.abort();
		}
    	if(ChangeAuditUtil.checkHasSettlementBill(keyValue)){
    		MsgBox.showWarning(this, "�ú�ͬ���ս��㵥���������У��������������");
			SysUtil.abort();
    	}
        super.actionAddNew_actionPerformed(e);
    }

    public void actionAttachment_actionPerformed(ActionEvent e)
    		throws Exception {
    	checkSelected(tblChangeList);
    	boolean isEdit=false;
    	AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    	String boID = this.getSelectedKeyValue();
    	if (boID == null)
    	{
    		return;
    	}
    	if(getBillStatePropertyName()!=null){
    		int rowIdx=tblChangeList.getSelectManager().getActiveRowIndex();
    		ICell cell =tblChangeList.getCell(rowIdx, getBillStatePropertyName());
    		Object obj=cell.getValue();
    		isEdit=ContractClientUtils.canUploadAttaForAudited(obj,canUploadForAudited);
    	}
    	acm.showAttachmentListUIByBoID(boID,this,isEdit);
    }
    
	/**
	 * 
	 * ����������Զ�̽ӿڣ��������ʵ�֣�
	 * @return
	 * @throws BOSException
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return ContractChangeBillFactory.getRemoteInstance();
	}
    
	protected ICoreBase getBizInterface() throws Exception {
	
		return getRemoteInterface();
	}
	/**
	 * 
	 * ���������ͨ�����������ʵ�֣����÷������˴���˱�־�ķ������ɣ�
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected void audit(List ids) throws Exception {
		ContractChangeBillFactory.getRemoteInstance().audit(ids);
	}

	/**
	 * 
	 * ����������ˣ��������ʵ�֣����÷������˴���˱�־�ķ������ɣ�
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected void unAudit(List ids) throws Exception {
		
		for (int i = 0; i < ids.size(); i++) {
			String settId=(String) ids.get(i);
			FilterInfo filterSett = new FilterInfo();
			filterSett.getFilterItems().add(
					new FilterItemInfo("contractChange.id", settId));
			filterSett.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			boolean hasSettleSplit = false;
			if (ConChangeSplitFactory.getRemoteInstance().exists(filterSett)
					|| ConChangeNoCostSplitFactory.getRemoteInstance()
					.exists(filterSett)) {
				hasSettleSplit = true;
			}
			if (hasSettleSplit) {
				MsgBox.showWarning("������Ѿ����,���ܷ�����!");
				SysUtil.abort();
				return;
			}
		}
		
		ContractChangeBillFactory.getRemoteInstance().unAudit(ids);
	}
		
	/**
	 * 
	 * ��������ȡ��ǰ���ݵ�Table���������ʵ�֣�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-8-2 <p>
	 */
	protected KDTable getBillListTable() {
		return tblChangeList;
	}
	
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractChangeBillEditUI.class
				.getName();
	}
	
	protected boolean checkBeforeRemove() throws Exception {
		if(!super.checkBeforeRemove()){
			return false;
		}		
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());		
		Set idSet = ContractClientUtils.listToSet(idList);		
		EntityViewInfo viewAudit = new EntityViewInfo();
		FilterInfo filterAudit = new FilterInfo();
		filterAudit.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		viewAudit.setFilter(filterAudit);
		viewAudit.getSelector().add("id");
		viewAudit.getSelector().add("changeAudit");
		viewAudit.getSelector().add(getBillStatePropertyName());
		ContractChangeBillCollection collAudit = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(viewAudit);
		for (Iterator iter = collAudit.iterator(); iter.hasNext();) {
			ContractChangeBillInfo element = (ContractChangeBillInfo) iter.next();
			if(isTransformVisa()){
				continue;
			}
			if(element.getChangeAudit()!=null){
				// MsgBox.showWarning(this,
				// ChangeAuditUtil.getRes("hasChangeAudit"));
				// SysUtil.abort();
			}
		}	
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractChange", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = ConChangeSplitFactory.getRemoteInstance().getCoreBillBaseCollection(view);
		Iterator iter = coll.iterator(); 
		if(iter.hasNext()) {	
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
			SysUtil.abort();
		}
		
		return true;
	}
	
	/**
	 * �·�
	 */
	public void actionDisPatch_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if(FDCUtils.isRunningWorkflow(getSelectedKeyValue(tblChangeList))){
			  MsgBox.showWarning("���ڹ����������У���ǰ�����ִ���˲�ƥ��");
			  SysUtil.abort();
		 }
		checkBeforeDisPatch();
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		IContractChangeBill bill = (IContractChangeBill) getBizInterface();		
    	if(selectedIdValues!=null){
    		bill.disPatch(FDCHelper.CollectionToArrayPK(selectedIdValues));
    		showOprtOKMsgAndRefresh();
    	}
		super.actionDisPatch_actionPerformed(e);
	}
	
	private void checkBeforeDisPatch() throws Exception{
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(),
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		view.getSelector().add("changeAudit");
		view.getSelector().add("changeAudit.changeState");
		IContractChangeBill bill = (IContractChangeBill) getBizInterface();	
		ContractChangeBillCollection coll = bill.getContractChangeBillCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ContractChangeBillInfo element = (ContractChangeBillInfo) iter.next();
			// ��鵥���Ƿ��ڹ�������
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());
			if(element.getChangeAudit()!=null){
				if((element.getChangeAudit().getChangeState()!=null)
						&&(!((element.getChangeAudit().getChangeState().equals(ChangeBillStateEnum.Audit))
								||(element.getChangeAudit().getChangeState().equals(ChangeBillStateEnum.Announce))
								||(element.getChangeAudit().getChangeState().equals(ChangeBillStateEnum.Visa))))){
					MsgBox.showWarning(this, ChangeAuditUtil.getRes("reSelect"));
					SysUtil.abort();
				} else if (!element.getState().equals(FDCBillStateEnum.AUDITTED)) {
					MsgBox.showWarning(this, ChangeAuditUtil.getRes("reSelect"));
					abort();
				} else
					continue;
			}
			else{
				if (!element.getState().equals(FDCBillStateEnum.AUDITTED)) {
					MsgBox.showWarning(this, ChangeAuditUtil.getRes("reSelect"));
					abort();
				}
			}
		}
	}
	public void onShow() throws Exception{
		super.onShow();
		getBillListTable().setColumnMoveable(true);

		//���в������������� 
		//�Ƽ��ͻ�ʹ������ģʽ
//		actionAddNew.setEnabled(true);
//		actionAddNew.setVisible(true);
//		menuItemAddNew.setEnabled(true);
//		menuItemAddNew.setVisible(true);
		//��ʱ������,��������������ɱ����ָ�,���Ա༭
		actionEdit.setEnabled(true);
		actionEdit.setVisible(true);
		actionVisaBatch.setEnabled(true);
		btnWorkFlowG.setVisible(true);
		btnWorkFlowG.setEnabled(true);
		
		menuItemMultiapprove.setVisible(true);
		menuItemMultiapprove.setEnabled(true);
		menuItemWorkFlowG.setVisible(true);
		menuItemWorkFlowG.setEnabled(true);
		menuItemNextPerson.setVisible(true);
		menuItemNextPerson.setEnabled(true);
		menuItemAuditResult.setVisible(true);
		menuItemAuditResult.setEnabled(true);
		menuWorkFlow.setVisible(true);
		menuWorkFlow.setEnabled(true);
		
		actionViewChangeAudtiAttachment.setVisible(true);
		actionViewChangeAudtiAttachment.setEnabled(true);
		btnViewChangeAuditAttachment.setEnabled(true);
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		actionDisPatch.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_emend"));
		actionVisa.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_move"));
		actionSettlement.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_assetchange"));
		actionVisaBatch.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_terminateinstance"));
		actionDisPatch.setEnabled(true);
		actionVisa.setEnabled(true);
		
		actionUnVisa.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_cancelcollate"));
		actionUnDispatch.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_cancelcase"));
		actionUnDispatch.setEnabled(true);
		actionUnVisa.setEnabled(true);
		
		// by Cassiel_peng  2009-9-20
		actionViewChangeAudtiAttachment.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_affixmanage"));
	}
	
	protected void checkBillState(String[] states, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("changeAudit");
		ContractChangeBillCollection coll = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ContractChangeBillInfo element = (ContractChangeBillInfo) iter.next();			
//			��鵥���Ƿ��ڹ�������
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			
//			if (!element.getString(getBillStatePropertyName()).equals(states)) {
			if(isTransformVisa()){
				continue;
			}
//			if (element.getChangeAudit()!=null) {
//				MsgBox.showWarning(this, ChangeAuditUtil.getRes("noUnAudit"));
//				abort();
//			}

		}
		super.checkBillState(states, res);
	}
	public void actionViewChangeAudtiAttachment_actionPerformed(ActionEvent e)
	throws Exception {
		// TODO Auto-generated method stub
		checkSelected(this.tblChangeList);
		String contractChangeBillID= getSelectedKeyValue(this.tblChangeList);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("changeAudit.id");
		ContractChangeBillInfo contractChange=ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChangeBillID)), selector);
		if(contractChange!=null){
			ChangeAuditBillInfo changeAuditBill=contractChange.getChangeAudit();
			if(changeAuditBill!=null){
				AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
				String boId=changeAuditBill.getId().toString();
				acm.showAttachmentListUIByBoID(boId,this,false);
			}else{
				MsgBox.showWarning("����ѡ��ĵ���û�й����ı��ǩ֤���룬���ܲ鿴������");
				return;
			}
		}else{
			return;
		}
	}
	/**
	 * ǩ֤
	 */
	public void actionVisa_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		//checkBeforeVisa();
		
	   if(FDCUtils.isRunningWorkflow(getSelectedKeyValue(tblChangeList))){
		   MsgBox.showWarning("���ڹ����������У���ǰ�����ִ���˲�ƥ��");
		   SysUtil.abort();
	   }
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		
		IContractChangeBill bill = (IContractChangeBill) getBizInterface();
		Set idSet = ContractClientUtils.listToSet(selectedIdValues);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		view.getSelector().add("number");
		ContractChangeBillCollection coll = bill.getContractChangeBillCollection(view);
		
		//����ǩ֤
		if(selectedIdValues.size()>1){
			List idList = new ArrayList();
			Iterator iter = coll.iterator(); 
			while(iter.hasNext()){
				ContractChangeBillInfo element = (ContractChangeBillInfo) iter.next();
				if(element.getState()!=null){
					if(element.getState().equals(FDCBillStateEnum.ANNOUNCE)){
						idList.add(element.getId().toString());
					}
				}
			}
			
			if(idList.size()==0){
				MsgBox.showWarning(this,ChangeAuditUtil.getRes("noVisa"));
				SysUtil.abort();
			}
			
			UIContext uiContext = new UIContext(this); 
			uiContext.put(UIContext.IDLIST,idList);
			IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
					ContractChangeVisaBatchUI.class.getName(),	uiContext, null , OprtState.EDIT);       
			uiWin.show();
			
		}else{
			//����ǩ֤
			Iterator iter = coll.iterator(); 
			if(iter.hasNext()){
				ContractChangeBillInfo element = (ContractChangeBillInfo) iter.next();
				if(element.getState()!=null){
					if(!isDispatch()){
						//����ģʽ,����Ҫ�·�
						if(!element.getState().equals(FDCBillStateEnum.AUDITTED)){
							MsgBox.showWarning(this,ChangeAuditUtil.getRes("noVisa"));
							SysUtil.abort();
						}
					}else{
						//����
						if(!element.getState().equals(FDCBillStateEnum.ANNOUNCE)){
							MsgBox.showWarning(this,ChangeAuditUtil.getRes("noVisa"));
							SysUtil.abort();
						}
					}
				}else{
					MsgBox.showWarning(this,ChangeAuditUtil.getRes("noVisa"));
					SysUtil.abort();
				}
				UIContext uiContext = new UIContext(this); 
				uiContext.put(UIContext.ID, element.getId());
				IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
						ContractChangeVisaUI.class.getName(),	uiContext, null , OprtState.EDIT);       
				uiWin.show();
			}
		}
		refreshList();
	}	
	
	/**
	 * ����ǩ֤
	 */
    public void actionVisaBatch_actionPerformed(ActionEvent e) throws Exception
    {
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		
		Map uiContext = this.getUIContext();
		Object userObject2 = getProjSelectedTreeNode().getUserObject();
		BOSUuid projId = ((CurProjectInfo) userObject2).getId();
		uiContext.put("projectId", projId);
		
		
		IContractChangeBill bill = (IContractChangeBill) getBizInterface();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projId.toString()));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		ContractChangeBillCollection coll = bill.getContractChangeBillCollection(view);
		
		//����ǩ֤
		if(coll.size()>0){
			List idList = new ArrayList();
			Iterator iter = coll.iterator(); 
			while(iter.hasNext()){
				ContractChangeBillInfo element = (ContractChangeBillInfo) iter.next();
				if(element.getState()!=null){
					if (element.getState().equals(FDCBillStateEnum.AUDITTED) || element.getState().equals(FDCBillStateEnum.ANNOUNCE)) {
						idList.add(element.getId().toString());
					}
				}
			}
			
			if(idList.size()==0){
				MsgBox.showWarning(this,ChangeAuditUtil.getRes("noVisa"));
				SysUtil.abort();
			}
			
			uiContext.put(UIContext.IDLIST,idList);
			IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
					ContractChangeVisaBatchUI.class.getName(),	uiContext, null , OprtState.EDIT);       
			uiWin.show();
			
			refreshList();			
		}else{
			MsgBox.showWarning(this,"û����Ҫǩ֤�ĵ���");			
		}
    }
    
	private void checkBeforeVisa() throws Exception{
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		if(selectedIdValues.size()>1){
			MsgBox.showWarning(this,ChangeAuditUtil.getRes("mustSingle"));
			SysUtil.abort();
		}
	}
	
	/**
	 * ����
	 */
	public void actionSettlement_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBeforeVisa();
		if(FDCUtils.isRunningWorkflow(getSelectedKeyValue(tblChangeList))){
			   MsgBox.showWarning("���ڹ����������У���ǰ�����ִ���˲�ƥ��");
			   SysUtil.abort();
		 }
		//����Ϊ��ǩ֤
		int activeRowIndex = getBillListTable().getSelectManager().getActiveRowIndex();
		ICell cell = getBillListTable().getCell(activeRowIndex, "state");
		if(cell.getValue()!=null&&!cell.getValue().equals(FDCBillStateEnum.VISA)){
			MsgBox.showWarning(this,"�������֮ǰ������ǩ֤");
			SysUtil.abort();
		}
		//��ͬ�ѽ������ܽ��б������   see R121203-0392
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("id", getSelectedKeyValue(tblMain));
		filter.appendFilterItem("hasSettled",Boolean.TRUE);
		if(ContractBillFactory.getRemoteInstance().exists(filter)){
			MsgBox.showWarning(this, "��ͬ�ѽ��㣬�����������㣡");
			SysUtil.abort();
		}
		
		UIContext uiContext = new UIContext(this); 
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
				ContractChangeSettUI.class.getName(),	uiContext, null , OprtState.EDIT);       
		uiWin.show();
		ContractChangeSettUI ui=(ContractChangeSettUI)uiWin.getUIObject();
		if(ui.isOk()){
			refreshList();
			super.actionSettlement_actionPerformed(e);
		}
	}	
	
	protected boolean isFootVisible() {
		return false;
	}
	
	//����ɾ���Ŀ�ݼ�,�����Լ�������
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
//		return;
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");

			super.actionAudit_actionPerformed(e);
			ProgrammingContractUtil.amountChange(idList);
	
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
//		return;
		super.actionUnAudit_actionPerformed(e);
	}
	protected void fetchInitData() throws Exception {
		Map param = new HashMap();
		Map initData = ((IFDCBill)getRemoteInterface()).fetchInitData(param);
		
		//��õ�ǰ��֯
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		
		Map paramMap = FDCUtils.getDefaultFDCParam(null, orgUnit.getId().toString());
		if(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL)!=null){
			canUploadForAudited = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL).toString()).booleanValue();
		}
		
	}
	

    /**
     * output actionUnVisa_actionPerformed method
     */
    public void actionUnVisa_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
		 if(FDCUtils.isRunningWorkflow(getSelectedKeyValue(tblChangeList))){
			   MsgBox.showWarning("���ڹ����������У���ǰ�����ִ���˲�ƥ��");
			   SysUtil.abort();
		   }
		//����Ϊ��ǩ֤
		int activeRowIndex = getBillListTable().getSelectManager().getActiveRowIndex();
		ICell cell = getBillListTable().getCell(activeRowIndex, "state");
		if(cell.getValue()!=null&&!cell.getValue().equals(FDCBillStateEnum.VISA)){
			MsgBox.showWarning(this,"��û��ǩ֤,����ȡ��ǩ֤");
			SysUtil.abort();
		}
		
		String id  =  getSelectedKeyValue();
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("hasSettled");
		ContractChangeBillInfo billInfo = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(id), sic);
		if(billInfo.isHasSettled()) {
			MsgBox.showWarning(this, "�ѽ��㣬����ȡ��ǩ֤");
			SysUtil.abort();
		}
		
		((IContractChangeBill)getRemoteInterface()).unVisa(new ObjectUuidPK(id));
		
		showOprtOKMsgAndRefresh();
    }

    /**
     * output actionUnDispatch_actionPerformed method
     */
    public void actionUnDispatch_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
		if(FDCUtils.isRunningWorkflow(getSelectedKeyValue(tblChangeList))){
			  MsgBox.showWarning("���ڹ����������У���ǰ�����ִ���˲�ƥ��");
			  SysUtil.abort();
		 }
		//����Ϊ���·�
		int activeRowIndex = getBillListTable().getSelectManager().getActiveRowIndex();
		ICell cell = getBillListTable().getCell(activeRowIndex, "state");
		if(cell.getValue()!=null&&!cell.getValue().equals(FDCBillStateEnum.ANNOUNCE)){
			MsgBox.showWarning(this,"��û���·�,����ȡ���·�");
			SysUtil.abort();
		}
		

		String id  =  getSelectedKeyValue();
		((IContractChangeBill)getRemoteInterface()).unDispatch(new ObjectUuidPK(id));
		
		showOprtOKMsgAndRefresh();
    }
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	super.prepareUIContext(uiContext, e);
    	getUIContext().put("isFromTempEvalConChangeListUI", Boolean.FALSE);
    }

    public void onLoad() throws Exception {
    	
    	super.onLoad();
    	//ken_liu..err code,,
//    	FilterInfo filter = this.mainQuery.getFilter();
//    	filter.getFilterItems().add(new FilterItemInfo( "state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTLIKE ) );
    	
    	fetchInitData();
    	initParam = FDCUtils.getDefaultFDCParam(null,null);	
		if(isTransformVisa()){
			String visaConfirm = ChangeAuditUtil.getRes("visaConfirm");
			String unVisaConfirm = ChangeAuditUtil.getRes("unVisaConfirm");
			btnVisa.setText(visaConfirm);
			menuItemVisa.setText(visaConfirm);
			btnVisa.setToolTipText(visaConfirm);
			btnUnVisa.setText(unVisaConfirm);
			btnUnVisa.setToolTipText(unVisaConfirm);
			menuItemUnVisa.setText(unVisaConfirm);
			menuItemSettlement.setText(ChangeAuditUtil.getRes("visaSettlement"));
			btnSettlement.setText(ChangeAuditUtil.getRes("visaSettlement"));
			btnSettlement.setToolTipText(ChangeAuditUtil.getRes("visaSettlement"));
			
			actionVisaBatch.setVisible(false);
			actionVisaBatch.setEnabled(false);
		}
		if(!isDispatch()){
			actionAddNew.setEnabled(true);
			actionAddNew.setVisible(true);
			menuItemAddNew.setEnabled(true);
			menuItemAddNew.setVisible(true);
			
			btnRemove.setEnabled(true);
			actionRemove.setEnabled(true);
			actionRemove.setVisible(true);
			menuItemRemove.setEnabled(true);
			menuItemRemove.setVisible(true);
			
			btnAudit.setVisible(true);
			btnAudit.setEnabled(true);
			btnUnAudit.setVisible(true);
			btnUnAudit.setEnabled(true);
			
			menuItemAudit.setVisible(true);
			menuItemAudit.setEnabled(true);
			menuItemUnAudit.setVisible(true);
			menuItemUnAudit.setEnabled(true);
			
			btnDisPatch.setVisible(false);
			btnDisPatch.setEnabled(false);
			btnUnDispatch.setVisible(false);
			btnUnDispatch.setEnabled(false);
			menuItemDisPatch.setVisible(false);
			menuItemDisPatch.setEnabled(false);
			menuItemUnDispatch.setVisible(false);
			menuItemUnDispatch.setEnabled(false);
		}
		//������ɱ��ǩ֤���������Զ����ɵ�ָ��������ͷ�������ť���ɼ�  by cassiel_peng 2010-04-14
		if(!generAfterAudit()){
			this.actionAudit.setVisible(false);
			this.actionUnAudit.setVisible(false);
		}
		
		tblChangeList.addKDTSelectListener(new KDTSelectListener() {
			public void tableSelectChanged(KDTSelectEvent e) {
				IRow row = getBillListTable().getRow(e.getSelectBlock().getBeginRow());
				String id = row.getCell("id").getValue().toString();
				try {
					ContractChangeBillInfo info = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(
							new ObjectStringPK(id));
					actionTraceUp.setEnabled(info.getChangeAudit() != null);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});
		
		
		sortManager = new KDTSortManager(tblChangeList) {
			public void sort(int colIndex, int sortType) {
				super.sort(colIndex, sortType);
			}
		};
		
		//����ӱ�����¼�
		tblChangeList.addKDTMouseListener(new KDTMouseListener() {

			SortType viewSort = SortType.DESCEND;
			int sortType = ISortManager.SORT_ASCEND;
			public void tableClicked(KDTMouseEvent e) {
				
				//����������״̬�е���ͷ
				if (e.getType() == KDTStyleConstants.HEAD_ROW && e.getClickCount() == 2 && (e.getRowIndex() == 0)
						&& (e.getColIndex() == STATE_COLUMN_INDEX)) {
					sortManager.setSortAuto(false);
					Object o = getBillListTable().getColumn(e.getColIndex()).getKDTColumn().getUserObject();
					if (o != null) {
						EntityViewInfo view = (EntityViewInfo) o;
						view.getSorter().clear();
						//״̬����
						SorterItemInfo stateSorter = new SorterItemInfo("state");
						viewSort = (viewSort == SortType.ASCEND ? SortType.DESCEND : SortType.ASCEND);
						sortType = (sortType == ISortManager.SORT_ASCEND ? ISortManager.SORT_DESCEND : ISortManager.SORT_ASCEND);
						stateSorter.setSortType(viewSort);
						view.getSorter().add(stateSorter);

						//ҵ�����ڽ���
						SorterItemInfo bizDateSorter = new SorterItemInfo("bookedDate");
						bizDateSorter.setSortType(SortType.DESCEND);
						view.getSorter().add(bizDateSorter);
						try {
							//���ñ������ͼ��
							sortManager.sort(e.getColIndex(), sortType);
							getBillListTable().removeRows(false);
							queryChangeBillFillTable(view);
							
						} catch (BOSException ex) {
							logger.error(ex);
							handUIExceptionAndAbort(ex);
						}
					}
				} else {
					//�����������Զ�����
					sortManager.setSortAuto(true);
				}
			}
			
		});
		
		actionTraceUp.setVisible(true);
		actionTraceUp.setEnabled(true);
		actionQuery.setVisible(true);
		actionQuery.setEnabled(true);

    }
    
    
    
    private boolean isDispatch(){
    	if(initParam.get(FDCConstants.FDC_PARAM_ALLOWDISPATCH)!=null){
			return Boolean.valueOf(initParam.get(FDCConstants.FDC_PARAM_ALLOWDISPATCH).toString()).booleanValue();
		}
    	//Ĭ��true
    	return true;
    }
    private boolean isTransformVisa(){
    	if(initParam.get(FDCConstants.FDC_PARAM_ALLOWDISPATCH)!=null){
			return Boolean.valueOf(initParam.get(FDCConstants.FDC_PARAM_AUTOCHANGETOPROJECTVISA).toString()).booleanValue();
		}
    	return false;
    }
    
    /**
	 * �ϲ�
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractChangeBillListUI#actionTraceUp_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int[] selectedRows = KDTableUtil.getSelectedRows(getBillListTable());
		if (selectedRows.length > 0) {
			for (int i = 0; i < selectedRows.length; i++) {
				IRow row = getBillListTable().getRow(selectedRows[i]);
				String id = row.getCell("id").getValue().toString();
				ContractChangeBillInfo info = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(
						new ObjectStringPK(id));
				if (info.getChangeAudit() != null) {
					String sourceBillID = info.getChangeAudit().getId().toString();
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.ID, sourceBillID);
					IUIWindow uiWindow = null;
					uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(ChangeAuditEditUI.class.getName(), uiContext, null,
							OprtState.VIEW);
					uiWindow.show();
				} else {
					MsgBox.showWarning(this, "Դ���ݲ�����!");
				}
			}
		}
	}
	
	/*  public void loadFields()
	  {
		  FilterInfo filter = this.mainQuery.getFilter();
		  filter.getFilterItems().add(new FilterItemInfo( "state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTLIKE ) );
		  
		  super.loadFields();
	  }*/
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			setIsAmtWithoutCostFilter(viewInfo.getFilter());
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
}
