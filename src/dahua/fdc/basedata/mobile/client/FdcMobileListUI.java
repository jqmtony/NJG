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

	// ��ݼ�
	protected static final String ACTIONKEY_SHOW_MORE = "SHOW_MORE";
	protected static final String ACTIONKEY_LOCK = "LOCK";
	protected static final String ACTIONKEY_SUPER_LOCK = "SUPER_LOCK";

	// �Ƿ���ʾ������
	protected boolean isShowMore = false;
	// �Ƿ������ڱ༭��
	protected boolean isEditting = false;
	// �Ƿ������ڳ����༭��
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
	 * ������
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
	 * ������
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#getEditUIName()
	 */
	@Override
	protected abstract String getEditUIName();

	/**
	 * ������
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
	 * ������
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#onLoad()
	 */
	@Override
	public void onLoad() throws Exception {
		// Ĭ�ϡ���ͨ�༭��ģʽ
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
	 * ������
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

		// ע���ݼ�
		detachListeners();
		registeShortKey();
		attachListeners();

		// �������
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
	 * ע���ݼ�
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
	 * ��ж������
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
		// �Ƿ����Ա��¼
		if (!isShowMore && !FdcContextHelper.isAdminLogin(this)) {
			SysUtil.abort();
		}

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		isShowMore = !isShowMore;

		// �������
		resetComponent();
	}

	protected void lock() throws Exception {
		// �Ƿ�FDC��¼
		if (!isEditting && !FdcContextHelper.isFdcLogin(this)) {
			SysUtil.abort();
		}

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		isEditting = !isEditting;
		isSuperEditting = false;

		// �������
		resetComponent();
	}

	protected void superLock() throws Exception {
		// �Ƿ񳬼�FDC��¼
		if (!isSuperEditting && !FdcContextHelper.isSuperFdcLogin(this)) {
			SysUtil.abort();
		}

		// //////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////

		isEditting = false;
		isSuperEditting = !isSuperEditting;

		// �������
		resetComponent();
	}

	/**
	 * �������
	 */
	public void resetComponent() throws Exception {
		// /////////////////////////////////////////////////////////////////////

		FDCClientHelper.setActionEnV(actionAttachment, false);
		FDCClientHelper.setActionEnV(actionWorkFlowG, false);
		FDCClientHelper.setActionEnV(actionAuditResult, false);

		// /////////////////////////////////////////////////////////////////////

		// ֻ�г����༭״̬�£��������������롢ɾ��������
		FDCClientHelper.setActionEnV(actionAddNew, isSuperEditting);
		FDCClientHelper.setActionEnV(actionEdit, isEditting || isSuperEditting);
		FDCClientHelper.setActionEnV(actionRemove, isSuperEditting);
		// FDCClientHelper.setActionEnV(actionAudit, isSuperEditting);
		// FDCClientHelper.setActionEnV(actionUnAudit, isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		// ���ε���
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
	 * ������
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
			// ����Ƿ�������ε���
			checkBotp();

			super.actionRemove_actionPerformed(e);
		}
	}

	/**
	 * 
	 * ���������ݿ��޸ġ�ɾ����״̬
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-26
	 *               <p>
	 */
	protected String[] getBillStateForEditOrRemove() {
		return new String[] { FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE };
	}

	/**
	 * 
	 * ��������鵥��״̬
	 * 
	 * @param states
	 *            ״̬
	 * @param scene
	 *            ����
	 * @throws BOSException
	 * @author:liupd ����ʱ�䣺2006-7-27
	 *               <p>
	 */
	protected boolean checkBill(String[] states, String scene) throws Exception {

		// ����Ƿ�ѡ����
		checkSelected();

		// ��Ҫ���ı��table
		KDTable table = getBillListTable();

		// ��ȡ��Ҫ���ĵ���ID
		List idList = FDCClientHelper.getSelectedIdValues(table, getKeyFieldName());
		if (idList == null || idList.size() == 0) {
			return false;
		}
		Set idSet = FDCClientHelper.listToSet(idList);

		// ����Ҫ��ĵ���״̬
		Set stateSet = FDCClientHelper.getSetByArray(states);

		// ȡ����
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		/** �и����ҵ���飬������getCheckBillSelector */
		view.getSelector().addObjectCollection(getCheckBillSelector());

		CoreBaseCollection coll = getBizInterface().getCollection(view);

		// ���ÿһ�����ݽ��м��
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();

			// ��鵥���Ƿ��ڹ�������
			FDCClientHelper.checkBillInWorkflow(this, element.getId().toString());

			// ����״̬�����ϲ���
			String state = element.getString(getBillStatePropertyName());
			if (!stateSet.contains(state) && state != null) {
				/** ��ͬ����ʾ,������action��ʾ�������ش˷��� */
				String stateAlia = FDCBillStateEnum.getEnum(state).getAlias();
				showCheckMsg(scene, element, stateAlia);
				abort();
			}

			// ҵ���飬��ͬ�ĵ��������ش˷���
			checkRef(scene, element);

		}

		return true;
	}

	/**
	 * ���ص��ݼ����Ҫ��selector
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
	 * 1:����״̬�����ϲ��� �����������ʾ
	 */
	protected void showCheckMsg(String scene, CoreBillBaseInfo element, String state) throws Exception {
		if (scene.equals("actionRemove")) {
			MsgBox.showWarning(this, "����" + element.getNumber() + "����" + state + "״̬������ɾ��");
		} else if (scene.equals("actionEdit")) {
			MsgBox.showWarning(this, "����" + element.getNumber() + "����" + state + "״̬�������޸�");
		} else if (scene.equals("actionAudit")) {
			MsgBox.showWarning(this, "����" + element.getNumber() + "����" + state + "״̬���������");
		} else if (scene.equals("actionUnAudit")) {
			MsgBox.showWarning(this, "����" + element.getNumber() + "����" + state + "״̬�����ܷ����");
		} else if (scene.equals("actionRevise")) {
			MsgBox.showWarning(this, "����" + element.getNumber() + "����" + state + "״̬������ǩ��");
		}

	}

	/**
	 * 
	 * ����������Ƿ��й�������(ɾ��ǰ����)
	 * 
	 * @author:liupd ����ʱ�䣺2006-8-26
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

		// ���Ȩ��
		OrgUnitInfo orgUnit = ((FDCBillInfo) element).getOrgUnit();
		if (orgUnit != null && action != null) {
			String orgUnitId = orgUnit.getId().toString();
			PermissionHelper.checkFunctionPermission(getUserPk(), new ObjectUuidPK(orgUnitId), getMetaDataPK(), action);
		}
    }
    
    /**
	 * ���
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// ���ѡ�е��еĵ���״̬�Ƿ��ʺϲ���
		checkBill(new String[] { getStateForAudit() }, "actionAudit");

		if (MsgBox.showConfirm2(this, "��ȷ��Ҫ�����") == 0) {
			// ���
			audit(FDCClientHelper.getSelectedIdValues(getBillListTable(), getKeyFieldName()));

			// ��ɺ���ʾ
			showOKMsgAndRefresh();
		}
	}

	/**
	 * output actionUnAudit_actionPerformed method
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// ���ѡ�е��еĵ���״̬�Ƿ��ʺϲ���
		checkBill(new String[] { getStateForUnAudit() }, "actionUnAudit");

		// ����Ƿ�������ε���
		if (needCheckBotp()) {
			checkBotp();
		}
		if (MsgBox.showConfirm2(this, "��ȷ��Ҫ�������") == 0) {
			// �����
			unAudit(FDCClientHelper.getSelectedIdValues(getBillListTable(), getKeyFieldName()));

			// ��ʾ
			showOKMsgAndRefresh();
		}
	}

	/**
	 * 
	 * ��������˲����ĵ���ǰ��״̬
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-1
	 *               <p>
	 */
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}

	/**
	 * 
	 * ����������˲����ĵ���ǰ��״̬
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-1
	 *               <p>
	 */
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}

	// �������
	protected void audit(List ids) throws Exception {

		for (int i = 0; i < ids.size(); i++) {
			this.requestObjectLock((String) ids.get(i));
		}

		((IFDCBill) getBillInterface()).audit(ids);
	}

	// ���������
	protected void unAudit(List ids) throws Exception {
		((IFDCBill) getBillInterface()).unAudit(ids);
	}

	/**
	 * 
	 * ��������ʾ�����ɹ�
	 * 
	 * @author:liupd ����ʱ�䣺2006-8-1
	 *               <p>
	 */
	protected void showOKMsgAndRefresh() throws Exception {
		FDCClientHelper.showOprtOK(this);
		refreshList();
	}

	protected void checkBotp() throws BOSException {
		String id = getSelectedKeyValue();
		if (BTPManagerFactory.getRemoteInstance().ifHaveDestBills(id)) {
			MsgBox.showInfo("�õ����ѹ����������ε��ݣ�����ɾ�����߷���ˣ�");
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

		// ����Ksql
		exportSql(getExportBillTableName());
	}

	@Override
	public void actionExportEntrySql_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionExportEntrySql_actionPerformed(e);

		// ����Ksql
		exportSql(getExportEntryTableName());
	}

	@Override
	public void actionExportItemSql_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionExportItemSql_actionPerformed(e);

		// ����Ksql
		exportSql(getExportItemTableName());
	}

	@Override
	public void actionExportSql_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionExportSql_actionPerformed(e);

		String tableName = JOptionPane.showInputDialog(this, "���������ݿ����(��¼)");
		if (FdcStringUtil.isBlank(tableName)) {
			SysUtil.abort();
		}

		String filerFrag = JOptionPane.showInputDialog(this, "�������������(�Ǳ�¼)");
		if (FdcStringUtil.isBlank(filerFrag)) {
			// SysUtil.abort();
		}

		String orderByFiledName = JOptionPane.showInputDialog(this, "�����������ֶ���(�Ǳ�¼)");
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

		// ����Ksql
		FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
	}

	/**
	 * ����Ksql
	 * 
	 * @param tableName
	 *            ���ݱ���������Ϊ��
	 * @throws EASBizException
	 */
	public void exportSql(String tableName) throws EASBizException {
		if (FdcStringUtil.isBlank(tableName)) {
			FDCUtils.throwEASBizException("��ڲ���tableName(���ݱ���)����Ϊ��");
		}

		// //////////////////////////////////////////////////////////////////

		String[] idArr = null;

		int choice = MsgBox.showConfirm2New(this, "�Ƿ�ȫ������?");
		if (!(MsgBox.isYes(choice) || MsgBox.isOk(choice))) {
			List<String> selectList = getSelectedIdValues(getMainTable());

			if (FdcCollectionUtil.isNotEmpty(selectList)) {
				idArr = selectList.toArray(new String[0]);
			}
		}

		String sql = null;
		String whereFrag = null;

		// //////////////////////////////////////////////////////////////////
		// 1����Ŀָ��
		// //////////////////////////////////////////////////////////////////

		// ����
		if ("T_FDC_ProjectTargetBill".equalsIgnoreCase(tableName)) {
			sql = " SELECT bill.* FROM " + tableName + " bill ";
			whereFrag = FdcSqlUtil.generateInFrag("bill.FID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// ��¼
		else if ("T_FDC_ProjectTargetEntry".equalsIgnoreCase(tableName)) {
			sql = " SELECT entry.* FROM " + tableName
					+ " entry INNER JOIN T_FDC_ProjectTargetBill bill ON bill.FID = entry.FBillID ";
			whereFrag = FdcSqlUtil.generateInFrag("entry.FBillID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime, entry.FTargetGroup, entry.FSeqNum ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// // ϸĿ
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
		// 2����Ŀָ��չʾ
		// //////////////////////////////////////////////////////////////////

		// ����
		if ("T_FDC_ProjectTargetSBill".equalsIgnoreCase(tableName)) {
			sql = " SELECT bill.* FROM " + tableName + " bill ";
			whereFrag = FdcSqlUtil.generateInFrag("bill.FID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// ��¼
		else if ("T_FDC_ProjectTargetSEntry".equalsIgnoreCase(tableName)) {
			sql = " SELECT entry.* FROM " + tableName
					+ " entry INNER JOIN T_FDC_ProjectTargetSBill bill ON bill.FID = entry.FBillID ";
			whereFrag = FdcSqlUtil.generateInFrag("entry.FBillID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime, entry.FSeqNum ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// ϸĿ
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
		// 3����Ŀָ��ֵ
		// //////////////////////////////////////////////////////////////////

		// ����
		if ("T_FDC_ProjectTargetVBill".equalsIgnoreCase(tableName)) {
			sql = " SELECT bill.* FROM " + tableName + " bill ";
			whereFrag = FdcSqlUtil.generateInFrag("bill.FID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// ��¼
		else if ("T_FDC_ProjectTargetVEntry".equalsIgnoreCase(tableName)) {
			sql = " SELECT entry.* FROM " + tableName
					+ " entry INNER JOIN T_FDC_ProjectTargetVBill bill ON bill.FID = entry.FBillID ";
			whereFrag = FdcSqlUtil.generateInFrag("entry.FBillID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime, entry.FTargetGroup, entry.FSeqNum ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// // ϸĿ
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
		// 4��ȫ��ָ��
		// //////////////////////////////////////////////////////////////////

		// ����
		if ("T_FDC_GlobalTargetBill".equalsIgnoreCase(tableName)) {
			sql = " SELECT bill.* FROM " + tableName + " bill ";
			whereFrag = FdcSqlUtil.generateInFrag("bill.FID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// ��¼
		else if ("T_FDC_GlobalTargetEntry".equalsIgnoreCase(tableName)) {
			sql = " SELECT entry.* FROM " + tableName
					+ " entry INNER JOIN T_FDC_GlobalTargetBill bill ON bill.FID = entry.FBillID ";
			whereFrag = FdcSqlUtil.generateInFrag("entry.FBillID", idArr, "WHERE");
			sql += whereFrag;
			sql += " ORDER BY bill.FCreateTime, entry.FSeqNum ";

			FDCClientHelper.exportSql(this, tableName, sql, true, "FID");
		}
		// // ϸĿ
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
	 * ������ȡ�õ�������SQL��Ӧ�����ݿ������
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportBillTableName() {
		return null;
	}

	/**
	 * ������ȡ�õ�����¼SQL��Ӧ�����ݿ������
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportEntryTableName() {
		return null;
	}

	/**
	 * ������ȡ�õ����嵥SQL��Ӧ�����ݿ������
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
	 * �Ƿ����CU����
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