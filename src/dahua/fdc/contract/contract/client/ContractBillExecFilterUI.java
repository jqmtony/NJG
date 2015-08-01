/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.SpinnerNumberModel;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * 合同执行情况表——自定义过滤条件
 */
public class ContractBillExecFilterUI extends AbstractContractBillExecFilterUI {
	private static final String IS_ARCHIVED = "isArchived";

	private static final String CURRENCY_ID = "currencyId";

	private static final String MONTH_TO = "monthTo";

	private static final String MONTH_FROM = "monthFrom";

	private static final String YEAR_TO = "yearTo";

	private static final String YEAR_FROM = "yearFrom";

	/**
	 * 付款时间（起始时间），即序时薄界面中的“实际付款日期”，不会用作合同和无文本中的合同过滤条件
	 */
	private static final String DATE_FROM = "dateFrom";

	/**
	 * 付款时间（结束时间），即序时薄界面中的“实际付款日期”，不会用作合同和无文本中的合同过滤条件
	 */
	private static final String DATE_TO = "dateTo";

	private static final String IS_SHOW_ALL = "isShowAll";

	private static final String SETTLE_STATE = "settleState";

	private static final String DATE_TYPE = "dateType";

	private static final String PROVIDER_ID = "providerId";

	private static final String CONTRACT_TYPE_IDS = "contractTypeIds";

	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	protected ItemAction actionListOnLoad;

	private TreeSelectDialog contractTypeSelectDlg;

	private boolean isLoaded;

	protected ListUI listUI;

	/**
	 * 是否使用成本月结,当前财务组织的期间
	 */
	private Date[] pkdate;

	/**
	 * output class constructor
	 */
	public ContractBillExecFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;

		pkdate = FDCClientHelper.getCompanyCurrentDate();
	}

	protected void btnContractTypeSelectProjectSelect_actionPerformed(ActionEvent e) throws Exception {
		if (this.contractTypeSelectDlg == null) {
			this.initContractTypeDlg(null);
		}
		Object object = contractTypeSelectDlg.showDialog();
		setContractTypeByTree(object);
		super.btnContractTypeSelectProjectSelect_actionPerformed(e);
	}

	public void clear() {
		this.contractTypeSelectDlg = null;
		this.txtContractType.setText(null);
		this.txtContractType.setUserObject(null);

		this.f7Provider.setData(null);
		this.pkDateFrom.setValue(pkdate[0]);
		this.pkDateTo.setValue(pkdate[1]);
		this.radioByDay.setSelected(true);
		this.radioSettleAll.setSelected(true);
		this.chkAllContract.setSelected(false);
		this.f7Provider.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
		initControlByDateType();
	}

	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());

		FilterInfo filter = new FilterInfo();

		// TODO 取树结点
		if (para.isNotNull(CONTRACT_TYPE_IDS)) {
			filter.getFilterItems().add(
					new FilterItemInfo("contractType.id", FDCHelper.getSetByArray(para.getStringArray(CONTRACT_TYPE_IDS)), CompareType.INCLUDE));
		} else {// 如果没有选择合同类型，则要过滤掉禁用的合同类型的合同
			filter.getFilterItems().add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		}
		if (para.isNotNull(PROVIDER_ID)) {
			filter.getFilterItems().add(new FilterItemInfo("partB.id", para.getString(PROVIDER_ID)));
		}
		if (para.isNotNull(SETTLE_STATE)) {
			Boolean settle = null;
			if (para.getInt(SETTLE_STATE) == 0) {
				settle = Boolean.TRUE;
			} else if (para.getInt(SETTLE_STATE) == 1) {
				settle = Boolean.FALSE;
			}
			if (settle != null) {
				filter.getFilterItems().add(new FilterItemInfo("hasSettled", settle));
			}
		}
		if (para.isNotNull(IS_SHOW_ALL) && !para.getBoolean(IS_SHOW_ALL) && para.isNotNull(DATE_TYPE)) {

			if (getBeginQueryDate(para) != null) {
				filter.getFilterItems().add(new FilterItemInfo("signDate", getBeginQueryDate(para), CompareType.GREATER_EQUALS));
			}
			if (getEndQueryDate(para) != null) {
				filter.getFilterItems().add(new FilterItemInfo("signDate", FDCHelper.getNextDay(getEndQueryDate(para)), CompareType.LESS));
			}
		}

		if (para.isNotNull(CURRENCY_ID)) {
			filter.getFilterItems().add(new FilterItemInfo("currency.id", (String) para.get(CURRENCY_ID)));
			hasCurrency = true;
		} else {
			hasCurrency = false;
		}
		return filter;
	}

	private Date getBeginQueryDate(FDCCustomerParams para) {
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0) {
			date = para.getDate(DATE_FROM);
		} else if (dateType == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, para.getInt(MONTH_FROM) - 1);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, (para.getInt(MONTH_FROM) - 1) * 3);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}

	private Date getEndQueryDate(FDCCustomerParams para) {
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0) {
			date = para.getDate(DATE_TO);
		} else if (dateType == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO));
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO) * 3);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO) + 1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}

	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		String[] contractTypeIds = (String[]) this.txtContractType.getUserObject();
		if (!FDCHelper.isEmpty(contractTypeIds)) {
			param.add(CONTRACT_TYPE_IDS, contractTypeIds);
		}

		SupplierInfo supplierInfo = (SupplierInfo) this.f7Provider.getValue();
		if (supplierInfo != null) {
			param.add(PROVIDER_ID, supplierInfo.getId().toString());
		}
		if (this.radioByDay.isSelected()) {
			param.add(DATE_FROM, (Date) this.pkDateFrom.getValue());
			param.add(DATE_TO, (Date) this.pkDateTo.getValue());
			param.add(DATE_TYPE, 0);
		} else if (this.radioByMonth.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue()).intValue());
			param.add(YEAR_TO, ((Integer) this.spiYearTo.getValue()).intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue()).intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue()).intValue());
			param.add(DATE_TYPE, 1);
		} else if (this.radioByQuarter.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue()).intValue());
			param.add(YEAR_TO, ((Integer) this.spiYearTo.getValue()).intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue()).intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue()).intValue());
			param.add(DATE_TYPE, 2);
		} else if (this.radioByYear.isSelected()) {
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue()).intValue());
			param.add(YEAR_TO, ((Integer) this.spiYearTo.getValue()).intValue());
			param.add(DATE_TYPE, 3);
		}

		if (this.radioHasSettled.isSelected()) {
			param.add(SETTLE_STATE, 0);
		} else if (this.radioNoSettled.isSelected()) {
			param.add(SETTLE_STATE, 1);
		} else if (this.radioSettleAll.isSelected()) {
			param.add(SETTLE_STATE, 2);
		}
		param.add(IS_SHOW_ALL, this.chkAllContract.isSelected());

		if (prmtCurrency.getValue() != null) {
			CurrencyInfo currency = (CurrencyInfo) prmtCurrency.getValue();
			param.add(CURRENCY_ID, currency.getId().toString());
		}

		return param.getCustomerParams();
	}

	private void initControlByDateType() {
		SpinnerNumberModel yearMo1 = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearFrom.setModel(yearMo1);
		SpinnerNumberModel yearMo2 = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearTo.setModel(yearMo2);

		if (this.radioByDay.isSelected()) {
			this.contYearFrom.setVisible(false);
			this.contYearTo.setVisible(false);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.contDateFrom.setVisible(true);
			this.contDateTo.setVisible(true);

		} else if (this.radioByMonth.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(true);
			this.lblMonthTo.setVisible(true);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
			SpinnerNumberModel monthMo1 = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthFrom.setModel(monthMo1);
			SpinnerNumberModel monthMo2 = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthTo.setModel(monthMo2);
		} else if (this.radioByQuarter.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(true);
			this.lblQuarterTo.setVisible(true);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
			int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
			SpinnerNumberModel quarterMo1 = new SpinnerNumberModel(SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
			spiMonthFrom.setModel(quarterMo1);
			SpinnerNumberModel quarterMo2 = new SpinnerNumberModel(SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
			spiMonthTo.setModel(quarterMo2);

		} else if (this.radioByYear.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
		}

		if (this.chkAllContract.isSelected()) {
			this.radioByDay.setEnabled(false);
			this.radioByMonth.setEnabled(false);
			this.radioByQuarter.setEnabled(false);
			this.radioByYear.setEnabled(false);
			this.spiYearFrom.setEnabled(false);
			this.spiYearTo.setEnabled(false);
			this.spiMonthFrom.setEnabled(false);
			this.spiMonthTo.setEnabled(false);
			this.pkDateFrom.setEnabled(false);
			this.pkDateTo.setEnabled(false);
		} else {
			this.radioByDay.setEnabled(true);
			this.radioByMonth.setEnabled(true);
			this.radioByQuarter.setEnabled(true);
			this.radioByYear.setEnabled(true);
			this.plDateType.setEnabled(true);
			this.spiYearFrom.setEnabled(true);
			this.spiYearTo.setEnabled(true);
			this.spiMonthFrom.setEnabled(true);
			this.spiMonthTo.setEnabled(true);
			this.pkDateFrom.setEnabled(true);
			this.pkDateTo.setEnabled(true);
		}
	}

	private void initContractTypeDlg(String[] contractTypeIds) throws Exception, BOSException {
		TreeModel treeModel = FDCClientHelper.createDataTree(ContractTypeFactory.getRemoteInstance(), null);
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) treeModel.getRoot(), "isIsEnabled", Boolean.FALSE);
		contractTypeSelectDlg = new TreeSelectDialog(this, treeModel, "getId,toString", contractTypeIds);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
	}

	protected void radioByDay_actionPerformed(ActionEvent e) throws Exception {
		super.radioByDay_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByMonth_actionPerformed(ActionEvent e) throws Exception {
		super.radioByMonth_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByQuarter_actionPerformed(ActionEvent e) throws Exception {
		super.radioByQuarter_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByYear_actionPerformed(ActionEvent e) throws Exception {
		super.radioByYear_actionPerformed(e);
		this.initControlByDateType();
	}

	private void setContractTypeByTree(Object object) {
		List contractTypeIdList = new ArrayList();
		if (object != null) {
			List contractTypeList = (List) object;
			String text = "";
			for (int i = 0; i < contractTypeList.size(); i++) {
				if (contractTypeList.get(i) instanceof ContractTypeInfo) {
					ContractTypeInfo contractType = (ContractTypeInfo) contractTypeList.get(i);
					contractTypeIdList.add(contractType.getId().toString());
					// /if (contractType != null && contractType.isIsLeaf()) {
					// update by renliang
					if (contractType.isIsLeaf()) {
						if (!text.equals("")) {
							text += ",";
						}
						text += contractType.getName();
					}
				}
			}
			this.txtContractType.setText(text);
			Object[] ids = contractTypeIdList.toArray(new String[] {});
			if (FDCHelper.isEmpty(ids)) {
				this.txtContractType.setUserObject(null);
			} else {
				this.txtContractType.setUserObject(ids);

			}
		}
	}

	public void setCustomerParams(CustomerParams cp) {

		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		try {
			initContractTypeDlg(para.getStringArray(CONTRACT_TYPE_IDS));
			setContractTypeByTree(contractTypeSelectDlg.getUserObject());
		} catch (Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		if (para.get(PROVIDER_ID) != null) {
			SupplierInfo supplier = null;
			try {
				supplier = SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(BOSUuid.read(para.get(PROVIDER_ID))));
			} catch (EASBizException e) {
				e.printStackTrace();
				SysUtil.abort();
			} catch (BOSException e) {
				e.printStackTrace();
				SysUtil.abort();
			}
			this.f7Provider.setValue(supplier);
		} else {
			this.f7Provider.setValue(null);
		}
		if (para.getInt(DATE_TYPE) == 0) {
			this.radioByDay.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 1) {
			this.radioByMonth.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 2) {
			this.radioByQuarter.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 3) {
			this.radioByYear.setSelected(true);
		}

		if (para.getInt(SETTLE_STATE) == 0) {
			this.radioHasSettled.setSelected(true);
		} else if (para.getInt(SETTLE_STATE) == 1) {
			this.radioNoSettled.setSelected(true);
		} else if (para.getInt(SETTLE_STATE) == 2) {
			this.radioSettleAll.setSelected(true);
		}
		this.chkAllContract.setSelected(para.getBoolean(IS_SHOW_ALL));
		this.initControlByDateType();

		if (para.getInt(DATE_TYPE) == 0) {
			this.pkDateFrom.setValue(para.getDate(DATE_FROM));
			this.pkDateTo.setValue(para.getDate(DATE_TO));
		} else if (para.getInt(DATE_TYPE) == 1) {
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 2) {
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 3) {
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
		}

		if (para.get(CURRENCY_ID) != null) {
			String currencyId = (String) para.get(CURRENCY_ID);
			CurrencyInfo currencyInfo = null;
			try {
				currencyInfo = CurrencyFactory.getRemoteInstance().getCurrencyInfo(new ObjectUuidPK(currencyId));
			} catch (Exception e) {
				handUIException(e);
				SysUtil.abort();
			}
			prmtCurrency.setValue(currencyInfo);
		}

		super.setCustomerParams(para.getCustomerParams());
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public boolean verify() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		if (para.isNotNull("endQueryDate") && para.isNotNull("beginQueryDate")) {
			if (para.getDate("endQueryDate").before(para.getDate("beginQueryDate"))) {
				MsgBox.showWarning(this, EASResource.getString(resourcePath, "DateBoundErrer"));
				return false;
			}
		}
		Date start = (Date) pkDateFrom.getValue();
		Date end = (Date) pkDateTo.getValue();
		// 当选择了不按日期，取全部 BT485167
		if (para.isNotNull(IS_SHOW_ALL) && !para.getBoolean(IS_SHOW_ALL)) {
			if (start != null && end != null) {
				if (start.after(end)) {
					MsgBox.showWarning(this, "付款日期的开始日期不能大于结束日期!");
					this.pkDateFrom.requestFocus();
					return false;
				}
			}
		}
		return true;
	}

	protected void chkAllContract_actionPerformed(ActionEvent e) throws Exception {
		super.chkAllContract_actionPerformed(e);
		this.initControlByDateType();
	}

	boolean hasCurrency = false;

	public boolean hasCurrency() {
		return hasCurrency;
	}
}