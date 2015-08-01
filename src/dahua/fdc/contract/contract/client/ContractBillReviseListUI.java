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
 * 合同修订 序时薄
 */
public class ContractBillReviseListUI extends AbstractContractBillReviseListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractBillReviseListUI.class);

	/**
	 * 是否刷新MainTable
	 */
	private boolean isNeedRefresh = true;
	
	private static final String SUPPLY = "SUPPLY";
	/**
	 * 状态字段名字
	 */
	private static final String STATE_FIELD_NAME = "state";

	/**
	 * output class constructor
	 */
	public ContractBillReviseListUI() throws Exception {
		super();
	}
	  
	/**
	 * description		批量审批
	 * @author			蒲磊 <p>
	 * @createDate		2011-8-23<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#audit(java.util.List)
	 */
	protected void audit(List ids) throws Exception {
	    // 之前所有的合同信息都是通过远程方法来获取的，现在将所有合同信息都嵌入到修订列表信息中，方便追查及减少远程连接，节省资源
		// 此处主要处理合同ID为null时，报中断的异常，具体操作：
		//当选择左边工程项目后，在右边上面的合同列表中选择具体合同，会出现合同下的修订信息，选择修订合同进行审批的时候，
		//正确操作为选择修订合同直接进行审批，中间不进行任何操作。其他操作如：在审批之前，在合同列表表格中进行刷新，使合同别表
		//中选择的合同失去选择焦点后，再审批，就会报中断，即空指针异常，是因为获取不到原合同ID。故在此根据修订合同获取原合同ID
		// Added by Adward_huang 2012-8-29  现在已经换了一种方式,不是基于远程方式来获取原合同信息，而是将合同信息嵌入到修订信息中
		int rowIndex = this.tblContractRev.getSelectManager().getActiveRowIndex();
		ContractBillReviseInfo contractBillReviseInfo = (ContractBillReviseInfo) tblContractRev.getRow(rowIndex).getUserObject();
		ContractBillInfo contractBillInfo = contractBillReviseInfo.getContractBill();
		
		if(contractBillInfo.getState() == FDCBillStateEnum.CANCEL) {
			MsgBox.showWarning(this, "合同已终止，修订不能审批");
			SysUtil.abort();
		}
		
		if(contractBillInfo.isHasSettled()) {
			MsgBox.showWarning(this, "合同已结算，修订不能审批");
			SysUtil.abort();
		}
		
		ContractBillReviseFactory.getRemoteInstance().audit(ids);

	}
	
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		tblContractRev.refresh();
	}
	
	/**
	 * 直接反审批,不反写合同,不处理后续逻辑。
	 * @author ling_peng
	 */
	  protected void unAudit(List ids) throws Exception {
		  
		String conId = getSelectedKeyValue(getMainTable());
			
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select state, hasSettled where id = '" + conId + "'");
			
		if(contractBillInfo.getState() == FDCBillStateEnum.CANCEL) {
				MsgBox.showWarning(this, "合同已终止，修订不能反审批。");
				SysUtil.abort();
		}
			
		if(contractBillInfo.isHasSettled()) {
				MsgBox.showWarning(this, "合同已结算，修订不能反审批。");
				SysUtil.abort();
		}
		String contractBillId = null;
		ContractClientUtils.checkSplitedForAmtChange(this, contractBillId);
		ContractBillReviseFactory.getRemoteInstance().unAudit(ids);
		
	  }
	
//	审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		获取用户选择的块
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
	 * description		修订列表TABLE
	 * @author			蒲磊 <p>
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
	 * 描述：根据选择的合同显示单据列表
	 * 
	 * @param e
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-8-2
	 *               <p>
	 */
	protected void displayBillByContract(EntityViewInfo view) throws BOSException {
		if(null == view){
			return;
		}
		btnAddNew.setEnabled(true);

		//		int pre = getPre(e);
		//设置精度
		//		String oriFormat = FDCClientHelper.getNumberFormat(pre,true);
		//		getBillListTable().getColumn("originalAmount").getStyleAttributes().setNumberFormat(oriFormat);
		//		getBillListTable().getColumn("oldOriginalAmount").getStyleAttributes().setNumberFormat(oriFormat);
		
		ContractBillReviseCollection contractChangeBillCollection = ContractBillReviseFactory.getRemoteInstance()
				.getContractBillReviseCollection(view);
		for (Iterator iter = contractChangeBillCollection.iterator(); iter.hasNext();) {
			ContractBillReviseInfo element = (ContractBillReviseInfo) iter.next();
			//建发要不显示打回和保存的
			IRow row = getBillListTable().addRow();
			// 将用户对象信息嵌入到修订信息中，减少远程方法是用 Added by Adward_huang 2012-08-29
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
	 * description		生成查询修订列表数据的条件
	 * @author			蒲磊 <p>
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
	 * description		获取修订列表查询字段集合
	 * @author			蒲磊 <p>
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

        // 将原合同信息嵌入到修订信息中，减少远程方法是用 Added by Adward_huang 2012-08-29
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
			//是否刷新tblMain设置为【不刷新】
			isNeedRefresh = false;
			//关闭已经打开的拆分界面 （关闭的时候会自动刷新tblMain，那么tblMain选择的行就会丢失，所以在这里做个标识来控制）
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
			MsgBox.showWarning(this, "该合同存在未审核的修订记录，不能新增修订");
			SysUtil.abort();
		}
		
		FilterInfo conSetFilter = new FilterInfo();
		conSetFilter.appendFilterItem("id", id);
		conSetFilter.appendFilterItem("hasSettled", Boolean.TRUE);
		boolean hasSettled = ContractBillFactory.getRemoteInstance().exists(conSetFilter);
		if(hasSettled) {
			MsgBox.showWarning(this, "该合同已经结算，不能修订");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * description		返回合同修订单据的编辑界面
	 * @author			蒲磊 <p>
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
		//合同修订增加附件功能         by Cassiel_peng
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
		
		//获得当前组织
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		
	}
	
	/**
	 * 返回定位字段的集合<p>
	 * 添加了定位字段，现在为：合同编号，合同名称，合同类型，签约时间，签约乙方，合同原币金额，本位币金额
	   Modified by Owen_wen 2010-09-06
	 * @author zhiyuan_tang 2010/07/12
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "contractType.name", 
				"signDate", "partB.name", "originalAmount", "amount",};
	}
	
	/**
	 * description		返回合同的分录集合
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-17<p>
	 * @param			billInfo 合同单据
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
				//只有合同才需求追加过滤条件：非独立核算的补充合同， 无文本合同不需要
				setIsAmtWithoutCostFilter(viewInfo.getFilter());
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	private void refreshTblMain(EntityViewInfo view) throws BOSException, SQLException, EASBizException {

		tblMain.removeRows(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);//只允许选择单行


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
			 * 没有合同时不能新增下游单据 sxhong
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