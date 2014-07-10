/**
 * output package name
 */
package com.kingdee.eas.port.pm.contract.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractListBaseUI;
import com.kingdee.eas.fdc.contract.client.ContractSettlementBillContants;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.FindDialog;
import com.kingdee.eas.port.pm.contract.ContractChangeSettleBillCollection;
import com.kingdee.eas.port.pm.contract.ContractChangeSettleBillFactory;
import com.kingdee.eas.port.pm.contract.ContractChangeSettleBillInfo;
import com.kingdee.eas.port.pm.contract.IContractChangeSettleBill;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * 描述:变更签证确认单列表界面
 * 
 * @author liupd date:2006-10-13
 *         <p>
 * @version EAS5.1.3
 */
public class ContractChangeSettleBillListUI extends
		AbstractContractChangeSettleBillListUI {
	private static final Logger logger = CoreUIObject.getLogger(ContractChangeSettleBillListUI.class);

	// 定位框 jelon
	private FindDialog findDialog = null;

	//合同可进行多次结算
	private boolean canSetterMore = false;
	
	protected void initWorkButton() {
		super.initWorkButton();
		actionRespite.setVisible(true);
		actionRespite.setEnabled(true);
		actionCancelRespite.setVisible(true);
		actionCancelRespite.setEnabled(true);
		
	}
	 protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
	    super.prepareUIContext(uiContext, e);
	    uiContext.put("parent", this);
	 }
	 public void doRefresh() throws Exception{
	    	refresh(null);
	    }
	/**
	 * output class constructor
	 */
	public ContractChangeSettleBillListUI() throws Exception {
		super();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkIsInWorkflow();
		checkBeforeEdit();
		super.actionEdit_actionPerformed(e);
	}
	
	
    protected void checkBeforeEdit() throws Exception {
	    checkSelected();
	    List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
	    if(idList.size()<1)return;
	    ContractChangeSettleBillInfo  info =  ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(idList.get(0).toString()));
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if(states[i].equals(info.getState().getValue())) {
				pass = true;
			}
		}
		if(!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
    }
    
    /**
     * 
     * 描述：单据可修改、删除的状态
     * @return
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected String[] getBillStateForEditOrRemove() {
    	return new String[]{FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE};
    }

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	List id = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			ContractChangeSettleBillInfo  info =  ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(id.get(i).toString()),sels);
			if (!FDCBillStateEnum.SUBMITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IContractChangeSettleBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	List id = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			ContractChangeSettleBillInfo  info =  ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(id.get(i).toString()),sels);
			if (!FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IContractChangeSettleBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List id = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
		}
		super.actionRemove_actionPerformed(e);
	}
	/**
	 * 快速模糊定位<P>
	 * 增加定位字段：合同类型、签约时间 Modified by Owen_wen 2010-09-07
	 * @author ling_peng
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "contractType.name", "signDate", "partB.name",};
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)throws Exception {
		super.tblMain_tableClicked(e);
	}
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		if (!isHasBillTable()) {
			super.tblMain_tableSelectChanged(e);
			return;
		}
		if (e.getSelectBlock() == null)
			return;
		getBillListTable().removeRows(false);

		EntityViewInfo view = genBillQueryView(e);
		if (!displayBillByContract(e, view)) {
			displayBillByContract(view);
		}
		if (getBillListTable() != null && getBillListTable().getRowCount() > 0) {
			getBillListTable().getSelectManager().select(0, 0);
		}
	}
	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// 检查是否选中合同
		checkSelected(getMainTable());

		// 检查合同是否已有结算
		String billId = getSelectedKeyValue(getMainTable());
		super.actionAddNew_actionPerformed(e);
	}

	
	
	/**
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.client.ListUI#getTableForPrintSetting()
	 *      描述：重载以实现打印非tblMain控件
	 * @return
	 * @author:jelon 创建时间：2006-09-13
	 *               <p>
	 */
	protected KDTable getTableForPrintSetting() {
		// return super.getTableForPrintSetting();
		return tblSettlementList;
	}

	/**
	 * 
	 * 描述：返回远程接口（子类必须实现）
	 * 
	 * @return
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return ContractChangeSettleBillFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {

		return getRemoteInterface();
	}

	/**
	 * 
	 * 描述：获取当前单据的Table（子类必须实现）
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-2
	 *               <p>
	 */
	protected KDTable getBillListTable() {
		return tblSettlementList;
	}

    
		
	/**
	 * 
	 * 描述：根据选择的合同显示单据列表
	 * 
	 * @param e
	 * @throws BOSException
	 * @author:Jelon 创建时间：2006-8-18
	 *               <p>
	 */
	
	protected boolean  displayBillByContract(KDTSelectEvent e,EntityViewInfo view) throws BOSException {
		if(view==null){
			return false;
		}

		int pre = getPre(e);
		
		if(canSetterMore){
			getBillListTable().getColumn(ContractSettlementBillContants.COL_ISFINALSETTLE).getStyleAttributes().setHided(false);
		}else{
		}

		//设置精度
		String oriFormat = FDCClientHelper.getNumberFormat(pre,true);
		getBillListTable().getColumn("originalAmount").getStyleAttributes().setNumberFormat(oriFormat);
		
		ContractChangeSettleBillCollection coll = ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillCollection(view);
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ContractChangeSettleBillInfo element = (ContractChangeSettleBillInfo) iter.next();

			String contractId = element.getContractBill().getId().toString();
			EntityViewInfo conView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			conView.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("contract.Id", contractId));
			IRow row = getBillListTable().addRow();

			row.getCell(ContractSettlementBillContants.COL_ID).setValue(element.getId().toString());
			row.getCell(ContractSettlementBillContants.COL_STATE).setValue(element.getState());
			row.getCell(ContractSettlementBillContants.COL_NUMBER).setValue(element.getNumber());
			row.getCell("name").setValue(element.getName());

			row.getCell(ContractSettlementBillContants.COL_CONTRACTNUMBER).setValue(element.getContractBill().getNumber());
			row.getCell("contractBill.name").setValue(element.getContractBill().getName());

			row.getCell("curProject.number").setValue(element.getCurProject().getNumber());
			row.getCell("curProject.name").setValue(element.getCurProject().getName());
			row.getCell("reportAmount").setValue(element.getReportAmount());
			row.getCell("allowAmount").setValue(element.getAllowAmount());
			if (element.getAuditor() != null)
				row.getCell(ContractSettlementBillContants.COL_AUDITOR).setValue(element.getAuditor().getName());
			row.getCell(ContractSettlementBillContants.COL_AUDITTIME).setValue(element.getAuditTime());

		}

		return true;
	}

	/**
	 * 
	 * 描述：生成获取单据的Selector
	 * 
	 * @return
	 * @author:jelon 创建时间：2006-8-18
	 *               <p>
	 */
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");

		//币别精度
		//selectors.add("currency.precision");	
		
		selectors.add("contractBill.number");
		selectors.add("contractBill.name");
		
		selectors.add("creator.name");
		selectors.add("auditor.name");
		
//		selectors.add("voucher.number");
		
		selectors.add("period.number");
		selectors.add("period.periodNumber");
		selectors.add("period.periodYear");
		selectors.add("isRespite");
		return selectors;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#freezeBillTableColumn()
	 */
	protected void freezeBillTableColumn() {
		super.freezeBillTableColumn();
		// 结算单造价
	}

	protected String getEditUIName() {
		return ContractChangeSettleBillEditUI.class.getName();
	}


	protected void updateButtonStatus() {
		super.updateButtonStatus();

		// 如果是虚体成本中心，则不能审批、反审批
		// if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
		// /*
		// * actionAudit.setEnabled(false); actionUnAudit.setEnabled(false);
		// */
		// actionAudit.setVisible(false);
		// actionUnAudit.setVisible(false);
		// }

		// 放开，在虚体可以增删改
		actionAddNew.setEnabled(true);
		actionEdit.setEnabled(true);
		actionRemove.setEnabled(true);
		actionAddNew.setVisible(true);
		actionEdit.setVisible(true);
		actionRemove.setVisible(true);
		actionAttachment.setEnabled(true);
		actionAttachment.setVisible(true);
		menuEdit.setVisible(true);
		actionRespite.setVisible(true);
		actionCancelRespite.setVisible(true);
	}

	public void initUIToolBarLayout() {
		// TODO 自动生成方法存根
		super.initUIToolBarLayout();

		// 设置审批、反审批图标
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
	}

	protected boolean checkBeforeRemove() throws Exception {
		if(!super.checkBeforeRemove()){
			return false;
		}
		
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("settlementBill", idSet,
								CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = SettlementCostSplitFactory
				.getRemoteInstance().getCoreBillBaseCollection(view);
		Iterator iter = coll.iterator();
		if (iter.hasNext()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("exist_ref"));
			SysUtil.abort();
		}
		
		return true;
	}

	protected boolean isFootVisible() {
		return false;
	}

	protected void fetchInitData() throws Exception {
		Map param = new HashMap();
		Map initData = ((IFDCBill)getRemoteInterface()).fetchInitData(param);
		
		//获得当前组织
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		
		try {
			//合同可进行多次结算
			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			if(paramItem.get(FDCConstants.FDC_PARAM_MORESETTER)!=null){
				canSetterMore = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_MORESETTER).toString()).booleanValue();
			}
			
			//启用成本财务一体化
			if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
				isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
			}
		}catch (Exception e) {
			handUIException(e);
		}
	}

	protected void tblSettlementList_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblSettlementList_tableSelectChanged(e);
		IRow row;
		if (this.tblSettlementList.getSelectManager().getActiveRowIndex()!= -1) {
			if((tblSettlementList.getSelectManager().getBlocks().size() > 1)
				||(e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() > 0)){
				actionRespite.setEnabled(true);
				actionCancelRespite.setEnabled(true);
			}else{
				row = this.tblSettlementList.getRow(this.tblSettlementList.getSelectManager().getActiveRowIndex());
				if(tblMain.getSelectManager().getBlocks().size() == 1 
					&&FDCBillStateEnum.AUDITTED.equals(row.getCell("state").getValue())){
					actionRespite.setEnabled(false);
					actionCancelRespite.setEnabled(true);
				}
			}
		}
	}
	
	/**
	 * 重载父类方法，支持跨项目拆分的工程项目也可进行合同结算及请款
	 */
	protected FilterInfo getTreeSelectFilter(Object projectNode,
			Object typeNode, boolean containConWithoutTxt) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		String companyID = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		/*
		 * 工程项目树
		 */
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			boolean isCompanyUint = false;
			FullOrgUnitInfo selectedOrg = new FullOrgUnitInfo();
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();
					selectedOrg = ((OrgStructureInfo)projTreeNodeInfo).getUnit();
					isCompanyUint = selectedOrg.isIsCompanyOrgUnit();
				}else{
					selectedOrg = (FullOrgUnitInfo)projTreeNodeInfo;
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
					isCompanyUint = selectedOrg.isIsCompanyOrgUnit();
				}				
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				
				f.setMaskString("#0 and #1 and #2");
				if(FDCUtils.getDefaultFDCParamByKey(null, companyID, FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT)
						&& (!isCompanyUint)){
					FilterInfo f2 = new FilterInfo();
					Set proSet = FDCClientUtils.getProjIdsOfCostOrg(selectedOrg, true);
					String filterSplitSql="select fcontractbillid from T_con_contractCostSplit head " +
						" inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid " +
						" inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " +
						" ("+FDCClientUtils.getSQLIdSet(FDCClientUtils.getSQLIdSet(proSet))+") and fstate<>'9INVALID'";
					f2.getFilterItems().add(new FilterItemInfo("id", filterSplitSql,CompareType.INNER));
					f2.setMaskString("(#0 or #1)");
					if(filter!=null){
						filter.mergeFilter(f2,"and");
					}
				}
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(new FilterItemInfo("curProject.id", idSet,CompareType.INCLUDE));
				if(FDCUtils.getDefaultFDCParamByKey(null, companyID, FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT)){
					String filterSplitSql="select fcontractbillid from T_con_contractCostSplit head " +
							" inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid " +
							" inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " +
							" ("+FDCClientUtils.getSQLIdSet(idSet)+") and fstate<>'9INVALID'";
					f.getFilterItems().add(new FilterItemInfo("id", filterSplitSql,CompareType.INNER));
					f.setMaskString("(#0 or #1)");

				}
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		/*
		 * 合同类型树
		 */
		if (typeNode != null&& typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet,CompareType.INCLUDE));
		}else if(containConWithoutTxt && typeNode != null &&typeNode.equals("allContract")){
			//如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}
		
		//三方合同
		if(!((ContractListBaseUI)this instanceof ContractBillListUI)){
			typefilter.appendFilterItem("isAmtWithoutCost", String.valueOf(0));
		}
		
		if(filter!=null && typefilter!=null){
			filter.mergeFilter(typefilter,"and");
		}
		
		return filter;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionQuery.setEnabled(true);
		this.actionQuery.setVisible(true);
	}	

	protected boolean isOnlyQueryAudited() {
		return true;
	}
	protected void audit(List ids) throws Exception {
		ContractChangeSettleBillFactory.getRemoteInstance().audit(ids);
	}
	/**
	 * 
	 * 描述：反审核（子类必须实现，调用服务器端打反审核标志的方法即可）
	 * @param ids
	 * @throws Exception
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected void unAudit(List ids) throws Exception {
		 ContractChangeSettleBillFactory.getRemoteInstance().unAudit(ids);
	}
}