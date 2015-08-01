/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PayPlanCycleFactory;
import com.kingdee.eas.fdc.basedata.PayPlanCycleInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����ƻ����� �б����
 */
public class PayPlanCycleListUI extends AbstractPayPlanCycleListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PayPlanCycleListUI.class);

	/**
	 * output class constructor
	 */
	public PayPlanCycleListUI() throws Exception {
		super();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWWIN;
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new PayPlanCycleInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PayPlanCycleFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return PayPlanCycleEditUI.class.getName();
	}

	protected boolean isSystemDefaultData(int activeRowIndex) {
		return false;
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		treeMain_valueChanged(null);
	}

	protected void initTree() throws Exception {
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}

		String rootid = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();

		TreeModel orgTreeModel = null;
		// �����񣬼�鹤����������Ƿ��Ӧ�Ŀ��Ƶ�Ԫ��֯�����Ҹ���ƻ����ݺ�ͬ���β��Ź���
		rootid = cuInfo.getId().toString();
		orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY, "",
				rootid, null, FDCHelper.getActionPK(this.actionOnLoad));

		this.treeMain.setModel(orgTreeModel);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {

		// if (e.getPrevSelectBlock() == null) {
		// return;
		// }

		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		Object o = tblMain.getCell(actRowIdx, "id").getValue();
		if (o == null) {
			return;
		}
		String id = o.toString();
		// �ӽ����ϻ�ȡ����ȫ�����Ǵ����ݿ����ȡ�ɣ�
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select FIsEnabled from T_FDC_PayPlanCycle where FID = ?  ");
		builder.addParam(id);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet != null && rowSet.size() == 1) {
			rowSet.next();
			boolean isEnabled = rowSet.getBoolean("FIsEnabled");
			if (isEnabled) {
				this.actionCancelCancel.setEnabled(false);
				this.actionCancel.setEnabled(true);
			} else {
				this.actionCancel.setEnabled(false);
				this.actionCancelCancel.setEnabled(true);
			}
		}

	}

	String selectOrgId = null;
	Set projectLeafset = null;

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {

		// tblMain.removeRows();// �����²�ѯ
		// DefaultKingdeeTreeNode root =
		// (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		// if(root==node){
		// tblMain.removeRows();
		// tblMain.getSelectManager().removeAll();
		// return;
		// }
		this.refresh(null);
	}

	protected void refresh(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		// super.refresh(e);
		execQuery();
	}

	protected void execQuery() {
		tblMain.removeRows();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tblMain.getSelectManager().removeAll();
			}
		});
	}

	protected FilterInfo getMainFilter() throws Exception {
		return new FilterInfo();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		viewInfo = (EntityViewInfo) viewInfo.clone();
		try {
			FilterInfo filter = getMainFilter();
			if (filter != null) {
				if (viewInfo.getFilter() != null) {
					viewInfo.getFilter().mergeFilter(filter, "and");
				} else {
					viewInfo.setFilter(filter);
				}
			}

		} catch (Exception e) {
			this.handUIExceptionAndAbort(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null)
			return;
		if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo curOrg = (OrgStructureInfo) node.getUserObject();
			getUIContext().put("selectedOrg", curOrg);
		} else {
			return;
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		Object o = tblMain.getCell(actRowIdx, "id").getValue();
		if (o == null) {
			return;
		}
		String id = o.toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select FIsEnabled from T_FDC_PayPlanCycle where FID = ?  ");
		builder.addParam(id);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet != null && rowSet.size() == 1) {
			rowSet.next();
			boolean isEnabled = rowSet.getBoolean("FIsEnabled");
			if (isEnabled) {// ����״̬�Ľ�ֹ�޸�
				MsgBox.showWarning("�õ����Ѿ������ã�����ɾ����");
				SysUtil.abort();
			}
			// ɾ��ѡ�з�¼���Ѿ������õ����ڱ����õ���ʾ����ɾ��
			else {
				FDCSQLBuilder builder1 = new FDCSQLBuilder();
				builder1
						.appendSql("select fid from  T_FNC_FDCDepConPayPlanBill where FPayPlanCycleID = ?  ");
				builder1.addParam(id);
				IRowSet rowSet1 = builder1.executeQuery();
				if (rowSet1 != null && rowSet1.size() >= 1) {
					rowSet1.next();
					String testId = rowSet1.getString("fid");
					if (testId != null && !"".equals(testId)) {
						MsgBox.showWarning("�õ����Ѿ������ã�����ɾ����");
						SysUtil.abort();
					}
				}
			}
		}

		super.actionRemove_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		
		int selectedRowIndex = tblMain.getSelectManager().getActiveRowIndex();
		boolean isEnabled = ((Boolean) tblMain.getRow(selectedRowIndex).getCell("isEnabled").getValue()).booleanValue();
		if (isEnabled) {// ����״̬�Ľ�ֹ�޸�
			MsgBox.showWarning("�õ����Ѿ������ã��������޸ģ�");
			SysUtil.abort();
		}
		// �޸�ѡ�з�¼���Ѿ������õ����ڱ����õ���ʾ�����޸�
		else {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("PayPlanCycle", getSelectedIdValues().get(0)));
			if (FDCDepConPayPlanBillFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showWarning("�õ����Ѿ������ã������޸ģ�");
				SysUtil.abort();
			}
		}

		super.actionEdit_actionPerformed(e);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode selectTreeNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();

		if (selectTreeNode == null) {
			MsgBox.showWarning(this, "��ѡ����֯��");
			SysUtil.abort();
		}
		if (!(selectTreeNode.getUserObject() instanceof OrgStructureInfo)) {
			MsgBox.showWarning("............");
			SysUtil.abort();
		}

		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * ����
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if (actRowIdx < 0) {
			FDCMsgBox.showWarning("��ѡ��Ҫ�������У�");
			SysUtil.abort();
		}
		Object o = tblMain.getCell(actRowIdx, "id").getValue();
		if (o == null) {
			return;
		}
		String id = o.toString();
		// ����ѡ�з�¼���Ѿ�������ƻ����õ���ʾ���ܽ���
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select fid from  T_FNC_FDCDepConPayPlanBill where FPayPlanCycleID = ?  ");
		builder.addParam(id);
		// IRowSet rowSet = builder.executeQuery();
		// if(rowSet!=null&&rowSet.size()>=1){
		// rowSet.next();
		// String testId = rowSet.getString("fid");
		// if(testId!=null&&!"".equals(testId)){
		// MsgBox.showWarning("�õ����Ѿ�������ƻ����ã����ܽ��ã�");
		// SysUtil.abort();
		// }
		// }

		builder.clear();
		builder
				.appendSql("update T_FDC_PayPlanCycle set  FIsEnabled=0 where fid =?  ");
		builder.addParam(id);
		builder.execute();

		this.refresh(null);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null && !node.isLeaf()) {
			this.actionCancelCancel.setEnabled(true);
			this.actionCancel.setEnabled(false);
		}
	}

	/**
	 * ����
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if (actRowIdx < 0) {
			FDCMsgBox.showWarning("��ѡ��Ҫ�������У�");
			SysUtil.abort();
		}
		Object o = tblMain.getCell(actRowIdx, "id").getValue();
		if (o == null) {
			return;
		}
		String id = o.toString();
		// һ��ʵ�������֯��ֻ����һ�������õĸ���ƻ�����
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select count(fid) as cishu from T_FDC_PayPlanCycle where FOrgId=( select FOrgId from T_FDC_PayPlanCycle where FID = ? ) and FIsEnabled = 1  ");
		builder.addParam(id);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet != null && rowSet.size() >= 1) {
			rowSet.next();
			int count = rowSet.getInt("cishu");
			if (count > 0) {
				MsgBox.showWarning("ֻ������һ�׼ƻ����ڣ�");
				SysUtil.abort();
			}
		}

		builder.clear();
		builder
				.appendSql("update T_FDC_PayPlanCycle set  FIsEnabled=1 where fid =?  ");
		builder.addParam(id);
		builder.execute();

		this.refresh(null);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null && !node.isLeaf()) {
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(true);
		}
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnQuery.setVisible(false);
		this.btnLocate.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.menuItemQuery.setVisible(false);
		this.menuItemLocate.setVisible(false);
		this.menuItemPrint.setVisible(false);
		this.menuItemPrintPreview.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.btnAddNew.setEnabled(true);
		this.btnRemove.setEnabled(true);
		this.btnEdit.setEnabled(true);
		this.btnCancel.setVisible(true);
		this.btnCancelCancel.setVisible(true);
		this.btnCancel.setEnabled(true);
		this.btnCancelCancel.setEnabled(true);
		this.treeView.setVisible(false);
	}

	public void onLoad() throws Exception {
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			MsgBox.showWarning(this, "�Ǽ�����֯���ܲ鿴!");
			SysUtil.abort();
		}

		super.onLoad();
		FDCBaseDataClientUtils.setupUITitle(this, "����ƻ�����-��ʱ��");

		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getColumn("cycle").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * �������Ƿ�֧��EAS�߼�ͳ��(EAS800�����Ĺ���)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}