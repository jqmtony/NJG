/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.netctrl.IMutexServiceControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCParamInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.AttachmentUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.IContractSettlementBill;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.costindexdb.client.BuildPriceIndexEditUI;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitContract;
import com.kingdee.eas.fdc.costindexdb.database.BuildSplitDataType;
import com.kingdee.eas.fdc.costindexdb.database.client.BuildSplitBillEditUI;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillCollection;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * 
 * 描述:结算单编辑界面
 * 
 * @author liupd date:2006-10-10
 *         <p>
 * @version EAS5.1.3
 */
public class ContractSettlementBillEditUI extends
		AbstractContractSettlementBillEditUI {
	private static boolean isFirstLoad = true;
	private static final Logger logger = CoreUIObject
			.getLogger(ContractSettlementBillEditUI.class);

	// 结算详细信息helper
	private SettlementDetailHelper settlementDetailHelper;
	private final static Set uiSet = new HashSet();
	// 数量
	private boolean isQualityGuarante = false;
	
	private boolean isKeep6ForGuarantterate = false;//FDC_PARAM_FDC224_KEEP6FORGUARANTERATE

	//合同结算时是否要求变更必须结算完毕
	boolean isContractChangeMustComplete =false;
	
	// 合同可进行多次结算
	private boolean canSetterMore = false;

	//是否严格控制超过比例结算
	private boolean isOverContractAmt=false;
	private Map totalSettleMap = null;
	private Map perMap = null;
	
	//当前单据是否有附件
	private boolean isHasAttachment = false;
	//当前单据是否有归档稿
	private boolean isHasAttenTwo = false;
	
	//允许结算预付款的次数
	//private int advancePaymentNumber = 1;
    //是否允许多次结算
	private boolean isMoreSettlement = false;
	//合同结算造价必须等于合同最新造价 建发专用
	private String settleAmtEqualsLasterPrice = "2";
	
	private KDWorkButton btnImportSplit = new KDWorkButton("引入拆分");
	private KDWorkButton btnRemoveSplit = new KDWorkButton("删除分录");
	
	/**
	 * 汇率是否取合同签订时的汇率
	 */
	private boolean selectExeRateFromContractbill = false;
	
	/**
	 * output class constructor
	 */
	
	private String conSettType = "rdContractOverRate";
	
	private String costCenterConstRate = "0.00";
	
	public void formatCtrl(KDFormattedTextField ctrl){
		if(isKeep6ForGuarantterate){
			ctrl.setPrecision(6);
		}else{
			ctrl.setPrecision(2);
		}
	}
	
	
	public ContractSettlementBillEditUI() throws Exception {
		super();
		settlementDetailHelper = new SettlementDetailHelper(this);
		jbInit();
	}

	private void jbInit() {
		titleMain = getUITitle();
		cbFinalSettle.addItems(BooleanEnum.getEnumList().toArray());
	}

	// 业务日期变化事件
	protected void bookedDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		if (contractBill == null) {
			return;
		}
		String projectId = this.contractBill.getCurProject().getId().toString();
		fetchPeriod(e, pkbookedDate, cbPeriod, projectId, true);
	}

	protected void cbFinalSettle_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {

		if (STATUS_VIEW.equals(getOprtState())
				|| STATUS_FINDVIEW.equals(getOprtState()))
			return;
		if (e == null || e.getStateChange() == ItemEvent.SELECTED) {
			Object newValue = cbFinalSettle.getSelectedItem();
			if (BooleanEnum.TRUE.equals(newValue)) {
				settlementDetailHelper.unLock(true);
			} else {
				settlementDetailHelper.unLock(false);
			}
		}
	}

	private void currencyChanged(DataChangeEvent e) throws Exception {

		Object newValue = e.getNewValue();
		if (newValue instanceof CurrencyInfo) {
			if (isFirstLoad && !getOprtState().equals(OprtState.ADDNEW))
				return;
			if (e.getOldValue() != null
					&& ((CurrencyInfo) e.getOldValue()).getId().equals(
							((CurrencyInfo) newValue).getId())) {
				// 设置汇率的值，在录入界面点新增时可能的情况
				return;
			}

			BOSUuid srcid = ((CurrencyInfo) newValue).getId();
			setExchangeRate(srcid);
		}
	}

	/**
	 * 如果汇率不取合同签订时的汇率，即需要取即时汇率， added by Owen_wen 2010-10-09
	 * @param srcid
	 * @throws Exception
	 */
	protected void setExchangeRate(BOSUuid srcid) throws Exception {
		if (this.selectExeRateFromContractbill){
			txtExchangeRate.setValue(contractBill.getExRate());
		}
		else {
			Date bookedDate = (Date) pkbookedDate.getValue();
			ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, srcid, company, bookedDate);
	
			int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
			BigDecimal exRate = FDCHelper.ONE;
			int exPrecision = curPrecision;
	
			if (exchangeRate != null) {
				exRate = exchangeRate.getConvertRate();
				exPrecision = exchangeRate.getPrecision();
			}
			
			txtExchangeRate.setValue(exRate);
			txtExchangeRate.setPrecision(exPrecision);
			txtOriginalAmount.setPrecision(curPrecision);
		}
	}

	private void modifySettlePrice() {
		BigDecimal originalAmt = FDCHelper.toBigDecimal(txtOriginalAmount
				.getBigDecimalValue());
		BigDecimal exchangeRate = FDCHelper.toBigDecimal(txtExchangeRate
				.getBigDecimalValue());

		BigDecimal amount = FDCHelper.ZERO;
		if (originalAmt.compareTo(FDCHelper.ZERO) == 0
				|| exchangeRate.compareTo(FDCHelper.ZERO) == 0) {
			txtSettlePrice.setValue(FDCHelper.ZERO);
			// return; // by Cassiel_peng
		} else {
			amount = FDCHelper.divide(originalAmt.multiply(exchangeRate), FDCHelper.ONE, 2, BigDecimal.ROUND_HALF_UP);
			txtSettlePrice.setValue(amount);
		}

		// 累计结算原币= 以前的原币+这次的原币
		if (totalSettleMap == null) {
			txtTotalSettlePrice.setValue(amount);
			txtTotalOriginalAmount.setValue(originalAmt);
		} else {

			BigDecimal totalAmount = (BigDecimal) totalSettleMap
					.get("SettlePrice");
			txtTotalSettlePrice.setValue(FDCHelper.add(totalAmount, amount));

			totalAmount = (BigDecimal) totalSettleMap.get("OriginalAmount");
			txtTotalOriginalAmount.setValue(totalAmount.add(originalAmt));
		}
	}
	
	//成本指标库按钮功能
	protected void btnCostIndex_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId() == null){
			MsgBox.showInfo("请先保存单据！");
		}else{
			Iterator iterator = uiSet.iterator();
			if(!iterator.hasNext())
				return;
			KDTable kdtSplitEntry = ((SettlementCostSplitEditUI)iterator.next()).getDetailTable();
			IRowSet rs = null;
			FDCSQLBuilder cpBuilder = new FDCSQLBuilder();
			cpBuilder.appendSql("select bill.fid from CT_DAT_BuildSplitBill bill left join CT_DAT_BuildSplitBillEntry entry on bill.fid=entry.fparentid ");
			cpBuilder.appendSql("where bill.CFDataType='contract' and bill.CFContractLevel='endCal' and bill.CFProjectNameID=? and bill.CFCostAccountID=? and bill.CFSourceNumber=?");
			//校验专业要素的楼号拆分
			FDCSQLBuilder proBuilder = new FDCSQLBuilder();
			proBuilder.appendSql("select bill.fid from CT_DAT_BuildSplitBill bill left join CT_DAT_BuildSplitBillEntry entry on bill.fid=entry.fparentid ");
			proBuilder.appendSql("where bill.CFDataType='professPoint' and bill.CFContractLevel='endCal' and bill.CFProjectNameID=? and bill.CFCostAccountID=? and bill.CFSourceNumber=?");
			String billNumber = txtNumber.getText();
			String projectId = null;
			String costId = null;
			for(int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
				if((Integer)kdtSplitEntry.getCell(i,"level").getValue()!=0)
					continue;
				projectId = ((BOSUuid)kdtSplitEntry.getCell(i,"costAccount.curProject.id").getValue()).toString();
				costId = ((BOSUuid)kdtSplitEntry.getCell(i,"costAccount.id").getValue()).toString();
				cpBuilder.addParam(projectId);
				cpBuilder.addParam(costId);
				cpBuilder.addParam(billNumber);
				rs = cpBuilder.executeQuery();
				if(rs.size() == 0){
					MsgBox.showInfo("结算拆分页签的第"+(i+1)+"行记录没有做合同价拆分！");
					abort();
				}
				//校验专业要素
				proBuilder.addParam(projectId);
				proBuilder.addParam(costId);
				proBuilder.addParam(billNumber);
				rs = proBuilder.executeQuery();
				if(rs.size() == 0){
					MsgBox.showInfo("结算拆分页签的第"+(i+1)+"行记录没有做专业要素拆分！");
					abort();
				}
				cpBuilder.getParamaters().clear();
				proBuilder.getParamaters().clear();
			}
			UIContext uiContext = new UIContext(this);
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select fid from CT_COS_BuildPriceIndex where CFContractId='"+contractBill.getId().toString()+"' and CFContractStation='30' and FSourceBillID='"+editData.getId().toString()+"'");
			rs = builder.executeQuery();
			String state = null;
			if(rs.next() && rs.getString(1) != null){
				state = OprtState.VIEW;
				uiContext.put("ID", rs.getString(1));
			}else{
				state = OprtState.ADDNEW;
				uiContext.put("contractInfo", contractBill);
				uiContext.put("contractStationType", "settle");
				uiContext.put("kdtable", kdtSplitEntry);
				uiContext.put("sourceBillId", editData.getId().toString());
			}
			UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(BuildPriceIndexEditUI.class.getName(), uiContext, null,state).show();
		}
	}

	protected void controlDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,
			ControlDateChangeListener listener) throws Exception {
		if ("bookedDate".equals(listener.getShortCut())) {
			bookedDate_dataChanged(e);
		}
		// by Cassiel_peng
		else if ("guaranteAmt".equals(listener.getShortCut())) {
			setQualityGuarante("txtGuaranceAmt", e);

		} else if ("qualityGuarante".equals(listener.getShortCut())) {
			setQualityGuarante("txtqualityGuarante", e);
			isQualityGuarante = true;
		} else if ("qualityGuaranteRate".equals(listener.getShortCut())) {
			setQualityGuarante("txtqualityGuaranteRate", e);
			isQualityGuarante = false;
		}
		// else if("totalSettlePrice".equals(listener.getShortCut())){
		// setQualityGuarante("txtqualityGuaranteRate", e);
		// isQualityGuarante = false;
		// }
		else if ("settlePrice".equals(listener.getShortCut())) {//如果是"结算造价"发生改变，
																// 默认地根据保修金比例来计算保修金
																// by
																// Cassiel_peng
			setQualityGuarante("txtqualityGuaranteRate", e);
			isQualityGuarante = false;
		} else if ("currency".equals(listener.getShortCut())) {

			// 引起汇率的变化
			currencyChanged(e);
			// 引起结算价
			modifySettlePrice();

		} else if ("originalAmount".equals(listener.getShortCut())) {
			// 引起结算价
			modifySettlePrice();
		} else if ("exchageRate".equals(listener.getShortCut())) {
			// 引起结算价
			modifySettlePrice();
		}
	}

	// 业务日期变化事件
	ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener(
			"bookedDate");
	// 累计保修金
	ControlDateChangeListener qualityGuaranteDataChangeLs = new ControlDateChangeListener(
			"qualityGuarante");
	// 保修金
	ControlDateChangeListener guaranteAmtDataChangeLs = new ControlDateChangeListener(
			"guaranteAmt");
	// 保修金比例
	ControlDateChangeListener qualityGuaranteRateDataChangeLs = new ControlDateChangeListener(
			"qualityGuaranteRate");
	// 结算价
	ControlDateChangeListener settlePriceDataChangeLs = new ControlDateChangeListener(
			"settlePrice");
	// 币别
	ControlDateChangeListener currencyDataChangeLs = new ControlDateChangeListener(
			"currency");
	// 原币
	ControlDateChangeListener originalAmountDataChangeLs = new ControlDateChangeListener(
			"originalAmount");
	// 汇率
	ControlDateChangeListener exchageRateDataChangeLs = new ControlDateChangeListener(
			"exchageRate");

	// 加载完数据后加上监听器
	protected void attachListeners() {
		// by Cassiel_peng
		txtGuaranceAmt.addDataChangeListener(guaranteAmtDataChangeLs);

		txtqualityGuarante.addDataChangeListener(qualityGuaranteDataChangeLs);
		txtqualityGuaranteRate
				.addDataChangeListener(qualityGuaranteRateDataChangeLs);
		// txtSettlePrice.addDataChangeListener(settlePriceDataChangeLs);
		txtTotalSettlePrice.addDataChangeListener(settlePriceDataChangeLs);

		pkbookedDate.addDataChangeListener(bookedDateChangeListener);

		prmtCurrency.addDataChangeListener(currencyDataChangeLs);
		txtOriginalAmount.addDataChangeListener(originalAmountDataChangeLs);
		txtExchangeRate.addDataChangeListener(exchageRateDataChangeLs);

		addDataChangeListener(txtbuildArea);

		addDataChangeListener(cbFinalSettle);
		perMap = new HashMap();
		try {
			// 违约的权限
			if (PermissionFactory
					.getRemoteInstance()
					.hasFunctionPermission(
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentUserInfo().getId().toString()),
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentOrgUnit().getId().toString()),
							new MetaDataPK(
									"com.kingdee.eas.fdc.contract.client.CompensationBillListUI"),
							new MetaDataPK("ActionOnLoad"))) {
				perMap.put(this.panelCompensation.getName(), Boolean.TRUE);
			}
			// 扣款的权限
			if (PermissionFactory
					.getRemoteInstance()
					.hasFunctionPermission(
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentUserInfo().getId().toString()),
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentOrgUnit().getId().toString()),
							new MetaDataPK(
									"com.kingdee.eas.fdc.contract.client.DeductListUI"),
							new MetaDataPK("ActionOnLoad"))) {
				perMap.put(this.panelDeduct.getName(), Boolean.TRUE);
			}
			// 奖励的权限
			if (PermissionFactory
					.getRemoteInstance()
					.hasFunctionPermission(
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentUserInfo().getId().toString()),
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentOrgUnit().getId().toString()),
							new MetaDataPK(
									"com.kingdee.eas.fdc.contract.client.GuerdonBillListUI"),
							new MetaDataPK("ActionOnLoad"))) {
				perMap.put(this.panelGuerdon.getName(), Boolean.TRUE);
			}
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		this.tabTop.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// TODO 自动生成方法存根
				String panelName = tabTop.getSelectedComponent().getName();
				if ((panelName.equals(panelCompensation.getName())
						|| panelName.equals(panelDeduct.getName()) || panelName
						.equals(panelGuerdon.getName()))
						&& !perMap.containsKey(panelName)) {
					if (panelName.equals(panelCompensation.getName()))
						tblCompensationBill.setVisible(false);
					else if (panelName.equals(panelDeduct.getName()))
						tblDeduct.setVisible(false);
					else if (panelName.equals(panelGuerdon.getName()))
						tblGuerdon.setVisible(false);
					MsgBox.showError(ContractClientUtils.getRes("NoPermission")
							.replaceAll(
									"\\{0\\}",
									SysContext.getSysContext()
											.getCurrentOrgUnit().getName()));
				}
			}

		});
	}

	// 注销监听器
	protected void detachListeners() {
		// by Cassiel_peng
		txtGuaranceAmt.removeDataChangeListener(guaranteAmtDataChangeLs);

		txtqualityGuarante
				.removeDataChangeListener(qualityGuaranteDataChangeLs);
		txtqualityGuaranteRate
				.removeDataChangeListener(qualityGuaranteRateDataChangeLs);
		// txtSettlePrice.removeDataChangeListener(settlePriceDataChangeLs);
		txtTotalSettlePrice.removeDataChangeListener(settlePriceDataChangeLs);

		pkbookedDate.removeDataChangeListener(bookedDateChangeListener);

		prmtCurrency.removeDataChangeListener(currencyDataChangeLs);
		txtOriginalAmount.removeDataChangeListener(originalAmountDataChangeLs);
		txtExchangeRate.removeDataChangeListener(exchageRateDataChangeLs);

		removeDataChangeListener(txtGuaranceAmt);
		removeDataChangeListener(txtbuildArea);
		removeDataChangeListener(txtqualityGuarante);
		removeDataChangeListener(txtqualityGuaranteRate);
		removeDataChangeListener(txtSettlePrice);

		removeDataChangeListener(cbFinalSettle);
	}

	public void loadFields() {

		// 先注销监听器
		detachListeners();

		super.loadFields();

		// fillCompensationBills();
		settlementDetailHelper.fill();
		setSaveActionStatus();

		CurProjectInfo curProjectInfo = editData.getCurProject();
		if (editData != null && editData.getContractBill() != null
				&& editData.getContractBill().getCurProject() != null
				&& curProjectInfo == null) {
			String projId = editData.getContractBill().getCurProject().getId()
					.toString();
			curProjectInfo = FDCClientUtils.getProjectInfoForDisp(projId);
		}

		txtProj.setText(curProjectInfo.getDisplayName());
		FullOrgUnitInfo costOrg = FDCClientUtils
				.getCostOrgByProj(curProjectInfo.getId().toString());
		txtOrgUnit.setText(costOrg.getDisplayName());
		editData.setOrgUnit(costOrg);
		editData.setCU(curProjectInfo.getCU());

		// 如果是新增
		if (STATUS_ADDNEW.equals(getOprtState())) {
			this.actionNextPerson.setVisible(false);
		}

		// 如果币别等于本位币,那么汇率不可编辑,本位币不可编辑
		CurrencyInfo currency = (CurrencyInfo) prmtCurrency.getValue();
		if (currency != null
				&& currency.getId().toString().endsWith(
						this.baseCurrency.getId().toString())) {
			this.txtExchangeRate.setEnabled(false);
			this.txtSettlePrice.setEnabled(false);
		} else {
			this.txtExchangeRate.setEnabled(true);
		}
		this.txtSettlePrice.setEnabled(false);

		// 下表选择第一个页签
		tabTop.setSelectedIndex(0);

		// 结算价
		BigDecimal price = txtSettlePrice.getBigDecimalValue();
		if (price != null) {
			settlePrice = price.toString();
		}

		if (totalSettleMap == null) {
			totalSettleMap = new HashMap();
			totalSettleMap.put("OriginalAmount", editData
					.getTotalOriginalAmount().subtract(
							editData.getCurOriginalAmount()));
			totalSettleMap.put("SettlePrice", editData.getTotalSettlePrice()
					.subtract(editData.getCurSettlePrice()));
		}

		// 加载完数据后加上
		attachListeners();

		if (STATUS_VIEW.equals(getOprtState())
				|| BooleanEnum.FALSE.equals(editData.getIsFinalSettle())) {
			try {
				settlementDetailHelper.unLock(false);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		
		try {
			loadSettlementSplitUI();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void storeFields() {
		super.storeFields();

		ContractSettlementBillInfo objectValue = (ContractSettlementBillInfo) editData;
		if (BooleanEnum.TRUE.equals(objectValue.getIsFinalSettle())) {
			objectValue.setOriginalAmount(objectValue.getTotalOriginalAmount());
			objectValue.setSettlePrice(objectValue.getTotalSettlePrice());
		} else {
			objectValue.setOriginalAmount(objectValue.getCurOriginalAmount());
			objectValue.setSettlePrice(objectValue.getCurSettlePrice());
		}
	}

	// 设置精度
	protected void setPrecision() {
		CurrencyInfo currency = editData.getCurrency();
		Date bookedDate = (Date) editData.getBookedDate();

		ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,
					currency.getId(), company, bookedDate);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

		int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
		int exPrecision = curPrecision;

		if (exchangeRate != null) {
			exPrecision = exchangeRate.getPrecision();
		}

		txtExchangeRate.setPrecision(exPrecision);
		txtOriginalAmount.setPrecision(curPrecision);
	}

	// private void setComSumAmount() {
	// BigDecimal sum = FDCHelper.ZERO;
	// for (int i = 0; i < this.tblCompensationBill.getRowCount(); i++) {
	// IRow row = this.tblCompensationBill.getRow(i);
	// Boolean select = (Boolean) row.getCell("select").getValue();
	// if (select.booleanValue()) {
	// BigDecimal amount = (BigDecimal) row.getCell("amount")
	// .getValue();
	// if (amount != null) {
	// sum = sum.add(amount);
	// }
	// }
	// }
	// this.txtCompensationAmount.setValue(sum);
	// BigDecimal settleAmount = this.txtSettlePrice.getBigDecimalValue();
	// if (settleAmount == null) {
	// settleAmount = FDCHelper.ZERO;
	// }
	// this.txtFinalAmount.setValue(settleAmount.subtract(sum));
	// }

	/*
	 * private void fillCompensationBills() { tblCompensationBill.removeRows();
	 * tblCompensationBill.getColumn("amount").getStyleAttributes()
	 * .setHorizontalAlign(HorizontalAlignment.RIGHT);
	 * tblCompensationBill.getColumn("amount").getStyleAttributes()
	 * .setNumberFormat(FDCHelper.getNumberFtm(2)); String formatString =
	 * "yyyy-MM-dd";
	 * tblCompensationBill.getColumn("createDate").getStyleAttributes()
	 * .setNumberFormat(formatString); CompensationBillCollection coll = new
	 * CompensationBillCollection(); try { String contractId = (String)
	 * getUIContext().get("contractBillId"); if (contractId == null) { String
	 * settId = getUIContext().get(UIContext.ID).toString(); if (settId != null)
	 * { ContractSettlementBillInfo sett = (ContractSettlementBillInfo) this
	 * .getValue(new ObjectUuidPK(BOSUuid.read(settId))); contractId =
	 * sett.getContractBill().getId().toString(); } } EntityViewInfo conView =
	 * new EntityViewInfo(); conView.getSelector().add(new
	 * SelectorItemInfo("*")); conView.getSelector().add(new
	 * SelectorItemInfo("currency.*")); conView.getSelector().add(new
	 * SelectorItemInfo("creator.*")); conView.getSelector().add( new
	 * SelectorItemInfo("compensationType.*")); FilterInfo filter = new
	 * FilterInfo(); conView.setFilter(filter); filter.getFilterItems().add( new
	 * FilterItemInfo("contract.Id", contractId)); coll =
	 * CompensationBillFactory.getRemoteInstance()
	 * .getCompensationBillCollection(conView); } catch (Exception e) {
	 * e.printStackTrace(); this.abort(e); }
	 * 
	 * for (int i = 0; i < coll.size(); i++) { CompensationBillInfo info =
	 * coll.get(i); if (info.getAuditor() == null) { continue; } IRow row =
	 * tblCompensationBill.addRow(); row.setUserObject(info); if
	 * (info.getDescription() != null) {
	 * row.getCell("select").setValue(Boolean.TRUE); } else {
	 * row.getCell("select").setValue(Boolean.FALSE); }
	 * row.getCell("number").setValue(info.getNumber());
	 * row.getCell("name").setValue(info.getName());
	 * row.getCell("type").setValue(info.getCompensationType());
	 * row.getCell("amount").setValue(info.getAmount());
	 * row.getCell("deductType").setValue(
	 * CompensationBillEditUI.getResource("settleDeduct")); if
	 * (info.getCreator() != null) {
	 * row.getCell("creator").setValue(info.getCreator().getName()); }
	 * row.getCell("createDate").setValue(info.getCreateTime()); }
	 * this.tblCompensationBill.getStyleAttributes().setLocked(true); }
	 */

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		checkFinalSettleDup();

		super.actionCopy_actionPerformed(e);
		editData.setSourceType(SourceTypeEnum.ADDNEW);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkFinalSettleDup();

		super.actionAddNew_actionPerformed(e);
	}

	public void onShow() throws Exception {
		super.onShow();
		// 鑫苑：结算单查询中增加打印的按钮 by Cassiel_peng
		btnPrint.setVisible(true);
		btnPrint.setEnabled(true);
		btnPrintPreview.setVisible(true);
		btnPrintPreview.setEnabled(true);
		actionPrint.setVisible(true);
		actionPrint.setEnabled(true);
		actionPrintPreview.setVisible(true);
		actionPrintPreview.setEnabled(true);
		getNumberCtrl().requestFocus();
		/*
		 * SwingUtilities.invokeLater(new Runnable(){ public void run() { try {
		 * settlementDetailHelper.fillCollectPanel(); } catch (Exception e) {
		 * handUIException(e); } } });
		 */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (txtNumber.isEnabled()) {
					txtNumber.requestFocus(true);
				} else {
					txtbillName.requestFocus(true);
				}

			}
		});
		menuView.setVisible(false);
	}

	protected void fetchInitData() throws Exception {
		// 合同Id
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId == null) {
			Object obj = getUIContext().get("ID");
			String settId = null;
			// 工作流界面传过来时BOSUuid
			if (obj instanceof BOSUuid) {
				settId = ((BOSUuid) obj).toString();
			} else {
				settId = (String) obj;
			}

			if (settId != null) {
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder
						.appendSql("select fcontractBillId from T_CON_ContractSettlementBill where fid=?");
				builder.addParam(settId);
				IRowSet rowSet = builder.executeQuery();
				if (rowSet.size() == 1) {
					rowSet.next();
					getUIContext().put("contractBillId",
							rowSet.getString("fcontractBillId"));
				}
			}
		}
		super.fetchInitData();

	}

	/**
	 * by Cassiel_peng
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		ConSettlementPrintProvider data = new ConSettlementPrintProvider(
				editData.getString("id"), getTDQueryPK(),getATTCHQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		ConSettlementPrintProvider data = new ConSettlementPrintProvider(
				editData.getString("id"), getTDQueryPK(),getATTCHQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	// 获得结算单套打对应的目录
	protected String getTDFileName() {
		return "/bim/fdc/contract/ContractSettlement";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.ContractSettlementBillForPrintQuery");
	}
	
	// 对应的套打Query
	protected IMetaDataPK getATTCHQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.AttchmentForPrintQuery");
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractSettlementBillEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ContractSettlementBillFactory
				.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return null;
	}

	/**
	 * 子类重载此方法，负责清空一些不能重复的域的值
	 */
	protected void setFieldsNull(AbstractObjectValue newData) {
		super.setFieldsNull(newData);
		ContractSettlementBillInfo info = (ContractSettlementBillInfo) newData;
		info.setContractBill(editData.getContractBill());
		info.setContractBillNumber(editData.getContractBillNumber());
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {

		ContractSettlementBillInfo objectValue = new ContractSettlementBillInfo();
		objectValue.setCreator((UserInfo) (SysContext.getSysContext()
				.getCurrentUserInfo()));
		// objectValue.setCreateTime(new Timestamp(new Date().getTime()));
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		}
		objectValue.setSourceType(SourceTypeEnum.ADDNEW);

		ContractBillInfo contractBillInfo = contractBill;

		objectValue.setContractBill(contractBillInfo);
		objectValue.setCurProject(this.curProject);

		objectValue.setCurrency(contractBill.getCurrency());
		objectValue.setExchangeRate(contractBill.getExRate());
		objectValue.setIsCostSplit(contractBill.isIsCoseSplit());
		String contractBillNumber = (String) getUIContext().get(
				"contractBillNumber");
		objectValue.setContractBillNumber(contractBillNumber);
		objectValue.setQualityGuaranteRate(contractBillInfo.getGrtRate());
		txtcontractNumber.setText(contractBillInfo.getNumber());
		txtcontractName.setText(contractBillInfo.getName());
		txtqualityGuaranteRate.setValue(contractBillInfo.getGrtRate());
		objectValue.setContractBill(contractBillInfo);
		objectValue.setCU(contractBillInfo.getCU());
		objectValue.setOrgUnit(contractBillInfo.getOrgUnit());
		// 只允许一次结算   此处的逻辑应该是有问题的吧？如果合同允许进行多次结算那么新增的时候就不应该设置该属性值为"是"  by cassiel_peng  2009-12-05
		objectValue.setIsFinalSettle(BooleanEnum.TRUE);

		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);
		objectValue.setOrgUnit(orgUnitInfo);

		objectValue.setOriginalAmount(FDCConstants.ZERO);
		objectValue.setSettlePrice(FDCConstants.ZERO);
		objectValue.setCurOriginalAmount(FDCConstants.ZERO);
		objectValue.setCurSettlePrice(FDCConstants.ZERO);

		// 给保修金和累计保修金都设置一个默认值为0吧，否则当结算造价原币初始为0的时候保修金和累计保修金都为空，保存到数据库之后
		// 系统很多地方都极有可能抛NullPointException(如:)
		// com.kingdee.eas.fdc.contract.client
		// .SettlementCostSplitEditUI.setQuaAmt()
		objectValue.setQualityGuarante(FDCConstants.ZERO);
		objectValue.setGuaranteAmt(FDCConstants.ZERO);
		// 计算累计结算加
		try {
			Map param = new HashMap();
			param.put("ContractBillId", contractBill.getId().toString());
			totalSettleMap = ContractFacadeFactory.getRemoteInstance()
					.getTotalSettlePrice(param);
			if (totalSettleMap != null) {
				objectValue.setTotalOriginalAmount((BigDecimal) totalSettleMap
						.get("OriginalAmount"));
				objectValue.setTotalSettlePrice((BigDecimal) totalSettleMap
						.get("SettlePrice"));
			} else {
				objectValue.setTotalOriginalAmount(FDCConstants.ZERO);
				objectValue.setTotalSettlePrice(FDCConstants.ZERO);
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		objectValue.setOwnID(BOSUuid.create("1D604E7D").toString());
		return objectValue;
	}

	// 子类可以重载
	protected void fetchInitParam() throws Exception {

		super.fetchInitParam();
		HashMap param = getParameterValue();
		if (orgUnitInfo != null) {
			
	        
			//HashMap param = FDCUtils.getDefaultFDCParam(null, orgUnitInfo.getId().toString());
			if (param.get(FDCConstants.FDC_PARAM_MORESETTER) != null) {
				canSetterMore = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MORESETTER).toString()).booleanValue();
			}

			
			if(param.get(FDCConstants.FDC_PARAM_FDC323_SELECTEXECHANGERATE) != null){
				selectExeRateFromContractbill = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_FDC323_SELECTEXECHANGERATE).toString()).booleanValue();
			}
			
			if(param.get(FDCConstants.FDC_PARAM_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE) != null){
				isContractChangeMustComplete = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE).toString()).booleanValue();
			}
			if(param.get(FDCConstants.FDC_PARAM_SETTLEMENTOVERCONTRACTAMOUNT) != null){
				isOverContractAmt = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SETTLEMENTOVERCONTRACTAMOUNT).toString()).booleanValue();
			}
			
			settleAmtEqualsLasterPrice = ParamManager.getParamValue(null, new ObjectUuidPK(orgUnitInfo.getId().toString()),
					FDCConstants.FDC_PARAM_SETTLEAMTEQUALSLATESTPRICE);
		}
		//系统参数
		//HashMap sysParam = FDCUtils.getDefaultFDCParam(null, null);
		if(param.get(FDCConstants.FDC_PARAM_MORESETTER) != null){
			isMoreSettlement = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MORESETTER).toString()).booleanValue();
		}
		if(param.get(FDCConstants.FDC_PARAM_FDC224_KEEP6FORGUARANTERATE) != null){
			isKeep6ForGuarantterate = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_FDC224_KEEP6FORGUARANTERATE).toString()).booleanValue();
		}
		
	}


	private HashMap getParameterValue() throws BOSException, EASBizException {
		HashMap param = new HashMap();
		HashMap hmParamIn = new HashMap();
		// 合同可进行多次结算
		if(orgUnitInfo != null){
			IObjectPK comPK = new ObjectUuidPK(orgUnitInfo.getId()
					.toString());
			
			hmParamIn.put(FDCConstants.FDC_PARAM_MORESETTER, null);
			// 合同结算时，汇率是否取合同签订时的汇率
	    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC323_SELECTEXECHANGERATE, null);
	    	//合同结算时是否要求变更必须结算完毕,非集团控制 by zhiyuan_tang 2010/07/28
	        hmParamIn.put(FDCConstants.FDC_PARAM_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE, comPK);
	        hmParamIn.put(FDCConstants.FDC_PARAM_SETTLEMENTOVERCONTRACTAMOUNT, comPK);
	        //项目合同签约总金额超过项目
	        hmParamIn.put(FDCConstants.FDC_PARAM_OUTCOST, comPK);
	      //合同结算造价必须等于合同最新造价
			hmParamIn.put(FDCConstants.FDC_PARAM_SETTLEAMTEQUALSLATESTPRICE, comPK);
	      
	        
		}
		//结算单上保修金比例是否保留6位小数
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC224_KEEP6FORGUARANTERATE, null);
    	param = FDCUtils.getParamHashMapBatch(null, hmParamIn);
		return param;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#onLoad()
	 */
	public void onLoad() throws Exception {
		// 工作流中的滚动条
		
		this.setPreferredSize(new Dimension(1013, 600));
		txtOriginalAmount.setDataType(1);
		txtcontractName.setMaxLength(200);
		formatCtrl(this.txtqualityGuaranteRate);
		super.onLoad();
		
		
		this.btnAttenTwo.setIcon(EASResource.getIcon("imgTbtn_addline"));
		
		// FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
		// public void run() {
		// try {		
		settlementDetailHelper.fillCollectPanel();
		// } catch (Exception e) {
		// handUIException(e);
		// }
		// }
		// });
		if(this.getUIContext().get("isShowPanelCollection")!=null){
			// Boolean.parseBoolean(this.getUIContext().get("isShowPanelCollection").toString());
			boolean b=Boolean.valueOf(this.getUIContext().get("isShowPanelCollection").toString()).booleanValue();
			if(!b){
				this.tabTop.remove(panelCollection);
			}
		}

		txtOriginalAmount.setRequired(true);
		txtSettlePrice.setCommitsOnValidEdit(true);
		txtcontractName.setMaxLength(200);
		txtNumber.setMaxLength(80);
		txtbillName.setMaxLength(80);

		txtgetFeeCriteria.setMaxLength(50);
		txtinfoPrice.setMaxLength(50);
		tetQualityTime.setMaxLength(20);

		actionNext.setEnabled(false);
		actionNext.setVisible(false);
		actionPre.setEnabled(false);
		actionPre.setVisible(false);
		actionLast.setEnabled(false);
		actionLast.setVisible(false);
		actionFirst.setEnabled(false);
		actionFirst.setVisible(false);
		actionSplit.setEnabled(false);
		actionSplit.setVisible(false);
		

		// this.storeFields();
		txtqualityGuaranteRate.setMaximumValue(FDCHelper.ONE_HUNDRED);
		txtqualityGuaranteRate.setMinimumValue(FDCHelper.ZERO);
		// 中渝:根据参数来控制保修金比例是否应该保留6位小数 by Cassiel_peng 2009-10-6
		txtqualityGuaranteRate.setPrecision(6);
		if (!isKeep6ForGuarantterate) {
			txtqualityGuaranteRate.setPrecision(2);
		}
		txtqualityGuarante.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		// 才此处设置了"累计保修金"的最小值为0,导致后续修改保修金比例或者是结算造价原币来反算累计保修金的时候只要计算出来的值小于0就
		// 将"累计保修金"文本框里的值设置为了0,给人的感觉就是没有计算正确！因为"保修金"文本框里既然都可以录入负数，并且通过"结算造价本位币"
		// 和保修金比例计算出来的"保修金"文本框的值都可以是负数，那么"累计保修金额"就应该支持0甚至是负数。 by Cassiel_peng
		// 2008-9-3
		// txtqualityGuarante.setMinimumValue(FDCHelper.ZERO);

		txtExchangeRate.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		txtExchangeRate.setMinimumValue(FDCHelper.ZERO);
		txtExchangeRate.setPrecision(6);
		txtConstructPrice.setMinimumValue(FDCHelper.ZERO);
		if (getOprtState().equals(OprtState.ADDNEW)) {
			txtqualityGuaranteRate.setValue(editData.getContractBill()
					.getGrtRate());

			// 如果是新增,合同是外币,那么重新取汇率
			if (!this.baseCurrency.getId().equals(editData.getCurrency().getId())) {
				setExchangeRate(editData.getCurrency().getId());
			}
		}

		// 应客户要求，合同编码和合同名称可用CTRL+C复制
		if (OprtState.ADDNEW.equals(getOprtState())
				|| OprtState.EDIT.equals(getOprtState())) {
			txtcontractNumber.setEnabled(true);
			txtcontractNumber.setEditable(false);
			txtcontractName.setEnabled(true);
			txtcontractName.setEditable(false);
		}

		setPrecision();

		// FDCClientHelper.initComboCurrency(prmtCurrency, true);

		handleOldData();
		// 检查合同是否可进行多次结算
		if (canSetterMore) {
			cbFinalSettle.setEnabled(true);
			// cbFinalSettle.setSelectedItem(com.kingdee.eas.base.commonquery.
			// BooleanEnum.TRUE);
		} else {
			cbFinalSettle.setEnabled(false);
			kDLabelContainer16.setVisible(false);
			kDLabelContainer17.setVisible(false);
		}

		// 业务日志判断为空时取期间中断
		if (pkbookedDate != null && pkbookedDate.isSupportedEmpty()) {
			pkbookedDate.setSupportedEmpty(false);
		}
		fillAttachmentList();
		
		fillCmBoxTwo();
		
		if (this.editData != null) {//PBG095804。。。老数据归档ID不存在的处理。
			if (StringUtils.isEmpty(this.editData.getOwnID())) {
				String boid = BOSUuid.create("1D604E7D").toString();
				this.editData.setOwnID(boid);

				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("ownID");
				ContractSettlementBillFactory.getRemoteInstance().updatePartial(editData, sic);
			}
				
		}
		if (OprtState.ADDNEW.equals(getOprtState()) || OprtState.EDIT.equals(getOprtState()) || OprtState.VIEW.equals(getOprtState())) {
			txtqualityGuarante.setValue(FDCHelper.add(getLstQualityGuarante(), txtGuaranceAmt.getBigDecimalValue()), false);
		}
		
		btnImportSplit.setIcon(EASResource.getIcon("imgTbtn_collect"));
		btnImportSplit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					btnImportSplit_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemoveSplit.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnRemoveSplit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					btnRemoveSplit_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		actionContractPriceSplit.setEnabled(true);
		actionProfessionSplit.setEnabled(true);
		KDWorkButton btnContractPriceSplit = (KDWorkButton)this.kDContainer1.add(actionContractPriceSplit);
		btnContractPriceSplit.setText("合同价拆分");
		btnContractPriceSplit.setIcon((EASResource.getIcon("imgTbtn_split")));
		KDWorkButton btnProfessionSplit = (KDWorkButton)this.kDContainer1.add(actionProfessionSplit);
		btnProfessionSplit.setText("专业要素拆分");
		btnProfessionSplit.setIcon((EASResource.getIcon("imgTbtn_split")));
		this.kDContainer1.addButton(btnImportSplit);
		this.kDContainer1.addButton(btnRemoveSplit);
	}
	
	private void btnImportSplit_actionPerformed(ActionEvent e) throws Exception {
		Iterator iterator = uiSet.iterator();
		while(iterator.hasNext()){
			((SettlementCostSplitEditUI)iterator.next()).actionImpContrSplit_actionPerformed(e);
		}
	}
	private void btnRemoveSplit_actionPerformed(ActionEvent e) throws Exception {
		Iterator iterator = uiSet.iterator();
		while(iterator.hasNext())
			((SettlementCostSplitEditUI)iterator.next()).actionRemoveLine_actionPerformed(e);
	}
	
	protected void setButtonStatus() {
		super.setButtonStatus();
		boolean flse = (getOprtState().equals("ADDNEW")||getOprtState().equals("EDIT"))?true:false;
		this.btnImportSplit.setEnabled(flse);
		this.btnRemoveSplit.setEnabled(flse);
		actionContractPriceSplit.setEnabled(flse);
		actionProfessionSplit.setEnabled(flse);
		Iterator iterator = uiSet.iterator();
        while(iterator.hasNext()){
        	((SettlementCostSplitEditUI)iterator.next()).kdtEntrys.setEnabled(flse);
        }
	}
	
	public void actionContractPriceSplit_actionPerformed(ActionEvent e) throws Exception {
		Iterator iterator = uiSet.iterator();
		if(!iterator.hasNext())
			return;
		SettlementCostSplitEditUI cseditui = (SettlementCostSplitEditUI)iterator.next();
		cseditui.getOprtState();
		KDTable kdtSplitEntry = cseditui.getDetailTable();
		if(kdtSplitEntry.getSelectManager().size() == 0){
            MsgBox.showInfo("没有选中分录，无法拆分到楼号！");
            return;
        }
        int top = kdtSplitEntry.getSelectManager().get().getTop();
        IRow row = kdtSplitEntry.getRow(top);
        if(row == null){
            MsgBox.showInfo("没有选中分录，无法拆分到楼号！");
            return;
        }
        FDCSplitBillEntryInfo splitEntryInfo = (FDCSplitBillEntryInfo)row.getUserObject();
        if(splitEntryInfo.getLevel() != 0){
        	MsgBox.showInfo("请选择上级记录！");
            return;
        }
        BOSUuid proId = (BOSUuid)row.getCell("costAccount.curProject.id").getValue();
        CurProjectInfo projectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(proId));
		try {
			UIContext uiContext = new UIContext(this);
			IUIWindow ui = null;
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select bill.fid from CT_DAT_BuildSplitBill bill left join CT_DAT_BuildSplitBillEntry entry on bill.fid=entry.fparentid ");
			builder.appendSql("where bill.CFDataType='contract' and bill.CFContractLevel='endCal' ");
			builder.appendSql("and bill.CFProjectNameID='"+projectInfo.getId().toString()+"' ");
			builder.appendSql("and bill.CFCostAccountID='"+splitEntryInfo.getCostAccount().getId().toString()+"' ");
			builder.appendSql("and bill.CFSourceNumber='"+txtNumber.getText()+"'");
			IRowSet rs = builder.executeQuery();
			String state = null;
			if(rs.next()){
				state = OprtState.VIEW;
				uiContext.put("ID",rs.getString(1));
			}else{
				uiContext.put("dataType",BuildSplitDataType.contract);
				uiContext.put("contractLevel",BuildSplitContract.endCal);
				uiContext.put("pointName","合同价");
				uiContext.put("projectName",projectInfo);
				uiContext.put("pointValue",splitEntryInfo.getAmount());
				uiContext.put("sourceNumber",txtNumber.getText());
				uiContext.put("costAccount",splitEntryInfo.getCostAccount());
				state = OprtState.ADDNEW;
			}
			ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BuildSplitBillEditUI.class.getName(),uiContext,null,state);
			ui.show();
		} catch (Exception e1) {
			handUIException(e1);
		}
	}
	
	public void actionProfessionSplit_actionPerformed(ActionEvent e) throws Exception {
		Iterator iterator = uiSet.iterator();
		if(!iterator.hasNext())
			return;
		KDTable kdtSplitEntry = ((SettlementCostSplitEditUI)iterator.next()).getDetailTable();
		if(kdtSplitEntry.getSelectManager().size() == 0){
            MsgBox.showInfo("没有选中分录，无法拆分到楼号！");
            return;
        }
        int top = kdtSplitEntry.getSelectManager().get().getTop();
        IRow row = kdtSplitEntry.getRow(top);
        if(row == null){
            MsgBox.showInfo("没有选中分录，无法拆分到楼号！");
            return;
        }
        FDCSplitBillEntryInfo splitEntryInfo = (FDCSplitBillEntryInfo)row.getUserObject();
        if(splitEntryInfo.getLevel() != 0){
        	MsgBox.showInfo("请选择上级记录！");
            return;
        }
        BOSUuid proId = (BOSUuid)row.getCell("costAccount.curProject.id").getValue();
        CurProjectInfo projectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(proId));
		try {
			UIContext uiContext = new UIContext(this);
			IUIWindow ui = null;
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select bill.fid from CT_DAT_BuildSplitBill bill left join CT_DAT_BuildSplitBillEntry entry on bill.fid=entry.fparentid ");
			builder.appendSql("where bill.CFDataType='professPoint' and bill.CFContractLevel='endCal' ");
			builder.appendSql("and bill.CFProjectNameID='"+projectInfo.getId().toString()+"' ");
			builder.appendSql("and bill.CFCostAccountID='"+splitEntryInfo.getCostAccount().getId().toString()+"' ");
			builder.appendSql("and bill.CFSourceNumber='"+txtNumber.getText()+"'");
			IRowSet rs = builder.executeQuery();
			String state = null;
			if(rs.next()){
				state = OprtState.VIEW;
				uiContext.put("ID",rs.getString(1));
			}else{
				uiContext.put("dataType",BuildSplitDataType.professPoint);
				uiContext.put("contractLevel",BuildSplitContract.endCal);
//				uiContext.put("pointName","合同价");
				uiContext.put("projectName",projectInfo);
				uiContext.put("pointValue",splitEntryInfo.getAmount());
				uiContext.put("sourceNumber",txtNumber.getText());
				uiContext.put("costAccount",splitEntryInfo.getCostAccount());
				state = OprtState.ADDNEW;
			}
			ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BuildSplitBillEditUI.class.getName(),uiContext,null,state);
			ui.show();
		} catch (Exception e1) {
			handUIException(e1);
		}
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		setButtonStatus();
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

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("contractBill.id"));
		sic.add(new SelectorItemInfo("contractBill.isCoseSplit"));
		sic.add(new SelectorItemInfo("splitState"));
		sic.add("contractBillNumber");
		sic.add("contractBill.CurProject.id");
		sic.add("curProject.id");
		sic.add("curProject.displayName");
		sic.add("curProject.CU.number");
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));

		sic.add("currency.number");
		sic.add("currency.name");
		sic.add("amount");
		sic.add("originalAmount");
		sic.add("settlePrice");
		sic.add("buildArea");
		sic.add("getFeeCriteria");
		sic.add("unitPrice");
		sic.add("isFinalSettle");

		sic.add("totalOriginalAmount");
		sic.add("totalSettlePrice");
		sic.add("curOriginalAmount");
		sic.add("curSettlePrice");

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		
		sic.add("ownID");

		return sic;
	}

	public void checkFinalSettleDup() {
		// 检查合同是否已有结算
		String billId = editData.getContractBill().getId().toString();
		ContractSettlementBillCollection billColl = null;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", billId));
		// 单据状态不为“暂存”
		// filter.getFilterItems().add(new FilterItemInfo("state",
		// FDCBillStateEnum.SAVED,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
		view.setFilter(filter);
		try {
			billColl = ContractSettlementBillFactory.getRemoteInstance()
					.getContractSettlementBillCollection(view);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		if (billColl != null && billColl.size() > 0) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("hasFinalSettle"));
			SysUtil.abort();
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}

	// 已经被拆分的结算单，不允许删除 by Cassiel_peng
	protected void checkBeforeRemove() throws Exception {
		boolean isCostSplit = FDCSplitClientHelper.isBillSplited(
				getSelectBOID(), "T_CON_SettNoCostSplit", "FSettlementBillID");
		boolean isNoCostSplit = FDCSplitClientHelper.isBillSplited(
				getSelectBOID(), "T_CON_SettlementCostSplit",
				"FSettlementBillID");
		if (isCostSplit || isNoCostSplit) {
			MsgBox.showWarning("此结算单已拆分，不能删除！");
			SysUtil.abort();
		}

		/*
		 * EntityViewInfo view = new EntityViewInfo(); FilterInfo filter = new
		 * FilterInfo(); filter.getFilterItems().add(new
		 * FilterItemInfo("settlementBill", editData.getId()));
		 * view.setFilter(filter); view.getSelector().add("id");
		 * CoreBillBaseCollection coll =
		 * SettlementCostSplitFactory.getRemoteInstance
		 * ().getCoreBillBaseCollection(view); Iterator iter = coll.iterator();
		 * if (iter.hasNext()) { MsgBox.showWarning(this,
		 * ContractClientUtils.getRes("noRemove")); SysUtil.abort(); }
		 */
	}

	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, txtbillName);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

		super.actionEdit_actionPerformed(e);

		// 如果币别等于本位币,那么汇率不可编辑,本位币不可编辑
		CurrencyInfo currency = (CurrencyInfo) prmtCurrency.getValue();
		if (currency != null
				&& currency.getId().toString().endsWith(
						this.baseCurrency.getId().toString())) {
			this.txtExchangeRate.setEnabled(false);
			this.txtSettlePrice.setEnabled(false);
		} else {
			this.txtExchangeRate.setEnabled(true);
			this.txtSettlePrice.setEnabled(false);
		}

		settlementDetailHelper.unLock(false);
		if (OprtState.EDIT.equals(getOprtState())) {
			txtqualityGuarante.setValue(FDCHelper.add(getLstQualityGuarante(), txtGuaranceAmt.getBigDecimalValue()), false);
		}
	}

	protected void tblCompensationBill_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblCompensationBill_tableClicked(e);
		if (e.getColIndex() == 0) {
			if (this.getOprtState().equals("ADDNEW")
					|| this.getOprtState().equals("EDIT")) {
				IRow row = this.tblCompensationBill.getRow(e.getRowIndex());
				if (row == null) {
					return;
				}
				Boolean b = (Boolean) row.getCell("select").getValue();
				row.getCell("select").setValue(new Boolean(!b.booleanValue()));
			}
		}
	}

	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()==null)
			btnSave.doClick();
 		//改为服务端验证，ken_liu
		/*if(!checkSubmit()){
			return;
		}*/
		// saveCompensationRel();
		if (this.editData.getContractBill().getId() != null) {
			Map paramMap = new HashMap();
			paramMap.put("contractBillId", this.editData.getContractBill()
					.getId().toString());
			paramMap.put("billState", this.editData.getState());
			paramMap.put("txtSettlePrice", txtOriginalAmount
					.getBigDecimalValue());
			paramMap.put("updateAfterSavedToSubmitBillId", this.editData
					.getId());
			ContractClientUtils.checkTotalSettlePriceSmallerThanZero(paramMap);
		}
		checkTotalSettlePrice();
		
		//需求，与工程量确认判断
		boolean isOK = checkWorkloadConConfirm();
		if(!isOK){
			SysUtil.abort();
		}
		/**
		 * 提示：结算单保修金不能大于累计结算价 - 付款累计  也请一起处理
		 * 如果是最终结算，首先要检查结算金额是否小于已付款金额（所有为非保存状态的会款单） 再检查，结算金额（大于等于保修金+已付款金额）
		 * 如果是非最终结算，则只要检查结算金额是否大于等于保修金额+已付款金额 2009-11-12 兰远军 *
		 */
		if (isMoreSettlement) {

			if (((com.kingdee.eas.base.commonquery.BooleanEnum)this.cbFinalSettle.getSelectedItem()).getValue()==BooleanEnum.TRUE_VALUE) {
				checkAmt();
				ContractClientUtils.chkSettAmtOverRate(settleAmtEqualsLasterPrice, this.isOverContractAmt, this.editData.getContractBill()
						.getId(),
						txtTotalOriginalAmount.getBigDecimalValue(), txtOriginalAmount.getBigDecimalValue(), true);
				chkSettlementAmt();
                  
			} else {
				chkSettlementAmt();
			}
		} else {
			checkAmt();
			ContractClientUtils.chkSettAmtOverRate(settleAmtEqualsLasterPrice, this.isOverContractAmt, this.editData.getContractBill()
					.getId(), txtTotalOriginalAmount
					.getBigDecimalValue(), txtOriginalAmount.getBigDecimalValue(), true);
			chkSettlementAmt();
		}
//		 chkSettAmtOverRate();
		settlementDetailHelper.save();
		
		//Add by zhiyuan_tang 2010/07/27   增加参数:合同结算时是否要求变更必须结算完毕
		//判断是否启用参数：合同结算时是否要求变更必须结算完毕
		if (isContractChangeMustComplete) {
			//当启用参数：合同结算时是否要求变更必须结算完毕时,先判断是否有未结算的变更签证确认
			Map paramMap = new HashMap();
			paramMap.put("contractBillId", this.editData.getContractBill()
					.getId().toString());
			if (!isContractChangeSettled(paramMap)) {
				//提示消息
				FDCMsgBox.showWarning(this, "存在未结算的变更，不能结算。");
				// if(JOptionPane.YES_OPTION != result){
					//选择否，退出操作
					abort();
				// }
			}
		}
		runAction(e,"SUBMIT");
		super.actionSubmit_actionPerformed(e);
	}
	/**
	 * 判断是否存在未审核的变更
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean checkSubmit() throws EASBizException, BOSException {
		if(BooleanEnum.TRUE.equals(editData.getIsFinalSettle())){
			String contractId = editData.getContractBill().getId().toString();
			FilterInfo filter = new FilterInfo();
			
			
		
			//是否存在未审核的变更签证申请     by Cassiel_peng
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.SAVED));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.AUDITTING));//不知道这种单据状态是否需要考虑
			filter.setMaskString("#0 and ( #1 or #2 or #3)");
			if(ChangeSupplierEntryFactory.getRemoteInstance().exists(filter)){
				int isGoOn = FDCMsgBox.showConfirm2("存在还未审批通过的变更签证申请，是否确认提交");
				if(isGoOn!=0){
					return true;
				}else{
					return false;
				}
			}
			
			
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING));//不知道这种单据状态是否需要考虑
			filter.setMaskString("#0 and ( #1 or #2 or #3)");
			if(ContractChangeBillFactory.getRemoteInstance().exists(filter)){
				
				int isGoOn = FDCMsgBox.showConfirm2("存在未审核通过的变更签证确认，是否确认提交");
				if(isGoOn==0){
					return true;
				}else{
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 描述：判断是否所有的变更签证确认都已经结算完毕
	 * @param paramMap	一个包含了合同ID的HashMap
	 * @return true:
	 * 				所有的变更签证确认都已经结算完毕。<p>
	 * 		   false:
	 * 				存在未结算的变更签证确认。
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws UuidException
	 * @author zhiyuan_tang 2010/07/28
	 */
	private boolean isContractChangeSettled(Map paramMap) throws BOSException, EASBizException, UuidException {
		
		boolean isContractChangeSettled = false;
		Object contractBillId= paramMap.get("contractBillId");//合同ID
		
		//根据合同获取所有的未结算的变更签证确认
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractBill",contractBillId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("hasSettled", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING, CompareType.NOTEQUALS));
		view.getSelector().add("id");
		view.getSelector().add("number");
		ContractChangeBillCollection ContractChangeCol = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);
		if (ContractChangeCol != null && ContractChangeCol.size() > 0) {
			//不存在未结算的变更签证确认
			isContractChangeSettled = false;
		} else {
			//存在未结算的变更签证确认
			isContractChangeSettled = true;
		}
		return isContractChangeSettled;
	}


	/**
	 * 1、从界面上获取累计保修金(txtqualityGuarante)和累计结算金额本币（txtTotalSettlePrice）
	 * 2、从付款单中取出所有非保存状态的付款单合计累计付款额本币（totalPay）
	 * 3、判断累计结算金额-累计付款额是否大于累计保修金,如果大于则终止。
	 * by 兰远军  2009-11-12
	 */

	public void chkSettlementAmt() throws BOSException {
		if(BooleanEnum.FALSE.equals(this.cbFinalSettle.getSelectedItem())){
				return;
		}
		BigDecimal qualityGuarante = FDCHelper.toBigDecimal(txtqualityGuarante
				.getBigDecimalValue());// 累计保修金
	    BigDecimal leastSettAmt = FDCHelper.toBigDecimal(txtTotalSettlePrice
				.getBigDecimalValue());// 累计结算金额本币
		BigDecimal totalPay = FDCHelper.ZERO;// 累计付款

		//取数方式改变   合同的实付款取的是已关闭的付款申请单对应付款单的合同内工程款+未关闭的付款申请单的合同内工程款之和 by cassiel_peng 2009-12-02
		if(this.editData.getContractBill().getId()!=null){
			totalPay=ContractClientUtils.getPayAmt(this.editData.getContractBill().getId().toString());
		}
	
		if (qualityGuarante.compareTo(FDCHelper
				.subtract(leastSettAmt, totalPay)) > 0) {
				FDCMsgBox.showError("结算单累计保修金不能大于累计结算价-合同实付款【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
				abort();
			
		}

	}
	

	public boolean isModify() {
		if (getOprtState().equals(OprtState.ADDNEW) || getOprtState().equals(OprtState.EDIT)) {
			for (int i = 0; i < this.tblCompensationBill.getRowCount(); i++) {
				IRow row = this.tblCompensationBill.getRow(i);
				CompensationBillInfo info = (CompensationBillInfo) row
						.getUserObject();
				Boolean b = (Boolean) row.getCell("select").getValue();
				if (info == null || b == null)
					return false;
				if (b.booleanValue() != info.isIsCompensated()) {
					return true;
				}
			}

			for (int i = 0; i < this.tblGuerdon.getRowCount(); i++) {
				IRow row = this.tblGuerdon.getRow(i);
				GuerdonBillInfo info = (GuerdonBillInfo) row.getUserObject();
				Boolean b = (Boolean) row.getCell("select").getValue();
				if (info == null || b == null)
					return false;
				if (b.booleanValue() != info.isIsGuerdoned()) {
					return true;
				}
			}

			for (int i = 0; i < this.tblDeduct.getRowCount(); i++) {
				IRow row = this.tblDeduct.getRow(i);
				DeductBillEntryInfo info = (DeductBillEntryInfo) row
						.getUserObject();
				Boolean b = (Boolean) row.getCell("select").getValue();
				if (info == null || b == null)
					return false;
				if (b.booleanValue() != info.isHasApplied()) {
					return true;
				}
			}
		}

		return super.isModify();
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSplit_actionPerformed(e);

		String id = getSelectBOID();

		if (id == null)
			return;

		AbstractSplitInvokeStrategy invokeStra = new ConSettleSplitInvokeStrategy(
				id, this);
		invokeStra.invokeSplit();
	}

	public void initUIToolBarLayout() {
		super.initUIToolBarLayout();

	}

	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		// btnSplit.setIcon(FDCConstants.ICON_SPLIT);
		// 拆分按钮在非实体成本中心下不可用
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		if (orgUnit.isIsCostOrgUnit() && orgUnit.isIsLeaf()) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			
		} else {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
			
		}
		this.btnViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// saveCompensationRel();
		/*
		 * if(this.editData.getContractBill().getId()!=null){ Map paramMap=new
		 * HashMap(); paramMap.put("contractBillId",
		 * this.editData.getContractBill().getId().toString());
		 * paramMap.put("billState", this.editData.getState());
		 * paramMap.put("txtSettlePrice", txtSettlePrice.getBigDecimalValue());
		 * ContractClientUtils.checkTotalSettlePriceSmallerThanZero(paramMap); }
		 */
		checkAmt();
		settlementDetailHelper.save();
		runAction(e,"SAVE");
		super.actionSave_actionPerformed(e);
	}

	/**
	 * by Cassiel_peng
	 */
	boolean isWarn = false;

	protected void txtOriginalAmount_focusGained(FocusEvent e) throws Exception {
		super.txtOriginalAmount_focusGained(e);
		if (STATUS_EDIT.equals(getOprtState()) && !isWarn) {
			boolean isCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_CON_SettNoCostSplit",
					"FSettlementBillID");
			boolean isNoCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_CON_SettlementCostSplit",
					"FSettlementBillID");
			if (isCostSplit || isNoCostSplit) {
				MsgBox
						.showWarning("此结算已经拆分，修改金额会导致结算金额与拆分不一致，修改金额后，将清除拆分及相关的付款拆分！");
				isWarn = true;
			}
		}
	}

	/**
	 * 如单据已经拆分,在修改单据金额的时候给予提示重新拆分
	 */
	boolean amtWarned = false;
	String settlePrice = null;

	protected void txtSettlePrice_focusLost(FocusEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.txtSettlePrice_focusLost(e);

		if (settlePrice == null || txtSettlePrice.getText() == null) {
			return;
		}

		BigDecimal a = txtSettlePrice.getBigDecimalValue();

		if ((settlePrice == null && txtSettlePrice.getText() != null)
				|| (settlePrice != null && txtSettlePrice.getText() == null)
				|| new BigDecimal(settlePrice).compareTo(a) != 0) {
			if (a != null) {
				settlePrice = a.toString();
			}

			if (STATUS_EDIT.equals(getOprtState()) && !amtWarned) {
				FDCSplitClientHelper.checkSplitedForAmtChange(this,
						getSelectBOID(), "T_CON_SettlementCostSplit",
						"FSettlementBillID");
				amtWarned = true;
			}
		}
	}

	// 结算造价发生变化
	protected void txtSettlePrice_dataChanged(DataChangeEvent e)
			throws Exception {
		super.txtSettlePrice_dataChanged(e);

		calUnitPrice();
	}

	// 建筑面积变化
	protected void txtbuildArea_dataChanged(DataChangeEvent e) throws Exception {
		super.txtbuildArea_dataChanged(e);

		calUnitPrice();
	}

	// 计算单位造价
	private void calUnitPrice() {
		BigDecimal settPrice = txtSettlePrice.getBigDecimalValue();
		BigDecimal buildArea = txtbuildArea.getBigDecimalValue();
		if (settPrice != null && buildArea != null
				&& settPrice.floatValue() != 0 && buildArea.floatValue() != 0) {
			BigDecimal unitPrice = settPrice.divide(buildArea,
					BigDecimal.ROUND_HALF_UP);
			txtunitPrice.setValue(unitPrice);
		}
	}

	/**
	 * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
	 */
	protected void setAutoNumberByOrg(String orgType) {

	}

	private void setQualityGuarante(String flag, DataChangeEvent e) {
		// 结算单的质保金 <= 结算价 – 合同下已申请付款金额之和
		BigDecimal settleAmtAll = FDCHelper.toBigDecimal(txtTotalSettlePrice
				.getBigDecimalValue());
		// settleAmtSingle:单笔结算单造价 by Cassiel_peng
		BigDecimal settleAmtSingle = FDCHelper.toBigDecimal(txtSettlePrice
				.getBigDecimalValue());
		// if (isFirstLoad)
		// return;
		if (settleAmtAll.compareTo(FDCHelper.ZERO) == 0
				|| settleAmtSingle.compareTo(FDCHelper.ZERO) == 0) {
			txtqualityGuarante.setValue(FDCHelper.ZERO);
			txtGuaranceAmt.setValue(FDCHelper.ZERO);
			// txtqualityGuaranteRate.setValue(FDCHelper.ZERO);
			return;
		}
		if (flag.equals("txtqualityGuarante")) {// 如果修改了累计保修金
												// 其实这种场景已经不存在(控制了"累计保修金"不能编辑)
												// by Cassiel_peng
			BigDecimal qualityGuarante = FDCHelper
					.toBigDecimal(txtqualityGuarante.getBigDecimalValue());
			//			if (qualityGuarante.compareTo(FDCHelper.ZERO) == 0) {
			//				// return;
			//			}
			DataChangeListener[] listeners = (DataChangeListener[]) txtqualityGuaranteRate
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuaranteRate.removeDataChangeListener(listeners[i]);
			}
			// BigDecimal qualityGuaranteRate =
			// qualityGuarante.multiply(FDCHelper
			// .ONE_HUNDRED).divide(settleAmtAll, 6, BigDecimal.ROUND_HALF_UP);
//			BigDecimal qualityGuaranteRate = FDCHelper.divide(FDCHelper
//					.multiply(qualityGuarante, FDCHelper.ONE_HUNDRED),
//					settleAmtAll, 6, BigDecimal.ROUND_HALF_UP);
			
			BigDecimal qualityGuaranteRate = FDCHelper.divide(FDCHelper.multiply(qualityGuarante, FDCHelper.ONE_HUNDRED),settleAmtAll, 8, BigDecimal.ROUND_HALF_UP);
			txtqualityGuaranteRate.setValue(qualityGuaranteRate);
			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuaranteRate.addDataChangeListener(listeners[i]);
			}
		}
		/**
		 * 修改了保修金比例 by Cassiel_peng
		 */
		else if (flag.equals("txtqualityGuaranteRate")) {

			DataChangeListener[] listeners = (DataChangeListener[]) txtqualityGuarante
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuarante.removeDataChangeListener(listeners[i]);
			}

			BigDecimal qualityGuaranteRate = FDCHelper
					.toBigDecimal(txtqualityGuaranteRate.getBigDecimalValue());
			// 累计保修金按公式(保修金比例 * 累计结算本位币 / 100)更新；
			// BigDecimal qualityGuaranteAll =
			// qualityGuaranteRate.multiply(settleAmtAll
			// ).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			//			BigDecimal qualityGuaranteAll = FDCHelper.divide(FDCHelper.multiply(qualityGuaranteRate, settleAmtAll),	FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			//			txtqualityGuarante.setValue(qualityGuaranteAll);
			// 保修金也要按公式(保修金比例 * 结算造价本位币 / 100)更新
			// BigDecimal
			// qualityGuaranteSingle=qualityGuaranteRate.multiply(settleAmtSingle
			// ).divide(FDCHelper.ONE_HUNDRED, 2,BigDecimal.ROUND_HALF_UP);
			BigDecimal qualityGuaranteSingle = FDCHelper.divide(FDCHelper
					.multiply(qualityGuaranteRate, settleAmtSingle),
					FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			txtGuaranceAmt.setValue(qualityGuaranteSingle);
			txtqualityGuarante.setValue(FDCHelper.add(getLstQualityGuarante(), txtGuaranceAmt.getBigDecimalValue()), false);
			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuarante.addDataChangeListener(listeners[i]);
			}

		}
		/**
		 * 修改了保修金 by Cassiel_peng
		 */
		else if (flag.equals("txtGuaranceAmt")) {
			BigDecimal qualityGuaranteSingle = FDCHelper.toBigDecimal(txtGuaranceAmt.getBigDecimalValue());
			//			if (qualityGuaranteSingle.compareTo(FDCHelper.ZERO) == 0) {
			//				// return;
			//			}
			DataChangeListener[] listeners = (DataChangeListener[]) txtqualityGuaranteRate
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuaranteRate.removeDataChangeListener(listeners[i]);
			}
			// 保修金比例自动按公式(保修金 * 100 / 结算造价本位币)更新
			// BigDecimal qualityGuaranteRate =
			// qualityGuaranteSingle.multiply(FDCHelper
			// .ONE_HUNDRED).divide(settleAmtSingle, 6,
			// BigDecimal.ROUND_HALF_UP);
			BigDecimal qualityGuaranteRate = FDCHelper.divide(FDCHelper.multiply(qualityGuaranteSingle, FDCHelper.ONE_HUNDRED),	settleAmtSingle, 8, BigDecimal.ROUND_HALF_UP);
			txtqualityGuaranteRate.setValue(qualityGuaranteRate);
			// 累计保修金要按公式(保修金比例 * 累计结算本位币 / 100)更新
			// BigDecimal
			// qualityGuaranteAll=qualityGuaranteRate.multiply(settleAmtAll
			// ).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			//			BigDecimal qualityGuaranteAll = FDCHelper.divide(FDCHelper.multiply(qualityGuaranteRate, settleAmtAll),	FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			txtqualityGuarante.setValue(FDCHelper.add(getLstQualityGuarante(), txtGuaranceAmt.getBigDecimalValue()), false);

			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuaranteRate.addDataChangeListener(listeners[i]);
			}
		}
	}
	
	/**
	 * 描述：获取历史保修金金额
	 * @Author：keyan_zhao
	 * @CreateTime：2013-6-21
	 */
	private BigDecimal getLstQualityGuarante() {
		Timestamp createTime = null;
		if (editData.getCreateTime() == null) {
			createTime = new Timestamp(new Date().getTime());

		} else {
			createTime = editData.getCreateTime();
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();

		builder.appendSql("select sum(FGuaranteAmt) as FGuaranteAmt from T_CON_ContractSettlementBill where FContractBillID='"
				+ this.editData.getContractBill().getId().toString() + "' and fstate='" + FDCBillStateEnum.AUDITTED_VALUE
				+ "' and fcreatetime < { ts'" + createTime.toLocaleString() + "'}");

		IRowSet rowSet = null;
		try {
			rowSet = builder.executeQuery();
			if (rowSet.next()) {
				return rowSet.getBigDecimal("FGuaranteAmt");
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		return FDCHelper.ZERO;
	}

	/**
	 * @deprecated
	 */
	private void checkQualityGuarante() {
		BigDecimal settleAmt = FDCHelper.toBigDecimal(txtSettlePrice
				.getBigDecimalValue());
		BigDecimal settleTotalAmt = FDCHelper.toBigDecimal(txtTotalSettlePrice
				.getBigDecimalValue());
		BigDecimal qualityGuarante = FDCHelper.toBigDecimal(txtqualityGuarante
				.getBigDecimalValue());// 累计保修金
		BigDecimal qualityGuaranteRate = FDCHelper
				.toBigDecimal(txtqualityGuaranteRate.getBigDecimalValue());

		if (settleAmt.compareTo(FDCHelper.ZERO) == 0) {
			MsgBox.showError(this, "结算造价不能为０");
			SysUtil.abort();
		}

		if (qualityGuaranteRate.compareTo(FDCHelper.ONE_HUNDRED) > 0
				&& qualityGuaranteRate.compareTo(FDCHelper.ZERO) < 0) {
			MsgBox.showError(this, "保修金比例必须大于等于0，小于等于100");
			SysUtil.abort();
		}

		if (isQualityGuarante) {
			// BigDecimal temp =
			// qualityGuarante.multiply(FDCHelper.ONE_HUNDRED).
			// divide(settleTotalAmt,6, BigDecimal.ROUND_HALF_UP);
			BigDecimal temp = FDCHelper.divide(FDCHelper.multiply(
					qualityGuarante, FDCHelper.ONE_HUNDRED), settleTotalAmt, 6,
					BigDecimal.ROUND_HALF_UP);
			if (temp.compareTo(qualityGuaranteRate) != 0) {
				MsgBox.showError(this, "累计保修金＝累计结算造价*保修金比例 关系不成立");
				SysUtil.abort();
			}
		} else {
			BigDecimal temp = settleTotalAmt.multiply(qualityGuaranteRate)
					.divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			if (temp.compareTo(qualityGuarante) != 0) {
				MsgBox.showError(this, "累计保修金＝累计结算造价*保修金比例 关系不成立");
				SysUtil.abort();
			}
		}
	}

	protected boolean checkCanSubmit() throws Exception {
		if (isIncorporation && ((FDCBillInfo) editData).getPeriod() == null) {
			MsgBox.showWarning(this, "启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		// checkQualityGuarante(); 不做强制控制了
		checkAmt();
	}
	
	private void checkSettlerEqualsLasterPrice(boolean isFinalSettle, BOSUuid billID, BigDecimal totalSettlementOrgiAmt) throws Exception {
		BigDecimal contractOrgiAmt = FDCHelper.ZERO;

		ContractBillInfo contract = null;
		contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(billID));
		if (contract != null) {
			contractOrgiAmt = (BigDecimal) FDCUtils.getLastOriginalAmt_Batch(null, new String[] { contract.getId().toString() }).get(
					contract.getId().toString());

		}
		HashMap paramItem = null;
		FDCParamInfo paramInfo = null;
		paramItem = ParamControlFactory.getRemoteInstance().getParamHashMap(
				SysContext.getSysContext().getCurrentCostUnit().getId().toString(), "com.kingdee.eas.fdc.contract.contract");
		paramInfo = new FDCParamInfo(paramItem);

		String conSettType = "rdContractOverRate";
		String costCenterConstRate = "0.00";
		if (paramInfo != null) {
			conSettType = paramInfo.getBase_ConSettType();
			costCenterConstRate = paramInfo.getBase_CostCenterConstRate();
		}
		double newRate = 0.0;
		if (contractOrgiAmt != null && contractOrgiAmt.compareTo(FDCHelper.ZERO) != 0) {
			if (conSettType.equalsIgnoreCase("rdContractOverRate")) {
				newRate = contract.getOverRate();
			} else {
				if (costCenterConstRate != null && !costCenterConstRate.trim().equals("")) {
					newRate = new Double(costCenterConstRate).doubleValue();
				}
			}
			contractOrgiAmt = FDCHelper.toBigDecimal(FDCHelper.multiply(contractOrgiAmt, FDCHelper.toBigDecimal(new Double(
					1 + newRate / 100.00))), 2);
		}
		if (isFinalSettle) {
			if (FDCHelper.compareTo(totalSettlementOrgiAmt, contractOrgiAmt) > 0) {
				FDCMsgBox.showWarning("累计结算金额大于合同最新造价，不能审批");
				abort();
			}
		}
	}

	protected void checkAmt() throws BOSException, EASBizException {
		storeFields();
		// 只有最终结算才判断实际付款数
//		if (!editData.getBoolean("isFinalSettle")) {
		//最终结算单提交、审批时校验:累计保修金是否小于等于累计结算价-合同实付款 上述判断结算单是否为最终结算单不合适 
		//见 createNewData()中objectValue.setIsFinalSettle(BooleanEnum.TRUE);代码句
		if(BooleanEnum.FALSE.equals(this.cbFinalSettle.getSelectedItem())){
			return;
		}
		ContractBillInfo contractBill=this.editData.getContractBill();
		boolean checkSettlePriceBiggerThanPayAmt = ContractClientUtils.checkSettlePriceBiggerThanPayAmt(this.editData,contractBill==null?"":contractBill.getId().toString());
		if (!checkSettlePriceBiggerThanPayAmt) {
			MsgBox.showError(this, "合同结算价不能小于合同实际付款数之和！");
			SysUtil.abort();
		}
		
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {

		if (this.editData.getContractBill().getId() != null) {
			Map paramMap = new HashMap();
			paramMap.put("contractBillId", this.editData.getContractBill()
					.getId().toString());
			paramMap.put("updateAfterSavedToSubmitBillId", this.editData
					.getId());
			paramMap.put("billState", this.editData.getState());
			paramMap.put("txtSettlePrice", txtOriginalAmount
					.getBigDecimalValue());
			ContractClientUtils.checkTotalSettlePriceSmallerThanZero(paramMap);
		}
		
		checkTotalSettlePrice();
		/**
		 * 如果是最终结算，首先要检查结算金额是否小于已付款金额（所有为非保存状态的会款单） 再检查，结算金额（大于等于保修金+已付款金额）
		 * 如果是非最终结算，则只要检查结算金额是否大于等于保修金额+已付款金额 2009-11-12 兰远军 *
		 */
		if (isOverContractAmt) {
			if (ContractChangeBillFactory.getRemoteInstance().exists("where contractBill.id='" + editData.getContractBill().getId() + "'")) {
				checkSettlerEqualsLasterPrice(editData.getBoolean("isFinalSettle"), editData.getContractBill().getId(), editData
						.getTotalOriginalAmount());
			}
		}
		if (isMoreSettlement) {
			if (((com.kingdee.eas.base.commonquery.BooleanEnum)this.cbFinalSettle.getSelectedItem()).getValue()==BooleanEnum.TRUE_VALUE) {
				checkAmt();
				chkSettlementAmt();

			} else {
				chkSettlementAmt();
			}
		} else {
			checkAmt();
			chkSettlementAmt();
		}

		super.actionAudit_actionPerformed(e);
		// 审批后屏蔽审批按钮 显示反审批按钮
		actionAudit.setEnabled(false);
		actionAudit.setVisible(false);
		actionUnAudit.setVisible(true);
		actionUnAudit.setEnabled(true);

		if (this.editData.getId() != null) {
			AbstractSplitInvokeStrategy strategy = SplitInvokeStrategyFactory
					.createSplitStrategyByParam(this.editData.getId()
							.toString(), this);
			strategy.invokeSplit();
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {

		super.actionUnAudit_actionPerformed(e);
		// 反审批后屏蔽反审批按钮 显示审批按钮
		actionAudit.setEnabled(true);
		actionAudit.setVisible(true);
		actionUnAudit.setVisible(false);
		actionUnAudit.setEnabled(false);
	}

	protected boolean isUseMainMenuAsTitle() {
		return false;
	}

	/**
	 * 
	 * 描述：检查是否有关联对象
	 * 
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	protected void checkRef(String id) throws Exception {
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(
				new FilterItemInfo("settlementBill.id", id));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = false;
		if (SettlementCostSplitFactory.getRemoteInstance().exists(filterSett)
				|| SettNoCostSplitFactory.getRemoteInstance()
						.exists(filterSett)) {
			hasSettleSplit = true;
		}
		if (hasSettleSplit) {
			MsgBox.showWarning("结算单已经拆分,不能反审批!");
			SysUtil.abort();
			return;
		}
	}

    public void fillAttachmentList(){
    	this.cmbAttchment.removeAllItems();
		String boId = null;
		if(this.editData.getId() == null){
			return;
		}else{
			boId = this.editData.getId().toString();
		}
		try {
			this.cmbAttchment.addItems(AttachmentUtils.getAttachmentListByBillID(boId).toArray());
			this.viewAttachment.setEnabled(this.cmbAttchment.getItemCount() > 0);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
    }
    
    
    
    protected void lockContainer(Container container) {
    	 if(lblAttachmentContainer.getName().equals(container.getName()) || conAttenTwo.getName().equals(container.getName())){
         	return;
         }
         else{
          	super.lockContainer(container);
         }
    }

	public void actionViewAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionViewAttachment_actionPerformed(e);
		String attachId = null;
		if (this.cmbAttchment.getSelectedItem() != null
				&& this.cmbAttchment.getSelectedItem() instanceof AttachmentInfo) {
			attachId = ((AttachmentInfo) this.cmbAttchment.getSelectedItem())
					.getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory
					.getClientManager();
			acm.viewAttachment(attachId);
		}
	}

	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionAttachment_actionPerformed(e);
		fillAttachmentList();
	}


	/**
	 * 非最终结算的结算单在提交、审批时，若启用了参数“未结算合同的实付款大于已实现产值时是否严格控制”
	 * 且合同实付款大于累计结算金额，则提示“未结算合同的实付款不能大于累计结算金额【合同实付款 =已关 闭的付款申请单对应的付款单合同内工程款合计 +
	 * 未关闭的付款申请单合同内工程款合计】”，则不能提交 或审批通过，若未启用，则在提示之后允许提交、审批。 by jian_wen
	 * 2009.12.24
	 */
	public void checkTotalSettlePrice() throws EASBizException, BOSException {
		// 非最终结算才 进入
		if (BooleanEnum.FALSE.equals(this.cbFinalSettle.getSelectedItem())) {
			boolean b = isOverContractAmt;
			// 取合同实付款
			BigDecimal payAmt = ContractClientUtils.getPayAmt(contractBill.getId().toString());
			if (payAmt == null) {
				payAmt = FDCHelper.ZERO;
			}
			if (payAmt.compareTo(txtTotalSettlePrice.getBigDecimalValue()) == 1) {
				if (b) {
					MsgBox.showError("未结算合同的实付款不能大于累计结算金额【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
					abort();
				} else {
					MsgBox.showWarning("未结算合同的实付款不能大于累计结算金额【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
				}
			}
		}
	}

	/**
	 * 需求来源：R100806-236合同录入、变更、结算单据界面增加审批按钮
	 * <P>
	 * 为实现此BT需求，特重写以下方法
	 * 
	 * @author owen_wen 2011-03-08
	 */
	protected void setAuditButtonStatus(String oprtType) {
		if (STATUS_VIEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
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
	}

	/**
	 * 需求来源：R100806-236合同录入、变更、结算单据界面增加审批按钮
	 * <P>
	 * 为实现此BT需求，特重写以下方法
	 * 
	 * @author owen_wen 2011-03-08
	 */
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {

		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);

		if (isSameUserForUnAudit && editData.getAuditor() != null) {

			if (!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())) {
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		// 检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		if (editData == null || editData.getState() == null || !editData.getState().equals(state)) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
//	private boolean  isAttenTwo = false;
	public void actionAttenTwo_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttenTwo_actionPerformed(e);
		   AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
	        String boID = editData.getOwnID();
	        if(boID == null)
	        {
	            return;
	        } 
	        acm.showAttachmentListUIByBoID(boID,this,true); // boID 是 与附件关联的 业务对象的 ID
	        
	        fillCmBoxTwo();
	}
	private void fillCmBoxTwo() {
		this.cmbAttenTwo.removeAllItems();
		String boId = null;
		if(this.editData.getOwnID() == null || editData.getOwnID().length() == 0){
			return;
		}else{
			boId = this.editData.getOwnID();
		}
		
		if(boId != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID",boId));
			
			EntityViewInfo evi = new EntityViewInfo();
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			 try {
				cols =BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
			if(cols != null && cols.size()>0){
				for(Iterator it = cols.iterator();it.hasNext();){
					AttachmentInfo attachment = ((BoAttchAssoInfo)it.next()).getAttachment();
					this.cmbAttenTwo.addItem(attachment);
				}
				this.isHasAttenTwo = true;
			}else{
				this.isHasAttenTwo =false;
			}
		}
//		this.btnv.setEnabled(this.isHasAttachment);
		this.viewAttenTwo.setEnabled(isHasAttenTwo);
	}
	public void actionViewTwo_actionPerformed(ActionEvent e) throws Exception {
		super.actionViewTwo_actionPerformed(e);
		String attachId = null;
    	if(this.cmbAttenTwo.getSelectedItem() != null && this.cmbAttenTwo.getSelectedItem() instanceof AttachmentInfo){
    		attachId = ((AttachmentInfo)this.cmbAttenTwo.getSelectedItem()).getId().toString();
    		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    		acm.viewAttachment(attachId);
    	}
	}
	
	/**增加需求:结算时增加控制，结算额不能小于工程量确认金额**/
	//by tim_gao
	
	
	protected com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill getWorkLoadBizInterface()
	throws Exception {
		return com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory
		.getRemoteInstance();
	}
	
	protected boolean checkWorkloadConConfirm() throws Exception{
		//by tim_gao
	
		String companyId = company.getId().toString();
		//HashMap paramMap = FDCUtils.getDefaultFDCParam(null, companyId);
		boolean isPass = true;
		Object newValue = cbFinalSettle.getSelectedItem();
		//是否启用参数了并且最终结算单时
		if(FDCUtils.getBooleanValue4FDCParamByKey(null, companyId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)&&newValue!=null
			&&BooleanEnum.TRUE.equals(newValue)){
			
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("contractBill.id");
			sels.add("workLoad");
			sels.add("state");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractBill.getId().toString()));
			
			view.setFilter(filter);
			view.setSelector(sels);
			WorkLoadConfirmBillCollection workLoadCols = null;
			//取出合同下所有的工程量确认单
			workLoadCols = getWorkLoadBizInterface().getWorkLoadConfirmBillCollection(view);
			
			//先拿工程量确认单的状态比较判断
			//有无未审批（保存、提交、审批中）完成的工程量确认单，有则不允许提交（工程量确认单审批后方可提交）
		
			BigDecimal amount = FDCHelper.ZERO;
			for(Iterator it = workLoadCols.iterator();it.hasNext();){
				WorkLoadConfirmBillInfo workload = (WorkLoadConfirmBillInfo) it.next();
			
				if(FDCBillStateEnum.AUDITTING.equals(workload.getState())||
						FDCBillStateEnum.SAVED.equals(workload.getState())||
						FDCBillStateEnum.SUBMITTED.equals(workload.getState())){
					isPass = false;
				}else if (FDCBillStateEnum.AUDITTED.equals(workload.getState())){
					amount = FDCHelper.add(amount, workload.getWorkLoad());
				}
			}
			
			if(isPass==false){
				FDCMsgBox.showWarning("本合同下存在未审批通过的工程量确认单，请先审批工程量确认单！");
				return isPass;
			}
			//需判断最终累计结算金额与累计工程量确认金额的大小，
			//如果最终累计结算金额大于等于累计工程量确认金额，则不提示
			//否则提示是否提交(工程量确认单上是本币，要换算)
			
			//备注：如果不是多次结算，已经与合同造价比较不做判断
			
//			ContractSettlementBillCollection iconSett = null;
//			((IContractSettlementBill)this.getBizInterface()).getContractSettlementBillCollection(view);
//			BigDecimal contractSettleAmount = FDCHelper.ZERO;
//			if(this.txtSettlePrice.getValue()!=null||!(("").equals(this.txtSettlePrice.getValue()))){
//				contractSettleAmount =	new BigDecimal(this.txtSettlePrice.getValue().toString());
//			}else{
//				FDCMsgBox.showWarning("结算金额不能为空");
//			}
//			for(Iterator its = iconSett.iterator();its.hasNext();){
//				ContractSettlementBillInfo settle = (ContractSettlementBillInfo) its.next();
//				if(settle.getTotalSettlePrice()!=null){
//					
//				}
//				
//			}
			
			BigDecimal settlePrice = FDCHelper.toBigDecimal(txtSettlePrice.getValue());
			//if(FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_MORESETTER)){
			if(isMoreSettlement){
			view = new EntityViewInfo();
			filter = new FilterInfo();
			sels = new SelectorItemCollection();
//			sels.add("contractBill.id");
			sels.add("amount");
			sels.add("state");
			sels.add("settlePrice");
			sels.add("curOriginalAmount");
			sels.add("curSettlePrice");
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractBill.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			view.setSelector(sels);
			view.setFilter(filter);
				ContractSettlementBillCollection conSels = 
					((IContractSettlementBill)this.getBizInterface()).getContractSettlementBillCollection(view);
				for(Iterator itsel = conSels.iterator();itsel.hasNext();){
					ContractSettlementBillInfo comSettle = (ContractSettlementBillInfo)(itsel.next());
					settlePrice = FDCHelper.add(settlePrice, comSettle.getCurSettlePrice());
				}
			}
			if(settlePrice.compareTo(amount)<0){
				int isGoOn = FDCMsgBox.showConfirm2("累计结算金额["+settlePrice+
						"]小于累计工程量确认金额["+amount+"]，是否确认提交?");
				if(isGoOn==0){
					isPass = true;
				}else{
					isPass = false;
				}
			}

		}
		
		return isPass;
	}
	
    private void runAction(ActionEvent e,String actionName) throws Exception{
    	Iterator iterator = uiSet.iterator();
        while(iterator.hasNext()){
        	SettlementCostSplitEditUI ui = (SettlementCostSplitEditUI)iterator.next();
        	ui.editData.setSourceBillId(editData.getId().toString());
    		ui.actionSave_actionPerformed(e);
        }
    }
	
	private void loadSettlementSplitUI() throws Throwable{
		Iterator iterator = uiSet.iterator();
		while(iterator.hasNext())
			((SettlementCostSplitEditUI)iterator.next()).destroyWindow();
		uiSet.clear();
		openSplitUI();
	}
	
	private void openSplitUI() throws Throwable {
		String billId = editData.getId()!=null?editData.getId().toString():null;
		if(billId==null)
			return;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filInfo = new FilterInfo(); 
		filInfo.getFilterItems().add(new FilterItemInfo("sourceBillId",billId));
		view.setFilter(filInfo);
		view.getSelector().add("id");
		SettlementCostSplitCollection splitColl = SettlementCostSplitFactory.getRemoteInstance().getSettlementCostSplitCollection(view);
		
	    IMutexServiceControl mutexServiceControl = FrameWorkClientUtils.createMutexServiceControl();
		SettlementCostSplitEditUI ui = null;
		if(splitColl.size()>0){
			UIContext uiContext = new UIContext(this);
	        uiContext.put(UIContext.ID, splitColl.get(0).getId());
	        uiContext.put("AUDITBILLVIEW", "AUDITBILLVIEW");
	        
	        ui = (SettlementCostSplitEditUI) UIFactoryHelper.initUIObject(SettlementCostSplitEditUI.class.getName(), uiContext, null,OprtState.EDIT);
	        mutexServiceControl.releaseObjIDForUpdate(splitColl.get(0).getId().toString());
		}else{
			UIContext uiContext = new UIContext(this);
			uiContext.put("costBillID",billId);
			uiContext.put("AUDITBILLVIEW", "AUDITBILLVIEW");
			ui = (SettlementCostSplitEditUI) UIFactoryHelper.initUIObject(SettlementCostSplitEditUI.class.getName(), uiContext, null,OprtState.ADDNEW);
		}
		kDContainer1.getContentPane().add(ui,BorderLayout.CENTER);
		uiSet.add(ui);
	}
	
	/**
	 * 查看正文...ken_liu
	 */
	public void actionViewContent_actionPerformed(ActionEvent e) throws Exception {
		//查看合同正文要传个info进去。。。要么rpc。要么加几个引用变量。。要么如下反查UI：
		if(this.panelCollection.getComponent(0) instanceof ContractFullInfoUI) {
			ContractFullInfoUI conFullInfoUI = (ContractFullInfoUI) panelCollection.getComponent(0);
			
			String conTitle = EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractFullResource", "billInfo");
			int index = conFullInfoUI.plContrastFullInfo.indexOfTab(conTitle);
			if(index>=0) {
				KDScrollPane scrollPane = (KDScrollPane) conFullInfoUI.plContrastFullInfo.getComponentAt(index);
				if( scrollPane.getViewport().getView() instanceof ContractBillEditUI ) {
					ContractBillEditUI conEditUI = (ContractBillEditUI) scrollPane.getViewport().getView();
					ForWriteMarkHelper.isUseWriteMarkForEditUI(conEditUI.editData, OprtState.VIEW, this);
				}
			}
		}
	}
}