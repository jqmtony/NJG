/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillReviseCollection;
import com.kingdee.eas.fdc.contract.ContractBillReviseFactory;
import com.kingdee.eas.fdc.contract.ContractBillReviseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ��ͬ�޶� ��ʱ��
 */
public class ContractBillReviseListUI extends AbstractContractBillReviseListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractBillReviseListUI.class);

	/**
	 * �Ƿ�ˢ��MainTable
	 */
	private boolean isNeedRefresh = true;
	
	private static final String SUPPLY = "SUPPLY";
	/**
	 * ״̬�ֶ�����
	 */
	private static final String STATE_FIELD_NAME = "state";

	/**
	 * output class constructor
	 */
	public ContractBillReviseListUI() throws Exception {
		super();
	}
	  
	/**
	 * description		��������
	 * @author			���� <p>
	 * @createDate		2011-8-23<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#audit(java.util.List)
	 */
	protected void audit(List ids) throws Exception {
	    // ֮ǰ���еĺ�ͬ��Ϣ����ͨ��Զ�̷�������ȡ�ģ����ڽ����к�ͬ��Ϣ��Ƕ�뵽�޶��б���Ϣ�У�����׷�鼰����Զ�����ӣ���ʡ��Դ
		// �˴���Ҫ�����ͬIDΪnullʱ�����жϵ��쳣�����������
		//��ѡ����߹�����Ŀ�����ұ�����ĺ�ͬ�б���ѡ������ͬ������ֺ�ͬ�µ��޶���Ϣ��ѡ���޶���ͬ����������ʱ��
		//��ȷ����Ϊѡ���޶���ֱͬ�ӽ����������м䲻�����κβ��������������磺������֮ǰ���ں�ͬ�б����н���ˢ�£�ʹ��ͬ���
		//��ѡ��ĺ�ͬʧȥѡ�񽹵�����������ͻᱨ�жϣ�����ָ���쳣������Ϊ��ȡ����ԭ��ͬID�����ڴ˸����޶���ͬ��ȡԭ��ͬID
		// Added by Adward_huang 2012-8-29  �����Ѿ�����һ�ַ�ʽ,���ǻ���Զ�̷�ʽ����ȡԭ��ͬ��Ϣ�����ǽ���ͬ��ϢǶ�뵽�޶���Ϣ��
		int rowIndex = this.tblContractRev.getSelectManager().getActiveRowIndex();
		ContractBillReviseInfo contractBillReviseInfo = (ContractBillReviseInfo) tblContractRev.getRow(rowIndex).getUserObject();
		ContractBillInfo contractBillInfo = contractBillReviseInfo.getContractBill();
		
		if(contractBillInfo.getState() == FDCBillStateEnum.CANCEL) {
			MsgBox.showWarning(this, "��ͬ����ֹ���޶���������");
			SysUtil.abort();
		}
		
		if(contractBillInfo.isHasSettled()) {
			MsgBox.showWarning(this, "��ͬ�ѽ��㣬�޶���������");
			SysUtil.abort();
		}
		
		ContractBillReviseFactory.getRemoteInstance().audit(ids);

	}
	
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		tblContractRev.refresh();
	}
	
	/**
	 * ֱ�ӷ�����,����д��ͬ,����������߼���
	 * @author ling_peng
	 */
	  protected void unAudit(List ids) throws Exception {
		  
		String conId = getSelectedKeyValue(getMainTable());
			
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select state, hasSettled where id = '" + conId + "'");
			
		if(contractBillInfo.getState() == FDCBillStateEnum.CANCEL) {
				MsgBox.showWarning(this, "��ͬ����ֹ���޶����ܷ�������");
				SysUtil.abort();
		}
			
		if(contractBillInfo.isHasSettled()) {
				MsgBox.showWarning(this, "��ͬ�ѽ��㣬�޶����ܷ�������");
				SysUtil.abort();
		}
		String contractBillId = null;
		ContractClientUtils.checkSplitedForAmtChange(this, contractBillId);
		ContractBillReviseFactory.getRemoteInstance().unAudit(ids);
		
	  }
	
//	����ʱ�������ݻ���
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		��ȡ�û�ѡ��Ŀ�
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			super.actionAudit_actionPerformed(e);
	
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
		this.btnAttachment.setEnabled(true);
	    this.btnAttachment.setVisible(true);
	}
	
	/**
	 * description		�޶��б�TABLE
	 * @author			���� <p>
	 * @createDate		2011-8-23<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#getBillListTable()
	 */
	protected KDTable getBillListTable() {
		return tblContractRev;
	}

	protected ICoreBase getBizInterface() throws Exception {

		return ContractBillReviseFactory.getRemoteInstance();
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return ContractBillReviseFactory.getRemoteInstance();
	}

	protected boolean isFootVisible() {
		return false;
	}

	protected void initTable() {
		super.initTable();
		
		FDCHelper.formatTableNumber(tblContractRev, "amount");
		FDCHelper.formatTableNumber(tblContractRev, "originalAmount");
		FDCHelper.formatTableDateTime(tblContractRev,"revDate");
	}

	protected void freezeBillTableColumn() {

		super.freezeBillTableColumn();
//
//		int name_col_index = getBillListTable().getColumn("name")
//				.getColumnIndex();
//		getBillListTable().getViewManager().setFreezeView(-1,
//				name_col_index + 1);
	}

	/**
	 * 
	 * ����������ѡ��ĺ�ͬ��ʾ�����б�
	 * 
	 * @param e
	 * @throws BOSException
	 * @author:liupd ����ʱ�䣺2006-8-2
	 *               <p>
	 */
	protected void displayBillByContract(EntityViewInfo view) throws BOSException {
		if(null == view){
			return;
		}
		btnAddNew.setEnabled(true);

		//		int pre = getPre(e);
		//���þ���
		//		String oriFormat = FDCClientHelper.getNumberFormat(pre,true);
		//		getBillListTable().getColumn("originalAmount").getStyleAttributes().setNumberFormat(oriFormat);
		//		getBillListTable().getColumn("oldOriginalAmount").getStyleAttributes().setNumberFormat(oriFormat);
		
		ContractBillReviseCollection contractChangeBillCollection = ContractBillReviseFactory.getRemoteInstance()
				.getContractBillReviseCollection(view);
		for (Iterator iter = contractChangeBillCollection.iterator(); iter.hasNext();) {
			ContractBillReviseInfo element = (ContractBillReviseInfo) iter.next();
			//����Ҫ����ʾ��غͱ����
			IRow row = getBillListTable().addRow();
			// ���û�������ϢǶ�뵽�޶���Ϣ�У�����Զ�̷������� Added by Adward_huang 2012-08-29
			row.setUserObject(element);
			row.getCell(ContractBillReviseContants.COL_ID).setValue(element.getId().toString());
			row.getCell(ContractBillReviseContants.COL_STATE).setValue(element.getState());
			row.getCell(ContractBillReviseContants.COL_CONTYPE).setValue(element.getContractType().getName());
			row.getCell(ContractBillReviseContants.COL_NUMBER).setValue(element.getNumber());
			row.getCell(ContractBillReviseContants.COL_NAME).setValue(element.getName());
			row.getCell("oldOriginalAmount").setValue(element.getOriginalAmount());
			row.getCell("oldAmount").setValue(element.getAmount());
			row.getCell(ContractBillReviseContants.COL_AMOUNT).setValue(element.getRevAmount());
			row.getCell(ContractBillReviseContants.COL_LOCALAMOUNT).setValue(element.getRevLocalAmount());

			row.getCell(ContractBillReviseContants.COL_REVDATE).setValue(element.getCreateTime());
			row.getCell(ContractBillReviseContants.COL_REVOR).setValue(element.getCreator().getName());
			row.getCell(ContractBillReviseContants.COL_REVISEREASON).setValue(element.getReviseReason());

			row.getCell(COL_DATE).setValue(element.getBookedDate());
			row.getCell(COL_PERIOD).setValue(element.getPeriod());
			
		}
		
	}

    protected String getBillStatePropertyName() {
    	return "state";
    }
    
	/**
	 * description		���ɲ�ѯ�޶��б����ݵ�����
	 * @author			���� <p>
	 * @createDate		2011-8-23<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent)
	 */
	protected EntityViewInfo genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	
    	String contractId = (String)getMainTable().getCell(top, getKeyFieldName()).getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
    	view.setFilter(filter);
    	view.getSorter().add(new SorterItemInfo("createTime"));
    	
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);				
			}
    	}
    	
		return view;
	}
    
	/**
	 * description		��ȡ�޶��б��ѯ�ֶμ���
	 * @author			���� <p>
	 * @createDate		2011-8-23<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#genBillQuerySelector()
	 */
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("bookedDate");
		selectors.add("state");
		selectors.add("number");
		selectors.add("name");
		
		selectors.add("originalAmount");
		//revLocalAmount
		selectors.add("amount");
		//revAmount
		selectors.add("revAmount");
		//revLocalAmount
		selectors.add("revLocalAmount");
		
		selectors.add("reviseReason");
		selectors.add("createTime");
		
		selectors.add("currency.name");
		selectors.add("period.number");
		selectors.add("contractType.name");
		selectors.add("creator.name");

        // ��ԭ��ͬ��ϢǶ�뵽�޶���Ϣ�У�����Զ�̷������� Added by Adward_huang 2012-08-29
		selectors.add("contractBill.id");
		selectors.add("contractBill.state");
		selectors.add("contractBill.hasSettled");

		return selectors;
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		// Added by Jian_cao 
		IUIWindow win = FDCClientUtils.findUIWindow(this.getEditUIName(), this.getUIContext(), dataObjects, OprtState.ADDNEW);
		if (null != win) {
			win.show();
			//�Ƿ�ˢ��tblMain����Ϊ����ˢ�¡�
			isNeedRefresh = false;
			//�ر��Ѿ��򿪵Ĳ�ֽ��� ���رյ�ʱ����Զ�ˢ��tblMain����ôtblMainѡ����оͻᶪʧ������������������ʶ�����ƣ�
			win.close();
			isNeedRefresh = true;
		}
		
		checkSelected(getMainTable());
		String id = getSelectedKeyValue(getMainTable());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", id));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
		boolean existNotAuditedRecord = ContractBillReviseFactory.getRemoteInstance().exists(filter);
		
		if(existNotAuditedRecord) {
			MsgBox.showWarning(this, "�ú�ͬ����δ��˵��޶���¼�����������޶�");
			SysUtil.abort();
		}
		
		FilterInfo conSetFilter = new FilterInfo();
		conSetFilter.appendFilterItem("id", id);
		conSetFilter.appendFilterItem("hasSettled", Boolean.TRUE);
		boolean hasSettled = ContractBillFactory.getRemoteInstance().exists(conSetFilter);
		if(hasSettled) {
			MsgBox.showWarning(this, "�ú�ͬ�Ѿ����㣬�����޶�");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * description		���غ�ͬ�޶����ݵı༭����
	 * @author			���� <p>
	 * @createDate		2011-8-23<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#getEditUIName()
	 */
	protected String getEditUIName() {
		return ContractBillReviseEditUI.class.getName();
	}
	
	
	public void onShow() throws Exception{
		super.onShow();
		getBillListTable().setColumnMoveable(true);
		FDCClientHelper.setActionEnable(actionAudit, true);
		FDCClientHelper.setActionEnable(actionWorkFlowG, false);
		//��ͬ�޶����Ӹ�������         by Cassiel_peng
		FDCClientHelper.setActionEnable(actionAttachment, true);
		FDCClientHelper.setActionEnable(actionRemove, true);
		
		tblContractRev.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.btnWorkFlowG.setVisible(true);
		this.btnWorkFlowG.setEnabled(true);
		this.menuItemWorkFlowG.setVisible(true);
		this.menuItemWorkFlowG.setEnabled(true);
	}
	
	protected boolean isShowAttachmentAction() {
		return false;
	}
	
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		
		FDCClientHelper.setActionEnable(actionAddNew, true);
		FDCClientHelper.setActionEnable(actionEdit, true);
		FDCClientHelper.setActionEnable(actionLocate, true);
		FDCClientHelper.setActionEnable(actionRefresh, true);
		FDCClientHelper.setActionEnable(actionRemove, true);
		
		menuEdit.setVisible(true);
	}
	

	protected void fetchInitData() throws Exception {
		Map param = new HashMap();
		Map initData = ((IFDCBill)getRemoteInterface()).fetchInitData(param);
		
		//��õ�ǰ��֯
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		
	}
	
	/**
	 * ���ض�λ�ֶεļ���<p>
	 * ����˶�λ�ֶΣ�����Ϊ����ͬ��ţ���ͬ���ƣ���ͬ���ͣ�ǩԼʱ�䣬ǩԼ�ҷ�����ͬԭ�ҽ���λ�ҽ��
	   Modified by Owen_wen 2010-09-06
	 * @author zhiyuan_tang 2010/07/12
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "contractType.name", 
				"signDate", "partB.name", "originalAmount", "amount",};
	}
	
	/**
	 * description		���غ�ͬ�ķ�¼����
	 * @author			����<p>	
	 * @createDate		2011-8-17<p>
	 * @param			billInfo ��ͬ����
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private ContractBillEntryCollection getContractBillEntryCols(ContractBillInfo billInfo){
		ContractBillEntryCollection cols = null;
		if (null != billInfo && null != billInfo.getEntrys()) {
			cols = billInfo.getEntrys();
		}
		
		return cols;
	}
	
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		actionQuery.setEnabled(true);
		actionQuery.setVisible(true);
		//		refreshTblMain(this.getMainQuery());
	}
	protected boolean isOnlyQueryAudited() {
		return true;
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			if (queryPK.toString().indexOf("ContractBillQuery") > -1) {
				//ֻ�к�ͬ������׷�ӹ����������Ƕ�������Ĳ����ͬ�� ���ı���ͬ����Ҫ
				setIsAmtWithoutCostFilter(viewInfo.getFilter());
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	private void refreshTblMain(EntityViewInfo view) throws BOSException, SQLException, EASBizException {

		tblMain.removeRows(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);//ֻ����ѡ����


	}


	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("signDate"));
        sic.add(new SelectorItemInfo("costProperty"));
        sic.add(new SelectorItemInfo("contractPropert"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("hasSettled"));
        sic.add(new SelectorItemInfo("contractSourceId.name"));
        sic.add(new SelectorItemInfo("name"));
//        sic.add(new SelectorItemInfo("exRate"));
//        sic.add(new SelectorItemInfo("isArchived"));
        sic.add(new SelectorItemInfo("originalAmount"));
//        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("mainContractNumber"));
        sic.add(new SelectorItemInfo("attachment"));
        sic.add(new SelectorItemInfo("content"));
//        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("isRespite"));
        sic.add(new SelectorItemInfo("bookedDate"));
        sic.add(new SelectorItemInfo("period"));
//        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("entrys.*"));
        sic.add(new SelectorItemInfo("contractSourceId.*"));
        sic.add(new SelectorItemInfo("contractType.*"));
        sic.add(new SelectorItemInfo("landDeveloper.*"));
        sic.add(new SelectorItemInfo("partB.*"));
        sic.add(new SelectorItemInfo("curProject.*"));
        sic.add(new SelectorItemInfo("partC.*"));
        sic.add(new SelectorItemInfo("currency.*"));
//        sic.add(new SelectorItemInfo("respDept.*"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
//        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
//        sic.add(new SelectorItemInfo("creator.*"));
		return sic;

	}
	
	protected void treeContractType_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeContractType_valueChanged(e);
		refreshTblMain(this.getMainQuery());
		initTable();
	}
	
	protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeProject_valueChanged(e);
		refreshTblMain(this.getMainQuery());
		initTable();
	}
	
	
	protected void treeSelectChange() throws Exception {
		DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
		DefaultKingdeeTreeNode  typeNode  =	getTypeSelectedTreeNode() ;
		
		Object project  = null;
		if(projectNode!=null){
			project = projectNode.getUserObject();
		}
		Object type  = null;
		if(typeNode!=null){
			type = typeNode.getUserObject();
		}
		
		mainQuery.setFilter(getTreeSelectFilter(project,type,containConWithoutTxt()));

		if(isHasBillTable()) {
			getBillListTable().removeRows(false);
		}	
		
		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
			btnAddNew.setEnabled(true);
		}else if(isHasBillTable()){
			/*
			 * û�к�ͬʱ�����������ε��� sxhong
			 */
			btnAddNew.setEnabled(false);
		}
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		//		super.refresh(e);
		if (isNeedRefresh) {
			refreshTblMain(this.getMainQuery());
		}
	}

	public void refreshListForOrder() throws Exception {
		refreshTblMain(this.getMainQuery());
	}
	
	protected String[] getNotOrderColumns() {
		return new String[]{"attachment","content","isLonelyCal"};
	}

}