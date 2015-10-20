/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
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
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.PutOutTypeEnum;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


/**
 * output class name
 */
public class GuerdonBillFullFilterUI extends AbstractGuerdonBillFullFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(GuerdonBillFullFilterUI.class);
    
	private static final String CONTRACT_STATE = "contractState";

	private static final String PUTOUTTYPE = "putOutType";
	
	private static final String DATE_TO = "dateTo";

	private static final String DATE_FROM = "dateFrom";

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

    public GuerdonBillFullFilterUI(GuerdonBillFullListUI listUI, ItemAction load) throws Exception{
		super();
		this.listUI = listUI;
		this.actionListOnLoad = load;
		
		pkdate = FDCClientHelper.getCompanyCurrentDate();
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output btnCompanySelect_actionPerformed method
     */
    protected void btnCompanySelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
		if (this.companySelectDlg == null) {
			this.initCompanyDlg(null);
		}
		Object object = companySelectDlg.showDialog();
		setCompanyByTree(object);
    }

    /**
     * output btnProjectSelect_actionPerformed method
     */
    protected void btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
		if (this.projectSelectDlg == null) {
			this.initProjectDlg(null);
		}
		Object object = projectSelectDlg.showDialog();
		setProjectByTree(object);
    }
    
    protected void btnContractTypeSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
		if (this.contractTypeSelectDlg == null) {
			this.initContractTypeDlg(null);
		}
		Object object = contractTypeSelectDlg.showDialog();
		setContractTypeByTree(object);
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

			if(companySelectDlg==null){
				try{
					initCompanyDlg(null);
				}catch(Exception e){
					e.printStackTrace();
					SysUtil.abort();
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
		if (para.isNotNull(PROJECT_IDS)) {
			filter.getFilterItems().add(
					new FilterItemInfo("contract.curProject.id", FDCHelper
							.getSetByArray(para.getStringArray(PROJECT_IDS)),
							CompareType.INCLUDE));
		}
		else {
			filter.getFilterItems().add(
					new FilterItemInfo("contract.curProject.isEnabled", Boolean.TRUE));
		}
		
		if (para.isNotNull(CONTRACT_TYPE_IDS)) {
			filter.getFilterItems().add(
					new FilterItemInfo("contract.contractType.id", FDCHelper
							.getSetByArray(para.getStringArray(CONTRACT_TYPE_IDS)),
							CompareType.INCLUDE));
		}
		else {//如果没有选择合同类型，则要过滤掉禁用的合同类型的合同
			filter.getFilterItems().add(new FilterItemInfo("contract.contractType.isEnabled", Boolean.TRUE));
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
		
		if (para.isNotNull(PUTOUTTYPE)) {
			filter.getFilterItems().add(
					new FilterItemInfo("putOutType", para.getString(PUTOUTTYPE)));
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
				filter.getFilterItems().add(new FilterItemInfo("contract.state", state));
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
		
		if (comboPutOutType.getSelectedItem() != CompensationFullFilterUI.ALL) {
			 param.add(PUTOUTTYPE, ((PutOutTypeEnum)comboPutOutType.getSelectedItem()).getValue());
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
		/*CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}*/
		
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
		
		comboPutOutType.addItem(CompensationFullFilterUI.ALL);
		comboPutOutType.addItems(PutOutTypeEnum.getEnumList().toArray());
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

		if (para.isNotNull(PUTOUTTYPE)) {
			FDCCustomerParams.setSelectedItem(comboPutOutType,para.getString(PUTOUTTYPE));
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
			//修改循环获得orgUnit的逻辑
			if(!node.isLeaf()) {
				popNode(list, node);
			}//else{//分期的上级组织也加上,老数据一些单据成本中心保存的为上级的
				list.add(node.getUserObject());				
			//}
		}
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


		this.pkDateFrom.setValue(null);
		this.pkDateTo.setValue(pkdate[1]);
		this.radioStateAll.setSelected(true);
		this.rbnAll.setSelected(true);		
	}
}