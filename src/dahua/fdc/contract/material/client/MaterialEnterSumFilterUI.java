/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.framework.client.ListUI;

/**
 * output class name
 */
public class MaterialEnterSumFilterUI extends AbstractMaterialEnterSumFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialEnterSumFilterUI.class);
    
    private TreeSelectDialog companySelectDlg;
    
    private TreeSelectDialog projectSelectDlg;
    
    private TreeSelectDialog materialSelectDlg;
    
    protected ItemAction actionListOnLoad;
    
    protected ListUI listUI;
    
    private static final String COMPANY_IDS = "companyIds";
     
    private static final String PROJECT_IDS = "projectIds";
    
    private static final String SUPPLIER_IDS = "supplierIds";
    
    private static final String ENTERTIME_FROM = "enterTime_From";
    
    private static final String ENTERTIME_TO = "enterTime_To";
    
    /**
     * output class constructor
     */
    public MaterialEnterSumFilterUI(ListUI list,ItemAction action) throws Exception
    {
        super();
        this.actionListOnLoad = action;
        this.listUI = list;
    }

    public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		setComponentState();
		//初始化供应商F7
		FDCClientUtils.initSupplierF7(this, this.prmtSupplier, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());		
		
	}
    
    private void setComponentEnabled(boolean enabled) {
		btnCostCenterSelect.setEnabled(enabled);
		btnCurProjectSelect.setEnabled(enabled);
		prmtSupplier.setEnabled(enabled);
		enterTimeFrom.setEnabled(enabled);
		enterTimeTo.setEnabled(enabled);
		txtCostCenter.setEnabled(enabled);
		txtCurProject.setEnabled(enabled);
	}
   protected void setComponentState()
   {
	   btnCostCenterSelect.setEnabled(true);
       btnCurProjectSelect.setEnabled(true);
       btnMaterialSelect.setEnabled(false); //待确认需求
       
       prmtSupplier.setEnabledMultiSelection(true);  //供应商F7多选
       prmtSupplier.setEditFormat("$number$");
       prmtSupplier.setDisplayFormat("$name$");
       prmtSupplier.setCommitFormat("$id$");
       this.chkBoxAllPlan.addChangeListener(new ChangeListener(){
		public void stateChanged(ChangeEvent e) {
			setComponentEnabled(!chkBoxAllPlan.isSelected());
		}});
   }

    /**
     * output btnCostCenterSelect_actionPerformed method
     */
    protected void btnCostCenterSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if (this.companySelectDlg == null) {
			this.initCompanyDlg(null);
		}
		Object object = companySelectDlg.showDialog();
		setCompanyByTree(object);
        super.btnCostCenterSelect_actionPerformed(e);
    }
    
 
    protected void btnCurProjectSelect_actionPerformed(ActionEvent e)
			throws Exception {
    	if (this.projectSelectDlg == null) {
			this.initProjectDlg(null);
		}
		Object object = projectSelectDlg.showDialog();
		setProjectByTree(object);
		super.btnCurProjectSelect_actionPerformed(e);
	}

	private void initCompanyDlg(String[] selectCompayIds) throws OUException, Exception {
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COSTCENTER, "", null, null, FDCHelper.getActionPK(this.actionListOnLoad));
		this.companySelectDlg = new TreeSelectDialog(this, orgTreeModel, "getUnit,getId,toString", selectCompayIds);
	}
    
	private void initProjectDlg(String[] projectIds) throws Exception {
		KDTree tree = new KDTree();
		if (this.companySelectDlg != null && this.companySelectDlg.getSelectTree() != null) {
			ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
			builder.buildByCostOrgTree(tree, this.companySelectDlg.getSelectTree());
		} else {
			ProjectTreeBuilder builder = new ProjectTreeBuilder(true, true);
			builder.build(this.listUI, tree, this.actionListOnLoad, null);
		}
		
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(), "isIsEnabled", Boolean.FALSE);
		projectSelectDlg = new TreeSelectDialog(this, model, "getId,toString", projectIds);
	}
	
	private void initMaterialDlg() throws Exception {
		
	}
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		String[]  companyIds = (String[])txtCostCenter.getUserObject();
		if (!FDCHelper.isEmpty(companyIds)) {
			param.add(COMPANY_IDS, companyIds);
		}
		String[] projIds = (String[]) this.txtCurProject.getUserObject();
		if (!FDCHelper.isEmpty(projIds)) {
			param.add(PROJECT_IDS, projIds);
		}
		if(this.prmtSupplier.getValue()!=null){
			Object obj = this.prmtSupplier.getValue();
			if( obj instanceof Object[]){
				Object[] objs = (Object[])obj;
				String[] supplerIds = new String[objs.length];
				for(int i =0;i<objs.length;i++)
				{
					SupplierInfo info = (SupplierInfo)objs[i];
					if(info!=null)
					supplerIds[i]=info.getId().toString();
				}
				if(!FDCHelper.isEmpty(supplerIds)){
					param.add(SUPPLIER_IDS, supplerIds);
				}
			}else{
				SupplierInfo info = (SupplierInfo)obj;
				param.add(SUPPLIER_IDS, new String[]{info.getId().toString()});
			}
		}
		
		if(!FDCHelper.isEmpty(this.enterTimeFrom.getValue()))
		{
			param.add(ENTERTIME_FROM,(Date)enterTimeFrom.getValue());
		}
		if(!FDCHelper.isEmpty(this.enterTimeTo.getValue()))
		{
			param.add(ENTERTIME_TO, (Date)enterTimeTo.getValue());
		}
		if(this.chkBoxAllPlan.isSelected()==true)
		{
			param.add("IS_SHOWALL", true);
		}
		return param.getCustomerParams();
	}

	public void setCustomerParams(CustomerParams cp) {
		// TODO Auto-generated method stub
		if (cp == null)
			return;	

		FDCCustomerParams para = new FDCCustomerParams(cp);
		try {
			initCompanyDlg(para.getStringArray(COMPANY_IDS));
			setCompanyByTree(companySelectDlg.getUserObject());
			initProjectDlg(para.getStringArray(PROJECT_IDS));
			setProjectByTree(projectSelectDlg.getUserObject());
//			initContractTypeDlg(para.getStringArray(CONTRACT_TYPE_IDS));
//			setContractTypeByTree(contractTypeSelectDlg.getUserObject());
            this.enterTimeFrom.setValue(para.getDate(ENTERTIME_FROM));
            this.enterTimeTo.setValue(para.getDate(ENTERTIME_TO));			
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setCustomerParams(cp);
	}

	public void setFilterInfo(FilterInfo filterInfo) {
		// TODO Auto-generated method stub
		super.setFilterInfo(filterInfo);
	}
	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
		
	//不勾选“全部材料进场计划”时
		if(!para.getBoolean("IS_SHOWALL")){
			//成本中心不选择是默认取当前成本中心及下级所子组织节点	
			if(para.isNotNull(COMPANY_IDS)){
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", FDCHelper.getSetByArray(para.getStringArray(COMPANY_IDS)), CompareType.INCLUDE));
			}
			if (para.isNotNull(PROJECT_IDS)) {
				filter.getFilterItems().add(new FilterItemInfo("curProjectId", FDCHelper.getSetByArray(para.getStringArray(PROJECT_IDS)), CompareType.INCLUDE));
			} 
	//		else {// 如果没有选择工程项目，则要过滤掉禁用的工程项目的合同
	//			filter.getFilterItems().add(new FilterItemInfo("matPlan.curProject_isEnabled", Boolean.TRUE));
	//		}
			if(para.isNotNull(SUPPLIER_IDS)){
				filter.getFilterItems().add(new FilterItemInfo("supplierId",FDCHelper.getSetByArray(para.getStringArray(SUPPLIER_IDS)),CompareType.INCLUDE));
			}
			if(para.isNotNull(ENTERTIME_FROM))
			{
				filter.getFilterItems().add(new FilterItemInfo("entrys_enterTime",para.getDate(ENTERTIME_FROM),CompareType.GREATER_EQUALS));
			}
			if(para.isNotNull(ENTERTIME_TO)){
				filter.getFilterItems().add(new FilterItemInfo("entrys_enterTime",FDCHelper.getNextDay(para.getDate(ENTERTIME_TO)),CompareType.LESS));
			}
		}//end if para.getBoolean("IS_SHOWALL")
		
		//添加"核定数量"大于"已汇总数量"过滤条件		
		// FilterItemInfo构造方法中的isRefCrossTable参数传入false，因为默认为false。 Added By Owen_wen
		FilterItemInfo item = new FilterItemInfo("matPlan_entrys_auditQuantity - isNull(matPlan_entrys_hasSumQty,0)",new Integer(0), CompareType.GREATER, false, true);
		filter.getFilterItems().add(item);
		return filter;
	}
	public boolean verify() {
		// TODO Auto-generated method stub
		return super.verify();
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
			ArrayList newArray = new ArrayList(FDCHelper.getSetByArray((String[]) this.txtCostCenter.getUserObject()));
			if (!oldArray.equals(newArray)) {
				try {
					this.initProjectDlg(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.txtCurProject.setUserObject(null);
				this.txtCurProject.setText(null);
			}
			this.txtCostCenter.setText(text);
			if (FDCHelper.isEmpty(ids)) {
				this.txtCostCenter.setUserObject(null);
			} else {
				this.txtCostCenter.setUserObject(ids);
			}
		}
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
			this.txtCurProject.setText(text);
			Object[] ids = projectIdList.toArray(new String[] {});
			if (FDCHelper.isEmpty(ids)) {
				this.txtCurProject.setUserObject(null);
			} else {
				this.txtCurProject.setUserObject(ids);
			}
		}
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
	
}