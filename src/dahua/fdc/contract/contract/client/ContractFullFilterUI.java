/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class ContractFullFilterUI extends AbstractContractFullFilterUI {
    private static final Logger logger = CoreUIObject.getLogger("com.kingdee.eas.fdc.contract.client.ContractFullFilterUI");
	private static final String IS_ARCHIVED = "isArchived";

	private static final String CURRENCY_ID = "currencyId";

	private static final String MONTH_TO = "monthTo";

	private static final String MONTH_FROM = "monthFrom";

	private static final String YEAR_TO = "yearTo";

	private static final String YEAR_FROM = "yearFrom";

	private static final String DATE_TO = "dateTo";

	private static final String DATE_FROM = "dateFrom";

	private static final String IS_SHOW_ALL = "isShowAll";

	private static final String SETTLE_STATE = "settleState";

	private static final String CONTRACT_STATE = "contractState";

	private static final String DATE_TYPE = "dateType";

	private static final String RESPDEPT_ID = "respDeptId";
	
	private static final String PROVIDER_ID = "providerId";

	protected static final String CONTRACT_TYPE_IDS = "contractTypeIds";

	protected static final String PROJECT_IDS = "projectIds";

	protected static final String COMPANY_IDS = "companyIds";

	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	protected ItemAction actionListOnLoad;

	private TreeSelectDialog companySelectDlg;

	private TreeSelectDialog contractTypeSelectDlg;

	protected boolean isLoaded;

	protected ListUI listUI;

	private TreeSelectDialog projectSelectDlg;

	// 是否使用成本月结,当前财务组织的期间
	private Date[] pkdate;

	/**
	 * output class constructor
	 */
	public ContractFullFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;

		pkdate = FDCClientHelper.getCompanyCurrentDate();
	}

	protected void btnCompanySelect_actionPerformed(ActionEvent e) throws Exception {
		if (this.companySelectDlg == null) {
			this.initCompanyDlg(null);
		}
		Object object = companySelectDlg.showDialog();
		setCompanyByTree(object);
		super.btnCompanySelect_actionPerformed(e);
	}

	protected void btnContractTypeSelectProjectSelect_actionPerformed(ActionEvent e) throws Exception {
		if (this.contractTypeSelectDlg == null) {
			this.initContractTypeDlg(null);
		}
		Object object = contractTypeSelectDlg.showDialog();
		setContractTypeByTree(object);
		super.btnContractTypeSelectProjectSelect_actionPerformed(e);
	}

	protected void btnProjectSelect_actionPerformed(ActionEvent e) throws Exception {
		if (this.projectSelectDlg == null) {
			this.initProjectDlg(null);
		}
		Object object = projectSelectDlg.showDialog();
		setProjectByTree(object);
		super.btnProjectSelect_actionPerformed(e);
	}

	public void clear() {
		this.companySelectDlg = null;
		this.txtCompany.setText(null);
		this.txtCompany.setUserObject(null);
		CostCenterOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentCostUnit();
		if (currentCompany.isIsBizUnit()) {
			this.btnCompanySelect.setEnabled(false);
			this.txtCompany.setText(currentCompany.getName());
			this.txtCompany.setUserObject(new String[] { currentCompany.getId().toString() });
		}
		this.projectSelectDlg = null;
		this.txtProject.setText(null);
		this.txtProject.setUserObject(null);
		this.contractTypeSelectDlg = null;
		this.txtContractType.setText(null);
		this.txtContractType.setUserObject(null);

		this.f7Provider.setData(null);
		this.prmtRespDept.setData(null);
		this.pkDateFrom.setValue(null);
		this.pkDateTo.setValue(pkdate[1]);
		this.radioByDay.setSelected(true);
		this.radioStateAll.setSelected(true);
		this.radioSettleAll.setSelected(true);
		this.chkAllContract.setSelected(false);
		// F7SupplierQuery包含分录，会出现重复数据 by hpw 2009-11-19
		this.f7Provider.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierDefaultQuery");
		initControlByDateType();
	}
	
	protected void getCostCenterFilter(FilterInfo filter, FDCCustomerParams para) {
		// 当前组织是实体财务时，过滤条件已经置为当前实体财务了，这里需要判断并取所有下级成本中心
		boolean isCompanyOrg = false;
		if (SysContext.getSysContext().getCurrentFIUnit() != null && SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) {
			isCompanyOrg = true;
		}

		if (!isCompanyOrg && para.isNotNull(COMPANY_IDS)) {
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", FDCHelper.getSetByArray(para.getStringArray(COMPANY_IDS)), CompareType.INCLUDE));
		} else {
			try {
				OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();
				if (orgUnitInfo == null) {
					orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();
				}
				// 获取当前用户有权限的成本中心
				Map costMap = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
						new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection selector = new SelectorItemCollection();
				view.setSelector(selector);
				selector.add("id");
				view.setFilter(new FilterInfo());
				HashSet costIdSet = new HashSet();
				costIdSet.addAll(costMap.keySet());
				view.getFilter().getFilterItems().add(new FilterItemInfo("id", costIdSet, CompareType.INCLUDE));
				view.getFilter().getFilterItems().add(
						new FilterItemInfo("longNumber", orgUnitInfo.getLongNumber() + "!%", CompareType.LIKE));
				// 获取当前用户在当前组织下有权限的成本中心. TODO 需要将当前组织加进来不
				FullOrgUnitCollection orgs = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
				costIdSet.clear();
				for (int i = 0; i < orgs.size(); ++i) {
					costIdSet.add(orgs.get(i).getId().toString());
				}
				if (orgUnitInfo.isIsCostOrgUnit()) {
					costIdSet.add(orgUnitInfo.getId().toString());
				}

				if (costIdSet.size() > 0) {
					filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", costIdSet, CompareType.INCLUDE));
				}
			} catch (Exception e) {
				logger.error(e.getCause(), e);
				SysUtil.abort();
				SysUtil.abort();
			}
		}
	}

	protected void getProjectFilter(FilterInfo filter, FDCCustomerParams para) {
		FilterInfo f = new FilterInfo();
		if (para.isNotNull(PROJECT_IDS)) {
			Set sqlSet = FDCClientUtils.getSQLIdSet(FDCHelper.getSetByArray(para.getStringArray(PROJECT_IDS)));
			f.getFilterItems().add(new FilterItemInfo("curProject.id",FDCHelper.getSetByArray(para.getStringArray(PROJECT_IDS)), CompareType.INCLUDE));
			if (isMultiProject()) {
				String filterSplitSql = "select distinct(fcontractbillid) from T_con_contractCostSplit head " + " inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid "
						+ " inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " + " (" + sqlSet + ") and fstate<>'9INVALID'";
				String filterNoCostSplitSql = "select distinct(fcontractbillid) from T_CON_ConNoCostSplit head " + " inner join T_CON_ConNoCostSplitEntry entry on head.fid=entry.fparentid "
				+ "  where entry.FCURPROJECTID in " + " (" + sqlSet + ") and fstate<>'9INVALID'";
				f .getFilterItems().add(new FilterItemInfo("id", filterSplitSql, CompareType.INNER));
				f .getFilterItems().add(new FilterItemInfo("id", filterNoCostSplitSql, CompareType.INNER));
				f .setMaskString("(#0 or #1 or #2)");
			}
			if(filter != null ){
				try {
					filter.mergeFilter(f, "and");
				} catch (BOSException e) {
					e.printStackTrace();
					logger.error("合并过滤条件出错", e);
					SysUtil.abort();
				}
			}
		} else {// 如果没有选择工程项目，则要过滤掉禁用的工程项目的合同
			filter.getFilterItems().add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		}
	}
	
	private boolean isMultiProject(){
		boolean flag = false;
		try {
			flag = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MULTIPROJECT);
		} catch (EASBizException e) {
			this.handUIException(e);
			SysUtil.abort();
		} catch (BOSException e) {
			this.handUIException(e);
			SysUtil.abort();
		}
		return flag;
	}

	/**
	 * 取得合同类型过滤条件<p>
	 * 修订：禁用的合同类型的合同 也要显示，已经控制了在禁用的合同类型下新增合同，但历史数据还是要显示  by owen_wen 2013-02-25
	 * @Author：owen_wen
	 * @CreateTime：2013-2-25
	 */
	protected void getContractTypeFilter(FilterInfo filter, FDCCustomerParams para) {
		if (para.isNotNull(CONTRACT_TYPE_IDS)) {
			filter.getFilterItems().add(new FilterItemInfo("contractType.id", FDCHelper.getSetByArray(para.getStringArray(CONTRACT_TYPE_IDS)), CompareType.INCLUDE));
		}
	}

	protected void getOtherFilter(FilterInfo filter, FDCCustomerParams para) {
		//责任部门
		if(para.isNotNull(RESPDEPT_ID)){
			filter.getFilterItems().add(new FilterItemInfo("respDept.id",para.getString(RESPDEPT_ID)));
		}
		if (para.isNotNull(PROVIDER_ID)) {
			filter.getFilterItems().add(new FilterItemInfo("partB.id", para.getString(PROVIDER_ID)));
		}
		if (para.isNotNull(CONTRACT_STATE)) {
			String state = null;
			if (para.getInt(CONTRACT_STATE) == 0) {
				state = FDCBillStateEnum.SAVED_VALUE;
			} else if (para.getInt(CONTRACT_STATE) == 1) {
				state = FDCBillStateEnum.SUBMITTED_VALUE;
			} else if (para.getInt(CONTRACT_STATE) == 2) {
				state = FDCBillStateEnum.AUDITTING_VALUE;

			} else if (para.getInt(CONTRACT_STATE) == 3) {
				state = FDCBillStateEnum.AUDITTED_VALUE;
			} else if (para.getInt(CONTRACT_STATE) == 5) {
				state = FDCBillStateEnum.CANCEL_VALUE;
			}

			if (state != null) {
				filter.getFilterItems().add(new FilterItemInfo("state", state));
			}
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

		boolean isArchived = para.getBoolean(IS_ARCHIVED);
		if (!isArchived) {
			//			filter.getFilterItems().add(new FilterItemInfo("isArchived", Boolean.FALSE));
		}
	}

	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", null, CompareType.NOTEQUALS));
		getCostCenterFilter(filter, para);
		getContractTypeFilter(filter, para);
		getOtherFilter(filter, para);
		getProjectFilter(filter, para);
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

		String[] companyIds = (String[]) this.txtCompany.getUserObject();
		if (!FDCHelper.isEmpty(companyIds)) {
			param.add(COMPANY_IDS, companyIds);
		}

		String[] projIds = (String[]) this.txtProject.getUserObject();
		if (!FDCHelper.isEmpty(projIds)) {
			param.add(PROJECT_IDS, projIds);
		}

		String[] contractTypeIds = (String[]) this.txtContractType.getUserObject();
		if (!FDCHelper.isEmpty(contractTypeIds)) {
			param.add(CONTRACT_TYPE_IDS, contractTypeIds);
		}

		AdminOrgUnitInfo respDept = (AdminOrgUnitInfo) this.prmtRespDept.getValue();
		if (respDept != null) {
			param.add(RESPDEPT_ID, respDept.getId().toString());
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

		if (this.radioSave.isSelected()) {
			param.add(CONTRACT_STATE, 0);
		} else if (this.radioSubmit.isSelected()) {
			param.add(CONTRACT_STATE, 1);
		} else if (this.radioAuditing.isSelected()) {
			param.add(CONTRACT_STATE, 2);
		} else if (this.radioAudited.isSelected()) {
			param.add(CONTRACT_STATE, 3);
		} else if (this.radioStateAll.isSelected()) {
			param.add(CONTRACT_STATE, 4);
		} else if (this.rbnCancel.isSelected()) {
			param.add(CONTRACT_STATE, 5);
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

		param.add(IS_ARCHIVED, chkArchived.isSelected());

		return param.getCustomerParams();
	}
	
	private void initCompanyDlg(String[] selectCompayIds) throws OUException, Exception {
//		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
//		if (cuInfo == null) {
//			throw new OUException(OUException.CU_CAN_NOT_NULL);
//		}
		
		//某些客户只有一个集团CU;实际中应以财务组织过滤
		OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
		if(orgUnitInfo==null){
			orgUnitInfo=SysContext.getSysContext().getCurrentOrgUnit();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COSTCENTER, "", orgUnitInfo.getId().toString(), null, FDCHelper.getActionPK(this.actionListOnLoad));
		this.companySelectDlg = new TreeSelectDialog(this, orgTreeModel, "getUnit,getId,toString", selectCompayIds);
	}

	private void initContractTypeDlg(String[] contractTypeIds) throws Exception, BOSException {
		TreeModel treeModel = FDCClientHelper.createDataTree(ContractTypeFactory.getRemoteInstance(), null);
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) treeModel.getRoot(), "isIsEnabled", Boolean.FALSE);
		contractTypeSelectDlg = new TreeSelectDialog(this, treeModel, "getId,toString", contractTypeIds);
	}

	protected void initControlByDateType() {
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

	private void initProjectDlg(String[] projectIds) throws Exception {
		ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
		KDTree tree = new KDTree();
		if (this.companySelectDlg != null && this.companySelectDlg.getSelectTree() != null) {
			builder.buildByCostOrgTree(tree, this.companySelectDlg.getSelectTree());
		} else {
			builder.build(this.listUI, tree, this.actionListOnLoad);
		}
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(), "isIsEnabled", Boolean.FALSE);
		projectSelectDlg = new TreeSelectDialog(this, model, "getId,toString", projectIds);
	}

	// 责任人是否可以选择其他部门的人员
	private boolean canSelecOtherOrgPerson() {
		boolean canSelectOtherOrgPerson = false;
		try {
			canSelectOtherOrgPerson = FDCUtils.getBooleanValue4FDCParamByKey(null, null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		} 
		return canSelectOtherOrgPerson;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}
		FDCClientUtils.setRespDeptF7(prmtRespDept, this, canSelecOtherOrgPerson() ? null : cuInfo.getId().toString());

		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
	}

	protected void radioByDay_actionPerformed(ActionEvent e) throws Exception {
		this.initControlByDateType();
	}

	protected void radioByMonth_actionPerformed(ActionEvent e) throws Exception {
		this.initControlByDateType();
	}

	protected void radioByQuarter_actionPerformed(ActionEvent e) throws Exception {
		super.radioByQuarter_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByYear_actionPerformed(ActionEvent e) throws Exception {
		this.initControlByDateType();
	}

	private void setCompanyByTree(Object object) {
		List companyIdList = new ArrayList();
		if (object != null) {
			List companyList = (List) object;
			String text = "";
			for (int i = 0; i < companyList.size(); i++) {
				OrgStructureInfo company = (OrgStructureInfo) companyList.get(i);
				companyIdList.add(company.getUnit().getId().toString());
				if (company.getUnit().isIsCostOrgUnit() || company.getUnit().isIsProfitOrgUnit()) {
					if (company.isIsLeaf()) {
						if (!text.equals("")) {
							text += ",";
						}
						text += company.getUnit().getName();
					}
				}
			}
			Object[] ids = companyIdList.toArray(new String[] {});
			ArrayList oldArray = new ArrayList(FDCHelper.getSetByArray((String[]) ids));
			ArrayList newArray = new ArrayList(FDCHelper.getSetByArray((String[]) this.txtCompany.getUserObject()));
			if (!oldArray.equals(newArray)) {
				try {
					this.initProjectDlg(null);
				} catch (Exception e) {
					e.printStackTrace();
					SysUtil.abort();
				}
				this.txtProject.setUserObject(null);
				this.txtProject.setText(null);
			}
			this.txtCompany.setText(text);
			if (FDCHelper.isEmpty(ids)) {
				this.txtCompany.setUserObject(null);
			} else {
				this.txtCompany.setUserObject(ids);
			}
		}
	}

	private void setContractTypeByTree(Object object) {
		List contractTypeIdList = new ArrayList();
		if (object != null) {
			List contractTypeList = (List) object;
			StringBuffer text = new StringBuffer();
			for (int i = 0; i < contractTypeList.size(); i++) {
				if (contractTypeList.get(i) instanceof ContractTypeInfo) {
					ContractTypeInfo contractType = (ContractTypeInfo) contractTypeList.get(i);
					contractTypeIdList.add(contractType.getId().toString());
					///if (contractType != null && contractType.isIsLeaf()) {
					//update by rneliang
					if(contractType.isIsLeaf()){
						if (text != null && !text.toString().equals("")) {
							text.append(",");
						}
						text.append(contractType.getName());
					}
				}
			}
			this.txtContractType.setText(text.toString());
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
			initCompanyDlg(para.getStringArray(COMPANY_IDS));
			setCompanyByTree(companySelectDlg.getUserObject());
			initProjectDlg(para.getStringArray(PROJECT_IDS));
			setProjectByTree(projectSelectDlg.getUserObject());
			initContractTypeDlg(para.getStringArray(CONTRACT_TYPE_IDS));
			setContractTypeByTree(contractTypeSelectDlg.getUserObject());
		} catch (Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		//责任部门
		if(para.get(RESPDEPT_ID)!=null){
			AdminOrgUnitInfo respDept = null;
			try {
				respDept = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(BOSUuid.read(para.get(RESPDEPT_ID))));
			} catch (Exception e) {
				e.printStackTrace();
				SysUtil.abort();
			}
			this.prmtRespDept.setValue(respDept);
		}else{
			this.prmtRespDept.setValue(null);
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

		if (para.getInt(CONTRACT_STATE) == 0) {
			this.radioSave.setSelected(true);
		} else if (para.getInt(CONTRACT_STATE) == 1) {
			this.radioSubmit.setSelected(true);
		} else if (para.getInt(CONTRACT_STATE) == 2) {
			this.radioAuditing.setSelected(true);
		} else if (para.getInt(CONTRACT_STATE) == 3) {
			this.radioAudited.setSelected(true);
		} else if (para.getInt(CONTRACT_STATE) == 4) {
			this.radioStateAll.setSelected(true);
		} else if (para.getInt(CONTRACT_STATE) == 5) {
			this.rbnCancel.setSelected(true);
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

		this.chkArchived.setSelected(para.getBoolean(IS_ARCHIVED));

		super.setCustomerParams(para.getCustomerParams());
	}

	private void setProjectByTree(Object object) {
		List projectIdList = new ArrayList();
		if (object != null) {
			List projectList = (List) object;
			String text = "";
			for (int i = 0; i < projectList.size(); i++) {
				if (projectList.get(i) instanceof ProjectInfo) {
					ProjectInfo project = (ProjectInfo) projectList.get(i);
					if (project.isIsLeaf()) {
						if (!text.equals("")) {
							text += ",";
						}
						text += project.getName();
					}
					projectIdList.add(project.getId().toString());
				}
			}
			this.txtProject.setText(text);
			Object[] ids = projectIdList.toArray(new String[] {});
			if (FDCHelper.isEmpty(ids)) {
				this.txtProject.setUserObject(null);
			} else {
				this.txtProject.setUserObject(ids);
			}
		}
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

	private void popNode(java.util.List list, DefaultKingdeeTreeNode root) {
		for (Enumeration c = root.children(); c.hasMoreElements();) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) c.nextElement();
			// 描述：非叶子结节嵌套调用，叶子节点则加入
			if (!node.isLeaf()) {
				popNode(list, node);
			}
			// 分期的上级组织也加上,老数据一些单据成本中心保存的为上级的
			list.add(node.getUserObject());
		}
	}

	protected boolean isOnlyQueryAudited() {
		return true;
	}
	
	

}