/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
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
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SettlementNoCostEditUI extends AbstractSettlementNoCostEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SettlementNoCostEditUI.class);
    private boolean hasDirectAmt = false;
    /**
     * output class constructor
     */
    public SettlementNoCostEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output txtSplitedAmount_dataChanged method
     */
    protected void txtSplitedAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        super.txtSplitedAmount_dataChanged(e);
        BigDecimal value = txtSplitedAmount.getBigDecimalValue();
		if (value != null && getDetailTable().getRow(0) != null) {
			getDetailTable().getCell(0, "amount").setValue(value);
			editData.setAmount(value);
		}
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
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
					if (amount.compareTo(FDCHelper.ZERO) == 0) {
						entry.setAmount(FDCHelper.ZERO);
						apptAmount(e.getRowIndex());
						calcAmount(0);
						setQuaAmt();
						setFirstLine();
						return;
					}
				}
				entry.setAmount(amount);
				setFirstLine();		//Add by zhiyuan_tang 2010/07/12	R100613-030  结算拆分时，未拆分结算金额不能及时刷新
			}
		}
		if (e.getColIndex() == kdtEntrys.getColumnIndex("directAmt")) {
			if (e.getValue() != e.getOldValue()) {
				BigDecimal amount = FDCHelper.ZERO;
				amount = amount.setScale(10);
				SettNoCostSplitEntryInfo entry;
				entry = (SettNoCostSplitEntryInfo) kdtEntrys.getRow(
						e.getRowIndex()).getUserObject();
				// amount=new
				// BigDecimal(kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue().toString());
				Object cellVal = kdtEntrys.getRow(e.getRowIndex()).getCell(
						"directAmt").getValue();
				if (cellVal != null) {
					amount = new BigDecimal(cellVal.toString());
				}
				entry.setDirectAmt(amount);

				if (amount.compareTo(FDCHelper.ZERO) == 0 && hasDirectAmt) {
					setHasDirectAmt();
					if (!hasDirectAmt) {
						// 清空
						for (int i = 0; i < getDetailTable().getRowCount(); i++) {
							((FDCNoCostSplitBillEntryInfo) getDetailTable().getRow(i)
									.getUserObject()).put("amount",
									FDCHelper.ZERO);
							getDetailTable().getCell(i, "amount").setValue(
									FDCHelper.ZERO);
							((FDCNoCostSplitBillEntryInfo) getDetailTable().getRow(i)
									.getUserObject()).put("grtSplitAmt",
									FDCHelper.ZERO);
							getDetailTable().getCell(i, "grtSplitAmt").setValue(
									FDCHelper.ZERO);
						}
					}
				} else {
					setHasDirectAmt();
					// hasDirectAmt=true;
				}
			}
		}
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	btnSplit.doClick();
		checkDirAmt();
        super.actionSave_actionPerformed(e);
        setFirstLine();
    }

    protected ICoreBase getBizInterface() throws Exception {
    	return SettNoCostSplitFactory.getRemoteInstance();
    }
    
    public void loadFields() {
    	super.loadFields();
    	setDisplay();
		setAmtDisplay();
    }
    
    private void setAmtDisplay() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof SettNoCostSplitEntryInfo)
						&& ((SettNoCostSplitEntryInfo) obj).getLevel() > -1) {
					SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						getDetailTable().getRow(i).getStyleAttributes()
								.setBackground(new Color(0xF6F6BF));
						getDetailTable().getRow(i).getCell("amount")
								.getStyleAttributes().setBackground(
										new Color(0xFFFFFF));
					} else {
						getDetailTable().getRow(i).getStyleAttributes()
								.setBackground(new Color(0xF5F5E6));
						getDetailTable().getRow(i).getCell("amount")
								.getStyleAttributes().setLocked(true);
					}
					if (entry.getCurProject() != null
							&& (entry.getCurProject().isIsLeaf())
							&& (entry.isIsLeaf())) {
						getDetailTable().getRow(i).getCell("directAmt")
								.getStyleAttributes().setBackground(
										new Color(0xFFFFFF));
					} else {
						getDetailTable().getRow(i).getCell("directAmt")
								.getStyleAttributes().setBackground(
										FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						getDetailTable().getRow(i).getCell("directAmt")
								.getStyleAttributes().setLocked(true);
					}
				}
			}
		}
	}
    
    protected KDTable getDetailTable() {
        return this.kdtEntrys;
	}
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initWorkButton();
    	String contractId = FDCHelper.getId(ContractSettlementBillFactory
				.getRemoteInstance().getContractSettlementBillInfo(
						new ObjectUuidPK(editData.getSettlementBill().getId()))
				.getContractBill());

		setContractBillId(contractId);

		setSplitBillType(CostSplitBillTypeEnum.SETTLEMENTSPLIT);

		if (STATUS_ADDNEW.equals(getOprtState())) {
//			importCostSplit();
			importCostSplist();
		}
		getDetailTable().getColumn("directAmt").setEditor(
				FDCSplitClientHelper.getCellNumberEdit());
		getDetailTable().getColumn("grtSplitAmt").getStyleAttributes()
				.setLocked(true);
		setAmtDisplay();
		//避免在未作更改时，直接退出提示”是否保存“，先storeFields()一下。
		if(!STATUS_VIEW.equals(this.getOprtState())){
			this.storeFields();
		}
    }
    
    private void importCostSplist() {
		importContractCostSplist();
		importChangeCostSplist();
		setDisplay();
		setAmtDisplay();
	}
    
    private void importContractCostSplist() {
		loadCostSplit(CostSplitBillTypeEnum.CONTRACTSPLIT,
				getCostSplitEntryCollection(
						CostSplitBillTypeEnum.CONTRACTSPLIT, null));
	}
    
    private void importChangeCostSplist() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractChange.contractBill.id",
						getContractBillId()));
		filter.getFilterItems().add(
				new FilterItemInfo("state",
						FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		view.setFilter(filter);
		SelectorItemCollection sic = view.getSelector();
		sic.add(new SelectorItemInfo("contractChange.contractBill.id"));

		ConChangeNoCostSplitCollection coll = null;
		try {
			coll = ConChangeNoCostSplitFactory.getRemoteInstance()
					.getConChangeNoCostSplitCollection(view);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

		if (coll == null || coll.size() == 0) {
			return;
		}

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ConChangeNoCostSplitInfo item = (ConChangeNoCostSplitInfo) iter.next();
			loadCostSplit(CostSplitBillTypeEnum.CNTRCHANGESPLIT,
					getCostSplitEntryCollection(
							CostSplitBillTypeEnum.CNTRCHANGESPLIT, item
									.getContractChange().getId()));
		}

	}

    protected void loadCostSplit(CostSplitBillTypeEnum costBillType,
			FDCNoCostSplitBillEntryCollection entrys) {
		FDCNoCostSplitBillEntryInfo entry = null;
		if (costBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			// contractAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (FDCNoCostSplitBillEntryInfo) iter.next();
				Object amount = entry.get("amount");
				entry.put("contractAmt", amount);
				entry.put("apportionValue", amount);
				entry.put("apportionValueTotal", amount);
				entry.put("otherRatioTotal", amount);
				entry.put("amount", null);
			}
		} else if (costBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			// changeAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (FDCNoCostSplitBillEntryInfo) iter.next();
				Object amount = entry.get("amount");
				entry.put("changeAmt", amount);
				entry.put("apportionValue", amount);
				entry.put("apportionValueTotal", amount);
				entry.put("otherRatioTotal", amount);
				// entry.put("amount", FDCHelper.ZERO);
				entry.put("amount", null);
			}
		}
		super.loadCostSplit(entrys);
	}
    
    public void onShow() throws Exception {
		super.onShow();
		setFirstLine();
		setHasDirectAmt();
		if (getOprtState().equals(OprtState.VIEW)) {
			actionRemove.setEnabled(false);
			actionSplit.setEnabled(false);
		}else{
			actionSplit.setEnabled(true);
		}
		getDetailTable().getColumn("contractAmt").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("changeAmt").getStyleAttributes().setLocked(true);
		
	}
    
    private void setFirstLine() {

		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			Object obj = row.getUserObject();
			if (!(obj instanceof SettNoCostSplitEntryInfo)
					|| ((SettNoCostSplitEntryInfo) obj).getLevel() > -1) {
				// 没有总计行
				row = getDetailTable().addRow(0);
				SettNoCostSplitEntryInfo entry = new SettNoCostSplitEntryInfo();
				entry.setLevel(-1000);
				row.setUserObject(entry);
			}

			row.getCell(2).setValue(FDCSplitClientHelper.getRes("total"));// 合计
			// int
			// contractAmt_Index=getDetailTable().getColumnIndex("contractAmt");
			BigDecimal contractAmt = FDCHelper.ZERO;
			BigDecimal changeAmt = FDCHelper.ZERO;
			BigDecimal grtSplitAmt = FDCHelper.ZERO;
			//Add by zhiyuan_tang 2010/07/12	R100613-030 结算拆分时，未拆分结算金额不能及时刷新
			BigDecimal amount = FDCHelper.ZERO;
			
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				obj = getDetailTable().getRow(i).getUserObject();
				if (obj instanceof SettNoCostSplitEntryInfo) {
					SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						if (entry.getContractAmt() instanceof BigDecimal) {
							contractAmt = contractAmt.add(entry
									.getContractAmt());
						}
						if (entry.getChangeAmt() instanceof BigDecimal) {
							changeAmt = changeAmt.add(entry.getChangeAmt());
						}
						if (entry.getGrtSplitAmt() != null) {
							grtSplitAmt = grtSplitAmt.add(entry
									.getGrtSplitAmt());
						}
						
						//Add by zhiyuan_tang 2010/07/12	R100613-030 结算拆分时，未拆分结算金额不能及时刷新
						if(entry.getAmount()!=null){
							amount = amount.add(entry.getAmount());
						}
					}
				}
			}
			row.getCell("contractAmt").setValue(contractAmt);
			row.getCell("changeAmt").setValue(changeAmt);
			row.getCell("grtSplitAmt").setValue(grtSplitAmt);
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(
					FDCHelper.KDTABLE_TOTAL_BG_COLOR);

			//Add by zhiyuan_tang 2010/07/12	R100613-030 结算拆分时，未拆分结算金额不能及时刷新
			txtSplitedAmount.setValue(amount);
			txtUnSplitAmount.setValue(FDCHelper.subtract(txtAmount.getBigDecimalValue(), amount));

		}
	}
    
    private void setHasDirectAmt() {
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getUserObject() != null) {
				SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) row
						.getUserObject();
				if (entry.getDirectAmt() != null
						&& entry.getDirectAmt().compareTo(FDCHelper.ZERO) > 0) {
					hasDirectAmt = true;
					return;
				}
			}
		}
		hasDirectAmt = false;
	}
    
    protected IObjectValue createNewData() {
    	SettNoCostSplitInfo objectValue = new SettNoCostSplitInfo();
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUserInfo()));
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setIsInvalid(false);
		String costBillID;
		costBillID = (String) getUIContext().get("costBillID");
		ContractSettlementBillInfo settlementBillInfo = null;

		SelectorItemCollection selectors = new SelectorItemCollection();
		// selectors.add("*");
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("settlePrice");
		selectors.add("contractBill.id");
		selectors.add("contractBill.number");
		selectors.add("contractBill.name");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
		selectors.add("qualityGuarante");
		try {
			settlementBillInfo = ContractSettlementBillFactory
					.getRemoteInstance().getContractSettlementBillInfo(
							new ObjectUuidPK(BOSUuid.read(costBillID)),
							selectors);			
			objectValue.setSettlementBill(settlementBillInfo);
			if(settlementBillInfo.getContractBill()!=null)
				objectValue.setContractBill(settlementBillInfo.getContractBill());
			if(settlementBillInfo.getCurProject()!=null)
				objectValue.setCurProject(settlementBillInfo.getCurProject());
			txtCostBillNumber.setText(settlementBillInfo.getNumber());
			txtCostBillName.setText(settlementBillInfo.getName());
			txtAmount.setValue(settlementBillInfo.getSettlePrice());
			// txtAmount.setValue(settlementBillInfo.getSettlePrice().subtract(sum));

		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return objectValue;
    }
    
    protected IObjectValue createNewDetailData(KDTable table) {
		return new com.kingdee.eas.fdc.contract.SettNoCostSplitEntryInfo();
	}

	protected String getSplitBillEntryClassName() {
		return com.kingdee.eas.fdc.contract.SettNoCostSplitEntryInfo.class
				.getName();
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
//		super.actionSplit_actionPerformed(e);
		boolean temp = hasDirectAmt;
		if (temp == true) {
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof SettNoCostSplitEntryInfo)
							&& ((SettNoCostSplitEntryInfo) obj).getLevel() > -1) {
						SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) obj;
						if (getDetailTable().getRow(i).getCell("directAmt")
								.getValue() != null) {
							getDetailTable().getRow(i).getCell("amount")
									.setValue(
											getDetailTable().getRow(i).getCell(
													"directAmt").getValue());
							entry.setAmount(entry.getDirectAmt());
						} else {
							getDetailTable().getRow(i).getCell("amount")
									.setValue(FDCHelper.ZERO);
							entry.setAmount(FDCHelper.ZERO);
						}
					}
				}
			}
			calDirAmt();
			calcAmount(0);
		} else {
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof SettNoCostSplitEntryInfo)
							&& ((SettNoCostSplitEntryInfo) obj).getLevel() > -1) {
						// if(getDetailTable().getRow(i).getCell("amount").getValue()!=null){
						SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							apptAmount(i);
						}
						// }
					}
				}
			}
			calcAmount(0);
		}
		setQuaAmt();
		setFirstLine();
	}
	
	protected void setQuaAmt() throws Exception {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof SettNoCostSplitEntryInfo)
						&& ((SettNoCostSplitEntryInfo) obj).getLevel() > -1) {
					SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) obj;
					if (entry.getAmount() != null
							&& editData.getSettlementBill()
									.getQualityGuarante() != null) {
						BigDecimal cmpAmt = FDCHelper.ZERO;
//						cmpAmt = (entry.getAmount().multiply(editData.getSettlementBill().getQualityGuarante())).divide(editData.getSettlementBill().getSettlePrice(), 2,BigDecimal.ROUND_HALF_EVEN);
						cmpAmt = FDCHelper.divide(FDCHelper.multiply(entry.getAmount(), editData.getSettlementBill().getQualityGuarante()), editData.getSettlementBill().getSettlePrice(), 2,BigDecimal.ROUND_HALF_EVEN);
						entry.setGrtSplitAmt(cmpAmt);
						getDetailTable().getRow(i).getCell("grtSplitAmt")
								.setValue(cmpAmt);
					}
				}
			}
			if (editData.getAmount().compareTo(
					editData.getSettlementBill().getSettlePrice()) == 0) {
				int idx = 0;
				BigDecimal amountMax = FDCHelper.ZERO;
				BigDecimal amount = FDCHelper.ZERO;// null;
				BigDecimal amountTotal = FDCHelper.ZERO;
				amountTotal = amountTotal.setScale(10);
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof SettNoCostSplitEntryInfo)
							&& ((SettNoCostSplitEntryInfo) obj).getLevel() > -1) {
						SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							amount = entry.getGrtSplitAmt();
							if (amount == null) {
								amount = FDCHelper.ZERO;
							}
							amountTotal = amountTotal.add(amount);
							// 修正项为金额最大的项
							// if(amount.compareTo(FDCHelper.ZERO)!=0){
							if (amount.compareTo(amountMax) >= 0) {
								amountMax = amount;
								idx = i;
							}
						} else {
							continue;
						}
					}
				}
				if (idx > 0
						&& editData.getSettlementBill().getQualityGuarante()
								.compareTo(amountTotal) != 0) {
					SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) getEntrys()
							.getObject(idx);
					if (entry.getGrtSplitAmt() != null) {
						if (!(entry.getGrtSplitAmt().equals(FDCHelper.ZERO))) {
							if (txtAmount.getBigDecimalValue() != null
									&& txtSplitedAmount.getBigDecimalValue() != null)
								if (txtAmount.getBigDecimalValue().equals(
										txtSplitedAmount.getBigDecimalValue())) {
									amount = entry.getGrtSplitAmt();
									if (amount == null) {
										amount = FDCHelper.ZERO;
									}
									amount = amount.add(editData
											.getSettlementBill()
											.getQualityGuarante().subtract(
													amountTotal));
									entry.setGrtSplitAmt(amount);
									getDetailTable().getRow(idx).getCell(
											"grtSplitAmt").setValue(amount);
								}
						}
					}
				}
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof SettNoCostSplitEntryInfo)
							&& ((SettNoCostSplitEntryInfo) obj).getLevel() > -1) {
						SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							int curIndex = getEntrys().indexOf(entry);
							SettNoCostSplitHelper.adjustQuaAmount(getEntrys(),
									entry);
							// 汇总生成非明细工程项目中附加科目的成本 jelon 12/29/2006
							SettNoCostSplitHelper.totAmountQuaAddlAcct(
									getEntrys(), curIndex);
							// calcAmount(0);
						}
					}
				}
			}
		}
	}
	
	//	 有直接金额，拆分后汇总
	private void calDirAmt() {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			SettNoCostSplitEntryInfo entry = (SettNoCostSplitEntryInfo) kdtEntrys
					.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumAccount(i);
				fdcNoCostSplit.totAmountAddlAcct(getEntrys(), curIndex);
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			FDCNoCostSplitBillEntryInfo entry = (FDCNoCostSplitBillEntryInfo) row
					.getUserObject();
			BigDecimal amount = entry.getAmount();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			row.getCell("amount").setValue(amount);
		}
	}
	
//	 对level=0的进行汇总
	private void sumAccount(int index) {
		SettNoCostSplitEntryInfo curEntry = (SettNoCostSplitEntryInfo) kdtEntrys
				.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
//		int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		SettNoCostSplitEntryInfo entry;
		if ((curEntry.getCurProject().isIsLeaf()) && (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (SettNoCostSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getLevel() == curEntry.getLevel() + 1) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumAccount(i);
						if (entry.getAmount() != null)
							amtTotal = amtTotal.add(entry.getAmount());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (SettNoCostSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getCurProject().getId().equals(
							curEntry.getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumAccount(i);
							if (entry.getAmount() != null)
								amtTotal = amtTotal.add(entry.getAmount());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			}
		}
	}

	private void checkDirAmt() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				if (getDetailTable().getRow(i).getCell("directAmt").getValue() != null) {
					BigDecimal dir = FDCHelper.toBigDecimal(getDetailTable().getRow(i)
							.getCell("directAmt").getValue());
					if (dir.compareTo(FDCHelper.ZERO) > 0) {
						BigDecimal dirAmt = FDCHelper.toBigDecimal(getDetailTable()
								.getRow(i).getCell("amount").getValue());
						if (dir.compareTo(dirAmt) != 0) {
							MsgBox.showWarning(this, FDCSplitClientHelper
									.getRes("haveDirAmt"));
							SysUtil.abort();
						}
					}
				}
			}
		}
	}
	
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic=super.getSelectors();		
		//sic.add(new SelectorItemInfo("paymentBill.contractBillId"));
		sic.add("state");
		return setSelectors(sic);
    }
	
	protected void initWorkButton() {
		super.initWorkButton();
		actionSplitBotUp.setEnabled(false);
		actionSplitProj.setEnabled(false);
		actionAcctSelect.setEnabled(false);
		actionSplitProd.setEnabled(false);
		actionRemoveLine.setEnabled(false);
		actionRemoveLine.setVisible(false);
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
    
    protected void loadData() throws Exception {
    	handleSplitWorkFlow();
    	super.loadData();
    }
    
    private void handleSplitWorkFlow() {
    	 Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
         if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()){
        	 if(this.getUIContext().get(UIContext.ID)!=null){
        		 String costBillID=this.getUIContext().get(UIContext.ID).toString();
         		 getUIContext().remove(UIContext.ID);
         		 getUIContext().put("costBillID",costBillID);
        		 setOprtState(OprtState.ADDNEW);
        	 }
         }
    }
}