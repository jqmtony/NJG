/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTViewManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ClearSplitFacadeFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ConChangeSplitListUI extends AbstractConChangeSplitListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ConChangeSplitListUI.class);

	/**
	 * output class constructor
	 */
	public ConChangeSplitListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	private static final BOSObjectType costSplit = new ConChangeSplitInfo()
			.getBOSType();

	private boolean isCostSplit(String id) {
		return id == null ? false : BOSUuid.read(id).getType()
				.equals(costSplit);
	}

	private boolean isNoCostSplit(String id) {
		return id == null ? false : !BOSUuid.read(id).getType().equals(
				costSplit);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList ids = getSelectedIdValues();
		if (ids != null && ids.size() > 1) {

			for (Iterator iter = ids.iterator(); iter.hasNext();) {
				String id = (String) iter.next();
				if (isCostSplit(id)
						&& SettledMonthlyHelper.existObject(null, costSplit,
								getSelectedKeyValue())) {
					MsgBox.showWarning(this,
							"�ɱ��½��Ѿ����ñ����ݣ�����ɾ���������޸ģ���Ѵ˸����Ӧ�ĺ�ͬ������������̣�");
					SysUtil.abort();
				}
			}
			checkBeforeRemove();
			checkImp();
			if (confirmRemove()) {
				List costSplits = new ArrayList();
				List noCostSplits = new ArrayList();
				for (Iterator iter = ids.iterator(); iter.hasNext();) {
					String id = (String) iter.next();
					if (isCostSplit(id)) {
						costSplits.add(id);
					}
					if (isNoCostSplit(id)) {
						noCostSplits.add(id);
					}
				}
				if (costSplits.size() > 0) {
					IObjectPK[] pks = new ObjectUuidPK[costSplits.size()];
					for (int i = 0; i < costSplits.size(); i++) {
						pks[i] = new ObjectUuidPK(costSplits.get(i).toString());
					}
					ConChangeSplitFactory.getRemoteInstance().delete(pks);
				}

				if (noCostSplits.size() > 0) {
					IObjectPK[] pks = new ObjectUuidPK[costSplits.size()];
					for (int i = 0; i < noCostSplits.size(); i++) {
						pks[i] = new ObjectUuidPK(noCostSplits.get(i)
								.toString());
					}
					ConChangeNoCostSplitFactory.getRemoteInstance().delete(pks);
				}
				// afterRemove();
				showOprtOKMsgAndRefresh();
			}

		} else {
			//update by renliang
			if(ids!=null && ids.size()>0){
				for (Iterator iter = ids.iterator(); iter.hasNext();) {
					String id = (String) iter.next();
					if (isCostSplit(id)
							&& (SettledMonthlyHelper.existObject(null, costSplit,
									getSelectedKeyValue()))) {
						MsgBox.showWarning(this,
								"�ɱ��½��Ѿ����ñ����ݣ�����ɾ���������޸ģ���Ѵ˸����Ӧ�ĺ�ͬ������������̣�");
						SysUtil.abort();
					}

				}
				super.actionRemove_actionPerformed(e);
			}
		}
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
	}

	/**
	 * output actionProjectAttachment_actionPerformed
	 */
	public void actionProjectAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProjectAttachment_actionPerformed(e);
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		}
		checkSelected();
		AttachmentClientManager acm = AttachmentManagerFactory
				.getClientManager();
		String contId = getMainTable().getCell(selectRows[0], "id").getValue()
				.toString();
		ContractChangeBillInfo contchange = ContractChangeBillFactory
				.getRemoteInstance().getContractChangeBillInfo(
						new ObjectUuidPK(BOSUuid.read((contId))));
		String contractId = contchange.getContractBill().getId().toString();
		ContractBillInfo contract = ContractBillFactory
				.getRemoteInstance()
				.getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
		CurProjectInfo curProject = contract.getCurProject();
		if (curProject == null) {
			return;
		}
		acm.showAttachmentListUIByBoID(curProject.getId().toString(), this);
	}

	/**
	 * output actionViewContent_actionPerformed
	 */
	public void actionViewContent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewContent_actionPerformed(e);
	}

	/**
	 * output actionAddContent_actionPerformed
	 */
	public void actionAddContent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAddContent_actionPerformed(e);
	}

	/**
	 * output actionCostSplit_actionPerformed
	 */
	public void actionCostSplit_actionPerformed(ActionEvent e) throws Exception {
		super.actionCostSplit_actionPerformed(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		if (isCostSplit()) {
			return ConChangeSplitFactory.getRemoteInstance();
		} else {
			return ConChangeNoCostSplitFactory.getRemoteInstance();
		}
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		// return super.getEditUIName();
		if (isCostSplit()) {
			return ConChangeSplitEditUI.class.getName();
		} else {
			return ConChangeNoCostEditUI.class.getName();
		}
	}

	private boolean isCostSplit() {
		boolean isCostSplit = false;
		// �ǳɱ���Ŀ���
		int[] selectRows = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length < 1) {
			return false;
		}
		Object costSplit = getMainTable().getCell(selectRows[0], "isCostSplit")
				.getValue();
		if (costSplit instanceof Boolean) {
			isCostSplit = ((Boolean) costSplit).booleanValue();
		}
		return isCostSplit;
	}

	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		return new ConChangeSplitInfo();
	}

	/**
	 * ���ع�����Ŀ��ListUI Query�ж�Ӧ����ʵ��Ĺ�����Ŀ�������Ե�·�����������ʵ�� ��treeSelectChange()�ڵ���
	 * 
	 * @author sxhong Date 2006-11-13
	 * @return
	 */
	protected String getCurProjectPath() {
		return "contractbill.curproject";
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillListUI#getRemoteInterface()
	 */
	protected ICoreBase getRemoteInterface() throws BOSException {
		if (isCostSplit()) {
			return ConChangeSplitFactory.getRemoteInstance();
		} else {
			return ConChangeNoCostSplitFactory.getRemoteInstance();
		}
	}

	/*
	 * protected void checkImp() throws Exception{ int[] selectRows =
	 * KDTableUtil.getSelectedRows(this.tblMain); int i = 0; for(i=0;i<selectRows.length;i++){
	 * String
	 * billId=getMainTable().getCell(selectRows[i],"id").getValue().toString();
	 * EntityViewInfo view = new EntityViewInfo(); FilterInfo filter = new
	 * FilterInfo(); filter.getFilterItems().add(new
	 * FilterItemInfo("costBillId",billId)); view.setFilter(filter);
	 * AbstractObjectCollection coll=null; AbstractObjectCollection
	 * collPay=null; coll =
	 * SettlementCostSplitEntryFactory.getRemoteInstance().getSettlementCostSplitEntryCollection(view);
	 * collPay =
	 * PaymentSplitEntryFactory.getRemoteInstance().getPaymentSplitEntryCollection(view);
	 * if(coll.iterator().hasNext()){
	 * MsgBox.showError(this,"�������Ѿ������������ã�����ɾ��!"); SysUtil.abort(); }
	 * if(collPay.iterator().hasNext()){
	 * MsgBox.showError(this,"�������Ѿ������������ã�����ɾ��!"); SysUtil.abort(); } } }
	 */
	protected boolean checkBeforeSplit() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		}
		String billId = getMainTable().getCell(selectRows[0], "id").getValue()
				.toString();
		if (billId != null) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("contractBill.id");
			selector.add("contractBill.isCoseSplit");
			ContractChangeBillInfo info = ContractChangeBillFactory
					.getRemoteInstance().getContractChangeBillInfo(
							new ObjectUuidPK(BOSUuid.read(billId)), selector);
			if (info.getContractBill() != null) {
				// ContractBillInfo con = ContractBillFactory
				// .getRemoteInstance()
				// .getContractBillInfo(
				// new ObjectUuidPK(info.getContractBill().getId()));
				// if (!con.isIsCoseSplit()) {
				// // ContractTypeInfo type =
				// //
				// ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new
				// // ObjectUuidPK(con.getContractType().getId()));
				// MsgBox.showWarning(this, FDCSplitClientHelper
				// .getRes("conNoSplit"));
				// SysUtil.abort();
				// }
				FilterInfo filterSett = new FilterInfo();
				filterSett.appendFilterItem("settlementBill.contractBill.id",
						info.getContractBill().getId());
				filterSett.getFilterItems().add(
						new FilterItemInfo("state",
								FDCBillStateEnum.INVALID_VALUE,
								CompareType.NOTEQUALS));
				filterSett.appendFilterItem("settlementBill.isFinalSettle", Boolean.TRUE);
				boolean hasSettleSplit = false;
				if (info.getContractBill().isIsCoseSplit()) {
					hasSettleSplit = SettlementCostSplitFactory
							.getRemoteInstance().exists(filterSett);
				} else {
					hasSettleSplit = SettNoCostSplitFactory.getRemoteInstance()
							.exists(filterSett);
				}
				if (hasSettleSplit) {
					MsgBox.showWarning(this, FDCSplitClientHelper
							.getRes("settleSplited"));
					SysUtil.abort();
				}
			}
		}
		/*
		 * EntityViewInfo viewtemp = new EntityViewInfo(); FilterInfo filtertemp =
		 * new FilterInfo(); filtertemp.getFilterItems().add(new
		 * FilterItemInfo("costBillId",billId)); viewtemp.setFilter(filtertemp);
		 * AbstractObjectCollection coll=null; AbstractObjectCollection
		 * collPay=null; coll =
		 * SettlementCostSplitEntryFactory.getRemoteInstance().getSettlementCostSplitEntryCollection(viewtemp);
		 * collPay =
		 * PaymentSplitEntryFactory.getRemoteInstance().getPaymentSplitEntryCollection(viewtemp);
		 * if(coll.iterator().hasNext()){
		 * MsgBox.showError(this,"�������Ѿ������������ã������޸�!"); SysUtil.abort(); }
		 * if(collPay.iterator().hasNext()){
		 * MsgBox.showError(this,"�������Ѿ������������ã������޸�!"); SysUtil.abort(); }
		 */
		boolean isAddNew = super.checkBeforeSplit();
		if (isAddNew) {
			// �����ݿ��ڽ��м��
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractChange.id", billId));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			if (getRemoteInterface().exists(filter)) {
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				String costBillID = getRemoteInterface().getCollection(view)
						.get(0).getId().toString();
				getMainTable().getCell(selectRows[0], getKeyFieldName())
						.setValue(costBillID);
				isAddNew = false;
			}
		}
		if (!isAddNew
				&& isCostSplit()
				&& SettledMonthlyHelper.existObject(null, costSplit,
						getSelectedKeyValue())) {
			MsgBox.showWarning(this, "�ɱ��½��Ѿ����ñ����ݣ������޸ģ������޸ģ��������ϱ���ͬ��֣�");
			SysUtil.abort();
		}
		return isAddNew;
	}

	protected void updateButtonStatus() {

		super.updateButtonStatus();

		// ���������ɱ����ģ���������ɾ����
//		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
//			actionAddNew.setEnabled(false);
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//			actionCostSplit.setEnabled(false);
//			actionAddNew.setVisible(false);
//			actionEdit.setVisible(false);
//			actionRemove.setVisible(false);
//			actionCostSplit.setVisible(false);
//			menuEdit.setVisible(false);
//			// actionView.setVisible(false);
//			// actionView.setEnabled(false);
//		}
		actionRemove.setEnabled(true);
		actionRemove.setVisible(true);
		actionAddNew.setEnabled(false);
		menuEdit.setVisible(true);
		if(isAdjustVourcherModel()){
			menuItemClearSplit.setText("���ϱ�����");
			btnClearSplit.setText("���ϱ�����");
		}
		if(isSimpleFinacialMode()){
			btnClearSplit.setVisible(false);
		}
	}

	/*
	 * protected SorterItemCollection getSorter() { SorterItemCollection
	 * sorter=new SorterItemCollection(); sorter.add(new
	 * SorterItemInfo("state")); //sorter.add(new
	 * SorterItemInfo(getSplitStateFieldName())); return sorter; }
	 */
	public void onLoad() throws Exception {
		if (isCostSplit()) {
			initParam =  ConChangeSplitFactory.getRemoteInstance().fetchInitParam();
		} else {
			initParam = ConChangeNoCostSplitFactory.getRemoteInstance().fetchInitParam();
		}
		super.onLoad();
		// ���Ӳ�ѯ����
		actionQuery.setEnabled(true);
		actionQuery.setVisible(true);
		if (!isFinacial) {
			if (isIncorporation) {
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionVoucher, actionDelVoucher }, false);
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionClearSplit, actionViewInvalid }, true);
			} else
				FDCClientHelper.setActionEnable(new ItemAction[] {
						actionVoucher, actionDelVoucher, actionClearSplit },
						false);
		}
		
	}

	/*	//R111215-0464 ���˵�������Ϊ0.00�ĵ���  added by andy_liu 2012-5-8
		protected void beforeExcutQuery(EntityViewInfo ev) {
			super.beforeExcutQuery(ev);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("amount", "0.00", CompareType.NOTLIKE));
			ev.setFilter(filter);
		}*/
	protected String getBillStateForAudit() {
		// return BillStatusEnum.AUDITED_VALUE+"";
		return CostSplitStateEnum.ALLSPLIT_VALUE + "";
	}

	protected void freezeTableColumn() {
		int number_col_index = getMainTable().getColumnIndex("name");
		KDTViewManager viewManager = getMainTable().getViewManager();
		// viewManager.setFreezeView(-1, state_col_index);
		// viewManager.setFreezeView(-1, number_col_index);
		viewManager.setFreezeView(-1, number_col_index);
	}

	protected void drawColorPanel() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(0);
		flowLayout.setHgap(10);
		// flowLayout.setVgap(5);
		// ��ÿ����ɫ�ŵ�һ��Lable��
		colorPanel.setLayout(flowLayout);
		drawALogo(FDCSplitClientHelper.getRes("allSplitState"),
				FDCSplitClientHelper.COLOR_ALLSPLIT);
		// drawALogo(FDCSplitClientHelper.getRes("partSplitState"),FDCSplitClientHelper.COLOR_PARTSPLIT);
		// drawALogo(FDCSplitClientHelper.getRes("auditNotSplit"),FDCSplitClientHelper.COLOR_AUDITTED);
		// drawALogo(FDCSplitClientHelper.getRes("notAudit"),FDCSplitClientHelper.COLOR_UNAUDITTED);
		drawALogo(FDCSplitClientHelper.getRes("notSubmmit"),
				FDCSplitClientHelper.COLOR_NOSPLIT);

	}

	public void actionViewInvalid_actionPerformed(ActionEvent e)
			throws Exception {
		IUIWindow testUI = null;
		UIContext uiContext = new UIContext(this);
		// uiContext.put(UIContext.ID, info.getId());
		try {
			testUI = UIFactory
					.createUIFactory(UIFactoryName.NEWTAB)
					.create(
							"com.kingdee.eas.fdc.contract.client.InvalidChangeSplitListUI",
							uiContext, null);
			testUI.show();
		} catch (BOSException exe) {
			handUIExceptionAndAbort(exe);
		}
	}
	
	private void checkStateSplit() {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 0) {
			if (selectRows.length > 0) {
				int rowIndex = selectRows[0];
				IRow row = tblMain.getRow(rowIndex);
				if (row == null) {
					abort();
				}

				ICell cell = row.getCell(getSplitStateFieldName());
				if ("δ���".equals(cell.getValue())) {
					FDCMsgBox.showWarning("����δ��֣�����ִ�д˲�����");
					abort();
				}
			}
		}
	}

	public void actionClearSplit_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		checkStateSplit();
		String changeId = getSelectedCostBillID();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.clear();
		builder.appendSql("select FContractBillID from T_CON_ContractChangeBill where fid=?");
		builder.addParam(changeId);
		IRowSet rowSetSplit = builder.executeQuery();
		String contractID = null;
		try {
			if (rowSetSplit.next()) {
				contractID = rowSetSplit.getString("FContractBillID");
				String ids = getSelectedKeyValue();
				if (isCostSplit(ids) && SplitClearClientHelper.isNewVersionChange(changeId)) {
					if (MsgBox.showConfirm2(this,
							"�˱����صĲ�֣������֡������֡������֣����ڲ���ڼ��ڵ�ǰ�ڼ�֮��İ汾����Ҫ����ǰ�ڼ�֮�����ز�������ȷ���Ƿ������") == MsgBox.OK) {
						boolean success = true;
						try {
							String result = ClearSplitFacadeFactory.getRemoteInstance().clearPeriodConSplit(
									changeId, "change");
							if (result != null) {
								success = false;
								String msg = "�Ѵ���֮���ڼ�ĸ��������ɵ�ƾ֤,ƾ֤��Ϊ:" + result + " ������ɾ��ƾ֤��";
								if (isAdjustVourcherModel()) {
									msg = "�Ѵ���֮���ڼ�ĸ�����ɵ�ƾ֤,ƾ֤��Ϊ:" + result + " ������ɾ��ƾ֤��";
								}
								MsgBox.showError(this, msg);
								SysUtil.abort();
							}
						} catch (Exception ex) {
							success = false;
							handUIException(ex);
						}
						if (success) {
							refresh(e);
							FDCClientUtils.showOprtOK(this);
						}
					}
				} else {
					if (!(isCostSplit(ids) && (SettledMonthlyHelper.existObject(null, costSplit,
							getSelectedKeyValue())))) {
						SplitClearClientHelper.checkClearable(this, contractID, true);
					} else {
						SplitClearClientHelper.checkClearable(this, contractID, false);
					}
					String msg = "���ϸ�����/�����ֽ��Ѻ�ͬ��ز�����ϣ�����Ӧ�ĺ�ͬ���Ѿ�����ƾ֤�ĸ����֣���˺�ͬ�������������,�������ͬƾ֤�����Զ�����������ƾ֤�ĸ����֣�ȷ���Ƿ������";
					if (isAdjustVourcherModel()) {
						msg = "���ϱ����ֽ��ѱ����ز�����ϣ�����Ӧ�ĺ�ͬ���ڱ������������ƾ֤�ĸ����ֻ�������ƾ֤�ĵ���������˺�ͬ������������̣���Ҫ������к���ƾ֤����ȷ���Ƿ������";
						checkSplitZp();
					}
					if (MsgBox.showConfirm2(this, msg) == MsgBox.OK) {
						// SplitClearClientHelper.checkClearable(this,
						// selectedContractBillID);
						boolean sccuss = true;
						try {
							ClearSplitFacadeFactory.getRemoteInstance().clearChangeSplit(getSelectedCostBillID());
						} catch (Exception ex) {
							sccuss = false;
							handUIException(ex);
						}

						if (sccuss) {
							refresh(e);
							FDCClientUtils.showOprtOK(this);
						}
					}
				}
			}
		} catch (SQLException ex) {
			logger.error(ex);
			handUIExceptionAndAbort(ex);
		}
	}
	
	
	protected void checkSplitZp() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("multiRowSelected"));
			SysUtil.abort();
		}
		String billId = getMainTable().getCell(selectRows[0], getCostBillIdFieldName()).getValue().toString();
		boolean existCostSplitColumn = getMainTable().getColumnIndex("isCostSplit") != -1;
		boolean isCostSplit = false;
		if (existCostSplitColumn) {
			Boolean temp = (Boolean) getMainTable().getCell(selectRows[0], "isCostSplit").getValue();
			if (temp != null) {
				isCostSplit = temp.booleanValue();
			}
		}
		FilterInfo filtertemp = new FilterInfo();
		filtertemp.getFilterItems().add(new FilterItemInfo("costBillId", billId));
		// ���ϵ��ݲ���
		filtertemp.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

		boolean existcollPay = false;
		boolean existcollPayNoCost = false;
		if (existCostSplitColumn && isCostSplit) {
			existcollPay = PaymentSplitEntryFactory.getRemoteInstance().exists(filtertemp);
		}
		if (existCostSplitColumn && !isCostSplit) {
			existcollPayNoCost = PaymentNoCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
		}
		if (!(existcollPay || existcollPayNoCost)) {
			MsgBox.showWarning("�ñ�����û�б�����������ã���������֡���ťֱ���޸ģ�");
			SysUtil.abort();
		}
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		// actionAudit.setVisible(false);
		// actionUnAudit.setVisible(false);
		// actionProjectAttachment.setVisible(true);
		btnAuditResult.setVisible(false);
		btnAuditResult.setEnabled(false);
		actionAuditResult.setVisible(false);
		actionAuditResult.setEnabled(false);
		
	}
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		if(this.isFinacialModel()){
			this.actionViewInvalid.setVisible(true);
		}else{
			this.actionViewInvalid.setVisible(false);
		}
	}
}