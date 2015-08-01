/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ConPayPlanCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDataCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanDataInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanFactory;
import com.kingdee.eas.fdc.finance.ConPayPlanInfo;
import com.kingdee.eas.fdc.finance.client.util.PayPlanClientUtil;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 *  描述：合同付款计划
 *  
 */
public class ConPayPlanListUI extends AbstractConPayPlanListUI {

	private static final Logger logger = CoreUIObject.getLogger(ConPayPlanListUI.class);


	public ConPayPlanListUI() throws Exception {
		super();
	}
	  

	protected void audit(List ids) throws Exception {
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}
	
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		tblConPayPlan.refresh();
	}

	protected void unAudit(List ids) throws Exception {
	}

	protected KDTable getBillListTable() {
		return tblConPayPlan;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ConPayPlanFactory.getRemoteInstance();
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return ConPayPlanFactory.getRemoteInstance();
	}

	protected boolean isFootVisible() {
		return false;
	}

	protected void initTable() {
		super.initTable();
	}

	protected void freezeBillTableColumn() {
		super.freezeBillTableColumn();
	}
	


	protected boolean displayBillByContract(KDTSelectEvent e, EntityViewInfo view) throws BOSException {
		if (null == view) {
			return false;
		}

		ConPayPlanCollection col = ConPayPlanFactory.getRemoteInstance().getConPayPlanCollection(view);

		if (col != null && col.size() > 0) {
			ConPayPlanInfo info = col.get(0);

			loadDataTable(info);
		} else {
			getBillListTable().removeColumns();
		}

		return true;
	}

	protected void loadDataTable(ConPayPlanInfo editData) {

		getBillListTable().removeColumns();

		ConPayPlanDataCollection datas = editData.getData();

		String[] columnKeys = new String[datas.size() + 3];
		Object[] head = new Object[datas.size() + 3];
		Object[][] body = new Object[1][datas.size() + 3];

		for (int i = 3; i <= datas.size() + 2; i++) {
			ConPayPlanDataInfo info = datas.get(i - 3);
			columnKeys[i] = "" + info.getPayMonth();
			head[i] = "" + info.getPayMonth() / 100 + "年" + info.getPayMonth() % 100 + "月";
			body[0][i] = info.getPayAmount();
		}

		columnKeys[0] = "id";
		head[0] = "id";
		body[0][0] = editData.getId().toString();
		columnKeys[1] = "contractBillId";
		head[1] = "合同ID";
		body[0][1] = editData.getContractBill().getId().toString();

			columnKeys[2] = "payMonth";
		head[2] = "付款时间";
		body[0][2] = "付款金额";

		KDTableHelper.initTable(getBillListTable(), columnKeys, head, body);

		for (int i = 3; i <= datas.size() + 2; i++) {
			PayPlanClientUtil.initFormattedTextCell(getBillListTable(), columnKeys[i], 2);
		}
		getBillListTable().getColumn("id").getStyleAttributes().setHided(true);
		getBillListTable().getColumn("contractBillId").getStyleAttributes().setHided(true);
		getBillListTable().getStyleAttributes().setLocked(true);

		
		getBillListTable().reLayoutAndPaint();
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
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
		if (getMainTable().getCell(top, getKeyFieldName()) == null) {
			return null;
		}

		String contractId = (String) getMainTable().getCell(top, getKeyFieldName()).getValue();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("createTime"));

		SelectorItemCollection selectors = genBillQuerySelector();
		if (selectors != null && selectors.size() > 0) {
			for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);
			}
		}

		return view;
	}

	/**
	 * 
	 * 描述：当左边的树选择变化时的缺省条件（提供默认实现，合同状态为审核，子类可以覆盖，如果没有条件，也要返回一个new FilterInfo()，不能直接返回null）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-9-5 <p>
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));

		return filter;
	}
    

	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("Data.*");
		selectors.add("contractBill.number");
		return selectors;
	}


	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(getMainTable());

		if (getBillListTable().getRowCount() > 0) {
			MsgBox.showInfo("一个合同只能有一个合同付款计划!");
			return;
		}
		super.actionAddNew_actionPerformed(e);
	}

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String boID = this.getSelectedKeyValue();
		checkSelected();
		if (boID == null) {
			return;
		}
		acm.showAttachmentListUIByBoID(boID, this); // boID 是 与附件关联的 业务对象的 ID
    }
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			String id = getSelectedKeyValue(getMainTable());
			uiContext.put("contractID", id);
		}
		super.prepareUIContext(uiContext, e);
		if (uiContext.get("contractBillId") == null) {
			uiContext.put("contractBillId", getSelectedKeyValue(getBillListTable(), "contractBillId"));
		}
	}


	/**
	 * 获取当前选择行的主键
	 * 
	 * @return 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
	 */
	protected String getSelectedKeyValue(KDTable table, String columnKey) {
		//String value = super.getSelectedKeyValue();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		int selectIndex = -1;
		if (selectRows.length > 0) {
			selectIndex = selectRows[0];
		}

		KDTSelectBlock selectBlock = table.getSelectManager().get();
		String selectKeyValue = null;

		if (selectBlock != null) {
			int rowIndex = selectBlock.getTop();
			IRow row = table.getRow(rowIndex);
			if (row == null) {
				return null;
			}

			ICell cell = row.getCell(columnKey);

			if (cell == null) {
				MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource + "Error_KeyField_Fail"));
				SysUtil.abort();
			}

			Object keyValue = cell.getValue();

			if (keyValue != null) {
				selectKeyValue = keyValue.toString();
			}
		}

		return selectKeyValue;
	}

	protected String getEditUIName() {
		return ConPayPlanUI.class.getName();
	}

	public void onShow() throws Exception {
		super.onShow();
		getBillListTable().setColumnMoveable(true);
		FDCClientHelper.setActionEnable(actionAudit, false);
		FDCClientHelper.setActionEnable(actionWorkFlowG, false);
		//合同修订增加附件功能         by Cassiel_peng
		FDCClientHelper.setActionEnable(actionRemove, true);

		tblConPayPlan.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		//		this.btnWorkFlowG.setVisible(true);
		//		this.btnWorkFlowG.setEnabled(true);
		//		this.menuItemWorkFlowG.setVisible(true);
		//		this.menuItemWorkFlowG.setEnabled(true);
	}

	protected boolean isShowAttachmentAction() {
		return false;
	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();

		//		FDCClientHelper.setActionEnable(actionAddNew, false);
		//		FDCClientHelper.setActionEnable(actionEdit, false);
		FDCClientHelper.setActionEnable(actionLocate, false);
		FDCClientHelper.setActionEnable(actionRefresh, true);
		//		FDCClientHelper.setActionEnable(actionRemove, true);

		menuEdit.setVisible(true);
	}
	

	protected void fetchInitData() throws Exception {
		Map param = new HashMap();
		Map initData = ((IFDCBill) getRemoteInterface()).fetchInitData(param);

		//获得当前组织
		orgUnit = (FullOrgUnitInfo) initData.get(FDCConstants.FDC_INIT_ORGUNIT);

	}

	/**
	 * 返回定位字段的集合<p>
	 * 添加了定位字段，现在为：合同编号，合同名称，合同类型，签约时间，签约乙方，合同原币金额，本位币金额
	   Modified by Owen_wen 2010-09-06
	 * @author zhiyuan_tang 2010/07/12
	 */
	protected String[] getLocateNames() {
		return new String[] { "number", "contractName", "contractType.name", "signDate", "partB.name", "originalAmount", "amount", };
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
	private ContractBillEntryCollection getContractBillEntryCols(ContractBillInfo billInfo) {
		ContractBillEntryCollection cols = null;
		if (null != billInfo && null != billInfo.getEntrys()) {
			cols = billInfo.getEntrys();
		}

		return cols;
	}

	/**
	 * description		判断补充合同是否 独立计算
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-17<p>
	 * @param			cols 分录集合
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private boolean isLonelyCal(ContractBillEntryCollection cols) {
		boolean isLonelyCal = false;
		if (null != cols) {
			ContractBillEntryInfo entryInfo = null;
			for (int i = 0; i < cols.size(); i++) {
				entryInfo = cols.get(i);
				if (null != entryInfo.getDetail() && entryInfo.getDetail().toString().equals("是否单独计算")) {
					String content = entryInfo.getContent();
					if (content.equals("是")) {
						isLonelyCal = true;
					} else {
						isLonelyCal = false;
					}
				}
			}
		}

		return isLonelyCal;
	}

	/**
	 * description		返回主合同包含的补充合同ID集合
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-17<p>
	 * @param			contractBillId 主合同ID
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @throws BOSException 
	 * @throws SQLException 
	 * @see							
	 */
	private HashSet getSupplyContract(String contractBillId) throws BOSException, SQLException {
		HashSet suppplySet = new HashSet();

		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select bill.fid id,bill.fcontractpropert ,entry.* from t_con_contractbill bill ");
		buildSQL.appendSql("left outer join t_con_contractbillentry entry on bill.fid=entry.fparentid ");
		buildSQL.appendSql("where bill.fcontractpropert = 'SUPPLY' and entry.fcontent = '" + contractBillId + "' ");
		IRowSet rowSet = buildSQL.executeQuery();
		while (rowSet.next()) {
			String id = rowSet.getString("id");
			suppplySet.add(id);
		}
		return suppplySet;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		actionAuditResult.setVisible(false);
		actionLocate.setVisible(false);
		actionAttachment.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		//		this.toolBar.remove(btnWorkFlowG);
		//		actionWorkFlowG.setVisible(false);
		//		btnWorkFlowG.setVisible(false);
	}
	


	/**
	 * description		返回是否单独增加补充合同行，true就要增加
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-25<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private boolean isAddSupplyRow(Set idsSet, ContractBillInfo billInfo) {
		boolean isAddSupplyRow = false;
		ContractBillEntryCollection cols = getContractBillEntryCols(billInfo);
		if (null != cols && cols.size() > 0) {
			ContractBillEntryInfo entryInfo = null;
			for (int i = 0; i < cols.size(); i++) {
				entryInfo = cols.get(i);
				if (null != entryInfo.getDetail() && entryInfo.getDetail().toString().equals("对应主合同编码")) {
					String content = entryInfo.getContent();
					if (!idsSet.contains(content)) {
						isAddSupplyRow = true;
					}
				}
			}
		}
		return isAddSupplyRow;
	}

	/**
	 * description		填充补充合同行数据
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-18<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private void fillSupplyRow(IRow row, ContractBillInfo info) {
		//合同ID
		if (null != row.getCell("id") && null != info.getId()) {
			row.getCell("id").setValue(info.getId().toString());
		}

		//订立时间
		if (null != row.getCell("bookedDate") && null != info.getBookedDate()) {
			row.getCell("bookedDate").setValue(info.getBookedDate());
		}
		//期间
		if (null != row.getCell("period") && null != info.getPeriod()) {
			row.getCell("period").setValue(info.getPeriod());
		}

		//合同类型
		if (null != row.getCell("contractType.name") && null != info.getContractType()) {
			row.getCell("contractType.name").setValue(info.getContractType().getName());
		}

		//合同编码
		if (null != row.getCell("number") && null != info.getNumber()) {
			row.getCell("number").setValue(info.getNumber());
		}

		//合同名称
		if (null != row.getCell("contractName") && null != info.getName()) {
			row.getCell("contractName").setValue(info.getName());
		}

		//状态
		if (null != row.getCell("state") && null != info.getState()) {
			row.getCell("state").setValue(info.getState());
		}

		//已结算
		if (null != row.getCell("hasSettle")) {
			row.getCell("hasSettle").setValue(Boolean.valueOf(info.isHasSettled()));
		}

		//合同性质
		if (null != row.getCell("contractPropert") && null != info.getContractPropert()) {
			row.getCell("contractPropert").setValue(info.getContractPropert());
		}

		//币别
		if (null != row.getCell("currency") && null != info.getCurrency() && null != info.getCurrency().getName()) {
			row.getCell("currency").setValue(info.getCurrency().getName());
		}

		//原币金额
		if (null != row.getCell("originalAmount") && null != info.getOriginalAmount()) {
			row.getCell("originalAmount").setValue(info.getOriginalAmount());
		}

		//本币金额
		if (null != row.getCell("amount") && null != info.getAmount()) {
			row.getCell("amount").setValue(info.getAmount());
		}

		//乙方
		if (null != row.getCell("partB.name") && null != info.getPartB() && null != info.getPartB().getName()) {
			row.getCell("partB.name").setValue(info.getPartB().getName());
		}

		//形成方式
		if (null != row.getCell("contractSource") && null != info.getContractSourceId()) {
			row.getCell("contractSource").setValue(info.getContractSourceId().getName());
		}

		//签约日期
		if (null != row.getCell("signDate") && null != info.getSignDate()) {
			row.getCell("signDate").setValue(info.getSignDate());
		}

		//责任部门
		if (null != row.getCell("respDept") && null != info.getRespDept() && null != info.getRespDept().getName()) {
			row.getCell("respDept").setValue(info.getRespDept().getName());
		}

		//签约甲方
		if (null != row.getCell("landDeveloper.name") && null != info.getLandDeveloper() && null != info.getLandDeveloper().getName()) {
			row.getCell("landDeveloper.name").setValue(info.getLandDeveloper().getName());
		}

		//丙方
		if (null != row.getCell("partC.name") && null != info.getPartC() && null != info.getPartC().getName()) {
			row.getCell("partC.name").setValue(info.getPartC().getName());
		}

		//造价性质
		if (null != row.getCell("costProperty") && null != info.getCostProperty()) {
			row.getCell("costProperty").setValue(info.getCostProperty());
		}

		if (null != row.getCell("currency.id") && null != info.getCurrency() && null != info.getCurrency().getId()) {
			row.getCell("currency.id").setValue(info.getCurrency().getId());
		}

		if (null != row.getCell("currency.precision") && null != info.getCurrency()) {
			row.getCell("currency.precision").setValue(String.valueOf(info.getCurrency().getPrecision()));
		}
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
		sic.add(new SelectorItemInfo("originalAmount"));
		sic.add(new SelectorItemInfo("mainContractNumber"));
		sic.add(new SelectorItemInfo("attachment"));
		sic.add(new SelectorItemInfo("content"));
		sic.add(new SelectorItemInfo("isRespite"));
		sic.add(new SelectorItemInfo("bookedDate"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("contractSourceId.*"));
		sic.add(new SelectorItemInfo("contractType.*"));
		sic.add(new SelectorItemInfo("landDeveloper.*"));
		sic.add(new SelectorItemInfo("partB.*"));
		sic.add(new SelectorItemInfo("curProject.*"));
		sic.add(new SelectorItemInfo("partC.*"));
		sic.add(new SelectorItemInfo("currency.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		return sic;

	}
	protected void treeContractType_valueChanged(TreeSelectionEvent e) throws Exception {
		if (getTypeSelectedTreeNode() == null)
			return;

		super.treeContractType_valueChanged(e);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		FDCHelper.formatTableNumber(getMainTable(), "amount");
	}

	protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeProject_valueChanged(e);

	}
	
	protected FilterInfo getTreeSelectFilter(Object projectNode, Object typeNode, boolean containConWithoutTxt) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		String companyID = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		/*
		 * 工程项目树
		 */
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			boolean isCompanyUint = false;
			FullOrgUnitInfo selectedOrg = new FullOrgUnitInfo();
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {

				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo) projTreeNodeInfo).getUnit().getId();
					selectedOrg = ((OrgStructureInfo) projTreeNodeInfo).getUnit();
					isCompanyUint = selectedOrg.isIsCompanyOrgUnit();
				} else {
					selectedOrg = (FullOrgUnitInfo) projTreeNodeInfo;
					id = ((FullOrgUnitInfo) projTreeNodeInfo).getId();
					isCompanyUint = selectedOrg.isIsCompanyOrgUnit();
				}

				String orgUnitLongNumber = null;
				if (orgUnit != null && id.toString().equals(orgUnit.getId().toString())) {
					orgUnitLongNumber = orgUnit.getLongNumber();
				} else {
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}

				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));

				f.setMaskString("#0 and #1 and #2");
				if (FDCUtils.getDefaultFDCParamByKey(null, companyID, FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT) && (!isCompanyUint)) {
					FilterInfo f2 = new FilterInfo();
					Set proSet = FDCClientUtils.getProjIdsOfCostOrg(selectedOrg, true);
					String filterSplitSql = "select fcontractbillid from T_con_contractCostSplit head "
							+ " inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid "
							+ " inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " + " ("
							+ FDCClientUtils.getSQLIdSet(FDCClientUtils.getSQLIdSet(proSet)) + ") and fstate<>'9INVALID'";
					f2.getFilterItems().add(new FilterItemInfo("id", filterSplitSql, CompareType.INNER));
					f2.setMaskString("(#0 or #1)");
					if (filter != null) {
						filter.mergeFilter(f2, "and");
					}
				}
				if (filter != null) {
					filter.mergeFilter(f, "and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(new FilterItemInfo("curProject.id", idSet, CompareType.INCLUDE));
				if (FDCUtils.getDefaultFDCParamByKey(null, companyID, FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT)) {
					String filterSplitSql = "select fcontractbillid from T_con_contractCostSplit head "
							+ " inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid "
							+ " inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " + " ("
							+ FDCClientUtils.getSQLIdSet(idSet) + ") and fstate<>'9INVALID'";
					f.getFilterItems().add(new FilterItemInfo("id", filterSplitSql, CompareType.INNER));
					f.setMaskString("(#0 or #1)");

				}
				if (filter != null) {
					filter.mergeFilter(f, "and");
				}
			}
		}

		FilterInfo typefilter = new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();
		/*
		 * 合同类型树
		 */
		if (typeNode != null && typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo) typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet, CompareType.INCLUDE));
		} else if (containConWithoutTxt && typeNode != null && typeNode.equals("allContract")) {
			// 如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}
		if (filter != null && typefilter != null) {
			filter.mergeFilter(typefilter, "and");
		}

		setIsAmtWithoutCostFilter(filter);

		return filter;
	}


	protected String[] getNotOrderColumns() {
		return new String[] { "attachment", "content", "isLonelyCal" };
	}

}