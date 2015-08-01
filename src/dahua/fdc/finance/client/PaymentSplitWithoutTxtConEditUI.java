/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.util.client.RENoteDataProvider;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PaymentSplitWithoutTxtConEditUI extends
		AbstractPaymentSplitWithoutTxtConEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PaymentSplitWithoutTxtConEditUI.class);

	/**
	 * output class constructor
	 */
	public PaymentSplitWithoutTxtConEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();

		setSplitState();
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		isParentCostAccount();
		super.actionSave_actionPerformed(e);
		// PaymentSplitInfo info =
		// (PaymentSplitInfo)getBizInterface().getValue(new
		// ObjectUuidPK(editData.getId()));
		// if(info.isFivouchered()){
		// FilterInfo filter=new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("parent.id",editData.getId().toString()));
		// filter.getFilterItems().add(new
		// FilterItemInfo("isLeaf",Boolean.TRUE));
		// filter.getFilterItems().add(new FilterItemInfo("accountView",null));
		// if(PaymentSplitEntryFactory.getRemoteInstance().exists(filter)){
		// getAccFromCost();
		// }
		// IDAPTrans dapTrans = new DAPTransImpl(this);
		// CoreBillBaseCollection sourceBillCollection = new
		// CoreBillBaseCollection();
		// info = (PaymentSplitInfo)getBizInterface().getValue(new
		// ObjectUuidPK(editData.getId()));
		// info.setFivouchered(false);
		// sourceBillCollection.add(info);
		// dapTrans.trans(sourceBillCollection);
		// }
	}

	/**
	 * 判断是否为父科目
	 * 
	 * @description
	 * @author 何鹏
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	public void isParentCostAccount() throws Exception {
		// 在启用财务一体化复杂模式下做此判断
		if (isFinacial()) {
			if (kdtEntrys.getRowCount() > 0) {
				for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
					Object obj = kdtEntrys.getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo) && ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (!entry.getCostAccount().isIsLeaf()) {
							MsgBox.showInfo(this, "无文本合同拆分必须拆分到项目的成本科目体系中最明细的科目，不能保存！");
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	protected void getAccFromCost() throws Exception {
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory
				.getRemoteInstance();
		IPaymentSplitEntry iPaymentSplitEntry = PaymentSplitEntryFactory
				.getRemoteInstance();
		PaymentSplitEntryCollection coll;
		CostAccountWithAccountCollection entryColl = null;
		CostAccountWithAccountInfo entryInfo = new CostAccountWithAccountInfo();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("parent.id", editData.getId()));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("seq"));
		view.getSelector().add(new SelectorItemInfo("costAccount.*"));
		view.getSelector().add(new SelectorItemInfo("accountView.*"));
		coll = iPaymentSplitEntry.getPaymentSplitEntryCollection(view);
		int entrySize = coll.size();
		for (int j = 0; j < entrySize; j++) {
			PaymentSplitEntryInfo info = coll.get(j);
			CostAccountInfo costAcc = info.getCostAccount();
			getAccForEntry(info, costAcc, iCostAccountWithAccount, entryColl,
					entryInfo, iPaymentSplitEntry, iCostAccount);
		}
	}

	private void getAccForEntry(PaymentSplitEntryInfo info,
			CostAccountInfo costAcc,
			ICostAccountWithAccount iCostAccountWithAccount,
			CostAccountWithAccountCollection entryColl,
			CostAccountWithAccountInfo entryInfo,
			IPaymentSplitEntry iPaymentSplitEntry, ICostAccount iCostAccount)
			throws Exception {
		EntityViewInfo entryView = new EntityViewInfo();
		FilterInfo entryFilter = new FilterInfo();
		entryFilter.getFilterItems().add(
				new FilterItemInfo("costAccount.id", costAcc.getId()));
		if (!iCostAccountWithAccount.exists(entryFilter)) {
			if (costAcc.getLevel() == 1) {
				MsgBox.showWarning(this, "成本科目未设置与会计科目的对应关系！");
				SysUtil.abort();
			} else {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("level");
				selector.add("parent.*");
				selector.add("curProject.name");
				selector.add("fullOrgUnit.name");
				// if(costAcc.getParent().getId()!= null)
				CostAccountInfo parent = iCostAccount
						.getCostAccountInfo(new ObjectUuidPK(costAcc
								.getParent().getId()), selector);
				getAccForEntry(info, parent, iCostAccountWithAccount,
						entryColl, entryInfo, iPaymentSplitEntry, iCostAccount);
			}
		}
		entryView.setFilter(entryFilter);
		entryColl = iCostAccountWithAccount
				.getCostAccountWithAccountCollection(entryView);
		if (entryColl.size() == 1) {
			entryInfo = entryColl.get(0);
			if (entryInfo.getAccount() != null) {
				info.setAccountView(entryInfo.getAccount());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("accountView");
				iPaymentSplitEntry.updatePartial(info, selector);
			}
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * String costBillID=null; if(getUIContext().get("costBillID")==null){
		 * costBillID=editData.getPaymentBill().getId().toString(); }else{
		 * costBillID = (String)getUIContext().get("costBillID"); }
		 * getUIContext().put("costBillID",costBillID);
		 */
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}
	//删除前检查状态
    private void checkBeforeRemove() throws Exception {
    	if(editData == null || editData.getId() == null || editData.getId().equals("")){
    		return;
    	}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("state",
								FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
			SysUtil.abort();
		}
    }

	// ------------------------------------------------------------
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.finance.client.PaymentSplitWithoutTxtConEditUI.class
				.getName();
	}

	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return PaymentSplitFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return new com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo();
	}

	protected IObjectValue createNewData() {
		PaymentSplitInfo objectValue = new PaymentSplitInfo();
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUserInfo()));
		//取服务端日期
//		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setIsInvalid(false);
		String costBillID = (String) getUIContext().get("costBillID");
		PaymentBillInfo costBillInfo = null;

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("localAmt");
		selectors.add("contractBillId");
		selectors.add("fdcPayReqID");
		selectors.add("actPayAmt");
		selectors.add("actPayLocAmt");
		selectors.add("fdcPayType.number");
		selectors.add("fdcPayType.name");
		selectors.add("payerAccount.id");
		selectors.add("payerAccount.isBank");
		selectors.add("payerAccount.isCash");
		selectors.add("payerAccount.isCashEquivalent");
		selectors.add("company.id");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
		try {
			costBillInfo = PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(costBillID)), selectors);
			txtCompletePrjAmt.setValue(costBillInfo.getActPayLocAmt());
			objectValue.setCompletePrjAmt(costBillInfo.getActPayLocAmt());
			
			// PBG095053  本期成本金额  ＝ （付款申请单上的）本期完工工程量金额
			if (costBillInfo.getFdcPayReqID() != null) {
				SelectorItemCollection selectorReq = new SelectorItemCollection();
				selectorReq.add("id");
				selectorReq.add("completePrjAmt");
				PayRequestBillInfo info = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(
						new ObjectUuidPK(BOSUuid.read(costBillInfo.getFdcPayReqID())), selectorReq);
				if (info.getCompletePrjAmt() != null) {
					txtCompletePrjAmt.setValue(info.getCompletePrjAmt());
					objectValue.setCompletePrjAmt(info.getCompletePrjAmt());
				}
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

		objectValue.setPaymentBill(costBillInfo);
		if (costBillInfo.getCurProject() != null) {
			objectValue.setCurProject(costBillInfo.getCurProject());
		}
		txtCostBillNumber.setText(costBillInfo.getNumber());
		txtAmount.setValue(costBillInfo.getLocalAmt());

		setContractBillId(costBillInfo.getContractBillId());
		objectValue.setIsConWithoutText(true);
		objectValue.setFivouchered(false);
		return objectValue;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		//避免在单据打开（未作修改）直接关闭时，出现保存提示
		this.storeFields();
		getDetailTable().getColumn("amount").setEditor(FDCSplitClientHelper._getTotalCellNumberEdit());
		// 新的更改的地方
		getDetailTable().getColumn("splitScale").setEditor(FDCSplitClientHelper._getTotalCellNumberEdit());
		setSplitBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);

		if (STATUS_ADDNEW.equals(getOprtState())) {
			importCostSplitContract();
			// importCostSplitCntrChange();
			setDisplay();

			IRow row = null;
			FDCSplitBillEntryInfo entry = null;

			BOSUuid costBillId = null;
			BigDecimal amount = null;

			BOSObjectType contractBill = (new ContractBillInfo()).getBOSType();

			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				row = kdtEntrys.getRow(i);
				entry = (FDCSplitBillEntryInfo) row.getUserObject();

				costBillId = entry.getCostBillId();
				if (costBillId != null) {
					amount = entry.getAmount();
					if (amount != null) {
						if (costBillId.getType().equals(contractBill)) {
							row.getCell("contractAmount").setValue(amount);
						} else {
							row.getCell("cntrChangeAmount").setValue(amount);
						}

						entry.setAmount(null);
						row.getCell("amount").setValue(null);
					}
				}
			}
		}
		setOprtState(getOprtState());
	}

	public void onShow() throws Exception {
		if(editData!=null&&editData.getState()!=null&&editData.getState().equals(FDCBillStateEnum.INVALID)){
			actionRemove.setEnabled(false);
			actionRemoveLine.setEnabled(false);
		}
//		else{
//			actionRemove.setEnabled(true);
//			actionRemove.setVisible(true);
//			actionRemoveLine.setEnabled(true);
//			actionRemoveLine.setVisible(true);
//		}
		super.onShow();

		getDetailTable().getColumn("cntrChangeAmount").getStyleAttributes()
				.setHided(true);
		getDetailTable().getColumn("contractAmount").getStyleAttributes()
				.setHided(true);
		/*
		 * actionUnAudit.setEnabled(false); actionAttachment.setVisible(false);
		 * btnAttachment.setVisible(false);
		 */
		// 设置无文本合同的编码及名称
		String contractId = editData.getPaymentBill().getContractBillId();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("number");
		selector.add("name");
		ContractWithoutTextInfo billInfo = ContractWithoutTextFactory
				.getRemoteInstance().getContractWithoutTextInfo(
						new ObjectUuidPK(BOSUuid.read(contractId)), selector);
		txtWithoutTxtConNumber.setText(billInfo.getNumber());
		txtWithoutTxtConName.setText(billInfo.getName());
		actionAttachment.setVisible(true);
		actionAttachment.setEnabled(true);
		btnAttachment.setVisible(true);
		btnAttachment.setEnabled(true);
		
		// modified by zhaoqin for R130704-0262 on 2013/07/29 start
		// 飞鹰提单补单：付款拆分界面要求增加流程审批和套打功能，流程图查看、套打功能没有完成
		this.actionPrint.setEnabled(true);
		this.actionPrint.setVisible(true);
		this.btnPrint.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionPrintPreview.setVisible(true);
		this.btnWorkFlowG.setVisible(true);
		this.btnWorkFlowG.setEnabled(true);
		// modified by zhaoqin for R130704-0262 on 2013/07/29 end

	}

	/**
	 * 
	 * 描述：返回编码控件（子类必须实现）
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-10-13
	 *               <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public void loadFields() {
		super.loadFields();
		setContractBillId(editData.getPaymentBill().getContractBillId());
		setDisplay();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("state");
		sic.add("paymentBill.contractBillId");
		sic.add("paymentBill.billStatus");
		sic.add("Fivouchered");
		sic.add("paymentBill.actPayAmt");
		sic.add("paymentBill.actPayLocAmt");
		sic.add("paymentBill.payerAccount.id");
		sic.add("paymentBill.payerAccount.isBank");
		sic.add("paymentBill.payerAccount.isCash");
		sic.add("paymentBill.fdcPayType.number");
		sic.add("paymentBill.fdcPayType.name");
		sic.add("paymentBill.payerAccount.isCashEquivalent");
		sic.add("paymentBill.company.id");
		
		return setSelectors(sic);
	}

	protected String getSplitBillEntryClassName() {
		return PaymentSplitEntryInfo.class.getName();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		/*
		 * btnRemove.setVisible(true); btnRemove.setEnabled(true);
		 * menuItemRemove.setVisible(true); menuItemRemove.setEnabled(true);
		 */
		actionRemove.setEnabled(true);
		actionRemove.setVisible(true);
		actionRemoveLine.setEnabled(true);
		actionRemoveLine.setVisible(true);
		// btnImpContrSplit.setText("面积刷新");
		// actionImpContrSplit.setVisible(true);
		// actionImpContrSplit.setEnabled(true);

		actionViewContract.setEnabled(true);
		actionViewPayment.setEnabled(true);
		actionViewContract.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
		actionViewPayment.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_linkviewlist"));
	}

	public void updateDetailTable(IObjectCollection entrys) {
		getDetailTable().removeRows(false);
		IRow row;
		FDCSplitBillEntryInfo entry;
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			entry = (FDCSplitBillEntryInfo) iter.next();
			row = getDetailTable().addRow();
			loadLineFields(getDetailTable(), row, entry);
		}
	}

	public void updateAmountColumn(IObjectCollection entrys) {
		// getDetailTable().removeRows(false);
		IRow row;
		FDCSplitBillEntryInfo entry;
		int rowIndex = 0;
		for (Iterator iter = entrys.iterator(); iter.hasNext(); rowIndex++) {
			entry = (FDCSplitBillEntryInfo) iter.next();
			row = getDetailTable().getRow(rowIndex);
			row.getCell("amount").setValue(entry.getAmount());
			row.getCell("apportionValue").setValue(entry.getApportionValue());
			row.getCell("apportionValueTotal").setValue(
					entry.getApportionValueTotal());
			row.getCell("directAmountTotal").setValue(
					entry.getDirectAmountTotal());
			row.getCell("directAmount").setValue(entry.getDirectAmount());
			row.getCell("otherRatioTotal").setValue(entry.getOtherRatioTotal());
			row.getCell("otherRatio").setValue(entry.getOtherRatio());
		}
	}

	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntrys_editStopped(e);
		
		if (editData.getCompletePrjAmt() != null) {
			if (e.getColIndex() == kdtEntrys.getColumnIndex("splitScale") || e.getColIndex() == kdtEntrys.getColumnIndex("amount")) {
				if (getDetailTable().getRowCount() > 0) {
					for (int i = 0; i < getDetailTable().getRowCount(); i++) {
						Object obj = getDetailTable().getRow(i).getUserObject();
						if ((obj instanceof PaymentSplitEntryInfo)
								&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
							PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
							if (entry.getAmount() != null
									&& editData.getCompletePrjAmt() != null) {
								// BigDecimal cmpAmt = FDCHelper.ZERO;
								// cmpAmt =
								// (entry.getAmount().multiply(editData.getCompletePrjAmt())).divide(editData.getPaymentBill().getAmount(),
								// 2, BigDecimal.ROUND_HALF_EVEN);
								// entry.setCompletePrjAmt(cmpAmt);
								// getDetailTable().getRow(i).getCell("completePrjAmt").setValue(cmpAmt);

								entry.setPayedAmt(entry.getAmount());
								getDetailTable().getRow(i).getCell("payedAmt")
										.setValue(entry.getAmount());
							}
						}
					}
				}
			}
		}
	}
	
	protected PaymentSplitEntryCollection getPayEntrys() {
		PaymentSplitEntryCollection coll = new PaymentSplitEntryCollection();
		PaymentSplitEntryInfo entry = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
			coll.add(entry);
		}
		return coll;
		// return editData.getEntrys();
	}

	public void actionViewContract_actionPerformed(ActionEvent e)
			throws Exception {
		if (editData.getPaymentBill() == null) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("selectProjLeafPls"));
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		if (editData.getPaymentBill().getContractBillId() != null) {
			uiContext.put(UIContext.ID, editData.getPaymentBill()
					.getContractBillId());
			if (editData.getPaymentBill().getCurProject() != null
					&& editData.getPaymentBill().getCurProject().getId() != null) {
				uiContext.put("projectId", editData.getPaymentBill()
						.getCurProject().getId());
			}
			IUIWindow testUI = UIFactory
					.createUIFactory(UIFactoryName.NEWTAB)
					.create(
							com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class
									.getName(), uiContext, null, OprtState.VIEW);
			testUI.show();
		}
	}

	public void actionViewPayment_actionPerformed(ActionEvent e)
			throws Exception {
		if (editData.getPaymentBill() == null) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("selectProjLeafPls"));
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		if (editData.getPaymentBill().getContractBillId() != null) {
			uiContext.put(UIContext.ID, editData.getPaymentBill().getId());
			uiContext.put("contractBillId", editData.getPaymentBill()
					.getContractBillId());
			uiContext.put("contractBillNumber", editData.getPaymentBill()
					.getContractNo());
			IUIWindow testUI = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
					.create(PaymentBillEditUI.class.getName(), uiContext, null,
							OprtState.VIEW);
			testUI.show();
		}
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnSplitBotUp || e.getSource() == btnSplitProd
				|| e.getSource() == btnSplitProj
				|| e.getSource() == menuItemSplitBotUp
				|| e.getSource() == menuItemSplitProd
				|| e.getSource() == menuItemSplitProj) {
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo)
							&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (entry.getAmount() != null
								&& editData.getCompletePrjAmt() != null) {
							entry.setPayedAmt(entry.getAmount());
							getDetailTable().getRow(i).getCell("payedAmt")
									.setValue(entry.getAmount());
						}
					}
				}
			}
		}
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_VIEW.equals(oprtType)) {
			if (editData != null && (!editData.isFivouchered())){
				if(editData.getState().equals(FDCBillStateEnum.INVALID)){
					actionVoucher.setVisible(false);
					actionRemove.setEnabled(false);
				}
				else if(editData.getPaymentBill().getBillStatus().equals(BillStatusEnum.PAYED)){
					actionVoucher.setVisible(true);
				}else
					actionVoucher.setVisible(false);
			}
			else{
				if(editData!=null&&editData.getState()!=null&&editData.getState().equals(FDCBillStateEnum.INVALID)){
					actionRemove.setEnabled(false);
				}
				actionVoucher.setVisible(false);
			}
		} else {
			actionVoucher.setVisible(false);
		}
		if(isAdjustVourcherModel()){
			actionVoucher.setVisible(false);
		}
		actionVoucher.setVisible(false);
		actionVoucher.setEnabled(false);
		/*
		 * if(!STATUS_ADDNEW.equals(oprtType)) {
		 * prmtcurProject.setEnabled(false); }
		 */
	}
	
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		if(editData!=null&&editData.getId()!=null){
			List idList = new ArrayList();
			idList.add(editData.getId().toString());
			PaymentSplitFactory.getRemoteInstance().traceData(idList);
			super.actionVoucher_actionPerformed(e);
		}
	}
	
    public  KDMenuItem getAttachMenuItem( KDTable table )
    {
//    	if (getTableForCommon()!=null||getDetailTable()!=null)
//    	{
//    		KDMenuItem item = new KDMenuItem();
//            item.setName("menuItemAttachment");
//            item.setAction(new ActionAttachMent());
//            return item;
//    	}
    	return null;

    }
//  分录附件
    class ActionAttachMent extends ItemAction {
    	public ActionAttachMent() {
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource +"SubAttachMentText");
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource);
//            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.NAME, _tempStr);
         }
//        public void actionPerformed(ActionEvent e) {
//        	showSubAttacheMent(null);
//        }
    }
    
    
    // modified by zhaoqin for R130704-0262 on 2013/07/29 start
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		invokePrintFunction(e, true);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		invokePrintFunction(e, false);
	}

	private void invokePrintFunction(ActionEvent e, boolean print) throws Exception {
		if (StringUtils.isEmpty(editData.getString("id"))) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		RENoteDataProvider data = new RENoteDataProvider(editData.getId().toString());
		KDNoteHelper appHlp = new KDNoteHelper();
		if (print) {
			appHlp.print(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
		} else {
			appHlp.printPreview(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
		}
	}

	/**
	 * 套打对应的目录
	 */
	protected String getTDFileName() {
		return "/bim/fdc/finance/paymentBill";
	}
	// modified by zhaoqin for R130704-0262 on 2013/07/29 end
}