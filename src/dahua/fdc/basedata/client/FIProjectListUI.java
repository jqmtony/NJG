/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FIProjectListUI extends AbstractFIProjectListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FIProjectListUI.class);
	//是否调整模式
	private boolean isAdjustMode = false;
	/**
	 * output class constructor
	 */
	public FIProjectListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		//王谭蓉说此处不需要，先注释
//		UserInfo user = com.kingdee.eas.common.client.SysContext
//				.getSysContext().getCurrentUserInfo();
		if(!FDCUtils.IsFinacial(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString())){
			MsgBox.showWarning(this, "此财务组织未启用财务成本一体化！");
			SysUtil.abort();
		}
		String comId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		isAdjustMode = FDCUtils.isAdjustVourcherModel(null, comId);
		// 判断用户是否属于集团
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionEdit, actionRemove, actionPrint, actionPrintPreview,
				actionCancel, actionCancelCancel, actionAttachment,
				actionIdxRefresh, actionVersionRedact, actionEnabled,
				actionDisEnabled, actionView }, false);
		menuEdit.setVisible(false);
		menuItemView.setVisible(true);
	}

	public void actionGetted_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		IObjectPK pk = new ObjectStringPK(id);
		CurProjectInfo info = (CurProjectInfo) getBizInterface().getValue(pk);
		if (info.getProjectStatus() != null
				&& info.getProjectStatus().getId().toString().equals(
						ProjectStatusInfo.notGetID) && info.isIsLeaf()) {
			String msg = "工程项目获取将处理这个工程项目下需要转入成本的凭证，此凭证处理过程不可逆，确认是否操作？";
			if(isAdjustMode){
				msg = "工程项目获取将处理这个工程项目下需要从预付帐款转入成本的凭证，自动生成对应合同下的调整单，确认是否操作？";
			}
			if (MsgBox.showConfirm2(this,msg) == MsgBox.OK) {
				// 我写的…………
				CurProjectFactory.getRemoteInstance().traceVoucher4Get(pk);
				refresh(e);
				FDCClientUtils.showOprtOK(this);
			}
		} else {
			MsgBox.showError(this, "工程项目状态不适合本操作！");
			SysUtil.abort();
		}
	}

	public void actionFlow_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		IObjectPK pk = new ObjectStringPK(id);
		CurProjectInfo info = (CurProjectInfo) getBizInterface().getValue(pk);
		if (info.getProjectStatus() != null
				&& info.getProjectStatus().getId().toString().equals(
						ProjectStatusInfo.notGetID) && info.isIsLeaf()) {
			String msg = "工程项目流失将处理这个工程项目下需要结转费用的凭证，此凭证处理过程不可逆，确认是否操作？";
			if(isAdjustMode){
				msg = "工程项目获取将处理这个工程项目下需要从预付帐款转入费用的凭证，自动生成对应合同下的调整单，确认是否操作？";
			}
			if (MsgBox.showConfirm2(this,msg) == MsgBox.OK) {
				CurProjectFactory.getRemoteInstance().traceVoucher4Flow(pk);
				refresh(e);
				FDCClientUtils.showOprtOK(this);
			}
		} else {
			MsgBox.showError(this, "工程项目状态不适合本操作！");
			SysUtil.abort();
		}
	}

	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		IObjectPK pk = new ObjectStringPK(id);
		CurProjectInfo info = (CurProjectInfo) getBizInterface().getValue(pk);
		if (info.getProjectStatus() != null
				&& info.getProjectStatus().getId().toString().equals(
						ProjectStatusInfo.settleID) && info.isIsLeaf()) {
			if (MsgBox.showConfirm2(this, "工程项目关闭将导致不能再新增进入动态成本的单据，确认是否操作？") == MsgBox.OK) {
				checkCurProject(info);
				CurProjectFactory.getRemoteInstance().changeStatus(id, "close");
				refresh(e);
				FDCClientUtils.showOprtOK(this);
			}
		} else {
			MsgBox.showError(this, "工程项目状态不适合本操作！");
			SysUtil.abort();
		}
	}

	private void checkCurProject(CurProjectInfo info)
			throws BOSException, EASBizException {
		String objectId = info.getId().toString();
		AimCostSplitDataGetter aimGetter;
		DyCostSplitDataGetter dyGetter;
		HappenDataGetter happenGetter;
		aimGetter = new AimCostSplitDataGetter(objectId);
		happenGetter = new HappenDataGetter(objectId, false, false,true);
		dyGetter = new DyCostSplitDataGetter(objectId, aimGetter,
				happenGetter);
		BigDecimal dyna = FDCHelper.ZERO;
		BigDecimal happen = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.id", info.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("id");
		CostAccountCollection coll = CostAccountFactory.getRemoteInstance()
				.getCostAccountCollection(view);
		for (Iterator it = coll.iterator(); it.hasNext();) {
			CostAccountInfo account = (CostAccountInfo) it.next();
			String accId = account.getId().toString();
			DynamicCostInfo dynamicCostInfo = dyGetter.getDynamicInfo(accId);
			if (dynamicCostInfo != null) {
				AdjustRecordEntryCollection adjusts = dynamicCostInfo
						.getAdjustEntrys();
				BigDecimal aimCost = aimGetter.getAimCost(accId);
				BigDecimal temp = aimCost.add(FDCHelper.ZERO);
				for (int i = 0; i < adjusts.size(); i++) {
					AdjustRecordEntryInfo adjust = adjusts.get(i);
					BigDecimal costAmount = adjust.getCostAmount();
					if (costAmount != null) {
						temp = temp.add(costAmount);
					}
				}
				dyna = dyna.add(temp);
			}

			HappenDataInfo happenInfo = happenGetter.getHappenInfo(accId);
			BigDecimal hasHappen = FDCHelper.ZERO;
			if (happenInfo != null) {
				hasHappen = happenInfo.getAmount();
			}
			happen = happen.add(hasHappen);
		}
		if (!(dyna.compareTo(happen) == 0)) {
			throw new FDCException(FDCException.NOCLOSEFORNOTZERO);
		}
	}

	public void actionAntiClose_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ICurProject iCurProject = CurProjectFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		CurProjectInfo info = (CurProjectInfo) iCurProject.getValue(pk);
		if (info.getProjectStatus() != null
				&& info.getProjectStatus().getId().toString().equals(
						ProjectStatusInfo.closeID) && info.isIsLeaf()) {
			
			if (MsgBox.showConfirm2(this, "工程项目反关闭将可以继续新增进入动态成本的单据，确认是否操作？") == MsgBox.OK) {
				CurProjectFactory.getRemoteInstance().changeStatus(id,
						"antiClose");
				refresh(e);
				FDCClientUtils.showOprtOK(this);
			}
		} else {
			MsgBox.showWarning(this, "工程项目状态不适合本操作！");
			return;
		}
	}

	public void actionAntiGetted_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ICurProject iCurProject = CurProjectFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		CurProjectInfo info = (CurProjectInfo) iCurProject.getValue(pk);
		if (info.getProjectStatus() != null
				&& info.getProjectStatus().getId().toString().equals(
						ProjectStatusInfo.notIntiID) && info.isIsLeaf()) {
			String msg = "工程项目反获取不处理相关的凭证，此操作可能造成科目数据错误，谨慎操作！请确认是否继续？";
			if(isAdjustMode){
				msg = "工程项目获取将处理这个工程项目下需要从成本转入预付帐款的凭证，自动生成对应合同下的调整单，确认是否操作？";
			}
			if (MsgBox.showConfirm2(this,msg) == MsgBox.OK) {
				CurProjectFactory.getRemoteInstance().changeStatus(id,
						"antiGetted");
				refresh(e);
				FDCClientUtils.showOprtOK(this);
			}
		} else {
			MsgBox.showWarning(this, "工程项目状态不适合本操作！");
			return;
		}
	}

	public void actionAntiFlow_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ICurProject iCurProject = CurProjectFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		CurProjectInfo info = (CurProjectInfo) iCurProject.getValue(pk);
		if (info.getProjectStatus() != null
				&& info.getProjectStatus().getId().toString().equals(
						ProjectStatusInfo.flowID) && info.isIsLeaf()) {
			String msg = "工程项目反流失不处理相关的凭证，此操作可能造成科目数据错误，谨慎操作！请确认是否继续？";
			if(isAdjustMode){
				msg = "工程项目获取将处理这个工程项目下需要从费用转入预付帐款的凭证，自动生成对应合同下的调整单，确认是否操作？";
			}
			if (MsgBox.showConfirm2(this,msg) == MsgBox.OK) {
				CurProjectFactory.getRemoteInstance().changeStatus(id,
						"antiFlow");
				refresh(e);
				FDCClientUtils.showOprtOK(this);
			}
		} else {
			MsgBox.showWarning(this, "工程项目状态不适合本操作！");
			return;
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	public void actionTransfer_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ICurProject iCurProject = CurProjectFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		CurProjectInfo info = (CurProjectInfo) iCurProject.getValue(pk);
		if (info.getProjectStatus() != null
				&& !info.getProjectStatus().getId().toString().equals(ProjectStatusInfo.flowID)//流失
				&& !info.getProjectStatus().getId().toString().equals(ProjectStatusInfo.closeID)//关闭
				&& !info.getProjectStatus().getId().toString().equals(ProjectStatusInfo.settleID)//竣工结算
				&& !info.getProjectStatus().getId().toString().equals(ProjectStatusInfo.transferID)//已中转
				&& info.isIsLeaf()) {
			if (MsgBox.showConfirm2(this, "中转后的工程项目将不能发生新的业务，确认是否继续？") == MsgBox.OK) {
				CurProjectFactory.getRemoteInstance().changeStatus(id,
						"transfer");
				refresh(e);
				FDCClientUtils.showOprtOK(this);
			}
		} else {
			MsgBox.showWarning(this, "工程项目状态不适合本操作！");
			return;
		}
	}
}