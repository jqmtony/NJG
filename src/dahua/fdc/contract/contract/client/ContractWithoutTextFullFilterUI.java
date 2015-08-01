/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
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

/**
 * output class name
 */
public class ContractWithoutTextFullFilterUI extends
		AbstractContractWithoutTextFullFilterUI {
	private static final String CONTRACT_STATE = "contractState";

	private static final String DATE_TO = "dateTo";

	private static final String DATE_FROM = "dateFrom";

	private static final String RESPDEPT_ID = "respdeptId";
	private static final String PAYEE_ID = "payeeId";

	private static final String PROJECT_IDS = "projectIds";

	private static final String COMPANY_IDS = "companyIds";
	
	protected static final String CONTRACT_TYPE_IDS = "contractTypeIds";

	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	protected ItemAction actionListOnLoad;

	private TreeSelectDialog companySelectDlg;
	
	private TreeSelectDialog contractTypeSelectDlg;

	private boolean isLoaded;

	protected ListUI listUI;

	private TreeSelectDialog projectSelectDlg;
	
	//是否使用成本月结,当前财务组织的期间
	protected Date[] pkdate;
	
	// 责任人是否可以选择其他部门的人员
	private boolean canSelecOtherOrgPerson() {
		boolean canSelectOtherOrgPerson = false;
		try {
			canSelectOtherOrgPerson = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		} catch (BOSException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		return canSelectOtherOrgPerson;
	}
	/**
	 * output class constructor
	 */
	public ContractWithoutTextFullFilterUI(ListUI listUI,
			ItemAction actionListOnLoad) throws Exception {
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
	
	private void initContractTypeDlg(String[] contractTypeIds) throws Exception, BOSException {
		TreeModel treeModel = FDCClientHelper.createDataTree(ContractTypeFactory.getRemoteInstance(), null);
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) treeModel.getRoot(), "isIsEnabled", Boolean.FALSE);
		contractTypeSelectDlg = new TreeSelectDialog(this, treeModel, "getId,toString", contractTypeIds);
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
			txtContractType.setText(text.toString());
			Object[] ids = contractTypeIdList.toArray(new String[] {});
			if (FDCHelper.isEmpty(ids)) {
				this.txtContractType.setUserObject(null);
			} else {
				this.txtContractType.setUserObject(ids);

			}
		}
	}

	protected void btnCompanySelect_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.companySelectDlg == null) {
			this.initCompanyDlg(null);
		}
		Object object = companySelectDlg.showDialog();
		setCompanyByTree(object);
		super.btnCompanySelect_actionPerformed(e);
	}

	protected void btnProjectSelect_actionPerformed(ActionEvent e)
			throws Exception {
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
		CostCenterOrgUnitInfo currentCompany = SysContext.getSysContext()
				.getCurrentCostUnit();
		if (currentCompany.isIsBizUnit()) {
			this.btnCompanySelect.setEnabled(false);
			this.txtCompany.setText(currentCompany.getName());
			this.txtCompany.setUserObject(new String[] { currentCompany.getId()
					.toString() });
		}
		this.projectSelectDlg = null;
		this.txtProject.setText(null);
		this.txtProject.setUserObject(null);

		this.prmtRespDept.setData(null);
		this.f7Payee.setData(null);
		this.pkDateFrom.setValue(null);
		this.pkDateTo.setValue(pkdate[1]);
		this.radioStateAll.setSelected(true);
		this.f7Payee
				.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
	}
	protected void getCompanyFilter(FDCCustomerParams para, FilterInfo filter) {
		if (para.isNotNull(COMPANY_IDS)) {
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", FDCHelper
							.getSetByArray(para.getStringArray(COMPANY_IDS)),
							CompareType.INCLUDE));
		}else{

			if(companySelectDlg==null){
				try{
					initCompanyDlg(null);
				}catch(Exception e){
					e.printStackTrace();
					SysUtil.abort();
				}
			}
			TreeModel tree = (DefaultTreeModel) companySelectDlg.getTree();
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) tree.getRoot();
			java.util.List list = new ArrayList();
			if(root.getUserObject()!=null){
				list.add(root.getUserObject());
			}
			popNode(list, root);
			HashSet set=new HashSet();
			for(Iterator iter=list.iterator();iter.hasNext();){
				OrgStructureInfo company = (OrgStructureInfo)iter.next();
				set.add(company.getUnit().getId().toString());
			}
			
			if(set.size()>0){
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", set,CompareType.INCLUDE));	
			}
		}
	}

	protected void getProjectFilter(FDCCustomerParams para, FilterInfo filter) {
		if (para.isNotNull(PROJECT_IDS)) {
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.id", FDCHelper
							.getSetByArray(para.getStringArray(PROJECT_IDS)),
							CompareType.INCLUDE));
		}
		else {
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		}
	}

	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", null, CompareType.NOTEQUALS));

		this.getCompanyFilter(para, filter);
		this.getProjectFilter(para, filter);
		getContractTypeFilter(filter, para);
		//责任部门
		if(para.isNotNull(RESPDEPT_ID)){
			filter.getFilterItems().add(new FilterItemInfo("useDepartment.id",para.getString(RESPDEPT_ID)));
		}
		
		if (para.isNotNull(PAYEE_ID)) {
			filter.getFilterItems().add(
					new FilterItemInfo("receiveUnit.id", para.get(PAYEE_ID)));
		}
		String state = null;
		if(para.isNotNull(CONTRACT_STATE)){
			if (para.getInt(CONTRACT_STATE) == 0) {
				state = FDCBillStateEnum.SAVED_VALUE;
			} else if (para.getInt(CONTRACT_STATE) == 1) {
				state = FDCBillStateEnum.SUBMITTED_VALUE;
			} else if (para.getInt(CONTRACT_STATE) == 2) {
				state = FDCBillStateEnum.AUDITTING_VALUE;
			} else if (para.getInt(CONTRACT_STATE) == 3) {
				state = FDCBillStateEnum.AUDITTED_VALUE;
			}
		}
		if (state != null) {
			filter.getFilterItems().add(new FilterItemInfo("state", state));
		}
		if (para.isNotNull(DATE_FROM)) {
			filter.getFilterItems().add(
					new FilterItemInfo("signDate", para.getDate(DATE_FROM),
							CompareType.GREATER_EQUALS));
		}
		if (para.isNotNull(DATE_TO)) {
			filter.getFilterItems().add(
					new FilterItemInfo("signDate", FDCHelper.getNextDay(para.getDate(DATE_TO)), CompareType.LESS));
		}
		return filter;
	}

	private void initCompanyDlg(String[] selectCompayIds) throws OUException,
			Exception {
//		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
//		if (cuInfo == null) {
//			throw new OUException(OUException.CU_CAN_NOT_NULL);
//		}
		//非CU组织会过滤出当前CU的其它财务组织数据，以当前财务组织过滤
		OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
		if(orgUnitInfo==null){
			orgUnitInfo=SysContext.getSysContext().getCurrentOrgUnit();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(
				OrgViewType.COSTCENTER, "", orgUnitInfo.getId().toString(),
				null, FDCHelper
						.getActionPK(this.actionListOnLoad));
		this.companySelectDlg = new TreeSelectDialog(this, orgTreeModel,
				"getUnit,getId,toString", selectCompayIds);
	}

	private void initProjectDlg(String[] projectIds) throws Exception {
		ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
		KDTree tree = new KDTree(true);
		if (this.companySelectDlg!=null&&this.companySelectDlg.getSelectTree() != null) {
			builder.buildByCostOrgTree(tree, this.companySelectDlg.getSelectTree());
		} else {
			builder.build(this.listUI, tree, this.actionListOnLoad);
		}
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(),
				"isIsEnabled", Boolean.FALSE);
		projectSelectDlg = new TreeSelectDialog(this,model,
				"getId,toString", projectIds);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}
		FDCClientUtils.setRespDeptF7(prmtRespDept, this, canSelecOtherOrgPerson() ? null :cuInfo.getId().toString());
		
		if (!isLoaded) {
			this.clear();
		}
	}

	private void setCompanyByTree(Object object) {
		List companyIdList = new ArrayList();
		if (object != null) {
			List companyList = (List) object;
			String text = "";
			for (int i = 0; i < companyList.size(); i++) {
				OrgStructureInfo company = (OrgStructureInfo) companyList
						.get(i);
				companyIdList.add(company.getUnit().getId().toString());
				if (company.getUnit().isIsCostOrgUnit()
						|| company.getUnit().isIsProfitOrgUnit()) {
					if (company.isIsLeaf()) {
						if (!text.equals("")) {
							text += ",";
						}
						text += company.getUnit().getName();
					}
				}
			}
			Object[] ids = companyIdList.toArray(new String[] {});
			ArrayList oldArray = new ArrayList(FDCHelper
					.getSetByArray((String[]) ids));
			ArrayList newArray = new ArrayList(FDCHelper
					.getSetByArray((String[]) this.txtCompany.getUserObject()));
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
	public void setCustomerParams(CustomerParams cp)
	{
		FDCCustomerParams para=new FDCCustomerParams(cp);
		try {
			initCompanyDlg(para.getStringArray(COMPANY_IDS));
			setCompanyByTree(companySelectDlg.getUserObject());
			initProjectDlg(para.getStringArray(PROJECT_IDS));
			initContractTypeDlg(para.getStringArray(CONTRACT_TYPE_IDS));
			setProjectByTree(projectSelectDlg.getUserObject());
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
		if (para.isNotNull(PAYEE_ID)) {
			SupplierInfo supplier = null;
			try {
				supplier = SupplierFactory.getRemoteInstance().getSupplierInfo(
						new ObjectUuidPK(BOSUuid.read(para.get(PAYEE_ID))));
			} catch (EASBizException e) {
				e.printStackTrace();
				SysUtil.abort();
			} catch (BOSException e) {
				e.printStackTrace();
				SysUtil.abort();
			}
			this.f7Payee.setValue(supplier);
		} else {
			this.f7Payee.setValue(null);
		}
		this.pkDateFrom.setValue(para.getDate(DATE_FROM));
		this.pkDateTo.setValue(para.getDate(DATE_TO));
		if(para.isNotNull(CONTRACT_STATE)){
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
			}
		}
		super.setCustomerParams(cp);
	}
	public CustomerParams getCustomerParams()
	{
		FDCCustomerParams param=new FDCCustomerParams();
		param.add(COMPANY_IDS, (String[]) this.txtCompany.getUserObject());
		param.add(PROJECT_IDS, (String[]) this.txtProject.getUserObject());

		AdminOrgUnitInfo respDept = (AdminOrgUnitInfo) this.prmtRespDept.getValue();
		if (respDept != null) {
			param.add(RESPDEPT_ID, respDept.getId().toString());
		}
		
		SupplierInfo supplierInfo = (SupplierInfo) this.f7Payee.getValue();
		if (supplierInfo != null) {
			param.add(PAYEE_ID,supplierInfo.getId().toString());
		}
		param.add(DATE_FROM, (Date)this.pkDateFrom.getValue());
		param.add(DATE_TO, (Date)this.pkDateTo.getValue());
		String[] contractTypeIds = (String[]) this.txtContractType.getUserObject();
		if (!FDCHelper.isEmpty(contractTypeIds)) {
			param.add(CONTRACT_TYPE_IDS, contractTypeIds);
		}
		if (this.radioSave.isSelected()) {
			param.add(CONTRACT_STATE,0);
		} else if (this.radioSubmit.isSelected()) {
			param.add(CONTRACT_STATE,1);
		} else if (this.radioAuditing.isSelected()) {
			param.add(CONTRACT_STATE,2);
		} else if (this.radioAudited.isSelected()) {
			param.add(CONTRACT_STATE,3);
		} else if (this.radioStateAll.isSelected()) {
			param.add(CONTRACT_STATE,4);
		}
	
		return param.getCustomerParams();
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
		if (this.pkDateTo.getValue() != null
				&& this.pkDateFrom.getValue() != null) {
			if (((Date) this.pkDateTo.getValue()).before((Date) this.pkDateFrom
					.getValue())) {
				MsgBox.showWarning(this, EASResource.getString(resourcePath,
						"DateBoundErrer"));
				return false;
			}
		}
		return true;
	}
	private void popNode(java.util.List list, DefaultKingdeeTreeNode root) {
		for (Enumeration c = root.children(); c.hasMoreElements();) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) c
					.nextElement();
			//描述：非叶子结节嵌套调用，叶子节点则加入
			if(!node.isLeaf()) {
				popNode(list, node);
			}
			//分期的上级组织也加上,老数据一些单据成本中心保存的为上级的
			list.add(node.getUserObject());
		}
	}
}