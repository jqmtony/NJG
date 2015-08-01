/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustCollection;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustFactory;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class AimAimCostAdjustListUI extends AbstractAimAimCostAdjustListUI {
	private static final Logger logger = CoreUIObject.getLogger(AimAimCostAdjustListUI.class);

	/**
	 * output class constructor
	 */
	public AimAimCostAdjustListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		treeProject.setRootVisible(true);
		treeProject.expandRow(0);
		treeProject.setSelectionRow(0);

		initButton();
		tblMain.checkParsed();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	}

	protected void updateButtonStatus() {
		
	}
	
	private void initButton() {
		btnAddNew.setEnabled(true);
		actionAddNew.setEnabled(true);
		actionAddNew.setVisible(true);
		this.menuItemAddNew.setVisible(true);
		this.menuItemAddNew.setEnabled(true);
		btnAddNew.setVisible(true);
		btnView.setEnabled(true);
		btnView.setVisible(true);
		btnEdit.setEnabled(true);
		btnEdit.setVisible(true);
		btnRemove.setEnabled(true);
		btnRemove.setVisible(true);
		btnRefresh.setEnabled(true);
		btnRefresh.setVisible(true);
		btnAttachment.setEnabled(true);
		btnAttachment.setVisible(true);
		btnAudit.setEnabled(true);
		btnAudit.setVisible(true);
		btnUnAudit.setEnabled(true);
		btnUnAudit.setVisible(true);
		btnPrint.setEnabled(true);
		btnPrint.setVisible(true);
		btnPrintPreview.setEnabled(true);
		btnPrintPreview.setVisible(true);
		
		
		
		
		menuItemAudit.setEnabled(true);
		menuItemAudit.setVisible(true);
		menuItemUnAudit.setEnabled(true);
		menuItemUnAudit.setVisible(true);

		menuEdit.setEnabled(true);
		menuEdit.setVisible(true);
		menuItemAddNew.setEnabled(true);
		menuItemAddNew.setVisible(true);
		menuItemEdit.setEnabled(true);
		menuItemEdit.setVisible(true);
		menuItemRemove.setEnabled(true);
		menuItemRemove.setVisible(true);
	}

	public void storeFields() {
		super.storeFields();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		//判断当前有没有打开新增的窗体
		IUIWindow win = FDCClientUtils.findUIWindow(this.getEditUIName(), this.getUIContext(), dataObjects, OprtState.ADDNEW);
		if (null != win) {
			//显示窗体
			win.show();
			//显示后试图关闭，如果新增的窗体有新增数据就会提示用户，如果没有就直接关掉打开了一个新的
			win.close();
		}
		checkBeforeAddNew(e);
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		if (!aimCostIsLastVersion(selectObjId)) {
			FDCMsgBox.showInfo("该工程项目下不存在目标成本，不允许新增！");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * 该工程项目中是否有目本成本
	 * 
	 * @param projectID
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private boolean aimCostIsLastVersion(String projectID) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select FID from T_AIM_AimCost ");
		builder.appendSql(" where ForgOrProId ='" + projectID + "' ");
		IRowSet rowSet = builder.executeQuery();
		if (rowSet.next()) {
			return true;
		}
		return false;
	}
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.refresh(e);
	}

	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
	}

	private void initData() throws BOSException {
		tblMain.checkParsed();
		tblMain.removeRows();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("curProject.name");
		view.getSelector().add("state");
		view.getSelector().add("creator.name");
		view.getSelector().add("createTime");
		view.getSelector().add("auditor.name");
		view.getSelector().add("auditTime");
		view.getSelector().add("id");
		//过滤工程项目ID
		Set idSet = this.getSelectObjLeafIds();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", idSet, CompareType.INCLUDE));
		AimAimCostAdjustCollection colls = AimAimCostAdjustFactory.getRemoteInstance().getAimAimCostAdjustCollection(view);
		if (colls != null) {
			IRow row = null;
			for (int i = 0; i < colls.size(); i++) {
				AimAimCostAdjustInfo info = colls.get(i);
				row = this.tblMain.addRow();
				row.getCell("id").setValue(info.getId().toString());
				row.getCell("number").setValue(info.getNumber());
				row.getCell("name").setValue(info.getName());
				row.getCell("state").setValue(info.getState());
				if (info.getCurProject() != null) {
					row.getCell("projectName").setValue(info.getCurProject().getName());
				}
				if (info.getCreator() != null) {
					row.getCell("creatorName").setValue(info.getCreator().getName());
				}
				row.getCell("creatorTime").setValue(info.getCreateTime());
				if (info.getAuditor() != null) {
					row.getCell("auditorName").setValue(info.getAuditor().getName());
				}
				row.getCell("auditorTime").setValue(info.getAuditTime());
			}

		}
		this.tblMain.repaint();
		this.setSelectFirstRow(tblMain);
		selectFirstRow();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}


	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String keyValue = getSelectedKeyValue();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		AimAimCostAdjustInfo info = AimAimCostAdjustFactory.getRemoteInstance().getAimAimCostAdjustInfo(new ObjectUuidPK(keyValue), sic);
		if (!(FDCBillStateEnum.SAVED.equals(info.getState()) || FDCBillStateEnum.SUBMITTED.equals(info.getState()))) {
			FDCMsgBox.showWarning("该状态单据不能修改");
			abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String keyValue = getSelectedKeyValue();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		AimAimCostAdjustInfo info = AimAimCostAdjustFactory.getRemoteInstance().getAimAimCostAdjustInfo(new ObjectUuidPK(keyValue), sic);
		if (!(FDCBillStateEnum.SAVED.equals(info.getState()) || FDCBillStateEnum.SUBMITTED.equals(info.getState()))) {
			FDCMsgBox.showWarning("该状态单据不能删除");
			abort();
		}
		super.actionRemove_actionPerformed(e);
	}
	/**
	 * 检查是否选择了项目工程
	 */
	protected void checkBeforeAddNew(ActionEvent e) {
		super.checkBeforeAddNew(e);
	}

	private String proId = null;
	protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		super.treeProject_valueChanged(e);
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		proId = selectObjId;
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAddNew(e);
		super.actionAudit_actionPerformed(e);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAddNew(e);
		super.actionUnAudit_actionPerformed(e);
	}

	protected void audit(List ids) throws Exception {
		checkSelected();
		AimAimCostAdjustFactory.getRemoteInstance().audit(BOSUuid.read(ids.get(0).toString()));
	}

	protected void unAudit(List ids) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		String projectID = proId;

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select FVersionNumber,FGenByAdjust from T_AIM_AimCost ");
		builder.appendSql(" where forgOrProid = '" + projectID + "' ");
		IRowSet rowSet;
		rowSet = builder.executeQuery();
		try {
			String adjustId = null;
			BigDecimal maxVersionNumber = FDCHelper.ZERO;
			while (rowSet.next()) {
				String versionNumber = rowSet.getString("FVersionNumber");
				BigDecimal versionNumberBig = new BigDecimal(versionNumber);
				if (versionNumberBig.compareTo(maxVersionNumber) > 0) {
					maxVersionNumber = versionNumberBig;
				}
			}
			builder.clear();
			builder.appendSql(" select FGenByAdjust,fid from T_AIM_AimCost ");
			builder.appendSql(" where FVersionNumber = '" + maxVersionNumber.toString() + "' ");
			builder.appendSql(" and forgOrProid = '" + projectID + "' ");
			rowSet = builder.executeQuery();
			String  aimCostID=null;
			if (rowSet.next()) {
				adjustId = rowSet.getString("FGenByAdjust");
				aimCostID = rowSet.getString("fid");
				if (adjustId == null) {
					FDCMsgBox.showInfo("该目标成本调整单生成的目标成本不是最新版的，不允许此操作！");
					SysUtil.abort();
				} else {
					if (adjustId.equals(id)) {
						Set oldID = new HashSet();
						builder.clear();
						builder.appendSql("select fid from t_aim_costentry where fheadid='"+aimCostID+"'");
						IRowSet rowSet2 = builder.executeQuery();
						while (rowSet2.next()) {
							oldID.add(rowSet2.getString("fid"));
						}
						
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("costEntryId",oldID,CompareType.INCLUDE));
						view.setFilter(filter);
						SelectorItemCollection sic = new SelectorItemCollection();
						sic.add("costEntryId");
						view.setSelector(sic);
						AimCostProductSplitEntryCollection splitEntryCollection = AimCostProductSplitEntryFactory.getRemoteInstance().getAimCostProductSplitEntryCollection(view );
						oldID.clear();
						for(int i=0;i<splitEntryCollection.size();i++){
							AimCostProductSplitEntryInfo splitEntryInfo = splitEntryCollection.get(i);
							oldID.add(splitEntryInfo.getCostEntryId());
						}
						CostEntryCollection entryCollection = new CostEntryCollection();
						CostEntryCollection lstEntryCollection = new CostEntryCollection();
						if(oldID.size()>0){
							view = new EntityViewInfo();
							filter = new FilterInfo();
							view.setFilter(filter);
							filter.getFilterItems().add(new FilterItemInfo("id",oldID,CompareType.INCLUDE));
							entryCollection = CostEntryFactory.getRemoteInstance().getCostEntryCollection(view);
						}
						
						
						// 删除生成的目标成本
						filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("genByAdjust", id));
						filter.getFilterItems().add(new FilterItemInfo("isLastVersion", new Integer(1)));
						AimCostFactory.getRemoteInstance().delete(filter);
						// 把上一版本置为最新版
						updateAimCostToLastVersion(projectID);
						
						
						view = new EntityViewInfo();
						filter = new FilterInfo();
						view.setFilter(filter);
						view.getSelector().add("costEntry.*");
						filter.getFilterItems().add(new FilterItemInfo("orgOrProId", projectID));
						filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
						filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.TRUE));
						AimCostCollection aimCostCollection = AimCostFactory.getRemoteInstance().getAimCostCollection(view);
						if (aimCostCollection.size() != 0) {
							AimCostInfo aimCostInfo = aimCostCollection.get(0);
							lstEntryCollection = aimCostInfo.getCostEntry();
						}
						Map oldAndNewID = new HashMap();
						for(int i=0;i<entryCollection.size();i++){
							CostEntryInfo costEntryInfo = entryCollection.get(i);
							for(int j=0;j<lstEntryCollection.size();j++){
								CostEntryInfo lstEntryInfo = lstEntryCollection.get(j);
								boolean isOk = false;
								if(!costEntryInfo.getCostAccount().getId().toString().equals(lstEntryInfo.getCostAccount().getId().toString())){
									continue;
								}
								if(FDCHelper.compareTo(costEntryInfo.getCostAmount(), lstEntryInfo.getCostAmount())!=0){
									continue;
								}
								if(costEntryInfo.getProduct()!=null && lstEntryInfo.getProduct()==null){
									continue;
								}
								if(costEntryInfo.getProduct()==null && lstEntryInfo.getProduct()!=null){
									continue;
								}
								if(costEntryInfo.getProduct()!=null && lstEntryInfo.getProduct()!=null && !costEntryInfo.getProduct().getId().toString().equals(lstEntryInfo.getProduct().getId().toString())){
									continue;
								}
								oldAndNewID.put(costEntryInfo.getId().toString(), lstEntryInfo.getId().toString());
								break;
							}
						}
						for(int i=0;i<splitEntryCollection.size();i++){
							AimCostProductSplitEntryInfo splitEntryInfo = splitEntryCollection.get(i);
							splitEntryInfo.setCostEntryId(oldAndNewID.get(splitEntryInfo.getCostEntryId()).toString());
							AimCostProductSplitEntryFactory.getRemoteInstance().updatePartial(splitEntryInfo, sic);
						}
						
						
						
						// 目标成本调整单反审批
						AimAimCostAdjustFactory.getRemoteInstance().unAudit(BOSUuid.read(ids.get(0).toString()));
					} else {
						FDCMsgBox.showInfo("该目标成本调整单生成的目标成本不是最新版的，不允许此操作！");
						SysUtil.abort();
					}
				}
			} else {
				// 目标成本调整单反审批
				AimAimCostAdjustFactory.getRemoteInstance().unAudit(BOSUuid.read(ids.get(0).toString()));
			}
		} catch (SQLException ee) {
			logger.info(ee.getMessage(), ee);
			handUIExceptionAndAbort(ee);
		}
	}

	/**
	 * 目标成本版本最高的置为最新版本
	 * 
	 * @param projectID
	 */
	private void updateAimCostToLastVersion(String projectID) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" update T_AIM_AimCost acc set FIsLastVersion = 1 ");
		builder.appendSql(" where fversionnumber =");
		builder.appendSql(" (");
		builder.appendSql(" select max(fversionnumber) from T_AIM_AimCost ac");
		builder.appendSql(" where ac.ForgOrProId = '" + projectID + "'");
		builder.appendSql(" )");
		builder.appendSql(" and forgOrProid = '" + projectID + "'");
		try {
			builder.executeUpdate();
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}
	/**
	 * 描述：子类必须实现，返回编辑界面类名
	 */
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected String getEditUIName() {
		return AimAimCostAdjustEditUI.class.getName();
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return AimAimCostAdjustFactory.getRemoteInstance();
	}



}