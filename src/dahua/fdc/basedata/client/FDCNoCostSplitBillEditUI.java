/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplit;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.IFDCNoCostSplitBill;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjProductEntriesCollection;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.framework.AbstractBillEntryBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public abstract class FDCNoCostSplitBillEditUI extends
		AbstractFDCNoCostSplitBillEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCNoCostSplitBillEditUI.class);

	private String contractBillId = null;

	private CostSplitBillTypeEnum splitBillType = null;

	protected FDCNoCostSplit fdcNoCostSplit = new FDCNoCostSplit(null);

	private int groupIndex = 0;

	private boolean hasRemove = false;

	private IUIWindow acctUI = null;

	/**
	 * output class constructor
	 */
	public FDCNoCostSplitBillEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		dealWithEmptyDataBeforeSave();
		checkbeforeSave();
		boolean isAddNew=this.getOprtState()!=null&&this.getOprtState().equals(OprtState.ADDNEW);
		// �����¼����˳��
		FDCNoCostSplitBillEntryInfo entry = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();
			entry.setIndex(i + 1);
		}
		// editData.put("entrys", getEntrys());
		super.actionSave_actionPerformed(e);
		actionRemove.setEnabled(true);
        if(isAddNew&&editData.getId()!=null){
        	//�ͷ�����������Ӷ�����
        	String costBillID = (String)getUIContext().get("costBillID");
        	if(costBillID!=null){
        		java.util.List list=new ArrayList();
        		list.add(costBillID);
        		try {
					FDCClientUtils.releaseDataObjectLock(this, list);
					String id=editData.getId().toString();
					list.clear();
					list.add(id);
					FDCClientUtils.requestDataObjectLock(this, list, OprtState.EDIT);
				} catch (Throwable e1) {
					// @AbortException
					logger.error(e1.getMessage(), e1);
				}
        	}
        }
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// �򵥵�ɾ��
		// super.actionRemove_actionPerformed(e);
		// boolean isDelete=MsgBox.sho
		hasRemove = false;
		if (!confirmRemove()) {
			return;
		}
		String tempState = this.getOprtState();
		this.setOprtState("REMOVE");
		IObjectValue val = (IObjectValue) getUIContext().get("CURRENT.VO");
		getUIContext().put("CURRENT.VO", null);
		setDataObject(val);

		try {
			IObjectPK pk = new ObjectUuidPK(this.editData.getId());
			this.getBizInterface().delete(pk);
			hasRemove = true;
		} finally {
			// �ָ�״̬��
			this.setOprtState(tempState);
		}
		// actionExitCurrent_actionPerformed(null);

	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionRemoveLine_actionPerformed(e);

		if (!actionRemoveLine.isEnabled() || !actionRemoveLine.isVisible())
			return;
		// if ((kdtEntrys.getSelectManager().size() == 0) ||
		// isTableColumnSelected(kdtEntrys))
		if ((kdtEntrys.getSelectManager().size() == 0)) {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_NoneEntry"));

			// MsgBox.showInfo(this,"û��ѡ�з�¼���޷�ɾ����");
			return;
		}

		// [begin]����ɾ����¼����ʾ����
		if (confirmRemove()) {
			int top = kdtEntrys.getSelectManager().get().getBeginRow();
			int bottom = kdtEntrys.getSelectManager().get().getEndRow();

			int idx = 0;
			int idx1, idx2;

			boolean isTrue = false;
			FDCNoCostSplitBillEntryInfo entry = null;

			for (int i = bottom; i >= top; i--) {
				idx = i;

				idx1 = idx;
				idx2 = idx;

				// �������һ��
				isTrue = false;
				for (int j = i + 1; j < kdtEntrys.getRowCount(); j++) {
					entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(j)
							.getUserObject();
					if (entry.getLevel() == 0) {
						idx2 = j - 1;
						isTrue = true;
						break;
					}
				}
				if (!isTrue) {
					idx2 = kdtEntrys.getRowCount() - 1;
				}
				if (idx2 < idx) {
					idx2 = idx;
				}

				// �����һ����ǰɾ����ֱ��Level=0
				for (int j = idx2; j >= 0; j--) {
					idx1 = j;

					entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(j)
							.getUserObject();
					if (entry.getLevel() == 0) {
						removeEntry(j);
						break;
					} else {
						removeEntry(j);
					}
				}

				// i=idx1-1;
				i = idx1;
			}
		}

		if (kdtEntrys.getRowCount() > 0) {
			calcAmount(0);
		} else {
			txtSplitedAmount.setValue(FDCHelper.ZERO);
		}

		// ������ jelon 12/28/2006
		int idx = 0;
		FDCNoCostSplitBillEntryInfo entry = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();
			if (entry.getLevel() == 0) {
				if (entry.getIndex() > idx) {
					idx = entry.getIndex();
				}
			}
		}
		groupIndex = idx;
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// super.actionAudit_actionPerformed(e);
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SAVED, "cantAudit");
		String id = getSelectBOID();
		if (id != null) {
			((IFDCNoCostSplitBill) getBizInterface()).audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		editData.setState(FDCBillStateEnum.AUDITTED);
		actionAudit.setEnabled(false);
		actionUnAudit.setEnabled(true);
		bizPromptAuditor.setValue(SysContext.getSysContext()
				.getCurrentUserInfo());
		dateAuditTime.setValue(DateTimeUtils.truncateDate(new Date()));
		editData.setAuditor(SysContext.getSysContext().getCurrentUserInfo());
		editData.setAuditTime(DateTimeUtils.truncateDate(new Date()));
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, "cantUnAudit");
		String id = getSelectBOID();
		if (id != null) {
			((IFDCNoCostSplitBill) getBizInterface()).unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		editData.setState(FDCBillStateEnum.SAVED);
		actionUnAudit.setEnabled(false);
		actionAudit.setEnabled(true);
		bizPromptAuditor.setValue(null);
		dateAuditTime.setValue(null);
		editData.setAuditor(null);
		editData.setAuditTime(null);
		/*
		 * loadFields(); this.storeFields(); this.initOldData(this.editData);
		 */
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		// super.actionAddLine_actionPerformed(e);
		// �շ�������ʵ��
	}

	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionInsertLine_actionPerformed(e);
	}

	protected KDTextField getNumberCtrl() {
		// TODO �Զ����ɷ������
		return txtNumber;
	}

	protected void setSplitState() {

		BigDecimal amount;
		if (txtAmount.getValue() == null) {
			amount = FDCHelper.ZERO;
		} else {
			amount = new BigDecimal(txtAmount.getValue().toString());
		}

		BigDecimal amtSplit;
		if (txtSplitedAmount.getValue() == null) {
			amtSplit = FDCHelper.ZERO;
		} else {
			amtSplit = new BigDecimal(txtSplitedAmount.getValue().toString());
		}

		if (amtSplit.compareTo(amount) == 0) {
			editData.setSplitState(CostSplitStateEnum.ALLSPLIT);
		} else {
			if (amtSplit.compareTo(FDCHelper.ZERO) == 0) {
				editData.setSplitState(CostSplitStateEnum.NOSPLIT);
			} else {
				editData.setSplitState(CostSplitStateEnum.PARTSPLIT);
			}
		}

	}

	public void actionAcctSelect_actionPerformed(ActionEvent arg0)
			throws Exception {
		// TODO �Զ����ɷ������
		super.actionAcctSelect_actionPerformed(arg0);

		/*
		 * UIContext uiContext = new UIContext(this); IUIWindow uiWin =
		 * UIFactory.createUIFactory(UIFactoryName.MODEL). create(
		 * com.kingdee.eas.fdc.basedata.client.NoCostSplitAcctUI.class.getName(),
		 * uiContext, null , null); uiWin.show();
		 */

		if (acctUI == null) {
			Map map = getUIContext();
			CurProjectInfo curProject = null;
			String contractId = null;
			if (map.get("CURRENT.VO") != null) {
				if (map.get("CURRENT.VO") instanceof ConNoCostSplitInfo) {
					ConNoCostSplitInfo info = (ConNoCostSplitInfo) map
							.get("CURRENT.VO");
					contractId = info.getContractBill().getId().toString();
				}
				if (map.get("CURRENT.VO") instanceof ConChangeNoCostSplitInfo) {
					ConChangeNoCostSplitInfo noCostInfo = (ConChangeNoCostSplitInfo) map
							.get("CURRENT.VO");
					if (noCostInfo.getCurProject() != null) {
						curProject = noCostInfo.getCurProject();
					} else if (noCostInfo.getCurProject() == null) {
						contractId = noCostInfo.getId().toString();
					}
				}
				if (map.get("CURRENT.VO") instanceof PaymentNoCostSplitInfo) {
					PaymentNoCostSplitInfo paymentInfo = (PaymentNoCostSplitInfo) map
							.get("CURRENT.VO");
					curProject = paymentInfo.getCurProject();
				}
			}
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("*");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", contractId));
			view.setFilter(filter);
			ContractBillCollection col = ContractBillFactory
					.getRemoteInstance().getContractBillCollection(view);
			if (col.size() == 1) {
				if (col.get(0) instanceof ContractBillInfo) {
					curProject = col.get(0).getCurProject();
				}
			}
			UIContext uiContext = new UIContext(this);
			// uiContext.put("curProject",this.curProject);
			// �����editDataȡ����Щ�����Ϊ��ֵ
			// uiContext.put("curProject",editData.getCurProject());
			uiContext.put("curProject", curProject);
			acctUI = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
					com.kingdee.eas.fdc.basedata.client.NoCostSplitAcctUI.class
							.getName(), uiContext, null, null);
		} else {
			((NoCostSplitAcctUI) acctUI.getUIObject())
					.actionNoneSelect_actionPerformed(null);
		}
		acctUI.show();
		IUIWindow uiWin = acctUI;
		AccountViewCollection accountViewCollection = null;
		CurProjectInfo curProjectInfo = null;
		if (((NoCostSplitAcctUI) uiWin.getUIObject()).isOk()) {
			HashMap map = ((NoCostSplitAcctUI) uiWin.getUIObject()).getData();
			accountViewCollection = (AccountViewCollection) map
					.get("accountViewCollection");
			curProjectInfo = (CurProjectInfo) map.get("curProject");
		} else {
			return;
		}

		for (Iterator iter = accountViewCollection.iterator(); iter.hasNext();) {
			AccountViewInfo acct = (AccountViewInfo) iter.next();
			FDCNoCostSplitBillEntryInfo entry = null;
			// �жϿ�Ŀ�Ƿ����
			boolean isExist = false;
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
						.getUserObject();

				// �жϹ��̼���Ŀ�Ƿ����
				if (entry.getLevel() == 0
						&& entry.getAccountView().getId().equals(acct.getId())
						&& entry.getCurProject().getId().equals(
								curProjectInfo.getId())) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {

				// entry=new FDCNoCostSplitBillEntryInfo();
				entry = (FDCNoCostSplitBillEntryInfo) createNewDetailData(kdtEntrys);
				entry.setCurProject(curProjectInfo);
				entry.setAccountView(acct);
				entry.setLevel(0);
				entry.setIsLeaf(true); // Jelon Dec 11, 2006
				entry.setAmount(FDCHelper.ZERO);

				// ������ jelon 12/27/2006
				groupIndex++;
				entry.setIndex(groupIndex);

				IRow row = addEntry(entry);

				setDisplay(row.getRowIndex());
			}
		}
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#actionSplitBotUp_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplitBotUp_actionPerformed(ActionEvent arg0)
			throws Exception {
		// TODO �Զ����ɷ������
		super.actionSplitBotUp_actionPerformed(arg0);

		splitCost(CostSplitTypeEnum.BOTUPSPLIT);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#actionSplitProd_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplitProd_actionPerformed(ActionEvent arg0)
			throws Exception {
		// TODO �Զ����ɷ������
		super.actionSplitProd_actionPerformed(arg0);

		splitCost(CostSplitTypeEnum.PRODSPLIT);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#actionSplitProj_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplitProj_actionPerformed(ActionEvent arg0)
			throws Exception {
		// TODO �Զ����ɷ������
		super.actionSplitProj_actionPerformed(arg0);

		splitCost(CostSplitTypeEnum.PROJSPLIT);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#initWorkButton()
	 */
	protected void initWorkButton() {
		// TODO �Զ����ɷ������
		super.initWorkButton();

		actionAcctSelect.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_evaluatecortrol"));
		actionSplitProj.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_showparent"));
		actionSplitBotUp.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_showsubflow"));
		actionSplitProd.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_citetree"));
		actionImpContrSplit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_collect"));
		/*
		 * this.btnAcctSelect.setIcon(EASResource.getIcon("imgTbtn_evaluatecortrol"));
		 * this.btnSplitProj.setIcon(EASResource.getIcon("imgTbtn_showparent"));
		 * this.btnSplitBotUp.setIcon(EASResource.getIcon("imgTbtn_showsubflow"));
		 * this.btnSplitProd.setIcon(EASResource.getIcon("imgTbtn_citetree"));
		 * this.btnImpContrSplit.setIcon(EASResource.getIcon("imgTbtn_collect"));
		 * 
		 * this.menuItemAcctSelect.setIcon(EASResource.getIcon("imgTbtn_evaluatecortrol"));
		 * this.menuItemSplitProj.setIcon(EASResource.getIcon("imgTbtn_showparent"));
		 * this.menuItemSplitBotUp.setIcon(EASResource.getIcon("imgTbtn_showsubflow"));
		 * this.menuItemSplitProd.setIcon(EASResource.getIcon("imgTbtn_citetree"));
		 * this.menuItemImpContrSplit.setIcon(EASResource.getIcon("imgTbtn_collect"));
		 * 
		 * this.btnAcctSelect.setEnabled(true);
		 * this.btnSplitProj.setEnabled(true);
		 * this.btnSplitBotUp.setEnabled(true);
		 * this.btnSplitProd.setEnabled(true);
		 */
		actionAcctSelect.setEnabled(true);
		actionSplitProj.setEnabled(true);
		actionSplitProd.setEnabled(true);
		actionSplitBotUp.setEnabled(true);
		actionImpContrSplit.setEnabled(false);// ֻ�б��������õ�
		actionImpContrSplit.setVisible(false);

		btnAuditResult.setVisible(false);
		btnAuditResult.setEnabled(false);
		btnMultiapprove.setVisible(false);
		btnMultiapprove.setEnabled(false);
		btnNextPerson.setVisible(false);
		btnNextPerson.setEnabled(false);
		actionNextPerson.setVisible(false);
		actionNextPerson.setEnabled(false);
		actionMultiapprove.setVisible(false);
		actionMultiapprove.setEnabled(false);
		actionAuditResult.setVisible(false);
		actionAuditResult.setEnabled(false);
		// TODO ��ˡ������
		/*
		 * btnAudit.setVisible(false); btnUnAudit.setVisible(false);
		 */

		// btnRemove.setVisible(false);
		// �Ѹ����������ص�--��������
		// actionAttachment.setEnabled(false);
		// actionAttachment.setVisible(false);

		menuItemAcctSelect.setAccelerator(KeyStroke
				.getKeyStroke("ctrl shift C"));
		menuItemAcctSelect.setText(menuItemAcctSelect.getText() + "(C)");
		menuItemAcctSelect.setMnemonic('C');

		menuItemSplitProj
				.setAccelerator(KeyStroke.getKeyStroke("ctrl shift A"));
		menuItemSplitProj.setText(menuItemSplitProj.getText() + "(A)");
		menuItemSplitProj.setMnemonic('A');

		menuItemSplitBotUp.setAccelerator(KeyStroke
				.getKeyStroke("ctrl shift L"));
		menuItemSplitBotUp.setText(menuItemSplitBotUp.getText() + "(L)");
		menuItemSplitBotUp.setMnemonic('L');

		menuItemSplitProd
				.setAccelerator(KeyStroke.getKeyStroke("ctrl shift P"));
		menuItemSplitProd.setText(menuItemSplitProd.getText() + "(P)");
		menuItemSplitProd.setMnemonic('P');

		menuItemImpContrSplit.setAccelerator(KeyStroke
				.getKeyStroke("ctrl shift I"));
		menuItemImpContrSplit.setText(menuItemImpContrSplit.getText() + "(I)");
		menuItemImpContrSplit.setMnemonic('I');
	}

	protected IRow addEntry(IObjectValue detailData) {
		/*
		 * if(table == null) { return; } IObjectValue detailData =
		 * createNewDetailData(table);
		 */

		IRow row = kdtEntrys.addRow();
		loadLineFields(kdtEntrys, row, detailData);
		afterAddLine(kdtEntrys, detailData);

		return row;
	}

	protected IRow insertEntry(int rowIndex, IObjectValue detailData) {
		/*
		 * if(table == null) { return; } IObjectValue detailData =
		 * createNewDetailData(table);
		 */
		IRow row = null;

		/*
		 * if (table.getSelectManager().size() > 0) { int top =
		 * table.getSelectManager().get().getTop();
		 * 
		 * if (isTableColumnSelected(table)) { row = table.addRow(); } else {
		 * row = table.addRow(top); } } else { row = table.addRow(); }
		 */
		row = kdtEntrys.addRow(rowIndex);

		loadLineFields(kdtEntrys, row, detailData);
		// afterInsertLine(table, detailData);

		return row;
	}

	protected void removeEntry(int idxRow) {

		IObjectValue detailData = (IObjectValue) kdtEntrys.getRow(idxRow)
				.getUserObject();
		kdtEntrys.removeRow(idxRow);

		IObjectCollection collection = (IObjectCollection) kdtEntrys
				.getUserObject();
		if (collection == null) {
			// logger.error("collection not be binded to table");
			return;
		} else {
			if (detailData != null) {
				collection.removeObject(detailData);
			}
		}

	}

	/**
	 * ������������ʾ
	 * 
	 * @return
	 * @author:jelon ����ʱ�䣺2006-10-19
	 *               <p>
	 */
	protected void setDisplay() {
		FDCNoCostSplitBillEntryInfo entry = null;
		IRow row = null;

		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			row = kdtEntrys.getRow(i);
			entry = (FDCNoCostSplitBillEntryInfo) row.getUserObject();

			if (entry.getLevel() == 0) {
				setDisplay(i);
			}
		}
	}

	private void setDisplay(int rowIndex) {
		FDCNoCostSplitBillEntryInfo topEntry = (FDCNoCostSplitBillEntryInfo) kdtEntrys
				.getRow(rowIndex).getUserObject();
		int topLevel = topEntry.getLevel();
		AccountViewInfo topAcct = topEntry.getAccountView();

		FDCNoCostSplitBillEntryInfo entry = null;
		IRow row = null;
		ICell cell = null;
		String display = null;
		int level = 0;

		AccountViewInfo acct = null;
		CurProjectInfo proj = null;

		for (int i = rowIndex; i < kdtEntrys.getRowCount(); i++) {
			row = kdtEntrys.getRow(i);
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();
			row.getCell("directAmount").setValue(entry.getDirectAmount());
			level = entry.getLevel();

			acct = entry.getAccountView();
			proj = entry.getCurProject();

			if (level >= topLevel) {
				if (level == topLevel && i != rowIndex) {
					// ��һ��������
					break;
				}

				// ���롢����
				if (entry.getCurProject() != null) {
					// ����
					row.getCell("curProject.number").setValue(
							entry.getCurProject().getLongNumber().replace('!',
									'.'));
					row.getCell("accountView.number").setValue(
							entry.getAccountView().getNumber());

					// ����
					if (level == 0) {
						row.getCell("curProject.name").setValue(
								entry.getCurProject().getDisplayName().replace(
										'_', '\\'));
						row.getCell("accountView.name").setValue(
								entry.getAccountView().getDisplayName()
										.replace('_', '\\'));

					} else if (entry.getSplitType() == CostSplitTypeEnum.PRODSPLIT
							&& entry.isIsLeaf()) {
						// ��Ʒ�����ϸ
						row.getCell("curProject.number").setValue("");
						row.getCell("accountView.number").setValue("");
						row.getCell("curProject.name").setValue("");
						row.getCell("accountView.name").setValue("");

					} else if (entry.isIsAddlAccount()) {
						// ���ӿ�Ŀ��ֱ�ӷ���
						row.getCell("curProject.number").setValue("");
						row.getCell("curProject.name").setValue("");
						// row.getCell("accountView.number").setValue(entry.getAccountView().getLongNumber());
						row.getCell("accountView.name").setValue(
								entry.getAccountView().getName());

					} else {
						row.getCell("curProject.name").setValue(
								entry.getCurProject().getName());
						row.getCell("accountView.name").setValue(
								entry.getAccountView().getName());
					}

					// TODO ��������
					if (level >= topLevel) {
						CellTreeNode node = new CellTreeNode();
						cell = row.getCell("curProject.name");
						// �ڵ��ֵ
						node.setValue(cell.getValue());
						// �Ƿ����ӽڵ�
						// if(entry.getAccountView().getLongNumber().equals(topAcct.getLongNumber())
						// && !entry.getCurProject().isIsLeaf()){
						/*
						 * if(entry.getAccountView().getLongNumber().replace('!','.').equals(topAcct.getLongNumber().replace('!','.')) &&
						 * !entry.getCurProject().isIsLeaf() &&
						 * !isProdSplitLeaf(entry)){
						 */
						if (!entry.isIsLeaf()
								&& !entry.getCurProject().isIsLeaf()
								&& entry.getAccountView().getLongNumber()
										.replace('!', '.').equals(
												topAcct.getLongNumber()
														.replace('!', '.'))) {
							node.setHasChildren(true);
						} else {
							node.setHasChildren(false);
						}
						// node.setHasChildren(!entry.isIsAddlAccount());
						// �ڵ��������
						node.setTreeLevel(entry.getLevel());
						cell.getStyleAttributes().setLocked(false);
						cell.setValue(node);

						if (level != topLevel) {
							node = new CellTreeNode();
							cell = row.getCell("accountView.name");
							// �ڵ��ֵ
							node.setValue(cell.getValue());
							// �Ƿ����ӽڵ�
							node
									.setHasChildren(!entry.getAccountView()
											.isIsLeaf()
											|| (!entry.isIsLeaf()
													&& entry.getAccountView()
															.isIsLeaf()
													&& entry.getSplitType() != null && entry
													.getSplitType()
													.equals(
															CostSplitTypeEnum.PRODSPLIT)));
							// �ڵ��������
							// node.setTreeLevel(entry.getAccountView().getLevel()-topAcct.getLevel());
							if (entry.isIsLeaf()
									&& entry.getSplitType() != null
									&& entry.getSplitType().equals(
											CostSplitTypeEnum.PRODSPLIT)) {
								node.setTreeLevel(entry.getAccountView()
										.getLevel()
										- topAcct.getLevel() + 1);
							} else {
								node.setTreeLevel(entry.getAccountView()
										.getLevel()
										- topAcct.getLevel());
							}

							cell.getStyleAttributes().setLocked(false);
							cell.setValue(node);
						}
						// end
					}
				}

				// ��ɫ
				if (level == 0) {
					row.getStyleAttributes().setBackground(new Color(0xF6F6BF));
					row.getCell("amount").getStyleAttributes().setBackground(
							new Color(0xFFFFFF));
				} else {
					if (entry.getSplitType() != null
							&& entry.getSplitType().equals(
									CostSplitTypeEnum.PRODSPLIT)
							&& entry.getProduct() == null) {
						row.getStyleAttributes().setBackground(
								new Color(0xF5F5E6));
					} else {
						row.getStyleAttributes().setBackground(
								new Color(0xE8E8E3));
					}
					row.getCell("amount").getStyleAttributes().setLocked(true);

					// ���ӿ�Ŀ��������¼���
					/*
					 * if(entry.isIsAddlAccount() &&
					 * entry.getAccountView().isIsLeaf() &&
					 * entry.getCurProject().isIsLeaf()){ if(entry.isIsLeaf() &&
					 * entry.getSplitType()!=null &&
					 * entry.getSplitType().equals(CostSplitType.PRODSPLIT)){
					 * }else{
					 * row.getCell("amount").getStyleAttributes().setLocked(false);
					 * row.getCell("amount").getStyleAttributes().setBackground(new
					 * Color(0xFFFFFF)); } }
					 */
					if (entry.isIsAddlAccount() && proj != null
							&& proj.isIsLeaf() && acct != null
							&& acct.isIsLeaf() && !isProdSplitLeaf(entry)) {
						row.getCell("amount").getStyleAttributes().setLocked(
								false);
						row.getCell("amount").getStyleAttributes()
								.setBackground(new Color(0xFFFFFF));
					}
				}

				// ֱ�ӹ���
				initDirectAssign(row);

			} else {
				break;
			}

		}

		// ������׼
		setStandard(rowIndex);
	}

	/**
	 * ���������÷�̯��׼
	 * 
	 * @return
	 * @author:jelon ����ʱ�䣺2006-10-19
	 *               <p>
	 */
	private void setStandard(int index) {
		FDCNoCostSplitBillEntryInfo curEntry = (FDCNoCostSplitBillEntryInfo) kdtEntrys
				.getRow(index).getUserObject();

		int level = curEntry.getLevel();

		// 1. ��ָ��ݽڵ㣬ʹ�ò��������Ϊ������׼
		if (level == 0) {
			// Jelon Dec 13, 2006
			/*
			 * if(curEntry.getSplitType()!=null &&
			 * curEntry.getSplitType()!=CostSplitType.MANUALSPLIT){
			 * kdtEntrys.getRow(index).getCell("standard").setValue(curEntry.getSplitType().toString()); }
			 */
			if (curEntry.getSplitType() == null
					|| curEntry.getSplitType() == CostSplitTypeEnum.MANUALSPLIT) {
				kdtEntrys.getRow(index).getCell("standard").setValue("");
			} else {
				kdtEntrys.getRow(index).getCell("standard").setValue(
						curEntry.getSplitType().toString());
			}
		}

		// 2. ������ֽ�㣬ʹ�ø����ķ�̯������Ϊ������׼
		String apptType = null;
		if (curEntry.getApportionType() != null) {
			apptType = curEntry.getApportionType().getName();
		}
		FDCNoCostSplitBillEntryInfo entry = null;
		IRow row = null;

		for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
			row = kdtEntrys.getRow(i);
			entry = (FDCNoCostSplitBillEntryInfo) row.getUserObject();

			if (entry.getLevel() == level + 1) {
				if (entry.isIsAddlAccount()) {
					if (entry.isIsLeaf()
							&& entry.getSplitType() != null
							&& entry.getSplitType().equals(
									CostSplitTypeEnum.PRODSPLIT)) {
						row.getCell("standard").setValue(apptType);
					} else {
						row.getCell("standard").setValue("ֱ�ӷ���");
					}
				} else {
					row.getCell("standard").setValue(apptType);
				}

				if (!entry.isIsLeaf()) {
					setStandard(i);
				}
			} else if (entry.getLevel() <= level) {
				break;
			}

		}
	}

	public void initDirectAssign(IRow row) {
		FDCNoCostSplitBillEntryInfo entry;
		entry = (FDCNoCostSplitBillEntryInfo) row.getUserObject();

		boolean isTrue = false;
		/*
		 * if(entry.getSplitType()==null ||
		 * entry.getSplitType().equals(CostSplitType.MANUALSPLIT)){
		 * if(entry.getAccountView().isIsLeaf() &&
		 * entry.getCurProject().isIsLeaf()){ isTrue=true; } }else
		 * if(entry.getSplitType().equals(CostSplitType.PROJSPLIT) ||
		 * entry.getSplitType().equals(CostSplitType.BOTUPSPLIT)){
		 * if(entry.getAccountView().isIsLeaf() &&
		 * entry.getCurProject().isIsLeaf()){ isTrue=true; } }else
		 * if(entry.getSplitType().equals(CostSplitType.PRODSPLIT)){ // }else{
		 * isTrue=true; }
		 */
		isTrue = isProdSplitEnabled(entry);

		if (!isTrue || !entry.isIsLeaf()) {
			row.getCell("product").getStyleAttributes().setLocked(true);
			return;
		} else {
			row.getCell("product").getStyleAttributes().setBackground(
					new Color(0xFFFFFF));
		}

		// ��Ʒ����
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.id", entry.getCurProject()
						.getId().toString()));
		// filter.getFilterItems().add(new FilterItemInfo("isEnabled",
		// Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("productType.isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("isAccObj", Boolean.TRUE));
		view.setFilter(filter);

		/*
		 * view.getSelector().add("productType.id");
		 * view.getSelector().add("productType.name");
		 */
		view.getSelector().add("productType.*"); // ʹ�á�*���������б��е����ݺͷ�¼�е�����ƥ��
		try {
			ProjProductEntriesCollection coll = null;
			// CurProjProductEntriesInfo item=null;
			coll = CurProjProductEntriesFactory.getRemoteInstance()
					.getProjProductEntriesCollection(view);

			ProductTypeCollection collProd = new ProductTypeCollection();

			// ����
			ProductTypeInfo prod = new ProductTypeInfo();

			prod.setName(null);
			// prod.setName("��");

			collProd.insertObject(-1, prod);

			// ��ǰ��Ŀȫ����Ʒ
			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				prod = ((CurProjProductEntriesInfo) iter.next())
						.getProductType();
				if (prod != null) {
					collProd.add(prod);
				}
			}

			KDComboBox cbo = new KDComboBox();
			// cbo.removeAllItems();
			// cbo.addItem(null);
			cbo.addItems(collProd.toArray());

			// KDTDefaultCellEditor billTypeEditor = new
			// KDTDefaultCellEditor(cbo);
			row.getCell("product").setEditor(new KDTDefaultCellEditor(cbo));
			// row.getCell("product").setValue(entry.getProduct());

		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractCostSplitEditUI#kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent)
	 */
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		// TODO �Զ����ɷ������
		// super.kdtEntrys_editStopped(e);

		super.kdtEntrys_editStopped(e);

		if (e.getColIndex() == kdtEntrys.getColumnIndex("amount")) {
			if (e.getValue() != e.getOldValue()) {

				BigDecimal amount = FDCHelper.ZERO;
				amount = amount.setScale(10);

				FDCNoCostSplitBillEntryInfo entry;
				entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(
						e.getRowIndex()).getUserObject();
				// amount=new
				// BigDecimal(kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue().toString());
				Object cellVal = kdtEntrys.getRow(e.getRowIndex()).getCell(
						"amount").getValue();
				if (cellVal != null) {
					amount = new BigDecimal(cellVal.toString());
				}
				entry.setAmount(amount);

				// ��̯
				/*
				 * CostSplitType splitType=entry.getSplitType();
				 * calcApportionAmount(e.getRowIndex(),splitType);
				 */
				apptAmount(e.getRowIndex());

				// ����
				if (entry.getLevel() == 0) {
					calcAmount(0);

				} else if (entry.isIsLeaf()) {

					// ������ϸ��Ŀ¼�������бȽ�ת�� Jelon Dec 12, 2006

					int idx = e.getRowIndex();
					int idx1 = idx;
					int idx2 = idx;

					int level = entry.getLevel();

					IRow row = null;

					// ���һ��
					for (int i = idx + 1; i < kdtEntrys.getRowCount(); i++) {
						row = kdtEntrys.getRow(i);
						entry = (FDCNoCostSplitBillEntryInfo) row
								.getUserObject();

						if (entry.getLevel() == level) {
							idx2 = i;

						} else if (entry.getLevel() < level) {
							break;
						}
					}

					// ��ǰ����������
					amount = FDCHelper.ZERO;

					for (int i = idx2; i >= 0; i--) {
						row = kdtEntrys.getRow(i);
						entry = (FDCNoCostSplitBillEntryInfo) row
								.getUserObject();

						if (entry.getLevel() == level) {
							if (entry.getAmount() != null) {
								amount = amount.add(entry.getAmount());
							}
							idx1 = i;

						} else if (entry.getLevel() == level - 1) {
							idx = i;

							BigDecimal amountTotal = FDCHelper.ZERO;
							if (entry.getAmount() != null) {
								amountTotal = entry.getAmount();
							}

							// �����ϸ��Ŀ���Ƿ�����ϼ���Ŀ�Ľ��
							if (amountTotal.compareTo(amount) == 0) {

								// ��̯�ϼ�Ϊ100
								amount = new BigDecimal(100);
								if (amountTotal.compareTo(FDCHelper.ZERO) == 0) {
									amount = FDCHelper.ZERO;
								} else {
									amountTotal = amountTotal.divide(
											new BigDecimal(100), 4,
											BigDecimal.ROUND_HALF_EVEN);
								}
								entry.setOtherRatioTotal(amount);
								row.getCell("otherRatioTotal").setValue(amount);

								// ����̯��ϸ�İٷֱ�
								BigDecimal apptValue = null;
								for (int j = idx1; j <= idx2; j++) {
									row = kdtEntrys.getRow(j);
									entry = (FDCNoCostSplitBillEntryInfo) row
											.getUserObject();

									if (entry.getAmount() != null) {
										amount = entry.getAmount();
									} else {
										amount = FDCHelper.ZERO;
									}

									apptValue = FDCHelper.ZERO;
									if (amountTotal.compareTo(FDCHelper.ZERO) != 0) {
										apptValue = amount.divide(amountTotal,
												2, BigDecimal.ROUND_HALF_EVEN);
									}

									entry.setApportionValue(apptValue);
									row.getCell("apportionValue").setValue(
											apptValue);
								}

							}

							break;
						}
					}

				}

			}
		}
	}

	/**
	 * ��������̯������FDCCostSplit�ӿڣ�
	 * 
	 * @return
	 * @author:jelon ����ʱ�䣺2006-12-26
	 *               <p>
	 */
	protected void apptAmount(int rowIndex) {
		FDCNoCostSplitBillEntryInfo entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys
				.getRow(rowIndex).getUserObject();

		// �޸ĵ��ýӿ� jelon 12/26/2006
		/*
		 * CostSplitType splitType=entry.getSplitType();
		 * calcApportionAmount(rowIndex,splitType);
		 * 
		 * //������� adjustAmount(rowIndex);
		 */

		// TODO ��̯
		fdcNoCostSplit.apptAmount(getEntrys(), entry);
		int level = entry.getLevel();
		IRow row = null;
		for (int i = rowIndex + 1; i < kdtEntrys.getRowCount(); i++) {
			row = kdtEntrys.getRow(i);
			entry = (FDCNoCostSplitBillEntryInfo) row.getUserObject();

			if (entry.getLevel() > level) {
				BigDecimal amount = entry.getAmount();
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				row.getCell("amount").setValue(amount);
			} else {
				break;
			}
		}

	}

	protected void calcAmount(int rowIndex) {

		BigDecimal amountTotal = FDCHelper.ZERO;

		BigDecimal amount = FDCHelper.ZERO;

		FDCNoCostSplitBillEntryInfo entry = null;

		// �������ܽ��
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			/*
			 * if (kdtEntrys.getRow(i).getCell(COLAMOUNT).getValue()!=null){
			 * amount = amount.add(new
			 * BigDecimal(kdtEntrys.getRow(i).getCell(COLAMOUNT).getValue().toString())); }
			 */
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();

			if (entry.getLevel() == 0) {
				amount = entry.getAmount();
				if (amount != null) {
					amountTotal = amountTotal.add(amount);
				}
			}
		}

		if (txtSplitedAmount.getBigDecimalValue() != null
				&& amountTotal.compareTo(txtSplitedAmount.getBigDecimalValue()) == 0) {
			try {
				txtSplitedAmount_dataChanged(null);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				handUIExceptionAndAbort(e);
			}
		} else {
			txtSplitedAmount.setValue(amountTotal);
		}

	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#txtSplitedAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	protected void txtSplitedAmount_dataChanged(DataChangeEvent e)
			throws Exception {
		// TODO �Զ����ɷ������
		if (e == null)
			return;
		super.txtSplitedAmount_dataChanged(e);

		if (e.getNewValue() != null && txtAmount.getValue() != null) {

			BigDecimal amount = new BigDecimal(txtAmount.getValue().toString());

			BigDecimal amtSplited = new BigDecimal(e.getNewValue().toString());

			txtUnSplitAmount.setValue(amount.subtract(amtSplited));
		}
	}

	/**
	 * ���ݴ���Ĳ�����ͽ��в�� ����߼�����:�Զ����,ĩ�����,��Ʒ���
	 * 
	 * @author sxhong Date 2007-1-28
	 * @param costSplitType
	 *            �Զ����,ĩ�����,��Ʒ��ֵ�
	 * @throws Exception
	 */
	private void splitCost(CostSplitTypeEnum costSplitType) throws Exception {

		// ----------------------------------------------------------------------------------------
		// ѡ����

		if ((kdtEntrys.getSelectManager().size() == 0)
				|| isTableColumnSelected(kdtEntrys)) {
			MsgBox.showInfo(this, "û��ѡ�з�¼���޷����ò�ַ�����");
			return;
		}

		int topIdx = -1;
		int[] selectRows = KDTableUtil.getSelectedRows(kdtEntrys);
		if (selectRows.length > 0) {
			topIdx = selectRows[0];
		}
		if (!(topIdx >= 0)) {
			return;
		}

		// ----------------------------------------------------------------------------------------
		// ��ֶ���
		IRow topRow = kdtEntrys.getRow(topIdx);
		// FDCNoCostSplitBillEntryInfo
		// selectEntry=editData.getEntrys().get(selectIdx);
		FDCNoCostSplitBillEntryInfo topEntry = (FDCNoCostSplitBillEntryInfo) topRow
				.getUserObject();

		int topLevel = topEntry.getLevel();
		BOSUuid topId = topEntry.getId();
		AccountViewInfo topAcct = topEntry.getAccountView();
		if (topAcct == null) {
			return;
		}
		String topAcctNo = topEntry.getAccountView().getNumber();

		/** ****************������ͽ����Ƿ��ܹ����в�ֵļ�� start*********** */
		CostSplitTypeEnum splitType = topEntry.getSplitType();
		boolean isTrue = true;

		if (costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)) { // ��Ʒ���
			if (!isProdSplitEnabled(topEntry)) {
				// MsgBox.showInfo(this,"��ǰ��¼�޷����ò�Ʒ��̯������");
				MsgBox.showWarning(this, ContractClientUtils
						.getRes("selectRightRowForApportion"));
				return;
			}

		} else if (costSplitType.equals(CostSplitTypeEnum.PROJSPLIT)) { // �Զ����
			if (splitType != null
					&& !splitType.equals(CostSplitTypeEnum.MANUALSPLIT)) {
				if (!splitType.equals(CostSplitTypeEnum.PROJSPLIT)) {
					isTrue = false;
				}
			}

			if (topAcct.isIsLeaf() && topEntry.getCurProject().isIsLeaf()) {
				isTrue = false;
			}

			if (topEntry.getLevel() != 0) {
				isTrue = false;
			}

			if (!isTrue) {
				// MsgBox.showInfo(this,"��ǰ��¼�޷������Զ���ַ�����");
				MsgBox.showWarning(this, ContractClientUtils
						.getRes("selectRightRowForApportion"));
				return;
			}

		} else if (costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)) { // ĩ�����
			if (splitType != null
					&& !splitType.equals(CostSplitTypeEnum.MANUALSPLIT)) {
				// if(topEntry.getSplitType().equals(CostSplitType.PROJSPLIT)){
				if (!splitType.equals(CostSplitTypeEnum.BOTUPSPLIT)) {
					// isTrue=false;

					// ����ǰ���Զ����ת����ĩ����� jelon 12/6/06
					if (splitType.equals(CostSplitTypeEnum.PROJSPLIT)
							&& topEntry.getLevel() == 0) {
						if (!MsgBox.isYes(MsgBox.showConfirm2(this,
								FDCSplitClientHelper.getRes("sure")))) {
							return;
						}
					} else {
						isTrue = false;
					}
				}
			}

			if (topEntry.getLevel() != 0) {
				isTrue = false;
			}

			if (topAcct.isIsLeaf() && topEntry.getCurProject().isIsLeaf()) {
				isTrue = false;
			}

			if (!isTrue) {
				// MsgBox.showInfo(this,"��ǰ��¼�޷�����ĩ����ַ�����");
				MsgBox.showWarning(this, ContractClientUtils
						.getRes("selectRightRowForApportion"));
				return;
			}
		}
		/** ****************������ͽ����Ƿ��ܹ����в�ֵļ�� end *********** */

		// topEntry.setSplitType(costSplitType);
		/***********************************************************************
		 * ---------------------------------------------------------------------------------------- **
		 * ׼������,���ݵ��������
		 * --------------------------------------------------------------------------------------*/
		 
		// �Ѳ�ֵķ�¼
		FDCNoCostSplitBillEntryCollection entrys = new FDCNoCostSplitBillEntryCollection();
		entrys.add(topEntry);

		FDCNoCostSplitBillEntryInfo entry = null;
		for (int i = topIdx + 1; i < kdtEntrys.getRowCount(); i++) {
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();

			if (entry.getLevel() > topLevel) {
				entrys.add(entry);
			} else {
				break;
			}
		}

		// ----------------------------------------------------------------------------------------
		// �������UI
		UIContext uiContext = new UIContext(this);
		uiContext.put("costSplit", entrys);
		uiContext.put("splitType", costSplitType);
		uiContext.put("entryClass", getSplitBillEntryClassName());

		String apptUiName;
		if (costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)) {
			apptUiName = NoCostSplitApptProdUI.class.getName();
		} else {
			// �Զ����,ĩ�����
			apptUiName = NoCostSplitApptProjUI.class.getName();
		}
		// ���в������,���ز�ֽ����entrys
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(apptUiName, uiContext, null, STATUS_ADDNEW);
		uiWin.show();

		if (!((NoCostSplitApptUI) uiWin.getUIObject()).isOk()) {
			return;
		}
		entrys = (FDCNoCostSplitBillEntryCollection) ((NoCostSplitApptUI) uiWin
				.getUIObject()).getData();

		// ----------------------------------------------------------------------------------------
		// ɾ��ԭ���Ĳ��
		int index = 0;
		for (int i = topIdx + 1; i < kdtEntrys.getRowCount(); i++) {
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();
			if (entry.getLevel() > topLevel) {
				index = i;
			} else {
				break;
			}
		}
		for (int i = index; i > topIdx; i--) {
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();
			if (entry.getLevel() == topLevel) {
				break;
			} else {
				removeEntry(i);
			}
		}

		// ----------------------------------------------------------------------------------------

		// �ɱ���Ŀ
		AccountViewInfo acct = entrys.get(0).getAccountView();

		// �������
		splitType = costSplitType; // CostSplitType.BOTUPSPLIT;

		// ��̯����
		ApportionTypeInfo apportionType = entrys.get(0).getApportionType();

		// ���ӿ�Ŀ
		boolean isAddlAcct = entrys.get(0).isIsAddlAccount();

		topEntry.setSplitType(splitType);
		topEntry.setApportionType(apportionType);
		topEntry.setIsLeaf(false);
		topEntry.setProduct(null);

		topRow.getCell("standard").setValue(splitType.toString());
		topRow.getCell("product").setValue("");
		topRow.getCell("product").getStyleAttributes().setLocked(true);

		// ���� begin
		topRow.getCell("apportionType.name").setValue(apportionType.getName());
		topRow.getCell("splitType").setValue(splitType);
		// ���� end

		IRow row;

		// ɾ��ȫ�������
		if (entrys.size() == 1) { // û�н��в�������ó��˹����
			topEntry.setIsLeaf(true);

			if (topEntry.getLevel() == 0) {
				topEntry.setSplitType(CostSplitTypeEnum.MANUALSPLIT);
				topEntry.setApportionType(null);

				topRow.getCell("splitType").setValue(
						CostSplitTypeEnum.MANUALSPLIT);
				// topRow.getCell("apportionType").setValue(new
				// ApportionTypeInfo());

				// topRow.getCell("standard").setValue("");
				setDisplay(topIdx);

			} else {
				for (int i = topIdx - 1; i >= 0; i--) {
					row = kdtEntrys.getRow(i);
					entry = (FDCNoCostSplitBillEntryInfo) row.getUserObject();
					if (entry.getLevel() == topEntry.getLevel() - 1) {
						topEntry.setSplitType(entry.getSplitType());
						topEntry.setApportionType(null);

						topRow.getCell("splitType").setValue(
								entry.getSplitType());
						// topRow.getCell("apportionType").setValue(new
						// ApportionTypeInfo());

						setDisplay(i);

						break;
					}
				}

			}

			return;
		}

		// �����µĲ����
		int idxCurr = topIdx;

		for (int i = 1; i < entrys.size(); i++) {
			entry = entrys.get(i);

			// ������ jelon 12/27/2006
			entry.setIndex(topEntry.getIndex());

			// entry.setSplitType(splitType);
			if (entry.getSplitType() != null
					&& entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)) {
				// ��Ŀ����а����Ĳ�Ʒ���
			} else {
				entry.setSplitType(splitType);
			}

			if (costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)) {
				// ��Ŀ����а����Ĳ�Ʒ���
				entry.setIsAddlAccount(isAddlAcct);
			}

			idxCurr++;
			row = insertEntry(idxCurr, entry);

			row.getCell("curProject.name").setValue(
					entry.getCurProject().getName());
			row.getCell("accountView.name").setValue(
					entry.getAccountView().getName());
		}

		// -----------------------��ֺ����ô���-----------------------------------------------
		// ���������
		// calcApportionData(topIdx,costSplitType); //ʹ���½ӿ� jelon 12/26/2006
		totApptValue(topIdx);

		// ��̯�ɱ�
		// calcApportionAmount(topIdx,costSplitType); //ʹ���½ӿ� jelon 12/26/2006
		apptAmount(topIdx);

		// ������ʾ
		index = topIdx;

		// ��Ʒ����ҵ����ڵ㣬�Ӳ�����ĸ��ڵ㿪ʼ����
		if (costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)) {
			row = kdtEntrys.getRow(topIdx);
			entry = (FDCNoCostSplitBillEntryInfo) row.getUserObject();
			if (entry.getLevel() != 0) {
				for (int i = topIdx - 1; i >= 0; i--) {
					row = kdtEntrys.getRow(i);
					entry = (FDCNoCostSplitBillEntryInfo) row.getUserObject();
					if (entry.getLevel() == 0) {
						index = i;
						break;
					}
				}
			}
		}
		setDisplay(index);

	}

	private void initSplitWindow(FDCNoCostSplitBillEntryInfo topEntry,
			int topIdx, CostSplitTypeEnum costSplitType) {
		/*       
		 *//*******************************************************************
			 * ---------------------------------------------------------------------------------------- **
			 * ׼������,���ݵ��������
			 * --------------------------------------------------------------------------------------*/
		/*
		 * //�Ѳ�ֵķ�¼ FDCNoCostSplitBillEntryCollection entrys=new
		 * FDCNoCostSplitBillEntryCollection(); entrys.add(topEntry); int
		 * topLevel=topEntry.getLevel(); FDCNoCostSplitBillEntryInfo entry=null;
		 * for(int i=topIdx+1; i<kdtEntrys.getRowCount(); i++){
		 * entry=(FDCNoCostSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
		 * 
		 * if(entry.getLevel()>topLevel){ entrys.add(entry); }else{ break; } }
		 * 
		 * //----------------------------------------------------------------------------------------
		 * //�������UI UIContext uiContext = new UIContext(this);
		 * uiContext.put("costSplit", entrys); uiContext.put("splitType",
		 * costSplitType); uiContext.put("entryClass",
		 * getSplitBillEntryClassName());
		 * 
		 * 
		 * String apptUiName; if(costSplitType.equals(CostSplitType.PRODSPLIT)){
		 * apptUiName=NoCostSplitApptProdUI.class.getName(); }else{ //�Զ����,ĩ�����
		 * apptUiName=NoCostSplitApptProjUI.class.getName(); }
		 * //���в������,���ز�ֽ����entrys IUIWindow uiWin =
		 * UIFactory.createUIFactory(UIFactoryName.MODEL).create( apptUiName,
		 * uiContext, null ,STATUS_ADDNEW ); uiWin.show();
		 * 
		 * if (!((NoCostSplitApptUI) uiWin.getUIObject()).isOk()) { return; }
		 * entrys =(FDCNoCostSplitBillEntryCollection) ((NoCostSplitApptUI)
		 * uiWin.getUIObject()).getData() ;
		 * 
		 * return entrys;
		 */
	}

	/**
	 * ���������ܷ�ָ̯�꣨����FDCCostSplit�ӿڣ�
	 * 
	 * @return
	 * @author:jelon ����ʱ�䣺2006-12-26
	 *               <p>
	 */
	private void totApptValue(int rowIndex) {
		FDCNoCostSplitBillEntryInfo entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys
				.getRow(rowIndex).getUserObject();

		// �޸ĵ��ýӿ� jelon 12/26/2006
		/*
		 * CostSplitType splitType=entry.getSplitType();
		 * calcApportionData(rowIndex,splitType);
		 */

		// TODO sxhong
		fdcNoCostSplit.totApptValue(getEntrys(), entry);

		int level = entry.getLevel();
		IRow row = null;
		BigDecimal amount = null;

		for (int i = rowIndex; i < kdtEntrys.getRowCount(); i++) {
			row = kdtEntrys.getRow(i);
			entry = (FDCNoCostSplitBillEntryInfo) row.getUserObject();

			if (entry.getLevel() > level
					|| (entry.getLevel() == level && i == rowIndex)) {
				amount = entry.getApportionValueTotal();
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				row.getCell("apportionValueTotal").setValue(amount);

				amount = entry.getDirectAmountTotal();
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				row.getCell("directAmountTotal").setValue(amount);

				amount = entry.getOtherRatioTotal();
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				row.getCell("otherRatioTotal").setValue(amount);

			} else {
				break;
			}
		}
	}

	private boolean isProdSplitEnabled(FDCNoCostSplitBillEntryInfo entry) {
		boolean isTrue = false;
		if (entry.getAccountView().isIsLeaf()
				&& entry.getCurProject().isIsLeaf()) {

			if (entry.getSplitType() == null
					|| entry.getSplitType().equals(
							CostSplitTypeEnum.MANUALSPLIT)) {
				isTrue = true;
			} else if (entry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT)
					|| entry.getSplitType()
							.equals(CostSplitTypeEnum.BOTUPSPLIT)) {
				isTrue = true;
			} else if (entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)) {
				if (!entry.isIsLeaf()) {
					isTrue = true;
				}
			}
		}

		return isTrue;
	}

	private boolean isProdSplitParent(FDCNoCostSplitBillEntryInfo entry) {
		boolean isTrue = false;

		if (!entry.isIsLeaf() && entry.getSplitType() != null
				&& entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)) {
			isTrue = true;
		}

		return isTrue;
	}

	protected SelectorItemCollection setSelectors(SelectorItemCollection sic) {
		SelectorItemCollection selector = setSelectorsEntry(sic,false);
		//���ݵĵ�һ������ȫ������
		selector.add("*");
		return selector;
	}

	protected SelectorItemCollection setSelectorsEntry(
			SelectorItemCollection sic, boolean isEntry) {
		return fdcNoCostSplit.setSelectorsEntry(sic, isEntry);
	}

	protected String getSplitBillEntryClassName() {
		return null;
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#onLoad()
	 */
	public void onLoad() throws Exception {
		// TODO �Զ����ɷ������
		super.onLoad();
		// ����������ĸ�ʽ��ֻ����������
		getDetailTable().getColumn("amount").setEditor(
				FDCSplitClientHelper.getTotalCellNumberEdit());
		// getDetailTable().getColumn("directAmount").setEditor(FDCSplitClientHelper.getCellNumberEdit());

		// ���㵽�����һ��ʱ�����Զ�������
		disableAutoAddLine(kdtEntrys);
		disableAutoAddLineDownArrow(getDetailTable());
		// �������delete�¼�
		getDetailTable().setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					for (int i = 0; i < getDetailTable().getSelectManager()
							.size(); i++) {
						KDTSelectBlock block = getDetailTable()
								.getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block
								.getEndRow(); rowIndex++) {
							for (int colIndex = block.getBeginCol(); colIndex <= block
									.getEndCol(); colIndex++) {
								int amount_index = getDetailTable()
										.getColumnIndex("amount");
								int directAmt_index = getDetailTable()
										.getColumnIndex("directAmt");
								if ((colIndex != amount_index && colIndex != directAmt_index)
										|| (getDetailTable().getCell(rowIndex,
												colIndex).getStyleAttributes()
												.isLocked())) {
									e.setCancel(true);
									continue;
								}
								try {
									getDetailTable().getCell(rowIndex, colIndex).setValue(FDCHelper.ZERO);
									kdtEntrys_editStopped(new KDTEditEvent(e.getSource(), null, FDCHelper.ZERO, rowIndex, colIndex, false,
											1));
								} catch (Exception e1) {
									logger.error(e1.getMessage(), e1);
									handUIExceptionAndAbort(e1);
								}
							}
							// e.setCancel(true);
						}
					}

				} else if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
					e.setCancel(true);
				}
			}
		});

		// ������ jelon 12/27/2006
		int idx = 0;
		FDCNoCostSplitBillEntryInfo entry = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();
			if (entry.getLevel() == 0) {
				if (entry.getIndex() > idx) {
					idx = entry.getIndex();
				}
			}
		}

		if (getOprtState().equals(OprtState.ADDNEW)) {
			actionRemove.setEnabled(false);
		}
		groupIndex = idx;

		actionInsertLine.setEnabled(false);
		actionAddLine.setEnabled(false);
		// actionRemoveLine.setEnabled(false);
		
		boolean isAddNew=this.getOprtState()!=null&&this.getOprtState().equals(OprtState.ADDNEW);
        if(isAddNew){
        	//�����
        	String costBillID = (String)getUIContext().get("costBillID");
        	if(costBillID!=null){
        		java.util.List list=new ArrayList();
        		list.add(costBillID);
        		try {
					FDCClientUtils.requestDataObjectLock(this, list, "edit");
        		}catch (Throwable e) {
        			this.handUIException(e);
        			SysUtil.abort();
				}
        		
        	}
        }

        //�޸Ĳ�ֵ�С��λ����������С��λ���²�ֲ�����ȫ��Ӱ�췶Χȫ�����,���ʵ��ֻ֧����λС��
		getTotalTxt().setValue(FDCHelper.toBigDecimal(getTotalTxt().getBigDecimalValue(),2));
		this.editData.setAmount(FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(this.editData.getAmount(),2)));
	}

	public void onShow() throws Exception {
		// setDisplayHideColumn(); //���Ե�ʱ���
		kdtEntrys.setColumnMoveable(true);
		super.onShow();
		if (OprtState.VIEW.equals(getOprtState())) {
			// �鿴ʱ��һЩ״̬�趨
			// ��¼���ɱ༭
			// getDetailTable().setEditable(false);
			// getDetailTable().setEnabled(false);
			getDetailTable().getStyleAttributes().setLocked(true);

			// ��ť��״̬
			actionSplitBotUp.setEnabled(false);
			btnSplitBotUp.setEnabled(false);
			actionSplitProd.setEnabled(false);
			btnSplitProd.setEnabled(false);
			actionSplitProj.setEnabled(false);
			btnSplitProj.setEnabled(false);
			actionAcctSelect.setEnabled(false);
			btnAcctSelect.setEnabled(false);
			// actionAudit.setEnabled(false);
			// actionUnAudit.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			btnRemoveLine.setEnabled(false);
			if (editData != null && editData.getState() != null
					&& editData.getState().equals(FDCBillStateEnum.INVALID)) {
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(false);
			}
		} else if (getOprtState() == STATUS_FINDVIEW) {
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
			actionRemoveLine.setEnabled(true);
			actionRemove.setEnabled(true);
			actionSave.setEnabled(true);
		}
		if (OprtState.ADDNEW.equals(getOprtState())) {
			txtUnSplitAmount.setValue(txtAmount.getValue());
		}
		addDebugWin();

		txtSplitedAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		txtUnSplitAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);

		if (OprtState.ADDNEW.equals(getOprtState())) {
			actionRemove.setEnabled(false);
		}

	}

	/**
	 * ��ʾ���ص��У����ڵ��� �����Ԫ���������������أ����ڴ��������������صĻ�����ڱ�������ҳ��
	 * ��ʾ���ص���������ͨ����Ԫ��������������Ȼ��ͨ����ʾ������ʾ�����еķ� ʽ���ﵽ���Ե�Ŀ��
	 * 
	 * @author sxhong Date 2006-12-1
	 */
	protected void setDisplayHideColumn() {
		// ���ض������
		int column_index = getDetailTable().getColumnIndex("curProject.id");
		for (int i = column_index; i < getDetailTable().getColumnCount(); i++) {
			getDetailTable().getColumn(i).getStyleAttributes().setHided(false);
		}

	}

	protected void setContractBillId(String billId) {
		contractBillId = billId;
	}

	protected String getContractBillId() {
		return contractBillId;
	}

	protected void setSplitBillType(CostSplitBillTypeEnum type) {
		splitBillType = type;
	}

	protected KDFormattedTextField getTotalTxt() {
		return txtAmount;
	}
	
	/**
	 *  �������ռ�¼����
	 */
	protected void dealWithEmptyDataBeforeSave(){
		if (CostSplitBillTypeEnum.CONTRACTSPLIT.equals(splitBillType)
				|| CostSplitBillTypeEnum.CNTRCHANGESPLIT.equals(splitBillType)
				|| (CostSplitBillTypeEnum.PAYMENTSPLIT.equals(splitBillType)&&editData.getBoolean("isConWithoutText"))) {
			boolean isEmptyRow = false;
			for(int i=0;!isEmptyRow&&i<getDetailTable().getRowCount();i++){
				IRow row = getDetailTable().getRow(i);
				BigDecimal amount = FDCHelper.toBigDecimal(row.getCell("amount").getValue());
				if(amount.signum()==0){
					isEmptyRow = true;
				}
			}
			if(isEmptyRow){
				int ok = MsgBox.showConfirm2(this,"���ڹ������Ϊ  0  �ķ�¼���Ƿ�ɾ��֮�󱣴� ��");
				if(MsgBox.OK==ok){
					for(int i=getDetailTable().getRowCount()-1;i>=0;i--){
						IRow row = getDetailTable().getRow(i);
						BigDecimal amount = FDCHelper.toBigDecimal(row.getCell("amount").getValue());
						if(amount.signum()==0){
							getDetailTable().removeRow(i);
						}
					}
				}
			}
		}
		
	}
	
	protected void checkbeforeSave() {
		BigDecimal amount = null;
		// �����¼�ڵ����ӽ���ֶ�
		for (int i = 0; i < getDetailTable().getRowCount(); i++) {
			IRow row = getDetailTable().getRow(i);
			FDCNoCostSplitBillEntryInfo entry = (FDCNoCostSplitBillEntryInfo) row
					.getUserObject();
			if (entry.getLevel() != 0) {
				continue;
			}

			amount = entry.getAmount();
			Object obj = row.getCell("amount").getValue();
			if (!(obj instanceof BigDecimal)&&isLimitCost) {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("mustInput"));
				SysUtil.abort();
			}
		}
		// �����ܽ��
		amount = getTotalTxt().getBigDecimalValue();
		if (amount == null) {
			amount = FDCHelper.ZERO;
		}

		BigDecimal splitAmount = txtSplitedAmount.getBigDecimalValue();
		if (splitAmount == null) {
			splitAmount = FDCHelper.ZERO;
		}
		if ((splitAmount == null || splitAmount.compareTo(FDCHelper.ZERO) == 0)
				&& amount.compareTo(FDCHelper.ZERO) != 0) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("notSplited"));
			SysUtil.abort();
		} else if (amount.compareTo(splitAmount) > 0) {
			if (editData instanceof ConNoCostSplitInfo) {
				editData.setSplitState(CostSplitStateEnum.PARTSPLIT);
			} else {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("notAllSplit"));
				SysUtil.abort();
			}
		} else if (amount.compareTo(splitAmount) == 0) {
			editData.setSplitState(CostSplitStateEnum.ALLSPLIT);

			// ������ϸ������Ŀ�Ŀ�Ŀ�Ƿ��Ѳ�� //Jelon Dec 11, 2006
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				IRow row = kdtEntrys.getRow(i);
				FDCNoCostSplitBillEntryInfo entry = (FDCNoCostSplitBillEntryInfo) row
						.getUserObject();

				if (entry.getLevel() < 0)
					continue;// �ܼ���

				if (entry.getLevel() == 0 && entry.isIsLeaf()) {
					if (!entry.getCurProject().isIsLeaf()) {

						if (splitBillType
								.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
							editData
									.setSplitState(CostSplitStateEnum.PARTSPLIT);
							break;
						} else {
							MsgBox.showWarning(this, "�����ֵ�����ϸ�Ĺ�����Ŀ�ĳɱ���Ŀ!");
							SysUtil.abort();
						}
					}
				}
			}

		} else {
//			if(isSimpleInvoice&&editData instanceof ConNoCostSplitInfo){
//				editData.setSplitState(CostSplitStateEnum.ALLSPLIT);
//			}else{
				MsgBox.showWarning(this, FDCSplitClientHelper.getRes("moreThan"));
				SysUtil.abort();
//			}
		}

		editData.setState(FDCBillStateEnum.SAVED);
	}

	private void setOtherRatioByAmount(int index) {
		FDCNoCostSplitBillEntryInfo topEntry = (FDCNoCostSplitBillEntryInfo) kdtEntrys
				.getRow(index).getUserObject();
		int level = topEntry.getLevel();
		AccountViewInfo topAcct = topEntry.getAccountView();

		FDCNoCostSplitBillEntryInfo entry = null;
		IRow row = null;
		ICell cell = null;
		String display = null;
		// int level=0;

		int idxTop = 0;
		for (int i = index; i < kdtEntrys.getRowCount(); i++) {
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();

			if (entry.getLevel() == level + 1) {

			}
		}

		for (int i = index; i < kdtEntrys.getRowCount(); i++) {
		}

	}

	/**
	 * �������Ƿ��Ʒ�����ϸ
	 * 
	 * @return
	 * @author:jelon ����ʱ�䣺2006-11-30
	 *               <p>
	 */
	protected boolean isProdSplitLeaf(FDCNoCostSplitBillEntryInfo entry) {
		boolean isTrue = false;

		if (entry.isIsLeaf() && entry.getSplitType() != null
				&& entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)) {
			isTrue = true;
		}

		return isTrue;
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.framework.client.EditUI#removeByPK(com.kingdee.bos.dao.IObjectPK)
	 */
	protected void removeByPK(IObjectPK pk) throws Exception {
		// TODO �Զ����ɷ������
		super.removeByPK(pk);
	}

	private void addDebugWin() {
		menuBiz.add(new AbstractHidedMenuItem("ctrl alt F11") {
			public void action_actionPerformed() {
				try {
					KDDialog diag = new KDDialog();
					diag.setSize(400, 300);
					diag.setLocation(300, 100);
					KDTextArea txt = new KDTextArea();
					diag.getContentPane().add(txt);
					String s = "id:" + editData.getId();
					txt.setText(s);
					// txt.select(3, s.length());
					diag.setVisible(true);
					logger.info(s);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					SysUtil.abort(e);
				}
				// setDisplayHideColumn(); //���Ե�ʱ���
			}
		});
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		setSelectorsEntry(sic, false);
		return sic;
	}

	/**
	 * ��������ַ�¼��
	 * 
	 * @return
	 * @author:jelon ����ʱ�䣺2006-12-26
	 *               <p>
	 */
	protected FDCNoCostSplitBillEntryCollection getEntrys() {
		FDCNoCostSplitBillEntryCollection coll = new FDCNoCostSplitBillEntryCollection();

		FDCNoCostSplitBillEntryInfo entry = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();
			coll.add(entry);
		}

		return coll;
	}

	/**
	 * �������Ƿ񸽼ӿ�Ŀ��ϸ�����Ƿ�����ϸ���̵���ϸ���ӿ�Ŀ
	 * 
	 * @return
	 * @author:jelon ����ʱ�䣺2006-11-30
	 *               <p>
	 */
	protected boolean isAddlAcctLeaf(FDCSplitBillEntryInfo entry) {
		boolean isTrue = false;

		if (entry.isIsAddlAccount() && entry.getCostAccount() != null
				&& entry.getCostAccount().isIsLeaf()
				&& entry.getCostAccount().getCurProject() != null
				&& entry.getCostAccount().getCurProject().isIsLeaf()
				&& !isProdSplitLeaf(entry)) {
			isTrue = true;
		}

		return isTrue;
	}

	private boolean isProdSplitEnabled(FDCSplitBillEntryInfo entry) {
		boolean isTrue = false;

		/*
		 * if(entry.getSplitType()==null ||
		 * entry.getSplitType().equals(CostSplitType.MANUALSPLIT)){
		 * if(entry.getCostAccount().isIsLeaf() &&
		 * entry.getCostAccount().getCurProject().isIsLeaf()){ isTrue=true; }
		 * }else if(entry.getSplitType().equals(CostSplitType.PROJSPLIT) ||
		 * entry.getSplitType().equals(CostSplitType.BOTUPSPLIT)){
		 * if(entry.getCostAccount().isIsLeaf() &&
		 * entry.getCostAccount().getCurProject().isIsLeaf()){ isTrue=true; }
		 * }else if(entry.getSplitType().equals(CostSplitType.PRODSPLIT)){
		 * if(!entry.isIsLeaf()){ isTrue=true; } }
		 */

		if (entry.getCostAccount().isIsLeaf()
				&& entry.getCostAccount().getCurProject().isIsLeaf()) {

			if (entry.getSplitType() == null
					|| entry.getSplitType().equals(
							CostSplitTypeEnum.MANUALSPLIT)) {
				isTrue = true;
			} else if (entry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT)
					|| entry.getSplitType()
							.equals(CostSplitTypeEnum.BOTUPSPLIT)) {
				isTrue = true;
			} else if (entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)) {
				if (!entry.isIsLeaf()) {
					isTrue = true;
				}
			}
		}

		return isTrue;
	}

	/**
	 * �������Ƿ��Ʒ�����ϸ
	 * 
	 * @return
	 * @author:jelon ����ʱ�䣺2006-11-30
	 *               <p>
	 */
	protected boolean isProdSplitLeaf(FDCSplitBillEntryInfo entry) {
		boolean isTrue = false;

		if (entry.isIsLeaf() && entry.getSplitType() != null
				&& entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)) {
			isTrue = true;
		}

		return isTrue;
	}

	private boolean isProdSplitParent(FDCSplitBillEntryInfo entry) {
		boolean isTrue = false;

		if (!entry.isIsLeaf() && entry.getSplitType() != null
				&& entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)) {
			isTrue = true;
		}

		return isTrue;
	}

	protected int addGroupIndex(FDCNoCostSplitBillEntryInfo entry) {
		if (entry.getLevel() == 0) {
			groupIndex++;
		}

		return groupIndex;
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnRemove || e.getSource() == menuItemRemove) {
			if (hasRemove) {
				try {
					actionExitCurrent_actionPerformed(null);
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
					handUIExceptionAndAbort(e1);
				}
			}
		}
	}

	protected FDCNoCostSplitBillEntryCollection getCostSplitEntryCollection(
			CostSplitBillTypeEnum splitBillType) {
		return getCostSplitEntryCollection(splitBillType, null);
	}

	/**
	 * ͨ������Ĳ�ֵ��ݵ�����(�����֣������ֵ�)����Ӧ�ĺ�ͬID�õ���ַ�¼
	 * 
	 * @param splitBillType
	 * @param costBillUuId
	 *            ��ֵ��ݵ�CostBillUuid�������BOSUuid�����BOSUuid��
	 * @return
	 */
	protected FDCNoCostSplitBillEntryCollection getCostSplitEntryCollection(
			CostSplitBillTypeEnum splitBillType, BOSUuid costBillUuId) {

		String costBillId = null;
		if (costBillUuId == null) {
			costBillUuId = BOSUuid.read(getContractBillId());
		}
		costBillId = costBillUuId.toString();

		if (costBillId == null) {
			return new FDCNoCostSplitBillEntryCollection();
		}
		AbstractObjectCollection coll = null;
		AbstractBillEntryBaseInfo item = null;
		EntityViewInfo view = new EntityViewInfo();
		String filterField = null;
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = view.getSelector();
		setSelectorsEntry(sic, true);
		view.getSorter().add(new SorterItemInfo("seq"));

		BOSUuid splitBillId = null;
		FilterInfo filterSplit = new FilterInfo();
		EntityViewInfo viewSplit = new EntityViewInfo();
		FDCNoCostSplitBillCollection costColl = new FDCNoCostSplitBillCollection();
		viewSplit.getSorter().add(new SorterItemInfo("createTime"));
		if (splitBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			filterField = "parent.contractBill.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, costBillId));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);

			filterSplit.getFilterItems().add(
					new FilterItemInfo("contractBill.id", costBillId));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			try {
				costColl = ConNoCostSplitFactory.getRemoteInstance().getFDCNoCostSplitBillCollection(viewSplit);
				coll = ConNoCostSplitEntryFactory.getRemoteInstance().getConNoCostSplitEntryCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage(), e);
				handUIExceptionAndAbort(e);
			}
		} else if (splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			filterField = "parent.contractChange.id";
			filter.getFilterItems().add(new FilterItemInfo(filterField, costBillId));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			view.setFilter(filter);

			filterSplit.getFilterItems().add(new FilterItemInfo("contractChange.id", costBillId));
			filterSplit.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			try {
				costColl = ConChangeNoCostSplitFactory.getRemoteInstance().getFDCNoCostSplitBillCollection(viewSplit);
				coll = ConChangeNoCostSplitEntryFactory.getRemoteInstance().getFDCNoCostSplitBillEntryCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage(), e);
				handUIExceptionAndAbort(e);
			}

		} else if (splitBillType.equals(CostSplitBillTypeEnum.SETTLEMENTSPLIT)) {
			filterField = "parent.settlementBill.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, costBillId));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);

			filterSplit.getFilterItems().add(
					new FilterItemInfo("settlementBill.id", costBillId));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			try {
				costColl = SettNoCostSplitFactory.getRemoteInstance().getFDCNoCostSplitBillCollection(viewSplit);
				coll = SettNoCostSplitEntryFactory.getRemoteInstance().getFDCNoCostSplitBillEntryCollection(view);
			} catch (BOSException e) {
				// @AbortException
				logger.error(e.getMessage(), e);
				handleException(e);
			}
		} else {
			// ������ֵ�,�Ժ��ṩ֧��
			return new FDCNoCostSplitBillEntryCollection();
		}

		if (costColl != null && costColl.iterator().hasNext()) {
			splitBillId = costColl.get(0).getId();
		}

		FDCNoCostSplitBillEntryCollection entrys = new FDCNoCostSplitBillEntryCollection();
		FDCNoCostSplitBillEntryInfo entry = null;

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			item = (AbstractBillEntryBaseInfo) iter.next();
			item.setId(null);
			// item.setSeq(null);

			entry = (FDCNoCostSplitBillEntryInfo) createNewDetailData(kdtEntrys);
			entry.putAll(item);
			if (splitBillId != null) {
				entry.setSplitBillId(splitBillId);
			}
			entry.setCostBillId(costBillUuId);
			entrys.add(entry);
		}

		return entrys;
	}

	public void actionImpContrSplit_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO �Զ����ɷ������
		super.actionImpContrSplit_actionPerformed(e);

		IRow row = null;
		FDCNoCostSplitBillEntryInfo entry = null;

		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			row = kdtEntrys.getRow(i);
			entry = (FDCNoCostSplitBillEntryInfo) row.getUserObject();

			if (entry.getCostBillId() != null) {
				MsgBox.showInfo(this, "�Ѿ������˺�ͬ��֣������ظ����룡");
				return;
			}
		}
		importCostSplitContract();
		setDisplay();
	}

	protected void importCostSplitContract() {
		loadCostSplit(getCostSplitEntryCollection(CostSplitBillTypeEnum.CONTRACTSPLIT));
	}

	protected void loadCostSplit(FDCNoCostSplitBillEntryCollection entrys) {

		FDCNoCostSplitBillEntryInfo entry = null;
		BigDecimal amount = FDCHelper.ZERO;

		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			entry = (FDCNoCostSplitBillEntryInfo) iter.next();

			// ֱ�ӷ���
			entry.setDirectAmount(amount);
			entry.setDirectAmountTotal(amount);

			// �������֣��������ý��
			if (splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
				entry.setAmount(amount);
			}
			// ������ jelon 12/28/2006
			if (entry.getLevel() == 0) {
				groupIndex++;
			}
			entry.setIndex(groupIndex);
			addEntry(entry);
		}
	}

	protected void attachListeners() {
		addDataChangeListener(txtSplitedAmount);
	}

	protected void detachListeners() {
		removeDataChangeListener(txtSplitedAmount);
	}

	protected void initListener() {
		// ��ֲ�ʹ��ѡ��selectchange�Լ���ͷ����Ĺ���
		// super.initListener();
	}

	protected void setAuditButtonStatus(String oprtType) {
		if (STATUS_VIEW.equals(oprtType)) {
			actionAudit.setVisible(true);
			actionUnAudit.setVisible(true);
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(true);

			FDCBillInfo bill = (FDCBillInfo) editData;
			if (editData != null) {
				if (FDCBillStateEnum.AUDITTED.equals(bill.getState())) {
					actionUnAudit.setVisible(true);
					actionUnAudit.setEnabled(true);
					actionAudit.setVisible(false);
					actionAudit.setEnabled(false);
				} else {
					actionUnAudit.setVisible(false);
					actionUnAudit.setEnabled(false);
					actionAudit.setVisible(true);
					actionAudit.setEnabled(true);
				}
			}
		} else {
			actionAudit.setVisible(false);
			actionUnAudit.setVisible(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}
		if (!actionAudit.isVisible() && !actionUnAudit.isVisible()) {
			this.menuBiz.setVisible(false);
		} else {
			this.menuBiz.setVisible(true);
		}
		btnAudit.setVisible(actionAudit.isVisible());
		btnUnAudit.setVisible(actionUnAudit.isVisible());
	}

	protected void updateButtonStatus() {

		super.updateButtonStatus();
		// ���������ɱ����ģ���������ɾ����
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionAddNew.setVisible(false);
			actionEdit.setVisible(false);
			actionRemove.setVisible(false);
		}

	}
	
	public boolean destroyWindow() {
		boolean destroyWindow = super.destroyWindow();
		if(destroyWindow){
			boolean isAddNew=this.editData.getId()==null;
	        if(isAddNew){
	        	//�����
	        	String costBillID = (String)getUIContext().get("costBillID");
	        	if(costBillID!=null){
	        		java.util.List list=new ArrayList();
	        		list.add(costBillID);
	        		try {
						FDCClientUtils.releaseDataObjectLock(this, list);
	        		}catch (Throwable e) {
						logger.error(e.getMessage(), e);
						SysUtil.abort();
					}
	        		
	        	}
	        }
		}
		return destroyWindow;
	}
	
	
	protected boolean isMeasureContract(){
		return false;
	}

	private boolean isAdjustVourcherMode=false;
	/***
	 * ��ģʽ����ۿ��ΥԼ
	 */
	private boolean isSimpleFinancialExtend = false;
	/***
	 * ��ģʽ
	 */
	private boolean isSimpleFinancial = false;
	/**
	 * ��ģʽ����Ʊ
	 */
	private boolean isSimpleInvoice = false;
	/**
	 * ����ģʽ
	 */
	private boolean isFinacial = false;
	
	/**
	 * �������븶�����
	 */
	private boolean isWorkLoadSeparate=false;
	/**
	 * ��ν���
	 */
	private boolean isMoreSetter = false;
	/**
	 * ֻ��ʹ�ð��������
	 */
	private boolean isSplitBaseOnProp = false;
	
	protected  void fetchInitData() throws Exception{		
		super.fetchInitData();
		//���񼶲���
		String companyId = company.getId().toString();
		HashMap paramMap = FDCUtils.getDefaultFDCParam(null, companyId);
		isAdjustVourcherMode=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
		isSimpleFinancial = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		isSimpleFinancialExtend = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
		isSimpleInvoice = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEINVOICE);
		isFinacial = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_FINACIAL);
		isWorkLoadSeparate = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		invoiceMgr = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INVOICEMRG);
		isMoreSetter=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_MORESETTER);
		
		//�ɱ�������
		String orgUnitId = orgUnitInfo.getId().toString();
		isLimitCost = FDCUtils.getBooleanValue4FDCParamByKey(null, orgUnitId, FDCConstants.FDC_PARAM_LIMITCOST);
		isSplitBaseOnProp = FDCUtils.getBooleanValue4FDCParamByKey(null, orgUnitId, FDCConstants.FDC_PARAM_SPLITBASEONPROP);
	}
	
	protected boolean isSplitBaseOnProp(){
		return isSplitBaseOnProp;
	}
	protected boolean isSimpleFinancialExtend(){
		return isSimpleFinancialExtend;
	}
	protected boolean isSimpleFinancial(){
		return isSimpleFinancial;
	}
	protected boolean isSimpleInvoice(){
		return isSimpleInvoice;
	}
	protected boolean isAdjustVourcherModel(){
		return isAdjustVourcherMode;
	}
	protected boolean isFinacial(){
		return isFinacial;
	}
	/**
	 * �������븶�����
	 * @return
	 */
	protected boolean isWorkLoadSeparate(){
		return isWorkLoadSeparate;
	}
	
	/**
	 * ���÷�Ʊ����
	 */
	private boolean invoiceMgr=false;
	/**
	 * ���÷�Ʊ����
	 */
	protected boolean isInvoiceMgr(){
		return invoiceMgr;	
	}
	protected boolean isMoreSetter(){
		return isMoreSetter;
	}
	/**
	 * �����ֿ�Ŀ�Ľ���ܶ�Ӧ��Ŀ�Ѳ�ֳɱ���������
	 */
	private boolean isLimitCost=true;
	protected boolean isLimitCost(){
		return isLimitCost;
	}
}