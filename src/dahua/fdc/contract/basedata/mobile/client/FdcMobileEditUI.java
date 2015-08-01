/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.UIFocusTraversalPolicy;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FdcContextHelper;
import com.kingdee.eas.fdc.basedata.mobile.FdcMobileBillInfo;
import com.kingdee.eas.fdc.basedata.mobile.FdcMobileEntryCollection;
import com.kingdee.eas.fdc.basedata.mobile.FdcMobileEntryInfo;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryCollection;
import com.kingdee.eas.fdc.basedata.mobile.ProjectTargetEntryFactory;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class FdcMobileEditUI extends AbstractFdcMobileEditUI {
	private static final Logger logger = CoreUIObject.getLogger(FdcMobileEditUI.class);

	// 快捷键
	protected static final String ACTIONKEY_SHOW_MORE = "SHOW_MORE";
	protected static final String ACTIONKEY_LOCK = "LOCK";
	protected static final String ACTIONKEY_SUPER_LOCK = "SUPER_LOCK";

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// 是否显示更多列
	protected boolean isShowMore = false;
	// 是否正处于编辑中
	protected boolean isEditting = false;
	// 是否正处于超级编辑中
	protected boolean isSuperEditting = false;

	// 控件数据变化监听Map
	protected Map<String, Object> dataChangeListenerMap = null;

	// 服务器端时间
	protected Date serverDate = null;

	// 默认失效时间(2100-1-1)
	protected Date defaultEndDate = new Date(4102416000000L);

	// 分录表格当前行
	protected int curEntryIndex = -1;

	// 分录集合
	protected FdcMobileEntryCollection entryClos;

	// 保存分录id的Map：其中key存在新分录id，value对应的行索引
	protected Map<String, Integer> entryIdIndexMap = new LinkedHashMap<String, Integer>();

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 排序管理器Map
	 */
	protected Map<KDTable, KDTSortManager> sortManagerMap = new LinkedHashMap<KDTable, KDTSortManager>();
	// protected KDTSortManager entrySorts = new KDTSortManager(getDetailTable());

	/**
	 * 分录编辑适配器
	 */
	protected EntryEditAdapter entryEditLs = new EntryEditAdapter();

	/**
	 * 表格列表
	 */
	private List<KDTable> tableList;

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * output class constructor
	 */
	public FdcMobileEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 加载数据到界面
	 */
	public void loadFields() {
		// 注销监听器
		detachListeners();

		super.loadFields();

		// 保存旧的数据
		editData.put("oldFdcMobileInfo", this.editData.clone());

		// 注册监听器
		registeShortKey();

		// 最后加上监听器
		attachListeners();
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	@Override
	protected abstract ICoreBase getBizInterface() throws Exception;

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return this.kdtEntries;
	}

	@Override
	protected abstract KDTextField getNumberCtrl();

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：
	 * 
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#handleCodingRule()
	 */
	@Override
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		// TODO Auto-generated method stub
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
	}

	/**
	 * 获取初始化参数
	 * 
	 * @return
	 * @throws Exception
	 */
	protected void fetchInitParam() throws Exception {
		// 这里只获取集团参数
		super.fetchInitParam();
	}

	/**
	 * 获取初始化界面数据
	 * 
	 * @throws Exception
	 */
	protected void fetchInitData() throws Exception {
		// // 编辑、查看打开时，直接传入了单据ID
		// String id = null;
		// if (getUIContext().get("ID") instanceof String) {
		// id = (String) getUIContext().get("ID");
		// } else if (getUIContext().get("ID") instanceof BOSUuid) {
		// id = ((BOSUuid) getUIContext().get("ID")).toString();
		// }
		//
		// Map map = new HashMap();
		// map.put("ID", id);
		// // 获取初始数据
		// Map<String, Object> initData = ((IFdcMobileBill)
		// getBizInterface()).fetchInitData(map);
		// dealInitData(initData);

		super.fetchInitData();

		isEditting = (Boolean) getUIContext().get("isEditting");
		isSuperEditting = (Boolean) getUIContext().get("isSuperEditting");
		// 查看进入
		if (OprtState.VIEW.equals(getOprtState())) {
			isEditting = false;
			isSuperEditting = false;
		}

	}

	// 处理服务端取回的参数
	protected void dealInitData(Map<String, Object> initData) throws Exception {
		if (initData != null) {
			// 取服务器端时间
			serverDate = (Date) initData.get("serverDate");
		} else {
			serverDate = new Timestamp(System.currentTimeMillis());
		}
	}

	/**
	 * 表头设置
	 * 
	 * @throws Exception
	 */
	protected void initHeadStyle() throws Exception {
	}

	/**
	 * 表体设置
	 * 
	 * @throws Exception
	 */
	protected void initTableStyle() throws Exception {
		List<KDTable> tableList = getTableList();

		for (KDTable table : tableList) {
			if (null != table) {
				table.checkParsed();

				initTableStyle(table);
			}
		}
	}

	/**
	 * 描述：表体设置
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected void initTableStyle(KDTable table) {

	}

	protected void initWorkButton() {
		super.initWorkButton();

		// /////////////////////////////////////////////////////////////////////

		// FDCClientHelper.setActionEnV(actionAddNew, false);
		FDCClientHelper.setActionEnV(actionEdit, false);
		// FDCClientHelper.setActionEnV(actionSave, false);
		// FDCClientHelper.setActionEnV(actionSubmit, false);
		// FDCClientHelper.setActionEnV(actionRemove, false);

		FDCClientHelper.setActionEnV(actionAddNew, false);
		FDCClientHelper.setActionEnV(actionSave, false);
		FDCClientHelper.setActionEnV(actionRemove, false);

		FDCClientHelper.setActionEnV(actionSave, false);
		FDCClientHelper.setActionEnV(actionCopy, false);
		FDCClientHelper.setActionEnV(actionPrint, false);
		FDCClientHelper.setActionEnV(actionPrintPreview, false);
		FDCClientHelper.setActionEnV(actionCancel, false);
		FDCClientHelper.setActionEnV(actionCancelCancel, false);

		FDCClientHelper.setActionEnV(actionAttachment, false);
		FDCClientHelper.setActionEnV(actionFirst, false);
		FDCClientHelper.setActionEnV(actionPre, false);
		FDCClientHelper.setActionEnV(actionNext, false);
		FDCClientHelper.setActionEnV(actionLast, false);

		FDCClientHelper.setActionEnV(actionTraceUp, false);
		FDCClientHelper.setActionEnV(actionTraceDown, false);
		FDCClientHelper.setActionEnV(actionCreateFrom, false);
		FDCClientHelper.setActionEnV(actionCreateTo, false);

		FDCClientHelper.setActionEnV(actionAudit, false);
		FDCClientHelper.setActionEnV(actionUnAudit, false);
		FDCClientHelper.setActionEnV(actionAuditResult, false);
		FDCClientHelper.setActionEnV(actionMultiapprove, false);
		FDCClientHelper.setActionEnV(actionWorkFlowG, false);
		FDCClientHelper.setActionEnV(actionWorkflowList, false);
		FDCClientHelper.setActionEnV(actionNextPerson, false);

		// /////////////////////////////////////////////////////////////////////

		FDCClientHelper.setActionEnV(actionAddLine, false);
		FDCClientHelper.setActionEnV(actionInsertLine, false);
		FDCClientHelper.setActionEnV(actionRemoveLine, false);

		// /////////////////////////////////////////////////////////////////////

		btnAddEntry.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnInsertEntry.setIcon(EASResource.getIcon("imgTbtn_insert"));
		btnRemoveEntry.setIcon(EASResource.getIcon("imgTbtn_deleteline"));

		// /////////////////////////////////////////////////////////////////////

		remove(btnAddEntry);
		remove(btnInsertEntry);
		remove(btnRemoveEntry);

		if (isSuperEditting) {
			containerMain.addButton(btnAddEntry);
			containerMain.addButton(btnInsertEntry);
			containerMain.addButton(btnRemoveEntry);
		}

		// /////////////////////////////////////////////////////////////////////
	}

	protected void setButtonStatus() {
		super.setButtonStatus();

		// /////////////////////////////////////////////////////////////////////

		FDCClientHelper.setActionEnV(actionEdit, false);

		// /////////////////////////////////////////////////////////////////////

		FDCClientHelper.setActionEnV(actionAudit, false);
		FDCClientHelper.setActionEnV(actionUnAudit, false);
		FDCClientHelper.setActionEnV(actionAuditResult, false);
		FDCClientHelper.setActionEnV(actionMultiapprove, false);

		// /////////////////////////////////////////////////////////////////////

		// 只有超级编辑状态下，才能新增、插入、删除
		actionAddEntry.setVisible(isSuperEditting);
		actionInsertEntry.setVisible(isSuperEditting);
		actionRemoveEntry.setVisible(isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		// 重设组件
		resetComponent();
	}

	/**
	 * 
	 * 描述：避免在新增单据（未作修改）直接关闭时，出现保存提示
	 * 
	 */
	protected void handleOldData() {
		if (!STATUS_VIEW.equals(getOprtState())) {
			storeFields();
			initOldData(this.editData);
		}
	}

	/**
	 * 加载后
	 * 
	 * @throws Exception
	 */
	protected void afterOnLoad() throws Exception {
		// //////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////

		// 重设组件
		resetComponent();

		// //////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 取得主表格
	 * 
	 * @return
	 */
	public KDTable getMainTable() {
		return getDetailTable();
	}

	/**
	 * 取得分录表格
	 * 
	 * @return
	 */
	public KDTable getEntryTable() {
		return getDetailTable();
	}

	/**
	 * 获取当前选择行的主键
	 * 
	 * @return 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
	 */
	protected String getSelectedKeyValue(KDTable table) {
		String key = null;

		Object obj = FDCTableHelper.getSelectCellValue(this, table, getKeyFieldName());
		if (null != obj) {
			key = obj.toString();
		}

		return key;
	}

	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 * @return query中的主键列名称
	 */
	protected String getKeyFieldName() {
		return "id";
	}

	/**
	 * 格式化表格精度
	 * 
	 * @param table
	 */
	protected void formatTableScale(KDTable table) {
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	/**
	 * 检查是否保存过或修改过
	 */
	protected void checkIsSaveOrModify() {
		if (null == editData || null == editData.getId() || isModify()) {
			MsgBox.showWarning(this, "单据未保存，请先保存");
			SysUtil.abort();
		}
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

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
					FdcMobileEditUI.this.handleException(e);
				}
			}
			if (ACTIONKEY_LOCK.equals(shortcut)) {
				try {
					lock();
				} catch (Exception e) {
					e.printStackTrace();
					FdcMobileEditUI.this.handleException(e);
				}
			}
			if (ACTIONKEY_SUPER_LOCK.equals(shortcut)) {
				try {
					superLock();
				} catch (Exception e) {
					e.printStackTrace();
					FdcMobileEditUI.this.handleException(e);
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

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	protected void attachListeners() {
		ActionMap entryActionMap = this.getActionMap();
		entryActionMap.put(ACTIONKEY_SHOW_MORE, this.showMoreListener);
		entryActionMap.put(ACTIONKEY_LOCK, this.lockListener);
		entryActionMap.put(ACTIONKEY_SUPER_LOCK, this.superLockListener);

		// ////////////////////////////////////////////////////////////////

		List<KDTable> tableList = getTableList();

		for (KDTable table : tableList) {
			if (null != table) {
				attachListeners(table);
			}
		}
	}

	/**
	 * 描述：
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected void attachListeners(KDTable table) {
		table.addKDTEditListener(entryEditLs);
		table.addKDTMouseListener(getSortManager(table));
	}

	/**
	 * 拆卸监听器
	 */
	protected void detachListeners() {
		ActionMap entryActionMap = this.getActionMap();
		entryActionMap.put(ACTIONKEY_SHOW_MORE, null);
		entryActionMap.put(ACTIONKEY_LOCK, null);
		entryActionMap.put(ACTIONKEY_SUPER_LOCK, null);

		// ////////////////////////////////////////////////////////////////

		List<KDTable> tableList = getTableList();

		for (KDTable table : tableList) {
			if (null != table) {
				detachListeners(table);
			}
		}
	}

	/**
	 * 描述：
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected void detachListeners(KDTable table) {
		table.removeKDTEditListener(entryEditLs);
		table.removeKDTMouseListener(getSortManager(table));
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// 分录表格编辑事件
	protected class EntryEditAdapter extends KDTEditAdapter {
		public void editStopped(KDTEditEvent evt) {
			try {
				tblEntry_editStopped(evt);
			} catch (Exception e) {
				e.printStackTrace();
				FdcMobileEditUI.this.handleException(e);
			}
		}

		public void editValueChanged(KDTEditEvent evt) {
			try {
				tblEntry_editValueChanged(evt);
			} catch (Exception e) {
				e.printStackTrace();
				FdcMobileEditUI.this.handleException(e);
			}
		}
	}

	// 分录表格编辑事件
	protected void tblEntry_editStopped(KDTEditEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();

		if (FDCUtils.isEqual(oldValue, newValue)) {
			return;
		}

		KDTable tblDetail = (KDTable) e.getSource();
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();

		String key = tblDetail.getColumnKey(colIndex);
		afterEntryEditStopped(tblDetail, oldValue, newValue, colIndex, rowIndex);
	}

	protected void tblEntry_editValueChanged(KDTEditEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();

		if (FDCUtils.isEqual(oldValue, newValue)) {
			return;
		}

		KDTable tblDetail = (KDTable) e.getSource();
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();

		String key = tblDetail.getColumnKey(colIndex);
		afterEntryEditChanged(tblDetail, oldValue, newValue, colIndex, rowIndex);
	}
	
	/**
	 * 分录表格编辑事件
	 * 
	 * @throws Exception
	 */
	protected void afterEntryEditStopped(KDTable table, Object oldValue, Object newValue, int colIndex, int rowIndex)
			throws Exception {
	}
	
	/**
	 * 分录表格编辑事件
	 * 
	 * @throws Exception
	 */
	protected void afterEntryEditChanged(KDTable table, Object oldValue, Object newValue, int colIndex, int rowIndex)
			throws Exception {
	}


	/**
	 * output tblEntry_tableSelectChanged method
	 */
	protected void tblEntry_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		KDTable tblEntry = (KDTable) e.getSource();

		if (tblEntry.getSelectManager().size() > 0) {
			int top = tblEntry.getSelectManager().get().getTop();

			if (isTableColumnSelected(tblEntry)) {
				return;
			}

			if (tblEntry.getRow(top) == null) {
				return;
			}
			IObjectValue detailData = (IObjectValue) tblEntry.getRow(top).getUserObject();
			beforeSelectEntry(tblEntry, detailData);
			setRow(top);

			afterSelectEntry(tblEntry, detailData);
		}
	}

	/**
	 * 描述：设置当前行
	 */
	protected void setRow(int line) {
		this.curEntryIndex = line;
	}

	@Override
	protected void kdtEntries_tableSelectChanged(KDTSelectEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.kdtEntries_tableSelectChanged(e);

		tblEntry_tableSelectChanged(e);
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	protected void showMore() throws Exception {
		if (true) {
			FDCUtils.throwEASBizException("功能未完成");
		}

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
		// 先要保存
		if (isEditting || isSuperEditting) {
			checkIsSaveOrModify();
		}

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

		if (isEditting) {
			actionEdit_actionPerformed(null);
		} else {
			this.setOprtState(STATUS_VIEW);
		}
	}

	protected void superLock() throws Exception {
		// 先要保存
		if (isEditting || isSuperEditting) {
			checkIsSaveOrModify();
		}

		// 是否超级EC登录
		if (!isSuperEditting && !FdcContextHelper.isSuperFdcLogin(this)) {
			SysUtil.abort();
		}

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		isEditting = false;
		isSuperEditting = !isSuperEditting;

		// 重设组件
		resetComponent();

		if (isSuperEditting) {
			actionEdit_actionPerformed(null);
		} else {
			this.setOprtState(STATUS_VIEW);
		}
	}

	/**
	 * 重设组件
	 */
	public void resetComponent() {
		boolean flag = isEditting || isSuperEditting;

		// /////////////////////////////////////////////////////////////////////

		FDCClientHelper.setActionEnV(actionEdit, flag);
		FDCClientHelper.setActionEnV(actionSubmit, flag);

		FDCClientHelper.setActionEnV(actionAudit, false);
		FDCClientHelper.setActionEnV(actionUnAudit, false);
		FDCClientHelper.setActionEnV(actionAuditResult, false);
		FDCClientHelper.setActionEnV(actionMultiapprove, false);

		// /////////////////////////////////////////////////////////////////////

		containerMain.removeAllButton();

		// 只有(超级)编辑状态下，才能新增、插入、删除
		if (flag) {
			containerMain.addButton(btnAddEntry);
			// containerMain.addButton(btnInsertEntry);
			containerMain.addButton(btnRemoveEntry);

			actionAddEntry.setVisible(true);
			// actionInsertEntry.setVisible(true);
			actionRemoveEntry.setVisible(true);
		}

		// /////////////////////////////////////////////////////////////////////

		actionAddEntry.setEnabled(flag);
		// actionInsertEntry.setEnabled(isSuperEditting);
		actionRemoveEntry.setEnabled(flag);

		// /////////////////////////////////////////////////////////////////////

		if (getOprtState().equals(OprtState.VIEW)) {
			List<KDTable> tableList = getTableList();

			for (KDTable table : tableList) {
				if (null != table) {
					table.getStyleAttributes().setLocked(true);
				}
			}
		}

	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	protected KDTable getSelectedTable() {
		return getDetailTable();
	}

	/**
	 * output actionAddEntry_actionPerformed method
	 */
	public void actionAddEntry_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = getSelectedTable();

		if (null != table) {
			addEntry(table);
			appendFootRow(table);
		}
	}

	/**
	 * output actionInsertEntry_actionPerformed method
	 */
	public void actionInsertEntry_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = getSelectedTable();

		if (null != table) {
			insertEntry(table);
			appendFootRow(table);
		}
	}

	/**
	 * output actionRemoveEntry_actionPerformed method
	 */
	public void actionRemoveEntry_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = getSelectedTable();

		if (null != table) {
			actionRemoveEntry_actionPerformed(table);
		}
	}

	/**
	 * 描述：
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	private void actionRemoveEntry_actionPerformed(KDTable table) {
		removeEntry(table);
		appendFootRow(table);
		// 实现删除后的焦点策略 2007-10-09 haiti_yang
		if (table.getRowCount() == 0) {
			FocusTraversalPolicy policy = null;
			Container container = null;
			Component initComponent = null;
			if (this.getFocusTraversalPolicy() != null
					&& this.getFocusTraversalPolicy() instanceof UIFocusTraversalPolicy) {
				policy = this.getFocusTraversalPolicy();
				container = this;
				Component[] traverComponent = ((UIFocusTraversalPolicy) policy).getComponents();
				for (int i = 0; i < traverComponent.length; i++) {
					if (traverComponent[i] == table) {
						initComponent = traverComponent[i];
						break;
					}
				}
				if (initComponent == null) {
					initComponent = policy.getLastComponent(container);
					initComponent.requestFocusInWindow();
				} else if (initComponent != null) {
					Component component = policy.getComponentBefore(container, initComponent);
					while ((component instanceof IKDTextComponent) == false || component.isEnabled() == false) {
						component = policy.getComponentBefore(container, component);
					}
					component.requestFocusInWindow();
				}
			} else if (policy == null) {
				if (this.getUIWindow() instanceof Dialog) {
					policy = ((Dialog) uiWindow).getFocusTraversalPolicy();
					container = (Dialog) uiWindow;
				} else if (this.getUIWindow() instanceof Window) {
					policy = ((Window) uiWindow).getFocusTraversalPolicy();
					container = (Window) uiWindow;
				}

				if (policy != null) {
					try {
						Component component = policy.getComponentBefore(container, table);
						while ((component instanceof IKDTextComponent) == false || component.isEnabled() == false) {
							component = policy.getComponentBefore(container, component);
						}
						component.requestFocusInWindow();
					} catch (Exception ex) {
					}
				}
			}
		}
	}

	/**
	 * 在指定表格中新增行（新增到最后一行）
	 * 
	 * @param table
	 */
	protected void addEntry(KDTable table) {
		if (table == null) {
			return;
		}
		beforeAddEntry(table);
		IObjectValue detailData = createNewEntryData(table);
		if (detailData != null) {

			IRow row = table.addRow();
			// getUILifeCycleHandler().fireOnAddNewLine(table,detailData);
			loadEntryFields(table, row, detailData);
			// afterAddEntry(table, detailData);
		}
	}

	/**
	 * 在指定表格中插入行（在当前选中行前插入，如果当前未选中任何行的话，则新增到最后一行）
	 * 
	 * @param table
	 */
	protected void insertEntry(KDTable table) {
		if (table == null) {
			return;
		}
		beforeInsertEntry(table);
		IObjectValue detailData = createNewEntryData(table);
		IRow row = null;

		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();

			if (isTableColumnSelected(table)) {
				row = table.addRow();
			} else {
				row = table.addRow(top);
			}
		} else {
			row = table.addRow();
		}

		// getUILifeCycleHandler().fireOnAddNewLine(table,detailData);
		loadEntryFields(table, row, detailData);
		// afterInsertEntry(table, detailData);
	}

	/**
	 * 在指定表格中删除当前选中行 增加隔行删除功能 2007-03-12
	 * 
	 * @param table
	 */
	protected void removeEntry(KDTable table) {
		if (table == null) {
			return;
		}

		beforeRemoveEntry(table);
		if ((table.getSelectManager().size() == 0))
		// || isTableColumnSelected(table))
		{
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));

			return;
		}

		// [begin]进行删除分录的提示处理。
		if (confirmRemove()) {
			// 获取选择块的总个数
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			// 因为先择块的顺序可能并非是表中行的顺序，所以先要排序使选择块的顺序正好是由小到大
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null)
				return;
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
				table.removeRow(rowIndex);
				IObjectCollection collection = (IObjectCollection) table.getUserObject();

				if (collection == null) {
					logger.error("collection not be binded to table");
				} else {
					// Modify By Jacky Zhang
					if (detailData != null) {
						int index = getCollectionIndex(collection, detailData);
						// 避免有合计行的分录处理。
						if (index >= 0 && collection.size() > index) {
							collection.removeObject(index);
						}
					}
				}
				afterRemoveEntry(table, detailData);
			}

			// 如果现在有记录定位到第一行
			if (table.getRow(0) != null) {
				// table.getSelectManager().select(0, 0);

				FDCTableHelper.selectRow(table, 0);
				setRow(0);

				IObjectValue detailData = (IObjectValue) table.getRow(0).getUserObject();
				afterSelectEntry(table, detailData);
			}
		}
	}

	/**
	 * 增加单据行前的相关处理
	 */
	protected void beforeAddEntry(KDTable table) {
	}

	/**
	 * 插入单据行前的相关处理
	 */
	protected void beforeInsertEntry(KDTable table) {
	}

	/**
	 * 删除单据行前的相关处理
	 */
	protected void beforeRemoveEntry(KDTable table) {
	}

	/**
	 * 增加单据行后的相关处理
	 */
	protected void afterAddEntry(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 插入单据行后的相关处理
	 */
	protected void afterInsertEntry(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 删除单据行后的相关处理
	 */
	protected void afterRemoveEntry(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 选中单据行前的相关处理
	 */

	protected void beforeSelectEntry(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 选中单据行后的相关处理
	 */
	protected void afterSelectEntry(KDTable table, IObjectValue lineData) {
	}

	/**
	 * 新建单据行，返回一个新的分录行的默认值
	 */
	protected IObjectValue createNewEntryData(KDTable table) {
		return null;
	}

	/**
	 * 显示单据行
	 */
	public void loadEntryFields(KDTable table, IRow row, IObjectValue obj) {
	}

	/**
	 * 保存单据行
	 */
	protected void storeEntryFields(KDTable table, IRow row, IObjectValue obj) {
	}

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// ////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////

	// 因为目前ObjectValue比较是按值比较，但集合中使用，如果分录值相同，
	// 都会删除找到的第一个，会出错。自行实现按指针比较。2007-2-5
	private int getCollectionIndex(IObjectCollection collection, IObjectValue obj) {
		int index = -1;
		if (collection == null) {
			return index;
		}
		for (int i = collection.size() - 1; i >= 0; i--) {
			if (obj == collection.getObject(i)) {
				index = i;
				return index;
			}
		}
		return index;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * 描述：
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#createNewData()
	 */
	@Override
	protected IObjectValue createNewData() {
		FdcMobileBillInfo newData = new FdcMobileBillInfo();

		// ////////////////////////////////////////////////////////////////

		createNewData(newData);

		// ////////////////////////////////////////////////////////////////

		return newData;
	}

	/**
	 * 描述：
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#verifyInputForSubmint()
	 */
	@Override
	protected void verifyInputForSubmint() throws Exception {
		// TODO Auto-generated method stub
		super.verifyInputForSubmint();

		KDTable table = getDetailTable();
		if (null != table) {
			// 验证表格非空
			FDCClientVerifyHelper.verifyInput(this, table);
		}
	}

	/**
	 * 描述：
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#setNumberTextEnabled()
	 */
	@Override
	protected void setNumberTextEnabled() {
		// TODO Auto-generated method stub
		super.setNumberTextEnabled();

		KDTextField numberCtrl = getNumberCtrl();
		if (numberCtrl != null) {
			numberCtrl.setEnabled(isSuperEditting);
			numberCtrl.setEditable(isSuperEditting);
		}
	}

	/**
	 * 描述：取得排序管理器
	 * 
	 * @param table
	 *            表格
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected KDTSortManager getSortManager(KDTable table) {
		KDTSortManager sortManager = sortManagerMap.get(table);
		if (null == sortManager) {
			sortManager = new KDTSortManager(table);
			sortManagerMap.put(table, sortManager);
		}

		return sortManager;
	}

	/**
	 * 描述：取得表格列表
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected final List<KDTable> getTableList() {
		if (null == tableList) {
			// 初始化表格列表
			initTableList();
		}
		if (null == tableList) {
			tableList = new ArrayList<KDTable>();
		} else {
			// 清除掉集合中的重复值和Null值
			FdcCollectionUtil.clearDuplicateAndNull(tableList);
		}

		return tableList;
	}

	/**
	 * 描述：设置表格列表
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected final void setTableList(List<KDTable> tableList) {
		this.tableList = tableList;
		
		// 清除掉集合中的重复值和Null值
		FdcCollectionUtil.clearDuplicateAndNull(this.tableList);
	}

	/**
	 * 描述：初始化表格列表
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected List<KDTable> initTableList() {
		if (null == tableList) {
			tableList = new ArrayList<KDTable>();
		}

		KDTable table = getDetailTable();
		if (null != table) {
			tableList.add(table);
		}

		return tableList;
	}

	/**
	 * 描述：加载字段后
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-31
	 */
	protected void afterLoadFields() {
		List<KDTable> tableList = getTableList();

		for (KDTable table : tableList) {
			if (null != table) {
				afterLoadFields(table);
			}
		}
	}

	/**
	 * 描述：加载字段后
	 * 
	 * @param table
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-1
	 */
	protected void afterLoadFields(KDTable table) {
	}

	/**
	 * 描述：初始化排序编码
	 * 
	 * @param seq
	 * @param info
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-13
	 */
	protected void initSeqNum(int seq, FdcMobileEntryInfo info) {
		// 编码、名称，设置默认值，不可编辑
		String createTimeFormate = DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");

		String seqNumPre = "SeqNum";
		String seqNum = seqNumPre + "-" + createTimeFormate + "-" + seq;
		String numPre = isSuperEditting ? "SystemDefine" : "UserDefine";
		String number = numPre + "-" + createTimeFormate + "-" + seq;
		info.setSeqNum(seqNum);
		info.setNumber(number);
	}

	/**
	 * 描述：获取项目指标分录
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-13
	 */
	protected ProjectTargetEntryCollection fetchProjectTargetEntrys() {
		ProjectTargetEntryCollection projectTargetEntryCollection = null;
	
		EntityViewInfo view = new EntityViewInfo();
	
		view.getSelector().add("bill.*");
		view.getSelector().add("*");
		view.getSelector().add("unit.id");
		view.getSelector().add("unit.number");
		view.getSelector().add("unit.name");
		// view.setTopCount(1);
	
		// 获得项目指标分录过滤器
		FilterInfo filter = getProjectTargetEntrysFilter();
		if (null != filter) {
			view.setFilter(filter);
		}
	
		view.getSorter().add(new SorterItemInfo("targetGroup"));
		view.getSorter().add(new SorterItemInfo("seqNum"));
	
		try {
			projectTargetEntryCollection = ProjectTargetEntryFactory.getRemoteInstance()
					.getProjectTargetEntryCollection(view);
		} catch (BOSException e) {
			this.handUIExceptionAndAbort(e);
		}
	
		if (FdcObjectCollectionUtil.isEmpty(projectTargetEntryCollection)) {
			MsgBox.showWarning(this, "请配置项目指标单据");
			SysUtil.abort();
		}
		Map propertyMap = FdcObjectCollectionUtil.parsePropertyMap(projectTargetEntryCollection, "bill.id");
		if (propertyMap.size() > 1) {
			MsgBox.showWarning(this, "只能有一条项目指标单据");
			SysUtil.abort();
		}
	
		return projectTargetEntryCollection;
	}

	/**
	 * 描述：获得项目指标分录过滤器
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-13
	 */
	protected abstract FilterInfo getProjectTargetEntrysFilter();

}