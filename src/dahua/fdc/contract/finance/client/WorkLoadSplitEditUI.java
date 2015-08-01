/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentAutoSplitHelper;
import com.kingdee.eas.fdc.finance.PaymentCostSplitImportHandler;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.mycompany.internal.edu.emory.mathcs.backport.java.util.Arrays;

/**
 * output class name
 */
public class WorkLoadSplitEditUI extends AbstractWorkLoadSplitEditUI {
	private static final Logger logger = CoreUIObject.getLogger(WorkLoadSplitEditUI.class);

	private static final BOSObjectType PAYMENTSPLITBOSTYPE = new PaymentSplitInfo().getBOSType();
	private boolean isClickSplit = false;
	
	private static final String REFERFLAG = "voucherRefer";
	
	// 新增时初始化的拆分对象:包含合同、变更、结算及已拆分数据
	PaymentSplitInfo newInfo = null;
	
	/**
	 * 工程量确认单
	 */
	protected WorkLoadConfirmBillInfo workLoadConfirmBillInfo = null;

	/**
	 * output class constructor
	 */
	public WorkLoadSplitEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output txtSplitedAmount_dataChanged method
	 */
	protected void txtSplitedAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		super.txtSplitedAmount_dataChanged(e);
	}

	public void actionViewContract_actionPerformed(ActionEvent e) throws Exception {
		ContractBillInfo contract = editData.getContractBill();
		if(contract==null){
			contract=editData.getWorkLoadConfirmBill().getContractBill();
		}
		if (contract==null) {
			return;
		}
		String contractID = contract.getId().toString();
		if (contractID == null) {
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, contractID);
		IUIWindow contractBillUI = UIFactory.createUIFactory(
				UIFactoryName.NEWTAB).create(
				ContractBillEditUI.class.getName(), uiContext,
				null, OprtState.VIEW);
		contractBillUI.show();

	}

	public void actionViewWorkLoadBill_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getWorkLoadConfirmBill() == null) {
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, editData.getWorkLoadConfirmBill().getId().toString());
		// uiContext.put("contractBillId",
		// editData.getPaymentBill().getContractBillId());
		// uiContext.put("contractBillNumber",
		// editData.getPaymentBill().getContractNo());
		IUIWindow testUI = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(WorkLoadConfirmBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		testUI.show();
	}

	/**
	 * output kdtEntrys_editStopped method
	 */
	protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		if (e.getColIndex() == kdtEntrys.getColumnIndex("directAmt")) {
			if (e.getValue() != e.getOldValue()) {
				// isAutoSplit=false;
				editData.setDescription("ManualSplit");
				BigDecimal amount = FDCHelper.ZERO;
				amount = amount.setScale(10);
				PaymentSplitEntryInfo entry;
				entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(e.getRowIndex()).getUserObject();
				Object cellVal = kdtEntrys.getRow(e.getRowIndex()).getCell("directAmt").getValue();
				if (cellVal != null) {
					amount = new BigDecimal(cellVal.toString());
				}
				entry.setDirectAmt(amount);
				if (hasDirectAmt && amount.compareTo(FDCHelper.ZERO) == 0) {
					setHasDirectAmt();
					if (!hasDirectAmt) {
						// 清空
						for (int i = 0; i < getDetailTable().getRowCount(); i++) {
							((FDCSplitBillEntryInfo) getDetailTable().getRow(i).getUserObject()).put("amount", FDCHelper.ZERO);
							getDetailTable().getCell(i, "amount").setValue(FDCHelper.ZERO);
//							((FDCSplitBillEntryInfo) getDetailTable().getRow(i).getUserObject()).put("payedAmt", FDCHelper.ZERO);
//							getDetailTable().getCell(i, "payedAmt").setValue(FDCHelper.ZERO);
							// if (editData.getQualityAmount() != null) {
							// setQuaAmt();
							// }
						}
					}
				} else {
					setHasDirectAmt();
				}
			}
		}
	}

	/**
	 * output kdtEntrys_editValueChanged method
	 */
	protected void kdtEntrys_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		super.kdtEntrys_editValueChanged(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PaymentSplitFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAcctSelect, actionSplitProj, actionSplitBotUp, actionSplitProd }, false);

		actionSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_split"));
		actionViewWorkLoadBill.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_linkviewlist"));
		actionAutoMatchSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_split"));
		actionSplitBaseOnProp.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_split"));
		actionViewContract.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));
		actionSplit.setEnabled(true);
		actionViewContract.setEnabled(true);
		actionAudit.setEnabled(false);
		actionAudit.setVisible(false);
		actionDelVoucher.setEnabled(false);
		actionDelVoucher.setVisible(false);
		FDCClientHelper.setActionEnable(new ItemAction[]{actionRemoveLine,actionAddLine,actionInsertLine}, false);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		handIsSplitAcct();

		setFirstLine();
		
		if (this.getOprtState().equals(OprtState.VIEW)) {
			btnAcctSelect.setEnabled(false);
			menuItemAcctSelect.setEnabled(false);
		}
		
		// /////////////////////////////////////////////////////////////////////////
		// 处理值_工程量确认流程与付款流程分离参数（参数FDC317）
		dealWithValue4WorkLoadSeparate();
		// /////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 描述：处理值_工程量确认流程与付款流程分离参数（参数FDC317）
	 * 
	 * @throws BOSException
	 * @throws SQLException
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-29
	 */
	private void dealWithValue4WorkLoadSeparate() throws BOSException, SQLException {
		// start==================
		// R141107-0203 by skyiter_wang 2014-12-29 start
		// 取得工程量拆分单.归属成本金额
		Map amountMap = getSplitedCostAmt4WorkLoadSeparate();

		/**
		 * <pre>
		 * 三、参数317”分离“为”是“：
		 * 工程量拆分单据
		 * 1、表头.已确认工程量=工程量确认单.确认工程量，改名为”本次已确认工程量“
		 * 2、表头.已拆分工程量=表格.已拆分成本金额，改名为”本次已拆分工程量“
		 * 3、表头.未拆分工程量=表头.已确认工程量-表头.已拆分工程量，改名为”本次未拆分工程量“
		 * 3、表格.已拆分成本金额=SUM（工程量拆分单据.表格.归属成本金额），过滤条件：当前单据之前
		 * 4、表格.归属成本金额=表格.直接金额（点击”拆分“自动传递），或者自动匹配拆分或按照比例拆分计算得来的
		 * 
		 * 校验：
		 * 1、校验：表格.成本拆分金额 &gt;= 表格.已拆分成本金额 + 表格.归属成本金额。
		 *  (业务说明： 成本拆分金额实际上是（合同拆分+合同变更拆分）或结算拆分，已拆分成本金额实际上是工程量拆分金额)
		 * 2、如果存在未审批合同变更单，给出提示信息“存在未审批合同变更单”+上述提示信息，是否继续？如果继续，不校验；否则，不提交
		 * </pre>
		 */
		if (isWorkLoadSeparate()) {
			// 表格
			KDTable table = getDetailTable();
			int costAccountIdColIndex = table.getColumnIndex("costAccount.id");
			int splitedCostAmtColIndex = table.getColumnIndex("splitedCostAmt");
			// int amountColIndex = table.getColumnIndex("amount");
			for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
				// table.getCell(i, amountColIndex).setValue(null);

				Object costAccountId = table.getCell(i, costAccountIdColIndex).getValue();
				if (null == costAccountId) {
					continue;
				}
				BigDecimal splitedCostAmt = (BigDecimal) amountMap.get(costAccountId.toString());
				table.getCell(i, splitedCostAmtColIndex).setValue(splitedCostAmt);
			}

			// 表头
			txtAmount.setValue(null);
			txtSplitedAmount.setValue(null);
			if (null != workLoadConfirmBillInfo) {
				txtAmount.setValue(workLoadConfirmBillInfo.getWorkLoad());
			}
			if (FDCTableHelper.isNotEmpty(table)) {
				calAmtTotal("splitedCostAmt");
				calAmtTotal("amount");
				
				IRow row = getDetailTable().getRow(0);
				BigDecimal amount = FDCHelper.toBigDecimal(row.getCell("amount").getValue(), 2);
				txtSplitedAmount.setValue(amount);
			}
			
			BigDecimal unSplitAmount = FDCNumberHelper.subtract(txtAmount.getBigDecimalValue(), txtSplitedAmount
					.getBigDecimalValue());
			txtUnSplitAmount.setValue(unSplitAmount);
		}
		/**
		 * <pre>
		 * 四、参数317”分离“为”否“：
		 * 工程量拆分单据
		 * 单据禁止打开
		 * </pre>
		 */
		else {
		}
		// end==================
	}

	/**
	 * 描述：取得已拆分成本金额_工程量确认流程与付款流程分离参数（参数FDC317）
	 * 
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-29
	 */
	private Map getSplitedCostAmt4WorkLoadSeparate() throws BOSException, SQLException {
		Map amountMap = new LinkedHashMap();
		
		StringBuffer sqlSb = new StringBuffer();
		
		// 工程量拆分单据.表格.归属成本金额
		if (isWorkLoadSeparate()) {
			sqlSb.append("	SELECT tfncPaySE.FCostAccountID, SUM(tfncPaySE.FAmount) FAmount	\r\n");
			sqlSb.append("	  FROM T_FNC_WorkLoadConfirmBill tfncWorLCB	\r\n");
			sqlSb.append("	 INNER JOIN T_FNC_PaymentSplit tfncPayS	\r\n");
			sqlSb.append("	    ON tfncPayS.FWorkLoadBillID = tfncWorLCB.FID	\r\n");
			sqlSb.append("	   AND tfncPayS.FIsInvalid = 0	\r\n");
			sqlSb.append("	 INNER JOIN T_FNC_PaymentSplitEntry tfncPaySE	\r\n");
			sqlSb.append("	    ON tfncPaySE.FParentID = tfncPayS.FID	\r\n");
			sqlSb.append("	   AND tfncPaySE.FLevel <> -1000	\r\n");
			sqlSb.append("	 WHERE tfncWorLCB.FContractBillId = ?	\r\n");
			sqlSb.append("	   AND tfncWorLCB.FState = '4AUDITTED'	\r\n");
			sqlSb.append("	   AND ((tfncPayS.FState IS NULL) OR (tfncPayS.FState <> '9INVALID'))	\r\n");
			sqlSb.append("	   AND tfncPayS.FCreateTime < ?	\r\n");
			sqlSb.append("	   AND tfncPaySE.FCostAccountID IS NOT NULL	\r\n");
			sqlSb.append("	 GROUP BY tfncPaySE.FCostAccountID	\r\n");
		}
		// 付款拆分单据.表格.归属成本金额
		else {
			/**
			 * <pre>
			 * 四、参数317”分离“为”否“：
			 * 工程量拆分单据
			 * 单据禁止打开
			 * </pre>
			 */
		}
		
		String sql = sqlSb.toString();

		String contractId = this.getContractBillId();
		Timestamp createTime = this.editData.getCreateTime();
		if (null == createTime && null != serverDate) {
			createTime = new Timestamp(serverDate.getTime());
			this.editData.setCreateTime(createTime);
		}
		Object[] paramArr = new Object[] { contractId, createTime };
		List paramList = Arrays.asList(paramArr);
		IRowSet rs = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql, paramList);
		while (rs.next()) {
			String costAccountId = rs.getString("FCostAccountID");
			BigDecimal amount = rs.getBigDecimal("FAmount");

			amountMap.put(costAccountId, amount);
		}

		// //////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////
		
		logger.info("WorkLoadSplitEditUI.getSplitedCostAmt4WorkLoadSeparate()----------------------->, start");
		logger.info("sql : " + sql);
		logger.info("paramList : " + paramList);
		logger.info("amountMap : " + amountMap);
		MapUtils.debugPrint(System.out, "amountMap", amountMap);
		logger.info("WorkLoadSplitEditUI.getSplitedCostAmt4WorkLoadSeparate()----------------------->, end");

		// //////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////
		
		return amountMap;
	}


	/**
	 * 描述：校验_工程量确认流程与付款流程分离参数（参数FDC317）
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-29
	 */
	private void check4WorkLoadSeparate() {
		// start==================
		// R141107-0203 by skyiter_wang 2014-12-29 start

		if (FDCTableHelper.isEmpty(getDetailTable())) {
			return;
		}
		IRow row = getDetailTable().getRow(0);
		BigDecimal costAmt = FDCHelper.toBigDecimal(row.getCell("costAmt").getValue(), 2);
		// BigDecimal payedAmt = FDCHelper.toBigDecimal(row.getCell("payedAmt").getValue(), 2);
		BigDecimal payedAmt = FDCConstants.ZERO;
		BigDecimal splitedCostAmt = FDCHelper.toBigDecimal(row.getCell("splitedCostAmt").getValue(), 2);
		BigDecimal amount = FDCHelper.toBigDecimal(row.getCell("amount").getValue(), 2);

		BigDecimal allCostAmt = FDCNumberHelper.add(costAmt, payedAmt);
		BigDecimal allSplitedCostAmt = FDCNumberHelper.add(splitedCostAmt, amount);

		/**
		 * <pre>
		 * 三、参数317”分离“为”是“：
		 * 工程量拆分单据
		 * 1、表头.已确认工程量=工程量确认单.确认工程量，改名为”本次已确认工程量“
		 * 2、表头.已拆分工程量=表格.已拆分成本金额，改名为”本次已拆分工程量“
		 * 3、表头.未拆分工程量=表头.已确认工程量-表头.已拆分工程量，改名为”本次未拆分工程量“
		 * 3、表格.已拆分成本金额=SUM（工程量拆分单据.表格.归属成本金额），过滤条件：当前单据之前
		 * 4、表格.归属成本金额=表格.直接金额（点击”拆分“自动传递），或者自动匹配拆分或按照比例拆分计算得来的
		 * 
		 * 校验：
		 * 1、校验：表格.成本拆分金额 &gt;= 表格.已拆分成本金额 + 表格.归属成本金额。
		 *  (业务说明： 成本拆分金额实际上是（合同拆分+合同变更拆分）或结算拆分，已拆分成本金额实际上是工程量拆分金额)
		 * 2、如果存在未审批合同变更单，给出提示信息“存在未审批合同变更单”+上述提示信息，是否继续？如果继续，不校验；否则，不提交
		 * </pre>
		 */
		if (isWorkLoadSeparate()) {
			// 表格.成本拆分金额 >= 表格.已拆分成本金额 + 表格.归属成本金额
			if (FDCNumberHelper.isLessThan(allCostAmt, allSplitedCostAmt)) {
				FDCMsgBox.showWarning(this, "成本拆分金额不能小于已拆分成本金额！");
				SysUtil.abort();
			}
		}
		/**
		 * <pre>
		 * 四、参数317”分离“为”否“：
		 * 工程量拆分单据
		 * 单据禁止打开
		 * </pre>
		 */
		else {
			FDCMsgBox.showWarning(this, "参数FDC317：工程量确认流程与付款流程分离为否，不能使用该单据！");
			SysUtil.abort();
		}
		// end==================
	}

	/**
	 * 处理可拆分科目问题,引入新增时初始化数据
	 * 
	 * @throws Exception
	 */
	private void handIsSplitAcct() throws Exception {
		Map param = new HashMap();
		param.put("contractId", getUIContext().get("contractId") == null ? editData
				.getContractBill().getId().toString() : getUIContext().get("contractId"));
		param.put("workLoadBillId", getUIContext().get("costBillID") == null ? editData
				.getWorkLoadConfirmBill().getId().toString() : getUIContext().get("costBillID"));
		param.put("workLoadSplitId", editData.getId() != null ? editData.getId().toString() : null);
		try {
			newInfo = (PaymentSplitInfo) PaymentSplitFactory.getRemoteInstance().getNewData(param);
		} catch (Exception e) {
			if (FDCBillStateEnum.INVALID == editData.getState() && "合同未拆分,请先拆分合同！".equals(e.getMessage())) {
				// BT678202 工程量拆分-查看作废的工程量拆分时不需要提示  "合同未拆分,请先拆分合同！"
			} else {
				this.handUIException(e);
			}
		}
	}

	/**
	 * 科目是否重新选择了,通过现有分录科目与合同变更结算拆分科目对比而定
	 * 
	 * @return
	 */
	private boolean isDifAcct() {

		if (newInfo == null || newInfo.getEntrys() == null) {
			return false;
		}
		Set acctIds = new HashSet();
		for (Iterator iter = newInfo.getEntrys().iterator(); iter.hasNext();) {
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) iter.next();
			acctIds.add(entry.getCostAccount().getId().toString());
		}
		FDCSplitBillEntryCollection entrys = getEntrys();
		for (int i = 1; i < entrys.size(); i++) {
			FDCSplitBillEntryInfo entry = entrys.get(i);
			String acctId = entry.getCostAccount().getId().toString();
			// 初始化分录中不含本次分录中的科目ID说明重新选择了
			if (!acctIds.contains(acctId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 描述：
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-30
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#fetchInitParam()
	 */
	@Override
	protected void fetchInitParam() throws Exception {
		// TODO Auto-generated method stub
		super.fetchInitParam();

		String costBillID;
		costBillID = (String) getUIContext().get("costBillID");
		// 获取工程量确认单
		fetchWorkLoadConfirmBill(costBillID);
	}

	/**
	 * 描述：获取工程量确认单
	 * 
	 * @param paymentBillId
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-30
	 */
	private void fetchWorkLoadConfirmBill(String paymentBillId) {
		workLoadConfirmBillInfo = null;

		// ///////////////////////////////////////////////////////////////////////////
		if (null == paymentBillId) {
			return;
		}
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("workLoad");
		try {
			workLoadConfirmBillInfo = WorkLoadConfirmBillFactory.getRemoteInstance().getWorkLoadConfirmBillInfo(
					new ObjectUuidPK(BOSUuid.read(paymentBillId)), selectors);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

	}
	
	
	public void onShow() throws Exception {
		super.onShow();
		getDetailTable().getColumn("splitScale").getStyleAttributes().setHided(true);
		if(isSplitBaseOnProp()){
			actionAutoMatchSplit.setEnabled(false);
			actionAutoMatchSplit.setVisible(false);
		}
	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();
//		if(SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) 
//			FDCClientHelper.setActionEnable(actionRemove, true);
//		else
//			FDCClientHelper.setActionEnable(actionRemove, false);
	}
	
	protected IObjectValue createNewData() {
		Map param = new HashMap();
		param.put("contractId", getUIContext().get("contractId"));
		param.put("workLoadBillId", getUIContext().get("costBillID"));
		PaymentSplitInfo info = null;
		try {
			info = (PaymentSplitInfo) PaymentSplitFactory.getRemoteInstance().getNewData(param);
			// 做一些特殊处理
			for (Iterator iter = info.getEntrys().iterator(); iter.hasNext();) {
				PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) iter.next();
				entry.setPayedAmt(FDCHelper.ZERO);
//				entry.setQualityAmount(FDCHelper.ZERO);
				entry.setSplitQualityAmt(FDCHelper.ZERO);
				entry.setSplitedPayedAmt(FDCHelper.ZERO);
				entry.setShouldPayAmt(FDCHelper.ZERO);
			}

		} catch (Exception e) {
			this.handUIException(e);
			SysUtil.abort();
		}
		return info;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		return new com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String wlSplitId = editData.getWorkLoadConfirmBill().getId().toString();
		if (SettledMonthlyHelper.existObject(null, PAYMENTSPLITBOSTYPE,wlSplitId)) {
			MsgBox.showWarning(this,
					"财务成本月结已经引用本数据，不能删除，如需修改，请把此工程量对应的合同进入待处理流程！");
			SysUtil.abort();
		}
		
		//作废检查
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", wlSplitId,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(this, "存在已经作废的记录，不能执行删除操作！");
			SysUtil.abort();
		}
		
		//是否被调整单或工程量确认单引用
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", wlSplitId,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));//#2
		filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE));
		filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE));
		filter.setMaskString("#0 and #1 and (#2 or #3)");
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(this, "存在被调整单或者工程量确认单引用的工程量拆分，不能执行删除操作！");
			SysUtil.abort();
		}
		
		//工程量确认单分录
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select pe.fid from t_fnc_workloadcostvoucherentry pe left join t_fnc_paymentsplit ps on pe.fparentid=ps.FWorkLoadBillID where ");
		sqlBuilder.appendParam("ps.fworkLoadbillId", wlSplitId);
		IRowSet rowSet = sqlBuilder.executeQuery();
		if (rowSet.size() > 0) {
			MsgBox.showWarning(this, "对应的工程量确认单已经生成凭证，不能执行删除操作！");
			SysUtil.abort();
		}
		
		//是否已审批
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", wlSplitId,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(this, "已审批单据不能删除");
			SysUtil.abort();
		}
		
		super.actionRemove_actionPerformed(e);
		
	}
	
	private void loadCostSplitForTotal() {
		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			Object obj = row.getUserObject();
			if (!(obj instanceof PaymentSplitEntryInfo) || ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
				// 没有总计行
				row = getDetailTable().addRow(0);
				PaymentSplitEntryInfo entry = new PaymentSplitEntryInfo();
				entry.setLevel(-1000);
				row.setUserObject(entry);
			}
			row.getCell(2).setValue(FDCSplitClientHelper.getRes("total"));// 合计
			// int
			// contractAmt_Index=getDetailTable().getColumnIndex("contractAmt");
			BigDecimal contractAmt = FDCHelper.ZERO;
			BigDecimal changeAmt = FDCHelper.ZERO;
			BigDecimal costAmt = FDCHelper.ZERO;
			BigDecimal splitAmt = FDCHelper.ZERO;
			BigDecimal amount = FDCHelper.ZERO;
			if (newInfo != null && newInfo.getEntrys() != null) {
				for (int i = 0; i < newInfo.getEntrys().size(); i++) {
					PaymentSplitEntryInfo entry = newInfo.getEntrys().get(i);
					if (entry.getLevel() == 0) {
						// 合同拆分
						contractAmt = FDCHelper.add(contractAmt, entry.getContractAmt());
						// 变更拆分
						changeAmt = FDCHelper.add(changeAmt, entry.getChangeAmt());
						// 成本拆分=合同拆分+变更拆分（初始化时已相加）
						costAmt = FDCHelper.add(costAmt, entry.getCostAmt());
						// 已拆分成本
						splitAmt = FDCHelper.add(splitAmt, entry.getSplitedCostAmt());
						// 本单据拆分
						amount = FDCHelper.add(amount, entry.getAmount());

					}
				}
			}
			// 除了本次拆分，其它汇列在上面汇总计算
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				IRow tempRow = getDetailTable().getRow(i);
				obj = tempRow.getUserObject();
				if (obj instanceof PaymentSplitEntryInfo) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						if (tempRow.getCell("amount").getValue() != null) {
							BigDecimal temp = FDCHelper.toBigDecimal(tempRow.getCell("amount")
									.getValue());
							amount = amount.add(temp);
						}
						if (tempRow.getCell("splitedCostAmt").getValue() != null) {
							BigDecimal temp = FDCHelper.toBigDecimal(tempRow.getCell(
									"splitedCostAmt").getValue());
							splitAmt = splitAmt.add(temp);
						}
					}
				}
			}
			row.getCell("contractAmt").setValue(contractAmt);
			row.getCell("changeAmt").setValue(changeAmt);
			row.getCell("costAmt").setValue(costAmt);
			row.getCell("splitedCostAmt").setValue(splitAmt);
			row.getCell("amount").setValue(amount);

			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			txtSplitedAmount.setValue(amount);
		}
	}

	private void setFirstLine() {
		if (isDifAcct()) {
			loadCostSplitForTotal();
			return;
		}
		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			Object obj = row.getUserObject();
			if (!(obj instanceof PaymentSplitEntryInfo)
					|| ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
				// 没有总计行
				row = getDetailTable().addRow(0);
				PaymentSplitEntryInfo entry = new PaymentSplitEntryInfo();
				entry.setLevel(-1000);
				row.setUserObject(entry);
			}
			row.getCell(2).setValue(FDCSplitClientHelper.getRes("total"));// 合计
			BigDecimal contractAmt = FDCHelper.ZERO;
			BigDecimal changeAmt = FDCHelper.ZERO;
			BigDecimal costAmt = FDCHelper.ZERO;
			BigDecimal splitAmt = FDCHelper.ZERO;
			BigDecimal amount = FDCHelper.ZERO;

			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				IRow tempRow = getDetailTable().getRow(i);
				obj = tempRow.getUserObject();
				if (obj instanceof PaymentSplitEntryInfo) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						if (entry.getContractAmt() instanceof BigDecimal) {
							contractAmt = contractAmt.add(entry.getContractAmt());
						}
						if (entry.getChangeAmt() instanceof BigDecimal) {
							changeAmt = changeAmt.add(entry.getChangeAmt());
						}
						if (tempRow.getCell("costAmt").getValue() != null) {
							BigDecimal temp = FDCHelper.toBigDecimal(tempRow.getCell("costAmt").getValue());
							costAmt = costAmt.add(temp);
						}
						if (tempRow.getCell("splitedCostAmt").getValue() != null) {
							BigDecimal temp = FDCHelper.toBigDecimal(tempRow.getCell("splitedCostAmt").getValue());
							splitAmt = splitAmt.add(temp);
						}

						if (tempRow.getCell("amount").getValue() != null) {
							BigDecimal temp = FDCHelper.toBigDecimal(tempRow.getCell("amount").getValue());
							amount = amount.add(temp);
						}

					}
				}
			}
			// if (!isClickSplit) {
				row.getCell("contractAmt").setValue(contractAmt);
				row.getCell("changeAmt").setValue(changeAmt);
				row.getCell("costAmt").setValue(costAmt);
				row.getCell("splitedCostAmt").setValue(splitAmt);
				row.getCell("amount").setValue(amount);
			// isClickSplit = false;
			// }
		
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			txtSplitedAmount.setValue(amount);
		}
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSplit_actionPerformed(e);
		if (hasDirectAmt) {
			// 手工拆分
			editData.setDescription("ManualSplit");
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo) && ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (getDetailTable().getRow(i).getCell("directAmt").getValue() != null) {
							getDetailTable().getRow(i).getCell("amount").setValue(getDetailTable().getRow(i).getCell("directAmt").getValue());
							entry.setAmount(FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("directAmt").getValue()));
						} else {
							getDetailTable().getRow(i).getCell("amount").setValue(FDCHelper.ZERO);
							entry.setAmount(FDCHelper.ZERO);
						}
					}
				}
			}
			calDirAmt();
			calcAmount(0);
		} else {
			// 目前不支持归属金额拆分
			return;
		}

		setFirstLine();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("state");
		sic.add("description");
		sic.add("isProgress");
		sic.add("Fivouchered");
		sic.add("isWorkLoadBill");
		sic.add("curProject.id");
		sic.add("curProject.number");
		sic.add("curProject.name");
		sic.add("contractBill.id");
		sic.add("workLoadConfirmBill.id");
		sic.add("workLoadConfirmBill.contractBill.id");
		sic.add("workLoadConfirmBill.contractBill.number");
		sic.add("hasEffected");
		return setSelectors(sic);
	}
	public void actionSplitBaseOnProp_actionPerformed(ActionEvent e)
			throws Exception {
		hasDirectAmt = false;
		editData.setDescription("splitBaseOnProp");//按比例拆分
		FDCSplitBillEntryCollection entrys = getEntrys();
		//归属成本金额
		HashMap costMap = new HashMap();
		costMap.put("headCostAmt", txtAmount.getBigDecimalValue());//表头的实付金额
		costMap.put("splitCostAmtSum",kdtEntrys.getRow(0).getCell("costAmt").getValue());//成本拆分金额个明细行合计
		costMap.put("hadSplitCostAmtSum", kdtEntrys.getRow(0).getCell("splitedCostAmt").getValue());//已拆分付款金额各明细行合计
		PaymentAutoSplitHelper.splitCostAmtBaseOnProp(entrys, costMap);
		calDirAmt();
		calcAmount(0);
		
		setFirstLine();
	}
	public void actionAutoMatchSplit_actionPerformed(ActionEvent e) throws Exception {
		hasDirectAmt = false;
		editData.setDescription("AutoSplit");

		boolean hasSettleSplit = false;// 该值实际在方法里面没有用到，不知道方法为什么加这个参数 by sxhong
		// 2009-05-19 16:16:52

		PaymentAutoSplitHelper.autoSplit(getEntrys(), txtAmount.getBigDecimalValue());
		calcAmount(0);
		calDirAmt();
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				// Object obj = getDetailTable().getRow(i).getUserObject();
				IRow row = getDetailTable().getRow(i);
				row.getCell("directAmt").setValue(null);
			}
		}

		setFirstLine();
	}

	private boolean hasDirectAmt = false;

	private void setHasDirectAmt() {
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getUserObject() != null) {
				// for (Iterator iter = getPayEntrys().iterator();
				// iter.hasNext();) {
				// for (Iterator iter = editData.getEntrys().iterator();
				// iter.hasNext();) {
				PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) row.getUserObject();
				if (entry.getDirectAmt() != null && entry.getDirectAmt().compareTo(FDCHelper.ZERO) != 0) { //负数也要可以手动拆分 Added By Owen_wen
					hasDirectAmt = true;
					return;
				}
			}
		}
		hasDirectAmt = false;
	}

	public void loadFields() {
		super.loadFields();
		setDisplay();
	}

	// 有直接金额，拆分后汇总
	private void calDirAmt() {
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumAccount(i);
				fdcCostSplit.totAmountAddlAcct(getEntrys(), curIndex);
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getUserObject() != null) {
				FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo) row.getUserObject();
				BigDecimal amount = entry.getAmount();
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				row.getCell("amount").setValue(amount);
			}
		}
	}

	// 对level=0的进行汇总
	private void sumAccount(int index) {
		PaymentSplitEntryInfo curEntry = (PaymentSplitEntryInfo) kdtEntrys.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentSplitEntryInfo entry;
		if ((curEntry.getCostAccount().getCurProject().isIsLeaf()) && (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCostAccount().getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
					if (fdcCostSplit.isChildProjSameAcct(entry, curEntry)) {
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
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
					if (entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumAccount(i);
							if (entry.getAmount() != null)
								amtTotal = amtTotal.add(entry.getAmount());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			}
		}
	}

	protected void checkbeforeSave() {
		checkDirAmt();
		checkOver();
		super.checkbeforeSave();
	}

	private void checkOver() {
		// /////////////////////////////////////////////////////////////////////////
		// 校验_工程量确认流程与付款流程分离参数（参数FDC317）
		check4WorkLoadSeparate();
		// /////////////////////////////////////////////////////////////////////////
		
		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			BigDecimal splited = FDCHelper.toBigDecimal(row.getCell("splitedCostAmt").getValue(), 2);
			BigDecimal splitThis = FDCHelper.toBigDecimal(row.getCell("amount").getValue(), 2);
			BigDecimal total = FDCHelper.toBigDecimal(row.getCell("costAmt").getValue(), 2);
			if (splited.add(splitThis).compareTo(total) == 1) {
				MsgBox.showWarning(this, "已拆分金额大于成本拆分金额，不能保存！");
				SysUtil.abort();
			}
		}
	}

	// 检查是否存在有直接金额，但没进行对应拆分的情况。
	private void checkDirAmt() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				if (getDetailTable().getRow(i).getCell("directAmt").getValue() != null) {
					BigDecimal dir = FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("directAmt").getValue());
					if (dir.compareTo(FDCHelper.ZERO) > 0) {
						BigDecimal dirAmt = FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("amount").getValue());
						if (dir.compareTo(dirAmt) != 0) {
							MsgBox.showWarning(this, FDCSplitClientHelper.getRes("haveDirAmt"));
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	protected void checkBeforeEditOrRemove(String warning) throws BOSException {
		if(editData == null) return;
		super.checkBeforeEditOrRemove(warning);
		String wlSplitId = editData.getId().toString();
		try{
			if (SettledMonthlyHelper.existObject(null, PAYMENTSPLITBOSTYPE,wlSplitId)) {
				MsgBox.showWarning(this,
						"财务成本月结已经引用本数据，不能删除，如需修改，请把此工程量对应的合同进入待处理流程！");
				SysUtil.abort();
			}
			
			//作废检查
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", wlSplitId,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE));
			if (getBizInterface().exists(filter)) {
				MsgBox.showWarning(this, "存在已经作废的记录，不能执行删除操作！");
				SysUtil.abort();
			}
			
			//是否被调整单或工程量确认单引用
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", wlSplitId,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));//#2
			filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE));
			filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE));
			filter.setMaskString("#0 and #1 and (#2 or #3)");
			if (getBizInterface().exists(filter)) {
				MsgBox.showWarning(this, "存在被调整单或者工程量确认单引用的工程量拆分，不能执行删除操作！");
				SysUtil.abort();
			}
			
			//工程量确认单分录
			FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
			sqlBuilder.appendSql("select pe.fid from t_fnc_workloadcostvoucherentry pe left join t_fnc_paymentsplit ps on pe.fparentid=ps.FWorkLoadBillID where ");
			sqlBuilder.appendParam("ps.fworkLoadbillId", wlSplitId);
			IRowSet rowSet = sqlBuilder.executeQuery();
			if (rowSet.size() > 0) {
				MsgBox.showWarning(this, "对应的工程量确认单已经生成凭证，不能执行删除操作！");
				SysUtil.abort();
			}
		}catch(Exception e){
			throw new BOSException(e);
		}
		
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getDescription() != null && !editData.getDescription().equals("AutoSplit")) {
			btnSplit.doClick();
		}
		 isParentCostAccount();
		isChangeData();	
		super.actionSave_actionPerformed(e);
		setFirstLine();
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
	public void isParentCostAccount() {
		// 在启用财务一体化复杂模式下做此判断
		if (isFinacial()) {
			if (kdtEntrys.getRowCount() > 0) {
				for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
					Object obj = kdtEntrys.getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo)
							&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (!entry.getCostAccount().isIsLeaf()) {
							MsgBox.showInfo(this, "工程量拆分必须拆分到项目的成本科目体系中最明细的科目，不能保存！");
							SysUtil.abort();
						}
					}
				}
			}
		}
	}
	
	/* 添加直接金额的可录控制
	 * (non-Javadoc)
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI#setDisplay()
	 */
	protected void setDisplay() {
		super.setDisplay();
		setDirectAmtDisplay();
	}
	private void setDirectAmtDisplay() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentSplitEntryInfo) && ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
					IRow row = getDetailTable().getRow(i);
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						row.getStyleAttributes().setBackground(new Color(0xF6F6BF));
						row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));
					} else {
						row.getStyleAttributes().setBackground(new Color(0xF5F5E6));
						row.getCell("amount").getStyleAttributes().setLocked(true);
					}
					if (entry.getCostAccount() != null && entry.getCostAccount().getCurProject() != null && (entry.getCostAccount().getCurProject().isIsLeaf()) && (entry.isIsLeaf())) {
						row.getCell("directAmt").getStyleAttributes().setBackground(new Color(0xFFFFFF));
						KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
						formattedTextField.setPrecision(2);
						formattedTextField.setRemoveingZeroInDispaly(false);
						formattedTextField.setRemoveingZeroInEdit(false);
						formattedTextField.setNegatived(true);
						formattedTextField.setMaximumValue(FDCHelper.toBigDecimal(row.getCell("costAmt").getValue(), 2).subtract(FDCHelper.toBigDecimal(row.getCell("splitedCostAmt").getValue(), 2)));
						formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
						ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
						row.getCell("directAmt").setEditor(numberEditor);
					} else {
						// row.getCell("directAmt").getStyleAttributes().
						// setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						row.getCell("directAmt").getStyleAttributes().setLocked(true);
					}
				}
			}
		}
	}
	
	protected void showSubmitSuccess() {
		setMessageText(getClassAlise() + " "
				+ EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
		if (chkMenuItemSubmitAndAddNew.isSelected())
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource
							.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
		else if (!chkMenuItemSubmitAndPrint.isSelected() && chkMenuItemSubmitAndAddNew.isSelected())
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource
							.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
		else
			setNextMessageText(getClassAlise() + " "
					+ EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
		setIsShowTextOnly(false);
		setShowMessagePolicy(0);
		showMessage();
	}
	
	protected void showSaveSuccess() {
		setMessageText("工程量拆分"
				+ " "
				+ EASResource
						.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
		setNextMessageText("工程量拆分" + " "
				+ EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
		setShowMessagePolicy(0);
		setIsShowTextOnly(false);
		showMessage();
	}

	protected void showEdit() {
		setMessageText("工程量拆分" + " "
				+ EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
		setIsShowTextOnly(true);
		showMessage();
	}
	
	 protected void showAddNew() {
		setMessageText("工程量拆分" + " "
				+ EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
		setIsShowTextOnly(true);
		showMessage();
	}

	 public void actionAcctSelect_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionAcctSelect_actionPerformed(arg0);
		if (this.getUIContext().get("isCanEnable") != null) {
			Boolean flag = Boolean.valueOf(this.getUIContext().get("isCanEnable").toString());
			if (!flag.booleanValue()) {
				this.btnAutoMatchSplit.setEnabled(false);
				this.btnSplitBaseOnProp.setEnabled(false);
				this.menuItemAutoMatchSplit.setEnabled(false);
			}
		}
		setSplitedCostAmt();
		setFirstLine();
	}
	 
	 // 多级成本管控已拆分成本
	private void setSplitedCostAmt() throws Exception {
		PaymentCostSplitImportHandler handler = new PaymentCostSplitImportHandler(null);
		Map entryMap = handler.handleSplitedCostAmt(editData);
		for (int i = 0; i < getDetailTable().getRowCount(); i++) {
			IRow tempRow = getDetailTable().getRow(i);
			Object obj = tempRow.getUserObject();
			if (obj instanceof PaymentSplitEntryInfo) {
				PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
				if (entry.getLevel() == 0) {
					String key = entry.getCostAccount().getId().toString();
					String costbillid = entry.getCostBillId() == null ? "" : entry.getCostBillId()
							.toString();
					key = key + costbillid;
					if (entry.getSplitType() != null) {
						key = key + entry.getSplitType().getValue();
					}
					if (entry.getProduct() != null) {
						key = key + entry.getProduct().getId().toString();
					}
					if (entryMap.containsKey(key)) {
						PaymentSplitEntryInfo mapEntry = (PaymentSplitEntryInfo) entryMap.get(key);
						tempRow.getCell("splitedCostAmt").setValue(mapEntry.getAmount());
					}
				}
			}
		}

	}
	 public boolean directAmount() {

		return false;
	}

	 /**
	 * 
	 * @description 判断是否改变了数据
	 * @author 向晓帆
	 * @createDate 2011-11-1
	 * @return
	 * @version EAS7.0
	 */
	public void isChangeData() {
		// 不能这么判断
		/*
		 * for (int i = 0; i < getDetailTable().getRowCount(); i++) { IRow row =
		 * getDetailTable().getRow(i); Object obj = row.getUserObject(); if
		 * (!(obj instanceof PaymentSplitEntryInfo) || ((PaymentSplitEntryInfo)
		 * obj).getLevel() > -1) { if (null ==
		 * row.getCell("directAmt").getValue()) {
		 * MsgBox.showWarning("数据没做任何改变，不能保存。"); SysUtil.abort(); } } }
		 */
	}
	
	/**
	 * 描述：统计总金额
	 * 
	 * @param key
	 * @author rd_skyiter_wang
	 * @createDate 2014-12-30
	 */
	protected void calAmtTotal(String key) {
		KDTable table = getDetailTable();
		if (FDCTableHelper.isEmpty(table)) {
			return;
		}

		BigDecimal amountTotal = FDCHelper.ZERO;
		BigDecimal amount = FDCHelper.ZERO;
		PaymentSplitEntryInfo entry = null;
		// 计算拆分总金额
		for (int i = 0; i < table.getRowCount(); i++) {
			entry = (PaymentSplitEntryInfo) table.getRow(i).getUserObject();
			if (entry.getLevel() == 0) {
				IRow row = table.getRow(i);

				amount = (BigDecimal) row.getCell(key).getValue();
				if (amount != null) {
					amountTotal = amountTotal.add(amount);
				}
			}
		}
		getDetailTable().getCell(0, key).setValue(amountTotal);
	}
}