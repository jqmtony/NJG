/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
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
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractSettleFullFilterUI extends
		AbstractContractSettleFullFilterUI {
    private static final Logger logger = CoreUIObject.getLogger("com.kingdee.eas.fdc.contract.client.ContractSettleFullFilterUI");
	private static final String CONTRACT_STATE = "contractState";

	private static final String DATE_TO = "dateTo";

	private static final String DATE_FROM = "dateFrom";

	private static final String PART_ID = "partId";

	private static final String PROJECT_IDS = "projectIds";

	private static final String COMPAY_IDS = "compayIds";
	
	private static final String CONTRACT_TYPE_IDS = "contractTypeIds";

	private static final String CON_STATE = "conState";
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	protected ItemAction actionListOnLoad;

	private TreeSelectDialog companySelectDlg;

	private TreeSelectDialog contractTypeSelectDlg;
	
	private boolean isLoaded;

	protected ListUI listUI;

	private TreeSelectDialog projectSelectDlg;
	
	//是否使用成本月结,当前财务组织的期间
	private Date[] pkdate ;
	/**
	 * output class constructor
	 */
	public ContractSettleFullFilterUI(ListUI listUI, ItemAction actionListOnLoad)
			throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
		
		pkdate = FDCClientHelper.getCompanyCurrentDate();
	}

	protected void btnCompanySelect_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.companySelectDlg == null) {
			this.initCompanyDlg(null);
		}
		Object object = companySelectDlg.showDialog();
		setCompanyByTree(object);
		//super.btnCompanySelect_actionPerformed(e);
	}

	protected void btnProjectSelect_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.projectSelectDlg == null) {
			this.initProjectDlg(null);
		}
		Object object = projectSelectDlg.showDialog();
		setProjectByTree(object);
		//super.btnProjectSelect_actionPerformed(e);
	}
	
	protected void btnContractTypeSelect_actionPerformed(
			ActionEvent e) throws Exception {
		if (this.contractTypeSelectDlg == null) {
			this.initContractTypeDlg(null);
		}
		Object object = contractTypeSelectDlg.showDialog();
		setContractTypeByTree(object);
		//super.btnContractTypeSelect_actionPerformed(e);
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

		this.contractTypeSelectDlg = null;
		this.txtContractType.setText(null);
		this.txtContractType.setUserObject(null);
		
		this.f7PartB.setData(null);
		this.pkDateFrom.setValue(pkdate[0]);
		this.pkDateTo.setValue(pkdate[1]);
		this.radioStateAll.setSelected(true);
		
		FDCClientUtils.initSupplierF7(this, f7PartB);
	}

	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", null, CompareType.NOTEQUALS));
		if (para.isNotNull(COMPAY_IDS)) {
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", FDCHelper
							.getSetByArray(para.getStringArray(COMPAY_IDS)),
							CompareType.INCLUDE));
		}else{
			try {
				OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
				if(orgUnitInfo==null){
					orgUnitInfo=SysContext.getSysContext().getCurrentOrgUnit();
				}
				//获取当前用户有权限的成本中心
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
				view.getFilter().getFilterItems().add(new FilterItemInfo("longNumber", orgUnitInfo.getLongNumber()+"!%", CompareType.LIKE));
				//获取当前用户在当前组织下有权限的成本中心. TODO 需要将当前组织加进来不
				FullOrgUnitCollection orgs = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
				costIdSet.clear();
				for(int i = 0; i < orgs.size(); ++i){
					costIdSet.add(orgs.get(i).getId().toString());
				}
				if(orgUnitInfo.isIsCostOrgUnit()){
					costIdSet.add(orgUnitInfo.getId().toString());
				}
				if(costIdSet.size()>0){
					filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", costIdSet,CompareType.INCLUDE));	
				}
			} catch (Exception e) {
				logger.error(e.getCause(), e);
				SysUtil.abort();
			}
		}
		if (para.isNotNull(PROJECT_IDS)) {
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.curProject.id", FDCHelper
							.getSetByArray(para.getStringArray(PROJECT_IDS)),
							CompareType.INCLUDE));
		}
		else {
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.curProject.isEnabled", Boolean.TRUE));
		}
		
		if (para.isNotNull(CONTRACT_TYPE_IDS)) {
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.contractType.id", FDCHelper
							.getSetByArray(para.getStringArray(CONTRACT_TYPE_IDS)),
							CompareType.INCLUDE));
		}
		else {//如果没有选择合同类型，则要过滤掉禁用的合同类型的合同
			filter.getFilterItems().add(new FilterItemInfo("contractBill.contractType.isEnabled", Boolean.TRUE));
		}
		
		if (para.isNotNull(PART_ID)) {
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.partB.id", para
							.get(PART_ID)));
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
			}
			if (state != null) {
				filter.getFilterItems().add(new FilterItemInfo("state", state));
			}
		}
		if (para.isNotNull(DATE_FROM)) {
			Timestamp beginTime = new Timestamp(para.getDate(DATE_FROM).getTime());
			filter.getFilterItems().add(
					new FilterItemInfo("createTime", beginTime,
							CompareType.GREATER_EQUALS));
		}
		if (para.isNotNull(DATE_TO)) {
			Calendar beginCal = new GregorianCalendar();
			beginCal.setTime(para.getDate(DATE_TO));
			beginCal.set(Calendar.DATE, beginCal.get(Calendar.DATE) + 1);
			Timestamp endTime = new Timestamp(beginCal.getTime().getTime());
			filter.getFilterItems()
					.add(
							new FilterItemInfo("createTime", endTime,
									CompareType.LESS));
		}
		
		if (para.isNotNull(CON_STATE)) {
			String state = null;
			if (para.getInt(CON_STATE) == 0) {
				state = FDCBillStateEnum.AUDITTED_VALUE;
			} else if (para.getInt(CON_STATE) == 1) {
				state = FDCBillStateEnum.CANCEL_VALUE;
			}
			
			if (state != null) {
				filter.getFilterItems().add(new FilterItemInfo("contractBill.state", state));
			}
		}
		return filter;
	}

	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		param.add(COMPAY_IDS, (String[]) this.txtCompany.getUserObject());
		param.add(PROJECT_IDS, (String[]) this.txtProject.getUserObject());

		String[] contractTypeIds = (String[]) this.txtContractType.getUserObject();
		if(!FDCHelper.isEmpty(contractTypeIds)) {
			param.add(CONTRACT_TYPE_IDS, contractTypeIds);
		}

		SupplierInfo supplierInfo = (SupplierInfo) this.f7PartB.getValue();
		if (supplierInfo != null) {
			param.add(PART_ID, supplierInfo.getId().toString());
		}
		param.add(DATE_FROM, (Date) this.pkDateFrom.getValue());
		param.add(DATE_TO, (Date) this.pkDateTo.getValue());

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
		}
		
		if (this.rbnAuditted.isSelected()) {
			param.add(CON_STATE, 0);
		} else if (this.rbnCanceled.isSelected()) {
			param.add(CON_STATE, 1);
		} else if (this.rbnAll.isSelected()) {
			param.add(CON_STATE, 2);
		} 
		
		return param.getCustomerParams();
	}

	private void initCompanyDlg(String[] selectCompayIds) throws OUException,
			Exception {
		//通过CU构建树,即通过CU做过滤
//		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
//		if (cuInfo == null) {
//			throw new OUException(OUException.CU_CAN_NOT_NULL);
//		}
		//某些客户只有一个集团CU;实际中应以财务组织过滤
		OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
		if(orgUnitInfo==null){
			orgUnitInfo=SysContext.getSysContext().getCurrentOrgUnit();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(
				OrgViewType.COSTCENTER, "", orgUnitInfo.getId().toString(), null, FDCHelper
						.getActionPK(this.actionListOnLoad));
		this.companySelectDlg = new TreeSelectDialog(this, orgTreeModel,
				"getUnit,getId,toString", selectCompayIds);
	}

	private void initProjectDlg(String[] projectIds) throws Exception {
		ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
		KDTree tree = new KDTree();
		if (this.companySelectDlg != null
				&& this.companySelectDlg.getSelectTree() != null) {
			builder.buildByCostOrgTree(tree, this.companySelectDlg.getSelectTree());
		} else {
			builder.build(this.listUI, tree, this.actionListOnLoad);
		}
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(),
				"isIsEnabled", Boolean.FALSE);
		projectSelectDlg = new TreeSelectDialog(this, model, "getId,toString",
				projectIds);
	}
	
	private void initContractTypeDlg(String[] contractTypeIds)
	throws Exception, BOSException {
		TreeModel treeModel = FDCClientHelper
				.createDataTree(ContractTypeFactory.getRemoteInstance(), null);
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) treeModel.getRoot(),
				"isIsEnabled", Boolean.FALSE);
		contractTypeSelectDlg = new TreeSelectDialog(this, treeModel,
				"getId,toString", contractTypeIds);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
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

	public void setCustomerParams(CustomerParams cp) {

		FDCCustomerParams para = new FDCCustomerParams(cp);
		try {
			initCompanyDlg(para.getStringArray(COMPAY_IDS));
			setCompanyByTree(companySelectDlg.getUserObject());
			initProjectDlg(para.getStringArray(PROJECT_IDS));
			setProjectByTree(projectSelectDlg.getUserObject());
		} catch (Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}

		if (para.get(PART_ID) != null) {
			SupplierInfo supplier = null;
			try {
				supplier = SupplierFactory.getRemoteInstance().getSupplierInfo(
						new ObjectUuidPK(BOSUuid.read(para.get(PART_ID))));
			} catch (EASBizException e) {
				e.printStackTrace();
				SysUtil.abort();
			} catch (BOSException e) {
				e.printStackTrace();
				SysUtil.abort();
			}
			this.f7PartB.setValue(supplier);
		} else {
			this.f7PartB.setValue(null);
		}
		this.pkDateFrom.setValue(para.getDate(DATE_FROM));
		this.pkDateTo.setValue(para.getDate(DATE_TO));
		if (para.isNotNull(CONTRACT_STATE)) {
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
		
		if (para.isNotNull(CON_STATE)) {
			if (para.getInt(CON_STATE) == 0) {
				this.rbnAuditted.setSelected(true);
			} else if (para.getInt(CON_STATE) == 1) {
				this.rbnCanceled.setSelected(true);
			} else if (para.getInt(CON_STATE) == 2) {
				this.rbnAll.setSelected(true);
			} 
		}
		
		super.setCustomerParams(cp);
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
	
	private void setContractTypeByTree(Object object) {
		List contractTypeIdList = new ArrayList();
		if (object != null) {
			List contractTypeList = (List) object;
			String text = "";
			for (int i = 0; i < contractTypeList.size(); i++) {
				if (contractTypeList.get(i) instanceof ContractTypeInfo) {
					ContractTypeInfo contractType = (ContractTypeInfo) contractTypeList
							.get(i);
					contractTypeIdList.add(contractType.getId().toString());
					//if (contractType != null && contractType.isIsLeaf()) {
					//update by renliang
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
			if(node.isLeaf()) {
				list.add(node.getUserObject());
			}
			popNode(list, node);
		}
	}
}