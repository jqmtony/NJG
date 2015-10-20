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
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TempEvalContractChangeFullFilterUI extends
AbstractTempEvalContractChangeFullFilterUI {
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	protected ItemAction actionListOnLoad;

	private TreeSelectDialog companySelectDlg;

	private boolean isLoaded;

	protected ListUI listUI;

	private TreeSelectDialog projectSelectDlg;
	
	//是否使用成本月结,当前财务组织的期间
	private Date[] pkdate ;

	/**
	 * output class constructor
	 */
	public TempEvalContractChangeFullFilterUI(ListUI listUI, ItemAction actionListOnLoad)
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
		this.f7ContractChangeType.setValue(null);

		this.f7PartB.setData(null);
		//日期
		this.pkDateFrom.setValue(pkdate[0]);
		this.pkDateTo.setValue(pkdate[1]);
		this.radioStateAll.setSelected(true);

		this.f7ContractChangeType
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ChangeTypeQuery");
		this.f7PartB
				.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
	}

	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", null, CompareType.NOTEQUALS));
		String[] companyIds = para.getStringArray(KEY_COMPANYIDS);
		if (companyIds != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", FDCHelper
							.getSetByArray(companyIds),
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
			if (root.getUserObject() != null) {
				list.add(root.getUserObject());
			}
			popNode(list, root);
			HashSet set = new HashSet();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				OrgStructureInfo company = (OrgStructureInfo) iter.next();
				set.add(company.getUnit().getId().toString());
			}

			if (set.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", set, CompareType.INCLUDE));
			}
		}
		if (para.isNotNull(KEY_PROJECTIDS)) {
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.curProject.id", FDCHelper
							.getSetByArray(para.getStringArray(KEY_PROJECTIDS)),
							CompareType.INCLUDE));
		}
		else {
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.curProject.isEnabled", Boolean.TRUE));
		}
		
		if (para.isNotNull(KEY_CHANGETYPEIDS)) {
			filter.getFilterItems().add(
					new FilterItemInfo("changeType.id", FDCHelper
							.getSetByArray(para.getStringArray(KEY_CHANGETYPEIDS)),
							CompareType.INCLUDE));
		}
		else {
			filter.getFilterItems().add(
					new FilterItemInfo("changeType.isEnabled", Boolean.TRUE));
		}
		if (para.isNotNull(KEY_PARTBID)) {
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.partB.id", para
							.get(KEY_PARTBID)));
		}
		if (para.isNotNull(KEY_DATEFROM)) {
			Timestamp beginTime = new Timestamp(para.getDate(KEY_DATEFROM).getTime());
			filter.getFilterItems().add(
					new FilterItemInfo("conductTime", beginTime,
							CompareType.GREATER_EQUALS));
		}
		if (para.isNotNull(KEY_DATETO)) {
			Calendar beginCal = new GregorianCalendar();
			beginCal.setTime(para.getDate(KEY_DATETO));
			beginCal.set(Calendar.DATE, beginCal.get(Calendar.DATE) + 1);
			Timestamp endTime = new Timestamp(beginCal.getTime().getTime());
			filter.getFilterItems()
					.add(
							new FilterItemInfo("conductTime", endTime,
									CompareType.LESS));
		}
		if(para.isNotNull(KEY_STATE)) {
			String state = null;
			if (para.getInt(KEY_STATE) == 0) {
				state = FDCBillStateEnum.SAVED_VALUE;
			} else if (para.getInt(KEY_STATE) == 1) {
				state = FDCBillStateEnum.SUBMITTED_VALUE;
			} else if (para.getInt(KEY_STATE) == 2) {
				state = FDCBillStateEnum.AUDITTING_VALUE;
			} else if (para.getInt(KEY_STATE) == 3) {
				state = FDCBillStateEnum.AUDITTED_VALUE;
			} else if (para.getInt(KEY_STATE) == 5) {
				state = FDCBillStateEnum.ANNOUNCE_VALUE;
			} else if (para.getInt(KEY_STATE) == 6) {
				state = FDCBillStateEnum.VISA_VALUE;
			}
			if (state != null) {
				filter.getFilterItems().add(new FilterItemInfo("state", state));
			}
		}
		filter.getFilterItems().add(new FilterItemInfo("contractBill.costProperty",CostPropertyEnum.TEMP_EVAL_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isFromTempEvalChange",Boolean.TRUE));
		return filter;
	}

	private static final String KEY_COMPANYIDS = "companyIds";
	private static final String KEY_PROJECTIDS = "projectIds";
	private static final String KEY_CHANGETYPEIDS = "changeTypeIds";
	private static final String KEY_PARTBID = "partBId";
	private static final String KEY_DATEFROM = "dateFrom";
	private static final String KEY_DATETO = "dateTo";
	private static final String KEY_STATE = "state";
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams  param = new FDCCustomerParams ();
		param.add(KEY_COMPANYIDS, (String[]) this.txtCompany.getUserObject());
		param.add(KEY_PROJECTIDS, (String[]) this.txtProject.getUserObject());
		

		Object[] changes = (Object[]) this.f7ContractChangeType.getValue();
		if (!FDCHelper.isEmpty(changes)) {
			String[] ids = new String[changes.length];
			for (int i = 0; i < changes.length; i++) {
				ids[i] = ((ChangeTypeInfo) changes[i]).getId().toString();
			}
			param.add(KEY_CHANGETYPEIDS, ids);
		}

		SupplierInfo supplierInfo = (SupplierInfo) this.f7PartB.getValue();
		if (supplierInfo != null) {
			param.add(KEY_PARTBID, supplierInfo.getId().toString());
		}
		param.add(KEY_DATEFROM, (Date) this.pkDateFrom.getValue());
		param.add(KEY_DATETO, (Date) this.pkDateTo.getValue());

		if (this.radioSave.isSelected()) {
			param.add(KEY_STATE, 0);
		} 
		else if (this.radioSubmit.isSelected()) {
			param.add(KEY_STATE, 1);
		}
//			else if (this.radioAuditing.isSelected()) {
//			param.add(KEY_STATE, 2);
//		} 
		else if (this.radioAudited.isSelected()) {
			param.add(KEY_STATE, 3);
		} else if (this.radioStateAll.isSelected()) {
			param.add(KEY_STATE, 4);
		}  else if (this.radioVisa.isSelected()) {
			param.add(KEY_STATE, 6);
		}
		return param.getCustomerParams();
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
		KDTree tree = new KDTree();
		if (this.companySelectDlg!=null&&this.companySelectDlg.getSelectTree() != null) {
			builder.buildByCostOrgTree(tree, this.companySelectDlg.getSelectTree());
		} else {
			builder.build(this.listUI, tree, this.actionListOnLoad);
		}
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(),
				"isIsEnabled", Boolean.FALSE);
		projectSelectDlg = new TreeSelectDialog(this, model,
				"getId,toString", projectIds);
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
			ArrayList oldArray=new ArrayList(FDCHelper.getSetByArray((String[]) ids));
			ArrayList newArray=new ArrayList(FDCHelper.getSetByArray((String[]) this.txtCompany.getUserObject()));
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

	public void setCustomerParams(CustomerParams  cp) {
		if(cp == null )
		{
			return ;
		}

		FDCCustomerParams param = new FDCCustomerParams(cp);
		try {
			initCompanyDlg(param.getStringArray(KEY_COMPANYIDS));
			setCompanyByTree(companySelectDlg.getUserObject());
			initProjectDlg(param.getStringArray(KEY_PROJECTIDS));
			setProjectByTree(projectSelectDlg.getUserObject());
		} catch (Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		String[] changeTypeIds = param.getStringArray(KEY_CHANGETYPEIDS);
		if (!FDCHelper.isEmpty(changeTypeIds)) {
			ChangeTypeCollection changes = null;
			try {
				changes = ChangeTypeFactory.getRemoteInstance()
						.getChangeTypeCollection(
								FDCHelper.getIncludeEntityView("id", changeTypeIds));
				this.f7ContractChangeType.setValue(changes.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
				SysUtil.abort();
			}
		}
		
		String partBId = param.get(KEY_PARTBID);
		if (partBId != null) {
			SupplierInfo supplier = null;
			try {
				supplier = SupplierFactory.getRemoteInstance().getSupplierInfo(
						new ObjectUuidPK(BOSUuid.read(partBId)));
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
		this.pkDateFrom.setValue(param.getDate(KEY_DATEFROM));
		this.pkDateTo.setValue(param.getDate(KEY_DATETO));
		if(param.isNotNull(KEY_STATE)) {
			int state = param.getInt(KEY_STATE);
			if (state == 0) {
				this.radioSave.setSelected(true);
			} else if (state == 1) {
				this.radioSubmit.setSelected(true);
			} 
//			else if (state == 2) {
//				this.radioAuditing.setSelected(true);
//			} 
			else if (state == 3) {
				this.radioAudited.setSelected(true);
			} else if (state == 4) {
				this.radioStateAll.setSelected(true);
			} else if (state == 6) {
				this.radioVisa.setSelected(true);
			} 
		}
		super.setCustomerParams(param.getCustomerParams());
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
			if(node.isLeaf()) {
				list.add(node.getUserObject());
			}
			popNode(list, node);
		}
	}
}