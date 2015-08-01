/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.client;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.permission.client.util.PermissionHelper;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FdcContextHelper;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcSqlUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class FdcMobileListUI extends AbstractFdcMobileListUI {

	private static final Logger logger = CoreUIObject.getLogger(FdcMobileListUI.class);

	// 快捷键
	protected static final String ACTIONKEY_SHOW_MORE = "SHOW_MORE";
	protected static final String ACTIONKEY_LOCK = "LOCK";
	protected static final String ACTIONKEY_SUPER_LOCK = "SUPER_LOCK";

	// 是否显示更多列
	protected boolean isShowMore = false;
	// 是否正处于编辑中
	protected boolean isEditting = false;
	// 是否正处于超级编辑中
	protected boolean isSuperEditting = false;

	/**
	 * output class constructor
	 */
	public FdcMobileListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 描述：
	 * 
	 * @return
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#getBizInterface()
	 */
	@Override
	protected abstract ICoreBase getBizInterface() throws Exception;

	/**
	 * 描述：
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#getEditUIName()
	 */
	@Override
	protected abstract String getEditUIName();

	/**
	 * 描述：
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#getEditUIModal()
	 */
	@Override
	protected String getEditUIModal() {
		// TODO Auto-generated method stub
		return UIFactoryName.NEWTAB;
	}
	
	/**
	 * 描述：
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#onLoad()
	 */
	@Override
	public void onLoad() throws Exception {
		// 默认“普通编辑”模式
		isEditting = true;
		isSuperEditting = false;

		// TODO Auto-generated method stub
		super.onLoad();
	}

	@Override
	protected void initTable() {
		// TODO Auto-generated method stub
		super.initTable();

		getMainTable().getStyleAttributes().setLocked(true);

		// //////////////////////////////////////////////////////////////////

		String[] tblMainTableFormateDateCols = new String[] { "createTime", "lastUpdateTime", "auditTime" };
		FDCTableHelper.formatTableDate(getMainTable(), tblMainTableFormateDateCols);
	}

	/**
	 * 描述：
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#initWorkButton()
	 */
	@Override
	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();

		// //////////////////////////////////////////////////////////////////

		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));

		// //////////////////////////////////////////////////////////////////

		FDCClientHelper.setActionVisible(actionAddNew, false);
		FDCClientHelper.setActionVisible(actionEdit, false);
		FDCClientHelper.setActionVisible(actionRemove, false);

		FDCClientHelper.setActionVisible(actionPrint, false);
		FDCClientHelper.setActionVisible(actionPrintPreview, false);
		FDCClientHelper.setActionVisible(actionCancel, false);
		FDCClientHelper.setActionVisible(actionCancelCancel, false);

		FDCClientHelper.setActionVisible(actionCreateTo, false);
		FDCClientHelper.setActionVisible(actionTraceDown, false);
		FDCClientHelper.setActionVisible(actionTraceUp, false);
		FDCClientHelper.setActionVisible(actionCopyTo, false);

		FDCClientHelper.setActionVisible(actionAttachment, false);

		FDCClientHelper.setActionEnV(actionAudit, false);
		FDCClientHelper.setActionEnV(actionUnAudit, false);
		FDCClientHelper.setActionEnV(actionAuditResult, false);
		FDCClientHelper.setActionEnV(actionMultiapprove, false);
		FDCClientHelper.setActionEnV(actionWorkFlowG, false);
		FDCClientHelper.setActionEnV(actionWorkflowList, false);
		FDCClientHelper.setActionEnV(actionNextPerson, false);

		// //////////////////////////////////////////////////////////////////
	}

	protected void afterOnLoad() throws Exception {
		// TODO Auto-generated method stub

		// 注册快捷键
		detachListeners();
		registeShortKey();
		attachListeners();

		// 重设组件
		resetComponent();
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	protected EditShortcutListener showMoreListener = new EditShortcutListener(ACTIONKEY_SHOW_MORE);
	protected EditShortcutListener lockListener = new EditShortcutListener(ACTIONKEY_LOCK);
	protected EditShortcutListener superLockListener = new EditShortcutListener(ACTIONKEY_SUPER_LOCK);

	class EditShortcutListener extends AbstractAction {
		String shortcut;

		EditShortcutListener(String sc) {
			shortcut = sc;
		}

		public void actionPerformed(ActionEvent evt) {
			if (ACTIONKEY_SHOW_MORE.equals(shortcut)) {
				try {
					showMore();
				} catch (Exception e) {
					e.printStackTrace();
					FdcMobileListUI.this.handleException(e);
				}
			}
			if (ACTIONKEY_LOCK.equals(shortcut)) {
				try {
					lock();
				} catch (Exception e) {
					e.printStackTrace();
					FdcMobileListUI.this.handleException(e);
				}
			}
			if (ACTIONKEY_SUPER_LOCK.equals(shortcut)) {
				try {
					superLock();
				} catch (Exception e) {
					e.printStackTrace();
					FdcMobileListUI.this.handleException(e);
				}
			}
		}
	}

	/**
	 * 注册快捷键
	 * 
	 * @throws Exception
	 */
	protected void registeShortKey() {
		InputMap imEntry = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

		// ACTIONKEY_SHOW_MORE
		KeyStroke ctrl_shift_alt_f10 = KeyStroke.getKeyStroke(KeyEvent.VK_F10, InputEvent.CTRL_MASK
				| InputEvent.SHIFT_MASK | InputEvent.ALT_MASK);
		imEntry.put(ctrl_shift_alt_f10, ACTIONKEY_SHOW_MORE);

		// ACTIONKEY_LOCK
		KeyStroke ctrl_shift_alt_f11 = KeyStroke.getKeyStroke(KeyEvent.VK_F11, InputEvent.CTRL_MASK
				| InputEvent.SHIFT_MASK | InputEvent.ALT_MASK);
		imEntry.put(ctrl_shift_alt_f11, ACTIONKEY_LOCK);

		// ACTIONKEY_SUPER_LOCK
		KeyStroke ctrl_shift_alt_f12 = KeyStroke.getKeyStroke(KeyEvent.VK_F12, InputEvent.CTRL_MASK
				| InputEvent.SHIFT_MASK | InputEvent.ALT_MASK);
		imEntry.put(ctrl_shift_alt_f12, ACTIONKEY_SUPER_LOCK);
	}

	protected void attachListeners() {
		ActionMap entryActionMap = this.getActionMap();
		entryActionMap.put(ACTIONKEY_SHOW_MORE, this.showMoreListener);
		entryActionMap.put(ACTIONKEY_LOCK, this.lockListener);
		entryActionMap.put(ACTIONKEY_SUPER_LOCK, this.superLockListener);

	}

	/**
	 * 拆卸监听器
	 */
	protected void detachListeners() {
		ActionMap entryActionMap = this.getActionMap();
		entryActionMap.put(ACTIONKEY_SHOW_MORE, null);
		entryActionMap.put(ACTIONKEY_LOCK, null);
		entryActionMap.put(ACTIONKEY_SUPER_LOCK, null);

	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	protected void showMore() throws Exception {
		// 是否管理员登录
		if (!isShowMore && !FdcContextHelper.isAdminLogin(this)) {
			SysUtil.abort();
		}

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		isShowMore = !isShowMore;

		// 重设组件
		resetComponent();
	}

	protected void lock() throws Exception {
		// 是否FDC登录
		if (!isEditting && !FdcContextHelper.isFdcLogin(this)) {
			SysUtil.abort();
		}

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		isEditting = !isEditting;
		isSuperEditting = false;

		// 重设组件
		resetComponent();
	}

	protected void superLock() throws Exception {
		// 是否超级FDC登录
		if (!isSuperEditting && !FdcContextHelper.isSuperFdcLogin(this)) {
			SysUtil.abort();
		}

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		isEditting = false;
		isSuperEditting = !isSuperEditting;

		// 重设组件
		resetComponent();
	}

	/**
	 * 重设组件
	 */
	public void resetComponent() throws Exception {
		// /////////////////////////////////////////////////////////////////////

		FDCClientHelper.setActionEnV(actionAttachment, false);
		FDCClientHelper.setActionEnV(actionWorkFlowG, false);
		FDCClientHelper.setActionEnV(actionAuditResult, false);

		// /////////////////////////////////////////////////////////////////////

		// 只有超级编辑状态下，才能新增、插入、删除、导出
		FDCClientHelper.setActionEnV(actionAddNew, isSuperEditting);
		FDCClientHelper.setActionEnV(actionEdit, isEditting || isSuperEditting);
		FDCClientHelper.setActionEnV(actionRemove, isSuperEditting);
		// FDCClientHelper.setActionEnV(actionAudit, isSuperEditting);
		// FDCClientHelper.setActionEnV(actionUnAudit, isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		// 屏蔽导出
		FDCClientHelper.setActionEnV(actionExportBillSql, false);
		FDCClientHelper.setActionEnV(actionExportEntrySql, false);
		FDCClientHelper.setActionEnV(actionExportItemSql, false);
		FDCClientHelper.setActionEnV(actionExportSql, false);

		// /////////////////////////////////////////////////////////////////////
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	@Override
	protected void prepareUIContext(UIContext uiContext) {
		// TODO Auto-generated method stub
		super.prepareUIContext(uiContext);

		if (null != uiContext) {
			uiContext.put("isSuperEditting", isSuperEditting);
			uiContext.put("isEditting", isEditting);
		}
	}

	@Override
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		// TODO Auto-generated method stub
		super.prepareUIContext(uiContext, e);

		if (null != uiContext) {
			uiContext.put("isSuperEditting", isSuperEditting);
			uiContext.put("isEditting", isEditting);
		}
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * output actionEdit_actionPerformed
	 */
	/**
	 * 描述：
	 * 
	 * @param e
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#actionEdit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBill(getBillStateForEditOrRemove(), "actionEdit");
		super.actionEdit_actionPerformed(e);
	}
	
	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (checkBill(getBillStateForEditOrRemove(), "actionRemove")) {
			// 检查是否存在下游单据
			checkBotp();

			super.actionRemove_actionPerformed(e);
		}
	}

	/**
	 * 
	 * 描述：单据可修改、删除的状态
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	protected String[] getBillStateForEditOrRemove() {
		return new String[] { FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE };
	}

	/**
	 * 
	 * 描述：检查单据状态
	 * 
	 * @param states
	 *            状态
	 * @param scene
	 *            场景
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-7-27
	 *               <p>
	 */
	protected boolean checkBill(String[] states, String scene) throws Exception {

		// 检查是否选中行
		checkSelected();

		// 需要检查的表格table
		KDTable table = getBillListTable();

		// 获取需要检查的单据ID
		List idList = FDCClientHelper.getSelectedIdValues(table, getKeyFieldName());
		if (idList == null || idList.size() == 0) {
			return false;
		}
		Set idSet = FDCClientHelper.listToSet(idList);

		// 符合要求的单据状态
		Set stateSet = FDCClientHelper.getSetByArray(states);

		// 取单据
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		/** 有更多的业务检查，请重载getCheckBillSelector */
		view.getSelector().addObjectCollection(getCheckBillSelector());

		CoreBaseCollection coll = getBizInterface().getCollection(view);

		// 针对每一个单据进行检查
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientHelper.checkBillInWorkflow(this, element.getId().toString());

			// 单据状态不符合操作
			String state = element.getString(getBillStatePropertyName());
			if (!stateSet.contains(state) && state != null) {
				/** 不同的提示,有其它action提示，请重载此方法 */
				String stateAlia = FDCBillStateEnum.getEnum(state).getAlias();
				showCheckMsg(scene, element, stateAlia);
				abort();
			}

			// 业务检查，不同的单据请重载此方法
			checkRef(scene, element);

		}

		return true;
	}

	/**
	 * 返回单据检查需要的selector
	 * 
	 * @return
	 */
	protected SelectorItemCollection getCheckBillSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();

    	sic.add("id");
		sic.add("number");
		sic.add("state");

		return sic;
    }
    
	/**
	 * 1:单据状态不符合操作 描述：检查提示
	 */
	protected void showCheckMsg(String scene, CoreBillBaseInfo element, String state) throws Exception {
		if (scene.equals("actionRemove")) {
			MsgBox.showWarning(this, "单据" + element.getNumber() + "处于" + state + "状态，不能删除");
		} else if (scene.equals("actionEdit")) {
			MsgBox.showWarning(this, "单据" + element.getNumber() + "处于" + state + "状态，不能修改");
		} else if (scene.equals("actionAudit")) {
			MsgBox.showWarning(this, "单据" + element.getNumber() + "处于" + state + "状态，不能审核");
		} else if (scene.equals("actionUnAudit")) {
			MsgBox.showWarning(this, "单据" + element.getNumber() + "处于" + state + "状态，不能反审核");
		} else if (scene.equals("actionRevise")) {
			MsgBox.showWarning(this, "单据" + element.getNumber() + "处于" + state + "状态，不能签订");
		}

	}

	/**
	 * 
	 * 描述：检查是否有关联对象(删除前调用)
	 * 
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
    protected void checkRef(String scene, CoreBillBaseInfo element) throws Exception {

		ItemAction action = null;
		if (scene.equals("actionEdit")) {
			action = actionEdit;
		} else if (scene.equals("actionAudit")) {
			action = actionAudit;
		} else if (scene.equals("actionUnAudit")) {
			action = actionUnAudit;
		} else if (scene.equals("actionRemove")) {
			action = actionRemove;
		}
		// else if(scene.equals("actionAddNew") ){
		// action = actionAddNew ;
		// }

		// 检察权限
		OrgUnitInfo orgUnit = ((FDCBillInfo) element).getOrgUnit();
		if (orgUnit != null && action != null) {
			String orgUnitId = orgUnit.getId().toString();
			PermissionHelper.checkFunctionPermission(getUserPk(), new ObjectUuidPK(orgUnitId), getMetaDataPK(), action);
		}
    }
    
    /**
	 * 审核
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// 检查选中的行的单据状态是否适合操作
		checkBill(new String[] { getStateForAudit() }, "actionAudit");

		if (MsgBox.showConfirm2(this, "您确认要审核吗？") == 0) {
			// 审核
			audit(FDCClientHelper.getSelectedIdValues(getBillListTable(), getKeyFieldName()));

			// 完成后提示
			showOKMsgAndRefresh();
		}
	}

	/**
	 * output actionUnAudit_actionPerformed method
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// 检查选中的行的单据状态是否适合操作
		checkBill(new String[] { getStateForUnAudit() }, "actionUnAudit");

		// 检查是否存在下游单据
		if (needCheckBotp()) {
			checkBotp();
		}
		if (MsgBox.showConfirm2(this, "您确认要反审核吗？") == 0) {
			// 反审核
			unAudit(FDCClientHelper.getSelectedIdValues(getBillListTable(), getKeyFieldName()));

			// 提示
			showOKMsgAndRefresh();
		}
	}

	/**
	 * 
	 * 描述：审核操作的单据前置状态
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}

	/**
	 * 
	 * 描述：反审核操作的单据前置状态
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}

	// 批量审核
	protected void audit(List ids) throws Exception {

		for (int i = 0; i < ids.size(); i++) {
			this.requestObjectLock((String) ids.get(i));
		}

		((IFDCBill) getBillInterface()).audit(ids);
	}

	// 批量反审核
	protected void unAudit(List ids) throws Exception {
		((IFDCBill) getBillInterface()).unAudit(ids);
	}

	/**
	 * 
	 * 描述：提示操作成功
	 * 
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected void showOKMsgAndRefresh() throws Exception {
		FDCClientHelper.showOprtOK(this);
		refreshList();
	}

	protected void checkBotp() throws BOSException {
		String id = getSelectedKeyValue();
		if (BTPManagerFactory.getRemoteInstance().ifHaveDestBills(id)) {
			MsgBox.showInfo("该单据已关联生成下游单据，不能删除或者反审核！");
			SysUtil.abort();
		}
	}

	protected boolean needCheckBotp() {
		return true;
	}
	
	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	@Override
	public void actionExportBillSql_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionExportBillSql_actionPerformed(e);

		// 导出Ksql
		exportSql(getExportBillTableName());
	}

	@Override
	public void actionExportEntrySql_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionExportEntrySql_actionPerformed(e);

		// 导出Ksql
		exportSql(getExportEntryTableName());
	}

	@Override
	public void actionExportItemSql_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionExportItemSql_actionPerformed(e);

		// 导出Ksql
		exportSql(getExportItemTableName());
	}

	@Override
	public void actionExportSql_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionExportSql_actionPerformed(e);

		String tableName = JOptionPane.showInputDialog(this, "请输入数据库表名(必录)");
		if (FdcStringUtil.isBlank(tableName)) {
			SysUtil.abort();
		}

		String filerFrag = JOptionPane.showInputDialog(this, "请输入过滤条件(非必录)");
		if (FdcStringUtil.isBlank(filerFrag)) {
			// SysUtil.abort();
		}

		String orderByFiledName = JOptionPane.showInputDialog(this, "请输入排序字段名(非必录)");
		if (FdcStringUtil.isBlank(orderByFiledName)) {
			// SysUtil.abort();
		}

		String sql = null;
		String whereFrag = null;
		String orderByFrag = null;

		sql = " SELECT bill.* FROM " + tableName + " bill ";
		if (FdcStringUtil.isNotBlank(filerFrag)) {
			whereFrag = " WHERE " + filerFrag;
			sql += whereFrag;
		}
		if (FdcStringUtil.isNotBlank(orderByFiledName)) {
			orderByFrag = " ORDER BY " + orderByFiledName;
			sql += orderByFrag;
		}

		// 导出Ksql
		FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
	}

	/**
	 * 导出Ksql
	 * 
	 * @param tableName
	 *            数据表名；不能为空
	 * @throws EASBizException
	 */
	public void exportSql(String tableName) throws EASBizException {
		if (FdcStringUtil.isBlank(tableName)) {
			FDCUtils.throwEASBizException("入口参数tableName(数据表名)不能为空");
		}

		// //////////////////////////////////////////////////////////////////

		String[] idArr = null;

		int choice = MsgBox.showConfirm2New(this, "是否全部导出?");
		if (!(MsgBox.isYes(choice) || MsgBox.isOk(choice))) {
			List<String> selectList = getSelectedIdValues(getMainTable());

			if (FdcCollectionUtil.isNotEmpty(selectList)) {
				idArr = selectList.toArray(new String[0]);
			}
		}

		String sql = null;
		String whereFrag = null;

		// //////////////////////////////////////////////////////////////////
		// 1、项目指标
		// //////////////////////////////////////////////////////////////////

		// 单据
		if ("T_FDC_ProjectTargetBill".equalsIgnoreCase(tableName)) {
			sql = " SELECT bill.* FROM " + tableName + " bill ";
			whereFrag = FdcSqlUtil.generateInFrag("bill.FID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// 分录
		else if ("T_FDC_ProjectTargetEntry".equalsIgnoreCase(tableName)) {
			sql = " SELECT entry.* FROM " + tableName
					+ " entry INNER JOIN T_FDC_ProjectTargetBill bill ON bill.FID = entry.FBillID ";
			whereFrag = FdcSqlUtil.generateInFrag("entry.FBillID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime, entry.FTargetGroup, entry.FSeqNum ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// // 细目
		// else if ("T_FDC_ProjectTargetItem".equalsIgnoreCase(tableName)) {
		// sql = " SELECT item.* FROM " + tableName
		// + " item INNER JOIN T_FDC_ProjectTargetEntry entry ON entry.FID = item.FParentID "
		// + " INNER JOIN T_FDC_ProjectTargetBill bill ON bill.FID = item.FBillID ";
		// whereFrag = FdcSqlUtil.generateInFrag("item.FBillID", idArr, "WHERE");
		// sql += whereFrag;
		// sql += " ORDER BY bill.FCreateTime, entry.FSeqNum, item.FSeqNum ";
		//
		// FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		// }

		// //////////////////////////////////////////////////////////////////
		// 2、项目指标展示
		// //////////////////////////////////////////////////////////////////

		// 单据
		if ("T_FDC_ProjectTargetSBill".equalsIgnoreCase(tableName)) {
			sql = " SELECT bill.* FROM " + tableName + " bill ";
			whereFrag = FdcSqlUtil.generateInFrag("bill.FID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// 分录
		else if ("T_FDC_ProjectTargetSEntry".equalsIgnoreCase(tableName)) {
			sql = " SELECT entry.* FROM " + tableName
					+ " entry INNER JOIN T_FDC_ProjectTargetSBill bill ON bill.FID = entry.FBillID ";
			whereFrag = FdcSqlUtil.generateInFrag("entry.FBillID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime, entry.FSeqNum ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// 细目
		else if ("T_FDC_ProjectTargetSItem".equalsIgnoreCase(tableName)) {
			sql = " SELECT item.* FROM " + tableName
					+ " item INNER JOIN T_FDC_ProjectTargetSEntry entry ON entry.FID = item.FParentID "
					+ " INNER JOIN T_FDC_ProjectTargetSBill bill ON bill.FID = item.FBillID ";
			whereFrag = FdcSqlUtil.generateInFrag("item.FBillID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime, entry.FSeqNum, item.FSeqNum ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		
		// //////////////////////////////////////////////////////////////////
		// 3、项目指标值
		// //////////////////////////////////////////////////////////////////

		// 单据
		if ("T_FDC_ProjectTargetVBill".equalsIgnoreCase(tableName)) {
			sql = " SELECT bill.* FROM " + tableName + " bill ";
			whereFrag = FdcSqlUtil.generateInFrag("bill.FID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// 分录
		else if ("T_FDC_ProjectTargetVEntry".equalsIgnoreCase(tableName)) {
			sql = " SELECT entry.* FROM " + tableName
					+ " entry INNER JOIN T_FDC_ProjectTargetVBill bill ON bill.FID = entry.FBillID ";
			whereFrag = FdcSqlUtil.generateInFrag("entry.FBillID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime, entry.FTargetGroup, entry.FSeqNum ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// // 细目
		// else if ("T_FDC_ProjectTargetVItem".equalsIgnoreCase(tableName)) {
		// sql = " SELECT item.* FROM " + tableName
		// + " item INNER JOIN T_FDC_ProjectTargetVEntry entry ON entry.FID = item.FParentID "
		// + " INNER JOIN T_FDC_ProjectTargetVBill bill ON bill.FID = item.FBillID ";
		// whereFrag = FdcSqlUtil.generateInFrag("item.FBillID", idArr, "WHERE");
		// sql += whereFrag;
		// sql += " ORDER BY bill.FCreateTime, entry.FSeqNum, item.FSeqNum ";
		//
		// FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		// }

		// //////////////////////////////////////////////////////////////////
		// 4、全局指标
		// //////////////////////////////////////////////////////////////////

		// 单据
		if ("T_FDC_GlobalTargetBill".equalsIgnoreCase(tableName)) {
			sql = " SELECT bill.* FROM " + tableName + " bill ";
			whereFrag = FdcSqlUtil.generateInFrag("bill.FID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// 分录
		else if ("T_FDC_GlobalTargetEntry".equalsIgnoreCase(tableName)) {
			sql = " SELECT entry.* FROM " + tableName
					+ " entry INNER JOIN T_FDC_GlobalTargetBill bill ON bill.FID = entry.FBillID ";
			whereFrag = FdcSqlUtil.generateInFrag("entry.FBillID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime, entry.FSeqNum ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// // 细目
		// else if ("T_FDC_GlobalTargetItem".equalsIgnoreCase(tableName)) {
		// sql = " SELECT item.* FROM " + tableName
		// + " item INNER JOIN T_FDC_GlobalTargetEntry entry ON entry.FID = item.FParentID "
		// + " INNER JOIN T_FDC_GlobalTargetBill bill ON bill.FID = item.FBillID ";
		// whereFrag = FdcSqlUtil.generateInFrag("item.FBillID", idArr, "WHERE");
		// sql += whereFrag;
		// sql += " ORDER BY bill.FCreateTime, entry.FSeqNum, item.FSeqNum ";
		//
		// FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		// }
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * 描述：取得导出单据SQL对应的数据库表名称
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportBillTableName() {
		return null;
	}

	/**
	 * 描述：取得导出分录SQL对应的数据库表名称
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportEntryTableName() {
		return null;
	}

	/**
	 * 描述：取得导出清单SQL对应的数据库表名称
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportItemTableName() {
		return null;
	}
	
	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * 是否忽略CU过滤
	 * 
	 * @see com.kingdee.eas.framework.client.ListUI#isIgnoreCUFilter()
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI.isIgnoreCUFilter()
	 */
	@Override
	protected boolean isIgnoreCUFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////
	
}