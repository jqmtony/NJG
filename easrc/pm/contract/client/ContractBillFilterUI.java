package com.kingdee.eas.port.pm.contract.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.framework.client.ListUI;

public class ContractBillFilterUI extends ContractFullFilterUI {
    private static final Logger logger = CoreUIObject.getLogger("com.kingdee.eas.fdc.contract.client.ContractBillFilterUI");
    
    protected Set authorizedOrgs = null;
    private FullOrgUnitInfo company = null;
	private CurProjectInfo project = null;
	private FilterInfo otherFilter = null;
	private boolean containConWithoutTxt;

	private ContractTypeInfo contractType;
	
	public ContractBillFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception {
		super(listUI, actionListOnLoad);
	}
	public void setOtherFilter(FilterInfo otherFilter) {
		this.otherFilter = otherFilter;
	}
	public void clear() {
		this.radioByDay.setSelected(true);
		this.radioSettleAll.setSelected(true);
		this.chkAllContract.setSelected(false);
		this.f7Provider.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierDefaultQuery");
		initControlByDateType();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.btnCompanySelect.setVisible(false);
		this.btnProjectSelect.setVisible(false);
		this.btnContractTypeSelect.setVisible(false);
	}
	public void setAuditedState() {
		this.radioSave.setSelected(false);
		this.radioSave.setEnabled(false);
		this.radioSubmit.setSelected(false);
		this.radioSubmit.setEnabled(false);
		this.radioAuditing.setSelected(false);
		this.radioAuditing.setEnabled(false);
		this.radioAudited.setSelected(false);
		this.radioAudited.setEnabled(false);
		this.radioStateAll.setSelected(false);
		this.radioStateAll.setEnabled(false);
		this.rbnCancel.setSelected(false);
		this.rbnCancel.setEnabled(false);
		this.radioAudited.setSelected(true);
		
		
	}
	public void setAuthorizedOrgs(Set authorizedOrgs) {
		if (authorizedOrgs == null) {
			authorizedOrgs = (Set) ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
			if (authorizedOrgs == null) {
				try {
					authorizedOrgs = new HashSet();
					Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
							new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
					if (orgs != null) {
						Set orgSet = orgs.keySet();
						Iterator it = orgSet.iterator();
						while (it.hasNext()) {
							authorizedOrgs.add(it.next());
						}
					}
				} catch (Exception e) {
					logger.error(e.getCause(), e);
				}
			}
		}
		this.authorizedOrgs = authorizedOrgs;
	}
	public void setCompany(FullOrgUnitInfo company) {
		if (company == null) {
			this.txtCompany.setText(null);
		} else {
			this.txtCompany.setText(company.getName());
		}
		this.company = company;
	}

	public void setProject(CurProjectInfo project) {
		if (project == null) {
			this.txtProject.setText(null);
			this.txtProject.setUserObject(null);
		} else {
			this.txtProject.setText(project.getName());
		}
		this.project = project;
	}

	public void setContractType(boolean containConWithoutTxt, ContractTypeInfo contractType) {
		this.containConWithoutTxt = containConWithoutTxt;
		this.contractType = contractType;		
		if (contractType != null) {
			this.txtContractType.setText(contractType.getName());
		} else {
			this.txtContractType.setText("所有合同");
		}
	}

	protected void getCostCenterFilter(FilterInfo filter, FDCCustomerParams para) {
		OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
		if (orgUnitInfo == null) {
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();
		}
		if (company != null) {
			orgUnitInfo = company;
		}
		try {
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
			view.getFilter().getFilterItems().add(new FilterItemInfo("longNumber", orgUnitInfo.getLongNumber() + "!%", CompareType.LIKE));
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
		}
	}
	protected void getProjectFilter(FilterInfo filter, FDCCustomerParams para) {
		if (project != null) {
			try {
				Set idSet = FDCClientUtils.genProjectIdSet(project.getId());
				filter.getFilterItems().add(new FilterItemInfo("curProject.id", idSet, CompareType.INCLUDE));
			} catch (Exception e) {
				logger.error(e);
			}
		} else {
		}
	}
	
	protected void getContractTypeFilter(FilterInfo filter, FDCCustomerParams para) {
		try {
			if (contractType != null) {
				BOSUuid id = contractType.getId();
				Set idSet = FDCClientUtils.genContractTypeIdSet(id);
				filter.getFilterItems().add(new FilterItemInfo("contractType.id", idSet, CompareType.INCLUDE));
			} else {
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	protected void getOtherFilter(FilterInfo filter, FDCCustomerParams para) {
		super.getOtherFilter(filter, para);
		addOtherFilter(filter);
	}

	private void addOtherFilter(FilterInfo filter) {
		try {
			filter.mergeFilter(otherFilter, "and");
		} catch (BOSException e) {
			logger.error(e.getCause(), e);
		}
	}
	
}
