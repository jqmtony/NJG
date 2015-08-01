/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectTypeCollection;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.RptParamConst;
import com.kingdee.eas.fdc.basedata.TargetTypeFactory;
import com.kingdee.eas.fdc.basedata.TargetTypeInfo;
import com.kingdee.eas.fdc.contract.client.F7ProjectTreeSelectorPromptBox;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.framework.bireport.client.BireportBaseMainUI;
import com.kingdee.eas.framework.report.util.RptConditionManager;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptParamsUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * 描述:项目指标查询过滤界面
 * 
 * @author liupd date:2006-10-27
 *         <p>
 * @version EAS5.2
 */
public class ProjectIndexDataFilterUI extends AbstractProjectIndexDataFilterUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectIndexDataFilterUI.class);

	/**
	 * output class constructor
	 */
	public ProjectIndexDataFilterUI(BireportBaseMainUI listUI, ItemAction actionListOnLoad) throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
	}

	/**
	 * output chkWholeProject_stateChanged method
	 */
	protected void chkWholeProject_stateChanged(javax.swing.event.ChangeEvent e)
			throws Exception {
		super.chkWholeProject_stateChanged(e);
		if (this.chkWholeProject.isSelected()) {
			this.prmtProductType.setEnabled(false);
			this.prmtProductType.setValue(null);
		} else {
			this.prmtProductType.setEnabled(true);
		}
	}

	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	protected ItemAction actionListOnLoad;

	private TreeSelectDialog companySelectDlg;

	private boolean isLoaded;

	protected BireportBaseMainUI listUI;

	private TreeSelectDialog projectSelectDlg;
	
	private TreeSelectDialog targetTypeSelectDlg;

	protected void btnCompanySelect_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.companySelectDlg == null) {
			this.initCompanyDlg(null);
		}
		Object object = companySelectDlg.showDialog();
		setCompanyByTree(object);
		super.btnCompanySelect_actionPerformed(e);
	}

	public FilterInfo getFilterInfo() {
		return new FilterInfo();
	}

	public CustomerParams getCustomerParams() {
		
    	CustomerParams cp=new CustomerParams();
    	RptParamsUtil.setToCustomerParams(cp,getCustomCondition());
    	return cp;
		//return getCustomCondition();
	}

	public void setCustomerParams(CustomerParams cp) {
		try {
			setParam(RptParamsUtil.getFromCustomerParams(cp));
		} catch (EASBizException e) {
			logger.error(e.getMessage(), e);
			handUIException(e);
			SysUtil.abort();
		}
	}

	public void clear() {
		this.companySelectDlg = null;
		this.txtCompany.setText(null);
		this.txtCompany.setUserObject(null);
		CostCenterOrgUnitInfo currentCompany = SysContext.getSysContext()
				.getCurrentCostUnit();
		if (currentCompany.isIsBizUnit()) {
			this.btnCompanySelect.setEnabled(true);
			this.txtCompany.setText(currentCompany.getName());
			this.txtCompany.setUserObject(new String[] { currentCompany.getId()
					.toString() });
		}
		this.projectSelectDlg = null;
//		this.txtProject.setText(null);
//		this.txtProject.setUserObject(null);
		this.prmtProjectType.setValue(null);
		this.prmtProject.setValue(null);
		this.txtTargetType.setText(null);
		this.txtTargetType.setUserObject(null);
		this.txtTargetType.setEnabled(false);
		this.prmtProductType.setValue(null);
		this.comboStage.setSelectedItem(ProjectStageEnum.DYNCOST);

	}

	private void initCompanyDlg(String[] selectCompayIds) throws OUException,
			Exception {
		/*CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}*/
//		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(
//				OrgViewType.COSTCENTER, "", cuInfo.getId().toString(), listUI
//						.getMetaDataPK(), FDCHelper
//						.getActionPK(this.actionListOnLoad));
		
		//非CU组织会过滤出当前CU的其它财务组织数据，以当前财务组织过滤
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

	public void onLoad() throws Exception {
		super.onLoad();
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
		prmtProject.setEnabledMultiSelection(true);
		prmtProject.setDisplayFormat("$name$");
		prmtProject.setEditFormat("$number$");
		prmtProject.setCommitFormat("$number$");
		prmtProjectType.setEnabledMultiSelection(true);
		prmtProjectType.setDisplayFormat("$name$");
		prmtProjectType.setEditFormat("$number$");
		prmtProjectType.setCommitFormat("$number$");
/*		final CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();
		if(currentCostUnit!=null&&currentCostUnit.isIsBizUnit()){
			txtCompany.setText(currentCostUnit.getName());
			txtCompany.setUserObject(new String[]{currentCostUnit.getId().toString()});
		}*/
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
				prmtProject.setValue(null);
			}
			this.txtCompany.setText(text);
			if (FDCHelper.isEmpty(ids)) {
				this.txtCompany.setUserObject(null);
			} else {
				this.txtCompany.setUserObject(ids);
			}
		}
	}


	private void initTargetTypeDlg(String[] contractTypeIds)
			throws Exception, BOSException {
		TreeModel treeModel = FDCClientHelper.createDataTree(
				TargetTypeFactory.getRemoteInstance(), null);
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) treeModel
				.getRoot(), "isIsEnabled", Boolean.FALSE);
		targetTypeSelectDlg = new TreeSelectDialog(this, treeModel,
				"getId,toString", contractTypeIds);
	}
	private void setTargetTypeByTree(Object object) {
		List targetTypeIdList = new ArrayList();
		if (object != null) {
			List targetTypeList = (List) object;
			String text = "";
			for (int i = 0; i < targetTypeList.size(); i++) {
				if (targetTypeList.get(i) instanceof TargetTypeInfo) {
					TargetTypeInfo targetType = (TargetTypeInfo) targetTypeList
							.get(i);
					///targetTypeIdList.add(targetType.getId().toString());
					//update by renliang
					if(targetType.getId().toString()!=null && targetType.getId().toString().length()>0){
						targetTypeIdList.add(targetType.getId().toString());
					}
						
					//if (targetType != null && targetType.isIsLeaf()) {
					if (targetType.isIsLeaf()) {
						if (!text.equals("")) {
							text += ",";
						}
						text += targetType.getName();
					}
				}
			}
			this.txtTargetType.setText(text);
			Object[] ids = targetTypeIdList.toArray(new String[] {});
			if (FDCHelper.isEmpty(ids)) {
				this.txtTargetType.setUserObject(null);
			} else {
				this.txtTargetType.setUserObject(ids);

			}
		}
	}
	protected void btnTargetTypeSelect_actionPerformed(
			ActionEvent e) throws Exception {
		if (this.targetTypeSelectDlg == null) {
			this.initTargetTypeDlg(null);
		}
		Object object = targetTypeSelectDlg.showDialog();
		setTargetTypeByTree(object);
		super.btnTargetTypeSelect_actionPerformed(e);
	}
	public boolean verify() {
		String[] projIds = FDCHelper.getF7Ids(prmtProject);
		if (FDCHelper.isEmpty(projIds)) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("noSelectProj"));
			return false;
		}
		return true;
	}

	public void onInit(RptParams initParams) throws Exception {
		/* TODO 自动生成方法存根 */

	}

	public RptParams getCustomCondition() {
		RptConditionManager rcm = new RptConditionManager();
		rcm.recordAllStatus(this);
		rcm.setProperty(RptParamConst.KEY_COMPANYIDS,
				(String[]) this.txtCompany.getUserObject());
		rcm.setProperty(RptParamConst.KEY_PROJECTIDS,FDCHelper.getF7Ids(prmtProject));
		rcm.setProperty(RptParamConst.KEY_PROJECTTYPEIDS,FDCHelper.getF7Ids(prmtProjectType));
		rcm.setProperty(RptParamConst.KEY_TARGETTYPEIDS,
				(String[]) this.txtTargetType.getUserObject());
		rcm.setProperty(RptParamConst.KEY_PRODCUTTYPEIDS, FDCHelper
				.getF7Ids(this.prmtProductType));
		rcm.setProperty(RptParamConst.KEY_WHOLEPROJ, Boolean.valueOf(chkWholeProject.isSelected()));
		rcm.setProperty(RptParamConst.KEY_PROJECTSTAGE, comboStage.getSelectedItem());
		
		return rcm.toRptParams();
	}

	public void setCustomCondition(RptParams params) {
		RptConditionManager rcm = new RptConditionManager(params);
		rcm.restoreAllStatus(this);
		
		txtCompany.setUserObject(params.getObject(RptParamConst.KEY_COMPANYIDS));
		
		if(params.getObject(RptParamConst.KEY_PROJECTTYPEIDS) != null) {
			String[] ids = (String[])params.getObject(RptParamConst.KEY_PROJECTTYPEIDS);
			if(!FDCHelper.isEmpty(ids)) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.getSetByArray(ids), CompareType.INCLUDE));
				view.setFilter(filter);
				view.getSelector().add("id");
				view.getSelector().add("number");
				view.getSelector().add("name");
				
				ProjectTypeCollection colls = null;
				try {
					colls = ProjectTypeFactory.getRemoteInstance().getProjectTypeCollection(view);
				} catch (Exception e) {
					handUIException(e);
					SysUtil.abort();
				} 
				prmtProjectType.setValue(colls.toArray());
			}
		}
		
		if(params.getObject(RptParamConst.KEY_PROJECTIDS) != null) {
			String[] ids = (String[])params.getObject(RptParamConst.KEY_PROJECTIDS);
			if(!FDCHelper.isEmpty(ids)) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.getSetByArray(ids), CompareType.INCLUDE));
				view.setFilter(filter);
				view.getSelector().add("id");
				view.getSelector().add("number");
				view.getSelector().add("name");
				
				CurProjectCollection colls = null;
				try {
					colls = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
				} catch (Exception e) {
					handUIException(e);
					SysUtil.abort();
				} 
				prmtProject.setValue(colls.toArray());
			}
		}
		txtTargetType.setUserObject(params
				.getObject(RptParamConst.KEY_TARGETTYPEIDS));
		String[] ptIds = (String[])params.getObject(RptParamConst.KEY_PRODCUTTYPEIDS);
		
		
		if(!FDCHelper.isEmpty(ptIds)) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.getSetByArray(ptIds), CompareType.INCLUDE));
			view.setFilter(filter);
			ProductTypeCollection productTypeCollection = null;
			try {
				productTypeCollection = ProductTypeFactory.getRemoteInstance().getProductTypeCollection(view);
			} catch (BOSException e) {
				handUIException(e);
				SysUtil.abort();
			}
			prmtProductType.setValue(productTypeCollection.toArray());
		}
		else {
			prmtProductType.setValue(null);
		}
		
		chkWholeProject.setSelected(params.getBoolean(RptParamConst.KEY_WHOLEPROJ));
		
		comboStage.setSelectedItem(params.getObject(RptParamConst.KEY_PROJECTSTAGE));
	}

	protected void prmtProjectType_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtProjectType_dataChanged(e);
		prmtProject.setValue(null);
	}
	protected void prmtProjectType_willShow(SelectorEvent e) throws Exception {
		super.prmtProjectType_willShow(e);
	}

	protected void prmtProject_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtProject_dataChanged(e);
	}
	protected void prmtProject_willShow(SelectorEvent e) throws Exception {
		F7ProjectTreeSelectorPromptBox projectTreeSelectorPromptBox = getProjectSelecotorBox();
		prmtProject.setSelector(projectTreeSelectorPromptBox);
		if(true){
			return;
		}
		prmtProject.getQueryAgent().resetRuntimeEntityView();
		Set projectTypeSet = FDCHelper.getF7IdSet(prmtProjectType);
		EntityViewInfo view = new EntityViewInfo();//prmtProject.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		if(projectTypeSet!=null&&projectTypeSet.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("projectType.id",projectTypeSet,CompareType.INCLUDE));
		}
		String[]companyIds=(String[])txtCompany.getUserObject();
		if(companyIds!=null&&companyIds.length>0){
			Set companySet=new HashSet();
			for(int i=0;i<companyIds.length;i++){
				companySet.add(companyIds[i]);
			}
			//找出它对应的成本中心
			EntityViewInfo prjView=new EntityViewInfo();
			FilterInfo myFilter=new FilterInfo();
			prjView.setFilter(myFilter);
			myFilter.getFilterItems().add(new FilterItemInfo("costCenterOU",companySet,CompareType.INCLUDE));
			prjView.getSelector().add("curProject.id");
			prjView.getSelector().add("curProject.longNumber");
			final ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(prjView);
			FilterInfo temp=new  FilterInfo();
			for(int i=0;i<projectWithCostCenterOUCollection.size();i++){
				String longNumber = projectWithCostCenterOUCollection.get(i).getCurProject().getLongNumber();
				FilterInfo tempFilter=new FilterInfo();
				tempFilter.getFilterItems().add(new  FilterItemInfo("longNumber",longNumber,CompareType.EQUALS));
				tempFilter.getFilterItems().add(new  FilterItemInfo("longNumber",longNumber+".%",CompareType.LIKE));
				tempFilter.setMaskString("#0 or #1");
				temp.mergeFilter(tempFilter, "or");
			}
			filter.mergeFilter(temp, "and");
			
		}
		
		prmtProject.setEntityViewInfo(view);
		super.prmtProject_willShow(e);
	}

	private F7ProjectTreeSelectorPromptBox getProjectSelecotorBox()
			throws Exception {
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
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(),
				"isIsEnabled", Boolean.FALSE);
		F7ProjectTreeSelectorPromptBox.setDisableSelectNodeByProjectType((DefaultKingdeeTreeNode) model.getRoot(), FDCHelper.getF7IdSet(prmtProjectType));
		F7ProjectTreeSelectorPromptBox projectTreeSelectorPromptBox=new F7ProjectTreeSelectorPromptBox(this, model, "getId,toString",
				FDCHelper.getF7Ids(prmtProject));
		return projectTreeSelectorPromptBox;
	}
}