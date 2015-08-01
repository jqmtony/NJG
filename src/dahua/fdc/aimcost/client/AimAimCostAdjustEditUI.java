/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustFactory;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo;
import com.kingdee.eas.fdc.aimcost.AimAimCostItemCollection;
import com.kingdee.eas.fdc.aimcost.AimAimCostItemFactory;
import com.kingdee.eas.fdc.aimcost.AimAimCostItemInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.AccountStageCollection;
import com.kingdee.eas.fdc.basedata.AccountStageFactory;
import com.kingdee.eas.fdc.basedata.AccountStageInfo;
import com.kingdee.eas.fdc.basedata.AdjustTypeCollection;
import com.kingdee.eas.fdc.basedata.AdjustTypeFactory;
import com.kingdee.eas.fdc.basedata.AdjustTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FdcCodingRuleUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class AimAimCostAdjustEditUI extends AbstractAimAimCostAdjustEditUI {
	private static final Logger logger = CoreUIObject.getLogger(AimAimCostAdjustEditUI.class);
	private CurProjectInfo project = null;

	private static final String COSTACCOUNT = "costAccountNumber";
	private static final String COSTACCOUNT_NAME = "costAccountName";
	private static final String ADJUSTTYPE = "adjustType";// ��������
	private static final String WORKLOAD = "workload";// ������
	private static final String PRICE = "price";// ����
	private static final String PRODUCT = "product";
	private static final String UNIT = "unit";
	private static final String ADJUSTAMOUNT = "adjustAmount";// �������
	private static final String DESCRIPTION = "description";
	
	/* modified by zhaoqin for R131022-0402 on 2013/12/31 */
	private static final String CHANGE_REASON = "changeReason";	//�仯ԭ��

	private static final String AIMCOST = "aimCost";// Ŀ��ɱ�
	private static final String OCCURREDAMUONT = "occurredAmount";// �ѷ����ɱ�
	private static final String OVERAMOUNT = "overAmount";// ��֧���
	private static final String CONTRACT = "contract";// ��ͬ
	private static final String CHANGECONTRACT = "changeContract";// ���
	private static final String SETTLECONTRACT = "settleContract";// ǩԼ
	private static final String OVERDESCRIPTION = "overDescription";// ��֧˵��

	private static final String CONTRACTID = "contarctID";
	private static final String CONCHANGEID = "conChangeID";
	private static final String CONSETTLEID = "conSettleID";
	private static final String ISSETTLED = "isSettled";

	private static final String COSTACCOUNTID = "costAccountID";
	private static final String SETTLED = "�ѽ���";

	private static Map itemsMap = new HashMap();
	private static Map oldItemsMap = new HashMap();

	private DyProductTypeGetter productTypeGetter;
	public AimAimCostAdjustEditUI() throws Exception {
		super();
		jbInit();
	}

	private void jbInit() {
		titleMain = getUITitle();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onShow() throws Exception {
		super.onShow();
		if (STATUS_VIEW.equals(this.getOprtState())) {
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
		}

	}

	public void onLoad() throws Exception {
		super.onLoad();
		project = getCurProjectInfo();
		initButton();
		initTable();
		prmtCurProject.setData(project);
		if (project != null) {
			final DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getDynamicCost(project.getId().toString(),
					curPeriod, true);
			productTypeGetter = dynamicCostMap.getDyProductTypeGetter();
		}
		AimCostCollection aimCostCollection = AimCostFactory.getRemoteInstance().getAimCostCollection(
				"select id,measureStage.id where orgOrProId='" + project.getId().toString()
						+ "' and isLastVersion =1 and state='4AUDITTED'");
		if (aimCostCollection.size() > 0) {
			String stageId = aimCostCollection.get(0).getMeasureStage().getId().toString();
			set = getAcctMap(project.getId().toString(), stageId);
		}
		initPrmtF7();
		initProTypeF7();
		initUnitF7();
		initAdjustType();

		itemsMap.clear();
		oldItemsMap.clear();
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			CostAccountInfo costAccount = (CostAccountInfo) kdtEntrys.getCell(i, COSTACCOUNT).getValue();
			List itemsList = initItemsByAccount(costAccount);
			if (costAccount != null) {
				itemsMap.put(costAccount.getId().toString(), itemsList);
				List oldItemsList = initItemsByAccount(costAccount);
				oldItemsMap.put(costAccount.getId().toString(), oldItemsList);
			}
		}
	}

	/**
	 * ���ݳɱ���Ŀ��ȡ��Ӧ��Ŀ��Ϣ
	 * 
	 * @param costAccount
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private List initItemsByAccount(CostAccountInfo costAccount) throws BOSException, EASBizException {
		List itemsList = new ArrayList();
		if (costAccount == null) {
			return itemsList;
		}
		String costAccounID = costAccount.getId().toString();
		// ��ͬ�����Ϣ
		Map contractSplitMap = initContractSplitData(costAccount);
		List contractSplitList = (List) contractSplitMap.get(costAccount.getId());
		for (int a = 0; a < contractSplitList.size(); a++) {
			ContractCostSplitEntryInfo ccseInfo = (ContractCostSplitEntryInfo) contractSplitList.get(a);
			AimAimCostAdjustItems item = new AimAimCostAdjustItems();
			item.setCostAccountID(costAccounID);
			item.setOccuredAmount(ccseInfo.getAmount());
			item.setContractNumber(ccseInfo.getParent().getContractBill().getNumber());
			item.setContract(ccseInfo.getParent().getContractBill().getName());
			item.setContractID(ccseInfo.getParent().getContractBill().getId().toString());
			itemsList.add(item);
		}

		// ��ͬ����������Ϣ
		Map conChangeSplitMap = initConChangeSplitData(costAccount);
		List conChangeSplitList = (List) conChangeSplitMap.get(costAccount.getId());
		for (int b = 0; b < conChangeSplitList.size(); b++) {
			ConChangeSplitEntryInfo cceInfo = (ConChangeSplitEntryInfo) conChangeSplitList.get(b);
			AimAimCostAdjustItems item = new AimAimCostAdjustItems();
			item.setCostAccountID(costAccounID);
			item.setOccuredAmount(cceInfo.getAmount());
			item.setChangeContract(cceInfo.getParent().getContractChange().getName());
			item.setChangeContractNumber(cceInfo.getParent().getContractChange().getNumber());
			item.setChangeContractID(cceInfo.getParent().getContractChange().getId().toString());
			item.setContractID(cceInfo.getParent().getContractBill().getId().toString());
			itemsList.add(item);
		}

		// ��ͬ��������Ϣ
		Map settlementCostSplitMap = initConSettleSplitData(costAccount);
		List settlementCostSplitList = (List) settlementCostSplitMap.get(costAccount.getId());
		for (int c = 0; c < settlementCostSplitList.size(); c++) {
			SettlementCostSplitEntryInfo cceInfo = (SettlementCostSplitEntryInfo) settlementCostSplitList.get(c);
			AimAimCostAdjustItems item = new AimAimCostAdjustItems();
			item.setCostAccountID(costAccounID);
			item.setOccuredAmount(cceInfo.getAmount());
			item.setSettleContract(cceInfo.getParent().getSettlementBill().getName());
			item.setSettleContractNumber(cceInfo.getParent().getSettlementBill().getNumber());
			item.setSettleContractID(cceInfo.getParent().getSettlementBill().getId().toString());
			item.setContractID(cceInfo.getParent().getContractBill().getId().toString());
			itemsList.add(item);
		}

		for (int d = 0; d < itemsList.size(); d++) {
			AimAimCostAdjustItems item = (AimAimCostAdjustItems) itemsList.get(d);
			if (item.getSettleContractID() == null) {
				continue;
			} else {
				String cotractID = item.getContractID();
				for (int dd = 0; dd < itemsList.size(); dd++) {
					AimAimCostAdjustItems item2 = (AimAimCostAdjustItems) itemsList.get(dd);
					if (item2.getContractID().equals(cotractID) && item2.getSettleContractID() == null) {
						item2.setOccuredAmount(null);
					}
				}
			}
		}

		// ��֧˵����Ϣ
		AimAimCostItemCollection items = fetchItems(editData.getId().toString(), costAccount.getId().toString());
		for (int i = 0; i < items.size(); i++) {
			AimAimCostItemInfo item = items.get(i);
			for (int j = 0; j < itemsList.size(); j++) {
				AimAimCostAdjustItems itemTemp = (AimAimCostAdjustItems) itemsList.get(j);
				if (itemTemp.getContract() != null && itemTemp.getContractID().equals(item.getObjectID())) {
					itemTemp.setOverDescription(item.getDescription());
					itemTemp.setId(item.getId().toString());
					itemsList.remove(j);
					itemsList.add(j, itemTemp);
				}
				if (itemTemp.getChangeContract() != null && itemTemp.getChangeContractID().equals(item.getObjectID())) {
					itemTemp.setOverDescription(item.getDescription());
					itemTemp.setId(item.getId().toString());
					itemsList.remove(j);
					itemsList.add(j, itemTemp);
				}
				if (itemTemp.getSettleContract() != null && itemTemp.getSettleContractID().equals(item.getObjectID())) {
					itemTemp.setOverDescription(item.getDescription());
					itemTemp.setId(item.getId().toString());
					itemsList.remove(j);
					itemsList.add(j, itemTemp);
				}
			}
		}

		// �ϼ��д���
		AimAimCostAdjustItems totleItem = new AimAimCostAdjustItems();
		BigDecimal aimCost = getAimCost(costAccount);// Ŀ��ɱ�
		totleItem.setAimCost(aimCost);
		BigDecimal occurredAmount = countTotalOccuredAmount(itemsList);// �ѷ������ϼ�
		totleItem.setOccuredAmount(occurredAmount);
		BigDecimal overAmount = occurredAmount.subtract(aimCost);// ��֧���
		totleItem.setOverAmount(overAmount);
		itemsList.add(totleItem);
		if (!itemsMap.containsKey(costAccount.getId().toString())) {
			itemsMap.put(costAccount.getId().toString(), itemsList);
		}
		return itemsList;
	}

	private BigDecimal countTotalOccuredAmount(List itemsList) {
		BigDecimal totalOccuredAmount = FDCHelper.ZERO;
		// �Ȱ��ѽ����ͬ�Ľ������ۼ�������������Ӧ�ѽ���ĺ�ͬ�����
		for (int i = 0; i < itemsList.size(); i++) {
			AimAimCostAdjustItems item = (AimAimCostAdjustItems) itemsList.get(i);
			if (item.getSettleContractID() != null) {
				totalOccuredAmount = totalOccuredAmount.add(item.getOccuredAmount());
				for (int j = 0; j < itemsList.size(); j++) {
					AimAimCostAdjustItems temp = (AimAimCostAdjustItems) itemsList.get(j);
					if (item.getContractID().equals(temp.getContractID())) {
						temp.setFlag(SETTLED);
						itemsList.remove(j);
						itemsList.add(j, temp);
					}
				}
			}
		}
		// �ٴ��ۼӻ�δ����ĺ�ͬ��ǩԼ���������
		for (int i = 0; i < itemsList.size(); i++) {
			AimAimCostAdjustItems item = (AimAimCostAdjustItems) itemsList.get(i);
			if (item.getFlag() == null) {
				totalOccuredAmount = totalOccuredAmount.add(item.getOccuredAmount());
			}
		}
		return totalOccuredAmount;
	}

	private void showItems(CostAccountInfo costAccount) {
		kdtItems.removeRows();
		ArrayList itemsList = (ArrayList) itemsMap.get(costAccount.getId().toString());
		if (itemsList.size() == 1) {
			return;
		}
		for (int i = 0; i < itemsList.size(); i++) {
			AimAimCostAdjustItems item = (AimAimCostAdjustItems) itemsList.get(i);
			IRow row = kdtItems.addRow();
			if (i == 0) {
				if (costAccount.getLongNumber() != null) {
					row.getCell(COSTACCOUNT).setValue(costAccount.getLongNumber().replace('!', '.'));
				}
				row.getCell(COSTACCOUNT_NAME).setValue(costAccount.getName());
			}
			row.getCell(AIMCOST).setValue(item.getAimCost());
			row.getCell(OCCURREDAMUONT).setValue(item.getOccuredAmount());
			row.getCell(OVERAMOUNT).setValue(item.getOverAmount());
			if (item.getContract() != null) {
				row.getCell(CONTRACT).setValue(item.getContractNumber() + ";" + item.getContract());
			}
			if (item.getChangeContract() != null) {
				row.getCell(CHANGECONTRACT).setValue(item.getChangeContractNumber() + ";" + item.getChangeContract());
			}
			if (item.getSettleContract() != null) {
				row.getCell(SETTLECONTRACT).setValue(item.getSettleContractNumber() + ";" + item.getSettleContract());
				item.getContractID();
			}
			row.getCell(OVERDESCRIPTION).setValue(item.getOverDescription());
			row.getCell(CONTRACTID).setValue(item.getContractID());
			row.getCell(CONCHANGEID).setValue(item.getChangeContractID());
			row.getCell(CONSETTLEID).setValue(item.getSettleContractID());
			row.getCell(ISSETTLED).setValue(item.getFlag());
			row.getCell(COSTACCOUNTID).setValue(item.getCostAccountID());
			if (i == itemsList.size() - 1) {
				row.getCell(COSTACCOUNT).setValue("�ϼ�");
				row.getCell(OVERDESCRIPTION).getStyleAttributes().setLocked(true);
			}
		}
		initItemsTable();
	}


	/**
	 * ��ȡĳһ���ɿ�ĿĿ��ɱ�
	 * 
	 * @param costAccount
	 * @return
	 * @throws BOSException
	 */
	private BigDecimal getAimCost(CostAccountInfo costAccount) throws BOSException {
		BigDecimal costAmount = FDCHelper.ZERO;
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSelector().add(new SelectorItemInfo("costAmount"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.isLastVersion", new Integer("1"), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", costAccount.getId(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("head.orgOrProId", costAccount.getCurProject().getId(), CompareType.EQUALS));
		evi.setFilter(filter);

		CostEntryCollection costEntries = CostEntryFactory.getRemoteInstance().getCostEntryCollection(evi);
		for (int i = 0; i < costEntries.size(); i++) {
			CostEntryInfo costEntryInfo = costEntries.get(i);
			if (costEntryInfo.getCostAmount() != null) {
				costAmount = costAmount.add(costEntryInfo.getCostAmount());
			}
		}
		return costAmount;
	}

	public void loadFields() {
		detachListeners();
		super.loadFields();
	}

	private void initButton() {
		if (this.oprtState.equals(OprtState.EDIT) && !this.editData.getState().equals(FDCBillStateEnum.SAVED)) {
			this.btnSave.setEnabled(false);
		}
		
		//added by ken_liu...�༭״̬���� ����������
		if( !this.oprtState.equals(OprtState.EDIT) ) {
			btnAudit.setEnabled(true);
			btnAudit.setVisible(true);
			btnUnAudit.setEnabled(true);
			btnUnAudit.setVisible(true);
		}
		
		
		btnPrintPreview.setVisible(false);
		btnPrint.setVisible(false);

		menuSubmitOption.setEnabled(false);
		menuSubmitOption.setVisible(false);

		menuItemCopy.setEnabled(false);
		menuItemCopy.setVisible(false);
		menuItemCreateFrom.setEnabled(false);
		menuItemCreateFrom.setVisible(false);
		menuItemCreateTo.setEnabled(false);
		menuItemCreateTo.setVisible(false);
		menuItemCopyFrom.setEnabled(false);
		menuItemCopyFrom.setVisible(false);

		menuView.setEnabled(false);
		menuView.setVisible(false);
	}

	private void initTable() {
		/**
		 * Entrys��
		 */
		FDCHelper.formatTableNumber(getDetailTable(), new String[] { WORKLOAD, ADJUSTAMOUNT }, FDCHelper.strDataFormat);
		FDCHelper.formatTableNumber(getDetailTable(), PRICE, FDCHelper.strDataFormat4);
		ICellEditor numberEditor = null;
		KDFormattedTextField formattedTextField = null;
		// ������
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMaximumValue(new BigDecimal("999999999"));
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		getDetailTable().getColumn(WORKLOAD).setEditor(numberEditor);
		// �۸�
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(4);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMinimumValue(new BigDecimal("0"));
		formattedTextField.setMaximumValue(new BigDecimal("999999999"));
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		getDetailTable().getColumn(PRICE).setEditor(numberEditor);
		// �������
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setMaximumValue(new BigDecimal("999999999999999999"));
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setRequired(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		getDetailTable().getColumn(ADJUSTAMOUNT).setEditor(numberEditor);
		// ��ע
		KDTextField textField = new KDTextField();
		textField.setMaxLength(255);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		getDetailTable().getColumn(DESCRIPTION).setEditor(txtEditor);
		
		/* modified by zhaoqin for R131022-0402 on 2013/12/31 start */
		// �仯ԭ��
		KDTextField changeReasonField = new KDTextField();
		changeReasonField.setMaxLength(255);
		ICellEditor changeEditor = new KDTDefaultCellEditor(changeReasonField);
		getDetailTable().getColumn(CHANGE_REASON).setEditor(changeEditor);
		/* modified by zhaoqin for R131022-0402 on 2013/12/31 end */
	}

	private void initItemsTable() {
		/**
		 * Items��
		 */
		FDCHelper.formatTableNumber(kdtItems, AIMCOST, FDCHelper.strDataFormat);
		FDCHelper.formatTableNumber(kdtItems, OCCURREDAMUONT, FDCHelper.strDataFormat);
		FDCHelper.formatTableNumber(kdtItems, OVERAMOUNT, FDCHelper.strDataFormat);
		ICellEditor numberEditor = null;
		KDFormattedTextField formattedTextField = null;
		// Ŀ��ɱ�
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		kdtItems.getColumn(AIMCOST).setEditor(numberEditor);
		// �ѷ������
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		kdtItems.getColumn(OCCURREDAMUONT).setEditor(numberEditor);
		// ��֧���
		formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		kdtItems.getColumn(OVERAMOUNT).setEditor(numberEditor);
		// ��֧˵��
		KDTextField textField1 = new KDTextField();
		textField1.setMaxLength(1000);
		ICellEditor txtEditor1 = new KDTDefaultCellEditor(textField1);
		kdtItems.getColumn(OVERDESCRIPTION).setEditor(txtEditor1);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// verifyInput();
		verifyInput(e);
		saveItemsData();
		super.actionSave_actionPerformed(e);
		reLoadOldItems();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (!isAuditOnLastVersion(project.getId().toString())) {
			FDCMsgBox.showInfo("���°�Ŀ��ɱ�δ��������ֱ���޸ģ�");
			SysUtil.abort();
		}
		// verifyInput();
		verifyInput(e);
		verifyEntry();
		saveItemsData();
		
		//added by ken_liu...�޸�Ϊ��������ģʽ
		this.chkMenuItemSubmitAndAddNew.setVisible(true);
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		super.actionSubmit_actionPerformed(e);
		reLoadOldItems();
		
	}

	/**
	 * Ŀ��ɱ����°汾�Ƿ�������
	 * 
	 * @param projectID
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private boolean isAuditOnLastVersion(String projectID) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select fstate from T_AIM_AimCost acc");
		builder.appendSql(" where acc.fversionnumber =");
		builder.appendSql(" (");
		builder.appendSql(" select max(fversionnumber) from T_AIM_AimCost ac");
		builder.appendSql(" where ac.ForgOrProId = '" + projectID + "'");
		builder.appendSql(" )");
		builder.appendSql(" and acc.forgOrProid = '" + projectID + "'");
		IRowSet rowSet;
		try {
			rowSet = builder.executeQuery();
			if (rowSet.next()) {
				if (FDCBillStateEnum.AUDITTED_VALUE.equals(rowSet.getString("fstate"))) {
					return true;
				}
			}
		} catch (BOSException e) {
			logger.info("the SQL statement is: " + builder.getTestSql());
			e.printStackTrace();
			
		} catch (SQLException e) {
			logger.info("the SQL statement is: " + builder.getTestSql());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ���»�ȡ��Ŀ��Ϣ
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void reLoadOldItems() throws BOSException, EASBizException {
		Set keySet = oldItemsMap.keySet();
		Object[] keyArry = keySet.toArray();
		for (int i = 0; i < oldItemsMap.size(); i++) {
			ArrayList itemsList = (ArrayList) oldItemsMap.get(keyArry[i].toString());
			itemsList.clear();
		}
		oldItemsMap.clear();
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			CostAccountInfo costAccount = (CostAccountInfo) kdtEntrys.getCell(i, COSTACCOUNT).getValue();
			if (costAccount != null) {
				List items = initItemsByAccount(costAccount);
				oldItemsMap.put(costAccount.getId().toString(), items);
			}
		}
	}

	/**
	 * ������Ŀ��Ϣ
	 * 
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean saveItemsData() throws EASBizException, BOSException {
		String parentID = editData.getId().toString();

		//�������û�з�¼����ɾ������֮ǰ������ı��ɿ�Ŀ��Ӧ����Ŀ
		if (this.oprtState.equals(OprtState.EDIT)) {
			if (kdtEntrys.getRowCount() == 0) {
				Set keySet = oldItemsMap.keySet();
				Object[] keyArry = keySet.toArray();
				for (int i = 0; i < oldItemsMap.size(); i++) {
					ArrayList itemsList = (ArrayList) oldItemsMap.get(keyArry[i].toString());
					if (itemsList != null) {
						for (int j = 0; j < itemsList.size() - 1; j++) {
							AimAimCostAdjustItems item = (AimAimCostAdjustItems) itemsList.get(j);
							if (!FDCHelper.isEmpty(item.getOverDescription())) {
								deleteItems(parentID, item.getCostAccountID());
							}
						}
					}
				}
			} else {// ���������ڷ�¼��ɾ������ʱ�ɱ���Ŀ����û�гɱ���Ŀ��Ӧ����Ŀ��Ϣ
				Set keySet = oldItemsMap.keySet();
				Object[] keyArry = keySet.toArray();
				for (int i = 0; i < oldItemsMap.size(); i++) {
					if (!itemsMap.containsKey(keyArry[i].toString())) {
						ArrayList oldItemsList = (ArrayList) oldItemsMap.get(keyArry[i].toString());
						if (oldItemsList != null) {
							for (int k = 0; k < oldItemsList.size() - 1; k++) {
								AimAimCostAdjustItems item = (AimAimCostAdjustItems) oldItemsList.get(k);
								if (!FDCHelper.isEmpty(item.getOverDescription())) {
									deleteItems(parentID, item.getCostAccountID());
								}
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount();) {
			if (kdtEntryRowIsEmpty(i)) {
				kdtEntrys.removeRow(i);
			} else {
				i++;
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			CostAccountInfo costAccount = (CostAccountInfo) kdtEntrys.getCell(i, COSTACCOUNT).getValue();
			if (costAccount == null) {
				continue;
			}
			ArrayList itemsList = (ArrayList) itemsMap.get(costAccount.getId().toString());
			
			// ɾ���ֽ������гɱ���Ŀ��Ӧ����Ŀ��Ϣ
			if (deleteItems(editData.getId().toString(), costAccount.getId().toString())) {// ɾ���ɹ�
				for (int j = 0; j < itemsList.size() - 1; j++) {
					AimAimCostAdjustItems item = (AimAimCostAdjustItems) itemsList.get(j);
					if (item.getOverDescription() != null) {
						AimAimCostItemInfo costItem = new AimAimCostItemInfo();
						if (item.getId() != null) {
							costItem.setId(BOSUuid.read(item.getId()));
						} else {
							costItem.setId(BOSUuid.create(costItem.getBOSType()));
						}
						costItem.setParent(parentID);
						costItem.setCostAccountID(costAccount.getId().toString());
						if (item.getContract() != null) {
							costItem.setObjectID(item.getContractID());
						} else if (item.getChangeContract() != null) {
							costItem.setObjectID(item.getChangeContractID());
						} else if (item.getSettleContract() != null) {
							costItem.setObjectID(item.getSettleContractID());
						}
						costItem.setDescription(item.getOverDescription());
						AimAimCostItemFactory.getRemoteInstance().save(costItem);
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * �жϷ�¼�������е�Ԫ���Ƿ�������
	 * 
	 * @param index
	 * @return
	 */
	private boolean kdtEntryRowIsEmpty(int index) {
		if (!FDCHelper.isEmpty(kdtEntrys.getCell(index, COSTACCOUNT).getValue())) {
			return false;
		}
		if (!FDCHelper.isEmpty(kdtEntrys.getCell(index, ADJUSTTYPE).getValue())) {
			return false;
		}
		if (!FDCHelper.isEmpty(kdtEntrys.getCell(index, WORKLOAD).getValue())) {
			return false;
		}
		if (!FDCHelper.isEmpty(kdtEntrys.getCell(index, PRICE).getValue())) {
			return false;
		}
		if (!FDCHelper.isEmpty(kdtEntrys.getCell(index, PRODUCT).getValue())) {
			return false;
		}
		if (!FDCHelper.isEmpty(kdtEntrys.getCell(index, UNIT).getValue())) {
			return false;
		}
		if (!FDCHelper.isEmpty(kdtEntrys.getCell(index, ADJUSTAMOUNT).getValue())) {
			return false;
		}
		if (!FDCHelper.isEmpty(kdtEntrys.getCell(index, DESCRIPTION).getValue())) {
			return false;
		}
		
		/* modified by zhaoqin for R131022-0402 on 2013/12/31 start */
		if (!FDCHelper.isEmpty(kdtEntrys.getCell(index, CHANGE_REASON).getValue())) {
			return false;
		}
		/* modified by zhaoqin for R131022-0402 on 2013/12/31 end */
		
		return true;
	}

	/**
	 * ɾ����Ӧ��Ŀ
	 * 
	 * @param parentID
	 * @param costAccountID
	 * @return
	 */
	private boolean deleteItems(String parentID, String costAccountID) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" delete from T_AIM_AimAimCostItem ");
		builder.appendSql(" where FParentID = '" + parentID + "' ");
		builder.appendSql(" and FCostAccountID = '" + costAccountID + "' ");
		try {
			builder.executeUpdate();
		} catch (BOSException e) {
			logger.info("the SQL statement is: " + builder.getTestSql());
			handUIException(e);
			return false;
		}
		return true;
	}

	/**
	 * ��ȡ��Ŀ��Ϣ
	 * 
	 * @param parentID
	 * @param costAccountID
	 * @return
	 * @throws BOSException
	 */
	private AimAimCostItemCollection fetchItems(String parentID, String costAccountID) throws BOSException {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.getSelector().add("*");
		filter.getFilterItems().add(new FilterItemInfo("parent", parentID, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("costAccountID", costAccountID, CompareType.EQUALS));
		evi.setFilter(filter);
		AimAimCostItemCollection costItemCollection = AimAimCostItemFactory.getRemoteInstance().getAimAimCostItemCollection(evi);
		return costItemCollection;
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		preparePrintPage(kdtEntrys);
		kdtEntrys.getPrintManager().printPreview();
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		preparePrintPage(kdtEntrys);
		kdtEntrys.getPrintManager().print();
	}

	/**
	 * ����ͷ��¼��
	 */
	private void verifyInput() {
		if (FDCHelper.isEmpty(txtNumber.getText())) {
			FDCMsgBox.showInfo("���ݱ��벻��Ϊ��");
			txtNumber.requestFocus();
			SysUtil.abort();
		}
		if (FDCHelper.isEmpty(txtName.getText())) {
			FDCMsgBox.showInfo("�������Ʋ���Ϊ��");
			txtName.requestFocus();
			SysUtil.abort();
		}
	}

	/**
	 * ����¼��¼��
	 */
	private void verifyEntry() {
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			Object costAccount = kdtEntrys.getCell(i, COSTACCOUNT).getValue();
			if (FDCHelper.isEmpty(costAccount)) {
				FDCMsgBox.showInfo("��¼��" + (i + 1) + "����¼�гɱ���Ŀ����Ϊ�գ��������ύ��");
				SysUtil.abort();
			}
			Object adjAmout = kdtEntrys.getCell(i, ADJUSTAMOUNT).getValue();
			if (FDCHelper.isEmpty(adjAmout)) {
				FDCMsgBox.showInfo("��¼��" + (i + 1) + "����¼�е�������Ϊ�գ��������ύ��");
				SysUtil.abort();
			}
		}
	}
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		Object newValue = e.getValue();
		Object oldValue = e.getOldValue();
		IRow row = getDetailTable().getRow(rowIndex);
		// �������
		if (getDetailTable().getColumnKey(colIndex).equals(WORKLOAD) || getDetailTable().getColumnKey(colIndex).equals(PRICE)) {
			BigDecimal workLoad = (BigDecimal) row.getCell(WORKLOAD).getValue();
			BigDecimal price = (BigDecimal) row.getCell(PRICE).getValue();
			if (workLoad == null) {
				workLoad = FDCHelper.ZERO;
			}
			if (price == null) {
				price = FDCHelper.ZERO;
			}
			if (workLoad.compareTo(FDCHelper.ZERO) == 0 && price.compareTo(FDCHelper.ZERO) == 0) {
				row.getCell(ADJUSTAMOUNT).getStyleAttributes().setLocked(false);
				row.getCell(WORKLOAD).setValue(null);
				row.getCell(PRICE).setValue(null);
			} else {
				BigDecimal aimCost = workLoad.multiply(price);
				row.getCell(ADJUSTAMOUNT).setValue(aimCost.setScale(2, BigDecimal.ROUND_HALF_UP));
				row.getCell(ADJUSTAMOUNT).getStyleAttributes().setLocked(true);
			}
		}
		// �ɱ���Ŀ
		if (e.getColIndex() == getDetailTable().getColumn(COSTACCOUNT).getColumnIndex()) {
			CostAccountInfo oldCostAccount = (CostAccountInfo) e.getOldValue();
			CostAccountInfo newCostAccount = (CostAccountInfo) e.getValue();
			if (oldCostAccount != null && newCostAccount != null
					&& oldCostAccount.getId().toString().equals(newCostAccount.getId().toString())) {
				return;
			}
			if (newCostAccount != null) {
				if (!newCostAccount.isIsLeaf() && set.toString().indexOf(newCostAccount.getLongNumber().replaceAll("!", ".") + ".") >= 0) {
					row.getCell(COSTACCOUNT).setValue(oldCostAccount);
					MsgBox.showWarning(this, "��ѡ����ϸ��Ŀ��");
					SysUtil.abort();
				}
				for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
					if (i != rowIndex) {
						CostAccountInfo tempCostAccount = (CostAccountInfo) kdtEntrys.getCell(i, COSTACCOUNT).getValue();
						if (tempCostAccount == null) {
							continue;
						}
						if (newCostAccount.getId().toString().equals(tempCostAccount.getId().toString())) {
							FDCMsgBox.showInfo("һ��Ŀ��������в���ѡ����ͬ�ĳɱ���Ŀ��������ѡ��");
							kdtEntrys.getCell(rowIndex, COSTACCOUNT).setValue(null);
							return;
						}
					}
				}
			}
			if (oldCostAccount != null) {
				if (newCostAccount != null) {// �¾�ֵ���У��¾ɸ���
					deletetViewItems(oldCostAccount.getId().toString());
					initItemsByAccount(newCostAccount);
					kdtEntrys.getCell(rowIndex, COSTACCOUNT_NAME).setValue(newCostAccount.getName());
					kdtItems.removeRows();
					showItems(newCostAccount);
				} else {// ��ֵ�У���ֵ�ޣ���ֵɾ��
					deletetViewItems(oldCostAccount.getId().toString());
					kdtEntrys.getCell(rowIndex, COSTACCOUNT_NAME).setValue(null);
					kdtItems.removeRows();
				}
			} else {
				if (newCostAccount != null) {// ���о��ޣ�����һ��
					initItemsByAccount(newCostAccount);
					kdtEntrys.getCell(rowIndex, COSTACCOUNT_NAME).setValue(newCostAccount.getName());
					showItems(newCostAccount);
				} else {// �¾ɶ��ޣ���������
					kdtItems.removeRows();
				}
			}
		}
	}

	protected void kdtItems_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		Object newValue = e.getValue();
		Object oldValue = e.getOldValue();
		if (e.getColIndex() == kdtItems.getColumn(OVERDESCRIPTION).getColumnIndex()) {
			String newOverDescription = (String) newValue;
			String oldOverDescription = (String) oldValue;
			if (newOverDescription == null && oldOverDescription == null) {
				return;
			}
			if (newOverDescription != null && oldOverDescription != null && newOverDescription.equals(oldOverDescription)) {
				return;
			}
			String costAccountID = (String) kdtItems.getCell(rowIndex, COSTACCOUNTID).getValue();
			ArrayList itemsList = (ArrayList) itemsMap.get(costAccountID);
			String contract = (String) kdtItems.getCell(rowIndex, CONTRACT).getValue();
			String contractID = (String) kdtItems.getCell(rowIndex, CONTRACTID).getValue();
			String changeContract = (String) kdtItems.getCell(rowIndex, CHANGECONTRACT).getValue();
			String changeContractID = (String) kdtItems.getCell(rowIndex, CONCHANGEID).getValue();
			String settleContract = (String) kdtItems.getCell(rowIndex, SETTLECONTRACT).getValue();
			String settleContractID = (String) kdtItems.getCell(rowIndex, CONSETTLEID).getValue();
			if (contract != null) {
				for (int i = 0; i < itemsList.size() - 1; i++) {
					AimAimCostAdjustItems item = (AimAimCostAdjustItems) itemsList.get(i);
					if (contractID.equals(item.getContractID())) {
						item.setOverDescription(newOverDescription);
						itemsList.remove(i);
						itemsList.add(i, item);
					}
				}
			} else if (changeContract != null) {
				for (int i = 0; i < itemsList.size() - 1; i++) {
					AimAimCostAdjustItems item = (AimAimCostAdjustItems) itemsList.get(i);
					if (changeContractID.equals(item.getChangeContractID())) {
						item.setOverDescription(newOverDescription);
						itemsList.remove(i);
						itemsList.add(i, item);
					}
				}
			} else if (settleContract != null) {
				for (int i = 0; i < itemsList.size() - 1; i++) {
					AimAimCostAdjustItems item = (AimAimCostAdjustItems) itemsList.get(i);
					if (settleContractID.equals(item.getSettleContractID())) {
						item.setOverDescription(oldOverDescription);
						itemsList.remove(i);
						itemsList.add(i, item);
					}
				}
			}

		}
	}


	protected void kdtEntrys_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			CostAccountInfo costAccount = (CostAccountInfo) kdtEntrys.getCell(rowIndex, COSTACCOUNT).getValue();
			if (costAccount != null) {
				showItems(costAccount);
			} else {
				kdtItems.removeRows();
			}
		}
	}

	/**
	 * ��ʼ����ͬ���
	 * 
	 * @param costAccount
	 * @return
	 * @throws BOSException
	 */
	private Map initContractSplitData(CostAccountInfo costAccount) throws BOSException {
		Map conSplitMap = new HashMap();
		List conSplitList = new ArrayList();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.getSelector().add(new SelectorItemInfo("*"));
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.name"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.number"));
		filter.getFilterItems().add(new FilterItemInfo("isleaf", Boolean.TRUE, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", costAccount.getId(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.hasSettled", Boolean.FALSE, CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("costAccount.curproject.id", costAccount.getCurProject().getId(), CompareType.EQUALS));
		evi.setFilter(filter);
		ContractCostSplitEntryCollection ccsec = ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(evi);
		for (int i = 0; i < ccsec.size(); i++) {
			if (conSplitMap.containsKey(costAccount.getId().toString())) {
				conSplitList = (List) conSplitMap.get(costAccount.getId().toString());
			} else {
				ContractCostSplitEntryInfo info = ccsec.get(i);
				conSplitList.add(info);
			}
		}
		conSplitMap.put(costAccount.getId(), conSplitList);
		return conSplitMap;
	}

	/**
	 * ��ʼ����ͬ������
	 * 
	 * @param costAccount
	 * @return
	 * @throws BOSException
	 */
	private Map initConChangeSplitData(CostAccountInfo costAccount) throws BOSException {
		Map conChangeSplitMap = new HashMap();
		List conChangeSplitList = new ArrayList();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.getSelector().add(new SelectorItemInfo("*"));
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractChange.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractChange.name"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractChange.number"));
		filter.getFilterItems().add(new FilterItemInfo("isleaf", Boolean.TRUE, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", costAccount.getId(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.hasSettled", Boolean.FALSE, CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("costAccount.curProject.id", costAccount.getCurProject().getId(), CompareType.EQUALS));
		evi.setFilter(filter);
		ConChangeSplitEntryCollection ccsec = ConChangeSplitEntryFactory.getRemoteInstance().getConChangeSplitEntryCollection(evi);
		for (int i = 0; i < ccsec.size(); i++) {
			if (conChangeSplitMap.containsKey(costAccount.getId().toString())) {
				conChangeSplitList = (List) conChangeSplitMap.get(costAccount.getId().toString());
			} else {
				ConChangeSplitEntryInfo info = ccsec.get(i);
				conChangeSplitList.add(info);
			}
		}
		conChangeSplitMap.put(costAccount.getId(), conChangeSplitList);
		return conChangeSplitMap;
	}

	/**
	 * ��ʼ����ͬ������
	 * 
	 * @param costAccount
	 * @return
	 * @throws BOSException
	 */
	private Map initConSettleSplitData(CostAccountInfo costAccount) throws BOSException {
		Map conSettleSplitMap = new HashMap();
		List conSettleSplitList = new ArrayList();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.getSelector().add(new SelectorItemInfo("*"));
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.settlementBill.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.settlementBill.name"));
		evi.getSelector().add(new SelectorItemInfo("parent.settlementBill.number"));
		filter.getFilterItems().add(new FilterItemInfo("isleaf", Boolean.TRUE, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", costAccount.getId(), CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("costAccount.curProject.id", costAccount.getCurProject().getId(), CompareType.EQUALS));
		evi.setFilter(filter);
		SettlementCostSplitEntryCollection scse = SettlementCostSplitEntryFactory.getRemoteInstance()
				.getSettlementCostSplitEntryCollection(evi);
		for (int i = 0; i < scse.size(); i++) {
			if (conSettleSplitMap.containsKey(costAccount.getId().toString())) {
				conSettleSplitList = (List) conSettleSplitMap.get(costAccount.getId().toString());
			} else {
				SettlementCostSplitEntryInfo info = scse.get(i);
				conSettleSplitList.add(info);
			}
		}
		conSettleSplitMap.put(costAccount.getId(), conSettleSplitList);
		return conSettleSplitMap;
	}

	private void verifyKDTEntrysInput() {

	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (!editData.getState().equals(FDCBillStateEnum.SAVED)) {
			this.btnSave.setEnabled(false);
		} else {
			this.btnSave.setEnabled(true);
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		addLine(kdtEntrys);
	}

	/**
	 * ��ָ������������У����������һ�У�
	 * 
	 * @param table
	 */
	protected void addLine(KDTable table) {
		table.addRow();
		super.addLine(table);
	}

	/**
	 * ��ָ������в����У��ڵ�ǰѡ����ǰ���룬�����ǰδѡ���κ��еĻ��������������һ�У�
	 * 
	 * @param table
	 */
	protected void insertLine(KDTable table) {
		if (table.getSelectManager().getActiveRowIndex() < 0) {
			FDCMsgBox.showInfo("��ѡ����");
		}
		table.addRow(table.getSelectManager().getActiveRowIndex());
	}

	private void deletetViewItems(String costAccountID) {
		ArrayList itemsList = (ArrayList) itemsMap.get(costAccountID);
		itemsList.clear();
		itemsMap.remove(costAccountID);
	}
	/**
	 * ��ָ�������ɾ����ǰѡ���� ���Ӹ���ɾ������
	 * 
	 * @param table
	 */
	protected void removeLine(KDTable table) {
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			FDCMsgBox.showInfo("��ѡ����");
			return;
		}
		CostAccountInfo costAccount = (CostAccountInfo) table.getCell(rowIndex, COSTACCOUNT).getValue();
		int oldRowCount = table.getRowCount();
		super.removeLine(table);

		int newRowCount = table.getRowCount();
		if (oldRowCount != newRowCount) {
			if (costAccount != null) {
				deletetViewItems(costAccount.getId().toString());
				kdtItems.removeRows();
			}
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() != null) {
			String id = editData.getId().toString();
			BOSUuid projectUuid = (BOSUuid) this.getUIContext().get("projectId");
			if (projectUuid != null) {
				String projectID = projectUuid.toString();
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql(" select FGenByAdjust from T_AIM_AimCost acc");
				builder.appendSql(" where acc.fversionnumber =");
				builder.appendSql(" (");
				builder.appendSql(" select max(fversionnumber) from T_AIM_AimCost ac");
				builder.appendSql(" where ac.ForgOrProId = '" + projectID + "'");
				builder.appendSql(" )");
				builder.appendSql(" and acc.forgOrProid = '" + projectID + "'");
				IRowSet rowSet;
				rowSet = builder.executeQuery();
				try {
					if (rowSet.next()) {
						String adjustId = rowSet.getString("FGenByAdjust");
						if (adjustId == null) {
							FDCMsgBox.showInfo("��Ŀ��ɱ����������ɵ�Ŀ��ɱ��������°�ģ�������˲�����");
							SysUtil.abort();
						} else {
							if (adjustId.equals(id)) {
								// ɾ�����ɵ�Ŀ��ɱ�
								FilterInfo filter = new FilterInfo();
								filter.getFilterItems().add(new FilterItemInfo("genByAdjust", id));
								filter.getFilterItems().add(new FilterItemInfo("isLastVersion", new Integer(1)));
								AimCostFactory.getRemoteInstance().delete(filter);
								// ����һ�汾��Ϊ���°�
								updateAimCostToLastVersion(projectID);
								super.actionUnAudit_actionPerformed(e);
								btnSubmit.setEnabled(true);
							} else {
								FDCMsgBox.showInfo("��Ŀ��ɱ����������ɵ�Ŀ��ɱ��������°�ģ�������˲�����");
								SysUtil.abort();
							}
						}
					}
				} catch (SQLException ee) {
					logger.info(ee.getMessage(), ee);
					handUIExceptionAndAbort(ee);
				}
			}
		}
	}

	/**
	 * Ŀ��ɱ��汾��ߵ���Ϊ���°汾
	 * 
	 * @param projectID
	 */
	private void updateAimCostToLastVersion(String projectID) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" update T_AIM_AimCost set FIsLastVersion = 1 ");
		builder.appendSql(" where fversionnumber =");
		builder.appendSql(" (");
		builder.appendSql(" select max(fversionnumber) from T_AIM_AimCost ac");
		builder.appendSql(" where ac.ForgOrProId = '" + projectID + "'");
		builder.appendSql(" )");
		builder.appendSql(" and forgOrProid = '" + projectID + "'");
		try {
			builder.executeUpdate();
		} catch (BOSException e) {
			logger.info("the SQL statement is: " + builder.getTestSql());
			handUIExceptionAndAbort(e);
		}
	}

	protected IObjectValue createNewData() {
		AimAimCostAdjustInfo objectValue = new AimAimCostAdjustInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext.getSysContext()
				.getCurrentUser()));
		objectValue.setOrgUnit(this.orgUnitInfo);
		return objectValue;
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AimAimCostAdjustFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	/**
	 * ��ȡ������Ŀ
	 * 
	 * @return
	 */
	private CurProjectInfo getCurProjectInfo() {
		CurProjectInfo curProjectInfo = null;
		BOSUuid projectID = (BOSUuid) this.getUIContext().get("projectId");
		if (projectID != null) {
			try {
				curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectID));
				return curProjectInfo;
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
		} else {
			curProjectInfo = editData.getCurProject();
		}
		return curProjectInfo;
	}
	
	
	/**
	 * ��������Ŀ����֯��ĳ����׶�ȡ���ɱ���Ŀ
	 * 
	 * @param objectId
	 * @param measureStageId
	 * @return �ɱ���ĿlongNumber��key���ɱ���Ŀ��value��TreeMap
	 */
	private Set getAcctMap(String objectId, String measureStageId) {
		set = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("costAccount.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (new CurProjectInfo().getBOSType().equals(BOSUuid.read(objectId).getType())) {
			filter.getFilterItems().add(new FilterItemInfo("costAccount.curProject.id", objectId, CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("costAccount.fullOrgUnit.id", objectId, CompareType.EQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("measureStage.id", measureStageId, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("value", new Integer(1), CompareType.EQUALS));
		AccountStageCollection asCol1;
		try {
			asCol1 = AccountStageFactory.getRemoteInstance().getAccountStageCollection(view);
			for (Iterator iter = asCol1.iterator(); iter.hasNext();) {
				AccountStageInfo info = (AccountStageInfo) iter.next();
				set.add(info.getCostAccount().getLongNumber().replaceAll("!", "."));
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return set;
	}

	private Set set = new HashSet(); //����׶ζ�Ӧ�ĳɱ���Ŀ������
	/**
	 * �ɱ� ��ĿF7
	 */
	private void initPrmtF7() throws BOSException {
		// �ɱ� ��Ŀ
		KDBizPromptBox f7CostAccount = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				String str = super.valueToString(o);
				if (!FDCHelper.isEmpty(o) && o instanceof IObjectValue) {
					return str.replaceAll("!", "\\.");
				}
				return str;
			}
		};
		f7CostAccount.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
		f7CostAccount.setVisible(true);
		f7CostAccount.setEditable(true);
		f7CostAccount.setDisplayFormat("$longNumber$");
		f7CostAccount.setEditFormat("$longNumber$");
		f7CostAccount.setCommitFormat("$longNumber$");

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		CurProjectInfo curProject = (CurProjectInfo) this.prmtCurProject.getValue();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));

				if (set.size() > 0) {
			filter.getFilterItems().add(new FilterItemInfo("longNumber", set, CompareType.INCLUDE));
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("codingnumber"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("curProject.name"));
		f7CostAccount.setEntityViewInfo(view);
		f7CostAccount.setSelectorCollection(sic);

		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(f7CostAccount);
		getDetailTable().getColumn("costAccountNumber").setEditor(cellEditor);
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					CostAccountInfo info = (CostAccountInfo) o;
					if (!FDCHelper.isEmpty(info.getLongNumber())) {
						return info.getLongNumber().replace('!', '.');
					}
				}
				return null;
			}
		});
		getDetailTable().getColumn("costAccountNumber").setRenderer(render);
	}

	/**
	 * ��ƷF7
	 */
	private void initProTypeF7() {
		KDBizPromptBox f7Product = new KDBizPromptBox();
		f7Product.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		filter.getFilterItems().add(
				new FilterItemInfo("id", FDCHelper.getSetByArray(this.productTypeGetter.getProductTypeIds()), CompareType.INCLUDE));
		view.setFilter(filter);
		f7Product.setEntityViewInfo(view);
		f7Product.setEditable(true);
		f7Product.setDisplayFormat("$name$");
		f7Product.setEditFormat("$number$");
		f7Product.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Product);
		kdtEntrys.getColumn(PRODUCT).setEditor(f7Editor);
	}

	/**
	 * ��λF7
	 */
	private void initUnitF7() {
		KDBizPromptBox f7Unit = new KDBizPromptBox();
		f7Unit.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isDisabled", new Integer(0)));
		view.setFilter(filter);
		f7Unit.setEntityViewInfo(view);
		f7Unit.setEditable(true);
		f7Unit.setDisplayFormat("$name$");
		f7Unit.setEditFormat("$number$");
		f7Unit.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Unit);
		kdtEntrys.getColumn(UNIT).setEditor(f7Editor);
	}

	/**
	 * �������������б�
	 * 
	 * @throws BOSException
	 */
	private void initAdjustType() throws BOSException {
		KDComboBox comboAdjustType = new KDComboBox();
		EntityViewInfo adjustTypeView = new EntityViewInfo();
		FilterInfo adjustTypeFilter = new FilterInfo();
		adjustTypeFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		adjustTypeView.setFilter(adjustTypeFilter);
		AdjustTypeCollection adjustTypes = AdjustTypeFactory.getRemoteInstance().getAdjustTypeCollection(adjustTypeView);
		comboAdjustType.addItem(new AdjustTypeInfo());
		for (int i = 0; i < adjustTypes.size(); i++) {
			comboAdjustType.addItem(adjustTypes.get(i));
		}
		comboAdjustType.setSelectedIndex(0);
		ICellEditor f7AdjustTypeEditor = new KDTDefaultCellEditor(comboAdjustType);
		getDetailTable().getColumn(ADJUSTTYPE).setEditor(f7AdjustTypeEditor);
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("creator.id"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("auditor.id"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));

		sic.add(new SelectorItemInfo("entrys.costAccount.id"));
		sic.add(new SelectorItemInfo("entrys.costAccount.name"));
		sic.add(new SelectorItemInfo("entrys.costAccount.number"));
		sic.add(new SelectorItemInfo("entrys.costAccount.longNumber"));

		sic.add(new SelectorItemInfo("entrys.adjustType.id"));
		sic.add(new SelectorItemInfo("entrys.adjustType.name"));

		sic.add(new SelectorItemInfo("entrys.costAccount.curProject.id"));
		sic.add(new SelectorItemInfo("entrys.costAccount.curProject.name"));
		sic.add(new SelectorItemInfo("entrys.costAccount.curProject.number"));

		sic.add(new SelectorItemInfo("entrys.product.id"));
		sic.add(new SelectorItemInfo("entrys.product.name"));

		sic.add(new SelectorItemInfo("entrys.unit.id"));
		sic.add(new SelectorItemInfo("entrys.unit.name"));

		sic.add(new SelectorItemInfo("entrys.workload"));
		sic.add(new SelectorItemInfo("entrys.price"));
		sic.add(new SelectorItemInfo("entrys.adjustAmt"));
		sic.add(new SelectorItemInfo("entrys.description"));
		
		/* modified by zhaoqin for R131022-0402 on 2013/12/31 */
		sic.add(new SelectorItemInfo("entrys.changeReason"));
		
		return sic;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	private final ArrayList orgIdList = new ArrayList();

	/**
	 * ����������
	 * 
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		String curSate = getOprtState();

		if (STATUS_ADDNEW.equals(curSate) || STATUS_EDIT.equals(curSate)) {
			setNumberByCodingRule();
		}
	}

	/**
	 * ���������ݱ���������ñ���
	 * 
	 * @author skyiter_wang
	 * @createDate 2013-12-20
	 */
	private void setNumberByCodingRule() {
		if (editData == null || (((FDCBillInfo) editData).getOrgUnit() == null)) {
			return;
		}
		String currentOrgId = null;
		if (((FDCBillInfo) editData).getOrgUnit() != null) {
			currentOrgId = ((FDCBillInfo) editData).getOrgUnit().getId().toString();
		} else {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if (org == null) {
				org = SysContext.getSysContext().getCurrentOrgUnit();
			}
			currentOrgId = org.getId().toString();
		}

		try {
			boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
			logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
			ArrayList tempOrgIdList = new ArrayList();
			// ��ǰ��֯ID����ѭ���б���ǰ��
			tempOrgIdList.add(currentOrgId);
			if (isRecycleParentOrgNumber) {
				if (FdcCollectionUtil.isEmpty(orgIdList)) {
					ContractUtil.findParentOrgUnitIdToList(null, currentOrgId, orgIdList);
				}

				if (FdcCollectionUtil.isNotEmpty(orgIdList)) {
					tempOrgIdList.addAll(orgIdList);
				}
			}
			// ����������е��ظ�ֵ��Nullֵ
			FdcCollectionUtil.clearDuplicateAndNull(tempOrgIdList);
			logger.info("orgIdList:" + tempOrgIdList);

			String bindingProperty = getBindingProperty();
			String orgId = null;
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			// ѭ����֯�б�����ѯ�Ƿ��б�������о�ʹ��
			for (int j = 0; j < tempOrgIdList.size(); j++) {
				orgId = tempOrgIdList.get(j).toString();
				if (setNumber(iCodingRuleManager, orgId, bindingProperty)) {
					return;
				}
			}

			this.txtNumber.setEnabled(true);
			this.txtNumber.setEditable(true);
		} catch (Exception err) {
			setNumberTextEnabled();
			handUIExceptionAndAbort(err);
		}
	}

	/**
	 * �������жϱ������״̬,�������á��������á������
	 * 
	 * @param iCodingRuleManager
	 * @param orgId
	 * @param bindingProperty
	 * @return
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-12-20
	 */
	protected boolean setNumber(ICodingRuleManager iCodingRuleManager, String orgId, String bindingProperty)
			throws CodingRuleException, EASBizException, BOSException {
		if (editData == null || orgId == null) {
			return false;
		}

		// ������ڱ������
		if (FdcCodingRuleUtil.isExist(iCodingRuleManager, this.editData, orgId, bindingProperty)) {
			CodingRuleInfo codingRuleInfo = FdcCodingRuleUtil.getCodingRule(iCodingRuleManager, editData, orgId,
					bindingProperty);
			this.txtNumber.setEnabled(false);
			this.txtNumber.setEditable(false);
			// ���������������"������ʾ"
			if (codingRuleInfo.isIsAddView()) {

				String costCenterId = null;
				if (editData instanceof FDCBillInfo && ((FDCBillInfo) editData).getOrgUnit() != null) {
					costCenterId = ((FDCBillInfo) editData).getOrgUnit().getId().toString();
				} else {
					costCenterId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
				}
				try {
					// ����ʱ��������Է����ı�ʱ����Ҫȥȡnumber
					if (STATUS_ADDNEW.equals(this.oprtState) || isCodePropertyChanged()) {
						String number = prepareNumberForAddnew(iCodingRuleManager, (FDCBillInfo) editData, orgId,
								costCenterId, bindingProperty);
						// ��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
						prepareNumber(editData, convertNumber(number));

						// ��ȡ��������Ƿ�֧���޸�
						boolean flag = FDCClientUtils.isAllowModifyNumber(this.editData, orgId, bindingProperty);
						this.txtNumber.setEnabled(flag);
						this.txtNumber.setEditable(flag);

						// ��ȡ����������У�������������Map
						fetchValueAttributeMap(orgId, bindingProperty);

						return true;
					}
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}

			
			if (STATUS_ADDNEW.equals(this.oprtState)
					&& FdcCodingRuleUtil.isUseIntermitNumber(iCodingRuleManager, editData, orgId, bindingProperty)) {
				this.txtNumber.setText("");
			}
			return true;
		}

		return false;
	}

	// ��������������ˡ�������ʾ��,��������Ƿ��Ѿ��ظ�
	protected String prepareNumberForAddnew(ICodingRuleManager iCodingRuleManager, FDCBillInfo info, String orgId,
			String costerId, String bindingProperty) throws Exception {

		String number = null;
		FilterInfo filter = null;
		int i = 0;
		do {
			// ��������ظ�����ȡ����
			if (bindingProperty != null) {
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			} else {
				number = iCodingRuleManager.getNumber(editData, orgId);
			}

			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
			}
			i++;
		} while (((IFDCBill) getBizInterface()).exists(filter) && i < 1000);

		return number;
	}

	/**
	 * ת������number����"!"��Ϊ"."
	 * 
	 * @param orgNumber
	 * @author owen_wen 2010-11-23
	 */
	private String convertNumber(String orgNumber) {
		return orgNumber.replaceAll("!", ".");
	}
	
	/**
	 * �������Ƿ�����ϼ���֯���ݵı������
	 * 
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected boolean isRecycleParentOrgNumber() {
		return true;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	

	/**
	 * ���������ñ���ؼ��Ƿ�ɱ༭
	 * 
	 * @author skyiter_wang
	 * @createDate 2013-11-22
	 * @see com.kingdee.eas.framework.client.EditUI#setNumberTextEnabled()
	 */
	protected void setNumberTextEnabled() {
		KDTextField numberCtrl = getNumberCtrl();
		if (numberCtrl == null) {
			return;
		}

		FDCBillInfo billInfo = getFDCBillInfo();
		if (null == billInfo) {
			numberCtrl.setEnabled(true);
			numberCtrl.setEditable(true);

			return;
		}

		OrgUnitInfo org = getOrgUnitInfo(billInfo);
		if (null == org) {
			numberCtrl.setEnabled(true);
			numberCtrl.setEditable(true);
			return;
		}

		// //////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////

		String currentOrgId = org.getId().toString();
		try {
			boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
			logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
			ArrayList tempOrgIdList = new ArrayList();
			// ��ǰ��֯ID����ѭ���б���ǰ��
			tempOrgIdList.add(currentOrgId);
			if (isRecycleParentOrgNumber) {
				if (FdcCollectionUtil.isEmpty(orgIdList)) {
					ContractUtil.findParentOrgUnitIdToList(null, currentOrgId, orgIdList);
				}

				if (FdcCollectionUtil.isNotEmpty(orgIdList)) {
					tempOrgIdList.addAll(orgIdList);
				}
			}
			// ����������е��ظ�ֵ��Nullֵ
			FdcCollectionUtil.clearDuplicateAndNull(tempOrgIdList);
			logger.info("orgIdList:" + tempOrgIdList);

			String bindingProperty = getBindingProperty();
			String orgId = null;
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			// ѭ����֯�б�����ѯ�Ƿ��б�������о�ʹ��
			for (int j = 0; j < tempOrgIdList.size(); j++) {
				orgId = tempOrgIdList.get(j).toString();
				if (setNumberTextEnabled1(iCodingRuleManager, orgId, bindingProperty)) {
					return;
				}
			}

			numberCtrl.setEnabled(true);
			numberCtrl.setEditable(true);
		} catch (Exception err) {
			numberCtrl.setEnabled(true);
			numberCtrl.setEditable(true);

			handUIExceptionAndAbort(err);
		}
	}

	/**
	 * ���������ñ���ؼ��Ƿ�ɱ༭
	 * 
	 * @author skyiter_wang
	 * @throws BOSException
	 * @throws EASBizException
	 * @createDate 2013-11-22
	 * @see com.kingdee.eas.framework.client.EditUI#setNumberTextEnabled()
	 */
	protected boolean setNumberTextEnabled1(ICodingRuleManager iCodingRuleManager, String orgId, String bindingProperty)
			throws EASBizException, BOSException {
		KDTextField numberCtrl = getNumberCtrl();
		if (numberCtrl == null) {
			return false;
		}

		if (editData == null || orgId == null) {
			return false;
		}

		// //////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////

		// ������ڱ������
		if (FdcCodingRuleUtil.isExist(iCodingRuleManager, this.editData, orgId, bindingProperty)) {
			CodingRuleInfo codingRuleInfo = FdcCodingRuleUtil.getCodingRule(iCodingRuleManager, editData, orgId,
					bindingProperty);
			numberCtrl.setEnabled(false);
			numberCtrl.setEditable(false);
			// ���������������"������ʾ"
			if (codingRuleInfo.isIsAddView()) {
				try {
					// ����ʱ��������Է����ı�ʱ����Ҫȥȡnumber
					if (STATUS_ADDNEW.equals(this.oprtState) || isCodePropertyChanged()) {
						// ��ȡ��������Ƿ�֧���޸�
						boolean flag = FDCClientUtils.isAllowModifyNumber(this.editData, orgId, bindingProperty);
						numberCtrl.setEnabled(flag);
						numberCtrl.setEditable(flag);

						return true;
					}
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}

			return true;
		}

		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
}